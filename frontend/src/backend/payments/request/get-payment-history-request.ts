import {PaymentTypeSearch} from "../../../app/main/non-group-options/payments-history/payment-type-search";

export interface GetPaymentHistoryRequest {
  pageNumber: number,
  itemsPerPage: number,
  paymentTypeSearch: PaymentTypeSearch;
}

