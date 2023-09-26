import {Payment} from "../../model/paymenthistory/payment";

export interface GetPaymentsHistoryResponse {
  payments: Payment[],
}
