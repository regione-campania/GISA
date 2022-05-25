/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { animate, style, transition, trigger } from '@angular/animations';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { IspezioniService } from 'src/app/ispezioni/ispezioni.service';
import { UserService } from 'src/app/user/user.service';
import { AbstractForm } from '../abstract-form';

@Component({
  selector: 'form-ricerca-cantiere',
  templateUrl: './form-ricerca-cantiere.component.html',
  styleUrls: ['./form-ricerca-cantiere.component.scss'],
  animations: [
    trigger('collapse', [
      transition(':enter', [
        style({ opacity: 0, height: 0, overflow: 'hidden' }),
        animate('500ms', style({ opacity: 1, height: 'auto' })),
      ]),
      transition(':leave', [
        style({ opacity: '*', height: '*', overflow: 'hidden' }),
        animate('500ms', style({ opacity: 0, height: 0 }))
      ])
    ])
  ]
})
export class FormRicercaCantiereComponent extends AbstractForm implements OnInit {

  @ViewChild('ricercaImpresa') formRicercaImpresa: any

  name = 'formRicercaCantiere'

  cantieriAttivi: any
  cantiereSelezionato: any
  impreseCantiereSelezionato: any

  constructor(private fb: FormBuilder, private ispezioni: IspezioniService, private us: UserService) { 
    super()
  }

  ngOnInit(): void {
    this.reset()
    this.ispezioni.getCantieriAttivi().subscribe(res => {
      this.cantieriAttivi = res
    })
  }

  reset() {
    this.cantiereSelezionato = this.impreseCantiereSelezionato = null
    this.form = this.fb.group({cantiere: this.fb.group({cun: '', cuc: '', denominazione: ''}), imprese: []})
  }

  selezionaCantiere(cantiere: any) {
    console.log(cantiere)
    this.cantiereSelezionato = cantiere
    this.form = this.fb.group({})
    this.form.addControl('cantiere', this.fb.group(this.cantiereSelezionato))
    this.us.getNotificaInfo(cantiere.id_notifica).subscribe(res => {
      console.log(res.cantiere_imprese)
      this.impreseCantiereSelezionato = res.cantiere_imprese
      this.form.addControl('imprese', this.fb.array(this.impreseCantiereSelezionato))
    })
  }

  addingImpresa = false

  //warning!: la struttura delle imprese di un cantiere è diversa da quelle presenti in formRicercaImpresa
  aggiungiImpresa() {
    (this.form.get('imprese') as FormArray).push(this.fb.control(this.formRicercaImpresa.impresaSelezionata))
    this.impreseCantiereSelezionato.push(this.formRicercaImpresa.impresaSelezionata)
    console.log(this.formRicercaImpresa)
    console.log(this.form.value)
    this.formRicercaImpresa.reset()
    this.addingImpresa = false
  }

  rimuoviImpresa(impresa: any) {
    this.impreseCantiereSelezionato = this.impreseCantiereSelezionato.filter((imp: any) => imp !== impresa)
    this.form.setControl('imprese', this.fb.array(this.impreseCantiereSelezionato))
    console.log(this.form.value)
  }



}
