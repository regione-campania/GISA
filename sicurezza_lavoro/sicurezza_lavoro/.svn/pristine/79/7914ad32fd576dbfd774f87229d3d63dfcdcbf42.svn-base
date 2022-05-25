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
