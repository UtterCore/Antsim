package UtterEng;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class View {

    private JFrame gameWindow;
    private GamePanel gamePanel = new GamePanel();

    public View(int width, int height) {
        buildGameWindow(width, height);
    }



    public void buildGameWindow(int width, int height) {
        gameWindow = new JFrame("Simulator");

        gameWindow.setLayout(new BorderLayout());
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //gameWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
        //gameWindow.setUndecorated(true);

        //gameWindow.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        gameWindow.setPreferredSize(new Dimension(width, height));

        gamePanel = new GamePanel();
        gameWindow.add(gamePanel, BorderLayout.CENTER);

        gameWindow.pack();

        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }

    public void refreshGUI(BufferedImage gameView) {

        if (gamePanel != null) {
            gamePanel.setImage(gameView);
            gamePanel.repaint();
        }

        gameWindow.revalidate();
        gameWindow.repaint();
    }

    public JFrame getGameWindow() {
        return gameWindow;
    }

}
