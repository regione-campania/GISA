import { Directive, Input, TemplateRef } from '@angular/core';

@Directive({
  selector: 'ng-template[aTemplate]'
})
export class ATemplateDirective {
  @Input('aTemplate') name?: string;

  constructor(public templateRef: TemplateRef<unknown>) {}

}
