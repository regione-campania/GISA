/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { AbstractForm } from '../abstract-form';
import { AnagraficaService } from 'src/app/anagrafica/anagrafica.service';

@Component({
  selector: 'form-ricerca-impresa',
  templateUrl: './form-ricerca-impresa.component.html',
  styleUrls: ['./form-ricerca-impresa.component.scss']
})
export class FormRicercaImpresaComponent extends AbstractForm implements OnInit {

  @Input() header = 'Impresa'

  name = 'formRicercaImpresa'
  imprese: any
  impresaSelezionata: any

  constructor(private fb: FormBuilder, private anagrafiche: AnagraficaService) { 
    super()
  }

  ngOnInit(): void {
    this.reset()
    this.anagrafiche.getImpreseSedi().subscribe(res => {
      this.imprese = res
    })
  }

  selezionaImpresa(impresa: any) {
    this.impresaSelezionata = impresa
    console.log(impresa)
    this.form = this.fb.group(this.impresaSelezionata)
  }

  reset() {
    this.impresaSelezionata = null
    this.form = this.fb.group({ragione_sociale: '', partita_iva: '', codice_fiscale: ''})
  }

}
