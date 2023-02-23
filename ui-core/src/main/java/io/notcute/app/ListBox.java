package io.notcute.app;

import io.notcute.ui.Container;
import io.notcute.util.ArrayUtils;
import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;
import io.notcute.util.TextUtils;
import io.notcute.util.signalslot.VoidSignal2;

public interface ListBox {

    final class Type {
        private Type() {
            throw new UnsupportedOperationException();
        }
        public static final int MENU = 0;
        public static final int CHECKBOX = 1;
        public static final int RADIO = 2;
        public static final int COMBO = 3;
    }

    class Info implements Resetable, SwapCloneable {

        private int type;
        private CharSequence[] options;
        private CharSequence title;

        public Info() {
            reset();
        }

        public Info(Info info) {
            this(info.getType(), info.getOptions(), info.getTitle());
        }

        public Info(int type, CharSequence[] options, CharSequence title) {
            setInfo(type, options, title);
        }

        public void setInfo(Info info) {
            setInfo(info.getType(), info.getOptions(), info.getTitle());
        }

        public void setInfo(int type, CharSequence[] options, CharSequence title) {
            this.type = type;
            this.options = options;
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public CharSequence[] getOptions() {
            return options;
        }

        public void setOptions(CharSequence[] options) {
            this.options = options;
        }

        public CharSequence getTitle() {
            return title;
        }

        public void setTitle(CharSequence title) {
            this.title = title;
        }

        @Override
        public void reset() {
            type = Type.MENU;
            title = null;
            options = null;
        }

        @Override
        public Object clone() {
            try {
                Info clone = (Info) super.clone();
                if (options != null) clone.setOptions(ArrayUtils.copyOf(options));
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

    VoidSignal2<Integer, CharSequence> onItemSelected();

}
