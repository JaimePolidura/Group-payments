export interface GetPaymentHistoryRequest {
  pageNumber: number,
  itemsPerPage: number,
  paymentTypeSearch: 'MEMBER_TO_APP' | 'APP_TO_ADMIN' | 'ALL';
}

