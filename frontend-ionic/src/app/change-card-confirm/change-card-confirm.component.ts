import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {PaymentsApiService} from "../../shared/payments/payments-api.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {
  DeleteAccountConfirmModalComponent
} from "../delete-account-confirm/delete-account-confirm-modal/delete-account-confirm-modal.component";
import {ChangeCardConfirmModalComponent} from "./change-card-confirm-modal/change-card-confirm-modal.component";

@Component({
  selector: 'app-change-card-confirm',
  templateUrl: './change-card-confirm.component.html',
  styleUrls: ['./change-card-confirm.component.scss'],
})
export class ChangeCardConfirmComponent implements OnInit {

  constructor(
    private router: Router,
    private modalService: NgbModal,
    ) { }

  ngOnInit() {
    this.openChangeCardConfirmModal();
  }

  private openChangeCardConfirmModal(): void {
    const modal = this.modalService.open(ChangeCardConfirmModalComponent);

    modal.result.then(() => this.redirectToMain(), () => this.redirectToMain());
  }

  private redirectToMain(): void {
    this.router.navigateByUrl("login");
  }

}
