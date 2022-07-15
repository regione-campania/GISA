import { TextFilterOption } from '../../data-manager.types';
import { Filter } from './filter';

export class TextFilter extends Filter {
  pattern = '';
  options: TextFilterOption[] = [
    'contains',
    'not-contains',
    'starts-with',
    'ends-with',
  ];
  private _selectedOption: TextFilterOption = 'contains';

  override condition = (val: string) => {
    val = val ?? '';
    return val.toLowerCase().includes(this.pattern.toLowerCase());
  };

  clean(): void {
    this.pattern = '';
  }

  get selectedOption() {
    return this._selectedOption;
  }

  set selectedOption(opt: TextFilterOption) {
    this._selectedOption = opt;
    let compareFn = (a: string, b: string) => a.includes(b);
    switch (this.selectedOption) {
      case 'not-contains':
        compareFn = (a: string, b: string) => !a.includes(b);
        break;
      case 'starts-with':
        compareFn = (a: string, b: string) => a.startsWith(b);
        break;
      case 'ends-with':
        compareFn = (a: string, b: string) => a.endsWith(b);
        break;
      default:
        compareFn = (a: string, b: string) => a.includes(b);
        break;
    }
    this.condition = (val: string) => {
      val = val ?? '';
      return compareFn(val.toLowerCase(), this.pattern.toLowerCase());
    };
  }
}
