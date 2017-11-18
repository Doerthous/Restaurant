package programmer.test.ui;

import restaurant.service.vo.Employee;
import restaurant.service.IEmployeeService;
import restaurant.service.ServiceFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by YMS on 2017/11/18.
 */
public class UITest {
    private JPanel panel1;
    private JTextArea textArea1;
    private JButton button1;

    public UITest() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IEmployeeService service = ServiceFactory.getEmployeeService();
                List<Employee> ems = service.getAllEmployee();
                String text = "";
                for(Employee em : ems){
                    text += em.toString()+'\n';
                }
                textArea1.setText(text);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UITest");
        frame.setContentPane(new UITest().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
