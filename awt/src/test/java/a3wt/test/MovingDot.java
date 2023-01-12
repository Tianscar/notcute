package a3wt.test;

import a3wt.app.A3Container;
import a3wt.awt.A3AWTFrame;
import a3wt.graphics.A3Graphics;
import a3wt.graphics.A3Point;
import a3wt.input.A3ContainerAdapter;
import a3wt.input.A3ContextAdapter;
import a3wt.input.A3InputAdapter;
import a3wt.util.A3Math;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MovingDot {

    private volatile static boolean paused = false;
    private static final Object lock = new Object();

    private volatile static boolean up, down, left, right;

    public static void main(String[] args) {
        A3AWTFrame frame = new A3AWTFrame("Moving Dot");
        frame.setMinimumSize(new Dimension(640, 480));
        frame.setLocationRelativeTo(null);
        A3Container.Holder holder = frame.getContainerHolder();
        A3Point dotPos = holder.getGraphicsKit().createPoint();
        dotPos.set(320, 240);
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
        holder.addContextInputListener(new A3InputAdapter() {
            @Override
            public boolean keyDown(int keyCode, int keyLocation) {
                switch (keyCode) {
                    case KeyCode.VK_W:
                    case KeyCode.VK_UP:
                        up = true;
                        break;
                    case KeyCode.VK_A:
                    case KeyCode.VK_LEFT:
                        left = true;
                        break;
                    case KeyCode.VK_S:
                    case KeyCode.VK_DOWN:
                        down = true;
                        break;
                    case KeyCode.VK_D:
                    case KeyCode.VK_RIGHT:
                        right = true;
                        break;
                }
                return true;
            }
            @Override
            public boolean keyUp(int keyCode, int keyLocation) {
                switch (keyCode) {
                    case KeyCode.VK_W:
                    case KeyCode.VK_UP:
                        up = false;
                        break;
                    case KeyCode.VK_A:
                    case KeyCode.VK_LEFT:
                        left = false;
                        break;
                    case KeyCode.VK_S:
                    case KeyCode.VK_DOWN:
                        down = false;
                        break;
                    case KeyCode.VK_D:
                    case KeyCode.VK_RIGHT:
                        right = false;
                        break;
                }
                return true;
            }
        });
        holder.addContextListener(new A3ContextAdapter() {
            @Override
            public void contextPainted(A3Graphics graphics, boolean snapshot) {
                graphics.setStrokeWidth(1);
                graphics.setStyle(A3Graphics.Style.FILL);
                graphics.setTextSize(32.f);
                graphics.drawText("WASD/DIRECTION KEYS TO MOVE THE DOT", 0, graphics.getFontMetrics().getAscent());
                if(up) dotPos.setY(dotPos.getY() - 10);
                if(down) dotPos.setY(dotPos.getY() + 10);
                if(left) dotPos.setX(dotPos.getX() - 10);
                if(right) dotPos.setX(dotPos.getX() + 10);
                dotPos.set(A3Math.clamp(dotPos.getX(), 0, holder.getWidth()),
                        A3Math.clamp(dotPos.getY(), 0, holder.getHeight()));
                graphics.reset();
                graphics.setStrokeJoin(A3Graphics.Join.ROUND);
                graphics.setStrokeCap(A3Graphics.Cap.ROUND);
                graphics.setStrokeWidth(32.f);
                graphics.drawPoint(dotPos);
            }
        });
        frame.setVisible(true);
    }

}
