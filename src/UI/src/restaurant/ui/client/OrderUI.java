package restaurant.ui.client;

import restaurant.service.core.IClientService;
import restaurant.ui.component.JLabelBuilder;
import restaurant.ui.component.thirdpart.ModifiedFlowLayout;
import restaurant.ui.component.thirdpart.VFlowLayout;
import restaurant.ui.ColorConstants;
import restaurant.ui.FontConstants;
import restaurant.ui.component.BasePanel;
import restaurant.ui.component.JButtonBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderUI extends BasePanel {
    private ClientFrame cf;
    private JPanel menuPanel;

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
    private void initUIComponent() {
        orderDishs = new ArrayList<>();
        initMenu();
        initOrderDetail();

        // foot
        JPanel p = new JPanel(new BorderLayout());
        p.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        p.setOpaque(false);
        JButton confirm = new JButton("确认");
        confirm.setBackground(ColorConstants.title);
        confirm.setPreferredSize(new Dimension(Constants.ContentEastWidth/2, 0));
        confirm.addActionListener(e -> {
            udc.sendOrder();
        });
        p.add("West", confirm);
        JButton ret = new JButton("返回");
        ret.setPreferredSize(new Dimension(Constants.ContentEastWidth/2, 0));
        ret.setBackground(ColorConstants.title);
        ret.addActionListener(e -> {
            cf.main();
        });
        p.add("East", ret);
        getFoot().add("East", p);
    }
    private void initMenu() {
        // subtitle
        JPanel center = new C1(3, ColorConstants.subtitle);
        for(String type: cf.getService().getDishType()){
            JButton button = JButtonBuilder.getInstance().text(type).listener(e->udc.loadMenuByType(type))
                    .foreground(Color.white).background(ColorConstants.subtitle).font(FontConstants.font1)
                    .build();
            center.add(button);
        }
        center.setPreferredSize(new Dimension(0, 80));
        getSubtitle().add("Center", center);

        // content
        center = new JPanel();
        center.setLayout(new BorderLayout(0, 0));
        center = new JPanel(); // new ScrollablePanel();
        center.setLayout(new ModifiedFlowLayout(FlowLayout.LEFT));
        center.setBackground(ColorConstants.background);
        menuPanel = center;
        JScrollPane cCenterScrollPane = new JScrollPane(center);
        getContent().add("Center", cCenterScrollPane);
    }
    private void initOrderDetail() {
        // subtitle
        JPanel east = new JPanel(new BorderLayout());
        east.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        east.add("Center", JLabelBuilder.getInstance().text("订单详情").horizontalAlignment(JLabel.CENTER)
                .foreground(Color.white).font(FontConstants.font1).build());
        east.setOpaque(false);
        getSubtitle().add("East", east);

        // content
        east = new JPanel(new BorderLayout());
        east.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        east.setOpaque(false);
        orderDetail = new JPanel(new VFlowLayout());
        orderDetail.setBackground(ColorConstants.background);
        JScrollPane jsp = new JScrollPane(orderDetail);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel south = new JPanel(new VFlowLayout());
        south.setPreferredSize(new Dimension(0, Constants.OrderTotalInfoHeight));
        south.setOpaque(false);
        south.setBorder(BorderFactory.createLineBorder(ColorConstants.subtitle));
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
        east.add("Center", jsp);
        east.add("South", south);
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
            int x = width;
            int y = height;
            SpringLayout layout = new SpringLayout();
            setLayout(layout);
            setBorder(BorderFactory.createLineBorder(Color.black));

            // 创建组件
            JLabel name = new JLabel(dishName);
            JLabel price = new JLabel(dishPrice);
            JLabel pictrue = new JLabel(dishPictrue);
            JButton dec = new JButton("-");
            this.count = new JLabel(count.toString(), JLabel.CENTER);
            JButton inc = new JButton("+");

            pictrue.setBorder(BorderFactory.createLineBorder(Color.black));

            // 添加组件到内容面板
            add(name);
            add(price);
            add(pictrue);
            add(dec);
            add(this.count);
            add(inc);

            //
            SpringLayout.Constraints panelCons = layout.getConstraints(this);
            panelCons.setConstraint(SpringLayout.EAST, Spring.constant(x));
            panelCons.setConstraint(SpringLayout.SOUTH, Spring.constant(y));

            SpringLayout.Constraints nameCons = layout.getConstraints(name);
            nameCons.setX(Spring.constant(5));
            nameCons.setY(Spring.constant(5));

            SpringLayout.Constraints priceCons = layout.getConstraints(price);
            priceCons.setY(Spring.constant(5));
            priceCons.setConstraint(SpringLayout.EAST,
                    Spring.sum(Spring.constant(x), Spring.minus(Spring.constant(5)))); // x-5

            SpringLayout.Constraints decCons = layout.getConstraints(dec);
            decCons.setX(Spring.constant(5));
            decCons.setConstraint(SpringLayout.EAST,
                    Spring.sum(Spring.constant(5), Spring.scale(Spring.constant(x), 0.25f))); // x/4+5
            decCons.setConstraint(SpringLayout.SOUTH,
                    Spring.sum(Spring.constant(y), Spring.minus(Spring.constant(5)))); // y-5

            SpringLayout.Constraints incCons = layout.getConstraints(inc);
            incCons.setConstraint(SpringLayout.EAST,
                    Spring.sum(Spring.constant(x), Spring.minus(Spring.constant(5)))); // x-5
            incCons.setConstraint(SpringLayout.WEST,
                    Spring.sum(Spring.minus(Spring.constant(5)), Spring.scale(Spring.constant(x), 0.75f))); // x-5-(x/4)
            incCons.setConstraint(SpringLayout.SOUTH,
                    Spring.sum(Spring.constant(y), Spring.minus(Spring.constant(5)))); // y-5

            SpringLayout.Constraints picCons = layout.getConstraints(pictrue);
            picCons.setX(Spring.constant(5));
            picCons.setY(nameCons.getConstraint(SpringLayout.SOUTH));
            picCons.setConstraint(SpringLayout.EAST,
                    Spring.sum(Spring.constant(x), Spring.minus(Spring.constant(5)))); // x-5
            picCons.setConstraint(SpringLayout.SOUTH,
                    Spring.sum(decCons.getConstraint(SpringLayout.NORTH),Spring.minus(Spring.constant(5))));

            SpringLayout.Constraints countCons = layout.getConstraints(this.count);
            countCons.setX(Spring.constant(5));
            countCons.setConstraint(SpringLayout.NORTH,
                    Spring.sum(picCons.getConstraint(SpringLayout.SOUTH), Spring.constant(5)));
            countCons.setConstraint(SpringLayout.WEST, decCons.getConstraint(SpringLayout.EAST));
            countCons.setConstraint(SpringLayout.EAST, incCons.getConstraint(SpringLayout.WEST));
            countCons.setConstraint(SpringLayout.SOUTH,
                    Spring.sum(Spring.constant(y), Spring.minus(Spring.constant(5)))); // y-5

            //
            inc.addActionListener(e->{ inc(); });
            inc.setFocusPainted(false);
            dec.addActionListener(e->{ dec(); });
            dec.setFocusPainted(false);
            this.count.setBorder(BorderFactory.createLineBorder(Color.black));

            setBackground(ColorConstants.title);
            //setBorder(ShadowBorder.newBuilder().shadowSize(5).center().build());
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
    class OrderDetailDishCard extends JPanel implements DishDataChange{
        private String dishName;
        private JLabel count;
        public OrderDetailDishCard(String name, String count, int height){
            this.dishName = name;
            setLayout(new BorderLayout());
            setBackground(ColorConstants.title);
            setPreferredSize(new Dimension(0, height));
            JPanel p = new JPanel(new BorderLayout());
            p.setOpaque(false);
            p.add("West", new JLabel(name));
            this.count = new JLabel(count);
            p.add("East", this.count);
            add("Center", p);
            JLabel l1 = new JLabel("");
            l1.setPreferredSize(new Dimension(5,0));
            JLabel l2 = new JLabel("");
            l2.setPreferredSize(new Dimension(5,0));
            add("West", l1);
            add("East", l2);
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
                        OrderDetailDishCard oddc = new OrderDetailDishCard(name, "1", 50);
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
