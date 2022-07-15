import {
  Component,
  ContentChild,
  ContentChildren,
  Input,
  OnChanges,
  OnInit,
  QueryList,
  SimpleChanges,
} from '@angular/core';
import { ATemplateDirective } from '../../directives/a-template.directive';
import { DataManagerService } from '../data-manager/data-manager.service';
import { NgbOffcanvas } from '@ng-bootstrap/ng-bootstrap';
import { ADataDeckFilterConf } from './a-data-deck.types';
import { Filter } from '../data-manager/data-manager.lib';

@Component({
  selector: 'a-data-deck',
  templateUrl: './a-data-deck.component.html',
  styleUrls: ['./a-data-deck.component.scss'],
})
export class ADataDeckComponent implements OnChanges {
  @Input() data: any[] = [];
  @Input('paginator') includePaginator: boolean = false;
  @ContentChild(ATemplateDirective) cardTemplate?: ATemplateDirective;

  private _filtersConf: ADataDeckFilterConf[] = [];
  selectedFilter?: Filter;

  constructor(
    public dm: DataManagerService,
    public offcanvasService: NgbOffcanvas
  ) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['data'].previousValue !== changes['data'].currentValue) {
      if (this.data) {
        this.dm.data = this.data;
      }
    }
  }

  selectFilter(field: string) {
    this.selectedFilter = this.dm.findFilterByField(field);
  }

  getFilterLabel(filter: Filter) {
    return this.filtersConf.find(f => f.field === filter.field)?.label
  }

  //getters & setters
  @Input('filters')
  get filtersConf() {
    return this._filtersConf;
  }

  set filtersConf(conf: ADataDeckFilterConf[]) {
    this._filtersConf = conf;
    this.filtersConf.forEach((c) => {
      this.dm.createFilter({
        field: c.field,
        type: c.type,
        values: c.values,
      });
    });
  }
}
