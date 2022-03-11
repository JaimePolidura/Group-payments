import { Component, OnInit } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Authentication} from "../../../backend/authentication/authentication";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {GroupsApiService} from "../../../backend/groups/groups-api.service";
import {CreateGroupRequest} from "../../../backend/groups/request/create-group-request";
import {GroupStateService} from "../group-state.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-non-group-options',
  templateUrl: './non-group-options.component.html',
  styleUrls: ['./non-group-options.component.css']
})
export class NonGroupOptionsComponent implements OnInit {
  createGroupForm: FormGroup;
  joinGroupForm: FormGroup;

  constructor(
    public modalService: NgbModal,
    public auth: Authentication,
    private groupService: GroupsApiService,
    private groupState: GroupStateService,
    private router: Router,
  ){}

  ngOnInit(): void {
    this.setupJoinGroupForm();
    this.setUpCreateGroupForm();
  }

  private setupJoinGroupForm(): void {
    this.joinGroupForm = new FormGroup({
      groupId: new FormControl('', [Validators.required]),
    });
  }
  get groupId(): AbstractControl | null { return this.joinGroupForm.get('groupId'); }

  private setUpCreateGroupForm(): void {
    this.createGroupForm = new FormGroup({
      money: new FormControl('', [Validators.required, Validators.min(1)]),
      title: new FormControl('GroupPayment', [Validators.required, Validators.maxLength(15)])
    });
  }
  get money(): AbstractControl | null { return this.createGroupForm.get('money'); }
  get title(): AbstractControl | null { return this.createGroupForm.get('title'); }

  public checkIfGroupToJoinExists(): void {
    const groupJoinShareLink = this.groupId?.value;

    this.groupService.getGroupById(this.getGroupIdToJoinFromShareLink(groupJoinShareLink)).subscribe(
      res => {
        this.joinGroup();
        this.joinGroupForm.setErrors(null)
      },
      err => {this.joinGroupForm.setErrors({groupNoExists: true})}
    );
  }

  public joinGroup(): void {
    const groupToJoinLink: string = this.groupId?.value;
    const groupIdToJoin = this.getGroupIdToJoinFromShareLink(groupToJoinLink);

    this.closeModal();

    this.groupService.joinGroup({groupId: groupIdToJoin, ignoreThis: ''}).subscribe(res => {
      this.groupState.setCurrentGroup(res.group);

      this.groupService.getGroupMembersByGroupId(res.group.groupId).subscribe(res => {
        this.groupState.setCurrentGroupMembers(res.members);
      });
    });
  }

  private getGroupIdToJoinFromShareLink(shareLink: string): string {
    return shareLink.split("/")[shareLink.split("/").length - 1];
  }

  public createGroup(): void {
    this.closeModal();

    const createGroupRequest: CreateGroupRequest = {
      money: this.money?.value,
      title: this.title?.value,
    }

    this.groupService.createGroup(createGroupRequest).subscribe(res => {
      this.groupState.setCurrentGroup(res.group);

      this.groupService.getGroupMembersByGroupId(res.group.groupId).subscribe(res => {
        this.groupState.setCurrentGroupMembers(res.members);
      });
    });
  }

  public logout(): void {
    this.auth.logout(() => {
      this.router.navigateByUrl("/");
    });
  }

  private closeModal(): void {
    this.modalService.dismissAll();
  }
}
