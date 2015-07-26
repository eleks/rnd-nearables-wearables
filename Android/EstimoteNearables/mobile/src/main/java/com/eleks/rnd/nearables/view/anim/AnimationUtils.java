package com.eleks.rnd.nearables.view.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by bogdan.melnychuk on 09.12.2014.
 */
public final class AnimationUtils {
    public static final void animateBounceIn(final View target) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1, 1, 1),
                ObjectAnimator.ofFloat(target, "scaleX", 0.3f, 1.05f, 0.9f, 1),
                ObjectAnimator.ofFloat(target, "scaleY", 0.3f, 1.05f, 0.9f, 1)
        );
        set.setDuration(500);
        target.setVisibility(View.VISIBLE);
        set.start();
    }
}
