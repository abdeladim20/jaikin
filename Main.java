import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chaikin's Algorithm");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ChaikinPanel chaikinPanel = new ChaikinPanel();
            InputHandler inputHandler = new InputHandler(chaikinPanel);

            frame.add(chaikinPanel);
            chaikinPanel.addMouseListener(inputHandler);
            chaikinPanel.addMouseMotionListener(inputHandler);
            chaikinPanel.addKeyListener(inputHandler);
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);

            // We will make the panel runnable later
            // new Thread(chaikinPanel).start();
        });
    }
}