package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AWTA3Graphics implements A3Graphics {

    protected volatile Graphics graphics;
    protected volatile boolean disposed = false;

    public AWTA3Graphics(BufferedImage bufferedImage) {
        if (bufferedImage == null) throw new NullPointerException("bufferedImage cannot be null.");
        graphics = bufferedImage.createGraphics();
    }

    public AWTA3Graphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public void drawImage(A3Image image, int x, int y) {
        checkDisposed("Can't call drawImage() on a disposed A3Graphics");
        graphics.drawImage(((AWTA3Image)image).getBufferedImage(), x, y, image.getWidth(), image.getHeight(), null);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public synchronized void dispose() {
        checkDisposed("Can't call dispose() on a disposed A3Graphics");
        disposed = true;
        graphics.dispose();
        graphics = null;
    }

    private void checkDisposed(String errorMessage) {
        if (disposed) {
            throw new IllegalStateException(errorMessage);
        }
    }

}
