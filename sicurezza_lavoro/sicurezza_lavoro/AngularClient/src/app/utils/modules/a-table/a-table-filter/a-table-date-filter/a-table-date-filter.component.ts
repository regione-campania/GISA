/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Utils } from 'src/app/utils/utils.class';
import { ATableFilterComponent } from '../a-table-filter.component';

@Component({
  selector: 'a-table-date-filter',
  templateUrl: './a-table-date-filter.component.html',
  styleUrls: ['./a-table-date-filter.component.scss']
})
export class ATableDateFilterComponent extends ATableFilterComponent implements AfterViewInit {

  @ViewChild('selectMode') selectMode: any
  @ViewChild('date') dateControl: any
  @ViewChild('startDate') startDateControl: any
  @ViewChild('endDate') endDateControl: any

  date: any
  startDate: any
  endDate: any

  constructor(protected override elementRef: ElementRef) { 
    super(elementRef)
  }

  override ngAfterViewInit(): void {
  }

  override apply() {
    let p: Function //predicate
    const mode = this.selectMode.nativeElement.value
    switch(mode) {
      case "from-date": p = function(x: Date, y: Date): boolean {
        return x >= y
      }; break;
      case "till-date": p = function(x: Date, y: Date): boolean {
        return x <= y
      }; break;
      case "range": p = function(x: Date, start: Date, end: Date): boolean {
        return (x >= start && x <= end)
      }; break;
      //default: "exact-date"
      default: p = function(x: Date, y: Date): boolean {
        return (x.getDate() === y.getDate() && x.getMonth() === y.getMonth() && x.getFullYear() === y.getFullYear())
      }; break;
    }

    let x: any
    if(mode !== "range") {
      this.date = new Date(this.dateControl.nativeElement.value)
      if(this.date.toString() === 'Invalid Date')
        return
      this.date.setHours(0, 0, 0)
      for(let r of this.rows) {
        x = Utils.fromItalianDateString(r.cells[this.th.cellIndex].innerText)
        if(p(x, this.date))
          delete r.dataset[`hiddenByDate-${this._getFingerPrint(this.date)}`]
        else
          r.dataset[`hiddenByDate-${this._getFingerPrint(this.date)}`] = ''
      }
    }
    else {
      this.startDate = new Date(this.startDateControl.nativeElement.value)
      if(this.startDate.toString() === 'Invalid Date')
        this.startDate = new Date(0) // EPOC
      this.startDate.setHours(0, 0, 0)
      this.endDate = new Date(this.endDateControl.nativeElement.value)
      if(this.endDate.toString() === 'Invalid Date')
        this.endDate = new Date() // NOW
      this.endDate.setHours(0, 0, 0)
      for(let r of this.rows) {
        x = Utils.fromItalianDateString(r.cells[this.th.cellIndex].innerText)
        if(p(x, this.startDate, this.endDate))
          delete r.dataset[`hiddenByDates-${this._getFingerPrint(this.startDate)}-${this._getFingerPrint(this.endDate)}`]
        else
          r.dataset[`hiddenByDates-${this._getFingerPrint(this.startDate)}-${this._getFingerPrint(this.endDate)}`] = ''
      }
    }
  }


  override reset() {
    if(this.dateControl)
      this.dateControl.nativeElement.value = ''
    if(this.startDateControl)
      this.startDateControl.nativeElement.value = ''
    if(this.endDateControl)
      this.endDateControl.nativeElement.value = ''
  }

  private _getFingerPrint(date: Date) {
    return date.toLocaleString().replace(/\D/g, '')
  }

  protected override _clean() {
    if(this.date) {
      for(let r of this.rows)
        delete r.dataset[`hiddenByDate-${this._getFingerPrint(this.date)}`]
    }
    if(this.startDate && this.endDate) {
      for(let r of this.rows) {
        delete r.dataset[`hiddenByDates-${this._getFingerPrint(this.startDate)}-${this._getFingerPrint(this.endDate)}`]      }
    }
  }


}
