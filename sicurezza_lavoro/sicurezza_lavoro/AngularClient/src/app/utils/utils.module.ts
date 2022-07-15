import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ATemplateDirective } from './directives/a-template.directive';
import { AutofillerDirective } from "./directives/autofiller.directive";
import { AMultiSelectComponent } from './components/a-multi-select/a-multi-select.component';
import { ANavigatorModule } from "./modules/a-navigator/a-navigator.module";
import { SandboxModule } from "./modules/_sandbox/sandbox.module";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { AutoscrollDirective } from "./directives/autoscroll.directive";

@NgModule({
  declarations: [
    ATemplateDirective,
      AutofillerDirective,
      AutoscrollDirective,
      AMultiSelectComponent
  ],
  imports: [
      CommonModule,
      NgbModule,
      ANavigatorModule,
      SandboxModule,
  ],
  exports: [
      NgbModule,
      ATemplateDirective,
      AutofillerDirective,
      AutoscrollDirective,
      AMultiSelectComponent,
      ANavigatorModule,
      SandboxModule
  ]
})
export class UtilsModule { }
