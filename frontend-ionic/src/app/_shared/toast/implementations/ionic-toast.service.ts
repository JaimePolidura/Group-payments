import { Injectable } from '@angular/core';
import {ToastController, ToastOptions} from '@ionic/angular';
import {ToastService} from "../toast-service";

@Injectable({
  providedIn: 'root'
})
export class IonicToastService implements ToastService{
  private static TOAST_CONFIG: ToastOptions = {
    duration: 7000,
    animated: true,
    position: 'top',
  };

  constructor(private ionicToastController: ToastController) { }

  async error(title: string, body: string, onClick: (() => void) | undefined) {
    await this.openToast(title, body, 'danger', onClick);
  }

  async info(title: string, body: string, onClick: (() => void) | undefined) {
    await this.openToast(title, body, 'secondary', onClick);
  }

  async success(title: string, body: string, onClick: (() => void) | undefined) {
    await this.openToast(title, body, 'success', onClick);
  }

  async warning(title: string, body: string, onClick: (() => void) | undefined) {
    await this.openToast(title, body, 'warning', onClick);
  }

  private async openToast(title: string, body: string, color: ToastColor, onCLick?: () => void) {
    //TODO Fix color, not working
    const toast = await this.ionicToastController.create({
      ...IonicToastService.TOAST_CONFIG,
      header: title,
      message: body,
    });

    toast.onclick = e => {
      if(onCLick != undefined)
        onCLick();

      toast.classList.add('hidden'); //TODO add annimation
    };

    await toast.present();
  }
}

export type ToastColor = 'primary' | 'secondary' | 'tertiary' | 'success' | 'warning' | 'danger' | 'light' | 'medium' | 'dark';
