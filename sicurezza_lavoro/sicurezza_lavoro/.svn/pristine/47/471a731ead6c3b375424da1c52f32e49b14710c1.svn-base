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
