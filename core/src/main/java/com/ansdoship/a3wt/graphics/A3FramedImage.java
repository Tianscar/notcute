package com.ansdoship.a3wt.graphics;

import java.util.List;
import java.util.RandomAccess;

public interface A3FramedImage extends A3Image, List<A3Image>, RandomAccess {

    int getIndex();
    void setIndex(final int index);

    int getLooping();
    void setLooping(final int loops);

    int getGeneralWidth();
    int getGeneralHeight();

    void setTypeAll(final int type);

    A3Image get();

}
