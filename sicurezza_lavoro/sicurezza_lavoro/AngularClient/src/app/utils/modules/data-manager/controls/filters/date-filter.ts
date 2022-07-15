import { Filter, normalizeDate } from '../../data-manager.lib';
import { DateFilterOption } from '../../data-manager.types';

export class DateFilter extends Filter {
  options: DateFilterOption[] = [
    'exact-date',
    'from-date',
    'till-date',
    'range',
  ];

  selectedOption: DateFilterOption = 'exact-date';
  exactDate = new Date(); // NOW
  startDate = new Date(0); // EPOC
  endDate = new Date(); // NOW

  override condition = (value: string | Date) => {
    let x = value instanceof Date ? value : new Date(value);
    if (x.toString() === 'Invalid Date') return false;
    x = normalizeDate(x);
    if(this.selectedOption === 'range') {
      return x >= normalizeDate(this.startDate) && x <= normalizeDate(this.endDate);
    }
    else {
     let y = normalizeDate(this.exactDate);
      switch (this.selectedOption) {
        case 'from-date':
          return x >= y;
        case 'till-date':
          return x <= y;
        default:
          //exact date
          return (
            x.getDate() === y.getDate() &&
            x.getMonth() === y.getMonth() &&
            x.getFullYear() === y.getFullYear()
          );
      }
    }
  };


  clean() {
    this.selectedOption = 'exact-date';
    this.exactDate = new Date(); // NOW
    this.startDate = new Date(0); // EPOC
    this.endDate = new Date(); // NOW
  }

}
