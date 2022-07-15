/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {NgbActiveModal, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserModule } from './user/user.module';
import { VerbaliModule } from './verbali/verbali.module';
import { UtilsModule } from './utils/utils.module';
import { SpinnersAngularModule  } from 'spinners-angular';
import { LoginModule } from './login/login.module';
import { HeaderComponent } from './layout/header/header.component';
import { IspezioniModule } from './ispezioni/ispezioni.module';
import { AFormModule } from './utils/modules/a-form/a-form.module';
import { TokenInterceptor } from './utils/utils.httpInterceptor';
import { FooterComponent } from './layout/footer/footer.component';
import { SupportoComponent } from './layout/supporto/supporto.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SupportoService } from './layout/supporto/supporto.service';
import { ASmartTableModule } from './utils/modules/a-smart-table/a-smart-table.module';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    SupportoComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule,
    UserModule,
    VerbaliModule,
    UtilsModule,
    SpinnersAngularModule,
    LoginModule,
    IspezioniModule,
    ASmartTableModule,
    AFormModule,
    FormsModule,
    ReactiveFormsModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      //enabled: environment.production,
      // Register the ServiceWorker as soon as the app is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    })
  ],
  providers: [
    NgbActiveModal,
    SupportoService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
