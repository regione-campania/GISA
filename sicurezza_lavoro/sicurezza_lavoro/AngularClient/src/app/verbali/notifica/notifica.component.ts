/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AnagraficaService } from 'src/app/anagrafica/anagrafica.service';
import { AppService } from 'src/app/app.service';
import { Utils } from 'src/app/utils/utils.class';
import { UserService } from '../../user/user.service';
declare let Swal: any;

@Component({
  selector: 'app-notifica',
  templateUrl: './notifica.component.html',
  styleUrls: ['./notifica.component.scss'],
})
export class NotificaComponent implements OnInit {
  notice: any;
  readonly = true;
  readonlyCantiere = true;
  noticeForm = this.fb.group({});
  currNatura: any;
  anagrafica: any = {
    comuni: [],
    comuniCantiere: [],
    imprese: [],
    soggettiFisici: [],
  };
  role?: any;
  mobileView = window.innerWidth < 992;
  navigatorAvailable =
    window.location.protocol == 'https' ||
    window.location.href.includes('localhost'); //la funzione di geolocalizzazione è presente solo per siti https o localhost

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private us: UserService,
    private fb: FormBuilder,
    public anagraficaService: AnagraficaService,
    private app: AppService
  ) {
    this.app.pageName = 'Notifica Preliminare';
  }

  ngOnInit(): void {
    this.role = this.us.userRole;
    this.caricaCantiere(-1);
    this.anagraficaService
      .getComuni()
      .subscribe((c) => (this.anagrafica.comuni = c));
    this.anagraficaService
      .getComuniCantiere()
      .subscribe((c) => (this.anagrafica.comuniCantiere = c));
    this.anagraficaService
      .getImprese()
      .subscribe((imp) => (this.anagrafica.imprese = imp));
    this.anagraficaService
      .getSoggettiFisici()
      .subscribe((sf) => (this.anagrafica.soggettiFisici = sf));
  }

  caricaCantiere(nuovoStato: any) {
    //se viene passato il nuovo stato controllo se è un invio, se sì scarico
    this.route.queryParams.subscribe((params) => {
      this.readonly = params['mode'] === 'false';
      this.readonlyCantiere = params['modeCantiere'] === 'false';
      this.us.getNotificaInfo(params['idNotifica']).subscribe((n) => {
        console.log(n);
        //n.cantiere.data_presunta = new Date(n.cantiere.data_presunta).toLocaleDateString();
        //n.cantiere.data_presunta = Utils.fromDateToLocaleDate(new Date(n.cantiere.data_presunta));
        if (n.cantiere.data_presunta != null)
          n.cantiere.data_presunta = n.cantiere.data_presunta.split('T')[0];
        this.notice = n;
        this.loadFormValues();
        if (this.readonly) this.disableForm();
        if (nuovoStato.scaricabile_stato_prossimo) this.download(true);
        this.currNatura = n.cantiere.id_natura_opera;
      });
    });
  }

  loadFormValues() {
    let _n = this.notice;
    this.noticeForm = this.fb.group({
      cantiere: this.fb.group(_n.cantiere),
      cantiere_imprese: this.fb.array(
        _n.cantiere_imprese.map((imp: any) => this.fb.group(imp))
      ),
      persona_ruoli: this.fb.array(
        _n.persona_ruoli.map((per: any) => this.fb.group(per))
      ),
      notifiche_prec: this.fb.array(
        _n.notifiche_prec.map((prec: any) => this.fb.group(prec))
      ),
      stati_successivi: this.fb.array(
        _n.stati_successivi.map((stat: any) => this.fb.group(stat))
      ),
    });
  }

  disableForm() {
    this.noticeForm.disable();
  }

  addCommittente() {
    const ruoli = this.noticeForm.get('persona_ruoli') as FormArray;
    const ultimoCommittente = ruoli.controls
      .filter((x) => x.value.ruolo === 'Committente')
      .pop();
    //clono ultimoCommittente aggiunto tenendo soltanto il valore di alcuni campi
    let nuovoCommittente = this.fb.group(ultimoCommittente?.value);
    for (const key in ultimoCommittente?.value) {
      switch (key) {
        case 'id_cantiere':
        case 'id_notifica':
        case 'id_ruolo':
        case 'ruolo':
        case 'obbligatorio':
          nuovoCommittente
            .get(key)
            ?.patchValue(ultimoCommittente?.get(key)?.value);
          break;
        default:
          nuovoCommittente.get(key)?.patchValue(null);
          break;
      }
    }
    ruoli.insert(
      ruoli.controls.indexOf(ultimoCommittente!) + 1,
      nuovoCommittente
    );
    this.notice.persona_ruoli.splice(
      ruoli.controls.indexOf(ultimoCommittente!) + 1,
      0,
      nuovoCommittente.value
    );
  }

  removeCommittente(index: number) {
    const ruoli = this.noticeForm.get('persona_ruoli') as FormArray;
    ruoli.removeAt(index);
    this.notice.persona_ruoli.splice(index, 1);
  }

  addImpresa() {
    let nuovaImpresa: any = {};
    Object.assign(nuovaImpresa, this.notice.cantiere_imprese[0]); //clono la struttura della prima impresa
    nuovaImpresa.ragione_sociale = null;
    nuovaImpresa.partita_iva = null;
    nuovaImpresa.codice_fiscale = null;
    let arr = this.noticeForm.controls['cantiere_imprese'] as FormArray;
    let lastImpresa = arr.value[arr.length - 1];
    /*if (
      (lastImpresa.ragione_sociale == null ||
        lastImpresa.ragione_sociale.trim() == '') &&
      (lastImpresa.partita_iva == null ||
        lastImpresa.partita_iva.trim() == '') &&
      (lastImpresa.codice_fiscale == null ||
        lastImpresa.codice_fiscale.trim() == '')
    ) {
      Swal.fire({ text: 'Valorizzare impresa precedente!', icon: 'warning' });
      return;
    }*/
    arr.push(this.fb.group(nuovaImpresa));
    this.notice.cantiere_imprese.push(nuovaImpresa); //serve per la visualizzazione in UI
  }

  patchValue(controlPath: any, value: any) {
    this.noticeForm.get(controlPath)?.patchValue(value);
  }

  setImpresa(controlPath: any, value: any, key: string) {
    let imp = this.anagrafica.imprese.find((i: any) => i[key] === value);
    this.patchValue(controlPath, imp);
  }

  setRuolo(controlPath: any, value: any) {
    let r = this.anagrafica.soggettiFisici.find(
      (s: any) => s['codice_fiscale'] === value
    );
    this.patchValue(controlPath, r);
  }

  setComune(controlPath: any, value: any) {
    let com = this.anagrafica.comuni.find((c: any) => c['comune'] === value);
    this.patchValue(controlPath, com);
  }

  setComuneCantiere(controlPath: any, value: any) {
    let c = this.anagrafica.comuni.find((c: any) => c['comune'] === value);
    let com = {
      id_comune: c.id,
      comune: value,
      cod_provincia: c.cod_provincia,
    };
    this.patchValue(controlPath, com);
  }

  setComuneNotificante(controlPath: any, value: any) {
    let c = this.anagrafica.comuni.find((c: any) => c['comune'] === value);
    let com = {
      id_comune_notificante: c.id,
      comune_notificante: value,
      cod_provincia_notificante: c.cod_provincia,
    };
    this.patchValue(controlPath, com);
  }

  changeNatura(ev: any) {
    this.currNatura = ev.target.value;
  }

  onSubmit(e: any): void {
    let statoSelezionato = {
      id_stato: e.submitter.value,
      stato: e.submitter.innerText,
    };
    if (this.noticeForm.get('cantiere')?.value.data_presunta == '')
      this.noticeForm.get('cantiere')!.value.data_presunta = null;
    this.noticeForm.patchValue(Utils.trimData(this.noticeForm.value));
    if(!statoSelezionato.stato.toLocaleUpperCase().includes("ELIMIN") && !statoSelezionato.stato.toLocaleUpperCase().includes("INTEGR")){ //se elimin non faccio check client
      if(!this.checkForm(statoSelezionato.stato)) //check client
        return;
      }
    
    if(statoSelezionato.stato.toLocaleUpperCase().includes("BOZZ") || statoSelezionato.stato.toLocaleUpperCase().includes("ELIMIN") || statoSelezionato.stato.toLocaleUpperCase().includes("INTEGR")){  //se bozza o elimin non faccio check db
      let nuovoStato: any;
      this.notice.stati_successivi.forEach((stato: any) => {
        if (stato.id_stato_prossimo == statoSelezionato.id_stato)
          nuovoStato = stato;
      });
      if (nuovoStato.msg == null) {
        this.salvaStato(nuovoStato);
      } else {
        Swal.fire({
          text: nuovoStato.msg,
          icon: 'question',
          showCancelButton: true,
          confirmButtonText: 'Conferma',
          cancelButtonText: 'Annulla',
        }).then((result: any) => {
          if (result.value) {
            this.salvaStato(nuovoStato);
          }
        });
      }
    }else{
      this.us.checkNotifica(this.noticeForm.value).subscribe((res:any) => { //check db
        if(!res.esito)
          Swal.fire({ text: res.msg.split(' [')[0], icon: 'warning' });
        else{
            let nuovoStato: any;
            this.notice.stati_successivi.forEach((stato: any) => {
              if (stato.id_stato_prossimo == statoSelezionato.id_stato)
                nuovoStato = stato;
            });
            if (nuovoStato.msg == null) {
              this.salvaStato(nuovoStato);
            } else {
              Swal.fire({
                text: nuovoStato.msg,
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Conferma',
                cancelButtonText: 'Annulla',
              }).then((result: any) => {
                if (result.value) {
                  this.salvaStato(nuovoStato);
                }
              });
            }
          }
      })
    }
  }

  salvaStato(nuovoStato: any) {
    this.noticeForm.get('cantiere')?.patchValue({
      id_stato: nuovoStato.id_stato_prossimo,
      stato: nuovoStato.descr_stato_prossimo,
    });    
    console.log(this.noticeForm.value);
    this.noticeForm.value.persona_ruoli.forEach((p:any) => {
      if(p.nome)
        p.nome = p.nome.toUpperCase().trim();
      if(p.cognome)
        p.cognome = p.cognome.toUpperCase().trim();
      if(p.codice_fiscale)
        p.codice_fiscale = p.codice_fiscale.toUpperCase().trim();
    })
    //this.noticeForm.value.cantiere_imprese = this.noticeForm.value.cantiere_imprese.filter((i:any) => i.partita_iva);
    console.log(this.noticeForm.value);
    this.us.updateNotifica(this.noticeForm.value).subscribe((res: any) => {
      //var ret = JSON.parse(res)
      if (res.esito) {
        if (res.msg == 'NUOVO ID') //{
          this.noticeForm.get('cantiere')!.value.id_notifica = res.valore;
          //è un integrazione andata a buon fine, in valore ho l id della nuova bozza da visualizzare
          this.router.routeReuseStrategy.shouldReuseRoute = () => false;
          this.router.onSameUrlNavigation = 'reload';
          this.router.navigate(['notifica'], {
            queryParams: {
              //idNotifica: res.valore,
              idNotifica: this.noticeForm.get('cantiere')!.value.id_notifica,
              mode: nuovoStato.modificabile_stato_prossimo,
              modeCantiere: nuovoStato.modificabile_cantiere_stato_prossimo,
            },
            skipLocationChange: true,
          });
        //} else {
          this.caricaCantiere(nuovoStato);
          Swal.fire({ text: `Notifica modificata nello stato: ${nuovoStato.descr_stato_prossimo}`, icon: 'success' });
          //this.indietro();
        //}
      } else {
        Swal.fire({ text: res.msg.split(' [')[0], icon: 'warning' });
      }
    });
  }

  download(nuovoPdf: boolean) {
    Utils.showSpinner(true, 'Generazione PDF notifica in corso');
    let dataToSend = Object.assign({}, this.noticeForm.value); //deep clone
    dataToSend.cantiere.data_notifica = Utils.fromISOTimeToLocaleTime(
      dataToSend.cantiere.data_notifica
    );
    dataToSend.cantiere.data_modifica = Utils.fromISOTimeToLocaleDate(
      dataToSend.cantiere.data_modifica
    );
    dataToSend.cantiere.data_presunta = Utils.fromISOTimeToLocaleDate(
      dataToSend.cantiere.data_presunta
    );
    dataToSend.notifiche_prec.forEach((nPrec: any) => {
      nPrec.data_modifica = Utils.fromISOTimeToLocaleDate(nPrec.data_modifica);
      nPrec.data_notifica = Utils.fromISOTimeToLocaleTime(nPrec.data_notifica);
    });
    this.us.downloadNotifica(dataToSend, nuovoPdf, 'notificaPreliminare').subscribe(
      (data) => {
        Utils.download(
          data,
          `notificaPreliminare_${dataToSend.cantiere.cun}_${dataToSend.cantiere.data_notifica}.pdf`
        );
        Utils.showSpinner(false);
      },
      (err) => {
        Swal.fire({ text: 'Errore nella generazione del PDF (Notifica)!', icon: 'error' });
        Utils.showSpinner(false);
        console.error(err);
      }
    );
    console.log(this.role);
    if(this.role != 'Profilo ASL - Notifiche Preliminari'){
      this.us.downloadNotifica(dataToSend, nuovoPdf, 'cartelloNotifica').subscribe(
        (data) => {
          Utils.download(
            data,
            `cartelloNotifica_${dataToSend.cantiere.cun}_${dataToSend.cantiere.data_notifica}.pdf`
          );
          Utils.showSpinner(false);
        },
        (err) => {
          Swal.fire({ text: 'Errore nella generazione del PDF (Cartello)!', icon: 'error' });
          Utils.showSpinner(false);
          console.error(err);
        }
      );
    }
  }

  indietro() {
    this.router.navigate(['user/dashboard'], { relativeTo: this.route.parent });
  }

  annulla(): void {
    //this.ngOnInit();
    //window.location.reload();
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['notifica'], {
      queryParams: {
        //idNotifica: res.valore,
        idNotifica: this.noticeForm.get('cantiere')!.value.id_notifica,
        mode: !this.readonly,
        modeCantiere: !this.readonlyCantiere,
      },
      skipLocationChange: true
    })
  }

  getLocation() {
    Utils.getLocation().then((value: any) => {
      let pos = { lat: value.coords.latitude, lng: value.coords.longitude };
      Swal.fire({
        text: `Associare le coordinate ${pos.lat}, ${pos.lng} al cantiere?`,
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Conferma',
        cancelButtonText: 'Non geolocalizzare',
      }).then((result: any) => {
        if (result.value) {
          this.notice.cantiere.lat = pos.lat;
          this.notice.cantiere.lng = pos.lng;
          this.patchValue(['cantiere'], pos);
        } else {
          this.notice.cantiere.lat = null;
          this.notice.cantiere.lng = null;
          this.patchValue(['cantiere'], { lat: null, lng: null });
        }
      });
    });
  }


  checkForm(statoSelezionato:string): boolean{
    var msg = '';
    var patternNumericoPositivo = '^[1-9][0-9]*$';
    var patternNumerico = '^[0-9]*$';
    var patternCap = '^[0-9]{5}$';
    console.log(this.noticeForm);
    var cantiere = this.noticeForm.get('cantiere')?.value;
    Object.keys(cantiere).map(k => cantiere[k] = typeof cantiere[k] == 'string' ? cantiere[k].trim() : cantiere[k]); //trim su tutte le stringhe

    //notificante
    if(!statoSelezionato.toLocaleUpperCase().includes("BOZZ")){ //se non è una bozza controllo che ci sia il cf

      if(
        (!cantiere.via_notificante)
        || (!cantiere.civico_notificante)
        || (!cantiere.comune_notificante)
        || (!cantiere.cap_notificante)
        ){
          Swal.fire({
            text: 'Indirizzo notificante incompleto!',
            icon: 'warning',
          });
          return false;
      }
    }
    if (
      cantiere.id_comune_notificante == null ||
      this.anagrafica.comuni.find(
        (c: any) =>
          c['comune'].toUpperCase() ===
          this.noticeForm
            .get('cantiere')
            ?.value.comune_notificante.trim()
            .toUpperCase()
      ) == undefined
    ) {
      Swal.fire({
        text: 'Selezionare comune notificante dalla lista!',
        icon: 'warning',
      });
      return false;
    }

    if(!statoSelezionato.toLocaleUpperCase().includes("BOZZ")){ //se non è una bozza controllo che ci sia il cf

      if(!cantiere.cap_notificante){
        Swal.fire({
          text: 'Valorizzare CAP notificante!',
          icon: 'warning',
        });
        return false;
      }
      if(cantiere.cap_notificante.length != 5 || !cantiere.cap_notificante.toString().match(patternCap)){
        Swal.fire({
          text: 'Formato CAP notificante non valido!',
          icon: 'warning',
        });
        return false;
      }
      if(cantiere.cap_notificante == '80100'){
        Swal.fire({
          text: 'CAP 80100 per notificante non consentito!',
          icon: 'warning',
        });
        return false;
      }
    }


    //cantiere
    if(!cantiere.via){
      Swal.fire({
        text: 'Valorizzare indirizzo cantiere!',
        icon: 'warning',
      });
      return false;
    }
    if(!cantiere.civico){
      Swal.fire({
        text: 'Valorizzare numero civico cantiere!',
        icon: 'warning',
      });
      return false;
    }
    if (
      cantiere.id_comune == null ||
      this.anagrafica.comuniCantiere.find(
        (c: any) =>
          c['comune'].toUpperCase() ===
          this.noticeForm
            .get('cantiere')
            ?.value.comune.trim()
            .toUpperCase()
      ) == undefined
    ) {
      Swal.fire({
        text: 'Selezionare comune cantiere dalla lista!',
        icon: 'warning',
      });
      return false;
    }

    if(!cantiere.cap){
      Swal.fire({
        text: 'Valorizzare CAP cantiere!',
        icon: 'warning',
      });
      return false;
    }
    if(cantiere.cap.length != 5 || !cantiere.cap.toString().match(patternCap)){
      Swal.fire({
        text: 'Formato CAP cantiere non valido!',
        icon: 'warning',
      });
      return false;
    }
    if(cantiere.cap == '80100'){
      Swal.fire({
        text: 'CAP 80100 per cantiere non consentito!',
        icon: 'warning',
      });
      return false;
    }

    if(!cantiere.denominazione){
      Swal.fire({
        text: 'Valorizzare denominazione cantiere!',
        icon: 'warning',
      });
      return false;
    }
    if(cantiere.id_natura_opera == null){
      Swal.fire({
        text: 'Valorizzare natura opera cantiere!',
        icon: 'warning',
      });
      return false;
    }
    if (
      cantiere.id_natura_opera == 0 &&
      (cantiere.altro == null ||
        cantiere.altro.trim() == '')
    ) {
      Swal.fire({ text: 'Specificare natura opera!', icon: 'warning' });
      return false;
    }
    if(!cantiere.data_presunta){
      Swal.fire({
        text: 'Valorizzare data presunta inizio lavori!',
        icon: 'warning',
      });
      return false;
    }

    //i prossimi campi non sono obbligatori in caso di bozza
    if(!statoSelezionato.toLocaleUpperCase().includes("BOZZ")){ //se non è una bozza controllo che ci sia il cf
      if(!cantiere.durata_presunta){
        Swal.fire({
          text: 'Valorizzare durata presunta lavori!',
          icon: 'warning',
        });
        return false;
      }
      if(!cantiere.durata_presunta.toString().match(patternNumericoPositivo)){
        Swal.fire({
          text: 'Il valore durata presunta lavori deve essere numerico, e maggiore di 0',
          icon: 'warning',
        });
        return false;
      }
      if(!cantiere.numero_imprese){
        Swal.fire({
          text: 'Valorizzare numero previsto di imprese e di lavoratori autonomi sul cantiere!',
          icon: 'warning',
        });
        return false;
      }
      if(!cantiere.numero_imprese.toString().match(patternNumericoPositivo)){
        Swal.fire({
          text: 'Il valore numero previsto di imprese e di lavoratori autonomi sul cantiere deve essere numerico, e maggiore di 0',
          icon: 'warning',
        });
        return false;
      }
      if(!cantiere.numero_lavoratori){
        Swal.fire({
          text: 'Valorizzare numero presunto dei lavoratori in cantiere!',
          icon: 'warning',
        });
        return false;
      }
      if(!cantiere.numero_lavoratori.toString().match(patternNumerico)){
        Swal.fire({
          text: 'Il valore numero presunto dei lavoratori in cantiere deve essere numerico',
          icon: 'warning',
        });
        return false;
      }
      if(cantiere.numero_lavoratori < 3){
        Swal.fire({
          text: 'Il valore numero presunto dei lavoratori in cantiere deve essere almeno 3',
          icon: 'warning',
        });
        return false;
      }

      if(!cantiere.ammontare){
        Swal.fire({
          text: 'Valorizzare ammontare complessivo presunto dei lavori (in €)!',
          icon: 'warning',
        });
        return false;
      }
      if(!cantiere.ammontare.toString().match(patternNumericoPositivo)){
        Swal.fire({
          text: 'Il valore ammontare complessivo presunto dei lavori (in €) deve essere numerico, e maggiore di 0',
          icon: 'warning',
        });
        return false;
      }
      /*if(cantiere.ammontare < 1){
        Swal.fire({
          text: 'Il valore numero presunto dei lavoratori in cantiere deve essere maggiore di 0',
          icon: 'warning',
        });
        return false;
      }*/

      ///Imprese
      var old = {partita_iva: null, codice_fiscale: null, ragione_sociale: null};
      var imprese = this.noticeForm.get('cantiere_imprese')?.value;
      imprese = imprese.sort((a :any, b :any) => a.partita_iva < b.partita_iva ? 1 : -1);
      var countImprese = 0; //deve essere inferiore a numero previsto di imprese e lavoratori autonomi
      imprese.every((i:any) => {
        Object.keys(i).map(k => i[k] = typeof i[k] == 'string' ? i[k].trim() : i[k]); //trim su tutte le stringhe
        if(i.partita_iva)
          countImprese++;
        if(!i.partita_iva && (i.ragione_sociale || i.codice_fiscale)){
          var imp = i.ragione_sociale;
          if(!i.ragione_sociale)
            imp = i.codice_fiscale;
          msg = `Inserita impresa ${imp} senza partita iva`;
          return false;
        }
        if(old.partita_iva != null){
            if(i.codice_fiscale == old.codice_fiscale && i.codice_fiscale != null && i.codice_fiscale.trim() != ''){
              msg = 'Due o più imprese con lo stesso codice fiscale';
              return false;
            }
            if(i.partita_iva == old.partita_iva && i.partita_iva != null && i.partita_iva.trim() != ''){
              msg = 'Due o più imprese con la stessa partita iva';
              return false;
            }
          }
        old = i
        return true;
      })
      if(msg != ''){
        Swal.fire({text: msg,
          icon: 'warning',
        });
        return false;
      }

      if(countImprese > parseInt(cantiere.numero_imprese)){
        Swal.fire({text: 'Il numero di imprese valorizzate non può essere maggiore del numero previsto di imprese e di lavoratori autonomi sul cantiere',
          icon: 'warning',
        });
        return false;
      }

      //persone
      var persone = this.noticeForm.get('persona_ruoli')?.value;
      var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
      persone.every((p:any) => {
        Object.keys(p).map(k => p[k] = typeof p[k] == 'string' ? p[k].trim().toUpperCase() : p[k]); //trim su tutte le stringhe
        if(!statoSelezionato.toLocaleUpperCase().includes("BOZZ")){ //se non è una bozza controllo che ci sia il cf

          if(p.obbligatorio && (!p.codice_fiscale)){
            msg = 'Valorizzare codice fiscale ' + p.ruolo;
            return false;
          }
        }

        if(p.codice_fiscale || p.nome || p.cognome || p.pec || p.via || p.comune || p.cap){ //se non è una bozza e c'è il cf proseguo con gli altri controlli
          var committenti= persone.filter((c:any) => c.id_ruolo == 1)
          var uniqueCf = new Set(committenti.map((p:any) => p.codice_fiscale));
          if(uniqueCf.size < committenti.length){
            /*Swal.fire({text: 'Sono stati inseriti più committenti con lo stesso codice fiscale',
              icon: 'warning',
            });*/
            msg = 'Sono stati inseriti più committenti con lo stesso codice fiscale';
            return false;
          }

          if(p.obbligatorio && (!p.nome)){
            msg = 'Valorizzare nome ' + p.ruolo;
            return false;
          }
          if(p.obbligatorio && (!p.cognome)){
            msg = 'Valorizzare cognome ' + p.ruolo;
            return false;
          }

          if(((p.codice_fiscale) && (!p.nome || !p.cognome))
            || ((p.cognome) && (!p.codice_fiscale || !p.nome))
            || ((p.nome) && (!p.codice_fiscale || !p.cognome))
          ){
            msg = 'Estremi del ' + p.ruolo + ' non completi (assicurarsi di aver inserito CF, cognome e nome)' ;
            return false;
          }

          if(p.id_ruolo == 2 && (!p.pec)){
            msg = 'Valorizzare PEC per il ' + p.ruolo;
            return false;
          }

          if((p.pec) && !p.pec.toString().match(mailformat)){
            msg = 'La email inserita per il ' + p.ruolo+ ' non è corretta';
            return false;
          }

          if((p.pec) && !['pec', 'legal', 'cert'].some((s:any) => p.pec.split('@')[1].toUpperCase().includes(s.toUpperCase()))){
            msg = 'La email inserita per il ' + p.ruolo+ ' non è una PEC';
            return false;
          }

          if(((p.via) && (!p.cap || !p.comune))
            || ((p.cap) && (!p.via || !p.comune))
            || ((p.comune) && (!p.cap || !p.via))
          ){
            msg = 'L\'indirizzo per il ' + p.ruolo+ '  è incompleto (assicurarsi di aver inserito via, comune e CAP)';
            return false;
          }

          if (p.comune && this.anagrafica.comuni.find((c: any) =>c['comune'].toUpperCase() === p.comune.trim().toUpperCase()) == undefined){
            msg = 'Selezionare il comune per ' + p.ruolo + ' dalla lista' ;
            return false;
          }

          if(p.cap && (p.cap.length != 5 || !p.cap.toString().match(patternCap))){
            Swal.fire({
              text: 'Formato CAP per ' + p.ruolo + '  non valido!',
              icon: 'warning',
            });
            return false;
          }
          if(p.cap == '80100'){
            Swal.fire({
              text: 'CAP 80100 per ' + p.ruolo + ' non consentito!',
              icon: 'warning',
            });
            return false;
          }

          return true;
        }
        return true;
      })

      if(msg != ''){
        Swal.fire({text: msg,
          icon: 'warning',
        });
        return false;
      }

      persone = persone.filter((p:any) => p.codice_fiscale);
      //persone = persone.sort((a:any , b:any) => a.codice_fiscale.localeCompare(b.codice_fiscale));
      //var oldPersona = {codice_fiscale: null, cognome: null, nome: null, pec: null, via: null, comune: null, cap: null, descr: null}
      persone.every((oldPersona:any) => {
        //Object.keys(p).map(k => p[k] != null && p[k].length == 0 ? null : p[k]); //trim su tutte le stringhe
        //if(oldPersona.codice_fiscale){
          persone.every((p:any) => {
          if(oldPersona.codice_fiscale.toUpperCase() == p.codice_fiscale.toUpperCase()){
            if(oldPersona.cognome.toUpperCase() != p.cognome.toUpperCase()){
              msg = `Stesso codice fiscale utilizzato con cognomi diversi per ${p.ruolo} e ${oldPersona.descr}`;
              return false;
            }
            if(oldPersona.nome.toUpperCase() != p.nome.toUpperCase()){
              msg = `Stesso codice fiscale utilizzato con nomi diversi per ${p.ruolo} e ${oldPersona.descr}`;
              return false;
            }
            /*if(oldPersona.pec != p.pec){
              msg = `Stesso codice fiscale utilizzato con PEC diverse per ${p.ruolo} e ${oldPersona.descr}`;
              return false;
            }*/
            /*if(oldPersona.via != p.via){
              msg = `Stesso codice fiscale utilizzato con indirizzi diversi per ${p.ruolo} e ${oldPersona.descr}`;
              return false;
            }
            if(oldPersona.cap != p.cap){
              msg = `Stesso codice fiscale utilizzato con CAP diversi per ${p.ruolo} e ${oldPersona.descr}`;
              return false;
            }
            if(oldPersona.comune != p.comune){
              msg = `Stesso codice fiscale utilizzato con comuni diversi per ${p.ruolo} e ${oldPersona.descr}`;
              return false;
            }*/
          }
          return true;
        })
          //oldPersona = p;
        //}
        if(msg != '') //se nel for interno non ho trovato errori
          return true;
        return false;
      })

      if(msg != ''){
        Swal.fire({text: msg,
          icon: 'warning',
        });
        return false;
      }
    }

    return true;
  }
}
