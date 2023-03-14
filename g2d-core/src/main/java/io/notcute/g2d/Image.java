package io.notcute.g2d;

import io.notcute.util.AlreadyDisposedException;
import io.notcute.util.Cloneable;
import io.notcute.util.Disposable;
import io.notcute.util.SwapCloneable;

public interface Image extends Disposable, Cloneable {

    class DisposalMode {
        private DisposalMode() {
            throw new UnsupportedOperationException();
        }
        public static final int NONE = 0;
        public static final int BACKGROUND = 1;
        public static final int PREVIOUS = 2;
    }

    class BlendMode {
        private BlendMode() {
            throw new UnsupportedOperationException();
        }
        public static final int SOURCE = 0;
        public static final int OVER = 1;
    }

    class Frame implements Disposable, SwapCloneable {

        private long duration;
        private Image image;
        private int hotSpotX, hotSpotY;
        private int disposal;
        private int blend;

        public Frame(Image image, int hotSpotX, int hotSpotY, long duration, int disposal, int blend) {
            this.image = image;
            this.hotSpotX = hotSpotX;
            this.hotSpotY = hotSpotY;
            this.duration = duration;
            this.disposal = disposal;
            this.blend = blend;
        }

        public Frame(Image image) {
            this.duration = 0;
            this.image = image;
        }

        public Frame(Frame frame) {
            this(frame.getImage(), frame.getHotSpotX(), frame.getHotSpotY(), frame.getDuration(), frame.getDisposal(), frame.getBlend());
        }

        public void setFrame(Frame frame) {
            setFrame(frame.getImage(), frame.getHotSpotX(), frame.getHotSpotY(), frame.getDuration(), frame.getDisposal(), frame.getBlend());
        }

        public void setFrame(Image image, int hotSpotX, int hotSpotY, long duration, int disposal, int blend) {
            setImage(image);
            setHotSpot(hotSpotX, hotSpotY);
            setDuration(duration);
            setDisposal(disposal);
            setBlend(blend);
        }

        public void setImage(Image image) {
            if (isDisposed()) throw new AlreadyDisposedException();
            this.image = image;
        }

        public Image getImage() {
            if (isDisposed()) throw new AlreadyDisposedException();
            return image;
        }

        public void setDuration(long duration) {
            if (isDisposed()) throw new AlreadyDisposedException();
            this.duration = duration;
        }

        public long getDuration() {
            if (isDisposed()) throw new AlreadyDisposedException();
            return duration;
        }

        public void setHotSpotX(int hotSpotX) {
            if (isDisposed()) throw new AlreadyDisposedException();
            this.hotSpotX = hotSpotX;
        }

        public int getHotSpotX() {
            if (isDisposed()) throw new AlreadyDisposedException();
            return hotSpotX;
        }

        public void setHotSpotY(int hotSpotY) {
            if (isDisposed()) throw new AlreadyDisposedException();
            this.hotSpotY = hotSpotY;
        }

        public int getHotSpotY() {
            if (isDisposed()) throw new AlreadyDisposedException();
            return hotSpotY;
        }

        public void setHotSpot(int hotSpotX, int hotSpotY) {
            setHotSpotX(hotSpotX);
            setHotSpotY(hotSpotY);
        }

        public int getDisposal() {
            return disposal;
        }

        public void setDisposal(int disposal) {
            this.disposal = disposal;
        }

        public int getBlend() {
            return blend;
        }

        public void setBlend(int blend) {
            this.blend = blend;
        }

        @Override
        public Object clone() {
            if (isDisposed()) throw new AlreadyDisposedException();
            try {
                return super.clone();
            }
            catch (CloneNotSupportedException e) {
                return new Frame(this);
            }
        }

        @Override
        public void to(Object dst) {
            if (isDisposed()) throw new AlreadyDisposedException();
            Frame frame = (Frame) dst;
            frame.setFrame(this);
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

    final class Type {
        private Type() {
            throw new UnsupportedOperationException();
        }
        public static final int ARGB_8888 = 0;
        public static final int RGB_565 = 1;
    }

    default boolean hasAlpha() {
        return getType() == Type.ARGB_8888;
    }

    Graphics getGraphics();

    int getWidth();
    int getHeight();
    int getPixel(int x, int y);
    void setPixel(int x, int y, int color);
    void getPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height);
    void setPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height);

    int getType();

}
