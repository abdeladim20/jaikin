import java.awt.event.*;
import java.awt.geom.Point2D;

public class InputHandler extends MouseAdapter implements KeyListener {

    private final ChaikinPanel panel;

    public InputHandler(ChaikinPanel panel) {
        this.panel = panel;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        panel.getControlPoints().add(new Point2D.Float(e.getX(), e.getY()));
        panel.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}