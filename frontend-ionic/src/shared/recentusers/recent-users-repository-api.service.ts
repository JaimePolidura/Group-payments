import { Injectable } from '@angular/core';
import {RecentUser} from "./model/recent-user";
import {RecentUsersService} from "./recent-users.service";

@Injectable({
  providedIn: 'root'
})
export class RecentUsersRepositoryApiService {
  private static DATA = 'recentusers.data';

  constructor(){
    this.setup();
  }

  private setup(): void {
     if(localStorage.getItem(RecentUsersRepositoryApiService.DATA) == null)
       localStorage.setItem(RecentUsersRepositoryApiService.DATA, JSON.stringify([]));
     else
       this.removeAllOldSavedUsers();
  }

  private removeAllOldSavedUsers(): void {
    let allSavedRecentUsers = this.findAll();

    for(let i = 0; i < allSavedRecentUsers.length; i++){
      const lastTimeSaved: number = <number> allSavedRecentUsers[i].lastTimeSaved;
      const timeTranscurredSinceSaved: number = Date.now() - lastTimeSaved;

      if(timeTranscurredSinceSaved > RecentUsersService.LIMIT_SAVE_SINCE){
        allSavedRecentUsers.splice(i, 1);
        i--;
      }
    }

    localStorage.setItem(RecentUsersRepositoryApiService.DATA, JSON.stringify(allSavedRecentUsers));
  }

  public clear(): void {
    localStorage.removeItem(RecentUsersRepositoryApiService.DATA);
  }

  public findAll(): RecentUser[] {
    return <RecentUser[]> JSON.parse(<string> localStorage.getItem(RecentUsersRepositoryApiService.DATA));
  }

  public saveAll(recentUsers: RecentUser[]): void {
    localStorage.setItem(RecentUsersRepositoryApiService.DATA, JSON.stringify(recentUsers));
  }

  public save(recentUser: RecentUser): void{
    const recentUsersWithNewUser: RecentUser[] = [...this.findAll(), recentUser];

    localStorage.setItem(RecentUsersRepositoryApiService.DATA, JSON.stringify(recentUsersWithNewUser));
  }

  public deleteById(userId: string): void {
    const allUsersSaved = this.findAll();
    const indexOfUserToDelete = allUsersSaved.findIndex(user => user.userId == userId);

    if(indexOfUserToDelete != -1){
      allUsersSaved.splice(indexOfUserToDelete, 1);
      localStorage.setItem(RecentUsersRepositoryApiService.DATA, JSON.stringify(allUsersSaved));
    }

  }
}
