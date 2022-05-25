/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { UtilsModule } from '../../utils.module';
import { AFormFactory } from './a-form-factory.service';
import { FormFaseIspezioneComponent } from './forms-prototypes/form-fase-ispezione/form-fase-ispezione.component';
import { FormIspezioneComponent } from './forms-prototypes/form-ispezione/form-ispezione.component';
import { FormCantiereComponent } from './forms-prototypes/form-cantiere/form-cantiere.component';
import { FormRicercaCantiereComponent } from './forms-prototypes/form-ricerca-cantiere/form-ricerca-cantiere.component';
import { FormRicercaImpresaComponent } from './forms-prototypes/form-ricerca-impresa/form-ricerca-impresa.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';



@NgModule({
  declarations: [
    FormIspezioneComponent,
    FormFaseIspezioneComponent,
    FormCantiereComponent,
    FormRicercaCantiereComponent,
    FormRicercaImpresaComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    UtilsModule
  ],
  providers: [
    AFormFactory
  ],
  exports: [
    FormIspezioneComponent,
    FormFaseIspezioneComponent,
    FormCantiereComponent,
    FormRicercaCantiereComponent,
    FormRicercaImpresaComponent
  ]
})
export class AFormModule { }
