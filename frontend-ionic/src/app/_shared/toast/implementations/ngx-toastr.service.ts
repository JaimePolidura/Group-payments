import { Injectable } from '@angular/core';
import {ToastService} from "../toast-service";
import {ActiveToast, ToastrService} from "ngx-toastr";
import {IndividualConfig} from "ngx-toastr/toastr/toastr-config";

@Injectable({
  providedIn: 'root'
})
export class NgxToastrService implements ToastService{
  private static TOASTR_CONFIG: Partial<IndividualConfig> = {progressBar: true};

  constructor(
    private toastService: ToastrService,
  ) {}

  error(title: string, body: string, onClick?: () => void): void {
    this.checkIfOnClick(this.toastService.error(body, title, NgxToastrService.TOASTR_CONFIG), onClick);
  }

  info(title: string, body: string, onClick?: (() => void)): void {
    this.checkIfOnClick(this.toastService.info(body, title, NgxToastrService.TOASTR_CONFIG), onClick);
  }

  success(title: string, body: string, onClick?: (() => void)): void {
    this.checkIfOnClick(this.toastService.success(body, title, NgxToastrService.TOASTR_CONFIG), onClick);
  }

  warning(title: string, body: string, onClick?: (() => void)): void {
    this.checkIfOnClick(this.toastService.warning(body, title, NgxToastrService.TOASTR_CONFIG), onClick);
  }

  private checkIfOnClick(toast: ActiveToast<any>, onClick?: () => void): void {
    if(onClick != undefined)
      toast.onTap.subscribe(() => onClick());
  }
}
