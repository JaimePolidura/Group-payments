import { Component, OnInit } from '@angular/core';
import {GroupCacheService} from "../../../../shared/groups/group-cache.service";
import {Group} from "../../../../shared/groups/model/group";
import {AuthenticationCacheService} from "../../../../shared/auth/authentication-cache.service";
import {CommissionService} from "../../../../shared/payments/commission.service";

@Component({
  selector: 'app-group-description',
  templateUrl: './group-description.component.html',
  styleUrls: ['./group-description.component.css']
})
export class GroupDescriptionComponent implements OnInit {

  constructor(
    public groupState: GroupCacheService,
    public auth: AuthenticationCacheService,
    private commissionService: CommissionService,
  ) { }

  ngOnInit(): void {
  }

  public currentGroup(): Group {
    return this.groupState.getGroup();
  }

  public deductFromFee(money: number): number {
    return this.commissionService.deductFromFee(money);
  }

}
