package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Canvas;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class A3Component extends Component implements A3Canvas {

    protected volatile long elapsed = 0;
    protected final AWTA3Graphics graphics = new A3ComponentGraphics();
    protected volatile Image buffer = null;

    private static class A3ComponentGraphics extends AWTA3Graphics {
        public A3ComponentGraphics() {
            super(null, -1, -1);
        }
        public void setGraphics(Graphics2D graphics2D, int width, int height) {
            this.graphics2D = graphics2D;
            this.width = width;
            this.height = height;
        }
    }

    @Override
    public void paint(Graphics g) {
        if (buffer != null) g.drawImage(buffer, 0, 0, null);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void repaint(long tm, int x, int y, int width, int height) {
    }

    @Override
    public long elapsed() {
        return elapsed;
    }

    @Override
    public void paint(A3Graphics graphics) {
    }

    @Override
    public synchronized void update() {
        long time = System.currentTimeMillis();
        buffer = createImage(getWidth(), getHeight());
        Graphics gTmp = buffer.getGraphics();
        gTmp.setColor(getBackground());
        gTmp.fillRect(0, 0, getWidth(), getHeight());
        gTmp.setColor(new Color(0x000000));
        ((A3ComponentGraphics)graphics).setGraphics((Graphics2D) gTmp, getWidth(), getHeight());
        paint(graphics);
        ((A3ComponentGraphics)graphics).setGraphics(null, -1, -1);
        gTmp.dispose();
        update(getGraphics());
        long now = System.currentTimeMillis();
        elapsed = now - time;
    }

    @Override
    public synchronized A3Image snapshot() {
        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gTmp = bufferedImage.createGraphics();
        print(gTmp);
        gTmp.dispose();
        return new AWTA3Image(bufferedImage);
    }

    @Override
    public synchronized A3Image snapshotBuffer() {
        if (buffer == null) return null;
        return new AWTA3Image(A3AWTUtils.copyImage(buffer));
    }

}
