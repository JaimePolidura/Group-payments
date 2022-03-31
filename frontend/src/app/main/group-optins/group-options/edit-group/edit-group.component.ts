import {ApplicationRef, Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {Group} from "../../../../../model/group/group";
import {GroupRepositoryService} from "../../../group-repository.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {EditGroupRequest} from "../../../../../backend/groups/request/edit-group-request";
import {GroupsApiService} from "../../../../../backend/groups/groups-api.service";

@Component({
  selector: 'app-edit-group',
  templateUrl: './edit-group.component.html',
  styleUrls: ['./edit-group.component.css']
})
export class EditGroupComponent implements OnInit {
  public editGroupForm: FormGroup;

  constructor(
    private groupState: GroupRepositoryService,
    private modalService: NgbModal,
    private groupService: GroupsApiService,
    private applicationRef: ApplicationRef,
  ) { }

  ngOnInit(): void {
    this.setUpEditGroupForm();
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
      groupId: this.groupState.getCurrentGroup().groupId,
      newDescription: this.newDescription.value,
      newMoney: this.newMoney.value,
    }

    this.groupService.editGroup(request).subscribe(res => {
      this.groupState.setCurrentGroup({
        ...this.currentGroup(),
        money: request.newMoney,
        description: request.newDescription,
      });

      this.refreshChangesInUI();
    });
  }

  private isEditGroupFormDataChanged(): boolean {
    return this.newMoney.value != this.currentGroup().money ||
      this.newDescription.value != this.currentGroup().description;
  }

  private currentGroup(): Group {
    return this.groupState.getCurrentGroup();
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
