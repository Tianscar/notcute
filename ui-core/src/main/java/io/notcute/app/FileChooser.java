package io.notcute.app;

import io.notcute.ui.Container;
import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;
import io.notcute.util.signalslot.VoidSignal1;

import java.io.File;
import java.io.FileFilter;

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
        private File directory;
        private File file;
        private CharSequence title;
        private FileFilter fileFilter;

        public Info() {
            reset();
        }

        public Info(Info info) {
            this(info.getMode(), info.isMultiple(), info.getDirectory(), info.getFile(), info.getTitle(), info.getFileFilter());
        }

        public Info(int mode, boolean multiple, File directory, File file, CharSequence title, FileFilter fileFilter) {
            setInfo(mode, multiple, directory, file, title, fileFilter);
        }

        public void setInfo(Info info) {
            setInfo(info.getMode(), info.isMultiple(), info.getDirectory(), info.getFile(), info.getTitle(), info.getFileFilter());
        }

        public void setInfo(int mode, boolean multiple, File directory, File file, CharSequence title, FileFilter fileFilter) {
            this.mode = mode;
            this.multiple = multiple;
            this.directory = directory;
            this.file = file;
            this.title = title;
            this.fileFilter = fileFilter;
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

        public File getDirectory() {
            return directory;
        }

        public void setDirectory(File directory) {
            this.directory = directory;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public CharSequence getTitle() {
            return title;
        }

        public void setTitle(CharSequence title) {
            this.title = title;
        }

        public FileFilter getFileFilter() {
            return fileFilter;
        }

        public void setFileFilter(FileFilter fileFilter) {
            this.fileFilter = fileFilter;
        }

        @Override
        public void reset() {
            mode = Mode.LOAD;
            multiple = true;
            directory = file = null;
            title = null;
            fileFilter = null;
        }

        @Override
        public Object clone() {
            try {
                Info clone = (Info) super.clone();
                if (title != null) clone.setTitle(title.subSequence(0, title.length()));
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
