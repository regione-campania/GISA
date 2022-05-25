/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { AppService } from 'src/app/app.service';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.scss']
})
export class UserDashboardComponent implements OnInit {

  constructor(
    private us: UserService,
    private router: Router,
    private app: AppService
    ) {
      this.app.pageName = 'Dashboard'
    }

  user?: any

  ngOnInit(): void {
    if(this.us.user == null || this.us.user.cf == null)
      this.router.navigate(['/'])
    else{

    }
      this.user = this.us.user
      console.log(this.us.user.id);
      this.user!.ruolo = JSON.parse(JSON.parse(this.us.user.id).info).ruolo //nel ritorno del servizio, in info ho il ruolo
      this.user!.id_asl = JSON.parse(JSON.parse(this.us.user.id).info).site_id //nel ritorno del servizio, in info ho il ruolo
  }

}
