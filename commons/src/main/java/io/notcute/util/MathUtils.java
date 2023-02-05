/*
 * MIT License
 *
 * Copyright (c) 2021 Tianscar
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.notcute.util;

import java.util.Objects;

public final class MathUtils {

    private MathUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method takes a numerical value and ensures it fits in a given numerical range. If the
     * number is smaller than the minimum required by the range, then the minimum of the range will
     * be returned. If the number is higher than the maximum allowed by the range then the maximum
     * of the range will be returned.
     *
     * @param value the value to be clamped.
     * @param min minimum resulting value.
     * @param max maximum resulting value.
     *
     * @return the clamped value.
     */
    public static int clamp(final int value, final int min, final int max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }
        return value;
    }

    /**
     * This method takes a numerical value and ensures it fits in a given numerical range. If the
     * number is smaller than the minimum required by the range, then the minimum of the range will
     * be returned. If the number is higher than the maximum allowed by the range then the maximum
     * of the range will be returned.
     *
     * @param value the value to be clamped.
     * @param min minimum resulting value.
     * @param max maximum resulting value.
     *
     * @return the clamped value.
     */
    public static long clamp(final long value, final long min, final long max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }
        return value;
    }

    /**
     * This method takes a numerical value and ensures it fits in a given numerical range. If the
     * number is smaller than the minimum required by the range, then the minimum of the range will
     * be returned. If the number is higher than the maximum allowed by the range then the maximum
     * of the range will be returned.
     *
     * @param value the value to be clamped.
     * @param min minimum resulting value.
     * @param max maximum resulting value.
     *
     * @return the clamped value.
     */
    public static float clamp(final float value, final float min, final float max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }
        return value;
    }

    /**
     * This method takes a numerical value and ensures it fits in a given numerical range. If the
     * number is smaller than the minimum required by the range, then the minimum of the range will
     * be returned. If the number is higher than the maximum allowed by the range then the maximum
     * of the range will be returned.
     *
     * @param value the value to be clamped.
     * @param min minimum resulting value.
     * @param max maximum resulting value.
     *
     * @return the clamped value.
     */
    public static double clamp(final double value, final double min, final double max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }
        return value;
    }

    public static int clampInt(final long value) {
        return (int) clamp(value, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static float clampFloat(final double value) {
        return (float) clamp(value, Float.MIN_VALUE, Float.MAX_VALUE);
    }
    
    public static int min(final int... args) {
        if (Objects.requireNonNull(args).length < 2) throw new IllegalArgumentException("at least 2 numbers");
        int min = args[0];
        for (int i = 1; i < args.length; i ++) {
            min = Math.min(min, args[i]);
        }
        return min;
    }

    public static long min(final long... args) {
        if (Objects.requireNonNull(args).length < 2) throw new IllegalArgumentException("at least 2 numbers");
        long min = args[0];
        for (int i = 1; i < args.length; i ++) {
            min = Math.min(min, args[i]);
        }
        return min;
    }

    public static float min(final float... args) {
        if (Objects.requireNonNull(args).length < 2) throw new IllegalArgumentException("at least 2 numbers");
        float min = args[0];
        for (int i = 1; i < args.length; i ++) {
            min = Math.min(min, args[i]);
        }
        return min;
    }

    public static double min(final double... args) {
        if (Objects.requireNonNull(args).length < 2) throw new IllegalArgumentException("at least 2 numbers");
        double min = args[0];
        for (int i = 1; i < args.length; i ++) {
            min = Math.min(min, args[i]);
        }
        return min;
    }

    public static int max(final int... args) {
        if (Objects.requireNonNull(args).length < 2) throw new IllegalArgumentException("at least 2 numbers");
        int max = args[0];
        for (int i = 1; i < args.length; i ++) {
            max = Math.max(max, args[i]);
        }
        return max;
    }

    public static long max(final long... args) {
        if (Objects.requireNonNull(args).length < 2) throw new IllegalArgumentException("at least 2 numbers");
        long max = args[0];
        for (int i = 1; i < args.length; i ++) {
            max = Math.max(max, args[i]);
        }
        return max;
    }

    public static float max(final float... args) {
        if (Objects.requireNonNull(args).length < 2) throw new IllegalArgumentException("at least 2 numbers");
        float max = args[0];
        for (int i = 1; i < args.length; i ++) {
            max = Math.max(max, args[i]);
        }
        return max;
    }

    public static double max(final double... args) {
        if (Objects.requireNonNull(args).length < 2) throw new IllegalArgumentException("at least 2 numbers");
        double max = args[0];
        for (int i = 1; i < args.length; i ++) {
            max = Math.max(max, args[i]);
        }
        return max;
    }

    /**
     * Returns the float conversion of the most positive (i.e. closest to
     * positive infinity) integer value which is less than the argument.
     *
     * @param value to be converted
     * @return the floor of value
     */
    public static float floor(final float value) {
        return (float) Math.floor(value);
    }

    /**
     * Returns the float conversion of the most negative (i.e. closest to
     * negative infinity) integer value which is greater than the argument.
     *
     * @param value to be converted
     * @return the ceiling of value
     */
    public static float ceil(final float value) {
        return (float) Math.ceil(value);
    }

    /**
     * Returns the closest float approximation of the sine of the argument.
     *
     * @param angle to compute the cosine of, in radians
     * @return the sine of angle
     */
    public static float sin(final float angle) {
        return (float) Math.sin(angle);
    }

    /**
     * Returns the closest float approximation of the cosine of the argument.
     *
     * @param angle to compute the cosine of, in radians
     * @return the cosine of angle
     */
    public static float cos(final float angle) {
        return (float) Math.cos(angle);
    }

    /**
     * Returns the closest float approximation of the square root of the
     * argument.
     *
     * @param value to compute sqrt of
     * @return the square root of value
     */
    public static float sqrt(final float value) {
        return (float) Math.sqrt(value);
    }

    /**
     * Returns the closest float approximation of the raising "e" to the power
     * of the argument.
     *
     * @param value to compute the exponential of
     * @return the exponential of value
     */
    public static float exp(final float value) {
        return (float) Math.exp(value);
    }

    /**
     * Returns the closest float approximation of the result of raising {@code
     * x} to the power of {@code y}.
     *
     * @param x the base of the operation.
     * @param y the exponent of the operation.
     * @return {@code x} to the power of {@code y}.
     */
    public static float pow(final float x, final float y) {
        return (float) Math.pow(x, y);
    }

    /**
     * Returns {@code sqrt(}<i>{@code x}</i><sup>{@code 2}</sup>{@code +} <i>
     * {@code y}</i><sup>{@code 2}</sup>{@code )}.
     *
     * @param x a float number
     * @param y a float number
     * @return the hypotenuse
     */
    public static float hypot(final float x, final float y) {
        return (float) Math.hypot(x, y);
    }

    /**
     * Returns the arc cosine of a value; the returned angle is in the
     * range 0.0 through <i>pi</i>.  Special case:
     * <ul><li>If the argument is NaN or its absolute value is greater
     * than 1, then the result is NaN.
     * <li>If the argument is {@code 1.0}, the result is positive zero.
     * </ul>
     *
     * <p>The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     *
     * @param   a   the value whose arc cosine is to be returned.
     * @return  the arc cosine of the argument.
     */
    public static float acos(final float a) {
        return (float) Math.acos(a);
    }

    public static float toRadians(final float angdeg) {
        return (float) Math.toRadians(angdeg);
    }

    public static float toDegrees(final float angrad) {
        return (float) Math.toDegrees(angrad);
    }

    public static float atan2(final float y, final float x) {
        return (float) Math.atan2(y, x);
    }

}