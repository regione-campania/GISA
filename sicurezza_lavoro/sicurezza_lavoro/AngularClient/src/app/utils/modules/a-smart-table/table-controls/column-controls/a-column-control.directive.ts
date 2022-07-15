import {
  AfterViewInit,
  Directive,
  Input,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ATableControl } from '../a-table-control.directive';
import { AColumnTriggerDirective } from './a-column-trigger.directive';

@Directive()
export abstract class AColumnControl extends ATableControl implements AfterViewInit {
  @Input() type: string = 'text';
  @Input() key?: string | number;
  @ViewChild(AColumnTriggerDirective) trigger?: AColumnTriggerDirective;

  ngAfterViewInit() {
    this.trigger?.element.addEventListener(this.trigger.event, () => {
      this.trigger?.action.call(this);
    });
  }
}
