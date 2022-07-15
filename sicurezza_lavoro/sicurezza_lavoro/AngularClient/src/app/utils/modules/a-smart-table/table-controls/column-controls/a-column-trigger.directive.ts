import { Directive, ElementRef, Input } from '@angular/core';

@Directive({
  selector: '[aColumnTrigger]',
})
export class AColumnTriggerDirective {
  @Input('aColumnTrigger') action: Function = () => {
    return;
  };
  @Input() event: keyof HTMLElementEventMap = 'click';

  element: any;

  constructor(private elementRef: ElementRef) {
    this.element = this.elementRef.nativeElement;
  }
}
