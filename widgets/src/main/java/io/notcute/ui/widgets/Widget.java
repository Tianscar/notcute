package io.notcute.ui.widgets;

import io.notcute.g2d.AffineTransform;
import io.notcute.g2d.Graphics;
import io.notcute.g2d.geom.Rectangle;
import io.notcute.ui.G2DContext;
import io.notcute.util.Color;

import java.util.Objects;

public class Widget {

    private transient final G2DContext g2DContext;

    private volatile int backgroundColor = Color.TRANSPARENT;
    private final Rectangle bounds = new Rectangle();
    private final AffineTransform backgroundTransform = new AffineTransform();

    public Widget(G2DContext g2DContext) {
        this.g2DContext = Objects.requireNonNull(g2DContext);
    }

    public G2DContext getG2DContext() {
        return g2DContext;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public AffineTransform getBackgroundTransform() {
        return backgroundTransform;
    }

    public void setBounds(Rectangle bounds) {
        if (bounds == null) this.bounds.reset();
        else this.bounds.from(bounds);
    }

    public void setBackgroundTransform(AffineTransform backgroundTransform) {
        if (backgroundTransform == null) this.backgroundTransform.reset();
        else this.backgroundTransform.from(backgroundTransform);
    }

    public void paint(G2DContext context, Graphics graphics, boolean snapshot) {
        graphics.save();
        Graphics.Info info = graphics.getInfo();
        info.setColor(backgroundColor);
        info.setStyle(Graphics.Style.FILL);
        graphics.drawShape(bounds, backgroundTransform);
        graphics.restore();
    }

}
