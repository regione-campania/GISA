/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IspezioniRoutingModule } from './ispezioni-routing.module';
import { IspezioniComponent } from './ispezioni.component';
import { ListaIspezioniComponent } from './lista-ispezioni/lista-ispezioni.component';
import { ListaMacchinariComponent } from './lista-macchinari/lista-macchinari.component';
import { IspezioniService } from './ispezioni.service';
import { UtilsModule } from '../utils/utils.module';
import { ATableModule } from '../utils/modules/a-table/a-table.module';
import { AFormModule } from '../utils/modules/a-form/a-form.module';
import { AnagraficaModule } from '../anagrafica/anagrafica.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NuovaIspezioneComponent } from './nuova-ispezione/nuova-ispezione.component';


@NgModule({
  declarations: [
    IspezioniComponent,
    ListaIspezioniComponent,
    ListaMacchinariComponent,
    NuovaIspezioneComponent
  ],
  imports: [
    CommonModule,
    IspezioniRoutingModule,
    AnagraficaModule,
    UtilsModule,
    ATableModule,
    AFormModule
  ],
  providers: [
    IspezioniService
  ]
})
export class IspezioniModule { }
