import com.ansdoship.a3wt.app.A3Container;
import com.ansdoship.a3wt.awt.A3AWTFrame;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.input.A3ContainerAdapter;
import com.ansdoship.a3wt.input.A3ContextAdapter;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GeometryTest {

    private volatile static boolean paused = false;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        A3AWTFrame frame = new A3AWTFrame("Simple Loop");
        frame.setMinimumSize(new Dimension(640, 480));
        frame.setLocationRelativeTo(null);
        A3Container.Handle handle = frame.getContainerHandle();
        handle.addContainerListener(new A3ContainerAdapter() {
            @Override
            public void containerCreated() {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        synchronized (lock) {
                            if (!frame.isDisposed()) {
                                if (!paused) handle.update();
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
        handle.addContextListener(new A3ContextAdapter() {
            @Override
            public void contextPainted(A3Graphics graphics, boolean snapshot) {
                graphics.drawArc(0, 0, 100, 120, 0, 360 / 4f, true);
                graphics.drawRoundRect(0, 0, 100, 100, 50, 100);
            }
        });
        frame.setVisible(true);
    }

}
