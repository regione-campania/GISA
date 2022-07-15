import { Component, Input, OnInit } from '@angular/core';
import { TextFilter } from 'src/app/utils/modules/data-manager/data-manager.lib';
import { TextFilterOption } from 'src/app/utils/modules/data-manager/data-manager.types';
import { ADeckFilterComponent } from '../a-deck-filter.component';

@Component({
  selector: 'a-deck-text-filter',
  templateUrl: './a-deck-text-filter.component.html',
  styleUrls: ['../a-deck-filter.component.scss'],
})
export class ADeckTextFilterComponent extends ADeckFilterComponent {
  override get filter(): TextFilter {
    return this._filter as TextFilter;
  }

  override set filter(val: TextFilter) {
    this._filter = val;
  }

  getOptionLabel(opt: string) {
    switch (opt) {
      case 'not-contains':
        return 'non contiene';
      case 'starts-with':
        return 'inizia con';
      case 'ends-with':
        return 'termina con';
      default:
        return 'contiene';
    }
  }
}
