package restaurant.ui.management;

import restaurant.service.core.vo.Table;
import restaurant.ui.component.*;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.layout.PageLayout;
import restaurant.ui.management.component.TableCard;
import restaurant.ui.management.component.TableForm;
import restaurant.ui.utils.Utility;
import restaurant.ui.utils.Validate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TableManageUI extends BasePanel4 implements ActionListener {
    public static final int ACTION_FIRST = 1;
    public static final int TABLE_CREATE = 1;
    public static final int TABLE_DELETE = 2;
    public static final int ACTION_LAST = 2;
    private static final String FILTER_ALL = "全部";
    private ManagementFrame mf;


    private String[] floors;
    private JComboBoxEx jcbFloor;
    private String[] types;
    private JComboBoxEx jcbType;

    public TableManageUI(ManagementFrame mf) {
        this.mf = mf;

        // subtitle
        setSubtitleLeft("餐桌管理");

        // content left
        loadTable(mf.getService().getAllTable());
        // content right
        List<String> l = mf.getService().getTableTypes();
        l.add(0, FILTER_ALL);
        types = l.toArray(new String[0]);
        l = mf.getService().getTableFloors();
        l.add(0, FILTER_ALL);
        floors = l.toArray(new String[0]);
        jcbType = new JComboBoxEx("类型", types).addActionListener(e->searchTable());
        jcbFloor = new JComboBoxEx("楼层", floors).addActionListener(e->searchTable());
        getContentRightTop().setLayout(new PageLayout().setSingleCol(true));
        getContentRightTop().add(jcbType);
        getContentRightTop().add(jcbFloor);


        // foot left
        PageLayout layout = new PageLayout();
        getContentLeft().setLayout(layout);
        PageButton pageButton = new PageButton(layout, getContentLeft());
        getFootLeft().add(pageButton);
        // foot right
        addFootRightButton("新增", e->createTable());
        addFootRightButton("返回", e->mf.main());
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof TableCard){
            TableCard ec = (TableCard)e.getSource();
            if (e.getActionCommand().equals(TableCard.MODIFY)) {
                modifyTable(ec);
            } else if (e.getActionCommand().equals(TableCard.DELETE)){
                deleteTable(ec);
            }
        }
    }

    private void createTable(){
        TableForm t = new TableForm(mf.getService().getTableTypes().toArray(new String[0]));
        TableForm.Data data = t.open();
        if(data != null) {
            String id = mf.getService().createTable(data.tableType, data.tableFloor, data.tableCapacity);
            if (id != null) {
                // 提示创建成功
                getContentLeft().add(new TableCard(id, data.tableType, data.tableFloor,
                        data.tableCapacity).setActionListener(this));
                setNotification("创建成功");
                Utility.revalidate(getContentLeft());
                mf.actionPerformed(new ActionEvent(this, TABLE_CREATE, id));
            } else {
                // 提示创建失败，失败原因
            }
        }
    }
    private void modifyTable(TableCard tc){
        Table table = mf.getService().getTableById(tc.getTableId());
        TableForm.Data data = new TableForm.Data(table.getType(), table.getFloor(), table.getCapacity());
        TableForm d = new TableForm(mf.getService().getTableTypes().toArray(new String[0]), data);
        data = d.open();
        if(data != null) {
            if (mf.getService().modifiTable(tc.getTableId(), data.tableType,
                    data.tableFloor, data.tableCapacity)) {
                setNotification("修改成功");
                tc.setTableType(data.tableType)
                        .setTableFloor(data.tableFloor)
                        .setTableCapacity(data.tableCapacity);
                Utility.revalidate(tc);
            } else {
                // 提示修改失败，失败原因
            }
        }
    }
    private void deleteTable(TableCard tc){
        // 此处应该弹框警告
        int isDelete = Utility.showConfirmDialog("删除后将无法恢复，确认删除吗？");
        if(isDelete == ConfirmDialog.YES_OPTION) {
            if (mf.getService().deleteTable(tc.getTableId())) {
                setNotification("删除成功");
                getContentLeft().remove(tc);
                Utility.revalidate(getContentLeft());
                mf.actionPerformed(new ActionEvent(this, TABLE_DELETE, tc.getTableId()));
            } else {
                // 提示失败，失败原因
            }
        }
    }
    private void searchTable(){
        // 获取检索条件
        String type = (String) jcbType.getSelectedItem();
        String floor = (String) jcbFloor.getSelectedItem();
        // 设置检索条件存在布尔值
        // 检测条件合法性
        // 检索
        List<Table> tables;
        if(type.equals(FILTER_ALL) && floor.equals(FILTER_ALL)){
            tables = mf.getService().getAllTable();
        } else if(type.equals(FILTER_ALL) && !floor.equals(FILTER_ALL)){
            tables = mf.getService().getTableByFloor(Integer.valueOf(floor));
        } else if(!type.equals(FILTER_ALL) && floor.equals(FILTER_ALL)){
            tables = mf.getService().getTableByType(type);
        } else {
            tables = mf.getService().getTableByTypeAndFloor(type, Integer.valueOf(floor));
        }
        // 加载
        loadTable(tables);
    }

    private void loadTable(List<Table> tables){
        getContentLeft().removeAll();
        for(Table table: tables){
            getContentLeft().add(new TableCard(table.getId(), table.getType(), table.getFloor(), table.getCapacity())
                    .setActionListener(this));
        }
        Utility.revalidate(getContentLeft());
    }
}
