package a3wt.test;

import a3wt.awt.A3AWTFrame;
import a3wt.bundle.A3MapBundle;
import a3wt.input.A3ContainerAdapter;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

public class MapBundleTest {

    public static void main(String[] args) {
        File bundleFile = new File(MapBundleTest.class.getCanonicalName() + ".prop");
        A3AWTFrame frame = new A3AWTFrame("Map Bundle Test");
        frame.setMinimumSize(new Dimension(640, 480));
        A3MapBundle bundle = frame.getContainerHandle().getBundleKit().createMapBundle(true);
        bundle.restore(bundleFile);
        frame.setSize(bundle.getInt("width", 640), bundle.getInt("height", 480));
        frame.setLocation(bundle.getInt("x", 0), bundle.getInt("y", 0));
        frame.getContainerHandle().addContainerListener(new A3ContainerAdapter() {
            @Override
            public void containerResized(int width, int height) {
                bundle.putInt("width", width).putInt("height", height);
            }
            @Override
            public void containerMoved(int x, int y) {
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
                bundle.save(bundleFile, "properties");
                return super.containerCloseRequested();
            }
        });
        frame.setVisible(true);
    }

}
