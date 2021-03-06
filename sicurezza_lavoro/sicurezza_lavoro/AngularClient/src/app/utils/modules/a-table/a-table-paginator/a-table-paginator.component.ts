/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { AfterViewChecked, Component, DoCheck, ViewChild } from '@angular/core';

@Component({
  selector: 'a-table-paginator',
  templateUrl: './a-table-paginator.component.html',
  styleUrls: ['./a-table-paginator.component.scss']
})
export class ATablePaginatorComponent implements DoCheck, AfterViewChecked {

  @ViewChild('rowsPerPageControl') rowsPerPageControl: any

  private _initialized = false

  table?: any
  get rows() { return this.table?.tBodies[0]?.rows}

  private get _visibleRows() {
    return this.rows ? Array.from(this.rows).filter((row: any) => !row.hidden) : new Array()
  }

  get totalRows() { return this._visibleRows.length }

  get rowsPerPage() { //selected by user
    return this.rowsPerPageControl ? this.rowsPerPageControl.nativeElement.value : 25
  }

  private _pages: Page[] = [new Page(null, 0)]
  get pages() { return this._pages }
  private set pages(pages: Page[]) { this._pages = pages }

  private _totalPages = 0

  private _currentPage: Page = this.pages[0]
  get currentPage() { return this._currentPage }
  private set currentPage(next: Page) {
    if (!this._currentPage)
      this._currentPage = this.pages[0]
    this._currentPage.rows?.forEach((r: any) => r.style.display = 'none')
    this._currentPage = next
    this._currentPage.rows.forEach((r: any) => r.style.display = 'table-row')
  }

  get firstPage() { return this.pages[0] }

  get lastPage() {
    return this.pages.length > 1 ? this.pages[this.pages.length-1] : undefined
  }

  get middlePages() {
    let mp
    if(this.pages.length >= 3) {
      mp = new Array<Page>()
      switch(this.pages.length) {
        case 3: mp = [this.pages[1]]; break;
        case 4: mp = [this.pages[1], this.pages[2]]; break;
        default: { // >= 5
          let mid = this.currentPage.pageNumber
          if (mid < 2)
            mid = 2
          else if (mid > this.pages.length - 3)
            mid = this.pages.length - 3
          mp = [this.pages[mid - 1], this.pages[mid], this.pages[mid + 1]]
        } break;
      }
    }
    return mp
  }

  constructor() {}

  ngDoCheck(): void {
    const newTotal = Math.max(1, Math.ceil(this.totalRows / this.rowsPerPage))
    if (this._totalPages !== newTotal) {
      this._totalPages = newTotal
      this._updatePages()
    }
  }

  ngAfterViewChecked(): void {
    if(this._initialized)
      return
    if (this.pages.length > 0 && this.pages[0].rows) {
      for (let r of this._visibleRows)
        r.style.display = 'none'
      this.openPage(0)
      this._initialized = true
    }
  }

  private _updatePages() {
    this.pages = []
    const pageSize = this.rowsPerPage
    for (let i = 0; i < this._totalPages; i++) {
      this._pages.push(new Page(this._visibleRows.slice(i * pageSize, (i + 1) * pageSize), i))
    }
    this.openPage(0)
  }

  openPage(pageNumber: number) {
    pageNumber = pageNumber < 0 ? 0 : (pageNumber >= this.pages.length ? this.pages.length - 1 : pageNumber)
    this.currentPage = this.pages[pageNumber]
  }

}

class Page {
  constructor(public rows: any, public pageNumber: number) {
    if(this.rows) {
      Array.from(this.rows).forEach((r: any) => {
        r.dataset['page'] = this.pageNumber
      })
    }
  }
}
