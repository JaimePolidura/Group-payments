import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Authentication} from "../../backend/authentication/authentication";
import {GroupService} from "../../backend/groups/group.service";
import {Group} from "../../model/group";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  public userId: string;

  public currentGroup: Group;
  joinGroupForm: FormGroup;
  createGroupForm: FormGroup;

  constructor(
    public auth: Authentication,
    private groupService: GroupService,
    public modalService: NgbModal,
    private formBuilder: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.setupJoinGroupForm();
    this.userId = this.auth.getUserId();

    this.groupService.getCurrentGroup(this.auth.getUserId()).subscribe(res => {
      if(res.group != undefined) {
        this.currentGroup = res.group;
      }
    });
  }

  private setupJoinGroupForm(): void {
    // this.joinGroupForm = new FormGroup({
    //   id: new FormControl('', [Validators.required]),
    // });

    this.joinGroupForm = this.formBuilder.group({
      groupId: ['', Validators.required],
    });
  }

  public checkIfGroupToJoinExists(): void {//
    const groupId = this.joinGroupForm.get('groupId')?.value;

    this.groupService.getGroupById(groupId).subscribe(
      res => {this.joinGroupForm.setErrors(null)},
      err => {this.joinGroupForm.setErrors({groupNoExists: true})}
    );
  }

  public joinGroup(): void {
    const groupIdToJoin: string = this.joinGroupForm.get("groupId")?.value;

    this.groupService.joinGroup({groupId: groupIdToJoin}).subscribe(res => {
      this.currentGroup = res.group;
    });
  }

  public createGroup(): void {

  }

  public isAdminOfCurrentGroup(): boolean {
    return this.currentGroup != undefined && this.currentGroup.adminUserId == this.userId;
  }

  public isInGroup(): boolean {
    return this.currentGroup != undefined;
  }
}
