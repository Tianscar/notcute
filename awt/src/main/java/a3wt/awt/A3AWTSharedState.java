package a3wt.awt;

import java.awt.Window;
import java.awt.Frame;
import java.awt.Dialog;

public class A3AWTSharedState {

    private A3AWTSharedState() {}

    private static volatile String companyName = "";
    private static volatile String appName = "";
    private static volatile float ppiScale = 1.0f;
    
    private static volatile Window mFullscreenWindow = null;
    private static volatile boolean mUndecorated = false;
    private static volatile boolean mResizable = false;

    public static String getAppName() {
        return appName;
    }

    public static String getCompanyName() {
        return companyName;
    }

    public static void setAppName(final String appName) {
        A3AWTSharedState.appName = appName;
    }

    public static void setCompanyName(final String companyName) {
        A3AWTSharedState.companyName = companyName;
    }

    public static void setPPIScale(final float scale) {
        ppiScale = scale;
    }

    public static float getPPIScale() {
        return ppiScale;
    }

    public static Window getFullscreenWindow() {
        return A3AWTUtils.getFullscreenWindow();
    }

    public static void setFullscreenWindow(final Window window) {
        if (mFullscreenWindow != window) {
            if (mFullscreenWindow != null) {
                final boolean visible = mFullscreenWindow.isVisible();
                if (mFullscreenWindow instanceof Dialog) {
                    final Dialog dialog = (Dialog) mFullscreenWindow;
                    dialog.setVisible(false);
                    dialog.setResizable(mResizable);
                    dialog.setUndecorated(mUndecorated);
                    dialog.setVisible(visible);
                }
                else if (mFullscreenWindow instanceof Frame) {
                    final Frame frame = (Frame) mFullscreenWindow;
                    frame.setVisible(false);
                    frame.setResizable(mResizable);
                    frame.setUndecorated(mUndecorated);
                    frame.setVisible(visible);
                }
            }
            mFullscreenWindow = window;
            final boolean visible = window.isVisible();
            window.setVisible(false);
            if (mFullscreenWindow instanceof Dialog) {
                final Dialog dialog = (Dialog) mFullscreenWindow;
                mResizable = dialog.isResizable();
                mUndecorated = dialog.isUndecorated();
                dialog.setResizable(false);
                dialog.setUndecorated(false);
            }
            else if (mFullscreenWindow instanceof Frame) {
                final Frame frame = (Frame) mFullscreenWindow;
                mResizable = frame.isResizable();
                mUndecorated = frame.isUndecorated();
                frame.setResizable(false);
                frame.setUndecorated(false);
            }
            A3AWTUtils.setFullscreenWindow(window);
            window.setVisible(visible);
        }
    }

}
