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