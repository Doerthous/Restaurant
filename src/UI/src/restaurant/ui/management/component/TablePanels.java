package restaurant.ui.management.component;

import doerthous.other.cli.CLI;
import restaurant.service.core.IManagementService;
import restaurant.service.core.vo.Employee;
import restaurant.ui.Constants;
import restaurant.ui.component.PagePanel;
import restaurant.ui.component.RectangleCard;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.component.builder.JPanelBuilder;
import restaurant.ui.management.ManagementFrame;
import restaurant.ui.utils.GBC;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TablePanels implements ActionListener, IManagementService.ITableObserver{
    private ManagementFrame mf;
    private String tableId;
    private OT ot;
    private FTI fti;
    private BTI bti;
    private Boolean isOnline;
    private JPanel info;
    private JPanel notification;
    private JPanel n;
    private ActionListener listener;

    private static class Notification {
        public static String KITCHEN = "传菜";
        public static String CLIENT = "服务";
        public String type;
        public String content;

        public static Notification kitchen(String content) {
            Notification n = new Notification();
            n.type = KITCHEN;
            n.content = content;
            return n;
        }
        public static Notification client() {
            Notification n = new Notification();
            n.type = CLIENT;
            n.content = "";
            return n;
        }
    }
    private java.util.List<Notification> notifications;
    private int targetIndex;


    public TablePanels(String tableId, ManagementFrame mf) {
        this.tableId = tableId;
        this.mf = mf;
        this.isOnline = false;
        mf.getService().addTableObserver(this);
        notifications = new ArrayList<>();

        fti = new FTI();
        fti.setListener(this);
        bti = new BTI();
        bti.setListener(this);
        ot = new OT(tableId);
        info = new JPanel(new BorderLayout());
        info.setOpaque(false);
        info.add(fti);
        n = new JPanel();
        n.setOpaque(false);
        notification = new PagePanel(n);
    }

    public OT getOt() {
        return ot;
    }

    public JPanel getTableInfo(){
        return info;
    }

    public JPanel getNotification() {
        return notification;
    }

    public void setActionListener(ActionListener listener){
        this.listener = listener;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "开台":{
                if(isOnline) {
                    info.remove(fti);
                    bti.setJlCusCnt(fti.getCustomerCount());
                    bti.setJlTotCst("0");
                    info.add(bti);
                    Utility.revalidate(info);
                    ot.setBackground(Color.red);
                    mf.getService().openTable(tableId, Integer.valueOf(fti.getCustomerCount()));
                }
            } break;
            case "通知":{
                loadNotification();
                listener.actionPerformed(new ActionEvent(this, 1, "通知"));
            } break;
            case "结账":{
                info.remove(bti);
                info.add(fti);
                Utility.revalidate(info);
                ot.setBackground(Color.green);
                mf.getService().closeTable(tableId);
            } break;
            case "指派":{
                new NF();
            }
        }
    }

    @Override
    public void online(String tableId) {
        if(this.tableId.equals(tableId)){
            isOnline = true;
            ot.setBackground(Color.green);
        }
    }

    @Override
    public void dishFinish(String dishName, String tableId) {
        notifications.add(Notification.kitchen(dishName));
        loadNotification();
    }

    @Override
    public void requestService(String tableId) {
        notifications.add(Notification.client());
        loadNotification();
    }

    @Override
    public void newOrder(String tableId) {

    }


    private class FTI extends JPanel implements ActionListener {
        private ActionListener listener;
        private JButton jb;
        private JTextField jtf;

        public FTI() {
            setLayout(new BorderLayout());
            setOpaque(false);
            JPanel panel = JPanelBuilder.getInstance().opaque(false).layout(new BorderLayout())
                    .border(BorderFactory.createEmptyBorder(10,20,10,20))
                    .build();
            jb = JButtonBuilder.getInstance().text("开台").listener(this)
                    .background(Constants.Color.title).focusPainted(false).build();
            panel.add(jb);
            add(panel, BorderLayout.SOUTH);

            jtf = new JTextField();
            JLabel jl1 = new JLabel("餐桌号：");
            JLabel jl2 = new JLabel("顾客数：");
            JLabel jlTableId = new JLabel(tableId);
            panel = new JPanel(new GridBagLayout());
            panel.setOpaque(false);

            panel.add(jl1, new GBC(0,0));
            panel.add(jlTableId, new GBC(1,0).setAnchor(GBC.WEST));
            panel.add(jl2, new GBC(0,1));
            panel.add(jtf, new GBC(1,1).setFill(GBC.HORIZONTAL).setWeight(1,1));
            panel.setBorder(BorderFactory.createEmptyBorder(20,20,0,20));

            add(panel, BorderLayout.CENTER);
        }


        public void setListener(ActionListener listener) {
            this.listener = listener;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() instanceof JButton) {
                listener.actionPerformed(new ActionEvent(this,
                        1, ((JButton)e.getSource()).getText()));
            }
        }

        public String getCustomerCount(){
            return jtf.getText();
        }
    }
    private class BTI extends JPanel implements ActionListener {
        private ActionListener listener;
        private JButton jbN;
        private JButton jbC;
        private JLabel jlCusCnt;
        private JLabel jlTotCst;

        public BTI() {
            setLayout(new GridBagLayout());
            setOpaque(false);

            jbN = JButtonBuilder.getInstance().text("通知").listener(this)
                    .background(Constants.Color.title).focusPainted(false).build();
            jbC = JButtonBuilder.getInstance().text("结账").listener(this)
                    .background(Constants.Color.title).focusPainted(false).build();
            JLabel jl1 = new JLabel("餐桌号：");
            JLabel jl2 = new JLabel("顾客数：");
            JLabel jl3 = new JLabel("总消费：");
            JLabel jlTableId = new JLabel(tableId);
            jlCusCnt = new JLabel();
            jlTotCst = new JLabel();

            add(jl1, new GBC(0,0).setFill(GBC.BOTH).setWeight(1,1));
            add(jlTableId, new GBC(1,0).setFill(GBC.BOTH).setWeight(1,1));
            add(jl2, new GBC(0,1).setFill(GBC.BOTH).setWeight(1,1));
            add(jlCusCnt, new GBC(1,1).setFill(GBC.BOTH).setWeight(1,1));
            add(jl3, new GBC(0,2).setFill(GBC.BOTH).setWeight(1,1));
            add(jlTotCst, new GBC(1,2).setFill(GBC.BOTH).setWeight(1,1));
            add(jbN, new GBC(0,3).setFill(GBC.HORIZONTAL).setWeight(1,1));
            add(jbC, new GBC(1,3).setFill(GBC.HORIZONTAL).setWeight(1,1));

            setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
        }

        public void setJlCusCnt(String cc) {
            this.jlCusCnt.setText(cc);
        }

        public void setJlTotCst(String tc) {
            this.jlTotCst.setText(tc);
        }

        public void setListener(ActionListener listener) {
            this.listener = listener;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(listener != null){
                if(e.getSource() instanceof JButton) {
                    listener.actionPerformed(new ActionEvent(this,
                            1, ((JButton)e.getSource()).getText()));
                }
            }
        }

    }
    public class OT extends RectangleCard implements MouseListener {
        private java.util.List<ActionListener> listeners;
        public OT(String tableId) {
            super(new Dimension(100,100));
            setLayout(new BorderLayout(0,0));
            listeners = new ArrayList<>();
            addMouseListener(this);
            add(JLabelBuilder.getInstance().text(tableId)
                    .mouseListener(this).build());
        }
        public OT addActionListener(ActionListener listener){
            listeners.add(listener);
            return this;
        }

        public String getTableId(){
            return tableId;
        }

        public void actionPerformed(ActionEvent e) {
            e = new ActionEvent(this, 1, tableId);
            for(ActionListener listener: listeners){
                listener.actionPerformed(e);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            ActionEvent e1 = new ActionEvent(this, 1, tableId);
            for(ActionListener listener: listeners){
                listener.actionPerformed(e1);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
    private class NF extends JDialog {
        private JPanel panel;
        public NF() {
            setModal(true);
            setSize(new Dimension(400,300));
            setLocationRelativeTo(null);

            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            getContentPane().setBackground(Constants.Color.background);
            panel = new JPanel();
            panel.setOpaque(false);
            add(new PagePanel(panel));
            JPanel buttons = new JPanel(new GridBagLayout());
            buttons.setOpaque(false);
            JButton ok = new JButton("确定");
            ok.addActionListener(e -> doSomething());
            JButton ca = new JButton("取消");
            ca.addActionListener(e -> close());
            buttons.add(ok, new GBC(0,0).setWeight(1,1));
            buttons.add(ca, new GBC(1,0).setWeight(1,1));
            add(buttons, BorderLayout.SOUTH);
            buttons.setPreferredSize(new Dimension(0,50));
            java.util.List<Employee> waiters = mf.getService().getOnlineWaiter();
            for(Employee waiter: waiters) {
                panel.add(new WaiterCard(waiter.getCode(), waiter.getName()));
            }
            setVisible(true);
        }

        private void doSomething(){
            Component[] components = panel.getComponents();
            for(Component component: components){
                if(component instanceof WaiterCard){
                    WaiterCard wc = (WaiterCard)component;
                    if(wc.isChecked()) {
                        System.out.println(wc.getCode());
                        Notification notification = notifications.get(targetIndex);
                        if(notification.type.equals(Notification.CLIENT)){
                            mf.getService().customerCall(tableId, wc.getCode());
                        } else {
                            mf.getService().dishFinish(tableId, notification.content, wc.getCode());
                        }
                    }
                    notifications.remove(targetIndex);
                    loadNotification();
                }
            }
            close();
        }

        private void close(){
            dispose();
        }

        class WaiterCard extends RectangleCard {
            private JCheckBox jcb;
            private String code;
            public WaiterCard(String code, String name) {
                this.code = code;
                jcb = new JCheckBox();
                jcb.setBackground(Constants.Color.title);
                add(jcb, BorderLayout.WEST);
                JPanel panel = new JPanel();
                panel.setOpaque(false);
                panel.add(new JLabel(code), BorderLayout.WEST);
                panel.add(new JLabel(name), BorderLayout.CENTER);
                add(panel);
            }
            public Boolean isChecked(){
                return jcb.isSelected();
            }
            public String getCode(){
                return code;
            }
        }
    }
    private class N extends RectangleCard implements MouseListener {
        public int index;
        public N(int index) {
            this.index = index;
            Notification notification = notifications.get(index);
            String text = notification.type;
            if(notification.type.equals(Notification.KITCHEN)){
                text += " ["+notification.content+"]";
            }
            add(JLabelBuilder.getInstance().text(text)
                    .mouseListener(this).build());
            addMouseListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            targetIndex = this.index;
            actionPerformed(new ActionEvent(this, 1,"指派"));
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private void loadNotification(){
        n.removeAll();
        for(int i = 0; i < notifications.size(); ++i) {
            n.add(new N(i));
        }
        Utility.revalidate(notification);
    }
}