import { Component } from '@angular/core';
import { AColumnControl } from '../a-column-control.directive';

@Component({
  selector: 'a-table-sorter',
  templateUrl: './a-table-sorter.component.html',
  styleUrls: ['./a-table-sorter.component.scss'],
})
export class ATableSorterComponent extends AColumnControl {
  private _sortOrder: SortOrder = SortOrder.none;
  private _originalOrder: any;

  sort() {
    //checks
    if (!this.key) throw new Error('Provide a key for this sorter');
    if (!this.aSmartTable.data) return;
    this._sortOrder =
      this._sortOrder === SortOrder.desc ? SortOrder.none : this._sortOrder + 1;
    let activeSorters = this.aSmartTable.activeSorters;
    if (this.sortOrder === SortOrder.none) {
      this.aSmartTable.content = this.originalOrder.slice();
      this.originalOrder = undefined;
      if (activeSorters.includes(this))
        activeSorters.splice(activeSorters.indexOf(this), 1);
    } else {
      if (!this.originalOrder)
        this.originalOrder = this.aSmartTable.content.slice();
      if (!activeSorters.includes(this)) activeSorters.push(this);
    }
    if (activeSorters.length > 0) {
      this.aSmartTable.content = this.aSmartTable.content.sort((a, b) => {
        let x, y, comparison;
        let comparisons: number[] = [];
        activeSorters.forEach((s) => {
          x = parseValue(this.aSmartTable.data![a.p as any][s.key!], s.type);
          y = parseValue(this.aSmartTable.data![b.p as any][s.key!], s.type);
          comparison = compare(x, y);
          if (s.sortOrder === SortOrder.desc) comparison = -comparison;
          comparisons.push(comparison);
        });
        return or(...comparisons);
      });
    }
    else {
      this.aSmartTable.content = this.aSmartTable.data.map((_, i) => { return {p: i, v: true} })
    }
  }

  //getters & setters
  get sortOrder() {
    return this._sortOrder;
  }

  private set sortOrder(order: SortOrder) {
    this._sortOrder = order;
  }

  get isActive() {
    return this.sortOrder !== SortOrder.none;
  }

  get originalOrder(): any {
    return this._originalOrder;
  }

  private set originalOrder(order: any) {
    this._originalOrder = order;
  }
}

enum SortOrder {
  'none',
  'asc',
  'desc',
}

//helpers
function parseValue(value: any, type: string = 'text') {
  let parsed = value ?? '';
  switch (type) {
    case 'number':
      parsed = parseInt(parsed, 10);
      break;
    case 'date':
      parsed = new Date(parsed);
      parsed = parsed.toString() === 'Invalid Date' ? Date.now() : parsed;
      break;
    default:
      parsed = parsed.toLowerCase().trim();
      break;
  }
  return parsed;
}

function compare<T>(a: T, b: T): number {
  return a < b ? -1 : a > b ? 1 : 0;
}

function or(...args: any): any {
  if (args.length === 0) return undefined;
  let result = args[0];
  let i = 1;
  while (!result && i < args.length) {
    result = args[i++];
  }
  return result;
}
