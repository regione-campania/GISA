/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { Injectable, Type } from "@angular/core";
import { FormCantiereComponent } from "./forms-prototypes/form-cantiere/form-cantiere.component";
import { FormFaseIspezioneComponent } from "./forms-prototypes/form-fase-ispezione/form-fase-ispezione.component";
import { FormIspezioneComponent } from "./forms-prototypes/form-ispezione/form-ispezione.component";
import { FormRicercaCantiereComponent } from "./forms-prototypes/form-ricerca-cantiere/form-ricerca-cantiere.component";
import { FormRicercaImpresaComponent } from "./forms-prototypes/form-ricerca-impresa/form-ricerca-impresa.component";

@Injectable()
export class AFormFactory {

    [formName: string]: Type<any>

    get formIspezione() {
        return FormIspezioneComponent
    }

    get formFaseIspezione() {
        return FormFaseIspezioneComponent
    }

    get formCantiere() {
        return FormCantiereComponent
    }

    get formRicercaCantiere() {
        return FormRicercaCantiereComponent
    }

    get formRicercaImpresa() {
        return FormRicercaImpresaComponent
    }
}