package doerthous.ui;

import javax.swing.*;

import static java.lang.Thread.sleep;

public class AFrameForJPanelTest extends JFrame{
    public AFrameForJPanelTest() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
    }
    public void open(){
        setVisible(true);
    }
}

