import { Filter } from "../../data-manager.lib";
import { FilterConf } from "../../data-manager.types";

export class SelectionFilter extends Filter {
  values: any[] = []; //set of values to search in
  valuesToFind: any[] = [];  // subset of values to find

  constructor(filterConf: FilterConf) {
    super(filterConf);
    this.values = filterConf.values;
  }

  override condition = (value: any): boolean => {
    if (!this.valuesToFind || this.valuesToFind.length === 0) return true;
    return this.valuesToFind.map((v: any) => v.toLowerCase()).includes(value.toLowerCase());
  };

  clean() {
    this.valuesToFind = [];
  }
}
