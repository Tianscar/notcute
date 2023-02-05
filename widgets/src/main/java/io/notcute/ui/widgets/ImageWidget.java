package io.notcute.ui.widgets;

import io.notcute.g2d.AffineTransform;
import io.notcute.g2d.Graphics;
import io.notcute.g2d.Image;
import io.notcute.ui.G2DContext;

public class ImageWidget extends Widget {

    private volatile Image image;
    private final AffineTransform imageTransform = new AffineTransform();

    public ImageWidget(G2DContext g2DContext) {
        super(g2DContext);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public AffineTransform getImageTransform() {
        return imageTransform;
    }

    public void setImageTransform(AffineTransform imageTransform) {
        if (imageTransform == null) this.imageTransform.reset();
        else this.imageTransform.from(imageTransform);
    }

    @Override
    public void paint(G2DContext context, Graphics graphics, boolean snapshot) {
        super.paint(context, graphics, snapshot);
        if (image != null) {
            graphics.save();
            graphics.drawImage(image, imageTransform);
            graphics.restore();
        }
    }

}
