package restaurant.ui.client;

import restaurant.service.IClientService;
import restaurant.ui.ColorConstants;
import restaurant.ui.component.JLabelBuilder;
import restaurant.ui.component.thirdpart.ScrollablePanel;
import restaurant.ui.component.thirdpart.VFlowLayout;
import restaurant.ui.FontConstants;
import restaurant.ui.component.BasePanel;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChatUI extends BasePanel {


    public ChatUI(ClientFrame cf) {
        this.cf = cf;
        initServiceComponent();
        initUIComponent();
        loadData();
    }

    /*
        Service组件
    */
    private String targetTableId;
    private void initServiceComponent(){
        targetTableId = "";
    }
    private void sendMessage(String message){
        cf.getService().sendMessage(targetTableId, message);
        loadSessionWithTargetTable();
    }


    /*
        数据加载
     */
    private void loadData(){
        loadOnlineTables();
    }
    void loadOnlineTables(){
        uiOnlineTablesPanel.removeAll();
        OnlineTablePanel p = new OnlineTablePanel();
        p.loadTableIds(cf.getService().getOnlineTableIds());
        p.setOpaque(false);
        cf.getService().addChatObserver(p);
        uiOnlineTablesPanel.add(p);
        uiOnlineTablesPanel.revalidate();
    }
    private void loadSessionWithTargetTable(){
        cCenter.remove(uiChatboxPanel);
        uiChatboxPanel = new ChatBoxPanel(cf.getService().getTableId(),
                targetTableId, cf.getService().getSessionWith(targetTableId));
        cCenter.add("Center", uiChatboxPanel);
        cCenter.revalidate();
    }


    /*
        UI组件
     */
    private ClientFrame cf;
    private JPanel cCenter;
    private JPanel uiOnlineTablesPanel;
    private JPanel uiChatboxPanel;
    public void initUIComponent(){
        // content
        JPanel east = new JPanel();
        cCenter = new JPanel(new BorderLayout(20,20));
        cCenter.setBackground(ColorConstants.background);
        cCenter.add("North", new JLabel());
        cCenter.add("South", new JLabel());
        cCenter.add("East", new JLabel());
        cCenter.add("West", new JLabel());
        initChatRecordBox(cCenter);
        east.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        east.setBackground(ColorConstants.background);
        east.setLayout(new BorderLayout(0,0));
        east.setBorder(BorderFactory.createLineBorder(Color.black));
        initOnlineTablesPanel(east);
        getContent().add("Center", cCenter);
        getContent().add("East", east);

        // subtitle
        JPanel center = new JPanel();
        center.add(JLabelBuilder.getInstance().text("聊天记录").horizontalAlignment(JLabel.CENTER)
                .foreground(Color.white).font(FontConstants.font1).build());
        center.setOpaque(false);
        east = new JPanel(new BorderLayout());
        east.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        east.add("Center", JLabelBuilder.getInstance().text("在线人").horizontalAlignment(JLabel.CENTER)
                .foreground(Color.white).font(FontConstants.font1).build());
        east.setOpaque(false);
        getSubtitle().add("Center",center);
        getSubtitle().add("East",east);

        // foot
        JButton ret = new JButton("返回");
        ret.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        ret.setBackground(ColorConstants.title);
        ret.addActionListener(e->{
            cf.main();
        });
        getFoot().add("East", ret);
    }
    public void initChatRecordBox(JPanel container){
        uiChatboxPanel = new ChatBoxPanel("2T", "", new ArrayList<>());
        container.add("Center", uiChatboxPanel);
    }
    public void initOnlineTablesPanel(JPanel container){
        container.setLayout(new BorderLayout());
        uiOnlineTablesPanel = new JPanel();
        uiOnlineTablesPanel.setLayout(new VFlowLayout());
        uiOnlineTablesPanel.setBackground(ColorConstants.background);
        JScrollPane jsp = new JScrollPane(uiOnlineTablesPanel);
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        container.add("Center", jsp);
    }


    /*
        UI组件类
     */
    class ChatBoxPanel extends JPanel {
        private String self;
        private List<IClientService.IChatData> records;
        private JButton clear;
        private JButton send;
        private ScrollablePanel recordsPanel;
        private JTextArea input;
        private class CRC extends JPanel {
            public CRC(int JLabelLeftOrRight, String name, String time, String message){
                setLayout(new BorderLayout());
                add("North", new JLabel(name + "   " + time + "", JLabelLeftOrRight));
                JTextPane tp = new JTextPane();
                if(JLabelLeftOrRight == JLabel.RIGHT){
                    SimpleAttributeSet bSet = new SimpleAttributeSet();
                    StyleConstants.setAlignment(bSet, StyleConstants.ALIGN_RIGHT);
                    tp.setParagraphAttributes(bSet, true);
                }
                tp.setText(message);
                add("Center", tp);
            }
        }
        public ChatBoxPanel(String self, String other, List<IClientService.IChatData> records) {
            this.self = self;
            this.records = records;
            initUIComponent();
            loadData();
        }
        private void initUIComponent(){
            setLayout(new BorderLayout());
            recordsPanel = new ScrollablePanel(new VFlowLayout(VFlowLayout.TOP,0,0, true, false));
            JScrollPane csp = new JScrollPane(recordsPanel);
            csp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            add("Center", csp);
            JPanel cia = new JPanel(new BorderLayout());
            JPanel ciaBp = new JPanel(new BorderLayout());
            clear = new JButton("清空");
            clear.setBackground(ColorConstants.title);
            clear.addActionListener(e->{
                input.setText("");
            });
            ciaBp.add("North",clear);
            send = new JButton("发送");
            send.setBackground(ColorConstants.title);
            send.addActionListener(e->{
                sendMessage(input.getText());
                input.setText("");
            });
            ciaBp.add("South",send);
            cia.add("East",ciaBp);
            input = new JTextArea();
            input.setLineWrap(true);
            //input.setWrapStyleWord(true);
            cia.add("Center",input);
            add("South", cia);
        }
        private void loadData(){
            for(IClientService.IChatData icd: records){
                int i = JLabel.LEFT;
                if(icd.getTableId().equals(self)){
                    i = JLabel.RIGHT;
                }
                recordsPanel.add(new ChatBoxPanel.CRC(i, icd.getTableId(),icd.getTime(), icd.getMessage()));
            }
            if(records.size() == 0){
                recordsPanel.add(new JLabel("暂无消息"));
            }
        }
    }
    class OnlineTablePanel extends JPanel implements IClientService.IChatObserver {
        private List<JButton> tableIds;

        public OnlineTablePanel() {
            super(new VFlowLayout());
            this.tableIds = new ArrayList<>();
        }
        public void loadTableIds(List<String> tableIds){
            for(String id: tableIds) {
                JButton b = new JButton(id);
                b.setBackground(ColorConstants.title);
                b.setPreferredSize(new Dimension(0, 50));
                b.setFocusPainted(false);
                b.addActionListener(e -> {
                    targetTableId = b.getText();
                    b.setBackground(ColorConstants.title);
                    loadSessionWithTargetTable();
                });
                add(b);
                this.tableIds.add(b);
            }
        }
        @Override
        public void newMessage(String tableId) {
            if(!targetTableId.equals(tableId)){
                for(JButton b : tableIds){
                    if(b.getText().equals(tableId)){
                        b.setBackground(Color.green);
                    }
                }
            } else {
                loadSessionWithTargetTable();
            }
        }
        @Override
        public void offline(String tableId) {
            for(JButton id: tableIds) {
                if(id.getText().equals(tableId)){
                    remove(id);
                    setVisible(false);
                    revalidate();
                    setVisible(true);
                    break;
                }
            }
        }
        @Override
        public void online(String tableId) {
            for(JButton id: tableIds) {
                if(id.getText().equals(tableId)){
                    return;
                }
            }
            JButton b = new JButton(tableId);
            b.setBackground(ColorConstants.title);
            b.setPreferredSize(new Dimension(0, 50));
            b.setFocusPainted(false);
            b.addActionListener(e -> {
                targetTableId = b.getText();
                b.setBackground(ColorConstants.title);
                loadSessionWithTargetTable();
            });
            add(b);
            this.tableIds.add(b);
            setVisible(false);
            revalidate();
            setVisible(true);
        }
    }


}
