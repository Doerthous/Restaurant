package restaurant.ui.component;

import javax.swing.*;
import java.awt.*;


public class BaseFrame {
    private JFrame frame;
    private JPanel toolkitPane;
    private JPanel contentPane;
    private CardLayout contentLayout;
    public BaseFrame() throws HeadlessException {
        frame = new JFrame();
        frame.setSize(new Dimension(800,600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        initComponent();
    }

    /*
        窗口相关操作
     */
    public void fullScreenUnresizeable(){
        frame.setUndecorated(true);
        frame.setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
    }
    private void initComponent(){
        toolkitPane = new JPanel();
        toolkitPane.setBackground(Color.white);
        toolkitPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JButton exit = JButtonBuilder.getInstance().text("退出")
                .enteredColor(Color.gray).exitedColor(Color.white)
                .background(Color.white).focusPainted(false)
                .borderPainted(false).listener(e->{
            System.exit(1);
        }).build();
        JButton mini = JButtonBuilder.getInstance().text("最小化")
                .enteredColor(Color.gray).exitedColor(Color.white)
                .background(Color.white).focusPainted(false)
                .borderPainted(false).listener(e->{
            frame.setExtendedState(JFrame.ICONIFIED);
        }).build();
        toolkitPane.setPreferredSize(new Dimension(0, (int) mini.getPreferredSize().getHeight()));
        toolkitPane.add(mini);
        toolkitPane.add(exit);
        frame.add("North", toolkitPane);
        contentPane = new JPanel();
        contentLayout = new CardLayout();
        getContentPane().setLayout(contentLayout);
        frame.add("Center", contentPane);
    }

    public Container getContentPane() {
        return contentPane;
    }

    public void add(Component component){
        getContentPane().add(component);
    }
    public void add(String name, Component ui){
        getContentPane().add(name, ui);
    }
    public void show(String name){
        contentLayout.show(getContentPane(), name);
    }

    public void open(){
        frame.setVisible(true);
    }
}
