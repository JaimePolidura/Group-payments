import { Injectable } from '@angular/core';
import {RecentUsersRepositoryApiService} from "./recent-users-repository-api.service";
import {RecentUser} from "./model/recent-user";
import {AuthenticationCacheService} from "../auth/authentication-cache.service";

@Injectable({
  providedIn: 'root'
})
export class RecentUsersRepositoryCacheService {
  private recentUsers: RecentUser[];

  constructor() {
    this.recentUsers = [];
  }

  public saveAll(users: RecentUser[]): void {
    users.forEach(user => {
      this.save(user);
    });
  }

  public save(user: RecentUser): void {
    this.recentUsers.push(user);
  }

  public clear(): void {
    this.recentUsers.splice(0, this.recentUsers.length);
  }

  public findAll(): RecentUser[] {
    return this.recentUsers;
  }

  public findByEmail(email: string): RecentUser | undefined{
    const found: RecentUser[] = this.recentUsers.filter(recentUser => recentUser.email == email)

    return found.length == 0 ? undefined : found[0];
  }

  public findById(userId: string): RecentUser | undefined {
    const found: RecentUser[] = this.recentUsers.filter(recentUser => recentUser.userId == userId)

    return found.length == 0 ? undefined : found[0];
  }

  public deleteById(userId: string): void {
    const index: number =  this.recentUsers.findIndex(user => user.userId == userId);

    if(index != -1)
      this.recentUsers.splice(index, 1);
  }
}
