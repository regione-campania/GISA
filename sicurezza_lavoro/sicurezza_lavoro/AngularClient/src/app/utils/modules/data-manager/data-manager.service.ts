import { EventEmitter } from '@angular/core';
import { DateFilter } from './controls/filters/date-filter';
import {
  Filter,
  DataPaginator,
  TextFilter,
  reflexMapping,
  SelectionFilter,
} from './data-manager.lib';
import { DataReflex, FilterConf } from './data-manager.types';

export class DataManagerService {
  private _data: any[] = [];
  private _reflex: DataReflex[] = []; //reflects data
  private _filters: Filter[] = [];
  private _paginator?: DataPaginator;

  //events
  reflexChange = new EventEmitter();

  constructor() {
    console.log('--- DataManagerService contructed ---');
  }

  //getters & setters
  get data() {
    return this._data;
  }

  set data(newData) {
    this._data = newData;
    this.reflex = this.data.map(reflexMapping);
  }

  get reflex() {
    return this._reflex;
  }

  set reflex(value: DataReflex[]) {
    this._reflex = value;
    this.reflexChange.emit();
  }

  get filters() {
    return this._filters;
  }

  get activeFilters() {
    return this.filters.filter((f: Filter) => f.isActive);
  }

  get paginator() {
    return this._paginator!;
  }

  set paginator(p: DataPaginator) {
    this._paginator = p;
  }

  //factory
  createPaginator() {
    this._paginator = new DataPaginator();
    this._paginator.dm = this;
    this._paginator.reloadPages();
    this.reflexChange.subscribe(() => this._paginator!.reloadPages());
    return this._paginator;
  }

  createFilter(filterConf: FilterConf) {
    let filter: Filter;
    switch(filterConf.type) {
      case 'date': filter = new DateFilter(filterConf); break;
      case 'selection': filter = new SelectionFilter(filterConf); break;
      default: filter = new TextFilter(filterConf); break;
    }
    filter.dm = this;
    this.filters.push(filter);
    return filter;
  }

  //repository
  //finds a filters by its field
  findFilterByField(field: string) {
    return this.filters.find((f) => f.field === field);
  }
}
