import { Injectable } from '@angular/core';
import {RecentUser} from "./model/recent-user";
import {RecentUsersRepositoryCacheService} from "./recent-users-repository-cache.service";
import {RecentUsersRepositoryApiService} from "./recent-users-repository-api.service";
import {AuthenticationCacheService} from "../auth/authentication-cache.service";

@Injectable({
  providedIn: 'root'
})
export class RecentUsersService {
  public static readonly MAX_USERS: number = 2;
  public static readonly LIMIT_SAVE_SINCE = 604800000; //Week

  constructor(private cache: RecentUsersRepositoryCacheService,
              private api: RecentUsersRepositoryApiService,
              private auth: AuthenticationCacheService)
  {
    this.setup();
  }

  private setup(): void {
    this.cache.saveAll(this.api.findAll());
  }

  public saveAll(users: RecentUser[]): void{
    users.forEach(user => {
      this.save(user);
    })
  }

  public save(user: RecentUser): void {
    if(this.alreadySaved(user)) return;
    if(this.auth.getUserId() === user.userId) return;
    if(this.cache.findAll().length >= RecentUsersService.MAX_USERS) this.clear();

    const userToSave = {...user, lastTimeSaved: Date.now()};

    this.cache.save(userToSave);
    this.api.save(userToSave);
  }

  private alreadySaved(user: RecentUser): boolean {
    return this.cache.findById(user.userId) !== undefined;
  }

  public findAll(): RecentUser[]{
    return this.cache.findAll();
  }

  public findByEmail(email: string): RecentUser | undefined{
    return this.cache.findByEmail(email);
  }

  public findById(id: string): RecentUser | undefined{
    return this.cache.findById(id);
  }

  public deleteById(userIdToDelete: string): RecentUser | undefined {
    const index: number =  this.cache.findAll().findIndex(user => user.userId == userIdToDelete);
    const userToDelete = this.findById(userIdToDelete);

    if(index != -1){
      this.cache.deleteById(userIdToDelete);
      this.api.deleteById(userIdToDelete);
    }

    return userToDelete;
  }

  private clear(): void {
    this.cache.clear();
    this.api.clear();
  }
}
