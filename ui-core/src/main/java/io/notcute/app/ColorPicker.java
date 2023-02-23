package io.notcute.app;

import io.notcute.g2d.Color;
import io.notcute.ui.Container;
import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;
import io.notcute.util.TextUtils;
import io.notcute.util.signalslot.VoidSignal1;

public interface ColorPicker {

    class Info implements Resetable, SwapCloneable {

        private int color;
        private CharSequence title;

        public Info() {
            reset();
        }

        public Info(Info info) {
            this(info.getColor(), info.getTitle());
        }

        public Info(int type, CharSequence title) {
            setInfo(type, title);
        }

        public void setInfo(Info info) {
            setInfo(info.getColor(), info.getTitle());
        }

        public void setInfo(int color, CharSequence title) {
            this.color = color;
            this.title = title;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public CharSequence getTitle() {
            return title;
        }

        public void setTitle(CharSequence title) {
            this.title = title;
        }

        @Override
        public void reset() {
            color = Color.BLACK;
            title = null;
        }

        @Override
        public Object clone() {
            try {
                Info clone = (Info) super.clone();
                if (title != null) clone.setTitle(TextUtils.deepCopy(title));
                return clone;
            } catch (CloneNotSupportedException e) {
                return new Info(this);
            }
        }

        @Override
        public void from(Object src) {
            setInfo((Info) src);
        }

    }

    Info getInfo();
    default void setInfo(Info info) {
        info.to(getInfo());
    }

    void attachContainer(Container container);

    VoidSignal1<Integer> onColorSelected();

}
