/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { Directive, Input, OnChanges, SimpleChanges, TemplateRef, Type, ViewContainerRef } from "@angular/core";

@Directive({
    selector: '[viewRender]'
})
export class ViewRenderDirective implements  OnChanges {

    @Input('viewRender') view?: TemplateRef<any> | Type<any>

    constructor(private viewContainer: ViewContainerRef) { }

    ngOnChanges(changes: SimpleChanges): void {
        if(changes['view'].previousValue !== changes['view'].currentValue) {
            if (this.view) {
                this.viewContainer.clear()
                if (this.view instanceof TemplateRef) 
                    this.viewContainer.createEmbeddedView(this.view)
                else if (this.view instanceof Type)
                    this.viewContainer.createComponent(this.view)
                else
                    throw new TypeError("Provide a valid argument")
            }
        }
    }

}