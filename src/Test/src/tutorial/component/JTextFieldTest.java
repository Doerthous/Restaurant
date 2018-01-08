package tutorial.component;

import doerthous.ui.AFrameForJPanelTest;

import javax.swing.*;

public class JTextFieldTest {
    public static void main(String[] args) {
        AFrameForJPanelTest f = new AFrameForJPanelTest();
        JTextField jtf = new JTextField();
        f.add(jtf);
        f.open();
    }
}
