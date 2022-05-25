/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ATableFilterComponent } from './a-table-filter/a-table-filter.component';
import { ATableTextFilterComponent } from './a-table-filter/a-table-text-filter/a-table-text-filter.component';
import { ATableDateFilterComponent } from './a-table-filter/a-table-date-filter/a-table-date-filter.component';
import { ATableSorterDirective } from './a-table-sorter.directive';
import { ATablePaginatorComponent } from './a-table-paginator/a-table-paginator.component';
import { ATableSelectionFilterComponent } from './a-table-filter/a-table-selection-filter/a-table-selection-filter.component';
import { UtilsModule } from '../../utils.module';
import { ASmartTableDirective } from './a-smart-table.directive';




@NgModule({
  declarations: [
    ATableSorterDirective,
    ATableFilterComponent,
    ATableTextFilterComponent,
    ATableDateFilterComponent,
    ATablePaginatorComponent,
    ATableSelectionFilterComponent,
    ASmartTableDirective
  ],
  imports: [
    CommonModule,
    UtilsModule
  ],
  exports: [
    ATableSorterDirective,
    ATableFilterComponent,
    ATablePaginatorComponent,
    ASmartTableDirective
  ]
})
export class ATableModule { }
