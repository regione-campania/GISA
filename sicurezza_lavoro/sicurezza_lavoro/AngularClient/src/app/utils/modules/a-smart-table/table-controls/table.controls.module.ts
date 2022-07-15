import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ATablePaginatorComponent } from "./a-table-paginator/a-table-paginator.component";
import { ColumnControlsModule } from "./column-controls/column-controls.module";

@NgModule({
  declarations: [
    ATablePaginatorComponent
  ],
  imports: [
    CommonModule,
    ColumnControlsModule
  ],
  exports: [
    ATablePaginatorComponent,
    ColumnControlsModule
  ]
})
export class TableControlsModule {}
