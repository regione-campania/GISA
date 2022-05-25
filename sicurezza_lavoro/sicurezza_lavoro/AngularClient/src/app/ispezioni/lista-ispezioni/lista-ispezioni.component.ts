/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AppService } from 'src/app/app.service';
import { Utils } from 'src/app/utils/utils.class';
import { VerbaliService } from 'src/app/verbali/verbali.service';
import { IspezioniService } from '../ispezioni.service';
declare let Swal: any

@Component({
  selector: 'app-lista-ispezioni',
  templateUrl: './lista-ispezioni.component.html',
  styleUrls: ['./lista-ispezioni.component.scss']
})
export class ListaIspezioniComponent implements OnInit {

  ispezioni: any
  buffer: any[] = []
  ispezioneAttiva: any
  faseAttiva: any
  dettaglioIspezioneModal?: NgbModalRef
  formIspezioneModal?: NgbModalRef
  entiDisponibili: any[] = []
  aslDisponibili: any[] = []
  motiviDisponibili: any[] = []
  statiDisponibili: any[] = []

  form = this.fb.group({})


  constructor(
      public is: IspezioniService,
      private modalEngine: NgbModal,
      private vs: VerbaliService,
      private fb: FormBuilder,
      private app: AppService
      ) {
        this.app.pageName = 'Ispezioni';
      }

  ngOnInit(): void {
    this.is.getIspezioni().subscribe(res => {
      this.ispezioni = res
      this.buffer = this.ispezioni.slice(0,100)
      this.buffer.forEach(isp => {
        if(!this.entiDisponibili.includes(isp.descr_ente_isp))
          this.entiDisponibili.push(isp.descr_ente_isp)
        if(!this.aslDisponibili.includes(isp.descr_ente))
          this.aslDisponibili.push(isp.descr_ente)
        if(!this.statiDisponibili.includes(isp.descr_stato_ispezione))
          this.statiDisponibili.push(isp.descr_stato_ispezione)
      })
      console.log(this.buffer)
    })
    this.is.getMotiviIspezione().subscribe((motivi: any) => {
      motivi.forEach((m: any) => {
        this.motiviDisponibili.push(m.descr)
      })
    })
  }

  openDettaglioIspezione(content: any, data: any) {
    this.ispezioneAttiva = data
    this.dettaglioIspezioneModal = this.modalEngine.open(content, {centered: true, size: 'xl', modalDialogClass: 'system-modal'})
    this.is.getIspezioneInfo(this.ispezioneAttiva.id_ispezione).subscribe((isp: any) => {
      isp.ispezione.data_ispezione = Utils.fromISOTimeToLocaleDate(isp.ispezione.data_ispezione);
      isp.fasi.forEach((fase: any) => {
        if(fase.altro_esito != null)
          fase.altro_esito = `(${fase.altro_esito})`;
        fase.data_fase = Utils.fromISOTimeToLocaleDate(fase.data_fase);
      })
      this.ispezioneAttiva = isp;
      console.log(this.ispezioneAttiva)
    })
  }

  getFaseInfo(idFase: any) {
    this.is.getFaseInfo(idFase).subscribe((res: any) => {
      console.log(res);
      res.fase.data_fase = Utils.fromISOTimeToLocaleDate(res.fase.data_fase);
      if(res.fase.altro_esito != null)
        res.fase.altro_esito = `(${res.fase.altro_esito})`
      res.verbali.forEach((verbale: any) => {
          verbale.data = Utils.fromISOTimeToLocaleTime(verbale.data);
      })
      this.faseAttiva = res
    })
  }

  /* openFormFase(idIspezione: any) {
    this.is.getNuovaFase(idIspezione).subscribe((esito: any) => {
      if(esito.esito)
        this.getFaseInfo(esito.valore)
    })
  } */

  exportTable(): void {
    const table = document.getElementById('tabella-ispezioni') as HTMLTableElement
    if(!table)
      throw 'No table found.'
    Utils.exportTable(table, {
      predicate: r => !r.hidden && !r.className.includes('paginator')
    })
  }


  insertFase(fase: any){
    console.log(fase);
    this.is.updateFaseInfo(fase).subscribe((ret:any) => {
      if(!ret.esito){
        Swal.fire({ text: ret.msg.split(' [')[0], icon: 'warning' });
        return;
      }
      this.modalEngine.dismissAll();
    })
  }

  scaricaVerbale(idVerbale: any){
    console.log(idVerbale)
    if(idVerbale != undefined){
      Utils.showSpinner(true);
      this.vs.getJsonVerbale(idVerbale).subscribe(json =>{
        let dataToSend = JSON.parse(json.get_verbale_valori);
        console.log(dataToSend);
        this.vs.downloadVerbale(dataToSend).subscribe(
          data => {
            Utils.download(data, `${dataToSend.numero_verbale}.pdf`)
            Utils.showSpinner(false);
          },
          err => {
            Swal.fire({ text: "Errore nella generazione del PDF!", icon: 'error' });
            Utils.showSpinner(false);
            console.error(err);
          }
        )
      })
    }


  }

}
