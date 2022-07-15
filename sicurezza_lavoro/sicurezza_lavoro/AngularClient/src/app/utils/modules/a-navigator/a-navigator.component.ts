/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
import { AfterViewInit, Component, DoCheck, ElementRef, EventEmitter, HostListener, Input, Output, TemplateRef, Type, ViewChild } from '@angular/core';
import { trigger, state, style, animate, transition } from '@angular/animations';

const TRANSITION_TIME = 400
const TRANSITION_TIMING = `${TRANSITION_TIME}ms ease-in-out`

interface View {
  label: string
  content: TemplateRef<any> | Type<any>
}

interface NavigatorView extends View {
  state?: any
}

@Component({
  selector: 'a-navigator',
  templateUrl: './a-navigator.component.html',
  styleUrls: ['./a-navigator.component.scss'],
  animations: [
    trigger('flyInOut', [
      state('in', style({
        transform: 'translateX(0)'
       })),
      state('out-to-left', style({
        opacity: 0,
        transform: 'translateX(-100%)'
      })),
      state('out-to-right', style({
        opacity: 0,
        transform: 'translateX(100%)'
      })),
      transition('void => first', style({
        opacity: 1,
        transform: 'translateX(0)'
      })),
      transition('void => in', [
        style({
          opacity: 0,
          transform: 'translateX(100%)'
        }),
        animate(TRANSITION_TIMING, style({
          opacity: 1,
          transform: 'translateX(0)'
         })),
      ]),
      transition('* => out-to-left', [
        style({
          opacity: 0.66,
          transform: 'translateX(0)'
         }),
        animate(TRANSITION_TIMING, style({
          opacity: 0,
          transform: 'translateX(-100%)'
        }))
      ]),
      transition('* => out-to-right', [
        style({
          opacity: 0.66,
          transform: 'translateX(0)'
         }),
        animate(TRANSITION_TIMING, style({
          opacity: 0,
          transform: 'translateX(100%)'
        }))
      ]),
      transition('out-to-left => in', [
        style({
          opacity: 0,
          transform: 'translateX(-100%)'
         }),
        animate(TRANSITION_TIMING, style({
          opacity: 1,
          transform: 'translateX(0)'
        }))
      ]),
      transition('out-to-right => in', [
        style({
          opacity: 0,
          transform: 'translateX(100%)'
         }),
        animate(TRANSITION_TIMING, style({
          opacity: 1,
          transform: 'translateX(0)'
        }))
      ])
    ])
  ]
})
export class ANavigatorComponent implements DoCheck, AfterViewInit {

  @ViewChild('mainContainer') mainContainer?: ElementRef
  @Input() views?: View[]
  @Input() header?: View
  @Output() dismiss = new EventEmitter()

  isModal = false

  private _activeView?: NavigatorView
  get activeView() { return this._activeView! ?? undefined }
  private set activeView(view: NavigatorView) { this._activeView = view }

  private _viewsPath: NavigatorView[] = []
  get viewsPath() { return this._viewsPath }
  private set viewsPath(views: NavigatorView[]) { this._viewsPath = views}


  constructor(private elementRef: ElementRef) { }

  getInstance() {
    return this
  }

  private _initialized = false
  get initialized() { return this._initialized }
  private set initialized(val: boolean) { this._initialized = val}

  ngDoCheck(): void {
    if(!this._initialized && this.views) {
      this.isModal = this.elementRef.nativeElement.parentElement.className.includes('modal')
      this.elementRef.nativeElement.parentElement.style.position = 'relative'
      this.viewsPath.push(this.views[0])
      this.activeView = this.views[0]
      this.activeView.state = 'first'
      this.initialized = true
    }
  }

  ngAfterViewInit(): void {
    this._updateSize()
    window.addEventListener('resize', () => this._updateSize)
  }

  @HostListener('click', ['$event']) onClick(event: any) {
    //event.preventDefault()
    event.stopPropagation()
    let eventPath = event.composedPath()
    let t = eventPath[0]
    for(let i = 1; t !== this.mainContainer?.nativeElement && !(t.hasAttribute('viewLink')); i++)
      t = eventPath[i]
    if(t.hasAttribute('viewLink'))
      this.openView(t.getAttribute('viewLink'))
  }


  openView(label: any) {
    let nextView = this.views?.find(v => v.label === label) as NavigatorView
    if(nextView) {
      if(!this.viewsPath.includes(nextView))
        this.viewsPath.push(nextView)
      if(this.viewsPath.indexOf(nextView) > this.viewsPath.indexOf(this.activeView)) {
        this.activeView.state = 'out-to-left'
      }
      else if(this.viewsPath.indexOf(nextView) < this.viewsPath.indexOf(this.activeView)) {
        this.activeView.state = 'out-to-right'
        setTimeout(() => this.viewsPath = this.viewsPath.slice(0, this.viewsPath.indexOf(nextView!)+1), TRANSITION_TIME)
      }
      nextView.state = 'in'
      this.activeView = nextView
    }
  }

  private _updateSize() {
    let elem = this.mainContainer?.nativeElement as Element
    let elemStyle = window.getComputedStyle(elem)
    this.elementRef.nativeElement.parentElement.style.width = elemStyle.width
    this.elementRef.nativeElement.parentElement.style.height = elemStyle.height
  }

}
