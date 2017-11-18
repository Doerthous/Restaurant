package programmer.test.ui;

/*
    ui层只依赖于service层：即只导入restaurant.service下的包
 */
import restaurant.service.IEmployeeService;
import restaurant.service.ServiceFactory;
import restaurant.service.vo.Employee;

import javax.swing.*;
import java.util.List;

/**
 * Created by YMS on 2017/11/18.
 *
 * 如果ui层需要service层的服务，请在对应service层接口中注释该服务
 *
 * 如：
 *      Employee的ui需要根据名字查询员工信息：
 *          List<Employee> getEmployeeByName(String name);
 *          将该声明注释在restaurant.service.IEmployeeService中，等待service层人员实现
 */
public class UITest {
    /*
        这些是ui的组件
     */
    private JPanel panel1;
    private JTextArea textArea1;
    private JButton button1;
    private JComboBox comboBox1;
    /*
        这是ui层与service层的接口
     */
    IEmployeeService service;

    // 构造函数，可用于初始化一些成员变量
    public UITest() {
        /*
            初始化ui组件
         */
        comboBox1.addItem("男");
        comboBox1.addItem("女");
        button1.addActionListener(e -> {
            List<Employee> ems = null;// = service.getEmployeeBySex(comboBox1.getSelectedItem().toString());
            String text = "";
            for(Employee em : ems){
                text += em.toString()+'\n';
            }
            textArea1.setText(text);
        });
        /*
            初始化service层接口
         */
        service = ServiceFactory.getEmployeeService();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UITest");
        frame.setContentPane(new UITest().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
