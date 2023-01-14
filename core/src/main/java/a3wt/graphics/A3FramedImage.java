package a3wt.graphics;

import a3wt.util.A3FilterList;

import java.util.RandomAccess;

public interface A3FramedImage extends A3Image, A3FilterList<A3Image.Frame>, RandomAccess {

    int getIndex();
    A3FramedImage setIndex(final int index);

    int getLooping();
    A3FramedImage setLooping(final int loops);

    int getGeneralWidth();
    int getGeneralHeight();

    Frame get();

    default long getDuration() {
        return get().getDuration();
    }

    default void setDuration(final long duration) {
        get().setDuration(duration);
    }

    default A3Image getImage() {
        return get().getImage();
    }

    default void setImage(final A3Image image) {
        get().setImage(image);
    }

}
