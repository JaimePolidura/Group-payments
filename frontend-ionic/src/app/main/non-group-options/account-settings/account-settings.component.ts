import {ChangeDetectionStrategy, Component, OnInit, ViewChild} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationCacheService} from "../../../../shared/auth/authentication-cache.service";
import {Currency} from "../../../../shared/currencies/model/currency";
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {UsersApiService} from "../../../../shared/users/users-api.service";
import {EditUserRequest} from "../../../../shared/users/api/request/edit-user-request";
import {UserEditedModalComponent} from "./user-edited-modal/user-edited-modal.component";
import {DeleteAccountEmailSendedComponent} from "./delete-account-email-sended/delete-account-email-sended.component";
import {CurrenciesService} from "../../../../shared/currencies/currencies.service";
import {ToastService} from "../../../_shared/toast/toast-service";
import {ProgressBarService} from "../../../_shared/loading-progress-bar/progress-bar.service";
import {ChangeCardComponent} from "./change-card/change-card.component";

@Component({
  selector: 'app-account-settings',
  templateUrl: './account-settings.component.html',
  styleUrls: ['./account-settings.component.css'],
})
export class AccountSettingsComponent implements OnInit {
  public allCurrcies: Currency[];

  editAccountForm: FormGroup;
  actualCurrency: Currency

  constructor(
    public authCache: AuthenticationCacheService,
    public currencies: CurrenciesService,
    private router: Router,
    public modalService: NgbModal,
    private usersService: UsersApiService,
    private toastService: ToastService,
    private progressBar: ProgressBarService
  ) {}

  ngOnInit(): void {
    this.setupEditAccountForm();
    this.allCurrcies = this.currencies.getAll();

    this.actualCurrency = this.currencies.findByCode(this.authCache.getCurrency().code);
  }

  public getUsingCurrencyCountries(currencies: Currency[]): string[]{
    if(currencies == undefined) return [];

    const usingCurrency = currencies.filter(currency => this.authCache.getCurrency().code == currency.code)[0];

    return usingCurrency == undefined ? [] : usingCurrency.usingCountries;
  }

  private setupEditAccountForm(): void {
    this.editAccountForm = new FormGroup({
      username: new FormControl(this.authCache.getUsername(), [Validators.required, Validators.maxLength(16), Validators.minLength(3)]),
      currency: new FormControl(this.authCache.getCurrency().code, []),
      country: new FormControl(this.authCache.getCountryCode().toUpperCase(), [])
    });
  }
  get username(): AbstractControl {return <AbstractControl> this.editAccountForm.get('username');}
  get currency(): AbstractControl {return <AbstractControl> this.editAccountForm.get('currency');}
  get country(): AbstractControl {return <AbstractControl> this.editAccountForm.get('country');}

  public editAccount(): void {
    if(this.nothingHasChanged()){
      this.cancel();
      return;
    }

    // @ts-ignore
    const newCountryCode: string = document.getElementById('country-select').value;

    const req: EditUserRequest = {
      username: this.username.value,
      currency: this.currency.value,
      countryCode: newCountryCode,
    }

    const oldUserDataInCache = this.authCache.getData();

    this.authCache.setData({
      ...oldUserDataInCache,
      user: {
        ...oldUserDataInCache.user,
        username: this.username.value,
        currency: <Currency>this.currencies.findByCode(this.currency.value),
        country: newCountryCode
      }
    });

    this.modalService.open(UserEditedModalComponent);

    this.usersService.editUser(req).subscribe(res => {
      this.router.navigateByUrl("main");
    }, err => {
      this.authCache.setData(oldUserDataInCache);
    });
  }

  public nothingHasChanged(): boolean {
    return this.username.value == this.authCache.getUsername() && this.currency.value == this.authCache.getCurrency().code
      && this.country.value == this.authCache.getCountryCode();
  }

  public onCurrencyChanged(event: any) {
    const currencyCode: string = event.target.value;

    this.actualCurrency = <Currency>this.currencies.findByCode(currencyCode);
  }

  public cancel(): void {
    this.editAccountForm.reset();
    this.router.navigateByUrl('main');
  }

  public deleteAccount() {
    this.sendEmail();

    const deleteAccountModal = this.modalService.open(DeleteAccountEmailSendedComponent);
    deleteAccountModal.componentInstance.emailResend.subscribe(() => {
      this.sendEmail();
    });
  }

  private sendEmail(): void {
    this.usersService.prepareDeleteAccount().subscribe(res => {});
  }

  public getUserPhotoUrl(): number {
    return this.authCache.getUserImageId();
  }

  public onNewUserPhotoSelected($event: Event) {
     // @ts-ignore
    const fileSelected = <File>event.target.files[0];

    if(fileSelected == undefined){
      this.toastService.error('Select a file', ``);
      return;
    }
    if(!this.correctFormatFile(fileSelected)){
      this.toastService.error('Incorrect format image file', `${fileSelected.type} not allowed, only jpeg and png`);
      return;
    }

    this.progressBar.start();

    this.usersService.changeUserImage(fileSelected).subscribe(res => {
      this.onImageChanged(res.imageId);
    }, err => {
      if(err.status == 409)
        this.onImageChanged(this.authCache.getUserImageId());
      else
        this.toastService.error('Error whiel save image', `${err}`);

      this.progressBar.stop();
    });
  }

  private correctFormatFile(file: File): boolean{
    return file.type == 'image/png' || file.type == 'image/jpeg'
  }

  private onImageChanged(newUserImageId: number) :void {
    this.toastService.success('Image changed', '');
    this.authCache.setUserImageId(newUserImageId);
    this.progressBar.stop();
  }

  public changeCard(): void {
    this.modalService.open(ChangeCardComponent);
  }
}
