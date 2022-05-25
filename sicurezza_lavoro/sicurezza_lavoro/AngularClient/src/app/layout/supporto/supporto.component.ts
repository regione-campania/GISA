/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { Component, OnInit } from '@angular/core';
import { FormBuilder} from '@angular/forms';
import { SupportoService} from './supporto.service';
declare let Swal: any

@Component({
  selector: 'app-supporto',
  templateUrl: './supporto.component.html',
  styleUrls: ['./supporto.component.scss']
})
export class SupportoComponent implements OnInit {

  formSupporto = this.formBuilder.group({
    nomeSegnalante: '',
    emailSegnalante: '',
    telefonoSegnalante: '',
    titolo: '',
    messaggio: ''
  });

  constructor(
    private formBuilder: FormBuilder,
    private ss: SupportoService
  ) { }

  ngOnInit(): void {
  }

  onSubmit(): void{
    let valori = this.formSupporto.value;
    console.log(valori)
    if(valori.nomeSegnalante.trim() == ''){
      Swal.fire({ text: "Valorizzare nome e cognome!", icon: 'warning' });
      return;    
    }
    if(valori.telefonoSegnalante.trim() == '' && valori.emailSegnalante.trim() == ''){
      Swal.fire({ text: "Valorizzare almeno uno tra email e telefono di ricontatto!", icon: 'warning' });
      return;    
    }
    if(valori.titolo.trim() == ''){
      Swal.fire({ text: "Valorizzare titolo!", icon: 'warning' });
      return;    
    }
    if(valori.messaggio.trim() == ''){
      Swal.fire({ text: "Valorizzare descrizione problema!", icon: 'warning' });
      return;    
    }
    try{
      this.ss.sendEmail(this.formSupporto.value).subscribe();
      Swal.fire({ text: "Messaggio inoltrato con successo!", icon: 'success' });
      //this.formSupporto.resetForm();
    }catch(err){
      Swal.fire({ text: "Errore nell'inoltro della segnalazione", icon: 'error' });
      return;
    }
  }

}
