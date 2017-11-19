package programmer.test.ui;

import javax.swing.*;

/**
 * Created by YMS on 2017/11/18.
 */
public class EmployeeUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("EmployeeUI");
        //frame.setContentPane(new EmployeeUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();

        //设置sringlayout布局
        SpringLayout layout = new SpringLayout();
        JPanel panel1 = new JPanel(layout);
        frame.setContentPane(panel1);
        frame.setVisible(true);
        //添加控件

        JButton jButton = new JButton();
        panel1.add(jButton);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
