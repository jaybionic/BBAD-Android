package com.jaycorp.animation;

import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.animation.Interpolator;

/**
 * <h1>Penner Easing</h1>
 * Easy CSS syle easing equations
 *
 * @author Jason Kisch (jckisch@gmail.com)
 * @version 1.0
 * @since 11/20/2015
 */
public class Easing {

    public static final Interpolator easeInSine = PathInterpolatorCompat.create(0.47f, 0f, 0.745f, 0.715f);
    public static final Interpolator easeOutSine = PathInterpolatorCompat.create(0.39f, 0.575f, 0.565f, 1f);
    public static final Interpolator easeInOutSine = PathInterpolatorCompat.create(0.445f, 0.05f, 0.55f, 0.95f);
    public static final Interpolator easeInQuad = PathInterpolatorCompat.create(0.55f, 0.085f, 0.68f, 0.53f);
    public static final Interpolator easeOutQuad = PathInterpolatorCompat.create(0.25f, 0.46f, 0.45f, 0.94f);
    public static final Interpolator easeInOutQuad = PathInterpolatorCompat.create(0.455f, 0.03f, 0.515f, 0.955f);
    public static final Interpolator easeInCubic = PathInterpolatorCompat.create(0.55f, 0.055f, 0.675f, 0.19f);
    public static final Interpolator easeOutCubic = PathInterpolatorCompat.create(0.215f, 0.61f, 0.355f, 1f);
    public static final Interpolator easeInOutCubic = PathInterpolatorCompat.create(0.645f, 0.045f, 0.355f, 1f);
    public static final Interpolator easeInQuart = PathInterpolatorCompat.create(0.895f, 0.03f, 0.685f, 0.22f);
    public static final Interpolator easeOutQuart = PathInterpolatorCompat.create(0.165f, 0.84f, 0.44f, 1f);
    public static final Interpolator easeInOutQuart = PathInterpolatorCompat.create(0.77f, 0f, 0.175f, 1f);
    public static final Interpolator easeInQuint = PathInterpolatorCompat.create(0.755f, 0.05f, 0.855f, 0.06f);
    public static final Interpolator easeOutQuint = PathInterpolatorCompat.create(0.23f, 1f, 0.32f, 1f);
    public static final Interpolator easeInOutQuint = PathInterpolatorCompat.create(0.86f, 0f, 0.07f, 1f);
    public static final Interpolator easeInExpo = PathInterpolatorCompat.create(0.95f, 0.05f, 0.795f, 0.035f);
    public static final Interpolator easeOutExpo = PathInterpolatorCompat.create(0.19f, 1f, 0.22f, 1f);
    public static final Interpolator easeInOutExpo = PathInterpolatorCompat.create(1f, 0f, 0f, 1f);
    public static final Interpolator easeInCirc = PathInterpolatorCompat.create(0.6f, 0.04f, 0.98f, 0.335f);
    public static final Interpolator easeOutCirc = PathInterpolatorCompat.create(0.075f, 0.82f, 0.165f, 1f);
    public static final Interpolator easeInOutCirc = PathInterpolatorCompat.create(0.785f, 0.135f, 0.15f, 0.86f);
    public static final Interpolator easeInBack = PathInterpolatorCompat.create(0.6f, -0.28f, 0.735f, 0.045f);
    public static final Interpolator easeOutBack = PathInterpolatorCompat.create(0.175f, 0.885f, 0.32f, 1.275f);
    public static final Interpolator easeInOutBack = PathInterpolatorCompat.create(0.68f, -0.55f, 0.265f, 1.55f);

}