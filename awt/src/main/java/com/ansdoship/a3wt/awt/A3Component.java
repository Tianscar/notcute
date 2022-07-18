package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Canvas;
import com.ansdoship.a3wt.graphics.A3Graphics;

import java.awt.Graphics;
import java.awt.Component;
import java.awt.Image;

public class A3Component extends Component implements A3Canvas {

    protected volatile long elapsed = 0;
    protected volatile boolean requestUpdate = false;
    protected AWTA3Graphics graphics = new A3ComponentGraphics();
    private static class A3ComponentGraphics extends AWTA3Graphics {
        public A3ComponentGraphics() {
            super((Graphics) null);
        }
        public void setGraphics(Graphics graphics) {
            this.graphics = graphics;
        }
    }

    @Override
    public void paint(Graphics g) {
        if (requestUpdate) {
            ((A3ComponentGraphics)graphics).setGraphics(g);
            paint(graphics);
            ((A3ComponentGraphics)graphics).setGraphics(null);
        }
    }

    @Override
    public synchronized void update(Graphics g) {
        long time = System.currentTimeMillis();
        if (requestUpdate) {
            Image tmp = createImage(getWidth(), getHeight());
            Graphics gTmp = tmp.getGraphics();
            gTmp.setColor(getBackground());
            gTmp.fillRect(0, 0, getWidth(), getHeight());
            paint(gTmp);
            gTmp.dispose();
            g.drawImage(tmp, 0, 0, null);
            requestUpdate = false;
        }
        long now = System.currentTimeMillis();
        elapsed = now - time;
    }

    @Override
    public long elapsed() {
        return elapsed;
    }

    @Override
    public void paint(A3Graphics graphics) {
    }

    @Override
    public void update() {
        this.requestUpdate = true;
        repaint();
    }

}
