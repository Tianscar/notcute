/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.ansdoship.a3wt.util;

import java.text.CharacterIterator;
import java.util.Arrays;

public class A3CharSegment implements Cloneable, CharacterIterator, CharSequence {

    private final char[] value;
    private final int offset;
    private final int count;
    private boolean isPartial;
    private int pos;

    public static A3CharSegment fromCharSequence(final CharSequence seq) {
        if (seq == null) throw new IllegalArgumentException("seq == NULL!");
        if (seq instanceof A3CharSegment) return (A3CharSegment) seq;
        final int len = seq.length();
        final char[] buf = new char[len];
        for (int i = 0; i < len; i ++) {
            buf[i] = seq.charAt(i);
        }
        return new A3CharSegment(buf, 0, len);
    }

    public static A3CharSegment fromCharacterIterator(final CharacterIterator iterator, final int begin, final int end) {
        if (iterator == null) throw new IllegalArgumentException("iterator == NULL!");
        char[] buf = new char[end - begin];
        iterator.setIndex(begin);
        for (int i = begin; i < end; i++) {
            buf[i] = iterator.current();
            iterator.next();
        }
        return new A3CharSegment(buf, 0, buf.length);
    }

    public static A3CharSegment fromCharacterIterator(final CharacterIterator iterator) {
        if (iterator == null) throw new IllegalArgumentException("iterator == NULL!");
        if (iterator instanceof A3CharSegment) return (A3CharSegment) iterator;
        return fromCharacterIterator(iterator, iterator.getBeginIndex(), iterator.getEndIndex());
    }

    public A3CharSegment(final A3CharSegment segment) {
        if (segment == null) throw new IllegalArgumentException("segment == NULL!");
        value = segment.value;
        offset = segment.offset;
        count = segment.count;
        pos = segment.pos;
        isPartial = segment.isPartial;
    }

    public A3CharSegment(final char[] value, final int offset, final int count) {
        if (value == null) throw new IllegalArgumentException("value == NULL!");
        if (offset < 0 || offset + count > value.length) throw new StringIndexOutOfBoundsException();
        this.value = value;
        this.offset = offset;
        this.count = count;
        this.pos = 0;
        this.isPartial = false;
    }

    public char[] array() {
        return value;
    }

    public int arrayOffset() {
        return offset;
    }

    @Override
    public Object clone() {
        Object clone;

        try {
            clone = super.clone();
        } catch (final CloneNotSupportedException e) {
            clone = null;
        }

        return clone;
    }

    public char current() {
        if (pos < 0 || pos >= count + offset) {
            return DONE;
        }
        return value[pos];
    }

    public char first() {
        pos = offset;

        if (length() == 0) {
            return DONE;
        }

        return value[pos];
    }

    public int getBeginIndex() {
        return offset;
    }

    public int getEndIndex() {
        return offset + count;
    }

    public int getIndex() {
        return pos;
    }

    public boolean isPartialReturn() {
        return isPartial;
    }

    public char last() {
        if (length() == 0) {
            pos = offset + count;
            return DONE;
        }

        pos = offset + count - 1;

        return value[pos];
    }

    public char next() {
        pos++;

        if (pos >= offset + count) {
            pos = offset + count;
            return DONE;
        }

        return value[pos];
    }

    public char previous() {
        if (pos == offset) {
            return DONE;
        }

        return value[--pos];
    }

    public char setIndex(final int position) {
        if (position < 0 || position > offset + count) {
            throw new IllegalArgumentException("Invalid position: " + position); //$NON-NLS-1$
        }

        pos = position;

        if (position == offset + count) {
            return DONE;
        }

        return value[pos];
    }

    public void setPartialReturn(final boolean p) {
        isPartial = p;
    }

    @Override
    public String toString() {
        return new String(value, offset, count);
    }

    public CharSequence subSequence(int start, int end) {
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        }
        else if (end > count) {
            throw new StringIndexOutOfBoundsException(end);
        }
        else if (start > end) {
            throw new StringIndexOutOfBoundsException(end - start);
        }
        return new A3CharSegment(value, offset + start, end - start);
    }

    public char charAt(int index) {
        if (index < 0 || index >= count) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return value[offset + index];
    }

    public int length() {
        return count;
    }

    /**
     * Copies the specified characters in this segment to the character array
     * starting at the specified offset in the character array.
     *
     * @param start
     *            the starting offset of characters to copy.
     * @param end
     *            the ending offset of characters to copy.
     * @param buffer
     *            the destination character array.
     * @param index
     *            the starting offset in the character array.
     * @throws NullPointerException
     *             if {@code buffer} is {@code null}.
     * @throws IndexOutOfBoundsException
     *             if {@code start < 0}, {@code end > length()}, {@code start >
     *             end}, {@code index < 0}, {@code end - start > buffer.length -
     *             index}
     */
    public void getChars(int start, int end, char[] buffer, int index) {
        // NOTE last character not copied!
        // Fast range check.
        if (0 <= start && start <= end && end <= count) {
            System.arraycopy(value, start + offset, buffer, index, end - start);
        } else {
            throw new StringIndexOutOfBoundsException();
        }
    }

    public void fillZero() {
        Arrays.fill(value, offset, offset + count, '\0');
    }

}
