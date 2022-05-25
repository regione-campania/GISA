/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { Directive, ElementRef, EventEmitter, HostListener, Input, OnChanges, Output, SimpleChanges } from '@angular/core';

@Directive({
  selector: 'input[autofiller]'
})
export class AutofillerDirective {

  @Input('autofiller') data?: any[]
  @Input('autofillerKey') key?: string | number
  @Input('minLength') minLength = 2
  @Input('matchingMode') matchingMode = 'startsWith'
  @Input() appendTo?: string

  @Output() valueChange = new EventEmitter<any>()
  @Output() dataChange = new EventEmitter<any>()

  private _wrapper?: any
  private _input?: any
  private _list?: any
  private _pattern?: string

  constructor(private elementRef: ElementRef) {
    this._input = this.elementRef.nativeElement
    this._input.autocomplete = 'off'
    this._wrapper = document.createElement('div')
    this._wrapper.className = 'autofiller-wrapper'
    this._input.insertAdjacentElement("beforebegin", this._wrapper)
    this._wrapper.append(this._input)
  }

  @HostListener('input') onInput() {
    this._pattern = this._input.value
    if(this._pattern!.length < this.minLength) {
      if(this._list)
        this._destroy()
    }
    else {
      this._build()
    }
  }

  @HostListener('keydown', ['$event']) onKeyDown(e: KeyboardEvent) {
    e.stopPropagation()
    if(this._list && !this._list.hasFocus && e.key === 'Tab') {
      this._list.hasFocus = true
      this._list.focusedElement = this._list.querySelector('li:not([hidden])')
    }
  }

  private _build() {
    if(!this._list) {
      let thereIsAMatch = false
      this._list = document.createElement('ul')
      this._list.className = 'autofiller-list box-3d'
      this.data?.forEach(d => {
        let value = this.key ? d[this.key] : d
        if(this._match(value, this._pattern!)) {
          thereIsAMatch = true
          this._addLi(value, d) //if data are primitives, d === value
        }
      })
      if(thereIsAMatch) {
        this._list.hasFocus = false
        this._list.focusedElement = this._list.firstElementChild
        if(this.appendTo === 'body') {
          document.body.append(this._list)
          this._updateListPosition()
          window.addEventListener('resize', () => this._updateListPosition())
        }
        else
          this._wrapper.append(this._list)
        this._addEvents()
      }
    }
    else { //a list is already present, hence a match (so continue searching in the list, instead)
      let thereIsAMatch = false
      this._list.childNodes.forEach((li: any) => {
        if(!this._match(li.innerText, this._pattern!))
          li.hidden = true
        else {
          li.hidden = false
          thereIsAMatch = true
        }
      })
      if(!thereIsAMatch)
        this.hide()
      else
        this.show()
    }
  }

  private _destroy() {
    this._list?.remove()
    this._list = undefined
  }

  private _match(a: string, b: string) {
    if(!a || !b)
      return false
    let result = false
    a = a.toLowerCase()
    b = b.toLowerCase()
    switch(this.matchingMode) {
      case 'startsWith': result = a.startsWith(b); break;
      case 'endsWith': result = a.endsWith(b); break;
      case 'includes': result = a.includes(b); break;
      default: throw new Error('provide a valid matching mode');
    }
    return result
  }

  private _addLi(value: any, data?: any) {
    if(this._list) {
      const li = document.createElement('li') as any
      li.innerText = value
      li.__data__ = data //extra data, use to pass the whole object selected (for complex values only)
      li.setAttribute('tabindex', '0')
      li.addEventListener('keydown', () => li.focus())
      this._list?.append(li)
    }
  }

  private _addEvents() {
    this._list?.addEventListener('click', (e: any) => {
      e.stopPropagation()
      let t = e.target
      if(t.tagName === 'LI') {
        this._list.focusedElement = t
        this._emitValues()
      }
    })

    this._list.addEventListener('keydown', (e: KeyboardEvent) => {
      e.stopPropagation()
      e.preventDefault()
      function getPreviousSibling(li: any) {
        let sibling = li.previousElementSibling ?? li
        while(sibling && sibling.hidden) {
          sibling = sibling.previousElementSibling
        }
        return sibling
      }
      function getNextSibling(li: any) {
        let sibling = li.nextElementSibling ?? li
        while(sibling && sibling.hidden) {
          sibling = sibling.nextElementSibling
        }
        return sibling
      }
      switch(e.key) {
        case "Down":
        case "ArrowDown":
          this._list.focusedElement = getNextSibling(this._list.focusedElement); break;
        case "Up":
        case "ArrowUp":
          this._list.focusedElement = getPreviousSibling(this._list.focusedElement); break;
        case "Enter": this._emitValues(); break;
        default: break;
      }
      this._list.focusedElement.focus()
    })

    window.addEventListener('click', (e: Event) => {
      if(e.target === this._input) {
        e.stopPropagation()
        this.show()
      }
      else
        this.hide()
    })
  }

  private _emitValues() {
    this.valueChange.emit(this._list.focusedElement.innerText)
    this.dataChange.emit(this._list.focusedElement.__data__)
    this.hide()
  }

  show() {
    if(this._list)
      this._list.hidden = false
  }

  hide() {
    if(this._list) {
      this._list.hidden = true
      this._list.hasFocus = false
    }
  }

  private _updateListPosition() {
    let wrapBox = this._wrapper.getBoundingClientRect()
    Object.assign(this._list.style, {
      maxWidth: `${wrapBox.width}px`,
      left: `${wrapBox.left}px`,
      top: `${wrapBox.bottom + window.scrollY}px`
    })
  }
}
