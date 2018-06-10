/*
* Created by Jason Kisch
* Copyright (c) 2018. All rights reserved.
* Licensed under the GNU General Public License v3.0
*/
package com.jaybionic.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.jaybionic.utils.Logs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.View.DRAWING_CACHE_QUALITY_HIGH;
import static android.view.View.LAYER_TYPE_HARDWARE;

/**
 * <h1>Brutal Beast Animation Destroyer (BBAD)</h1>
 * Animation shortcuts for one line animations
 * @code {BBAD.to(view, 500, 100, "translationX", Easing.easeOutExpo).start();}
 *
 * @author Jason Kisch (jckisch@gmail.com)
 * @version 2.0
 * @see <a href="">BBAD-Android Repo</a>
 */

public class BBAD {


    //region BBAD.to
    /**
     * Animate a Views' property TO a given value
     *
     * @param target    View to be animated
     * @param dur       duration of the animation
     * @param to        end value
     * @param prop      property of target to be animated
     * @param del {long} [del = 0] - delay of animation
     * @param ease {Interpolator} [ease = null] - easing curve
     * @param callback {OnCompleteListener} [callback = null] - function to call when animation has completed
     *
     * @return An ObjectAnimator with the animation ready to start
     */
    public static ObjectAnimator to(final View target, long dur, float to,
                                    String prop, long del, Interpolator ease,
                                    final OnCompleteListener callback) {
        ObjectAnimator obj = ObjectAnimator.ofFloat(target, prop, to);
        obj.setDuration(dur);
        obj.setStartDelay(del);
        if(ease != null) { obj.setInterpolator(ease); }
        obj.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(callback != null) { callback.onComplete(target); }
            }
            public void onAnimationStart(Animator animation) {}
            public void onAnimationCancel(Animator animation) {}
            public void onAnimationRepeat(Animator animation) {}
        });
        return obj;
    }
    public static ObjectAnimator to(View target, long dur, float to, String prop, Interpolator ease, final OnCompleteListener callback) {
        return to(target, dur, to, prop, 0, ease, callback);
    }
    public static ObjectAnimator to(View target, long dur, float to, String prop, long del, Interpolator ease) {
        return to(target, dur, to, prop, del, ease, null);
    }
    public static ObjectAnimator to(View target, long dur, float to, String prop, Interpolator ease) {
        return to(target, dur, to, prop, 0, ease, null);
    }
    public static ObjectAnimator to(View target, long dur, float to, String prop, long del) {
        return to(target, dur, to, prop, del, null, null);
    }
    public static ObjectAnimator to(View target, long dur, float to, String prop, long del, final OnCompleteListener callback) {
        return to(target, dur, to, prop, del, null, callback);
    }
    public static ObjectAnimator to(View target, long dur, float to, String prop, final OnCompleteListener callback) {
        return to(target, dur, to, prop, 0, null, callback);
    }
    public static ObjectAnimator to(View target, long dur, float to, String prop) {
        return to(target, dur, to, prop, 0, null, null);
    }
    //endregion


    //region BBAD.fromTo
    /**
     * Animate a Views' property FROM a value TO a given value
     *
     * @param target    View to be animated
     * @param dur       duration of the animation
     * @param from      start value
     * @param to        end value
     * @param prop      property of target to be animated
     * @param del {long} [del = 0] - delay of animation
     * @param ease {Interpolator} [ease = null] - easing curve
     * @param callback {OnCompleteListener} [callback = null] - function to call when animation has completed
     *
     * @return An ObjectAnimator with the animation ready to start
     */
    public static ObjectAnimator fromTo(final View target, long dur, float from, float to,
                                        String prop, long del, Interpolator ease,
                                        final OnCompleteListener callback) {
        ObjectAnimator obj = ObjectAnimator.ofFloat(target, prop, from, to);
        obj.setDuration(dur);
        obj.setStartDelay(del);
        setProp(target, prop, from);
        if(ease != null) { obj.setInterpolator(ease); }
        obj.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(callback != null) { callback.onComplete(target); }
            }

            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        return obj;
    }
    public static ObjectAnimator fromTo(View target, long dur, float from, float to,
                                        String prop, long del, Interpolator ease) {
        return fromTo(target, dur, from, to, prop, del, ease, null);
    }

    public static ObjectAnimator fromTo(View target, long dur, float from, float to,
                                        String prop, long del) {
        return fromTo(target, dur, from, to, prop, del, null, null);
    }
    public static ObjectAnimator fromTo(View target, long dur, float from, float to,
                                        String prop, Interpolator ease) {
        return fromTo(target, dur, from, to, prop, 0, ease, null);
    }
    public static ObjectAnimator fromTo(View target, long dur, float from, float to,
                                        String prop) {
        return fromTo(target, dur, from, to, prop, 0, null, null);
    }
    //endregion


    //region BBAD.allTo
    /**
     * Animate all Views' inside a List<View> TO a given value
     *
     * @param targets   List<View> of items to be animated
     * @param dur       duration of the animation
     * @param to        end value
     * @param prop      property of target to be animated
     * @param del {long} [del = 0] - delay of animation
     * @param ease {Interpolator} [ease = null] - easing curve
     * @param callback {OnCompleteListener} [callback = null] - function to call when animation has completed
     *
     * @return An AnimatorSet with the animation ready to start
     */
    public static AnimatorSet allTo(List<View> targets, long dur, float to,
                                    String prop, long del, Interpolator ease,
                                    final OnCompleteListener callback) {
        AnimatorSet set = new AnimatorSet();
        List<Animator> col = new ArrayList<>();
        View v;
        for(int i=0; i<targets.size(); i++) {
            v = targets.get(i);
            col.add(BBAD.to(v, dur, to, prop, 0, ease, null));
        }
        set.playTogether(col);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(callback != null) { callback.onComplete(null); }
            }
        });
        set.setStartDelay(del);
        return set;
    }
    public static AnimatorSet allTo(List<View> targets, long dur, float to,
                                    String prop, long del, Interpolator ease) {
        return allTo(targets, dur, to, prop, del, ease, null);
    }
    public static AnimatorSet allTo(List<View> targets, long dur, float to,
                                    String prop, Interpolator ease) {
        return allTo(targets, dur, to, prop, 0, ease, null);
    }
    public static AnimatorSet allTo(List<View> targets, long dur, float to,
                                    String prop, Interpolator ease,
                                    final OnCompleteListener callback) {
        return allTo(targets, dur, to, prop, 0, ease, callback);
    }
    public static AnimatorSet allTo(List<View> targets, long dur, float to,
                                    String prop, long del) {
        return allTo(targets, dur, to, prop, del, null, null);
    }
    public static AnimatorSet allTo(List<View> targets, long dur, float to,
                                    String prop, long del,
                                    final OnCompleteListener callback) {
        return allTo(targets, dur, to, prop, del, null, callback);
    }
    public static AnimatorSet allTo(List<View> targets, long dur, float to,
                                    String prop) {
        return allTo(targets, dur, to, prop, 0, null, null);
    }
    public static AnimatorSet allTo(List<View> targets, long dur, float to,
                                    String prop,
                                    final OnCompleteListener callback) {
        return allTo(targets, dur, to, prop, 0, null, callback);
    }
    //endregion

    //region BBAD.allFromTo
    /**
     * Animate all Views' inside a List<View> FROM a value, TO a given value
     *
     * @param targets   List<View> of items to be animated
     * @param dur       duration of the animation
     * @param from      start value
     * @param to        end value
     * @param prop      property of target to be animated
     * @param del       {long} [del = 0] - delay of animation
     * @param ease      {Interpolator} [ease = null] - easing curve
     * @param callback  {OnCompleteListener} [callback = null] - function to call when animation has completed
     *
     * @return An AnimatorSet with the animation ready to start
     */
    public static AnimatorSet allFromTo(List<View> targets, long dur,
                                        float from, float to,
                                        String prop, long del, Interpolator ease,
                                        final OnCompleteListener callback) {
        AnimatorSet set = new AnimatorSet();
        List<Animator> col = new ArrayList<>();
        View v;
        for(int i=0; i<targets.size(); i++) {
            v = targets.get(i);
            col.add(BBAD.fromTo(v, dur, from, to, prop, 0, ease, null));
        }
        set.playTogether(col);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(callback != null) { callback.onComplete(null); }
            }
        });
        set.setStartDelay(del);
        return set;
    }
    public static AnimatorSet allFromTo(List<View> targets, long dur,
                                        float from, float to,
                                        String prop, long del, Interpolator ease) {
        return allFromTo(targets, dur, from, to, prop, del, ease, null);
    }
    public static AnimatorSet allFromTo(List<View> targets, long dur,
                                        float from, float to,
                                        String prop, Interpolator ease) {
        return allFromTo(targets, dur, from, to, prop, 0, ease, null);
    }
    public static AnimatorSet allFromTo(List<View> targets, long dur,
                                        float from, float to,
                                        String prop, Interpolator ease,
                                        final OnCompleteListener callback) {
        return allFromTo(targets, dur, from, to, prop, 0, ease, callback);
    }
    public static AnimatorSet allFromTo(List<View> targets, long dur,
                                        float from, float to,
                                        String prop, long del) {
        return allFromTo(targets, dur, from, to, prop, del, null, null);
    }
    public static AnimatorSet allFromTo(List<View> targets, long dur,
                                        float from, float to,
                                        String prop, long del,
                                        final OnCompleteListener callback) {
        return allFromTo(targets, dur, from, to, prop, del, null, callback);
    }
    public static AnimatorSet allFromTo(List<View> targets, long dur,
                                        float from, float to,
                                        String prop) {
        return allFromTo(targets, dur, from, to, prop, 0, null, null);
    }
    public static AnimatorSet allFromTo(List<View> targets, long dur,
                                        float from, float to,
                                        String prop,
                                        final OnCompleteListener callback) {
        return allFromTo(targets, dur, from, to, prop, 0, null, callback);
    }
    //endregion


    //region BBAD.staggerTo
    /**
     * Stagger a ViewGroup TO a given value
     *
     * @param targets   List<View> of items to be animated
     * @param dur       duration of the animation
     * @param to        end value
     * @param prop      property of target to be animated
     * @param del {long} [del = 0] - delay of animation
     * @param stagger delay between targets animating
     * @param ease {Interpolator} [ease = null] - easing curve
     * @param callback {OnCompleteListener} [callback = null] - function to call when animation has completed
     *
     * @return An AnimatorSet with the animation ready to start
     */
    public static AnimatorSet staggerTo(List<View> targets, long dur, float to,
                                        String prop, long del, long stagger, Interpolator ease,
                                        final OnCompleteListener callback) {
        AnimatorSet set = new AnimatorSet();
        List<Animator> col = new ArrayList<>();
        View v;
        for(int i=0; i<targets.size(); i++) {
            v = targets.get(i);
            col.add(BBAD.to(v, dur, to, prop, ((i * stagger) + del), ease, null));
        }
        set.playTogether(col);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(callback != null) { callback.onComplete(null); }
            }
        });
        return set;
    }

    public static AnimatorSet staggerTo(List<View> targets, long dur, float to,
                                         String prop, long del, long stagger,
                                         final OnCompleteListener callback) {

        return staggerTo(targets, dur, to, prop, del, stagger, null, callback);
    }
    public static AnimatorSet staggerTo(List<View> targets, long dur, float to,
                                        String prop, long stagger,
                                        Interpolator ease) {

        return staggerTo(targets, dur, to, prop, 0, stagger, ease, null);
    }
    public static AnimatorSet staggerTo(List<View> targets, long dur, float to,
                                        String prop, long del, long stagger) {

        return staggerTo(targets, dur, to, prop, del, stagger, null, null);
    }
    public static AnimatorSet staggerTo(List<View> targets, long dur, float to,
                                        String prop, long del, long stagger,
                                        Interpolator ease) {
        Logs.i("del: " + del + " :: " + stagger);
        return staggerTo(targets, dur, to, prop, del, stagger, ease, null);
    }
    public static AnimatorSet staggerTo(List<View> targets, long dur, float to,
                                        String prop, long stagger, Interpolator ease,
                                        final OnCompleteListener callback) {

        return staggerTo(targets, dur, to, prop, 0, stagger, ease, callback);
    }
    public static AnimatorSet staggerTo(List<View> targets, long dur, float to,
                                        String prop, long stagger,
                                        final OnCompleteListener callback) {

        return staggerTo(targets, dur, to, prop, 0, stagger, null, callback);
    }
    public static AnimatorSet staggerTo(List<View> targets, long dur, float to,
                                        String prop, long stagger) {

        return staggerTo(targets, dur, to, prop, 0, stagger, null,  null);
    }
    //endregion

    //region BBAD.staggerFromTo
    /**
     * Stagger a ViewGroup FROM a value TO a given value
     *
     * @param targets   List<View> of items to be animated
     * @param dur       duration of the animation
     * @param from      start value
     * @param to        end value
     * @param prop      property of target to be animated
     * @param del {long} [del = 0] - delay of animation
     * @param stagger delay between targets animating
     * @param ease {Interpolator} [ease = null] - easing curve
     * @param callback {OnCompleteListener} [callback = null] - function to call when animation has completed
     *
     * @return An AnimatorSet with the animation ready to start
     */
    public static AnimatorSet staggerFromTo(List<View> targets, long dur, float from, float to,
                                             String prop, long del, long stagger, Interpolator ease,
                                             final OnCompleteListener callback) {
        AnimatorSet set = new AnimatorSet();
        List<Animator> col = new ArrayList<>();
        View v;
        for(int i=0; i<targets.size(); i++) {
            v = targets.get(i);
            col.add(BBAD.fromTo(v, dur, from, to, prop, ((i * stagger) + del), ease));
        }
        set.playTogether(col);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(callback != null) { callback.onComplete(null); }
            }
        });
        return set;
    }
    public static AnimatorSet staggerFromTo(List<View> targets, long dur, float from,  float to,
                                        String prop, long del, long stagger,
                                        final OnCompleteListener callback) {

        return staggerFromTo(targets, dur, from, to, prop, del, stagger, null, callback);
    }
    public static AnimatorSet staggerFromTo(List<View> targets, long dur, float from,  float to,
                                        String prop, long del, long stagger,
                                        Interpolator ease) {

        return staggerFromTo(targets, dur, from, to, prop, del, stagger, ease, null);
    }
    public static AnimatorSet staggerFromTo(List<View> targets, long dur, float from,  float to,
                                            String prop, long del, long stagger) {

        return staggerFromTo(targets, dur, from, to, prop, del, stagger, null, null);
    }
    public static AnimatorSet staggerFromTo(List<View> targets, long dur, float from,  float to,
                                        String prop, long stagger, Interpolator ease,
                                        final OnCompleteListener callback) {

        return staggerFromTo(targets, dur, from, to, prop, 0, stagger, ease, callback);
    }
    public static AnimatorSet staggerFromTo(List<View> targets, long dur, float from,  float to,
                                        String prop, long stagger,
                                        final OnCompleteListener callback) {

        return staggerFromTo(targets, dur, from, to, prop, 0, stagger, null, callback);
    }
    public static AnimatorSet staggerFromTo(List<View> targets, long dur, float from, float to,
                                            String prop, long stagger,  Interpolator ease) {

        return staggerFromTo(targets, dur, from, to, prop, 0, stagger, ease, null);
    }
    public static AnimatorSet staggerFromTo(List<View> targets, long dur, float from, float to,
                                        String prop, long stagger) {

        return staggerFromTo(targets, dur, from, to, prop, 0, stagger, null, null);
    }
    //endregion


    //region BBAD.resizeTo
    /**
     * Resize a View TO a new Width & Height
     *
     * @param target    View to be animated
     * @param dur       duration of the animation
     * @param width     endvalue of Width
     * @param height    endvalue of Height
     * @param del       {long} [del = 0] - delay of animation
     * @param ease      {Interpolator} [ease = null] - easing curve
     * @param callback  {OnCompleteListener} [callback = null] - function to call when animation has completed
     *
     * @return An ObjectAnimator with the animation ready to start
     */
    public static AnimatorSet resizeTo(final View target, long dur,
                                       int width, int height,
                                       long del, Interpolator ease,
                                       final OnCompleteListener callback) {
        AnimatorSet anim = new AnimatorSet();
        ValueAnimator objW = ValueAnimator.ofInt(target.getWidth(), width);
        ValueAnimator objH = ValueAnimator.ofInt(target.getHeight(), height);
        anim.setDuration(dur);
        anim.setStartDelay(del);
        anim.playTogether(objW, objH);
        objW.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                target.getLayoutParams().width = value.intValue();
                target.requestLayout();
            }
        });
        objH.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                target.getLayoutParams().height = value.intValue();
                target.requestLayout();
            }
        });
        if(ease != null) { anim.setInterpolator(ease); }
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (callback != null) {
                    callback.onComplete(target);
                }
            }
            public void onAnimationStart(Animator animation) {}
            public void onAnimationCancel(Animator animation) {}
            public void onAnimationRepeat(Animator animation) {}
        });
        return anim;
    }
    public static AnimatorSet resizeTo(final View target, long dur,
                                       int width, int height,
                                       long del, Interpolator ease) {
        return resizeTo(target, dur, width, height, del, ease, null);
    }
    public static AnimatorSet resizeTo(final View target, long dur,
                                       int width, int height,
                                       long del,
                                       final OnCompleteListener callback) {
        return resizeTo(target, dur, width, height, del, null, callback);
    }
    public static AnimatorSet resizeTo(final View target, long dur,
                                       int width, int height,
                                       long del) {
        return resizeTo(target, dur, width, height, del, null, null);
    }
    public static AnimatorSet resizeTo(final View target, long dur,
                                       int width, int height,
                                       Interpolator ease,
                                       final OnCompleteListener callback) {
        return resizeTo(target, dur, width, height, 0, ease, callback);
    }
    public static AnimatorSet resizeTo(final View target, long dur,
                                       int width, int height,
                                       Interpolator ease) {
        return resizeTo(target, dur, width, height, 0, ease, null);
    }
    public static AnimatorSet resizeTo(final View target, long dur,
                                       int width, int height,
                                       final OnCompleteListener callback) {
        return resizeTo(target, dur, width, height, 0, null, callback);
    }
    public static AnimatorSet resizeTo(final View target, long dur,
                                       int width, int height) {
        return resizeTo(target, dur, width, height, 0, null, null);
    }
    //endregion

    //region BBAD.resizeAllTo
    /**
     * Resize a List of Views TO a new Width & Height
     *
     * @param targets    View to be animated
     * @param dur       duration of the animation
     * @param width     endvalue of Width
     * @param height    endvalue of Height
     * @param ease      {Interpolator} [ease = null] - easing curve
     *
     * @return An AnimatorSet with the animation ready to start
     */
    public static AnimatorSet resizeAllTo(List<View> targets, long dur,
                                          int width, int height, Interpolator ease) {
        AnimatorSet set = new AnimatorSet();
        List<Animator> col = new ArrayList<>();
        View v;
        for(int i=0; i<targets.size(); i++) {
            v = targets.get(i);
            col.add(BBAD.resizeTo(v, dur, width, height, ease));
        }
        set.playTogether(col);
        return set;
    }
    public static AnimatorSet resizeAllTo(List<View> targets, long dur,
                                          int width, int height) {
        return resizeAllTo(targets, dur, width, height, null);
    }
    //endregion


    //region BBAD.fadeOutAutoOff
    public static ObjectAnimator fadeOutAutoOff(final View target, long dur, long del,
                                                Interpolator ease,
                                                final OnCompleteListener callback) {
        ObjectAnimator obj = ObjectAnimator.ofFloat(target, "alpha", 0);
        obj.setDuration(dur);
        obj.setStartDelay(del);
        if(ease != null) { obj.setInterpolator(ease); }
        obj.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(callback != null) { callback.onComplete(target); }
                target.setVisibility(View.GONE);
            }
            public void onAnimationStart(Animator animation) {}
            public void onAnimationCancel(Animator animation) {}
            public void onAnimationRepeat(Animator animation) {}
        });
        return obj;
    }
    public static ObjectAnimator fadeOutAutoOff(final View target, long dur,
                                                Interpolator ease,
                                                final OnCompleteListener callback) {
        return fadeOutAutoOff(target, dur, 0, ease, callback);
    }
    public static ObjectAnimator fadeOutAutoOff(final View target, long dur, long del) {
        return fadeOutAutoOff(target, dur, del, null, null);
    }
    public static ObjectAnimator fadeOutAutoOff(final View target, long dur, long del,
                                                Interpolator ease) {
        return fadeOutAutoOff(target, dur, del, ease, null);
    }
    public static ObjectAnimator fadeOutAutoOff(final View target, long dur,
                                                Interpolator ease) {
        return fadeOutAutoOff(target, dur, 0, ease, null);
    }
    public static ObjectAnimator fadeOutAutoOff(final View target, long dur,
                                                final OnCompleteListener callback) {
        return fadeOutAutoOff(target, dur, 0, null, callback);
    }
    public static ObjectAnimator fadeOutAutoOff(final View target, long dur) {
        return fadeOutAutoOff(target, dur, 0, null, null);
    }
    //endregion

    //region BBAD.fadeInAutoOn
    public static ObjectAnimator fadeInAutoOn(final View target, long dur, float to, long del,
                                              Interpolator ease,
                                              final OnCompleteListener callback) {
        target.setAlpha(0);
        target.setVisibility(View.VISIBLE);

        ObjectAnimator obj = ObjectAnimator.ofFloat(target, "alpha", to);
        obj.setDuration(dur);
        obj.setStartDelay(del);
        if(ease != null) { obj.setInterpolator(ease); }
        obj.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(callback != null) { callback.onComplete(target); }
            }
            public void onAnimationStart(Animator animation) {

            }
            public void onAnimationCancel(Animator animation) {}
            public void onAnimationRepeat(Animator animation) {}
        });
        return obj;
    }
    public static ObjectAnimator fadeInAutoOn(final View target, long dur,
                                              Interpolator ease,
                                              final OnCompleteListener callback) {
        return fadeInAutoOn(target, dur, 1, 0, ease, callback);
    }
    public static ObjectAnimator fadeInAutoOn(final View target, long dur,
                                              final OnCompleteListener callback) {
        return fadeInAutoOn(target, dur, 1, 0, null, callback);
    }
    public static ObjectAnimator fadeInAutoOn(final View target, long dur) {
        return fadeInAutoOn(target, dur, 1, 0, null, null);
    }
    public static ObjectAnimator fadeInAutoOn(final View target, long dur, long del) {
        return fadeInAutoOn(target, dur, 1, del, null, null);
    }

    public static ObjectAnimator fadeInAutoOn(final View target, long dur, float to,
                                              Interpolator ease,
                                              final OnCompleteListener callback) {
        return fadeInAutoOn(target, dur, to, 0, ease, callback);
    }
    public static ObjectAnimator fadeInAutoOn(final View target, long dur, float to,
                                              final OnCompleteListener callback) {
        return fadeInAutoOn(target, dur, to, 0, null, callback);
    }
    public static ObjectAnimator fadeInAutoOn(final View target, long dur, float to) {
        return fadeInAutoOn(target, dur, to, 0, null, null);
    }
    public static ObjectAnimator fadeInAutoOn(final View target, long dur, float to, long del) {
        return fadeInAutoOn(target, dur, to, del, null, null);
    }
    //endregion


    //region BBAD.initTransform
    public static View initTransform(View target,
                                     float x, float y,
                                     float scaleX, float scaleY,
                                     float pivotX, float pivotY) {
        target.setTranslationX(x);
        target.setTranslationY(y);
        target.setScaleX(scaleX);
        target.setScaleY(scaleY);
        target.setPivotX(pivotX);
        target.setPivotY(pivotY);
        return target;
    }
    public static View initTransform(View target, float x, float y, float scaleX, float scaleY) {
        return initTransform(target, x, y, scaleX, scaleY, (target.getWidth() / 2), (target.getHeight() / 2));
    }
    public static View initTransform(View target, float x, float y) {
        return initTransform(target, x, y, 1, 1, (target.getWidth()/2), (target.getHeight()/2));
    }
    //endregion

    //region BBAD.initTransformMulti
    public static List<View> initTransformMulti(List<View> targets,
                                     float x, float y,
                                     float scaleX, float scaleY,
                                     float pivotX, float pivotY) {
        View v;
        for(int i=0; i<targets.size(); i++) {
            v = targets.get(i);
            initTransform(v, x, y, scaleX, scaleY, pivotX, pivotY);
        }
        return targets;
    }
    public static List<View> initTransformMulti(List<View> targets,
                                                float x, float y,
                                                float scaleX, float scaleY) {
        View v;
        for(int i=0; i<targets.size(); i++) {
            v = targets.get(i);
            initTransform(v, x, y, scaleX, scaleY, (v.getWidth() / 2), (v.getHeight() / 2));
        }
        return targets;
    }
    public static List<View> initTransformMulti(List<View> targets,
                                                float x, float y) {
        View v;
        for(int i=0; i<targets.size(); i++) {
            v = targets.get(i);
            initTransform(v, x, y, 1, 1, (v.getWidth() / 2), (v.getHeight() / 2));
        }
        return targets;
    }
    //endregion


    //region BBAD.autoOn
    public static View autoOn(View v, float alpha) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(alpha);
        return v;
    }
    public static View autoOn(View v) {
        return autoOn(v, 1);
    }
    //endregion

    //region BBAD.autoOnMulti
    public static List<View> autoOnMulti(List<View> l, float alpha) {
        View v;
        for(int i=0; i<l.size(); i++) {
            v = l.get(i);
            v.setVisibility(View.VISIBLE);
            v.setAlpha(alpha);
        }
        return l;
    }
    public static List<View> autoOnMulti(List<View> l) {
        View v;
        for(int i=0; i<l.size(); i++) {
            v = l.get(i);
            autoOn(v, 1);
        }
        return l;
    }
    //endregion


    //region BBAD.autoOff
    public static View autoOff(View v, float alpha) {
        v.setVisibility(View.GONE);
        v.setAlpha(alpha);
        return v;
    }
    public static View autoOff(View v) {
        return autoOff(v, 0);
    }
    //endregion

    //region BBAD.autoOffMulti
    public static List<View> autoOffMulti(List<View> l) {
        View v;
        for(int i=0; i<l.size(); i++) {
            v = l.get(i);
            v.setAlpha(0);
            v.setVisibility(View.GONE);
        }
        return l;
    }
    //endregion


    public static ValueAnimator colorTo(final View target, long dur, int to,
                                        final String prop, long del, Interpolator ease,
                                        final OnCompleteListener callback) {
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(to);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setDuration(dur);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                target.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
            }
        });
        return null;
    }


    //region Helpers
    public static AnimatorSet createSet(ArrayList<Animator> list) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(list);
        return set;
    }
    public static AnimatorSet createSet(ArrayList<Animator> list, final OnCompleteListener callback) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(list);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(callback != null) { callback.onComplete(null); }
            }
        });
        return set;
    }


    public static List<View> getList(ViewGroup v) {
        return getList(v, false);
    }
    public static List<View> getList(ViewGroup v, boolean reverse) {
        List<View> list = new ArrayList<>();

        for(int i=0; i<v.getChildCount(); i++) {
            list.add(v.getChildAt(i));
        }

        if(reverse) { Collections.reverse(list); }

        return list;
    }

    public static void setProp(View view, String prop, float value) {
        switch (prop) {
            case "alpha":
                view.setAlpha(value);
                break;
            case "scaleX":
                view.setScaleX(value);
                break;
            case "scaleY":
                view.setScaleY(value);
                break;
            case "translationX":
                view.setTranslationX(value);
                break;
            case "translationY":
                view.setTranslationY(value);
                break;
            case "rotation":
                view.setTranslationY(value);
                break;
        }
    }
    public static void setPropMulti(List<View> list, String prop, float value) {
        for(View view : list) {
            setProp(view, prop, value);
        }
    }

    public static void setCache(View v) {
        v.setDrawingCacheEnabled(true);
        v.setDrawingCacheQuality(DRAWING_CACHE_QUALITY_HIGH);
        v.setLayerType(LAYER_TYPE_HARDWARE, null);
    }
    public static void setCacheMulti(ViewGroup vg) {
        List<View> l = BBAD.getList(vg);
        for(View v : l) {
            setCache(v);
        }
    }
    public static void setCacheMultiRecursive(ViewGroup vg) {
        List<View> l = BBAD.getList(vg);
        for(View v : l) {
            setCache(v);
        }
    }
    //endregion
}
