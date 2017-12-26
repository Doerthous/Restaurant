package restaurant.ui.client;

import restaurant.service.core.IClientService;
import restaurant.ui.component.*;
import restaurant.ui.component.border.AdvLineBorder;
import restaurant.ui.component.layout.PageLayout;
import restaurant.ui.component.thirdpart.ShadowBorder;
import restaurant.ui.component.thirdpart.VFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderUI extends BasePanel {
    private ClientFrame cf;


    public OrderUI(ClientFrame cf) {
        this.cf = cf;
        initServiceComponent();
        initUIComponent();
        loadData();
    }

    /*
        Service组件
     */
    private UIDataController udc;
    private void initServiceComponent(){
        udc = new UIDataController();
    }

    /*
        数据加载
     */
    private void loadData(){
        udc.loadMenu();
    }

    /*
        UI组件
     */
    private TotalDishCount totalDishCount;
    private TotalDishCost totalCost;
    private java.util.List<OrderDetailDishCard> orderDishs;
    private JPanel orderDetail;
    private JPanel menuPanel;
    private void initUIComponent() {
        orderDishs = new ArrayList<>();
        initMenu();
        initOrderDetail();

        // foot
        JPanel p = new JPanel(new BorderLayout());
        p.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        p.setOpaque(false);
        JButton confirm = new JButton("确认");
        confirm.setBackground(Constants.Color.title);
        confirm.setPreferredSize(new Dimension(Constants.ContentEastWidth/2, 0));
        confirm.addActionListener(e -> {
            udc.sendOrder();
        });
        p.add("West", confirm);
        JButton ret = new JButton("返回");
        ret.setPreferredSize(new Dimension(Constants.ContentEastWidth/2, 0));
        ret.setBackground(Constants.Color.title);
        ret.addActionListener(e -> {
            cf.main();
        });
        p.add("East", ret);
        getFoot().add("East", p);
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
        center.setPreferredSize(new Dimension(0, 80));
        getSubtitle().add("Center", center);

        // content
        PageLayout layout = new PageLayout();
        menuPanel = new JPanel(layout); // new ScrollablePanel();
        menuPanel.setBackground(Constants.Color.background);
        menuPanel.setBorder(new AdvLineBorder().setRight(1).setRightColor(restaurant.ui.Constants.Color.subtitle));
        PageButton pageButton = new PageButton(layout, menuPanel);
        //JScrollPane cCenterScrollPane = new JScrollPane(center);
        getContent().add("Center", menuPanel);
        getFoot().add("Center", pageButton);
    }
    private void initOrderDetail() {
        // subtitle
        JPanel east = new JPanel(new BorderLayout());
        east.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        east.add("Center", JLabelBuilder.getInstance().text("订单详情").horizontalAlignment(JLabel.CENTER)
                .foreground(Color.white).font(Constants.Font.font1).build());
        east.setOpaque(false);
        getSubtitle().add("East", east);

        // content
        east = new JPanel(new BorderLayout());
        east.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        east.setOpaque(false);


        orderDetail = new JPanel();
        orderDetail.setBackground(Constants.Color.background);
        PagePanel pagePanel = new PagePanel(orderDetail);
        pagePanel.setPageButtonBackground(restaurant.ui.Constants.Color.title);
        JPanel south = new JPanel(new VFlowLayout());
        south.setPreferredSize(new Dimension(0, Constants.OrderTotalInfoHeight));
        south.setOpaque(false);
        JPanel p1 = new JPanel(new BorderLayout());
        p1.setOpaque(false);
        p1.add("West",new JLabel("菜品数量："));
        totalDishCount = new TotalDishCount();
        udc.addDishCountChangeListener(totalDishCount);
        p1.add("Center", totalDishCount);
        JPanel p2 = new JPanel(new BorderLayout());
        p2.setOpaque(false);
        p2.add("West",new JLabel("总消费："));
        totalCost = new TotalDishCost();
        udc.addDishCountChangeListener(totalCost);
        p2.add("Center", totalCost);
        p2.add("East", new JLabel("元"));
        south.add(p1);
        south.add(p2);
        east.add("South", south);
        east.add("Center", pagePanel);
        getContent().add("East", east);
    }
    private void loadOrderDetail(){
        orderDetail.removeAll();
        for(OrderDetailDishCard p: orderDishs){
            orderDetail.add(p);
        }
        orderDetail.revalidate();
        orderDetail.repaint();
    }

    /*
        UI组件类
     */
    class OrderDishCard extends JPanel {
        private String dishName;
        private JLabel count;
        public OrderDishCard(int width, int height,
                             String dishName, String dishPrice, String dishPictrue, Integer count) {
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
            JLabel pictrue = new JLabel(dishPictrue);
            JButton dec = new JButton("-");
            this.count = new JLabel(count.toString(), JLabel.CENTER);
            JButton inc = new JButton("+");

            // 设置组件样式
            pictrue.setBorder(BorderFactory.createLineBorder(Color.black));
            this.count.setBorder(BorderFactory.createLineBorder(Color.black));
            jPanel1.setOpaque(false);
            jPanel2.setOpaque(false);

            // 添加组件到内容面板
            jPanel1.add("West", name);
            jPanel1.add("East", price);
            add("North",jPanel1);
            add("Center", pictrue);
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
        public TotalDishCount() {
            setText("0");
        }

        @Override
        public void update(String name, Float delta, Float price) {
            setText(String.valueOf(Float.valueOf(getText())+delta));
        }
    }
    class TotalDishCost extends JLabel implements DishCountChange{
        public TotalDishCost() {
            setText("0");
        }

        @Override
        public void update(String name, Float delta, Float price) {
            setText(String.valueOf(Float.valueOf(getText())+delta*price));
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
                dishes.add(new UIDishData(di.getDishName(), di.getPrice(), di.getType()));
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
            cf.getService().sendOrder(order);
        }

        public void loadMenu() {
            loadMenuByType("全部");
        }
        public void loadMenuByType(String type){
            menuPanel.removeAll();
            for (UIDishData di : dishes) {
                if(type.equals("全部") || di.getType().equals(type)) {
                    menuPanel.add(new OrderDishCard(Constants.OrderDishCardSize, Constants.OrderDishCardSize,
                            di.getName(), di.getPrice().toString(), di.getPictrue(), di.getCount()));
                }
            }
            menuPanel.setVisible(false);
            menuPanel.revalidate();
            menuPanel.setVisible(true);
        }
    }
    class UIDishData {
        private String name;
        private Float price;
        private Integer count;
        private String type;
        // other info

        private UIDishData(String name, Float price, String type) {
            this.name = name;
            this.price = price;
            this.count = 0;
            this.type = type;
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
        public String getPictrue() { return ""; }
        public String getType() {
            return type;
        }
        public void setCount(Integer count) {
            this.count = count;
        }
    }
}
