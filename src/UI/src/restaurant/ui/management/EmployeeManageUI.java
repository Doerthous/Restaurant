package restaurant.ui.management;

import restaurant.service.core.vo.Dish;
import restaurant.service.core.vo.Employee;
import restaurant.ui.component.BasePanel3;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.JComboBoxEx;
import restaurant.ui.component.PageButton;
import restaurant.ui.component.layout.PageLayout;
import restaurant.ui.management.component.DishForm;
import restaurant.ui.management.component.EmployeeCard;
import restaurant.ui.management.component.EmployeeForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeManageUI extends BasePanel3 implements ActionListener {
    private ManagementFrame mf;

    public EmployeeManageUI(ManagementFrame mf) {
        this.mf = mf;
        PageLayout layout = new PageLayout();
        getContentLeft().setLayout(layout);
        PageButton pageButton = new PageButton(layout, getContentLeft());
        getFootLeft().add(pageButton);

        // subtitle
        /*getSubtitleLeft().setLayout(new PageLayout().setSingleRow(true)
                .setHgap(0).setVgap(10).setPadding(new Insets(2,15,0,0)));
        getSubtitleLeft().add(createMenuButton("菜单管理"));
        getSubtitleLeft().add(createMenuButton("套餐管理"));*/
        // getSubtitleLeft().add(createMenuButton("类别管理"));

        // content
        java.util.List<Employee> employees = mf.getService().getAllEmployee();
        for(Employee employee: employees){
            getContentLeft().add(new EmployeeCard(employee.getName(), employee.getCode(), employee.getPosition(),
                    new ImageIcon(employee.getPhoto()), employee.getSex(), employee.getPhone())
                    .setActionListener(this));
        }
        getContentRightTop().setLayout(new PageLayout().setSingleCol(true));
        getContentRightTop().add(new JComboBoxEx("性别", new String[]{"男","女"}));
        getContentRightTop().add(new JComboBoxEx("类型", mf.getService().getAllEmployeePositions()
                .toArray(new String[]{})));

        // foot
        addFootRight(
                JButtonBuilder.getInstance().text("新增").background(restaurant.ui.client.Constants.Color.title)
                        .listener(e -> createEmployee()).margin(new Insets(0,0,0,0)).build()
        );
        addFootRight(
                JButtonBuilder.getInstance().text("删除").background(restaurant.ui.client.Constants.Color.title)
                        .listener(e -> deleteAllSelectedEmployee()).margin(new Insets(0,0,0,0)).build()
        );
        addFootRight(
                JButtonBuilder.getInstance().text("查询").background(restaurant.ui.client.Constants.Color.title)
                        .listener(e -> searchEmployee()).margin(new Insets(0,0,0,0)).build()
        );
        addFootRight(
                JButtonBuilder.getInstance().text("返回").background(restaurant.ui.client.Constants.Color.title)
                        .listener(e -> mf.main()).margin(new Insets(0,0,0,0)).build()
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof EmployeeCard){
            EmployeeCard ec = (EmployeeCard)e.getSource();
            if (e.getActionCommand().equals(EmployeeCard.MODIFY)) {
                modifyEmployee(ec);
            } else if (e.getActionCommand().equals(EmployeeCard.DELETE)){
                deleteEmployee(ec);
            }
        }
    }

    private void createEmployee(){
        EmployeeForm d = new EmployeeForm();
        System.out.println(d.open().toString());
    }

    private void modifyEmployee(EmployeeCard ec){
        Employee employee = mf.getService().getEmployeeByCode(ec.getCode());
        EmployeeForm.Data data = new EmployeeForm.Data(employee.getName(), employee.getPosition(),
                employee.getSex(), employee.getSalary(), employee.getNativePlace(), employee.getPhone(),
                employee.getPassword(), new ImageIcon(employee.getPhoto()));
        EmployeeForm d = new EmployeeForm(data);
        System.out.println(d.open().toString());
    }

    private void deleteEmployee(EmployeeCard ec){}

    private void deleteAllSelectedEmployee(){

    }

    private void searchEmployee(){

    }
}
