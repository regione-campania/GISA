/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { Component, DoCheck, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import { Utils } from '../../utils/utils.class'
declare let Swal: any

@Component({
  selector: 'app-user-notices',
  templateUrl: './user-notices.component.html',
  styleUrls: ['./user-notices.component.scss']
})
export class UserNoticesComponent implements OnInit, DoCheck {

  @ViewChild('table') table: any

  userNotices?: any[]
  statiDisponibili?: any[]
  anniDisponibili?: any[]
  aslDisponibili?: any[]
  mobileView = window.innerWidth < 992
  role?: any //ruolo utente

  constructor(
    private us: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.role = this.us.userRole
    this.us.getNotifiche().subscribe(notices => {
      this.statiDisponibili = []
      this.anniDisponibili = []
      this.aslDisponibili = []
      notices.forEach((n: any) => {
        n.data_notifica = Utils.fromTimeStringToLocaleData(n.data_notifica)
        n.data_modifica = Utils.fromTimeStringToLocaleData(n.data_modifica)
        if(!this.anniDisponibili?.includes(n.anno))
          this.anniDisponibili?.push(n.anno)
        if(!this.statiDisponibili?.includes(n.stato))
          this.statiDisponibili?.push(n.stato)
        if(!this.aslDisponibili?.includes(n.descr_asl))
          this.aslDisponibili?.push(n.descr_asl)
      })
      
      this.userNotices = notices
    })
    window.addEventListener('resize', () => this.mobileView = window.innerWidth < 992)
  }

  ngDoCheck(): void {
    if(this._exporting)
      if(document.getElementById('tabella-notifiche'))
        this.exportTable()
  }

  goToNoticeEditor(noticeId: number, mode: any, modeCantiere: any) {
    this.router.navigate(['notifica'], {queryParams: {idNotifica: noticeId, mode: mode, modeCantiere: modeCantiere}, skipLocationChange: true })
  }

  insertNotifica() {
    this.us.insertNotifica().subscribe(res => {
      if(res.esito){
        this.goToNoticeEditor(res.valore, true, null)
      }else{
        Swal.fire({text: res.msg.split(" [")[0], icon: 'error'});
      }
    })
  }


  private _exporting = false

  exportTable(): void {
    //enable export in mobile view
    if(this.mobileView) {
      this.mobileView = false
      this._exporting = true
      return
    }
    
    const table = document.getElementById('tabella-notifiche') as HTMLTableElement
    if(!table) 
      throw 'No table found.'

    Utils.exportTable(table, {
      predicate: r => !r.hidden && !r.className.includes('paginator'),
      skipColumns: [table.rows!.item(1)!.cells.length - 1]
    })

    this.mobileView = window.innerWidth < 992
    this._exporting = false
  }

}
