package a3wt.test;

import a3wt.app.A3Container;
import a3wt.awt.A3AWTFrame;
import a3wt.graphics.A3Graphics;
import a3wt.graphics.A3Image;
import a3wt.input.A3ContainerAdapter;
import a3wt.input.A3ContextAdapter;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class SimpleCustomCursor {

    private volatile static boolean paused = false;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        A3AWTFrame frame = new A3AWTFrame("Simple Custom Cursor");
        frame.setMinimumSize(new Dimension(640, 480));
        frame.setLocationRelativeTo(null);
        A3Container.Holder holder = frame.getContainerHolder();
        holder.addContainerListener(new A3ContainerAdapter() {
            @Override
            public void containerCreated() {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        synchronized (lock) {
                            if (!frame.isDisposed()) {
                                if (!paused) holder.update();
                            }
                            else System.exit(0);
                        }
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
        });
        A3Image image = holder.getGraphicsKit().readImage(holder.getAssets(), "tianscar_avatar.png", A3Image.Type.ARGB_8888);
        holder.setCursor(holder.getGraphicsKit().createCursor(image));
        image.dispose();
        holder.addContextListener(new A3ContextAdapter() {
            @Override
            public void contextPainted(A3Graphics graphics, boolean snapshot) {
            }
        });
        frame.setVisible(true);
    }

}
