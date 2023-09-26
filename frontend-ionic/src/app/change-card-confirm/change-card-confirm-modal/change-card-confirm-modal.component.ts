import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {PaymentsApiService} from "../../../shared/payments/payments-api.service";
import {ProgressBarService} from "../../_shared/loading-progress-bar/progress-bar.service";
import {ReplaySubject} from "rxjs";
import {ChangeCardConfirmState} from "./change-card-confirm-state";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-change-card-confirm-modal',
  templateUrl: './change-card-confirm-modal.component.html',
  styleUrls: ['./change-card-confirm-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ChangeCardConfirmModalComponent implements OnInit {
  public $state: ReplaySubject<ChangeCardConfirmState> = new ReplaySubject<ChangeCardConfirmState>();

  constructor(
    private acutalRoute: ActivatedRoute,
    private paymentService: PaymentsApiService,
    private router: Router,
    public progressBar: ProgressBarService,
    private modalService: NgbModal,
  ) { }

  ngOnInit() {
    this.$state.next(ChangeCardConfirmState.LOADING);
    this.changeCard();
  }

  private changeCard(): void {
    this.progressBar.start();

    this.acutalRoute.queryParams.subscribe(params => {
      const token: string = params['token'];

      this.paymentService.confirmChangeCard({token: token}).subscribe(res => {
        this.$state.next(ChangeCardConfirmState.DONE);
        this.progressBar.stop();
      }, err => {
        this.$state.next(ChangeCardConfirmState.ERROR);
        this.progressBar.stop();
      })
    });
  }

  public gotToHomePage(): void {
    this.router.navigate(["/"]);
    this.modalService.dismissAll();
  }

}
