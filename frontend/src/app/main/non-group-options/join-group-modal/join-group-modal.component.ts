import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {GroupsApiService} from "../../../../backend/groups/groups-api.service";
import {Authentication} from "../../../../backend/users/authentication/authentication";
import {GroupState} from "../../../../model/group/group-state";
import {NgbModalStack} from "@ng-bootstrap/ng-bootstrap/modal/modal-stack";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupRepositoryService} from "../../group-repository.service";

@Component({
  selector: 'app-join-group-modal',
  templateUrl: './join-group-modal.component.html',
  styleUrls: ['./join-group-modal.component.css']
})
export class JoinGroupModalComponent implements OnInit {
  joinGroupForm: FormGroup;

  constructor(
    private groupService: GroupsApiService,
    private auth: Authentication,
    private groupState: GroupRepositoryService,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.setupJoinGroupForm();
  }

  private setupJoinGroupForm(): void {
    this.joinGroupForm = new FormGroup({
      groupId: new FormControl('', [Validators.required]),
    });
  }
  get groupId(): AbstractControl | null { return this.joinGroupForm.get('groupId'); }

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

  public closeModal(): void {
    this.modalService.dismissAll();
  }
}
