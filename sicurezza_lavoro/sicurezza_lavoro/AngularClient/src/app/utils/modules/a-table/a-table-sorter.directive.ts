/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { AfterViewInit, Directive, ElementRef, Input, ViewContainerRef, ViewRef } from "@angular/core";
import { Utils } from "../../utils.class";

@Directive({
    selector: '[aTableSorter]'
})
export class ATableSorterDirective implements AfterViewInit {

    @Input('aTableSorter') type?: string

    get table() {
        let e = this.elementRef.nativeElement
        while (e.tagName !== 'BODY') {
            if (e.tagName === 'TABLE')
                return e
            else
                e = e.parentElement
        }
        return undefined
    }

    get th() {
        let e = this.elementRef.nativeElement
        while (e.tagName !== 'BODY') {
            if (e.tagName === 'TH')
                return e
            else
                e = e.parentElement
        }
        return undefined
    }

    private _rows: any
    private get rows() { return this.table.tBodies[0]?.rows }
    private set rows(r) { this._rows = r }
    private _rowsCopy: any
    private _sorted = false
    private _sortOrder?: 'asc' | 'desc'
    private _hitArea: any

    constructor(private readonly elementRef: ElementRef) {
        this._hitArea = document.createElement('span')
        this._hitArea.className = 'table-sorter hit-area'
        this._hitArea.style.display = 'inline-block'
        this._hitArea.style.padding = '2px 6px'
        this._hitArea.innerHTML = '<i class="icon sort-icon fa-solid fa-sort"></i>'
        this.elementRef.nativeElement.insertAdjacentElement('beforeend', this._hitArea)
        this._hitArea.addEventListener('click', () => {
            this.sort()
        })
    }

    ngAfterViewInit(): void {
        this.table?.classList.add('sortable')
        this._rowsCopy = Array.from(this.rows)
    }

    sort() {
        if (!this._sorted) {
            this._sorted = true
            this._sortOrder = 'asc'
        }
        this._sortTable()
        this._sortOrder = this._sortOrder === 'asc' ? 'desc' : 'asc'
    }

    private _sortTable() {
        let rArray =
            Array
                .from(this.rows)
                .sort((a: any, b: any) => {
                    let aValue = a.cells[this.th.cellIndex].innerText
                    let bValue = b.cells[this.th.cellIndex].innerText
                    switch (this.type) {
                        case 'number': {
                            aValue = parseInt(aValue, 10)
                            bValue = parseInt(bValue, 10)
                        }; break;
                        case 'date': {
                            aValue = Utils.fromItalianDateString(aValue)
                            aValue = aValue.toString() === 'Invalid Date' ? Date.now() : aValue
                            bValue = Utils.fromItalianDateString(bValue)
                            bValue = bValue.toString() === 'Invalid Date' ? Date.now() : bValue
                        }; break;
                        default: {
                            aValue = aValue.toLowerCase()
                            bValue = bValue.toLowerCase()
                        }; break;
                    }
                    if (aValue < bValue)
                        return -1
                    else if (aValue > bValue)
                        return 1
                    return 0
                })
        if (this._sortOrder === 'desc')
            rArray.reverse()
        rArray.forEach((row: any) => {
            this.rows[0].parentElement.appendChild(row)
        })
    }

    private _reset() {
        this._rowsCopy.forEach((row: any) => {
            this.rows[0].parentElement.appendChild(row)
        })
        this._sortOrder = undefined
        this._sorted = false
    }

}