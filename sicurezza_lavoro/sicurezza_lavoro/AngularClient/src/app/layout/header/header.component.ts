/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppService } from 'src/app/app.service';
import { UserService } from '../../user/user.service';

declare let GisaSpid: any;

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  isMenuCollapsed = true;
  isNotificatore = true;

  pageName = 'Da Definire';

  randomParameterManuale = Math.floor(Math.random() * 1000);

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private us: UserService,
    public app: AppService
  ) {}

  ngOnInit(): void {
    console.log(this.us.userRole);
    this.isNotificatore = this.us.userRole == 'Profilo Notificatore';
    document.querySelectorAll('.nav-link').forEach((link) => {
      link.addEventListener('click', () => {
        this.isMenuCollapsed = true;
      });
    });
  }

  logoutSpid(url: string) {
    GisaSpid.logoutSpid(url);
  }

  goToDashboard() {
    this.router.navigate(['user', 'dashboard']);
  }

  goToIspezioni() {
    this.router.navigate(['ispezioni']);
  }

  collapse(){
    this.isMenuCollapsed = !this.isMenuCollapsed
  }
}
