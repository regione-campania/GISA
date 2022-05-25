import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IspezioniComponent } from '../ispezioni/ispezioni.component';
import { NotificaComponent } from '../verbali/notifica/notifica.component';
import { VerbaleDiSopralluogoComponent } from '../verbali/verbale-di-sopralluogo/verbale-di-sopralluogo.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { UserComponent } from './user.component';

const routes: Routes = [
  { path: 'user', component: UserComponent, children: [
    {path: 'dashboard', component: UserDashboardComponent},
    {path: 'ispezioni', component: IspezioniComponent},
  ] },
  {path: 'notifica', component: NotificaComponent},
  {path: 'verbale-di-sopralluogo', component: VerbaleDiSopralluogoComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
