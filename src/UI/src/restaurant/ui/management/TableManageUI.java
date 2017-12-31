package restaurant.ui.management;

import restaurant.service.core.vo.Table;
import restaurant.ui.component.BasePanel3;
import restaurant.ui.component.ConfirmDialog;
import restaurant.ui.component.PageButton;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.layout.PageLayout;
import restaurant.ui.management.component.TableCard;
import restaurant.ui.management.component.TableForm;
import restaurant.ui.utils.Utility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableManageUI extends BasePanel3 implements ActionListener {
    public static final int ACTION_FIRST = 1;
    public static final int TABLE_CREATE = 1;
    public static final int ACTION_LAST = 1;
    private ManagementFrame mf;

    public TableManageUI(ManagementFrame mf) {
        this.mf = mf;


        // content left
        loadTable(mf.getService().getAllTable());

        // foot left
        PageLayout layout = new PageLayout();
        getContentLeft().setLayout(layout);
        PageButton pageButton = new PageButton(layout, getContentLeft());
        getFootLeft().add(pageButton);
        // foot right
        addFootRight(
                JButtonBuilder.getInstance().text("新增").background(restaurant.ui.client.Constants.Color.title)
                        .listener(e->createTable()).build()
        );
        addFootRight(
                JButtonBuilder.getInstance().text("返回").background(restaurant.ui.client.Constants.Color.title)
                        .listener(e -> mf.main()).build()
        );
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
                Utility.showTipDialog("增添成功", 2000);
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
                Utility.showTipDialog("修改成功", 2000);
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
                Utility.showTipDialog("删除成功",2000);
            } else {
                // 提示失败，失败原因
            }
            getContentLeft().remove(tc);
            Utility.revalidate(getContentLeft());
        }
    }
    private void searchTable(){

    }

    private void loadTable(java.util.List<Table> tables){
        getContentLeft().removeAll();
        for(Table table: tables){
            getContentLeft().add(new TableCard(table.getId(), table.getType(), table.getFloor(), table.getCapacity())
                    .setActionListener(this));
        }
        Utility.revalidate(getContentLeft());
    }
}
