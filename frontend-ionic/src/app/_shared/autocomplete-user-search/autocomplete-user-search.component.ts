import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {RecentUser} from '../../../shared/recentusers/model/recent-user';
import {AuthenticationCacheService} from '../../../shared/auth/authentication-cache.service';
import {GroupCacheService} from '../../../shared/groups/group-cache.service';
import {ReplaySubject} from 'rxjs';
import {RecentUsersService} from "../../../shared/recentusers/recent-users.service";

const MAX_SEARCH_RESULTS_TO_SHOW: number = 3;

@Component({
  selector: 'app-autocomplete-user-search',
  templateUrl: './autocomplete-user-search.component.html',
  styleUrls: ['./autocomplete-user-search.component.css']
})
export class AutocompleteUserSearchComponent implements OnInit {
  public searchResults: RecentUser[];

  @Input() inputText: HTMLInputElement;
  @Input() $clear: ReplaySubject<void>;
  @Input() notInCurrentGroupCondition: boolean = false;
  @Input() showInitialSearchResults: boolean = false;

  @Output() userSelected: EventEmitter<RecentUser> = new EventEmitter<RecentUser>();
  @Output() userFound: EventEmitter<RecentUser> = new EventEmitter<RecentUser>();

  constructor(
    private auth: AuthenticationCacheService,
    private recentUsers: RecentUsersService,
    private groupCache: GroupCacheService,
  ) { }

  ngOnInit(): void {
    this.onKeyUp();
    this.onFocusOut();
    this.onClear();

    if(this.showInitialSearchResults)
      this.searchResults = this.recentUsers.findAll()
        .filter(user => this.notInCurrentGroupCondition ? !this.groupCache.existsMemberByEmail(user.email) : true)
        .slice(0, MAX_SEARCH_RESULTS_TO_SHOW);
  }

  onClear(): void {
    if(this.$clear !== undefined){
      this.$clear.subscribe(() => {
        setTimeout(() => this.searchResults = [], 0);
      });
    }
  }

  onKeyUp(): void {
    this.inputText.onkeyup = event => {
      // @ts-ignore
      this.search(event.target.value);
    };
  }

  onFocusOut(): void {
    this.inputText.addEventListener('focusout', event => {
      //When clicked an optino of it loses the focus and this gets executed first
      setTimeout(() => this.searchResults = [], 0);
    });
  }

  onUserSelected(user: RecentUser) {
    this.searchResults = [];
    this.userSelected.emit(user);
  }

  public search(email: string): void {
    this.searchResults = [];
    if(!email || email == '') return;

    this.searchResults = this.recentUsers.findAll()
      .filter(user => this.matchByEmail(user.email, user) && user.email != this.auth.getEmail())
      .filter(user => this.notInCurrentGroupCondition ? !this.groupCache.existsMemberByEmail(user.email) : true)
      .splice(0, MAX_SEARCH_RESULTS_TO_SHOW);

    const exactlyUserTyped = this.searchResults.filter(user => user.email == email).length == 1;

    if(exactlyUserTyped){
      const userFound = this.searchResults.filter(user => user.email == email)[0];

      this.searchResults = [];
      this.userFound.emit(userFound);
    }
  }

  private matchByEmail(email: string, user: RecentUser): boolean {
    return user.email.includes(email);
  }
}
