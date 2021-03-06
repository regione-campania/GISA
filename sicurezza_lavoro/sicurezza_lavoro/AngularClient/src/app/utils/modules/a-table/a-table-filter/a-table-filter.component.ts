/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { AfterViewInit, Component, ElementRef, HostListener, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';

/*
 * Questo componente delega alle suo sottoclassi il compito di filtrare gli elementi poiché i filtri
 * possono essere di diverso tipo: testuali, numerici, temporali, ecc.
 * Le sottoclassi processano le righe applicando un attributo che inizia con ('hidden-by') quando una riga 
 * deve essere nascosta in modo che questo componente (contenitore) possa capire quali righe devono essere 
 * effettivamente nascoste.
 * Quando una sottoclasse ha finito di processare le righe, questo componente nasconde o mostra le righe
 * a seconda se esse siano 'flaggate' o meno.
*/

@Component({
  selector: 'a-table-filter',
  templateUrl: './a-table-filter.component.html',
  styleUrls: ['./a-table-filter.component.scss']
})
export class ATableFilterComponent implements AfterViewInit, OnChanges {

  get table() {
    let e = this.elementRef.nativeElement
    while(e.tagName !== 'BODY') {
      if(e.tagName === 'TABLE')
        return e
      else
        e = e.parentElement
    }
    return undefined
  }

  get th() {
    let e = this.elementRef.nativeElement
    while(e.tagName !== 'BODY') {
      if(e.tagName === 'TH')
        return e
      else
        e = e.parentElement
    }
    return undefined
  }

  get rows() {
    return this.table.tBodies[0]?.rows
  }

  private _isActive = false

  get isActive() {
    return this._isActive
  }
  private set isActive(newValue) {
    this._isActive = newValue
  }

  @Input() type?: string
  @Input() values?: any
  private _valuesLoaded = false

  @ViewChild('filterWrapper') protected _filterWrapper: any
  @ViewChild('filter') protected _filter: any

  constructor(protected elementRef: ElementRef) {}

  ngOnChanges(_: SimpleChanges): void {
    if(!this._valuesLoaded && this.type === 'selection') {
      if(!Array.isArray(this.values)) {
        this.values = this.values.split(',').map((s: string) => s.trim())
      }
      if(this.values)
        this._valuesLoaded = true
    }
  }

  ngAfterViewInit(): void {
    window.addEventListener('click', () => this.hide())
    if (!('filters' in this.table))
      this.table.filters = []
    this.table.filters.push(this)
    this.table.classList.add('filterable')
  }

  @HostListener('click', ['$event']) onClick(e: any) {
    e.stopPropagation()
  }

  onKeyDown(e: KeyboardEvent) {
    e.stopPropagation()
    if(e.key === 'Enter') {
      this.apply()
      this.hide()
    }
    else if(e.key === 'Escape')
      this.hide()
  }

  show() {
    if(this._filterWrapper) {
      this._filterWrapper.nativeElement.id = 'opened-filter'
      this._filterWrapper.nativeElement.hidden = false
      this._updatePosition()
    }
  }

  hide() {
    if(this._filterWrapper) {
      this._filterWrapper.nativeElement.id = ''
      this._filterWrapper.nativeElement.hidden = true
    }
  }

  toggle() {
    const openedFilter = document.getElementById('opened-filter')
    if(openedFilter && openedFilter !== this._filterWrapper.nativeElement) {
      openedFilter.hidden = true
      openedFilter.id = ''
    }
    this._filterWrapper.nativeElement.hidden ? this.show() : this.hide()
  }

  apply() {
    this._clean()
    this._filter.apply()
    let affected = false
    for(let r of this.rows) {
      affected = false
      for(let a of r.attributes) {
        if(a.name.startsWith('data-hidden-by')) {
          affected = true
          this.isActive = true //there's at least one row affected
        } 
      }
      r.hidden = affected
      r.style.display = affected ? 'none' : 'table-row'
    }
  }

  reset() {
    this._clean()
    this._filter.reset()
    let stillAffected = false
    for(let r of this.rows) {
      stillAffected = false
      for(let a of r.attributes) {
        if(a.name.startsWith('data-hidden-by'))
          stillAffected = true
      }
      if(!stillAffected) { //there's no more filter applied
        r.hidden = false
        r.style.display = 'table-row'
      }
    }
    this.isActive = false
  }

  resetAll() {
    this.table.filters.forEach((filter: any) => {filter.reset()})
  }

  protected _clean() {
    this._filter._clean()
    this.isActive = false
  }

  private _updatePosition() {
    if(this._filterWrapper) {
      let filterBox = this._filterWrapper.nativeElement.getBoundingClientRect()
      if(filterBox.right >= window.innerWidth)
        this._filterWrapper.nativeElement.style.right = '0'
    }
  }

}
