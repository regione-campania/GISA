import { Component, ElementRef, ViewChild } from '@angular/core';
import { ATableFilterComponent } from '../a-table-filter.component';

@Component({
  selector: 'a-table-text-filter',
  templateUrl: './a-table-text-filter.component.html',
  styleUrls: ['../a-table-filter.component.scss'],
})
export class ATableTextFilterComponent {
  @ViewChild('textControl') textControl?: ElementRef;
  @ViewChild('selectMode') selectMode?: ElementRef;

  condition = (value: string): boolean => {
    const pattern = this.textControl?.nativeElement.value;
    if(!pattern) return true;
    const x = value ? value.toLowerCase() : '';
    const y = pattern ? pattern.toLowerCase() : '';
    switch (this.selectMode?.nativeElement.value) {
      case 'not-contains': return !(x.includes(y));
      case 'starts-with': return x.startsWith(y);
      case 'ends-with': return x.endsWith(y);
      default: return x.includes(y);
    }
  };

  clean(): void {
    this.textControl!.nativeElement.value = '';
  }

}
