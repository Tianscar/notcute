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

package com.ansdoship.a3wt.util;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;

public class A3Math {

    private A3Math(){}

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
    
    public static int min(final int... args) {
        checkArgNotEmpty(args, "args");
        if (args.length < 2) throw new IllegalArgumentException("at least 2 numbers");
        int min = args[0];
        for (int i = 1; i < args.length; i ++) {
            min = Math.min(min, args[i]);
        }
        return min;
    }

    public static long min(final long... args) {
        checkArgNotEmpty(args, "args");
        if (args.length < 2) throw new IllegalArgumentException("at least 2 numbers");
        long min = args[0];
        for (int i = 1; i < args.length; i ++) {
            min = Math.min(min, args[i]);
        }
        return min;
    }

    public static float min(final float... args) {
        checkArgNotEmpty(args, "args");
        if (args.length < 2) throw new IllegalArgumentException("at least 2 numbers");
        float min = args[0];
        for (int i = 1; i < args.length; i ++) {
            min = Math.min(min, args[i]);
        }
        return min;
    }

    public static double min(final double... args) {
        checkArgNotEmpty(args, "args");
        if (args.length < 2) throw new IllegalArgumentException("at least 2 numbers");
        double min = args[0];
        for (int i = 1; i < args.length; i ++) {
            min = Math.min(min, args[i]);
        }
        return min;
    }

    public static int max(final int... args) {
        checkArgNotEmpty(args, "args");
        if (args.length < 2) throw new IllegalArgumentException("at least 2 numbers");
        int max = args[0];
        for (int i = 1; i < args.length; i ++) {
            max = Math.max(max, args[i]);
        }
        return max;
    }

    public static long max(final long... args) {
        checkArgNotEmpty(args, "args");
        if (args.length < 2) throw new IllegalArgumentException("at least 2 numbers");
        long max = args[0];
        for (int i = 1; i < args.length; i ++) {
            max = Math.max(max, args[i]);
        }
        return max;
    }

    public static float max(final float... args) {
        checkArgNotEmpty(args, "args");
        if (args.length < 2) throw new IllegalArgumentException("at least 2 numbers");
        float max = args[0];
        for (int i = 1; i < args.length; i ++) {
            max = Math.max(max, args[i]);
        }
        return max;
    }

    public static double max(final double... args) {
        checkArgNotEmpty(args, "args");
        if (args.length < 2) throw new IllegalArgumentException("at least 2 numbers");
        double max = args[0];
        for (int i = 1; i < args.length; i ++) {
            max = Math.max(max, args[i]);
        }
        return max;
    }

}