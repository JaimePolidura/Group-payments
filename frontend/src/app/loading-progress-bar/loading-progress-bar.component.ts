import { Component, OnInit } from '@angular/core';
import {ProgressBarService} from "../progress-bar.service";

@Component({
  selector: 'app-loading-progress-bar',
  templateUrl: './loading-progress-bar.component.html',
  styleUrls: ['./loading-progress-bar.component.css']
})
export class LoadingProgressBarComponent implements OnInit {

  constructor(
    public loaderService: ProgressBarService,
  ){}

  ngOnInit(): void {
  }

}
