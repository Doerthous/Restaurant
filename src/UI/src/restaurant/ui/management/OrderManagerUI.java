package restaurant.ui.management;

import restaurant.service.core.vo.Order;
import restaurant.ui.Constants;
import restaurant.ui.component.*;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.builder.JPanelBuilder;
import restaurant.ui.component.layout.PageLayout;
import restaurant.ui.management.component.OrderCard;
import restaurant.ui.management.component.OrderDetailCard;
import restaurant.ui.management.component.OrderView;
import restaurant.ui.utils.GBC;
import restaurant.ui.utils.Utility;
import restaurant.ui.utils.Validate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderManagerUI extends BasePanel4 implements ActionListener {
    private ManagementFrame mf;
    private JComboBoxEx jcbSearchByTableId;
    private JTextFieldEx jtfSearchByDateBegin;
    private JTextFieldEx jtfSearchByDateEnd;
    private JPanel jpOrderTable;

    public OrderManagerUI(ManagementFrame mf) {
        this.mf = mf;
        initUIData();

        // subtitle
        setSubtitleLeft("订单管理");

        // content
        List<String> tableNumbers = mf.getService().getAllTableNumbers();
        tableNumbers.add(0, ""); // 全部
        jcbSearchByTableId = new JComboBoxEx("桌号", tableNumbers.toArray(new String[0]))
                .addActionListener(e->searchOrder());
        jtfSearchByDateBegin = new JTextFieldEx("日期(起)", e -> searchOrder());
        jtfSearchByDateEnd = new JTextFieldEx("日期(止)", e -> searchOrder());
        jpOrderTable = JPanelBuilder.getInstance().opaque(false).build();
        Object[] constraints = new Object[]{
                new GBC(0,0).setWeight(2,1),
                new GBC(1,0).setWeight(2,1),
                new GBC(2,0).setWeight(2,1),
                new GBC(3,0),
        };
        PageTitle pt = new PageTitle(new GridBagLayout(), new String[]{"订单号","桌号","日期",""}, constraints);
        PagePanel pp = new PagePanel(jpOrderTable, pt);
        getContentLeft().add(pp);

        getContentRightTop().setLayout(new PageLayout().setSingleCol(true));
        getContentRightTop().add(jcbSearchByTableId); // 没做
        getContentRightTop().add(jtfSearchByDateBegin);
        getContentRightTop().add(jtfSearchByDateEnd);

        // foot left
        getFootLeft().add(pp.getPageButton());
        // foot right
        addFootRightButton("查询", e -> searchOrder());
        addFootRightButton("返回", e -> mf.main());

        //
        loadUIData();
        loadUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source instanceof OrderCard){
            OrderCard oc = (OrderCard)source;
            switch (e.getID()){
                case OrderCard.ACTION_DELETE:{
                    deleteOrder(oc);
                } break;
                case OrderCard.ACTION_CLICK: {
                    searchOrderDetail(oc);
                } break;
            }
        }
    }
    //
    private void searchOrder(){
        String tableId = (String) jcbSearchByTableId.getSelectedItem();
        String beginDate = jtfSearchByDateBegin.getText();
        String endDate = jtfSearchByDateEnd.getText();
        Date b = null;
        Date e = null;
        Boolean hasTableId = false;
        Boolean hasBeginDate = false;
        Boolean hasEndDate = false;
        // 设置检索条件存在布尔值
        if(!Validate.isNullOrEmpty(tableId)){
            hasTableId = true;
        }
        if(!Validate.isNullOrEmpty(beginDate)){
            try {
                b = new SimpleDateFormat("yyyyMMdd").parse(beginDate);
                hasBeginDate = true;
            } catch (ParseException e1) {
                setNotification("日期格式为: 10000101");
                return;
            }
        }
        if(!Validate.isNullOrEmpty(endDate)){
            try {
                e = new SimpleDateFormat("yyyyMMdd").parse(endDate);
                hasEndDate = true;
            } catch (ParseException e1) {
                setNotification("日期格式为: 10000101");
                return;
            }
        }
        // 检查检索条件合法性
        if(!hasBeginDate.equals(hasEndDate)){
           if(hasBeginDate){
               setNotification("请输入截止日期");
               return;
           }
           if(hasEndDate){
               setNotification("请输入开始日期");
               return;
           }
        }
        // 检索
        if(!hasTableId && !hasBeginDate){
            orders = mf.getService().getAllOrder();
        } else if(hasTableId && !hasBeginDate){
            orders = mf.getService().getOrderByTableId(tableId);
        } else if(!hasTableId && hasBeginDate){
            orders = mf.getService().getOrderByDateRange(b, e);
        } else {
            orders = mf.getService().getOrderByTableIdAndDateRange(tableId, b, e);
        }
        loadUIData();
        loadUI();
    }
    private void deleteOrder(OrderCard oc) {
        System.out.println("删除订单["+oc.getOrderId()+"]");
        if(mf.getService().deleteOrder(oc.getOrderId())){
            Utility.showTipDialog("删除成功",2000);
            jpOrderTable.remove(oc);
            Utility.revalidate(jpOrderTable);
        } else {
            //
        }
    }
    private void searchOrderDetail(OrderCard oc){
        System.out.println("加载订单["+oc.getOrderId()+"]");
        Order order = mf.getService().getOrderById(oc.getOrderId());
        new OrderView(order);
    }

    // ui data
    private List<Order> orders;
    private void initUIData(){
        orders = mf.getService().getAllOrder();
    };
    private List<OrderCard> jpOrders;
    private void loadUIData(){
        jpOrders = new ArrayList<>();
        for(Order order: orders){
            jpOrders.add(new OrderCard(order.getOrderId(), order.getTableId(), order.getDate(), this));
        }
    }
    private void loadUI(){
        jpOrderTable.removeAll();
        for(OrderCard orderCard: jpOrders){
            jpOrderTable.add(orderCard);
        }
        Utility.revalidate(jpOrderTable);
    };
}
