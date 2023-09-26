import {animate, AnimationTriggerMetadata, state, style, transition, trigger} from "@angular/animations";

const ANIMATION_TIMING: number = 500;

export const Fade = (timing: number = ANIMATION_TIMING): AnimationTriggerMetadata => {
  return trigger('fade', [
    state('void', style({opacity: 0})),

    transition(':enter, :leave', [
      animate(timing)
    ])
  ]);
};
