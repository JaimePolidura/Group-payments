import { Component, OnInit } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-user-edited-modal',
  templateUrl: './user-edited-modal.component.html',
  styleUrls: ['./user-edited-modal.component.css']
})
export class UserEditedModalComponent implements OnInit {

  constructor(
    public modalServive: NgbModal,
  ) { }

  ngOnInit(): void {
  }


  public closeModal(): void {
    this.modalServive.dismissAll();
  }
}
