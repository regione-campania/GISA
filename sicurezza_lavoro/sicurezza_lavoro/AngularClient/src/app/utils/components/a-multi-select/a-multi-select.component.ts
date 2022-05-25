/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { AfterViewInit, Component, ElementRef, EventEmitter, HostListener, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';

@Component({
  selector: 'a-multi-select',
  templateUrl: './a-multi-select.component.html',
  styleUrls: ['./a-multi-select.component.scss']
})
export class AMultiSelectComponent implements OnChanges, AfterViewInit {

  @ViewChild('control') control: any
  @ViewChild('optionsList') optionsList: any
  @Input() options: any
  @Output('onChange') ee = new EventEmitter<any>()
  private _optionsLoaded = false

  selectedOptions: any[] = []

  constructor(private elementRef: ElementRef) { 
    this.elementRef.nativeElement.style.display = 'block'
    this.elementRef.nativeElement.style.width = '100%'
  }

  @HostListener('click', ['$event']) onClick(e: any) {
    e.stopPropagation()
  }

  ngOnChanges(_: SimpleChanges): void {
    if(!this._optionsLoaded) {
      if(!Array.isArray(this.options)) {
        this.options = this.options.split(',').map((s: string) => s.trim())
      }
      if(this.options)
        this._optionsLoaded = true
    }
  }

  ngAfterViewInit(): void {
    this.control.nativeElement.addEventListener('click', () => {
      this.optionsList.nativeElement.hidden = !this.optionsList.nativeElement.hidden
    })
    this.optionsList.nativeElement.addEventListener('click', () => {
      this.selectedOptions = Array.from(this.optionsList.nativeElement.querySelectorAll('input:checked'))
        .map((i: any) => i.value)
      this.ee.emit(this.selectedOptions)
    })
    window.addEventListener('click', () => this.optionsList.nativeElement.hidden = true)
  }

  reset() {
    this.selectedOptions = []
    this.optionsList.nativeElement.querySelectorAll('input[type="checkbox"]')
      .forEach((i: any) => i.checked = false)
  }

}
