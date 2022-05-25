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
