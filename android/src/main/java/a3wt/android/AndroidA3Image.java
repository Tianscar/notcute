package a3wt.android;

import android.graphics.Bitmap;
import a3wt.graphics.A3Graphics;
import a3wt.graphics.A3Image;

import static a3wt.util.A3Preconditions.checkArgNotNull;
import static a3wt.util.A3Preconditions.checkArgRangeMin;

public class AndroidA3Image implements A3Image {

    protected volatile Bitmap bitmap;
    protected volatile AndroidA3Graphics graphics;
    protected volatile boolean disposed = false;

    public AndroidA3Image(final Bitmap bitmap, final long duration, final int hotSpotX, final int hotSpotY) {
        checkArgNotNull(bitmap, "bitmap");
        checkArgRangeMin(duration, 0, true, "duration");
        this.bitmap = bitmap;
    }

    public AndroidA3Image(final Bitmap bitmap) {
        this(bitmap, 0, 0, 0);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public int getType() {
        checkDisposed("Can't call getType() on a disposed A3Image");
        return A3AndroidUtils.bitmapConfig2ImageType(bitmap.getConfig());
    }

    @Override
    public A3Image copy(final int type) {
        checkDisposed("Can't call copy() on a disposed A3Image");
        final AndroidA3Image result = new AndroidA3Image(A3AndroidUtils.copyBitmap(bitmap, A3AndroidUtils.imageType2BitmapConfig(type)));
        return result;
    }

    @Override
    public A3Graphics createGraphics() {
        if (graphics == null) graphics = new AndroidA3Graphics(bitmap);
        return graphics;
    }

    @Override
    public int getWidth() {
        checkDisposed("Can't call getWidth() on a disposed A3Image");
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        checkDisposed("Can't call getHeight() on a disposed A3Image");
        return bitmap.getHeight();
    }

    @Override
    public int getPixel(final int x, final int y) {
        checkDisposed("Can't call getPixel() on a disposed A3Image");
        return bitmap.getPixel(x, y);
    }

    @Override
    public A3Image setPixel(final int x, final int y, final int color) {
        checkDisposed("Can't call setPixel() on a disposed A3Image");
        bitmap.setPixel(x, y, color);
        return this;
    }

    @Override
    public void getPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height) {
        checkArgNotNull(pixels, "pixels");
        checkDisposed("Can't call getPixels() on a disposed A3Image");
        bitmap.getPixels(pixels, offset, stride, x, y, width, height);
    }

    @Override
    public A3Image setPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height) {
        checkArgNotNull(pixels, "pixels");
        checkDisposed("Can't call setPixels() on a disposed A3Image");
        bitmap.setPixels(pixels, offset, stride, x, y, width, height);
        return this;
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        graphics.dispose();
        graphics = null;
        if (!bitmap.isRecycled()) bitmap.recycle();
        bitmap = null;
    }

    @Override
    public A3Image copy() {
        checkDisposed("Can't call copy() on a disposed A3Image");
        final AndroidA3Image result = new AndroidA3Image(A3AndroidUtils.copyBitmap(bitmap));
        return result;
    }

    public void setBitmap(final Bitmap bitmap) {
        checkArgNotNull(bitmap, "bitmap");
        checkDisposed("Can't call setBitmap() on a disposed AndroidA3Image");
        this.bitmap = bitmap;
        setGraphics(new AndroidA3Graphics(this.bitmap));
    }

    public void setGraphics(final AndroidA3Graphics graphics) {
        checkArgNotNull(graphics, "graphics");
        checkDisposed("Can't call setGraphics() on a disposed AndroidA3Image");
        this.graphics.dispose();
        this.graphics = graphics;
    }

    @Override
    public void to(final A3Image dst) {
        checkArgNotNull(dst, "dst");
        checkDisposed("Can't call to() on a disposed A3Image");
        final AndroidA3Image dst0 = (AndroidA3Image) dst;
        dst0.bitmap = A3AndroidUtils.copyBitmap(bitmap);
        if (graphics != null) dst0.createGraphics().setData(graphics.data);
    }

    @Override
    public void from(final A3Image src) {
        checkArgNotNull(src, "src");
        checkDisposed("Can't call from() on a disposed A3Image");
        src.to(this);
    }

}
