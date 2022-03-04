import { Component } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  joinGroupForm: FormGroup = new FormGroup({
    groupId: new FormControl('', []),
  });

  constructor(
    public modalService: NgbModal,
  ){}


  checkIfGroupToJoinExists() {
    console.log(this.joinGroupForm);
  }
}
