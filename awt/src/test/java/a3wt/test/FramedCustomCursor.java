package a3wt.test;

import a3wt.app.A3Container;
import a3wt.awt.A3AWTFrame;
import a3wt.graphics.A3FramedImage;
import a3wt.graphics.A3Graphics;
import a3wt.graphics.A3Image;
import a3wt.input.A3ContainerAdapter;
import a3wt.input.A3ContextAdapter;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class FramedCustomCursor {

    private volatile static boolean paused = false;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        A3AWTFrame frame = new A3AWTFrame("Framed Custom Cursor");
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
        A3FramedImage image = holder.getGraphicsKit().readFramedImage(holder.getAssets(), "piglin.gif", A3Image.Type.ARGB_8888);
        holder.setCursor(holder.getGraphicsKit().createFramedCursor(image, 0, 0));
        image.dispose();
        holder.addContextListener(new A3ContextAdapter() {
            @Override
            public void contextPainted(A3Graphics graphics, boolean snapshot) {
            }
        });
        frame.setVisible(true);
    }

}
