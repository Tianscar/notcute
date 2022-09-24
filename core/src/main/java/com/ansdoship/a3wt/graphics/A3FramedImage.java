package com.ansdoship.a3wt.graphics;

import java.util.List;
import java.util.RandomAccess;

public interface A3FramedImage extends A3Image, List<A3Image>, RandomAccess {

    int getIndex();
    void setIndex(int index);
    int getLooping();
    void setLooping(int loops);

    int getGeneralWidth();
    int getGeneralHeight();

    A3Image get();

}
