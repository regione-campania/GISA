import {
  Component,
  OnChanges,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { SelectionFilter } from 'src/app/utils/modules/data-manager/data-manager.lib';
import { ADeckFilterComponent } from '../a-deck-filter.component';

@Component({
  selector: 'a-deck-selection-filter',
  templateUrl: './a-deck-selection-filter.component.html',
  styleUrls: ['../a-deck-filter.component.scss'],
})
export class ADeckSelectionFilterComponent extends ADeckFilterComponent implements OnChanges
{
  @ViewChild('ms') multiselect: any;

  override get filter(): SelectionFilter {
    return this._filter as SelectionFilter;
  }

  override set filter(val: SelectionFilter) {
    this._filter = val;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (
      this.multiselect &&
      changes['filter'].previousValue !== changes['filter'].currentValue
    )
      this.multiselect.reset();
  }
}
