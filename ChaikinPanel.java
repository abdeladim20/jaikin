import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChaikinPanel extends JPanel {

    private final List<Point2D.Float> controlPoints = new CopyOnWriteArrayList<>();

    public static final int POINT_DIAMETER = 10;
    public static final float POINT_RADIUS = POINT_DIAMETER / 2.0f;

    public ChaikinPanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
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