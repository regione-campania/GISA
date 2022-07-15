/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { Component, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { AnagraficaService } from "src/app/anagrafica/anagrafica.service";
import { IspezioniService } from "src/app/ispezioni/ispezioni.service";
import { AbstractForm } from "../abstract-form";

@Component({
    selector: 'form-ispezione',
    templateUrl: './form-ispezione.component.html',
    styleUrls: ['./form-ispezione.component.scss']
})
export class FormIspezioneComponent extends AbstractForm implements OnInit {

    name = 'formIspezione'

    entiUopTree: any
    entiUopTreeChildren: any
    aslPerContoDi: any
    aslPerContoDiChildren: any
    motivi: any

    constructor(private fb: FormBuilder, private anagrafiche: AnagraficaService, private ispezioni: IspezioniService) {
        super()
    }

    ngOnInit(): void {
        console.log(this.data)
        this.anagrafiche.getEntiUnitaOperativeTree().subscribe(res => {
            this.entiUopTree = res
            this.aslPerContoDi = res

            this.entiUopTree = this.entiUopTree.filter((ente: any) => ente.id_asl === null)
            this.entiUopTreeChildren = this.entiUopTree.flatMap((ente: any) => ente.children)

            this.aslPerContoDi = this.aslPerContoDi.filter((ente: any) => ente.id_asl !== null)
            this.aslPerContoDiChildren = this.aslPerContoDi.flatMap((ente: any) => ente.children)

            console.log(this.aslPerContoDi)

        })
        this.ispezioni.getMotiviIspezione().subscribe(motivi => {
            this.motivi = motivi
            this.motivi.sort((a: any, b: any) => {
                return a.descr > b.descr ? 1 : (a.descr < b.descr ? -1 : 0)
            })
        })
        /* this.form = this.fb.group({
            cantiere: this.fb.group(this.data.ispezioneCluster.cantiere),
            fasi: this.fb.array(this.data.ispezioneCluster.fasi),
            imprese: this.fb.array(this.data.ispezioneCluster.imprese),
            ispezione: this.fb.group(this.data.ispezioneCluster.ispezione),
            nucleo_ispettivo: this.fb.array(this.data.ispezioneCluster.nucleo_ispettivo),
            stati_ispezione_successivi: this.fb.array(this.data.ispezioneCluster.stati_ispezione_successivi)
        }) */
        this.form = this.fb.group({
            cantiere: this.fb.group(this.data.cantiere),
            fasi: this.fb.array(this.data.fasi),
            imprese: this.fb.array(this.data.imprese),
            ispezione: this.fb.group(this.data.ispezione),
            nucleo_ispettivo: this.fb.array(this.data.nucleo_ispettivo),
            stati_ispezione_successivi: this.fb.array(this.data.stati_ispezione_successivi)
        })
    }

}