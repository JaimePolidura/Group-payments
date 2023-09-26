import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Payment} from "../../../../../shared/payments/model/paymenthistory/payment";
import {UsersApiService} from "../../../../../shared/users/users-api.service";
import {AuthenticationCacheService} from "../../../../../shared/auth/authentication-cache.service";
import {RecentUser} from "../../../../../shared/recentusers/model/recent-user";
import {RecentUsersService} from "../../../../../shared/recentusers/recent-users.service";

@Component({
  selector: 'app-payment-info-modal',
  templateUrl: './payment-info-modal.component.html',
  styleUrls: ['./payment-info-modal.component.css']
})
export class PaymentInfoModalComponent implements OnInit {
  @Input() payment: Payment;
  public username: string;
  public email: string;

  constructor(
    public modalService: NgbModal,
    public activeModal: NgbActiveModal,
    public auth: AuthenticationCacheService,
    private userService: UsersApiService,
    private recentUsers: RecentUsersService,
  ){ }

  ngOnInit(): void {
    this.getUserData(this.isTo() ? this.payment.fromUserId : this.payment.toUserId);
  }

  public roundNumber(number: number): number {
    return Math.round(number * 100) / 100;
  }

  public isFrom(): boolean {
    return this.payment.fromUserId == this.auth.getUserId();
  }

  public isTo(): boolean {
    return this.payment.toUserId == this.auth.getUserId();
  }

  private getUserData(userId: string): void {
    const cachedUser: RecentUser | undefined = this.recentUsers.findById(userId);
    const userInCache = cachedUser != undefined;

    if(userInCache){
      this.username = cachedUser.username;
      this.email = cachedUser.email;

      return;
    }

    this.userService.getUserDataByUserIdId(userId).subscribe(res => {
      this.username = res.username;
      this.email = res.email;
    });
  }
}
