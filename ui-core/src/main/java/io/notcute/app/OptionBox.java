package io.notcute.app;

import io.notcute.ui.Container;
import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;
import io.notcute.util.TextUtils;
import io.notcute.util.signalslot.VoidSignal1;

public interface OptionBox {

    final class Type {
        private Type() {
            throw new UnsupportedOperationException();
        }
        public static final int PLAIN = 0;
        public static final int QUESTION = 1;
        public static final int INFO = 2;
        public static final int WARN = 3;
        public static final int ERROR = 4;
        public static final int PASSWORD = 5;
    }

    final class Options {
        private Options() {
            throw new UnsupportedOperationException();
        }
        public static final int YES = 0;
        public static final int NO = 1;
        public static final int CANCEL = 2;
    }

    class Info implements Resetable, SwapCloneable {
        
        private int type;
        private int options;
        private CharSequence title;
        private CharSequence message;

        public Info() {
            reset();
        }

        public Info(Info info) {
            this(info.getType(), info.getOptions(), info.getTitle(), info.getMessage());
        }

        public Info(int type, int options, CharSequence title, CharSequence message) {
            setInfo(type, options, title, message);
        }

        public void setInfo(Info info) {
            setInfo(info.getType(), info.getOptions(), info.getTitle(), info.getMessage());
        }

        public void setInfo(int type, int options, CharSequence title, CharSequence message) {
            this.type = type;
            this.options = options;
            this.title = title;
            this.message = message;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getOptions() {
            return options;
        }

        public void setOptions(int options) {
            this.options = options;
        }

        public CharSequence getTitle() {
            return title;
        }

        public void setTitle(CharSequence title) {
            this.title = title;
        }

        public CharSequence getMessage() {
            return message;
        }

        public void setMessage(CharSequence message) {
            this.message = message;
        }

        @Override
        public void reset() {
            type = Type.PLAIN;
            title = message = null;
            options = Options.YES | Options.NO;
        }

        @Override
        public Object clone() {
            try {
                Info clone = (Info) super.clone();
                if (title != null) clone.setTitle(TextUtils.deepCopy(title));
                if (message != null) clone.setMessage(TextUtils.deepCopy(message));
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

    VoidSignal1<Integer> onOptionSelected();

}
