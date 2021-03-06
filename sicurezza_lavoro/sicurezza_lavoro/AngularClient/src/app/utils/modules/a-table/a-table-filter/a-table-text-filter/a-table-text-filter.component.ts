/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ATableFilterComponent } from '../a-table-filter.component';

@Component({
  selector: 'a-table-text-filter',
  templateUrl: './a-table-text-filter.component.html',
  styleUrls: ['./a-table-text-filter.component.scss']
})
export class ATableTextFilterComponent extends ATableFilterComponent implements AfterViewInit {

  @ViewChild('textControl') textControl: any
  @ViewChild('selectMode') selectMode: any

  pattern: string = ''

  constructor(protected override elementRef: ElementRef) { 
    super(elementRef)
  }

  override ngAfterViewInit(): void {
    this.textControl = this.textControl.nativeElement
  }

  override apply() {
    let p: Function // predicate
    this.pattern = this.textControl.value
    if(!this.pattern)
      return
    //capitalizing pattern (needed to set the data-* attribute)
    {
      let c = this.pattern.charAt(0)
      this.pattern = this.pattern.replace(c, c.toUpperCase())
    }
    const mode = this.selectMode.nativeElement.value
    switch(mode) {
      case "not-contains": p = function(x: string, y: string): boolean {
        return !x.toLowerCase().includes(y.toLowerCase())
      }; break;
      case "starts-with": p = function(x: string, y: string): boolean {
        return x.toLowerCase().startsWith(y.toLowerCase())
      }; break;
      case "ends-with": p = function(x: string, y: string): boolean {
        return x.toLowerCase().endsWith(y.toLowerCase())
      }; break;
      //default: "contains"
      default: p = function(x: string, y: string): boolean {
        return x.toLowerCase().includes(y.toLowerCase())
      }; break;
    }

    let x: any
    for(let r of this.rows) {
      x = r.cells[this.th.cellIndex].innerText
      if(p(x, this.pattern))
        delete r.dataset[`hiddenByText${this.pattern}`]
      else
        r.dataset[`hiddenByText${this.pattern}`] = ''
    }
  }

  override reset() {
    this.textControl.value = ''
  }

  protected override _clean() {
    for(let r of this.rows)
      delete r.dataset[`hiddenByText${this.pattern}`]
  }

}
