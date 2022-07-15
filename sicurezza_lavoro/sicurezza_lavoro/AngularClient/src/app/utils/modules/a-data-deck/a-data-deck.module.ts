import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { UtilsModule } from "../../utils.module";
import { DataManagerModule } from "../data-manager/data-manager.module";
import { ADataDeckComponent } from "./a-data-deck.component";
import { ADeckFilterComponent } from './controls/a-deck-filter/a-deck-filter.component';
import { ADeckPaginatorComponent } from './controls/a-deck-paginator/a-deck-paginator.component';
import { ADeckTextFilterComponent } from './controls/a-deck-filter/a-deck-text-filter/a-deck-text-filter.component';
import { FormsModule } from "@angular/forms";
import { ADeckDateFilterComponent } from './controls/a-deck-filter/a-deck-date-filter/a-deck-date-filter.component';
import { ADeckSelectionFilterComponent } from "./controls/a-deck-filter/a-deck-selection-filter/a-deck-selection-filter.component";

@NgModule({
  declarations: [
    ADataDeckComponent,
    ADeckFilterComponent,
    ADeckPaginatorComponent,
    ADeckTextFilterComponent,
    ADeckDateFilterComponent,
    ADeckSelectionFilterComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    DataManagerModule,
    UtilsModule
  ],
  exports: [
    ADataDeckComponent
  ]
})
export class ADataDeckModule {}
