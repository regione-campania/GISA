/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { AfterViewInit, Component, ElementRef, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import { ATableFilterComponent } from '../a-table-filter.component';

@Component({
  selector: 'a-table-selection-filter',
  templateUrl: './a-table-selection-filter.component.html',
  styleUrls: ['./a-table-selection-filter.component.scss']
})
export class ATableSelectionFilterComponent extends ATableFilterComponent implements OnChanges, AfterViewInit {

  @ViewChild('ms') multiSelect: any
  valuesToFind: any | any[]
  
  constructor(protected override elementRef: ElementRef) { 
    super(elementRef)
  }

  override ngOnChanges(_: SimpleChanges): void {
    super.ngOnChanges(_)
    if(this.valuesToFind) {
      if(!Array.isArray(this.valuesToFind))
        this.valuesToFind = Array.from(this.valuesToFind)
    }
  }

  override apply(): void {
    if(!this.valuesToFind || this.valuesToFind.length <= 0)
      return
    let x: any
    for(let r of this.rows) {
      x = r.cells[this.th.cellIndex].innerText.toLowerCase()
      if(this.valuesToFind.map((v: string) => v.toLowerCase()).includes(x))
        delete r.dataset[`hiddenBySelection`]
      else
        r.dataset[`hiddenBySelection`] = ''
    }
  }

  override reset() {
    this.valuesToFind = []
    this.multiSelect.reset()
  }

  protected override _clean() {
    for(let r of this.rows)
      delete r.dataset[`hiddenBySelection`]
  }

}
