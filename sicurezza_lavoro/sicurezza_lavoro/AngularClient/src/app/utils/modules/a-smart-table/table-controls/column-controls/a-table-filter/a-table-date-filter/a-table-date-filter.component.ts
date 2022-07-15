import { Component, ViewChild } from '@angular/core';
import { ATableFilterComponent } from '../a-table-filter.component';

@Component({
  selector: 'a-table-date-filter',
  templateUrl: './a-table-date-filter.component.html',
  styleUrls: ['../a-table-filter.component.scss'],
})
export class ATableDateFilterComponent {
  @ViewChild('selectMode') selectMode: any;
  @ViewChild('date') dateControl: any;
  @ViewChild('startDate') startDateControl: any;
  @ViewChild('endDate') endDateControl: any;

  condition = (value: string): boolean => {
    //converto la stringa da DD-MM-YYYY a MM-DD-YYYY
    let splitted = value.split("-");
    let x = new Date(`${splitted[2]}-${splitted[1]}-${splitted[0]}`);
    //let x = new Date(value);
    if (x.toString() === 'Invalid Date') return false;
    x.setHours(0, 0, 0, 0);
    const mode = this.selectMode?.nativeElement.value;
    if (mode === 'range') {
      let startDate = new Date(this.startDateControl?.nativeElement.value);
      if (startDate.toString() === 'Invalid Date') startDate = new Date(0); // EPOC
      startDate.setHours(0, 0, 0, 0);
      let endDate = new Date(this.endDateControl?.nativeElement.value);
      if (endDate.toString() === 'Invalid Date') endDate = new Date(); // NOW
      endDate.setHours(0, 0, 0, 0);
      return x >= startDate && x <= endDate;
    } else {
      const dateControlValue = this.dateControl?.nativeElement.value;
      if(!dateControlValue) return true;
      let y = new Date(dateControlValue);
      if (y.toString() === 'Invalid Date') return false;
      y.setHours(0, 0, 0, 0);
      switch (mode) {
        case 'from-date':
          return x >= y;
        case 'till-date':
          return x <= y;
        default:
          return (
            x.getDate() === y.getDate() &&
            x.getMonth() === y.getMonth() &&
            x.getFullYear() === y.getFullYear()
          );
      }
    }
  };

  clean() {
    if (this.dateControl) this.dateControl.nativeElement.value = '';
    if (this.startDateControl) this.startDateControl.nativeElement.value = '';
    if (this.endDateControl) this.endDateControl.nativeElement.value = '';
  }
}
