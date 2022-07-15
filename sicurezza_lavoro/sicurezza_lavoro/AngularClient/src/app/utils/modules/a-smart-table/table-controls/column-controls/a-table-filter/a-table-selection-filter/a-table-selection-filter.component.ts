import { Component, Input, ViewChild } from '@angular/core';
import { ATableFilterComponent } from '../a-table-filter.component';

@Component({
  selector: 'a-table-selection-filter',
  templateUrl: './a-table-selection-filter.component.html',
  styleUrls: ['../a-table-filter.component.scss'],
})
export class ATableSelectionFilterComponent {
  @ViewChild('ms') multiSelect: any;
  @Input() values?: any; //used when this instanceof ATableSelectionFilter
  valuesToFind: any | any[];

  condition = (value: any): boolean => {
    if (!this.valuesToFind || this.valuesToFind.length === 0) return true;
    if (value == null) return false;
    console.log(this.valuesToFind.map((v: any) => v.toLowerCase()).includes(value.toLowerCase()));
    return this.valuesToFind.map((v: any) => v.toLowerCase()).includes(value.toLowerCase());
  };

  clean() {
    this.valuesToFind = [];
    this.multiSelect.reset();
  }
}
