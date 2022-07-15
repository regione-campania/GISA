/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { UserService } from '../user/user.service';
import { catchError, Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { Utils } from 'src/app/utils/utils.class';
declare let Swal: any


@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(
      public us: UserService,
      private router: Router
    ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${this.us.accessToken}`
      },
      withCredentials: true
    });

    return next.handle(request).pipe(catchError(x=> this.handleAuthError(x)));
  }

  private handleAuthError(err: HttpErrorResponse): Observable<any> {
    if (err.status === 401 || err.status === 403) {
        Utils.showSpinner(false);
        Swal.fire({ text: "Sessione scaduta, eseguire login!", icon: 'warning' });
        this.router.navigateByUrl(`/login`);
    }
    return throwError(err);
}
}