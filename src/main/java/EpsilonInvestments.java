import View.*;

import javax.swing.*;
import java.awt.*;

public class EpsilonInvestments {

    final private int width = 1200;
    final private int height = 800;

    public void start()
    {
        // JFrame
        JFrame myFrame = new JFrame();
        myFrame.setLayout(null);
        myFrame.getContentPane().setLayout(null);

        // Size
        myFrame.setResizable(false);
        myFrame.setPreferredSize(new Dimension(width, height));

        // Packing the Frame
        myFrame.pack();

        // View
        ViewInterface currentView = new StartView();
        currentView.draw(myFrame);

        // Drawing the Frame
        myFrame.setVisible(true);
    }
}
