import {
  Component,
  ContentChildren,
  ElementRef,
  EventEmitter,
  Input,
  OnChanges,
  Output,
  QueryList,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { ATemplateDirective } from '../../directives/a-template.directive';
import { ATableControl } from './table-controls/a-table-control.directive';
import { ATablePaginatorComponent } from './table-controls/a-table-paginator/a-table-paginator.component';
import { ATableSorterComponent } from './table-controls/column-controls/a-table-sorter/a-table-sorter.component';

@Component({
  selector: 'a-smart-table',
  templateUrl: './a-smart-table.component.html',
  styleUrls: ['./a-smart-table.component.scss'],
})
export class ASmartTableComponent implements OnChanges {
  @ContentChildren(ATemplateDirective)
  templates?: QueryList<ATemplateDirective>;
  @ViewChild('table') table?: ElementRef;
  @Input('class') class?: string;
  @Input('data') data?: any[] = [];
  @Input('paginator') includePaginator = false;
  @Input('paginatorSide') paginatorSide: 'top' | 'bottom' | 'both' = 'top';
  @Output() contentChange = new EventEmitter<any>();

  controls: ATableControl[] = [];
  activeSorters: ATableSorterComponent[] = [];

  private _content: Array<TableContent> = [];
  get content() {
    return this._content;
  }
  set content(c: Array<TableContent>) {
    this._content = c;
    this.contentChange.emit();
  }

  get caption() {
    return this.templates?.find((t) => t.name === 'caption');
  }
  get head() {
    return this.templates?.find((t) => t.name === 'head');
  }
  get body() {
    return this.templates?.find((t) => t.name === 'body');
  }
  get footer() {
    return this.templates?.find((t) => t.name === 'footer');
  }
  get paginator() {
    return this.controls.find(
      (c) => c instanceof ATablePaginatorComponent
    ) as ATablePaginatorComponent;
  }

  constructor() {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['data'].previousValue !== changes['data'].currentValue) {
      if(this.data) {
        this.content = this.data.map((_, i) => { return {p: i, v: true} })
      }
    }
  }
}

export interface TableContent {
  p: any,      // p: pointer -> points to an element of data by saving its key
  v: boolean   // v: visibility -> tracks that element visibility
}
