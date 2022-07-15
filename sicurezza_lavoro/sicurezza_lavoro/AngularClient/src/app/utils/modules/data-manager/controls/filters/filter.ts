import { DataManagerControl, intersect, intersectArray, reflexPointerMapping } from "../../data-manager.lib";
import { DataManagerService } from "../../data-manager.service";
import { DataReflex, FilterConf, FilterType } from "../../data-manager.types";

//base (abstract) class for typed data filters
export abstract class Filter {
  field: string;
  type: FilterType;
  affected: DataReflex[] = [];
  discarded: DataReflex[] = [];
  dm!: DataManagerService;

  condition: (el: any) => boolean = function (_: any) {
    return true;
  };

  constructor(filterConf: FilterConf) {
    this.field = filterConf.field;
    this.type = filterConf.type;
  }

  apply() {
    this.reset();
    this.dm.data.forEach((d: any, i: number) => {
      if (this.condition(d[this.field])) 
        this.affected.push({ p: i, v: true });
      else this.discarded.push({ p: i, v: true });
    });
    this.OnChange();
  }

  reset() {
    this.affected = [];
    this.discarded = [];
    this.OnChange();
  }

  abstract clean(): void;

  protected OnChange() {
    const activeFilters = this.dm.activeFilters;
    if (activeFilters.length === 0) {
      //resets visibility to all elements
      this.dm.reflex.forEach((r: DataReflex) => (r.v = true));
    } else {
      //shows only elements that meet condition of all filters
      let intersection = activeFilters[0].affected.map(reflexPointerMapping);
      for (let i = 1; i < activeFilters.length; i++) {
        intersection = intersectArray(intersection, activeFilters[i].affected.map(reflexPointerMapping));
      }
      this.dm.reflex.forEach((r: DataReflex) => {
        r.v = intersection.includes(r.p)
      });
    }
    this.dm.reflexChange.emit();
  }

  get isActive() {
    return (
      (this.affected.length > 0 &&
        this.affected.length < this.dm.data.length) ||
      this.discarded.length > 0
    );
  }
}
