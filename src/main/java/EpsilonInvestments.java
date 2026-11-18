import View.*;

import javax.swing.*;
import java.awt.*;

public class EpsilonInvestments {

    public void start()
    {
        // JFrame
        JFrame myFrame = new JFrame();
        myFrame.setLayout(null);

        // Size
        myFrame.setResizable(false);
        myFrame.setPreferredSize(new Dimension(1200, 800));

        // Drawing the Frame
        myFrame.pack();
        myFrame.setVisible(true);

        // View
        ViewInterface currentView = new StartView();

        currentView.draw(myFrame);
    }
}
