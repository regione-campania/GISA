import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AppService } from 'src/app/app.service';
import { Utils } from 'src/app/utils/utils.class';
import { VerbaliService } from 'src/app/verbali/verbali.service';
import { IspezioniService } from '../ispezioni.service';
declare let Swal: any;

@Component({
  selector: 'app-lista-ispezioni',
  templateUrl: './lista-ispezioni.component.html',
  styleUrls: ['./lista-ispezioni.component.scss'],
})
export class ListaIspezioniComponent implements OnInit {
  @ViewChild('bodyTemplate') bodyTemplateRef?: TemplateRef<any>;
  @ViewChild('table') tableRef?: any;

  ispezioni: any;
  ispezioneAttiva: any;
  faseAttiva: any;
  dettaglioIspezioneModal?: NgbModalRef;
  formIspezioneModal?: NgbModalRef;
  entiDisponibili: any[] = [];
  aslDisponibili: any[] = [];
  motiviDisponibili: any[] = [];
  statiDisponibili: any[] = [];
  moduli: any;
  verbaleWindow: any;
  currModulo: any;

  form = this.fb.group({});

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
    this.is.getIspezioni().subscribe((res) => {
      this.ispezioni = res;
      this.ispezioni.forEach((isp: any) => {
        if (!this.entiDisponibili.includes(isp.descr_ente_isp))
          this.entiDisponibili.push(isp.descr_ente_isp);
        if (!this.aslDisponibili.includes(isp.descr_ente))
          this.aslDisponibili.push(isp.descr_ente);
        if (!this.statiDisponibili.includes(isp.descr_stato_ispezione))
          this.statiDisponibili.push(isp.descr_stato_ispezione);
      });
    });
    this.is.getMotiviIspezione().subscribe((motivi: any) => {
      motivi.forEach((m: any) => {
        this.motiviDisponibili.push(m.descr);
      });
    });
    this.is.getModuli().subscribe((res) => {
      this.moduli = res;
    });
  }

  openDettaglioIspezione(content: any, data: any) {
    this.ispezioneAttiva = data;
    this.dettaglioIspezioneModal = this.modalEngine.open(content, {
      centered: true,
      size: 'xl',
      modalDialogClass: 'system-modal',
    });
    this.is
      .getIspezioneInfo(this.ispezioneAttiva.id_ispezione)
      .subscribe((isp: any) => {
        isp.ispezione.data_ispezione = Utils.fromISOTimeToLocaleDate(
          isp.ispezione.data_ispezione
        );
        isp.fasi.forEach((fase: any) => {
          if (fase.altro_esito != null)
            fase.altro_esito = `(${fase.altro_esito})`;
          fase.data_fase = Utils.fromISOTimeToLocaleDate(fase.data_fase);
        });
        this.ispezioneAttiva = isp;
        console.log(this.ispezioneAttiva);
      });
  }

  getFaseInfo(idFase: any) {
    this.is.getFaseInfo(idFase).subscribe((res: any) => {
      console.log(res);
      res.fase.data_fase = Utils.fromISOTimeToLocaleDate(res.fase.data_fase);
      if (res.fase.altro_esito != null)
        res.fase.altro_esito = `(${res.fase.altro_esito})`;
      res.verbali.forEach((verbale: any) => {
        verbale.data = Utils.fromISOTimeToLocaleTime(verbale.data);
      });
      this.faseAttiva = res;
    });
  }

  exportTable(full = true): void {
    let table = document.getElementById('tabella-ispezioni')
      ?.firstChild as HTMLTableElement;
    if (!table) throw new Error('No table found.');
    if (full) {
      let temp = document.createElement('table');
      let row: HTMLTableRowElement, cell: HTMLTableCellElement;
      let head = temp.createTHead();
      Array.from(table.tHead!.rows).forEach(r => {
        row = head.insertRow();
        Array.from(r.cells!).forEach(c => {
          cell = row.insertCell();
          cell.innerText = c.innerText;
        })
      })
      let body = temp.createTBody();
      this.tableRef?.content.forEach((isp: any) => {
        row = body.insertRow();
        for (let p of [
          'codice_ispezione',
          'descr_isp',
          'descr_ente_isp',
          'descr_uo_isp',
          'descr_motivo_isp',
          'cantiere_o_impresa',
          'data_ispezione',
          'descr_ente',
          'descr_stato_ispezione',
        ]) {
          cell = row.insertCell();
          cell.innerText = isp[p];
        }
      });
      table = temp;
    }
    Utils.exportTable(table, {
      predicate: (r) => !r.hidden && !r.className.includes('paginator-wrapper'),
    });
  }

  insertFase(fase: any) {
    console.log(fase);
    this.is.updateFaseInfo(fase).subscribe((ret: any) => {
      if (!ret.esito) {
        Swal.fire({ text: ret.msg.split(' [')[0], icon: 'warning' });
        return;
      }
      this.modalEngine.dismissAll();
    });
  }

  scaricaVerbale(idVerbale: any) {
    console.log(idVerbale);
    if (idVerbale != undefined) {
      Utils.showSpinner(true);
      this.vs.getJsonVerbale(idVerbale).subscribe((json) => {
        let dataToSend = JSON.parse(json.get_verbale_valori);
        console.log(dataToSend);
        this.vs.downloadVerbale(dataToSend).subscribe(
          (data) => {
            Utils.download(data, `${dataToSend.numero_verbale}.pdf`);
            Utils.showSpinner(false);
          },
          (err) => {
            Swal.fire({
              text: 'Errore nella generazione del PDF!',
              icon: 'error',
            });
            Utils.showSpinner(false);
            console.error(err);
          }
        );
      });
    }
  }

  compilaVerbale(idModulo: any) {
    if (idModulo == null || idModulo == 'null') {
      Swal.fire({
        text: 'Selezionare tipo verbale da compilare',
        icon: 'warning',
      });
      return;
    }
    console.log(this.faseAttiva);
    console.log(idModulo);
    var url = this.moduli.find((m: any) => m.id == idModulo).url;
    console.log(url);
    if (url != null) {
      if (this.faseAttiva.verbali.length <= 0) {
        this.is
          .insVerbale(idModulo, this.faseAttiva.fase.id)
          .subscribe((res: any) => {
            console.log(res);
            if (!res.esito) {
              Swal.fire({ text: res.msg, icon: 'error' });
              return;
            }
            var idVerbale = res.valore;
            this.openVerbaleWindow(idModulo, idVerbale, url);
          });
      } else {
        this.openVerbaleWindow(
          idModulo,
          this.faseAttiva.verbali[0].id_verbale,
          url
        );
      }
      // window.open(url + `idVerbale=`);
    }
  }

  eliminaVerbale(idVerbale: any) {
    console.log(idVerbale);
    this.is.deleteVerbale(idVerbale).subscribe((res: any) => {
      console.log(res);
      if (res.esito) {
        this.faseAttiva.verbali[0].id_verbale = null;
        if (this.verbaleWindow != null) this.verbaleWindow.close();
        this.getFaseInfo(this.faseAttiva.fase.id);
      }
    });
  }

  openVerbaleWindow(idModulo: any, idVerbale: any, url: any) {
    this.verbaleWindow = window.open(
      url + `?idVerbale=${idVerbale}&idModulo=${idModulo}`,
      'Verbale',
      'toolbar=no,scrollbars=yes,resizable=yes,top=100,left=100,width=1200,height=800'
    );
    window.addEventListener('message', (event) => {
      //al salvataggio del verbale viene inviato il postmessage con le info del verbale salvato.
      var message = JSON.parse(event.data);
      //this.faseAttiva.verbali[0].id_modulo = message.idModulo;
      //this.faseAttiva.verbali[0].id_verbale = message.idVerbale;
      this.verbaleWindow.close();
      this.getFaseInfo(this.faseAttiva.fase.id);
    });
  }
}
