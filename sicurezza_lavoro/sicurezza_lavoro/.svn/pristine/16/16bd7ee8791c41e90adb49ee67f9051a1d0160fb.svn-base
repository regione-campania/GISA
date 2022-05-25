import { HttpClient, JsonpInterceptor } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from './../../../environments/environment';


@Injectable()
export class SupportoService {

  constructor(
    private http: HttpClient,
  ) {}

  
  sendEmail(_params: any) {
    return this.http.post<any>(`${environment.protocol}://${environment.host}:${environment.port}/um/sendEmailSupporto`, _params)
  }


}
