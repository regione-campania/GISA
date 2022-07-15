//type FilterType = 'text' | 'number' | 'date' | 'selection';

//dev only
type FilterType = string;

interface FilterConf {
  field: string; //the field this filter controls
  type: FilterType;
  values?: any; //used for selection filters
}

type TextFilterOption =
  | 'contains'
  | 'not-contains'
  | 'starts-with'
  | 'ends-with';

type DateFilterOption = 'exact-date' | 'from-date' | 'till-date' | 'range';

interface DataReflex {
  p: number; //p: pointer
  v: boolean; //v: visibility
}

interface DataPage {
  content: DataReflex[];
  pageNumber: number;
}

export {
  FilterConf,
  FilterType,
  DataReflex,
  DataPage,
  TextFilterOption,
  DateFilterOption,
};
