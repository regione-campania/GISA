/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { ComponentRef, Directive, ElementRef, Input, OnInit, ViewContainerRef } from "@angular/core";
import { ATablePaginatorComponent } from "./a-table-paginator/a-table-paginator.component";

@Directive({
    selector: 'table[aSmartTable]'
})
export class ASmartTableDirective implements OnInit {

    table: HTMLTableElement
    
    @Input('paginator') includePaginator: boolean = false
    paginatorComponent?: ComponentRef<ATablePaginatorComponent>
    
    constructor(private elementRef: ElementRef, private viewContainer: ViewContainerRef) {
        this.table = this.elementRef.nativeElement
    }

    ngOnInit(): void {
        if(this.includePaginator) {
            this._addPaginator()
        }
    }

    private _initPaginator() {
        this.paginatorComponent = this.viewContainer.createComponent(ATablePaginatorComponent)
        this.paginatorComponent.instance.table = this.table
    }

    private _addPaginator() {
        this._initPaginator()
        const wrapper = document.createElement('tr')
        const th = document.createElement('th')
        wrapper.className = 'paginator-wrapper'
        th.setAttribute('colspan', '100')
        th.appendChild(this.paginatorComponent?.location.nativeElement)
        wrapper.appendChild(th)
        this.table.tHead?.insertBefore(wrapper, this.table.tHead.firstChild)
    }
}