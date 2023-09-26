import {Component, OnInit} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {
  DeleteAccountConfirmModalComponent
} from "./delete-account-confirm-modal/delete-account-confirm-modal.component";
import {ActivatedRoute, Router} from "@angular/router";
import {UsersApiService} from "../../shared/users/users-api.service";

@Component({
  selector: 'app-delete-account-confirm',
  templateUrl: './delete-account-confirm.component.html',
  styleUrls: ['./delete-account-confirm.component.css']
})
export class DeleteAccountConfirmComponent implements OnInit {
  constructor(private modalService: NgbModal,
              private router: Router,
              private actualRoute: ActivatedRoute,
              private usersService: UsersApiService,
  ) {
  }

  ngOnInit(): void {
    const modal =  this.modalService.open(DeleteAccountConfirmModalComponent);

    modal.result.then(() => this.redirectToMain(), () => this.redirectToMain());
  }

  private redirectToMain(): void {
    this.router.navigateByUrl("login");
  }
}
