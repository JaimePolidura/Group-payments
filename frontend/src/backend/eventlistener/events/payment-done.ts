export interface PaymentDone {
  groupId: string,
  membersUsersId: string[],
  adminUserId: string,
  description: string,
  moneyPaidPerMember: number,
}
