import { AfterViewInit, Directive, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';

//abstract
@Directive()
export abstract class AbstractForm implements AfterViewInit {
  @Input() showButton = true;
  @Input() buttonLabel = 'Invia';
  @Input() data?: any;
  @Output('onSubmit') submitEvent = new EventEmitter<any>()
  form = new FormGroup({});

  constructor() {}

  ngAfterViewInit(): void {
    const datepickers = document.querySelectorAll('input[type=date]')
    datepickers?.forEach((dp: any) => {
      dp.addEventListener('click', () => dp.showPicker())
    })
  }

  patchValue(
    control: any,
    value: any,
    options?: { set: Object[]; toFind: string; toPick?: string }
  ) {
    let patch = value;
    if (options) {
      console.log(options);
      if (!options.toPick) options.toPick = options.toFind;
      patch = options.set.find((item: any) => item[options.toFind] == value);
      patch = patch[options.toPick];
    }
    this.form.get(control)?.patchValue(patch);
  }

  submit() {
    this.submitEvent.emit(this.form.value)
  }
}
