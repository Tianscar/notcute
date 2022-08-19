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

public final class Segment implements Cloneable, CharacterIterator, CharSequence {

    private final char[] text;
    private final int offset;
    private final int length;
    private boolean isPartial;
    private int pos;

    public Segment(final char[] text, final int offset, final int length) {
        this.text = text;
        this.offset = offset;
        this.length = length;
        this.pos = 0;
        this.isPartial = false;
    }

    public char[] array() {
        return text;
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
        if (pos < 0 || pos >= length + offset) {
            return DONE;
        }
        return text[pos];
    }

    public char first() {
        pos = offset;

        if (length() == 0) {
            return DONE;
        }

        return text[pos];
    }

    public int getBeginIndex() {
        return offset;
    }

    public int getEndIndex() {
        return offset + length;
    }

    public int getIndex() {
        return pos;
    }

    public boolean isPartialReturn() {
        return isPartial;
    }

    public char last() {
        if (length() == 0) {
            pos = offset + length;
            return DONE;
        }

        pos = offset + length - 1;

        return text[pos];
    }

    public char next() {
        pos++;

        if (pos >= offset + length) {
            pos = offset + length;
            return DONE;
        }

        return text[pos];
    }

    public char previous() {
        if (pos == offset) {
            return DONE;
        }

        return text[--pos];
    }

    public char setIndex(final int position) {
        if (position < 0 || position > offset + length) {
            throw new IllegalArgumentException("Invalid position: " + position); //$NON-NLS-1$
        }

        pos = position;

        if (position == offset + length) {
            return DONE;
        }

        return text[pos];
    }

    public void setPartialReturn(final boolean p) {
        isPartial = p;
    }

    @Override
    public String toString() {
        return text != null ? new String(text, offset, length) : "";
    }

    public CharSequence subSequence(int start, int end) {
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        }
        else if (end > length) {
            throw new StringIndexOutOfBoundsException(end);
        }
        else if (start > end) {
            throw new StringIndexOutOfBoundsException(end - start);
        }
        return new Segment(text, offset + start, end - start);
    }

    public char charAt(int index) {
        if (index < 0 || index >= length) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return text[offset + index];
    }

    public int length() {
        return length;
    }

}
