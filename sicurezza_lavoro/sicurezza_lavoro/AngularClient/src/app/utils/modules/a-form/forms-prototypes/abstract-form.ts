/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
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
