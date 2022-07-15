import { DataManagerService } from "../data-manager.service";

export abstract class DataManagerControl {
  //DataManagerService this control belongs (assigned when created with DataManagerService)

  constructor(public dm: DataManagerService) {
    console.log('--- contructing a DataManagerControl ---');
  }
}
