/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from './login.service';
import { UserService } from '../user/user.service';
import { AppService } from '../app.service';
declare let GisaSpid: any
declare let Swal: any

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private ls: LoginService,
    private us: UserService,
    private app: AppService
  ) {
    this.app.pageName = 'Login'
  }

  ngOnInit(): void {
    const _global = (window) as any
    let router = this.router
    let route = this.route
    let ls = this.ls
    ls.logout(); //appena entro o ritorno sulla login faccio logout
    _global.getSpidUser = function (user: any) {
      ls.login(user)
      //us.user = user
      //router.navigate(['user/dashboard'], {relativeTo: route.parent})
    }

    if(this.us.cookieAccepted == 'false'){ //controllo se in sessione ho i dati utente, se no gli mostro l'infomativa sui cookie
      Swal.fire({
        position: 'bottom-end',
        html: `
        <div style="font-size: 14px">
        Il portale "G.I.S.A. - Sicurezza e prevenzione sui luoghi di lavoro" utilizza i cookie. <br>
        Continuando a navigare sul sito accetterai automaticamente i cookie necessari.
        </div>
        `,
        footer: `<a href="informativa">Link all'informativa sui cookie</a>`,
        showConfirmButton: true,
        confirmButtonText: 'Ok, accetto',
        backdrop: true
     }).then(() => {
        this.us.cookieAccepted = 'true';
     })
    }
  }

  entraConSpid(event: any) {
    GisaSpid.entraConSpid(event, null, null, "getSpidUser")
  }

}
