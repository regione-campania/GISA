import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListaIspezioniComponent } from './lista-ispezioni/lista-ispezioni.component';
import { ListaMacchinariComponent } from './lista-macchinari/lista-macchinari.component';
import { NuovaIspezioneComponent } from './nuova-ispezione/nuova-ispezione.component';

export const routes: Routes = [
  {path: 'ispezioni', component: ListaIspezioniComponent},
  {path: 'ispezioni/nuova-ispezione', component: NuovaIspezioneComponent},
  {path: 'macchinari', component: ListaMacchinariComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class IspezioniRoutingModule { }
