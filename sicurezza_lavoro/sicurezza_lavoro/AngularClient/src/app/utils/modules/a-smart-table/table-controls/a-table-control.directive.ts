import { Directive, ElementRef } from "@angular/core";
import { ASmartTableComponent } from "../a-smart-table.component";

@Directive()
export abstract class ATableControl {
  constructor(public aSmartTable: ASmartTableComponent, protected elementRef: ElementRef) {
    this.aSmartTable.controls.push(this);
  }
}
