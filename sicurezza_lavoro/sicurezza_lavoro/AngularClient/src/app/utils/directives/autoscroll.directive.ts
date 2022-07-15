import { Directive, ElementRef, HostListener, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";

@Directive({
  selector: '[autoscroll]'
})
export class AutoscrollDirective implements OnInit, OnChanges {
  @Input('autoscroll') elementMaxWidth: string = '100%';
  elementInnerText: HTMLElement;

  constructor(private elementRef: ElementRef) {
    this.elementInnerText = document.createElement('span');
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes['elementMaxWidth'].currentValue !== changes['elementMaxWidth'].previousValue)
      this.elementRef.nativeElement.style.maxWidth = this.elementMaxWidth;
  }

  ngOnInit(): void {
    this.elementInnerText.className = 'inner-text';
    this.elementInnerText.innerText = this.elementRef.nativeElement.innerText;
    this.elementRef.nativeElement.innerText = '';
    this.elementRef.nativeElement.append(this.elementInnerText);
  }

  @HostListener('mouseenter', ['$event']) onMouseEnter(e: Event) {
    this.elementInnerText.style.left =
      `calc(100% - ${this.elementInnerText.getBoundingClientRect().width}px)`;
  }

  @HostListener('mouseleave', ['$event']) onMouseLeave(e: Event) {
    this.elementInnerText.style.left = '0';
  }
}
