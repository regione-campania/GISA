import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { IspezioniService } from 'src/app/ispezioni/ispezioni.service';
import { AbstractForm } from '../abstract-form';
import { ListaIspezioniComponent } from 'src/app/ispezioni/lista-ispezioni/lista-ispezioni.component';


declare let Swal: any;

@Component({
  selector: 'form-fase-ispezione',
  templateUrl: './form-fase-ispezione.component.html',
  styleUrls: ['./form-fase-ispezione.component.scss'],
})
export class FormFaseIspezioneComponent extends AbstractForm implements OnInit {

  name = 'formFaseIspezione';
  fase: any;
  impreseSelezionabili: any;
  currEsito: any;
  esitiFase?: any;
  moduli: any;
  currModulo: any;
  idFase: any;
  verbaleWindow: any;

  constructor(private fb: FormBuilder, private ispService: IspezioniService, public li: ListaIspezioniComponent) {
    super();
  }

  ngOnInit() {
    this.ispService
      .getNuovaFase(this.data.ispezione.id_ispezione)
      .subscribe((nf: any) => {
        if (!nf.esito) {
          Swal.fire({ text: nf.msg, icon: 'error' });
          return;
        }
        this.idFase = nf.valore;
        this.ispService.getFaseInfo(this.idFase).subscribe((res: any) => {
          this.impreseSelezionabili = res.imprese_selezionabili;
          this.fase = res.fase;
          this.form = this.fb.group({
            fase: this.fb.group(res.fase),
            persone_sanzionate: this.fb.array(res.persone_sanzionate),
            verbali: this.fb.array(res.verbali),
          });
        });
      });
    this.ispService
      .getEsitiFase(this.data.ispezione.id_ispezione)
      .subscribe((res) => {
        this.esitiFase = res;
        console.log(this.esitiFase);
      });
    this.ispService.getModuli().subscribe((res) => {
      this.moduli = res;
    });
  }

  fixPicker(event: any) {
    event.target.showPicker();
  }

  compilaVerbale(idModulo: any) {
    if (idModulo == null) {
      Swal.fire({
        text: 'Selezionare tipo verbale da compilare',
        icon: 'warning',
      });
      return;
    }
    console.log(idModulo);
    var url = this.moduli.find((m: any) => m.id == idModulo).url;
    console.log(url);
    if (url != null) {
      if (this.fase.idVerbale == null) {
        this.ispService
          .insVerbale(idModulo, this.idFase)
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
        this.openVerbaleWindow(idModulo, this.fase.idVerbale, url);
      }
      // window.open(url + `idVerbale=`);
    }
  }

  eliminaVerbale(idVerbale: any) {
    console.log(idVerbale);
    this.ispService.deleteVerbale(idVerbale).subscribe((res: any) => {
      console.log(res);
      if (res.esito) {
        this.fase.idVerbale = null;
        this.verbaleWindow.close();
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
      this.fase.idModulo = message.idModulo;
      this.fase.idVerbale = message.idVerbale;
    });
  }
}
