import {Component, Input, OnInit} from '@angular/core';
import {BackendUsingRoutesService} from "../../../shared/_shared/backend-using-routes.service";
import {Observable, of} from "rxjs";
import {ImageUserRepositoryApiService} from "../../../shared/usersimages/image-user-repository-api.service";

@Component({
  selector: 'app-user-image',
  templateUrl: './user-image.component.html',
  styleUrls: ['./user-image.component.css'],
})
export class UserImageComponent implements OnInit {
  private readonly ALT_IMAGE_ON_ERROR_LOADING_USER_IMAGE = 'assets/img/user.svg'
  private alreadyFetchedAltImage: boolean;

  private lastBlobPath: string;
  public stylesToApply: any;

  @Input() public userImageId: number | undefined;
  @Input() public height: string;
  @Input() public width: string;
  @Input() public otherStyles: any;

  constructor(private backendRoutes: BackendUsingRoutesService, private images: ImageUserRepositoryApiService) {
    this.alreadyFetchedAltImage = false;
  }

  ngOnInit(): void {
    this.stylesToApply = {
      height: this.height,
      width: this.width,
      ...this.otherStyles,
    };
  }

  public getImageUserLink(): Observable<string> {
    if(this.lastBlobPath != undefined) return of(this.lastBlobPath);
    if(this.alreadyFetchedAltImage) return undefined;

    return of(`${this.backendRoutes.USING}/usersimage/get/${this.userImageId}`);
  }

  public handleMissingImage(event: any): void {
    console.log(event);

    if(!this.alreadyFetchedAltImage){
      event.target.src = this.ALT_IMAGE_ON_ERROR_LOADING_USER_IMAGE;
      this.alreadyFetchedAltImage = true;
    }
  }
}
