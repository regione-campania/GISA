import { Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output, ViewChild } from '@angular/core';

@Component({
  selector: 'a-multi-select',
  templateUrl: './a-multi-select.component.html',
  styleUrls: ['./a-multi-select.component.scss']
})
export class AMultiSelectComponent implements OnInit {
  @Input() options: any
  @Output('onChange') ee = new EventEmitter<any>()

  @ViewChild('control') control: any
  @ViewChild('optionsList') optionsList: any

  selectedOptions: any[] = []

  constructor(private elementRef: ElementRef) {
    this.elementRef.nativeElement.style.display = 'block';
    this.elementRef.nativeElement.style.width = '100%';
  }

  @HostListener('click', ['$event']) onClick(e: any) {
    e.stopPropagation()
  }

  ngOnInit(): void {
    if(!Array.isArray(this.options))
      this.options = this.options.split(',').map((s: string) => s.trim());
    this.options = Array.from(new Set(this.options));
    window.addEventListener('click', () => this.optionsList.nativeElement.hidden = true);
  }

  onChange(e: Event) {
    const checkbox = e.target as HTMLInputElement;
    if(checkbox.checked)
      this.selectedOptions.push(checkbox.value);
    else
      this.selectedOptions = this.selectedOptions.filter((opt: any) => opt !== checkbox.value);
    this.ee.emit(this.selectedOptions);
  }

  reset() {
    this.selectedOptions = []
    this.optionsList.nativeElement.querySelectorAll('input[type="checkbox"]')
      .forEach((i: any) => i.checked = false)
  }

}
