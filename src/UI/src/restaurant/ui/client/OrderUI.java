package restaurant.ui.client;

import restaurant.service.core.IClientService;
import restaurant.ui.component.*;
import restaurant.ui.component.border.AdvLineBorder;
import restaurant.ui.component.layout.PageLayout;
import restaurant.ui.component.thirdpart.ShadowBorder;
import restaurant.ui.component.thirdpart.VFlowLayout;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderUI extends BasePanel3 {
    private ClientFrame cf;


    public OrderUI(ClientFrame cf) {
        this.cf = cf;
        initServiceComponent();
        initUIComponent();
        udc.loadMenu();
    }

    /*
        Service组件
     */
    private UIDataController udc;
    private void initServiceComponent(){
        udc = new UIDataController();
    }


    /*
        UI组件
     */
    private TotalDishCount totalDishCount;
    private TotalDishCost totalCost;
    private java.util.List<OrderDetailDishCard> orderDishs;
    private JPanel orderDetail;
    private void initUIComponent() {
        orderDishs = new ArrayList<>();
        initMenu();
        initOrderDetail();

        // foot
        addFootRight(JButtonBuilder.getInstance().text("确认").background(Constants.Color.title)
                .listener(e->udc.sendOrder()).build());
        addFootRight(JButtonBuilder.getInstance().text("返回").background(Constants.Color.title)
                .listener(e->cf.main()).build());
    }
    private void initMenu() {
        // subtitle
        JPanel center = new C1(3, Constants.Color.subtitle);
        for(String type: cf.getService().getDishType()){
            JButton button = JButtonBuilder.getInstance().text(type).listener(e->udc.loadMenuByType(type))
                    .foreground(Color.white).background(Constants.Color.subtitle).font(Constants.Font.font1)
                    .build();
            center.add(button);
        }
        getSubtitleLeft().add(center);

        // content
        PageLayout layout = new PageLayout();
        getContentLeft().setLayout(layout);
        getContentLeft().setBorder(new AdvLineBorder().setRight(1).setRightColor(restaurant.ui.Constants.Color.subtitle));
        PageButton pageButton = new PageButton(layout, getContentLeft());

        // foot
        getFootLeft().add(pageButton);
    }
    private void initOrderDetail() {
        // subtitle
        getSubtitleRight().add(JLabelBuilder.getInstance().text("订单详情")
                .horizontalAlignment(JLabel.CENTER).foreground(Color.white)
                .font(Constants.Font.font1).build());

        // content
        orderDetail = new JPanel();
        orderDetail.setBackground(Constants.Color.background);
        PagePanel pagePanel = new PagePanel(orderDetail);
        pagePanel.setPageButtonBackground(Constants.Color.title);
        getContentRightTop().add(pagePanel);

        totalDishCount = new TotalDishCount();
        totalCost = new TotalDishCost();
        getContentRightBottom().add(totalDishCount, BorderLayout.NORTH);
        getContentRightBottom().add(totalCost, BorderLayout.CENTER);
        getContentRightBottom().setBorder(BorderFactory.createEmptyBorder(20,10,20,10));
    }
    private void loadOrderDetail(){
        orderDetail.removeAll();
        for(OrderDetailDishCard p: orderDishs){
            orderDetail.add(p);
        }
        Utility.revalidate(orderDetail);
    }

    /*
        UI组件类
     */
    class OrderDishCard extends JPanel {
        private String dishName;
        private JLabel count;
        public OrderDishCard(int width, int height,
                             String dishName, String dishPrice, ImageIcon dishPicture, Integer count) {
            this.dishName = dishName;

            // 设置样式
            setPreferredSize(new Dimension(width, height));
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createCompoundBorder(
                    ShadowBorder.newBuilder().shadowSize(2).build(),
                    BorderFactory.createEmptyBorder(20,20,20,20)
            ));
            setBackground(Constants.Color.title);

            // 创建组件
            JPanel jPanel1  = new JPanel(new BorderLayout());
            JPanel jPanel2  = new JPanel(new BorderLayout());
            JLabel name = new JLabel(dishName);
            JLabel price = new JLabel(dishPrice);
            PictureLabel picture = new PictureLabel(dishPicture);
            JButton dec = new JButton("-");
            this.count = new JLabel(count.toString(), JLabel.CENTER);
            JButton inc = new JButton("+");

            // 设置组件样式
            picture.setBorder(BorderFactory.createLineBorder(Color.black));
            this.count.setBorder(BorderFactory.createLineBorder(Color.black));
            jPanel1.setOpaque(false);
            jPanel2.setOpaque(false);

            // 添加组件到内容面板
            jPanel1.add("West", name);
            jPanel1.add("East", price);
            add("North",jPanel1);
            add("Center", picture);
            jPanel2.add("West", dec);
            jPanel2.add("Center", this.count);
            jPanel2.add("East", inc);
            add("South", jPanel2);

            // 设置组件事件
            inc.addActionListener(e->{ inc(); });
            inc.setFocusPainted(false);
            dec.addActionListener(e->{ dec(); });
            dec.setFocusPainted(false);
        }

        private void inc(){
            count.setText(String.valueOf(Integer.valueOf(count.getText().toString())+1));
            udc.incDishCount(dishName);
        }
        private void dec(){
            Integer c = Integer.valueOf(count.getText().toString());
            if(c > 0){
                count.setText(String.valueOf(c-1));
                udc.decDishCount(dishName);
            }
        }
    }
    class OrderDetailDishCard extends RectangleCard implements DishDataChange{
        private String dishName;
        private JLabel count;
        public OrderDetailDishCard(String name, String count, int height){
            dishName = name;
            add("West", JLabelBuilder.getInstance().text(name).horizontalAlignment(JLabel.CENTER).build());
            this.count = JLabelBuilder.getInstance().text(count).horizontalAlignment(JLabel.CENTER).build();
            add("East", this.count);
        }
        public String getDishName(){
            return dishName;
        }
        @Override
        public void update(String name) {
            if(dishName.equals(name)){
                count.setText(udc.getDishData(name).getCount().toString());
            }
        }
    }
    class TotalDishCount extends JLabel implements DishCountChange{
        private Float count = 0f;
        private String prefix = "菜品数量：";
        public TotalDishCount() {
            setText(prefix+count);
        }

        @Override
        public void update(String name, Float delta, Float price) {
            count += delta;
            setText(prefix+count);
        }

        public void setCount(Float count){
            this.count = count;
            setText(prefix+count);
        }
    }
    class TotalDishCost extends JLabel implements DishCountChange{
        private Float cost = 0f;
        private String prefix = "总消费：";
        private String suffix = "元";
        public TotalDishCost() {
            setText(prefix+cost+suffix);
        }

        @Override
        public void update(String name, Float delta, Float price) {
            cost += price*delta;
            setText(prefix+cost+suffix);
        }

        public Float getCost() {
            return cost;
        }
        public void setCost(Float cost){
            this.cost = cost;
            setText(prefix+cost+suffix);
        }
    }
    class C1 extends JPanel {

        private int cols = 3;
        private int currPage = 0;
        private JPanel content;
        public C1(int cols, Color background){
            this.cols = cols;
            components = new ArrayList<>();
            setLayout(new BorderLayout());
            add("West", JButtonBuilder.getInstance().text("<").listener(e->priorPage())
                    .opaque(false).contentAreaFilled(false).focusPainted(false).build());
            add("East", JButtonBuilder.getInstance().text(">").listener(e->nextPage())
                    .opaque(false).contentAreaFilled(false).focusPainted(false).build());
            content = new JPanel(new GridLayout(1, cols, 1,0));
            content.setBackground(background);
            setBackground(background);
            add("Center", content);
        }

        private java.util.List<Component> components;
        public Component add(Component component){
            components.add(component);
            fresh();
            return component;
        }

        private void nextPage(){
            if((1+currPage)*cols < components.size()){
                ++currPage;
            }
            fresh();
        }
        private void priorPage(){
            if(currPage > 0){
                --currPage;
            }
            fresh();
        }
        public void fresh(){
            content.removeAll();
            int startIdx = currPage * cols;
            int endIdx = startIdx + cols;
            int compCount = components.size();
            for (int i = startIdx; i < endIdx; ++i) {
                if (i < compCount) {
                    content.add(components.get(i));
                } else {
                    JLabel l = new JLabel("");
                    content.add(l);
                }
            }
            content.setVisible(false);
            content.revalidate();
            content.setVisible(true);
        }
    }
    /*
        Service组件类
     */
    interface DishDataChange{
        void update(String name);
    }
    interface DishCountChange{
        void update(String name, Float delta, Float price);
    }
    class UIDataController {
        java.util.List<DishDataChange> listeners;
        java.util.List<DishCountChange> listeners2;
        java.util.List<UIDishData> dishes;
        public UIDataController(){
            listeners = new ArrayList<>();
            listeners2 = new ArrayList<>();
            initDishes();
        }
        public void initDishes(){
            dishes = new ArrayList<>();
            java.util.List<IClientService.IDishInfo> dis = cf.getService().getDishMenu();
            for(IClientService.IDishInfo di: dis){
                dishes.add(new UIDishData(di.getDishName(), di.getPrice(), di.getType(), di.getPicture()));
            }
        }
        public void resetDishes(){
            for(UIDishData udd: dishes){
                udd.setCount(0);
                updateDishDataChangeListenners(udd.getName());
            }
        }
        public void incDishCount(String name){
            for(UIDishData udd: dishes){
                if(udd.getName().equals(name)){
                    udd.setCount(udd.getCount()+1);
                    if(udd.getCount() == 1){
                        OrderDetailDishCard oddc = new OrderDetailDishCard(name, "1",
                                restaurant.ui.Constants.UISize.RecordHeight);
                        addDishDataChangeListener(oddc);
                        orderDishs.add(oddc);
                    }
                    updateDishDataChangeListenners(udd.getName());
                    updateDishCountChangeListenners(udd.getName(), 1f, udd.getPrice());
                    loadOrderDetail();
                    break;
                }
            }
        }
        public void decDishCount(String name){
            for(UIDishData udd: dishes){
                if(udd.getName().equals(name)){
                    if(udd.getCount() > 0) {
                        udd.setCount(udd.getCount() - 1);
                        if(udd.getCount() == 0) {
                            for(OrderDetailDishCard oddc : orderDishs) {
                                if(oddc.getDishName().equals(name)) {
                                    orderDishs.remove(oddc);
                                    break;
                                }
                            }
                        }
                        updateDishDataChangeListenners(name);
                        updateDishCountChangeListenners(udd.getName(), -1f, udd.getPrice());
                        loadOrderDetail();
                        break;
                    }
                }
            }
        }
        public UIDishData getDishData(String name){
            for(UIDishData udd: dishes){
                if(udd.getName().equals(name)){
                    return udd;
                }
            }
            return null;
        }
        public void addDishDataChangeListener(DishDataChange listener){
            listeners.add(listener);
        }
        public void updateDishDataChangeListenners(String dishName){
            for(DishDataChange ddc: listeners){
                ddc.update(dishName);
            }
        }
        public void addDishCountChangeListener(DishCountChange listener){
            listeners2.add(listener);
        }
        public void updateDishCountChangeListenners(String dishName, Float delta, Float price){
            for(DishCountChange dcc: listeners2){
                dcc.update(dishName, delta, price);
            }
        }

        public void sendOrder(){
            Map<String, Integer> order = new HashMap<>();
            for(UIDishData udd: dishes){
                order.putIfAbsent(udd.getName(), udd.getCount());
            }
            cf.getService().sendOrder(order, totalCost.getCost());
        }

        public void loadMenu() {
            loadMenuByType("全部");
        }
        public void loadMenuByType(String type){
            Container cl = getContentLeft();
            cl.removeAll();
            for (UIDishData di : dishes) {
                if(type.equals("全部") || di.getType().equals(type)) {
                    cl.add(new OrderDishCard(Constants.OrderDishCardSize, Constants.OrderDishCardSize,
                            di.getName(), di.getPrice().toString(), di.getPicture(), di.getCount()));
                }
            }
            Utility.revalidate(cl);
        }
    }
    class UIDishData {
        private String name;
        private Float price;
        private Integer count;
        private String type;
        private ImageIcon picture;

        private UIDishData(String name, Float price, String type, byte[] picture) {
            this.name = name;
            this.price = price;
            this.count = 0;
            this.type = type;
            if(picture != null) {
                this.picture = new ImageIcon(picture);
            }
        }
        public String getName() {
            return name;
        }
        public Float getPrice() {
            return price;
        }
        public Integer getCount() {
            return count;
        }
        public ImageIcon getPicture() { return picture; }
        public String getType() {
            return type;
        }
        public void setCount(Integer count) {
            this.count = count;
        }
    }

    public void start(){
        udc = new UIDataController();
        udc.loadMenu();
        udc.addDishCountChangeListener(totalCost);
        udc.addDishCountChangeListener(totalDishCount);
        orderDishs = new ArrayList<>();
        totalCost.setCost(0f);
        totalDishCount.setCount(0f);
        loadOrderDetail();
    }
    public void stop(){

    }
}
