import { Component, OnInit } from '@angular/core';
import {HttpLoadingService} from "../../backend/http-loading.service";

@Component({
  selector: 'app-loading-progress-bar',
  templateUrl: './loading-progress-bar.component.html',
  styleUrls: ['./loading-progress-bar.component.css']
})
export class LoadingProgressBarComponent implements OnInit {

  constructor(
    public loaderService: HttpLoadingService,
  ){}

  ngOnInit(): void {
  }

}
