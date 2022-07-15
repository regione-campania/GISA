/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from './../../environments/environment';

@Injectable()
export class AnagraficaService {

  constructor(private http: HttpClient) {}

  getComuni() {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/anagrafiche/getComuni`)
  }

  getComuniCantiere() {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/anagrafiche/getComuniCantiere`)
  }

  getImprese() {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/anagrafiche/getImprese`)
  }

  getSoggettiFisici() {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/anagrafiche/getSoggettiFisici`)
  }

  getEntiUnitaOperative() {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/anagrafiche/getEntiUnitaOperative`)
  }

  getEntiUnitaOperativeTree() {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/anagrafiche/getEntiUnitaOperativeTree`)
  }

  getImpreseSedi() {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/anagrafiche/getImpreseSedi`)
  }

  getStatiNotifica() {
    return this.http.get(`${environment.protocol}://${environment.host}:${environment.port}/anagrafiche/getStatiNotifica`)
  }

}
