/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ANavigatorComponent } from "./a-navigator.component";
import { ViewLinkDirective } from "./directives/view-link.directive";
import { ViewRenderDirective } from "./directives/view-render.directive";

@NgModule({
    declarations: [
        ANavigatorComponent,
        ViewRenderDirective,
        ViewLinkDirective
    ],
    providers: [],
    imports: [
        CommonModule
    ],
    exports: [
        ANavigatorComponent,
        ViewRenderDirective,
        ViewLinkDirective
    ]
})
export class ANavigatorModule {}