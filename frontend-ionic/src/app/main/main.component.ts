import {Component, OnInit} from '@angular/core';
import {GroupsApiService} from "../../shared/groups/groups-api.service";
import {GroupCacheService} from "../../shared/groups/group-cache.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationCacheService} from "../../shared/auth/authentication-cache.service";
import {RecentUsersService} from "../../shared/recentusers/recent-users.service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  public isGroupHTTPRequestDone: boolean = false;

  constructor(
    public auth: AuthenticationCacheService,
    private groupService: GroupsApiService,
    public groupState: GroupCacheService,
    private actualRoute: ActivatedRoute,
    private router: Router,
    private recentUsers: RecentUsersService,
  ) { }

  ngOnInit(): void {
    const userId: string = this.auth.getUserId();

    this.groupService.getCurrentGroup(userId).subscribe(res => {
      this.recentUsers.saveAll(res.members);

      this.isGroupHTTPRequestDone = true;
    }, err => this.isGroupHTTPRequestDone = true);
  }
}
