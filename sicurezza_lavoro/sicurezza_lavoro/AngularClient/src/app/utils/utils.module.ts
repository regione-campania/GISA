/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { AutofillerDirective } from "./directives/autofiller.directive";
import { AMultiSelectComponent } from './components/a-multi-select/a-multi-select.component';
import { ANavigatorModule } from "./modules/a-navigator/a-navigator.module";
import { SandboxModule } from "./modules/_sandbox/sandbox.module";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";


@NgModule({
    declarations: [
        AutofillerDirective,
        AMultiSelectComponent
    ],
    imports: [
        CommonModule,
        NgbModule,
        ANavigatorModule,
        SandboxModule
    ],
    exports: [
        NgbModule,
        AutofillerDirective,
        AMultiSelectComponent,
        ANavigatorModule,
        SandboxModule
    ]
})
export class UtilsModule { }