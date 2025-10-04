import java.awt.event.*;

public class InputHandler extends MouseAdapter implements KeyListener {
    private final ChaikinPanel panel;
    public InputHandler(ChaikinPanel panel) {
        this.panel = panel;
    }
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}