package io.notcute.ui.widgets;

import io.notcute.g2d.AffineTransform;
import io.notcute.g2d.Graphics;
import io.notcute.ui.G2DContext;
import io.notcute.util.Color;

public class TextWidget extends Widget {

    private volatile CharSequence text;
    private volatile int textColor = Color.BLACK;
    private volatile float textSize = 16;
    private final AffineTransform textTransform = new AffineTransform();

    public TextWidget(G2DContext g2DContext) {
        super(g2DContext);
    }

    public CharSequence getText() {
        return text;
    }

    public void setText(CharSequence text) {
        this.text = text;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public AffineTransform getTextTransform() {
        return textTransform;
    }

    public void setTextTransform(AffineTransform textTransform) {
        if (textTransform == null) this.textTransform.reset();
        else this.textTransform.from(textTransform);
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    @Override
    public void paint(G2DContext context, Graphics graphics, boolean snapshot) {
        super.paint(context, graphics, snapshot);
        if (text != null) {
            graphics.save();
            Graphics.Info info = graphics.getInfo();
            info.setColor(textColor);
            info.setStyle(Graphics.Style.FILL);
            info.setTextSize(textSize);
            graphics.drawText(text, 0, text.length(), textTransform);
            graphics.restore();
        }
    }

}
