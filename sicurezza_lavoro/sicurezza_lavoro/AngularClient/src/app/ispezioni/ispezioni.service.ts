/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable()
export class IspezioniService {

  constructor(private http: HttpClient) { 
  }

  getIspezioni() {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/ispezioni/getIspezioni`)
  }

  getNuovaIspezione(idUtente: string | number) {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/ispezioni/insertIspezione`, {
      params: {id_utente: idUtente}
    })
  }

  getNuovaFase(idIspezione: string | number) {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/ispezioni/insertIspezioneFase`, {
      params: {idIspezione: idIspezione}
    })
  }

  updateIspezione(ispezione: any) {
    return this.http.post(`${environment.protocol}://${environment.host}:${environment.port}/ispezioni/updateIspezioneInfo`, ispezione)
  }

  getIspezioneInfo(idIspezione: string | number) {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/ispezioni/getIspezioneInfo`, {
      params: {idIspezione: idIspezione}
    })
  }

  getFaseInfo(idFase: string | number) {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/ispezioni/getIspezioneFasi`, {
      params: {idIspezioneFase: idFase}
    })
  }

  updateFaseInfo(fase: any){
    return this.http.post(`${environment.protocol}://${environment.host}:${environment.port}/ispezioni/updateFaseInfo`, fase)
  }

  getEsitiFase(idIspezione: string | number) {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/ispezioni/getEsitiFaseTree`, {
      params: {idIspezione: idIspezione}
    })
  }

  getMotiviIspezione() {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/ispezioni/getMotiviIspezione`)
  }

  getMacchine() {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/ispezioni/getMacchine`)
  }

  getCantieriAttivi() {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/ispezioni/getCantieriAttivi`)
  }

  getCantiereImpreseSedi(idCantiere: string | number) {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/ispezioni/getMacchine`, {
      params: {idCantiere: idCantiere}
    })
  }

  getModuli() {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/ispezioni/getModuli`)
  }

  insVerbale(idModulo: any, idIspezioneFase: any) {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/ispezioni/insertVerbaleFase`, {
      params: {idModulo: idModulo, idIspezioneFase: idIspezioneFase}
    })
  }

  deleteVerbale(idVerbale: any) {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/verbali/deleteVerbale`, {
      params: {idVerbale: idVerbale}
    })
  }

}
