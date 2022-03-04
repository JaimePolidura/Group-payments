import { Component, OnInit } from '@angular/core';
import {Authentication} from "../../backend/authentication/authentication";
import {GroupService} from "../../backend/groups/group.service";
import {Group} from "../../model/group";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AbstractControl, Form, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  public userId: string;

  public currentGroup: Group;
  createGroupForm: FormGroup;
  joinGroupForm: FormGroup;

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
    console.log("Hola");

    // @ts-ignore
    const groupIdToJoin: string = document.getElementById("groupId");

    this.groupService.joinGroup({groupId: groupIdToJoin}).subscribe(res => {
      this.currentGroup = res.group;
      console.log(res);
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
