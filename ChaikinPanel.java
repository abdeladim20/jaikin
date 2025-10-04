import javax.swing.*;
import java.awt.*;

public class ChaikinPanel extends JPanel {
    public ChaikinPanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}