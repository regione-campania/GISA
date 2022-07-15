import {
  trigger,
  transition,
  style,
  animate,
  state,
} from '@angular/animations';

/* export const slide = trigger('slide', [
  transition(':enter', [
    style({ opacity: 0 }),
    animate('1000ms', style({ opacity: 1 })),
  ]),
  transition(':leave', [animate('1000ms', style({ opacity: 0 }))]),
]); */

/* export const slide = trigger('slide', [
  state('in', style({transition: 'translateX(0)'})),
  state('to-left', style({transform: 'translateX(-100%)'})),
  state('to-right', style({transform: 'translateX(100%)'})),
  transition('* => *', animate('500ms ease-in-out'))
]); */

/* state('in', style({transform: 'translateX(0)'})),
  state('from-left', style({transform: 'translateX(-100%)'})),
  state('from-right', style({transform: 'translateX(100%)'})),
  state('to-left', style({transform: 'translateX(-100%)'})),
  state('to-right', style({transform: 'translateX(100%)'})),
  transition('from-left => in', [
    animate('1000ms')
  ]),
  transition('from-right => in', [
    animate('1000ms')
  ]) */
