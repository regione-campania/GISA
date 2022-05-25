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