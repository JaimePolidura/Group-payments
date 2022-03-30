import { Component, OnInit } from '@angular/core';
import {CreateGroupRequest} from "../../../../backend/groups/request/create-group-request";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Authentication} from "../../../../backend/users/authentication/authentication";
import {GroupsApiService} from "../../../../backend/groups/groups-api.service";
import {GroupRepositoryService} from "../../group-repository.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-group-modal',
  templateUrl: './create-group-modal.component.html',
  styleUrls: ['./create-group-modal.component.css']
})
export class CreateGroupModalComponent implements OnInit {
  createGroupForm: FormGroup;

  constructor(
    public modalService: NgbModal,
    public auth: Authentication,
    private groupService: GroupsApiService,
    private groupState: GroupRepositoryService,
    private router: Router,
  ){}

  ngOnInit(): void {
    this.setUpCreateGroupForm();
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

  public closeModal(): void {
    this.modalService.dismissAll();
  }

}
