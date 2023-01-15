package a3wt.test;

import a3wt.awt.A3AWTFrame;
import a3wt.bundle.A3ExtMapBundle;
import a3wt.graphics.A3Graphics;
import a3wt.graphics.A3Point;
import a3wt.input.A3ContainerAdapter;
import a3wt.input.A3ContextAdapter;
import a3wt.input.A3InputAdapter;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ExtMapBundleTest {

    private volatile static boolean paused = false;

    public static void main(String[] args) {
        File bundleFile = new File(ExtMapBundleTest.class.getCanonicalName() + ".xml");
        A3AWTFrame frame = new A3AWTFrame("Extensive Map Bundle Test");
        frame.setMinimumSize(new Dimension(640, 480));
        A3Point point = frame.getContainerHolder().getGraphicsKit().createPoint();
        A3ExtMapBundle bundle = frame.getContainerHolder().getBundleKit().createExtMapBundle(true);
        if (!bundle.restore(bundleFile)) {
            bundle.putExtMapBundle("size", frame.getContainerHolder().getBundleKit().createExtMapBundle(true));
            bundle.putExtMapBundle("pos", frame.getContainerHolder().getBundleKit().createExtMapBundle(true));
        }
        else {
            A3Point bundleable = bundle.getBundleable("point", null);
            if (bundleable != null) point.from(bundleable);
        }
        frame.setSize(bundle.getExtMapBundle("size", null).getInt("width", 640),
                bundle.getExtMapBundle("size", null).getInt("height", 480));
        frame.setLocation(bundle.getExtMapBundle("pos", null).getInt("x", 0),
                bundle.getExtMapBundle("pos", null).getInt("y", 0));
        frame.getContainerHolder().addContainerListener(new A3ContainerAdapter() {
            @Override
            public void containerCreated() {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        frame.getContainerHolder().postRunnable(() -> {
                            if (!frame.isDisposed()) {
                                if (!paused) frame.getContainerHolder().update();
                            }
                            else System.exit(0);
                        });
                    }
                }, 0, 1000 / 60);
            }
            @Override
            public void containerPaused() {
                paused = true;
            }
            @Override
            public void containerResumed() {
                paused = false;
            }
            @Override
            public void containerResized(int width, int height) {
                bundle.getExtMapBundle("size", null).putInt("width", width);
                bundle.getExtMapBundle("size", null).putInt("height", height);
            }
            @Override
            public void containerMoved(int x, int y) {
                bundle.getExtMapBundle("pos", null).putInt("x", x);
                bundle.getExtMapBundle("pos", null).putInt("y", y);
            }
            @Override
            public boolean containerCloseRequested() {
                if (!bundleFile.exists()) {
                    try {
                        bundleFile.createNewFile();
                    } catch (IOException ignored) {
                    }
                }
                bundle.putBundleable("point", point);
                bundle.save(bundleFile, "xml");
                return super.containerCloseRequested();
            }
        });
        frame.getContextHolder().addContextListener(new A3ContextAdapter() {
            @Override
            public void contextPainted(A3Graphics graphics, boolean snapshot) {
                graphics.setStrokeWidth(128.f);
                graphics.setStrokeCap(A3Graphics.Cap.ROUND);
                graphics.setStrokeJoin(A3Graphics.Join.ROUND);
                graphics.setStyle(A3Graphics.Style.STROKE);
                graphics.drawPoint(point.getX(), point.getY());
            }
        });
        frame.getContextHolder().addContextInputListener(new A3InputAdapter() {
            @Override
            public boolean pointerDown(float x, float y, int pointer, int button) {
                point.set(x, y);
                return true;
            }
            @Override
            public boolean pointerUp(float x, float y, int pointer, int button) {
                point.set(x, y);
                return true;
            }
            @Override
            public boolean pointerDragged(float x, float y, int pointer) {
                point.set(x, y);
                return true;
            }
        });
        frame.setVisible(true);
    }

}
