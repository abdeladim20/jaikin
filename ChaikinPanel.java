import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.*;

public class ChaikinPanel extends JPanel implements Runnable {

    private final List<Point2D.Float> controlPoints = new CopyOnWriteArrayList<>();
    private volatile List<Point2D.Float> displayPoints = new ArrayList<>();

    private volatile boolean isAnimating = false;
    private int animationStep = 0;
    private long lastUpdateTime = 0;
    
    private Point2D.Float draggedPoint = null;
    private boolean showMessage = false;
    private long messageStartTime = 0;
    private static final long MESSAGE_DURATION_MS = 2000;
    
    private boolean showStaticLine = false;

    public static final int POINT_DIAMETER = 10;
    public static final float POINT_RADIUS = POINT_DIAMETER / 2.0f;
    private static final int ANIMATION_DELAY_MS = 1000;
    private static final int MAX_ANIMATION_STEPS = 7;

    public ChaikinPanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
    }
    
    @Override
    public void run() {
        while (true) {
            updateAnimation();
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    private void updateAnimation() {
        if (!isAnimating || controlPoints.size() < 2) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime > ANIMATION_DELAY_MS) {
            if (animationStep < MAX_ANIMATION_STEPS) {
                displayPoints = chaikin(displayPoints);
                animationStep++;
            } else {
                animationStep = 0;
                displayPoints = new ArrayList<>(controlPoints);
            }
            lastUpdateTime = currentTime;
        }
    }

    private static List<Point2D.Float> chaikin(List<Point2D.Float> points) {
        if (points.size() < 2) {
            return new ArrayList<>(points);
        }

        List<Point2D.Float> newPoints = new ArrayList<>();
        
        newPoints.add(points.get(0));

        for (int i = 0; i < points.size() - 1; i++) {
            Point2D.Float p0 = points.get(i);
            Point2D.Float p1 = points.get(i + 1);
            
            float qx = p0.x + (p1.x - p0.x) * 0.25f;
            float qy = p0.y + (p1.y - p0.y) * 0.25f;
            float rx = p0.x + (p1.x - p0.x) * 0.75f;
            float ry = p0.y + (p1.y - p0.y) * 0.75f;
            
            newPoints.add(new Point2D.Float(qx, qy));
            newPoints.add(new Point2D.Float(rx, ry));
        }
        
        newPoints.add(points.get(points.size() - 1));
        
        return newPoints;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isAnimating && controlPoints.size() >= 3 && displayPoints.size() > 1) {
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(2.0f));
            for (int i = 0; i < displayPoints.size() - 1; i++) {
                Point2D.Float p1 = displayPoints.get(i);
                Point2D.Float p2 = displayPoints.get(i + 1);
                g2d.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
            }
        }
        
        if (showStaticLine && controlPoints.size() == 2) {
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(2.0f));
            Point2D.Float p1 = controlPoints.get(0);
            Point2D.Float p2 = controlPoints.get(1);
            g2d.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
        }

        g2d.setColor(Color.WHITE);
        for (Point2D.Float pt : controlPoints) {
            g2d.fillOval((int) (pt.x - POINT_RADIUS), (int) (pt.y - POINT_RADIUS), POINT_DIAMETER, POINT_DIAMETER);
        }
        
        if (showMessage) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - messageStartTime < MESSAGE_DURATION_MS) {
                g2d.setColor(Color.YELLOW);
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                String msg = "Please add control points first!";
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(msg)) / 2;
                int y = 30;
                g2d.drawString(msg, x, y);
            } else {
                showMessage = false;
            }
        }
    }

    public void startAnimation() {
        if (controlPoints.size() == 0) {
            showMessage = true;
            messageStartTime = System.currentTimeMillis();
            repaint();
            return;
        }
        
        if (controlPoints.size() == 1) {
            return;
        }
        
        if (controlPoints.size() == 2) {
            showStaticLine = true;
            repaint();
            return;
        }
        
        if (controlPoints.size() >= 3 && !isAnimating) {
            isAnimating = true;
            animationStep = 0;
            lastUpdateTime = System.currentTimeMillis();
            displayPoints = new ArrayList<>(controlPoints);
        }
    }

    public void clearCanvas() {
        isAnimating = false;
        animationStep = 0;
        controlPoints.clear();
        displayPoints = new ArrayList<>();
        showMessage = false;
        showStaticLine = false;
    }

    public List<Point2D.Float> getControlPoints() {
        return controlPoints;
    }
    
    public boolean isAnimating() {
        return isAnimating;
    }
    
    public Point2D.Float findPointAt(int x, int y) {
        for (Point2D.Float pt : controlPoints) {
            double dist = Math.sqrt(Math.pow(pt.x - x, 2) + Math.pow(pt.y - y, 2));
            if (dist <= POINT_RADIUS * 2) {
                return pt;
            }
        }
        return null;
    }
    
    public void setDraggedPoint(Point2D.Float point) {
        this.draggedPoint = point;
    }
    
    public Point2D.Float getDraggedPoint() {
        return draggedPoint;
    }
    
    public void updateDraggedPoint(int x, int y) {
        if (draggedPoint != null) {
            draggedPoint.x = x;
            draggedPoint.y = y;
            if (isAnimating) {
                animationStep = 0;
                lastUpdateTime = System.currentTimeMillis();
                displayPoints = new ArrayList<>(controlPoints);
            }
        }
    }
}