package a3wt.graphics;

import a3wt.util.A3Copyable;
import a3wt.util.A3Disposable;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public interface A3Image extends A3Disposable, A3Copyable<A3Image> {

    interface Frame extends A3Copyable<Frame>, A3Disposable {
        void setImage(final A3Image image);
        A3Image getImage();
        void setDuration(final long duration);
        long getDuration();
        Frame copy(final int imageType);
    }

    class DefaultFrame implements Frame {

        protected long duration;
        protected A3Image image;

        public DefaultFrame(final A3Image image, final long duration) {
            checkArgNotNull(image, "image");
            this.image = image;
            this.duration = duration;
        }

        public DefaultFrame(final A3Image image) {
            checkArgNotNull(image, "image");
            this.duration = 0;
            this.image = image;
        }

        @Override
        public void setImage(final A3Image image) {
            checkArgNotNull(image, "image");
            checkDisposed("Can't call setImage() on a disposed A3Image.Frame");
            this.image = image;
        }

        @Override
        public A3Image getImage() {
            checkDisposed("Can't call getImage() on a disposed A3Image.Frame");
            return image;
        }

        @Override
        public void setDuration(final long duration) {
            checkDisposed("Can't call setDuration() on a disposed A3Image.Frame");
            this.duration = duration;
        }

        @Override
        public long getDuration() {
            checkDisposed("Can't call getDuration() on a disposed A3Image.Frame");
            return duration;
        }

        @Override
        public Frame copy(final int imageType) {
            checkDisposed("Can't call copy() on a disposed A3Image.Frame");
            return new DefaultFrame(image.copy(imageType), duration);
        }

        @Override
        public Frame copy() {
            checkDisposed("Can't call copy() on a disposed A3Image.Frame");
            return new DefaultFrame(image.copy(), duration);
        }

        @Override
        public void to(final Frame dst) {
            checkArgNotNull(dst, "dst");
            checkDisposed("Can't call to() on a disposed A3Image.Frame");
            dst.setDuration(duration);
            dst.setImage(image.copy());
        }

        @Override
        public void from(final Frame src) {
            checkArgNotNull(src, "src");
            checkDisposed("Can't call from() on a disposed A3Image.Frame");
            src.to(this);
        }

        @Override
        public boolean isDisposed() {
            return image.isDisposed();
        }

        @Override
        public void dispose() {
            image.dispose();
        }

    }

    class Type {
        public static final int ARGB_8888 = 0;
        public static final int RGB_565 = 1;
    }

    default boolean hasAlpha() {
        return getType() == Type.ARGB_8888;
    }

    A3Graphics createGraphics();
    int getWidth();
    int getHeight();
    int getPixel(final int x, final int y);
    A3Image setPixel(final int x, final int y, final int color);
    void getPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height);
    A3Image setPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height);

    int getType();

    A3Image copy(final int type);

}
