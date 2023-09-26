export interface RegisterWithStripeRequest {
  paymentMethod: string,
  dob: {
    year: number,
    month: number,
    day: number
  },
}
