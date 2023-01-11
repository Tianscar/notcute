package a3wt.test;

import a3wt.awt.A3AWTFrame;
import a3wt.bundle.A3SecMapBundle;
import a3wt.input.A3ContainerAdapter;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

public class SecMapBundleTest {

    public static void main(String[] args) {
        File bundleFile = new File(SecMapBundleTest.class.getCanonicalName() + ".ini");
        A3AWTFrame frame = new A3AWTFrame("Sectional Map Bundle Test");
        frame.setMinimumSize(new Dimension(640, 480));
        A3SecMapBundle bundle = frame.getContainerHandle().getBundleKit().createSecMapBundle(true);
        bundle.restore(bundleFile);
        bundle.setKey("size");
        frame.setSize(bundle.getInt("width", 640), bundle.getInt("height", 480));
        bundle.setKey("pos");
        frame.setLocation(bundle.getInt("x", 0), bundle.getInt("y", 0));
        frame.getContainerHandle().addContainerListener(new A3ContainerAdapter() {
            @Override
            public void containerResized(int width, int height) {
                bundle.setKey("size");
                bundle.putInt("width", width).putInt("height", height);
            }
            @Override
            public void containerMoved(int x, int y) {
                bundle.setKey("pos");
                bundle.putInt("x", x).putInt("y", y);
            }
            @Override
            public boolean containerCloseRequested() {
                if (!bundleFile.exists()) {
                    try {
                        bundleFile.createNewFile();
                    } catch (IOException ignored) {
                    }
                }
                bundle.save(bundleFile, "ini");
                return super.containerCloseRequested();
            }
        });
        frame.setVisible(true);
    }

}
