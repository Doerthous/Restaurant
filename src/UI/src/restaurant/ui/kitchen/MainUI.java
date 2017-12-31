package restaurant.ui.kitchen;

import restaurant.service.core.IKitchenService;
import restaurant.ui.Constants;
import restaurant.ui.component.*;
import restaurant.ui.component.border.AdvLineBorder;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.component.layout.PageLayout;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.Thread.sleep;

public class MainUI extends BasePanel {
    private KitchenFrame kf;

    public MainUI(KitchenFrame kf) {
        this.kf = kf;
        initServiceComponent();
        initUIComponent();
    }

    /*
        Service组件
     */
    private UIDataController udc;
    private void initServiceComponent(){
        udc = new UIDataController();
        kf.getService().addOrderDataObserver(udc);
    }

    /*
        UI组件
     */

    private JPanel remainDish;
    private PageButton dishPB;
    private JPanel table;
    private PageButton tablePB;
    private JPanel orderDetail;
    private PageButton orderPB;
    private void initUIComponent(){
        // subtitle
        getSubtitle().setLayout(new GridLayout(1,3));
        getSubtitle().add(JLabelBuilder.getInstance().text("需上菜品").horizontalAlignment(JLabel.CENTER)
                .foreground(Color.white).font(Constants.Font.font1).build());
        getSubtitle().add(JLabelBuilder.getInstance().text("订餐餐桌").horizontalAlignment(JLabel.CENTER)
                .foreground(Color.white).font(Constants.Font.font1)
                .border(new AdvLineBorder().setLeft(1).setLeftColor(Constants.Color.background)
                .setRight(1).setRightColor(Constants.Color.background)).build());
        getSubtitle().add(JLabelBuilder.getInstance().text("订餐详情").horizontalAlignment(JLabel.CENTER)
                .foreground(Color.white).font(Constants.Font.font1).build());

        // content
        getContent().setLayout(new GridLayout(1,3));
        PageLayout pageLayout = new PageLayout().setHgap(2).setSingleCol(true)
                .setPadding(new Insets(5,10,0,10));
        remainDish = new JPanel(pageLayout);
        dishPB = new PageButton(pageLayout, remainDish);
        remainDish.setOpaque(false);
        //
        pageLayout = new PageLayout().setHgap(5).setSingleCol(true)
                .setPadding(new Insets(5,10,0,10));
        table = new JPanel(pageLayout);
        tablePB = new PageButton(pageLayout, table);
        table.setOpaque(false);
        table.setBorder(new AdvLineBorder().setLeft(1).setLeftColor(Constants.Color.subtitle)
                .setRight(1).setRightColor(Constants.Color.subtitle));
        //
        pageLayout = new PageLayout().setHgap(2).setSingleCol(true)
                .setPadding(new Insets(5,10,0,10));
        orderDetail = new JPanel(pageLayout);
        orderPB = new PageButton(pageLayout, orderDetail);
        orderDetail.setOpaque(false);
        //
        getContent().add(remainDish);
        getContent().add(table);
        getContent().add(orderDetail);


        // font
        getFoot().setLayout(new GridLayout(1,3));
        getFoot().add(dishPB);
        getFoot().add(tablePB);
        getFoot().add(orderPB);
    }


