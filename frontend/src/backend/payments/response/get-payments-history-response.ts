import {Payment} from "../../../model/payments/paymenthistory/payment";

export interface GetPaymentsHistoryResponse {
  payments: Payment[],
}
