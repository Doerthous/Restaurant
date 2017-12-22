package restaurant.ui.kitchen;

import restaurant.service.core.IKitchenService;
import test.ui.layout.custom.PageLayout;
import restaurant.ui.ColorConstants;
import restaurant.ui.component.JLabelBuilder;
import restaurant.ui.component.thirdpart.VFlowLayout;
import restaurant.ui.FontConstants;
import restaurant.ui.component.BasePanel;
import restaurant.ui.component.JButtonBuilder;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

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
    private JPanel table;
    private PageLayout tablePL;
    private PageButton tablePB;
    private JPanel orderDetail;
    private void initUIComponent(){
        // subtitle
        getSubtitle().setLayout(new GridLayout(1,3));
        getSubtitle().add(JLabelBuilder.getInstance().text("需上菜品").horizontalAlignment(JLabel.CENTER)
                .foreground(Color.white).font(FontConstants.font1).build());
        getSubtitle().add(JLabelBuilder.getInstance().text("订餐餐桌").horizontalAlignment(JLabel.CENTER)
                .foreground(Color.white).font(FontConstants.font1).build());
        getSubtitle().add(JLabelBuilder.getInstance().text("订餐详情").horizontalAlignment(JLabel.CENTER)
                .foreground(Color.white).font(FontConstants.font1).build());

        // content
        getContent().setLayout(new GridLayout(1,3));
        remainDish = new JPanel(new VFlowLayout());
        remainDish.setOpaque(false);
        getContent().add(remainDish);
        tablePL = new PageLayout();
        table = new JPanel(tablePL);
        table.setOpaque(false);
        getContent().add(table);
        orderDetail = new JPanel(new VFlowLayout());
        orderDetail.setOpaque(false);
        getContent().add(orderDetail);

        // font
        getFoot().setLayout(new GridLayout(1,3));
        getFoot().add(new PageButton(null,null,null,null));
        tablePB = new PageButton(
                e->tablePL.first(table),
                e->tablePL.prior(table),
                e->tablePL.next(table),
                e->tablePL.last(table));
        getFoot().add(tablePB);
        getFoot().add(new PageButton(null,null,null,null));
    }

    /*
        UI组件类
     */
    class RemainDishCard extends JPanel {
        private JLabel count;
        private String dishName;
        public RemainDishCard(String dishName, Integer count, ActionListener listener) {
            this.dishName = dishName;
            setLayout(new BorderLayout());
            setBackground(ColorConstants.title);
            add("East", JButtonBuilder.getInstance().text("-").listener(e->{
                listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
            }).actionCommand(dishName).build());
            JPanel panel = new JPanel(new BorderLayout());
            panel.setOpaque(false);
            this.count = new JLabel(count.toString());
            panel.add("East", this.count);
            panel.add("Center",new JLabel(dishName));
            add("Center", panel);
        }
        public void incCount(){
            count.setText(String.valueOf(Integer.valueOf(count.getText())+1));
        }
        public void decCount(){
            Integer c = Integer.valueOf(count.getText());
            if(c > 0){
                count.setText(String.valueOf(c-1));
            }
        }
        public void setCount(String count){
            this.count.setText(count);
        }
        public String getDishName(){
            return dishName;
        }
    }
    class PageButton extends JPanel {
        private JLabel pageInfo;
        public PageButton(ActionListener first, ActionListener prior, ActionListener next, ActionListener last) {
            setLayout(new BorderLayout());
            setOpaque(false);
            JPanel center = new JPanel(new BorderLayout());
            center.setOpaque(false);
            add("Center", center);

            JButton fb = JButtonBuilder.getInstance().text("<<").listener(first)
                    .opaque(false).contentAreaFilled(false).build();
            JButton pb = JButtonBuilder.getInstance().text("<").listener(prior)
                    .opaque(false).contentAreaFilled(false).build();
            JButton nb = JButtonBuilder.getInstance().text(">").listener(next)
                    .opaque(false).contentAreaFilled(false).build();
            JButton lb = JButtonBuilder.getInstance().text(">>").listener(last)
                    .opaque(false).contentAreaFilled(false).build();
            pageInfo = JLabelBuilder.getInstance().text("").horizontalAlignment(JLabel.CENTER).build();

            add("West", fb);
            center.add("West", pb);
            center.add("Center", pageInfo);
            center.add("East", nb);
            add("East", lb);
        }

        public void setPageInfo(String text){
            pageInfo.setText(text);
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
            table.add(JButtonBuilder.getInstance().text(tableId).listener(e->loadTableOrderDetail(tableId)).build());
            tablePB.setPageInfo(tablePL.getCurrentPage(table)+"/"+tablePL.getPageCount(table));
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
                if(comp instanceof JButton){
                    if(((JButton) comp).getText().equals(tableId)){
                        table.remove(comp);
                        Utility.revalidate(table);
                    }
                }
            }
        }
        private void loadTableOrderDetail(String tableId){
            orderDetail.removeAll();
            for(UIData ud: this.orderData){
                if(ud.getTableId().equals(tableId)){
                    orderDetail.add(new JLabel(ud.getDishName() +
                            " " + ud.getCount()+"/"+ud.getFinishedCount()));
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
