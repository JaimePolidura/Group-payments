import { Injectable } from '@angular/core';
import {FormGroup} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class GroupIdExistsService {

  constructor() { }

  groupIdExists(groupId: string, idFormControl: string, component) {
    return (formGroup: FormGroup) => {
      const tickerControl = formGroup.controls[idFormControl];

      if (!tickerControl || tickerControl.errors || groupId == "") {
        return null;
      }

      return this.pixelcoinsApiUtils.getStockPrice(tickerControl.value).subscribe(
        res => {
          if(res.ticker != component.ticker.value){
            return;
          }

          this.pixelcoinsApiUtils.formBuyStockPrice = res.price;
          tickerControl.setErrors(null)
        },
        err => {
          if(err.error.ticker != component.ticker.value){
            return;
          }

          this.pixelcoinsApiUtils.formBuyStockPrice = undefined;
          tickerControl.setErrors({stockNotFound: true})
        }
      );
    }
  }
}
