import { Component, OnInit } from '@angular/core';
import {Authentication} from "../../backend/authentication/authentication";
import {GroupService} from "../../backend/groups/group.service";
import {Group} from "../../model/group";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AbstractControl, Form, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CreateGroupRequest} from "../../backend/groups/request/create-group-request";
import {User} from "../../model/user";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  public userId: string;

  public currentGroup: Group;
  public currentGroupMembers: User[];

  createGroupForm: FormGroup;
  joinGroupForm: FormGroup;

  constructor(
    public auth: Authentication,
    private groupService: GroupService,
    public modalService: NgbModal,
  ) { }

  ngOnInit(): void {
    this.setupJoinGroupForm();
    this.setUpCreateGroupForm();

    this.userId = this.auth.getUserId();

    this.groupService.getCurrentGroup(this.auth.getUserId()).subscribe(res => {
      if(res.group != undefined) {
        this.currentGroup = res.group;

        this.groupService.getGroupMembersByGroupId(res.group.groupId).subscribe(res =>  {
          this.currentGroupMembers = res.members;
        });
      }
    });
  }

  private setupJoinGroupForm(): void {
    this.joinGroupForm = new FormGroup({
      groupId: new FormControl('', [Validators.required]),
    });
  }
  get groupId(): AbstractControl | null { return this.joinGroupForm.get('groupId'); }

  public checkIfGroupToJoinExists(): void {
    const groupId = this.groupId?.value;
    console.log(groupId);

    this.groupService.getGroupById(groupId).subscribe(
      res => {this.joinGroupForm.setErrors(null)},
      err => {this.joinGroupForm.setErrors({groupNoExists: true})}
    );
  }

  public joinGroup(): void {
    const groupIdToJoin = this.groupId?.value;

    this.groupService.joinGroup({groupId: groupIdToJoin}).subscribe(res => {
      this.currentGroup = res.group;

      this.groupService.getGroupMembersByGroupId(res.group.groupId).subscribe(res => {
        this.currentGroupMembers = res.members;
      })
    });
  }

  private setUpCreateGroupForm(): void {
    this.createGroupForm = new FormGroup({
      money: new FormControl('', [Validators.required, Validators.min(1)]),
      title: new FormControl('GroupPayment', [Validators.required, Validators.maxLength(15)])
    });
  }
  get money(): AbstractControl | null { return this.createGroupForm.get('money'); }
  get title(): AbstractControl | null { return this.createGroupForm.get('title'); }

  public createGroup(): void {
    const createGroupRequest: CreateGroupRequest = {
      money: this.money?.value,
      title: this.title?.value,
    }

    this.groupService.createGroup(createGroupRequest).subscribe(res => {
      this.currentGroup = res.group;
    });
  }

  public isAdminOfCurrentGroup(): boolean {
    return this.currentGroup != undefined && this.currentGroup.adminUserId == this.userId;
  }

  public isInGroup(): boolean {
    return this.currentGroup != undefined;
  }

  public copyToClipboard(toCopy: any) {
    navigator.clipboard.writeText(toCopy);
  }

  public leaveGroup() {
    this.groupService.leaveGroup({groupId: this.currentGroup?.groupId, ignoreThis: ""}).subscribe(res => {
      this.currentGroupMembers = [];
      // @ts-ignore
      this.currentGroup = undefined;
    });
  }
}
