package restaurant.ui.management;

import restaurant.service.core.vo.Dish;
import restaurant.service.core.vo.Employee;
import restaurant.ui.component.*;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.layout.PageLayout;
import restaurant.ui.management.component.DishForm;
import restaurant.ui.management.component.EmployeeCard;
import restaurant.ui.management.component.EmployeeForm;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.spec.ECField;
import java.util.ArrayList;

public class EmployeeManageUI extends BasePanel3 implements ActionListener {
    private ManagementFrame mf;

    private String[] positions;
    private JComboBoxEx jcbPosition;
    private JComboBoxEx jcbSex;
    private JTextFieldEx jtfName;
    private JTextFieldEx jtfCode;
    public EmployeeManageUI(ManagementFrame mf) {
        this.mf = mf;
        PageLayout layout = new PageLayout();
        getContentLeft().setLayout(layout);
        PageButton pageButton = new PageButton(layout, getContentLeft());
        getFootLeft().add(pageButton);

        positions = mf.getService().getAllEmployeePositions().toArray(new String[0]);
        jtfName = new JTextFieldEx("姓名");
        jtfCode = new JTextFieldEx("工号");
        jcbPosition = new JComboBoxEx("类型", positions).addActionListener(this);
        jcbSex = new JComboBoxEx("性别", new String[]{"全部","男","女"}).addActionListener(this);
        java.util.List<Employee> employees = mf.getService().getAllEmployee();
        loadEmployee(mf.getService().getEmployeeByPosition(positions[0]));
        getContentRightTop().setLayout(new PageLayout().setSingleCol(true));
        getContentRightTop().add(jtfName);
        getContentRightTop().add(jtfCode);
        getContentRightTop().add(jcbSex);
        getContentRightTop().add(jcbPosition);

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
        } else if(e.getSource() instanceof JComboBoxEx) {
            sortEmployee();
        }
    }
    private void createEmployee(){
        EmployeeForm d = new EmployeeForm();
        EmployeeForm.Data data = d.open();
        if(data != null) {
            String code = mf.getService().createEmployee(data.name, data.position, data.salary,
                    data.sex, data.password, data.phone, data.nativePlace, data.url);
            if (code != null) {
                // 提示创建成功
                getContentLeft().add(new EmployeeCard(data.name, code, data.position, data.photo, data.sex, data.phone)
                        .setActionListener(this));
                Utility.showTipDialog("增添成功", 2000);
                Utility.revalidate(getContentLeft());
            } else {
                // 提示创建失败，失败原因
            }
        }
    }
    private void modifyEmployee(EmployeeCard ec){
        Employee employee = mf.getService().getEmployeeByCode(ec.getCode());
        EmployeeForm.Data data = new EmployeeForm.Data(employee.getName(), employee.getPosition(),
                employee.getSex(), employee.getSalary(), employee.getNativePlace(), employee.getPhone(),
                employee.getPassword(), new ImageIcon(employee.getPhoto()));
        EmployeeForm d = new EmployeeForm(data);
        data = d.open();
        if(data != null) {
            if (mf.getService().modifiEmployee(ec.getCode(), data.name, data.position, data.salary,
                    data.sex, data.password, data.phone, data.nativePlace, data.url)) {
                Utility.showTipDialog("修改成功", 2000);
                ec.setEmployeeName(data.name)
                        .setEmployeePosition(data.position)
                        .setEmployeeSex(data.sex)
                        .setEmployeePhone(data.phone)
                        .setEmployeePhoto(new ImageIcon(data.url));
                Utility.revalidate(ec);
            } else {
                // 提示修改失败，失败原因
            }
        }
    }
    private void deleteEmployee(EmployeeCard ec){
        // 此处应该弹框警告
        int isDelete = Utility.showConfirmDialog("删除后将无法恢复，确认删除吗？");
        if(isDelete == ConfirmDialog.YES_OPTION) {
            if (mf.getService().deleteEmployee(ec.getCode())) {
                Utility.showTipDialog("删除成功",2000);
            } else {
                // 提示失败，失败原因
            }
            getContentLeft().remove(ec);
            Utility.revalidate(getContentLeft());
        }
    }
    private void deleteAllSelectedEmployee(){
        Component[] components = getContentLeft().getComponents();
        java.util.List<EmployeeCard> ecs = new ArrayList();
        for(Component component: components){
            if(component instanceof EmployeeCard){
                EmployeeCard ec = (EmployeeCard)component;
                if(ec.isChecked()){
                    ecs.add(ec);
                }
            }
        }
        int isDelete = Utility.showConfirmDialog("删除后将无法恢复，确认删除吗？");
        if(isDelete == ConfirmDialog.YES_OPTION) {
            for(EmployeeCard ec: ecs){
                if(!mf.getService().deleteEmployee(ec.getCode())){
                    System.out.println("批量删除一条失败");
                }
                getContentLeft().remove(ec);
            }
            Utility.showTipDialog("删除成功",2000);
            Utility.revalidate(getContentLeft());
        }
    }
    private void sortEmployee() {
        String sex = (String) jcbSex.getSelectedItem();
        String position = (String) jcbPosition.getSelectedItem();
        loadEmployee(mf.getService().getEmployeeByPositionAndSex(position, sex));
    }
    private void searchEmployee(){

    }

    private void loadEmployee(java.util.List<Employee> employees){
        getContentLeft().removeAll();
        for(Employee employee: employees){
            getContentLeft().add(new EmployeeCard(employee.getName(), employee.getCode(), employee.getPosition(),
                    new ImageIcon(employee.getPhoto()), employee.getSex(), employee.getPhone())
                    .setActionListener(this));
        }
        Utility.revalidate(getContentLeft());
    }
}
