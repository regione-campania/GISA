import { Component, Input, OnInit } from '@angular/core';
import { Filter } from '../../../data-manager/data-manager.lib';

@Component({
  selector: 'a-deck-filter',
  templateUrl: './a-deck-filter.component.html',
  styleUrls: ['./a-deck-filter.component.scss']
})
export class ADeckFilterComponent {
  protected _filter?: Filter;

  @Input()
  get filter() {
    return this._filter;
  }

  set filter(value: Filter | undefined) {
    this._filter = value;
  }
}
