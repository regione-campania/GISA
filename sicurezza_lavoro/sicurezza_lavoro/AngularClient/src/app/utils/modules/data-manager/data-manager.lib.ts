import { Filter } from './controls/filters/filter';
import { TextFilter } from './controls/filters/text-filter';
import { DataManagerControl } from './controls/data-manager-control';
import { DataPaginator } from './controls/data-paginator/data-paginator';
import { DataReflex } from './data-manager.types';
import { DateFilter } from './controls/filters/date-filter';
import { SelectionFilter } from './controls/filters/selection-filter';

function reflexMapping(_: any, index: number): DataReflex {
  return { p: index, v: true };
}

function reflexPointerMapping(reflex: DataReflex) {
  return reflex.p;
}

function intersect<T>(a: Set<T>, b: Set<T>) {
  const intersection = new Set<T>();
  for (let el of b) if (a.has(el)) intersection.add(el);
  return intersection;
}

function intersectArray<T>(a: Array<T>, b: Array<T>) {
  const intersection = new Array<T>();
  for (let el of b) if (a.includes(el)) intersection.push(el);
  return intersection;
}

function normalizeDate(date: Date) {
  let temp = date;
  temp.setHours(0, 0, 0, 0);
  return temp;
}

export {
  reflexMapping,
  reflexPointerMapping,
  normalizeDate,
  intersect,
  intersectArray,
  DataManagerControl,
  Filter,
  TextFilter,
  DateFilter,
  SelectionFilter,
  DataPaginator,
};
