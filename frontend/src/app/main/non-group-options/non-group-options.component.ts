import { Component, OnInit } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Authentication} from "../../../backend/users/authentication/authentication";
import {Router} from "@angular/router";
import {MakeTransferModalComponent} from "./make-transfer-modal/make-transfer-modal.component";
import {JoinGroupModalComponent} from "./join-group-modal/join-group-modal.component";
import {CreateGroupModalComponent} from "./create-group-modal/create-group-modal.component";

@Component({
  selector: 'app-non-group-options',
  templateUrl: './non-group-options.component.html',
  styleUrls: ['./non-group-options.component.css']
})
export class NonGroupOptionsComponent implements OnInit {
  constructor(
    public modalService: NgbModal,
    public auth: Authentication,
    private router: Router,
  ){}

  ngOnInit(): void {
  }

  public logout(): void {
    this.auth.logout(() => {
      this.router.navigateByUrl("/");
    });
  }

  private closeModal(): void {
    this.modalService.dismissAll();
  }

  public openMakeTransferModal(): void {
    this.modalService.open(MakeTransferModalComponent);
  }

  public openCreateGroupModal(): void {
    this.modalService.open(CreateGroupModalComponent);
  }

  public goToPaymentHistory() {
    this.router.navigateByUrl("paymenthistory");
  }

  public openJoinGroupModal(): void {
    this.modalService.open(JoinGroupModalComponent);
  }
}
