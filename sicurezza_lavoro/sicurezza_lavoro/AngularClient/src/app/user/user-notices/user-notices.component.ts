import {
  Component,
  DoCheck,
  OnChanges,
  OnInit,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import { Utils } from '../../utils/utils.class';
import { AnagraficaService } from 'src/app/anagrafica/anagrafica.service';
declare let Swal: any;

@Component({
  selector: 'app-user-notices',
  templateUrl: './user-notices.component.html',
  styleUrls: ['./user-notices.component.scss'],
})
export class UserNoticesComponent implements OnInit, DoCheck {
  @ViewChild('table') tableRef: any;

  userNotices?: any[];
  statiDisponibili?: any[] = [];
  anniDisponibili?: any[] = [];
  aslDisponibili?: any[];
  mobileView = window.innerWidth < 992;
  role?: any; //ruolo utente

  deckControlsConf = [
    /* {field: 'nome_notificante', type: 'text', label: 'Nome Notificante'},
    {field: 'cognome_notificante', type: 'text', label: 'Cognome Notificante'},
    {field: 'cun', type: 'text', label: 'Cun'},
    {field: 'cuc', type: 'text', label: 'Cuc'},
    {field: 'denominazione', type: 'text', label: 'Denominazione'}, */
    {field: 'anno', type: 'selection', label: 'Anno notifica', values: this.anniDisponibili},
    /* {field: 'data_modifica', type: 'date', label: 'Ultima modifica'},
    {field: 'descr_asl', type: 'selection', label: 'ASL'}, */
    {field: 'stato', type: 'selection', label: 'Stato', values: this.statiDisponibili}
  ]

  constructor(
    private us: UserService,
    private router: Router,
    public anagraficaService: AnagraficaService
  ) {}

  ngOnInit(): void {
    this.role = this.us.userRole;
    Utils.showSpinner(
      true,
      'Caricamento notifiche in corso. Si prega di attendere'
    );
    this.us.getNotifiche().subscribe((notices) => {
      this.anagraficaService.getStatiNotifica().subscribe((s: any) => {
         s.map((a: any) => a.descr).forEach((el: any) => {
          this.statiDisponibili?.push(el);
          this.statiDisponibili?.sort((a:any, b:any) => a.localeCompare(b))
         });
         console.log(this.statiDisponibili);
      });
      this.anagraficaService.getStatiNotifica().subscribe((s: any) => {
        this.statiDisponibili = s.map((a: any) => a.descr);
        this.statiDisponibili?.sort((a:any, b:any) => a.localeCompare(b))
        console.log(this.statiDisponibili);
      });
      /* this.anniDisponibili = []; */
      this.aslDisponibili = [];
      notices.forEach((n: any) => {
        n.data_notifica = Utils.fromTimeStringToLocaleData(n.data_notifica);
        n.data_modifica = Utils.fromTimeStringToLocaleData(n.data_modifica);
        if (!this.anniDisponibili?.includes(n.anno))
          this.anniDisponibili?.push(n.anno);
        if (!this.aslDisponibili?.includes(n.descr_asl) && n.descr_asl)
          this.aslDisponibili?.push(n.descr_asl);
      });
      this.aslDisponibili.sort((a:any, b:any) => a.localeCompare(b))
      console.log(notices);
      this.userNotices = notices;
      Utils.showSpinner(false);
    });
    window.addEventListener(
      'resize',
      () => (this.mobileView = window.innerWidth < 992)
    );
  }

  ngDoCheck(): void {
    if (this._exporting)
      if (document.getElementById('tabella-notifiche')) this.exportTable();
  }

  goToNoticeEditor(noticeId: number, mode: any, modeCantiere: any) {
    this.router.navigate(['notifica'], {
      queryParams: {
        idNotifica: noticeId,
        mode: mode,
        modeCantiere: modeCantiere,
      },
      skipLocationChange: true,
    });
  }

  insertNotifica() {
    this.us.insertNotifica().subscribe((res) => {
      if (res.esito) {
        this.goToNoticeEditor(res.valore, true, null);
      } else {
        Swal.fire({ text: res.msg.split(' [')[0], icon: 'error' });
      }
    });
  }

  private _exporting = false;

  exportTable(full = true): void {
    console.log(this.userNotices?.length);
    //enable export in mobile view
    if(this.userNotices?.length == 0){
      Swal.fire({ text: "Non sono presenti notifiche da esportare", icon: 'warning' });
      return;
    }
    
      if (this.mobileView) {
        this.mobileView = false;
        this._exporting = true;
        return;
      }
      let table = document.getElementById('tabella-notifiche')
        ?.firstChild as HTMLTableElement;
      if (!table) throw 'No table found.';
      if (full) {
        let temp = document.createElement('table');
        let row: HTMLTableRowElement, cell: HTMLTableCellElement;
        let head = temp.createTHead();
        Array.from(table.tHead!.rows).forEach((r) => {
          row = head.insertRow();
          Array.from(r.cells!).forEach((c) => {
            cell = row.insertCell();
            cell.innerText = c.innerText;
          });
        });
        let body = temp.createTBody();
        //content shape {p: number, v: boolean}
        this.tableRef?.content.forEach((c: any) => {
          if(!c.v) return;
          let n = this.tableRef?.data[c.p];
          row = body.insertRow();
          for (let prop of [
            'cognome_notificante',
            'cun',
            'cuc',
            'denominazione',
            'anno',
            'data_modifica',
            'descr_asl',
            'stato',
            ''
          ]) {
            cell = row.insertCell();
            if (prop === 'cognome_notificante')
              cell.innerText = `${n['cognome_notificante']} ${n['nome_notificante']}`;
            else
              cell.innerText = n[prop];
          }
        });
        table = temp;
      }
      if (window.innerWidth < 992)
        // se � mobile utilizzo la libreria apposita compatibile con il celulare, altrimenti la nostra custom pi� personalizzata
        Utils.tableExportXlsx(table, {
          predicate: (r) =>
            !r.hidden && !r.className.includes('paginator-wrapper'),
          skipColumns: [table.rows!.item(1)!.cells.length - 1],
        });
      else
        Utils.exportTable(table, {
          predicate: (r) =>
            !r.hidden && !r.className.includes('paginator-wrapper'),
          skipColumns: [table.rows!.item(1)!.cells.length - 1],
        });
    
  
      this.mobileView = window.innerWidth < 992;
      this._exporting = false;
    
  }
}
