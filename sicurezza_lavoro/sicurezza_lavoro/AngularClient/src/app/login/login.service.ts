/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { Injectable } from '@angular/core';
import { __param } from 'tslib';
import { UserService } from '../user/user.service';
import { ActivatedRoute } from '@angular/router';
import { environment } from './../../environments/environment';
declare let GisaSpid: any
declare let Swal: any


@Injectable()
export class LoginService {

  constructor(
    private us: UserService,
    private route: ActivatedRoute
    ) {}

  login(user: any){
    let params = {nome: user.firstName, cognome: user.lastName, cf: user.fiscalCode, insert: 'false'}; //provo a loggarmi senza forzare l inserimento
    this.us.findUser(params).subscribe((usr: any) =>{
      console.log(usr);
      let esito = JSON.parse(usr.id).esito;
      let ret = JSON.parse(usr.id).valore;
      if(esito) //utente registrato
        this.us.setUser(usr)
      else if(ret == -3){
        Swal.fire({
          title: 'Registrazione',
          text: 'Registrarsi al sistema "G.I.S.A. - Sicurezza lavoro" come profilo notificatore?',
          icon: 'warning',
          showCancelButton: true,
          confirmButtonText: 'Ok, accetto',
          cancelButtonText: 'Non voglio registrarmi'
        }).then((result:any) => {
          if(result.value){
            params.insert = 'true'; //mi registro
            this.us.findUser(params).subscribe(usr => this.us.setUser(usr));
          }
          
        })
        /*if(confirm("Registrarsi al sistema Gesdasic come profilo notificatore?")){ //nuovo utente
          params.insert = 'true'; //mi registro
          this.us.findUser(params).subscribe(usr => this.us.setUser(usr));
        }*/
      }
    })
  }

  logout(){
    let params = {nome: null, cognome: null, cf: null}
    var test = false;
    this.route.queryParams.subscribe((p: any) => {
      if(p.test){
        let _params = {
          cf: 'CRBLSN67B67F839V',//this.user.fiscalCode,
          nome: 'Alessandra', //this.user.firstName,
          cognome: 'Carbone', //this.user.lastName
          insert: 'false'
        }
        if(p.cf != undefined){
          _params = {
            cf: p.cf,
            nome: '',
            cognome: '',
            insert: 'false'
          }
        }
        test = true;
        this.us.findUser(_params).subscribe(usr => this.us.setUser(usr))
      } 
    })
    if(!test)
      this.us.setUser(params);
  }

  
}
