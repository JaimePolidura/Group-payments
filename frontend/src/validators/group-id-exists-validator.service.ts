import { Injectable } from '@angular/core';
import {AbstractControl, FormGroup, ValidationErrors, ValidatorFn} from "@angular/forms";
import {GroupService} from "../backend/groups/group.service";

@Injectable({
  providedIn: 'root'
})
export class GroupIdExistsValidatorService {

  constructor(private groupsService: GroupService) { }

  validate(onError: any): ValidatorFn {
    return (groupIdFormControl: AbstractControl): ValidationErrors | null => {
      if (!groupIdFormControl || groupIdFormControl.errors || groupIdFormControl.value == "") {
        return null;
      }

      return this.groupsService.getGroupById(groupIdFormControl.value).subscribe(
        res => groupIdFormControl.setErrors(null),
        err => groupIdFormControl.setErrors(onError)
      );
    }
  }
}
