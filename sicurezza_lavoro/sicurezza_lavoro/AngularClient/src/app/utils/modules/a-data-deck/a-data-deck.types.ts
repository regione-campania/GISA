import { FilterConf } from "../data-manager/data-manager.types";

interface ADataDeckFilterConf extends FilterConf {
  label: string;   //label of the key to display
  values?: any;     //used with selection fiters
}

export { ADataDeckFilterConf };
