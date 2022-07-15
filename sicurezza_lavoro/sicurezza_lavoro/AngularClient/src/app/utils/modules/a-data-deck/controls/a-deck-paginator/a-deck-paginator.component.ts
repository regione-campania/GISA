import { Component, OnInit } from '@angular/core';
import { DataPaginator } from '../../../data-manager/data-manager.lib';
import { DataManagerService } from '../../../data-manager/data-manager.service';

@Component({
  selector: 'a-deck-paginator',
  templateUrl: './a-deck-paginator.component.html',
  styleUrls: ['./a-deck-paginator.component.scss']
})
export class ADeckPaginatorComponent {
  paginator: DataPaginator;

  constructor(protected dm: DataManagerService) {
    this.paginator = this.dm.createPaginator();
  }
}
