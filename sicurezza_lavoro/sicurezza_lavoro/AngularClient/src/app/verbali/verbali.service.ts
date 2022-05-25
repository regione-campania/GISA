/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { HttpClient, JsonpInterceptor } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from './../../environments/environment';

@Injectable()
export class VerbaliService {

  constructor(private http: HttpClient) {

  }

  getJsonVerbale(idVerbale: number | string) {
    return this.http.get<any>(`http://${environment.host}:${environment.port}/verbali/getJsonVerbale`, {
      params: {idVerbale: idVerbale}
    })
  }

  setJsonVerbale(json: any){
    return this.http.post<any>(`http://${environment.host}:${environment.port}/verbali/setJsonVerbale`, {
      verbale: json
    })
  }

  downloadVerbale(data: any): Observable<Blob> {
    return this.http.request<any>('POST', `${environment.protocol}://${environment.host}:${environment.port}/verbali/getVerbaleCompilato?descrizioneVerbale=verbaleDiSopralluogo`, 
      { body: data, responseType: 'blob' as 'json'})
  }

}
