import { Injectable } from "@angular/core";
import { NgxSpinnerService } from "ngx-spinner";

@Injectable({
  providedIn: "root",
})
export class SpinnerService {
  constructor(private _spinner: NgxSpinnerService) {}

  show() {
    this._spinner.show();
  }

  hide() {
    this._spinner.hide();
  }
}
