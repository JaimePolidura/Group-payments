import { Injectable } from '@angular/core';
import {CurrenciesRepositoryCacheService} from "./currencies-repository-cache.service";
import {CurrenciesRepositoryApiService} from "./currencies-repository-api.service";
import {Currency} from "./model/currency";

@Injectable({
  providedIn: 'root'
})
export class CurrenciesService {
  constructor(private curriciesCache: CurrenciesRepositoryCacheService,
              private curriciesApi: CurrenciesRepositoryApiService,) {
    this.setup();
  }

  private setup(): void {
    this.curriciesApi.findAll().subscribe(res => {
      this.curriciesCache.saveAll(res.currencies);
    });
  }

  public getAll(): Currency[] {
    return this.curriciesCache.findAll();
  }

  public findByCode(currencyCode: string): Currency | undefined {
    return this.curriciesCache.findByCode(currencyCode);
  }
}
