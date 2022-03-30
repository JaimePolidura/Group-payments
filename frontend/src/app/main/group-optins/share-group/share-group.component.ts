import {Component, Input, OnInit} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-share-group',
  templateUrl: './share-group.component.html',
  styleUrls: ['./share-group.component.css']
})
export class ShareGroupComponent implements OnInit {
  @Input() public linkGroup: string;

  constructor(
    public modalService: NgbModal,
  ) { }

  ngOnInit(): void {
  }

  public copyToClipboard(toCopy: any) {
    navigator.clipboard.writeText(toCopy);
    this.closeModal();
  }

  public closeModal(): void {
    this.modalService.dismissAll();
  }
}
