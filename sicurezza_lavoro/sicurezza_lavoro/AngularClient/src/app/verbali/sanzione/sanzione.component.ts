import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-sanzione',
  templateUrl: './sanzione.component.html',
  styleUrls: ['./sanzione.component.scss']
})
export class SanzioneComponent implements OnInit {

  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      info_pagamento: this.fb.group({
        ridotto: this.fb.control(''),
        ultra_ridotto: this.fb.control('')
      }),
      info_pagatore: this.fb.group({
        trasgressore: this.fb.group({
          tipo_pagatore: this.fb.control('G'),
          p_iva_o_cod_fiscale: this.fb.control(''),
          rag_sociale_o_nominativo: this.fb.control(''),
          indirizzo: this.fb.control(''),
          civico: this.fb.control(''),
          cap: this.fb.control(''),
          comune: this.fb.control(''),
          cod_provincia: this.fb.control(''),
          nazione: this.fb.control(''),
          email: this.fb.control(''),
          telefono: this.fb.control(''),
          mod_contestazione: this.fb.control('')
        }),
        obbligato_in_solido: this.fb.group({
          tipo_pagatore: this.fb.control('G'),
          p_iva_o_cod_fiscale: this.fb.control(''),
          rag_sociale_o_nominativo: this.fb.control(''),
          indirizzo: this.fb.control(''),
          civico: this.fb.control(''),
          cap: this.fb.control(''),
          comune: this.fb.control(''),
          cod_provincia: this.fb.control(''),
          nazione: this.fb.control(''),
          email: this.fb.control(''),
          telefono: this.fb.control(''),
          mod_contestazione: this.fb.control('')
        })
      })
    });
  }

  ngOnInit(): void {
    console.log(this.form.value)
  }

}
