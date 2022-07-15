import { Component, OnInit } from '@angular/core';
import { DateFilter } from 'src/app/utils/modules/data-manager/controls/filters/date-filter';
import { ADeckFilterComponent } from '../a-deck-filter.component';

@Component({
  selector: 'a-deck-date-filter',
  templateUrl: './a-deck-date-filter.component.html',
  styleUrls: ['../a-deck-filter.component.scss'],
})
export class ADeckDateFilterComponent extends ADeckFilterComponent {
  override get filter(): DateFilter {
    return this._filter as DateFilter;
  }

  override set filter(val: DateFilter) {
    this._filter = val;
  }

  getOptionLabel(opt: string) {
    switch (opt) {
      case 'from-date':
        return 'da';
      case 'till-date':
        return 'fino a';
      case 'range':
        return 'intervallo';
      default:
        return 'data esatta';
    }
  }

  formatDate(s: string) {
    let date = new Date(s);
    return date.toString() === 'Invalid Date' ? new Date() : date;
  }

}
