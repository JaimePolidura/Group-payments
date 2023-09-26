import { Injectable } from '@angular/core';
import {Invitation} from "./model/invitation";

@Injectable({
  providedIn: 'root'
})
export class InvitationsCacheService {
  private readonly invitations: Invitation[];

  constructor() {
    this.invitations = [];
  }

  public add(invitation: Invitation): void {
    this.invitations.push(invitation);
  }

  public addAll(invitations: Invitation[]): void {
    invitations.forEach(invitation => {
      if(!this.isContainted(invitation)){
        this.invitations.push(...invitations);
      }
    })
  }

  private isContainted(invitation: Invitation): boolean {
    return this.invitations.filter(ite => ite.invitationId == invitation.invitationId).length > 0;
  }

  public deleteById(invitationId: string): void {
    const indexToRemove = this.invitations.findIndex(invitation => invitation.invitationId == invitationId);

    this.invitations.splice(indexToRemove, 1);
  }

  public getAll(): Invitation[] {
    return this.invitations;
  }

  public isEmpty(): boolean {
    return this.size() == 0;
  }

  public clear(): void {
    this.invitations.splice(0, this.invitations.length);
  }

  public size(): number {
    return this.invitations.length;
  }
}
