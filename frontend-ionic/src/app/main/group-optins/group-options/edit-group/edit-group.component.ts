import {AfterViewInit, ApplicationRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {Group} from "../../../../../shared/groups/model/group";
import {GroupCacheService} from "../../../../../shared/groups/group-cache.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {EditGroupRequest} from "../../../../../shared/groups/api/request/edit-group-request";
import {GroupsApiService} from "../../../../../shared/groups/groups-api.service";

@Component({
  selector: 'app-edit-group',
  templateUrl: './edit-group.component.html',
  styleUrls: ['./edit-group.component.css']
})
export class EditGroupComponent implements OnInit, AfterViewInit {
  @ViewChild('editGroupNewMoneyInput') public editGroupNewMoneyInput: ElementRef<HTMLInputElement>;
  public editGroupForm: FormGroup;

  constructor(
    private groupState: GroupCacheService,
    private modalService: NgbModal,
    private groupService: GroupsApiService,
    private applicationRef: ApplicationRef,
  ) { }

  ngOnInit(): void {
    this.setUpEditGroupForm();
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.editGroupNewMoneyInput.nativeElement.focus();
    }, 0)
  }

  private setUpEditGroupForm(): void {
    this.editGroupForm = new FormGroup({
      newMoney: new FormControl(this.currentGroup().money, [Validators.required, Validators.min(0.1), Validators.max(10000)]),
      newDescription: new FormControl(this.currentGroup().description, [Validators.required, Validators.minLength(1), Validators.maxLength(16)])
    });
  }
  get newMoney(): AbstractControl {return <AbstractControl>this.editGroupForm.get('newMoney'); }
  get newDescription(): AbstractControl { return <AbstractControl>this.editGroupForm.get('newDescription'); }

  public editGroup() {
    if(!this.isEditGroupFormDataChanged()) return;

    const request: EditGroupRequest = {
      groupId: this.groupState.getGroup().groupId,
      newDescription: this.newDescription.value,
      newMoney: this.newMoney.value,
    }

    const groupBeforeEdited: Group = {...this.groupState.getGroup()};

    this.groupState.setGroup({
      ...this.currentGroup(),
      money: request.newMoney,
      description: request.newDescription,
    });

    this.groupService.editGroup(request).subscribe(res => {
      this.refreshChangesInUI();
    }, err => {
      this.groupState.setGroup(groupBeforeEdited);
    });
  }

  private isEditGroupFormDataChanged(): boolean {
    return this.newMoney.value != this.currentGroup().money ||
      this.newDescription.value != this.currentGroup().description;
  }

  private currentGroup(): Group {
    return this.groupState.getGroup();
  }

  public closeModal(): void {
    this.modalService.dismissAll();
  }

  //We force angular to update
  //TODO fix
  private refreshChangesInUI(): void {
    this.applicationRef.tick();
  }
}
