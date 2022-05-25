import { animate, style, transition, trigger } from '@angular/animations';
import { Location } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { AppService } from 'src/app/app.service';
import { FormIspezioneComponent } from 'src/app/utils/modules/a-form/forms-prototypes/form-ispezione/form-ispezione.component';
import { FormRicercaCantiereComponent } from 'src/app/utils/modules/a-form/forms-prototypes/form-ricerca-cantiere/form-ricerca-cantiere.component';
import { FormRicercaImpresaComponent } from 'src/app/utils/modules/a-form/forms-prototypes/form-ricerca-impresa/form-ricerca-impresa.component';
import { IspezioniService } from '../ispezioni.service';

declare let Swal: any;

const ANIMATION_TIMING = '600ms cubic-bezier(0.33, 1, 0.68, 1)';

@Component({
  selector: 'app-nuova-ispezione',
  templateUrl: './nuova-ispezione.component.html',
  styleUrls: ['./nuova-ispezione.component.scss'],
  animations: [
    trigger('formCantiereTrigger', [
      transition(':enter', [
        style({ left: '-100%', opacity: 0 }),
        animate(ANIMATION_TIMING, style({ left: 0, opacity: 1 })),
      ]),
      transition(':leave', [
        animate(ANIMATION_TIMING, style({ left: '-100%', opacity: 0 })),
      ]),
    ]),
    trigger('formImpresaTrigger', [
      transition(':enter', [
        style({ left: '100%', opacity: 0 }),
        animate(ANIMATION_TIMING, style({ left: 0, opacity: 1 })),
      ]),
      transition(':leave', [
        animate(ANIMATION_TIMING, style({ left: '100%', opacity: 0 })),
      ]),
    ]),
  ],
})
export class NuovaIspezioneComponent implements OnInit {
  @ViewChild('formIspezione') formIspezioneComponent?: FormIspezioneComponent;
  @ViewChild('formRicercaCantiere') formRicercaCantiere?: FormRicercaCantiereComponent;
  @ViewChild('formRicercaImpresa') formRicercaImpresa?: FormRicercaImpresaComponent;
  nuovaIspezione: any;

  constructor(
    private ispService: IspezioniService,
    private fb: FormBuilder,
    private location: Location,
    private app: AppService
  ) {
    this.app.pageName = 'Nuova Ispezione';
  }

  ngOnInit(): void {
    this.ispService.getNuovaIspezione(0).subscribe((ni: any) => {
      if (!ni.esito) {
        Swal.fire({ text: ni.msg, icon: 'error' });
        return;
      }
      this.ispService.getIspezioneInfo(ni.valore).subscribe((isp) => {
        console.log(isp);
        this.nuovaIspezione = isp;
      });
    });
  }

  onSubmit(ev: any) {
    ev.preventDefault();
    ev.stopPropagation();
    console.log(this.formIspezioneComponent?.form.value);
    if (
      this.formIspezioneComponent?.form.value.ispezione.cantiere_o_impresa.toUpperCase() ==
      'IMPRESA'
    ) {
      this.patchImprese(this.formRicercaImpresa?.form.value);
    } else {
      //CANTIERE
      this.patchCantiere(this.formRicercaCantiere?.form.value);
    }
    console.log(this.formIspezioneComponent?.form.value);
    if (
      this.formIspezioneComponent?.form.value.imprese[0].id_impresa_sede == null
    ) {
      Swal.fire({
        text: `Accertarsi di aver selezionato una o più imprese`,
        icon: 'warning',
      });
      return;
    }
    this.insertIspezione();
  }

  patchCantiere(patch: any) {
    console.log(patch);
    this.formIspezioneComponent?.form
      .get('cantiere')
      ?.patchValue(patch.cantiere);
    this.formIspezioneComponent?.form
      .get('ispezione')
      ?.patchValue(patch.cantiere);
    this.patchImprese(patch.imprese);
  }

  patchImprese(patch: any) {
    console.log(patch);
    const impresaModel = Object.assign(this.nuovaIspezione.imprese[0]);
    const formArray = this.fb.array([]);
    let newControl;
    if (Array.isArray(patch)) {
      patch.forEach((p: any) => {
        newControl = this.fb.group(impresaModel);
        newControl.patchValue(p);
        formArray.push(newControl);
      });
    } else {
      newControl = this.fb.group(impresaModel);
      newControl.patchValue(patch);
      formArray.push(newControl);
    }
    this.formIspezioneComponent?.form.setControl('imprese', formArray);
  }

  insertIspezione() {
    this.ispService
      .updateIspezione(this.formIspezioneComponent?.form.value)
      .subscribe((ret: any) => {
        if (!ret.esito) {
          Swal.fire({ text: ret.msg, icon: 'warning' });
          return;
        } else this.location.back();
      });
  }
}
