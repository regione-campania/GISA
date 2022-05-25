/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { VerbaleDiSopralluogoComponent } from './verbale-di-sopralluogo/verbale-di-sopralluogo.component';
import { VerbaliService } from './verbali.service';
import { NotificaComponent } from './notifica/notifica.component';
import { UtilsModule } from '../utils/utils.module';
import { AnagraficaModule } from '../anagrafica/anagrafica.module';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { ATableModule } from '../utils/modules/a-table/a-table.module';


@NgModule({
  declarations: [
    VerbaleDiSopralluogoComponent,
    NotificaComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    UtilsModule,
    AnagraficaModule,
    NgbDropdownModule,
    ATableModule
  ],
  providers: [VerbaliService],
  exports: [
    NotificaComponent
  ]
})
export class VerbaliModule { }
