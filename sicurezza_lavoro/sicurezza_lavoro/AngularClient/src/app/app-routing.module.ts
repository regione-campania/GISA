/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { InformativaComponent } from './login/informativa/informativa.component';
import { SupportoComponent } from './layout/supporto/supporto.component';
import { VerbaleDiSopralluogoComponent } from './verbali/verbale-di-sopralluogo/verbale-di-sopralluogo.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'informativa', component: InformativaComponent},
  {path: 'user', redirectTo: 'user/dashboard', pathMatch: 'full'},
  {path: 'verbaleDiSopralluogo', component: VerbaleDiSopralluogoComponent},
  {path: 'ispezioni', redirectTo: 'ispezioni', pathMatch: 'full'},
  {path: 'supporto', component: SupportoComponent},
  {path: '', redirectTo: 'login', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
