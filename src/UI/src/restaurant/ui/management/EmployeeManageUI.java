package restaurant.ui.management;

import restaurant.service.core.sc.EmployeeSearchCondition;
import restaurant.service.core.vo.Employee;
import restaurant.ui.component.*;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.layout.PageLayout;
import restaurant.ui.component.thirdpart.VFlowLayout;
import restaurant.ui.management.component.EmployeeCard;
import restaurant.ui.management.component.EmployeeForm;
import restaurant.ui.utils.Utility;
import restaurant.ui.utils.Validate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManageUI extends BasePanel4 implements ActionListener {
    private static final String FILTER_ALL = "全部";
    private ManagementFrame mf;

    private String[] positions;
    private JComboBoxEx jcbPosition;
    private JComboBoxEx jcbSex;
    private JTextFieldEx jtfName;
    private JTextFieldEx jtfCode;
    public EmployeeManageUI(ManagementFrame mf) {
        this.mf = mf;

        // subtitle
        setSubtitleLeft("员工管理");


        // content left
        PageLayout layout = new PageLayout();
        getContentLeft().setLayout(layout);
        // content right
        List<String> l = mf.getService().getAllEmployeePositions();
        l.add(0, FILTER_ALL);
        positions = l.toArray(new String[0]);
        jtfName = new JTextFieldEx("姓名", e -> searchEmployee());
        jtfCode = new JTextFieldEx("工号", e -> searchEmployee());
        jcbPosition = new JComboBoxEx("类型", positions)
                .addActionListener(e -> searchEmployee());
        jcbSex = new JComboBoxEx("性别", new String[]{FILTER_ALL,"男","女"})
                .addActionListener(e -> searchEmployee());
        getContentRightTop().setLayout(new PageLayout().setSingleCol(true));
        getContentRightTop().add(jtfName);
        getContentRightTop().add(jtfCode);
        getContentRightTop().add(jcbSex);
        getContentRightTop().add(jcbPosition);

        // foot left
        PageButton pageButton = new PageButton(layout, getContentLeft());
        getFootLeft().add(pageButton);
        // foot right
        addFootRightButton("新增", e -> createEmployee());
        addFootRightButton("删除", e -> deleteAllSelectedEmployee());
        addFootRightButton("查询", e -> searchEmployee());
        addFootRightButton("返回", e -> mf.main());

        // load data
        loadEmployee(mf.getService().getAllEmployee());
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
        EmployeeForm.Data data = d.open();
        if(data != null) {
            String code = mf.getService().createEmployee(data.name, data.position, data.salary,
                    data.sex, data.password, data.phone, data.nativePlace, data.url);
            if (code != null) {
                // 提示创建成功
                getContentLeft().add(new EmployeeCard(data.name, code, data.position, data.photo, data.sex, data.phone)
                        .setActionListener(this));
                setNotification("增添成功");
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
                setNotification("修改成功");
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
                setNotification("删除成功");
                getContentLeft().remove(ec);
                Utility.revalidate(getContentLeft());
            } else {
                // 提示失败，失败原因
            }
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
            setNotification("删除成功");
            Utility.revalidate(getContentLeft());
        }
    }
    private void searchEmployee(){
        // 获取检索条件
        String name = jtfName.getText();
        String code = jtfCode.getText();
        String position = (String) jcbPosition.getSelectedItem();
        String sex = (String) jcbSex.getSelectedItem();
        // 检测条件合法性
        name = Validate.isNullOrEmpty(name) ? EmployeeSearchCondition.FILTER_ALL : name;
        code = Validate.isNullOrEmpty(code) ? EmployeeSearchCondition.FILTER_ALL : code;
        position = position.equals(FILTER_ALL) ? EmployeeSearchCondition.FILTER_ALL : position;
        sex = sex.equals(FILTER_ALL) ? EmployeeSearchCondition.FILTER_ALL : sex;
        // 检索
        List<Employee> employees = mf.getService().getEmployee(
                EmployeeSearchCondition.getInstance()
                        .name(name).code(code).position(position).sex(sex));
        // 加载
        loadEmployee(employees);
    }

    private void loadEmployee(List<Employee> employees){
        getContentLeft().removeAll();
        for(Employee employee: employees){
            getContentLeft().add(new EmployeeCard(employee.getName(), employee.getCode(), employee.getPosition(),
                    new ImageIcon(employee.getPhoto()), employee.getSex(), employee.getPhone())
                    .setActionListener(this));
        }
        Utility.revalidate(getContentLeft());
    }
}
