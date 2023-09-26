import {Injectable} from '@angular/core';
import {Currency} from "./model/currency";

const CURRENCIES_DATA: string = 'currencies.data';
const CURRENCIES_LAST_TIME_CALLED: string = 'currencies.lasttimecalled';
const WEEK: number = 604800000;

@Injectable({
  providedIn: 'root'
})
export class CurrenciesRepositoryCacheService {
  private currencies: Currency[];

  constructor() {
    this.currencies = [];

    this.setup();
  }

  private setup(): void{
    if(this.isCacheInvalid())
      this.clear();
    else
      this.currencies = this.findAllFromLocalStorage();
  }

  public saveAll(currencies: Currency[]): void {
    localStorage.setItem(CURRENCIES_LAST_TIME_CALLED, String(Date.now()));
    localStorage.setItem(CURRENCIES_DATA, JSON.stringify(currencies));

    this.currencies = currencies;
  }

  public findAll(): Currency[]{
    return this.currencies;
  }

  private findAllFromLocalStorage(): Currency[] {
    this.currencies = <Currency[]> JSON.parse(localStorage.getItem(CURRENCIES_DATA));

    return this.currencies;
  }

  public findByCode(currencyCode: string): Currency | undefined {
    return this.currencies[this.currencies.findIndex(currency => currency.code == currencyCode)];
  }

  private isCacheInvalid(): boolean {
    // @ts-ignore
    const timeTranscurredSinceLastCall: number = Date.now() - <number>localStorage.getItem(CURRENCIES_LAST_TIME_CALLED);

    return timeTranscurredSinceLastCall <= WEEK;
  }

  public clear(): void {
    this.currencies = [];
    localStorage.removeItem(CURRENCIES_DATA);
    localStorage.removeItem(CURRENCIES_LAST_TIME_CALLED);
  }
}
