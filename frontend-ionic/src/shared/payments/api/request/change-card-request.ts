export interface ChangeCardRequest {
  paymentMethod: string,
  dob: {
    year: number,
    month: number,
    day: number
  },
}
