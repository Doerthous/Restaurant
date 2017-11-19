package programmer.test.ui.component;

import javax.swing.*;
import java.awt.*;

public class SimpleFrame {
    private JFrame frame;
    private JPanel contentPane;
    private JPanel toolkitPane;

    public SimpleFrame() throws HeadlessException {
        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        initComponent();
    }
    private void initComponent(){
        toolkitPane = new JPanel();
        toolkitPane.setBackground(Color.white);
        toolkitPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        SimpleButton exit = new SimpleButton("退出",Color.gray,Color.white,evt->{
            System.exit(1);
        });
        SimpleButton mini = new SimpleButton("最小化",Color.gray,Color.white,evt->{
            frame.setExtendedState(JFrame.ICONIFIED);
        });
        toolkitPane.setPreferredSize(new Dimension(0, (int) mini.getPreferredSize().getHeight()));
        toolkitPane.add(mini);
        toolkitPane.add(exit);
        frame.add("North", toolkitPane);
        contentPane = new JPanel(new BorderLayout());
        frame.add("Center", contentPane);
    }
    public Container getContentPane() {
        return contentPane;
    }
    public void setVisible(boolean b){
        frame.setVisible(b);
    }
    public void add(Component comp){
        getContentPane().add(comp);
    }
    public void add(String name, Component comp){
        getContentPane().add(name, comp);
    }
}
