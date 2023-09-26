import {animate, AnimationTriggerMetadata, state, style, transition, trigger} from "@angular/animations";

const ANIMATION_TIMING: number = 500;

export const SlideLeftToRight = (timing: number = ANIMATION_TIMING): AnimationTriggerMetadata => {
    return trigger('slidelefttoright', [
      state('void', style({transform: 'translateX(-100%)'})),

      transition(':enter', [
        animate(timing)
      ]),
      transition(':leave', [
        animate(timing, style({transform: 'translateX(100%)'}))
      ]),
    ]);
};
