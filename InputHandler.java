import java.awt.event.*;
import java.awt.geom.Point2D;

public class InputHandler extends MouseAdapter implements KeyListener {

    private final ChaikinPanel panel;

    public InputHandler(ChaikinPanel panel) {
        this.panel = panel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (panel.isAnimating()) return;
        
        panel.getControlPoints().add(new Point2D.Float(e.getX(), e.getY()));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                panel.startAnimation();
                break;
            case KeyEvent.VK_SPACE:
                panel.clearCanvas();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}