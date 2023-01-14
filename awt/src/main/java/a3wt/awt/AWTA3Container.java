package a3wt.awt;

import a3wt.app.A3Container;

public interface AWTA3Container extends A3Container, AWTA3Context {

    void setSize(final int width, final int height);
    void setMinimumSize(final int width, final int height);
    void setMaximumSize(final int width, final int height);
    void setPreferredSize(final int width, final int height);

}