    /*
        UI组件类
     */
    class RemainDishCard extends RectangleCard {
        private JLabel count;
        private String dishName;
        public RemainDishCard(String dishName, Integer count, ActionListener listener) {
            this.dishName = dishName;
            add("East", JButtonBuilder.getInstance().text("--").listener(e->{
                listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
            }).actionCommand(dishName)
                    .background(Constants.Color.title)
                    .foreground(Constants.Color.subtitle).build());
            JPanel panel = new JPanel(new BorderLayout());
            panel.setOpaque(false);
            this.count = new JLabel(count.toString());
            this.count.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));
            panel.add("East", this.count);
            panel.add("Center",new JLabel(dishName));
            add("Center", panel);
        }
        public void setCount(String count){
            this.count.setText(count);
        }
        public String getDishName(){
            return dishName;
        }
    }
    class WaitTableCard extends RectangleCard {
        private JLabel waitTime;
        private String tableId;
        public WaitTableCard(String tableId, ActionListener listener) {
            this.tableId = tableId;
            MouseToAction mta = new MouseToAction(listener);
            add("West", JLabelBuilder.getInstance().mouseListener(mta)
                    .text(tableId).horizontalAlignment(JLabel.CENTER).build());
            waitTime = JLabelBuilder.getInstance().mouseListener(mta)
                    .text("0min").horizontalAlignment(JLabel.CENTER).build();
            add("East", waitTime);
            addMouseListener(mta);
        }
        public WaitTableCard(String tableId, String waitTime, ActionListener listener) {
            this.tableId = tableId;
            MouseToAction mta = new MouseToAction(listener);
            add("West", JLabelBuilder.getInstance().mouseListener(mta)
                    .text(tableId).horizontalAlignment(JLabel.CENTER).build());
            add("East", JLabelBuilder.getInstance().mouseListener(mta)
                    .text(waitTime).horizontalAlignment(JLabel.CENTER).build());
            addMouseListener(mta);
        }
        public void setWaitTime(String time){
            waitTime.setText(time);
        }
        public String getText() {
            return tableId;
        }
        class MouseToAction extends MouseAdapter {
            private ActionListener listener;
            public MouseToAction(ActionListener listener) {
                this.listener = listener;
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(listener != null) {
                    listener.actionPerformed(new ActionEvent(this, 1, ""));
                }
            }
        }
    }
    class DishDetailCard extends WaitTableCard {
        public DishDetailCard(String dishName, String info) {
            super(dishName, info,null);
        }
    }


    /*
        Service组件类
     */
    class UIDataController implements IKitchenService.IOrderDataObserver {
        private java.util.List<UIData> orderData;
        public UIDataController(){
            orderData = new ArrayList<>();
        }
        public void finish(String dishName) {
            for(UIData ud: orderData){
                if(ud.getDishName().equals(dishName) && ud.getCount() > ud.getFinishedCount()){
                    ud.incFinishedCount();
                    updateDishCount(dishName, getDishRemainCount(dishName));
                    System.out.println(ud.getDishName()+"/"+ud.getCount()+"/"+ud.getFinishedCount());
                    loadTableOrderDetail(ud.getTableId());
                    if(checkForFinishedAllDish(ud.getTableId())){
                        removeTable(ud.getTableId());
                        System.out.println(ud.getTableId()+" finished");
                    }
                    kf.getService().dishFinish(dishName, ud.getTableId());
                    break;
                }
            }
        }
        @Override
        public void newOrder(IKitchenService.IOrderData orderData) {
            String tableId = orderData.getTableId();
            Map<String, Integer> o = orderData.getOrder();
            for(String dishName : o.keySet()) {
                this.orderData.add(new UIData(tableId, dishName, o.get(dishName), 0));
                updateDishCount(dishName, getDishRemainCount(dishName));
            }
            Component[] components = table.getComponents();
            for(Component component: components){
                if(component instanceof WaitTableCard){
                    if(((WaitTableCard)component).getText().equals(tableId)){
                        return;
                    }
                }
            }
            table.add(new WaitTableCard(tableId, e->loadTableOrderDetail(tableId)));
            Utility.revalidate(table);
        }
        private Integer getDishRemainCount(String dishName){
            Integer count = 0;
            for(UIData ud: orderData){
                if(ud.getDishName().equals(dishName)){
                    count += ud.getCount() - ud.getFinishedCount();
                }
            }
            return count;
        }
        private void updateDishCount(String dishName, Integer count){
            for(Component comp : remainDish.getComponents()){
                if(comp instanceof RemainDishCard) {
                    RemainDishCard ndc = (RemainDishCard) comp;
                    if (ndc.getDishName().equals(dishName)) {
                        if (count != 0) {
                            ndc.setCount(count.toString());
                        } else {
                            remainDish.remove(ndc);
                            Utility.revalidate(remainDish);
                        }
                        return;
                    }
                }
            }
            RemainDishCard ndc = new RemainDishCard(dishName, count,
                    e->udc.finish(((RemainDishCard)e.getSource()).getDishName()));
            remainDish.add(ndc);
            Utility.revalidate(remainDish);
        }
        private Boolean checkForFinishedAllDish(String tableId){
            for(UIData ud: orderData){
                if(ud.getTableId().equals(tableId)){
                    if(ud.getCount() > ud.getFinishedCount()){
                        return false;
                    }
                }
            }
            return true;
        }
        private void removeTable(String tableId){
            for(Component comp : table.getComponents()){
                if(comp instanceof WaitTableCard){
                    if(((WaitTableCard) comp).getText().equals(tableId)){
                        table.remove(comp);
                        Utility.revalidate(table);
                        orderDetail.removeAll();
                        Utility.revalidate(orderDetail);
                        java.util.List<UIData> od = orderData;
                        orderData = new ArrayList<>();
                        for(UIData data : od){
                            if(!data.getTableId().equals(tableId)){
                                orderData.add(data);
                            }
                        }
                    }
                }
            }
        }
        private void loadTableOrderDetail(String tableId){
            orderDetail.removeAll();
            for(UIData ud: this.orderData){
                if(ud.getTableId().equals(tableId)){
                    orderDetail.add(new DishDetailCard(ud.getDishName(),
                            ud.getCount()+"/"+ud.getFinishedCount()));
                }
            }
            Utility.revalidate(orderDetail);
        }
    }
    class UIData{
        private String tableId;
        private String dishName;
        private Integer count;
        private Integer finishedCount;

        public String getTableId() {
            return tableId;
        }

        public UIData(String tableId, String dishName, Integer count, Integer finishedCount) {
            this.tableId = tableId;
            this.dishName = dishName;
            this.count = count;
            this.finishedCount = finishedCount;
        }

        public String getDishName() {
            return dishName;
        }

        public Integer getCount() {
            return count;
        }

        public Integer getFinishedCount() {
            return finishedCount;
        }

        public void incFinishedCount(){
            ++finishedCount;
        }
    }
}
