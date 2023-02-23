package io.notcute.app;

import io.notcute.ui.Container;
import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;
import io.notcute.util.TextUtils;
import io.notcute.util.signalslot.VoidSignal1;

public interface InputBox {

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

    class Info implements Resetable, SwapCloneable {

        private int type;
        private CharSequence title;
        private CharSequence message;
        private CharSequence text;

        public Info() {
            reset();
        }

        public Info(Info info) {
            this(info.getType(), info.getTitle(), info.getMessage(), info.getText());
        }

        public Info(int type, CharSequence title, CharSequence message, CharSequence text) {
            setInfo(type, title, message, text);
        }

        public void setInfo(Info info) {
            setInfo(info.getType(), info.getTitle(), info.getMessage(), info.getText());
        }

        public void setInfo(int type, CharSequence title, CharSequence message, CharSequence text) {
            this.type = type;
            this.title = title;
            this.message = message;
            this.text = text;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public CharSequence getText() {
            return text;
        }

        public void setText(CharSequence text) {
            this.text = text;
        }

        @Override
        public void reset() {
            type = Type.PLAIN;
            title = message = text = null;
        }

        @Override
        public Object clone() {
            try {
                Info clone = (Info) super.clone();
                if (title != null) clone.setTitle(TextUtils.deepCopy(title));
                if (message != null) clone.setMessage(TextUtils.deepCopy(message));
                if (text != null) clone.setText(text.subSequence(0, text.length()));
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

    VoidSignal1<CharSequence> onTextInput();

}
