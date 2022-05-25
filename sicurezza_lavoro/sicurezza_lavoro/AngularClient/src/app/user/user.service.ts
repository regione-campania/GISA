/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { HttpClient, JsonpInterceptor } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from './../../environments/environment';
import { Observable } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';


@Injectable()
export class UserService {

  get user(): any {
    return JSON.parse(sessionStorage.getItem('user')!)
  }

  get userId(): number {
    return JSON.parse(this.user.id).valore
  }

  get userRole(): string {
    return JSON.parse(JSON.parse(this.user.id).msg).ruolo //se la login va a buon file nel msg ho il ruolo utente
  }

  get userIdAsl(): number {
    return JSON.parse(JSON.parse(this.user.id).msg).site_id //se la login va a buon file nel msg ho il ruolo utente
  }

  set cookieAccepted(value: string){
    console.log("cookie accettati");
    localStorage.setItem('cookieAccepted', value);
  }

  get cookieAccepted(): string{
    return localStorage.getItem('cookieAccepted') || 'false';
  }

  get accessToken(){
    return localStorage.getItem('authToken') || '';
  }

  constructor(
    private http: HttpClient,
    private router: Router, 
    private route: ActivatedRoute) {}

  setUser(usr: any){
    sessionStorage.setItem('user', JSON.stringify(usr))
    localStorage.setItem('authToken', usr.token);
    
    if(usr.cf != null) //login
      this.router.navigate(['user/dashboard'], {relativeTo: this.route.parent})
  }

  findUser(_params: any) {
    return this.http.post<any>(`${environment.protocol}://${environment.host}:${environment.port}/um/loginByCf`, _params)
  }

  getNotifiche() {
    return this.http.get<any>(`${environment.protocol}://${environment.host}:${environment.port}/notifiche/getNotifiche`, {
      //params: {idNotificante: this.userId, ruolo: this.userRole, site_id: this.userIdAsl}
    })
  }

  getNotificaInfo(id: number | string) {
    return this.http.get<any>(`${environment.protocol}://${environment.host}:${environment.port}/notifiche/getNotificaInfo`, {
      params: {idNotifica: id, /*idNotificante: this.userId*/}
    })
  }

  insertNotifica() {
    return this.http.post<any>(`${environment.protocol}://${environment.host}:${environment.port}/notifiche/insertNotifica`, {
      //id_soggetto_notificante: this.user.id
    })
  }

  updateNotifica(notifica: any) {
    return this.http.post<any>(`${environment.protocol}://${environment.host}:${environment.port}/notifiche/updateNotificaInfo`, {
      notifica: notifica
    })
  }

  downloadNotifica(notifica: any, nuovoPdf: boolean): Observable<Blob> {
    return this.http.request<any>('POST', `${environment.protocol}://${environment.host}:${environment.port}/verbali/getNotificaCompilata?descrizioneVerbale=notificaPreliminare&nuovoPdf=${nuovoPdf}`, 
      { body: notifica, responseType: 'blob' as 'json'})
  }

}
