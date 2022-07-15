import { Component, HostListener, Input, ViewChild } from '@angular/core';
import { TableContent } from '../../../a-smart-table.component';
import { AColumnControl } from '../a-column-control.directive';

@Component({
  selector: 'a-table-filter',
  templateUrl: './a-table-filter.component.html',
  styleUrls: ['./a-table-filter.component.scss'],
})
export class ATableFilterComponent extends AColumnControl {
  @ViewChild('filter') filter?: ATableFilterComponent; //actual filter
  @Input() values?: any; //used when this instanceof ATableSelectionFilter

  affected: Array<TableContent> = [];
  discarded: Array<TableContent> = [];
  isVisible = false;

  get isActive() {
    return (
      (this.affected.length > 0 &&
        this.affected.length < this.aSmartTable.content.length) ||
      this.discarded.length > 0
    );
  }

  protected get tableFilters() {
    return this.aSmartTable.controls.filter(
      (c) => c.constructor === ATableFilterComponent
    ) as ATableFilterComponent[];
  }

  condition: (value: any) => boolean = function () {
    return true;
  };

  toggle(): void {
    this.isVisible ? this.hide() : this.show();
  }

  show(): void {
    this.tableFilters.find((f) => f.isVisible)?.hide();
    this.isVisible = true;
    setTimeout(() => this._updatePosition(), 0);
    window.addEventListener('click', this._windowClickListener);
  }

  hide(): void {
    this.isVisible = false;
    window.removeEventListener('click', this._windowClickListener);
  }

  protected _windowClickListener = () => {
    this.hide();
  };

  apply() {
    //checks
    if (!this.key) throw new Error('Provide a key for this filter');
    if (!this.aSmartTable.data) return;

    let searchKey: any = [this.key];
    if (this.key.toString().includes('+')) {
      searchKey = [];
      this.key
        .toString()
        .split('+')
        .forEach((k: string) => {
          searchKey.push(k);
        });
    }
    this.affected = [];
    this.condition = this.filter?.condition ?? this.condition;
    this.aSmartTable.content?.forEach((c) => {
      searchKey.forEach((k: any) => {
        if (this.condition(this.aSmartTable.data![c.p as any][k]))
          this.affected.push(c);
        else this.discarded.push(c);
      });
    });
    this._updateTable();
  }

  reset() {
    this.affected = [];
    this.discarded = [];
    this._updateTable();
  }

  resetAll(): void {
    this.tableFilters.forEach((f) => {
      f.clean();
      f.reset();
    });
  }

  clean() {
    this.filter?.clean();
  }

  @HostListener('click', ['$event']) onClick(e: Event) {
    e.stopPropagation();
  }

  onKeyDown(e: KeyboardEvent) {
    e.stopPropagation();
    if (e.key === 'Enter') {
      e.preventDefault();
      this.apply();
      this.hide();
    } else if (e.key === 'Escape') this.hide();
  }

  private _updateTable() {
    const activeFilters = this.aSmartTable.controls.filter((c) => {
      if (c.constructor === ATableFilterComponent)
        return (c as ATableFilterComponent).isActive;
      return false;
    }) as ATableFilterComponent[];
    if (activeFilters.length === 0) {
      this.aSmartTable.content.forEach((c) => (c.v = true));
    } else {
      let intersection: any = new Set(activeFilters[0].affected);
      for (let i = 1; i < activeFilters.length; i++) {
        intersection = intersect(
          intersection,
          new Set(activeFilters[i].affected)
        );
      }
      intersection = Array.from(intersection) as Array<TableContent>;
      this.aSmartTable.content.forEach((c) => {
        c.v = intersection.includes(c);
      });
    }
    this.aSmartTable.contentChange.emit();
  }

  private _updatePosition() {
    const filterTemplate =
      this.elementRef.nativeElement.querySelector('.filter-template');
    if (filterTemplate) {
      const filterBox = filterTemplate.getBoundingClientRect();
      const tableBox =
        this.aSmartTable.table?.nativeElement.getBoundingClientRect();
      if (filterBox.right >= tableBox.right) filterTemplate.style.right = '0';
      else if (filterBox.left <= tableBox.left) filterTemplate.style.left = '0';
    }
  }
}

//helper
function intersect(setA: Set<any>, setB: Set<any>) {
  let intersection = new Set<any>();
  for (let el of setB) if (setA.has(el)) intersection.add(el);
  return intersection;
}
