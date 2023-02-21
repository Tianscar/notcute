package io.notcute.app;

import io.notcute.ui.Container;
import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;
import io.notcute.util.signalslot.VoidSignal1;

import java.io.File;

public interface FileChooser {

    final class Mode {
        private Mode() {
            throw new UnsupportedOperationException();
        }
        public static final int LOAD = 0;
        public static final int SAVE = 1;
    }

    class Info implements Resetable, SwapCloneable {

        private int mode;
        private boolean multiple;
        private File pathname;
        private CharSequence title;
        private String[] filterMIMETypes;

        public Info() {
            reset();
        }

        public Info(Info info) {
            this(info.getMode(), info.isMultiple(), info.getPathname(), info.getTitle(), info.getFilterMIMETypes().clone());
        }

        public Info(int mode, boolean multiple, File pathname, CharSequence title, String... filterMIMETypes) {
            setInfo(mode, multiple, pathname, title, filterMIMETypes);
        }

        public void setInfo(Info info) {
            setInfo(info.getMode(), info.isMultiple(), info.getPathname(), info.getTitle(), info.getFilterMIMETypes());
        }

        public void setInfo(int mode, boolean multiple, File pathname, CharSequence title, String... filterMIMETypes) {
            this.mode = mode;
            this.multiple = multiple;
            this.pathname = pathname;
            this.title = title;
            this.filterMIMETypes = filterMIMETypes;
        }

        public int getMode() {
            return mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        public boolean isMultiple() {
            return multiple;
        }

        public void setMultiple(boolean multiple) {
            this.multiple = multiple;
        }

        public File getPathname() {
            return pathname;
        }

        public void setPathname(File pathname) {
            this.pathname = pathname;
        }

        public CharSequence getTitle() {
            return title;
        }

        public void setTitle(CharSequence title) {
            this.title = title;
        }

        public String[] getFilterMIMETypes() {
            return filterMIMETypes;
        }

        public void setFilterMIMETypes(String... filterMIMETypes) {
            this.filterMIMETypes = filterMIMETypes;
        }

        @Override
        public void reset() {
            mode = Mode.LOAD;
            multiple = false;
            pathname = null;
            title = null;
            filterMIMETypes = null;
        }

        @Override
        public Object clone() {
            try {
                Info clone = (Info) super.clone();
                if (title != null) clone.setTitle(title.subSequence(0, title.length()));
                if (filterMIMETypes != null) clone.setFilterMIMETypes(filterMIMETypes.clone());
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

    VoidSignal1<File[]> onFileChosen();

}
