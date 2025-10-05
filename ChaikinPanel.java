import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.*;

public class ChaikinPanel extends JPanel {

    private final List<Point2D.Float> controlPoints = new CopyOnWriteArrayList<>();

    public static final int POINT_DIAMETER = 10;
    public static final float POINT_RADIUS = POINT_DIAMETER / 2.0f;

    public ChaikinPanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
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

        g2d.setColor(Color.WHITE);
        for (Point2D.Float pt : controlPoints) {
            g2d.fillOval((int) (pt.x - POINT_RADIUS), (int) (pt.y - POINT_RADIUS), POINT_DIAMETER, POINT_DIAMETER);
        }
    }
    
    public List<Point2D.Float> getControlPoints() {
        return controlPoints;
    }
}