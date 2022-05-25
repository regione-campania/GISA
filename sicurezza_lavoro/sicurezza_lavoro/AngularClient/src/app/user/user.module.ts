/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { UserService } from './user.service';
import { UserComponent } from './user.component';
import { UserNoticesComponent } from './user-notices/user-notices.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AnagraficaModule } from '../anagrafica/anagrafica.module';
import { UtilsModule } from '../utils/utils.module';
import { VerbaliModule } from '../verbali/verbali.module';
import { ATableModule } from '../utils/modules/a-table/a-table.module';


@NgModule({
  declarations: [
    UserDashboardComponent,
    UserComponent,
    UserNoticesComponent,
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    ReactiveFormsModule,
    AnagraficaModule,
    VerbaliModule,
    UtilsModule,
    ATableModule
  ],
  providers: [UserService]
})
export class UserModule { }
