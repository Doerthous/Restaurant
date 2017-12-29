package restaurant.ui.client;


import restaurant.service.core.IClientService;
import restaurant.ui.component.*;
import restaurant.ui.component.border.AdvLineBorder;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.component.thirdpart.ScrollablePanel;
import restaurant.ui.component.thirdpart.ShadowBorder;
import restaurant.ui.component.thirdpart.VFlowLayout;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChatUI extends BasePanel2 {


    public ChatUI(ClientFrame cf) {
        this.cf = cf;
        initServiceComponent();
        initUIComponent();
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
    private void loadSessionWithTargetTable(){
        getContentLeft().removeAll();
        getContentLeft().add(new ChatBoxPanel(cf.getService().getTableId(),
                targetTableId, cf.getService().getSessionWith(targetTableId)));
        getContentLeft().revalidate();
    }


    /*
        UI组件
     */
    private ClientFrame cf;
    public void initUIComponent(){
        // content
        getContentLeft().setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        getContentLeft().add(new ChatBoxPanel(cf.getService().getTableId(), "", new ArrayList<>()));
        getContentRight().setBorder(new AdvLineBorder().setLeft(1)
                .setLeftColor(restaurant.ui.Constants.Color.subtitle));
        OnlineTablePanel p = new OnlineTablePanel(cf.getService().getOnlineTableIds());
        cf.getService().addChatObserver(p);
        getContentRight().add(new PagePanel(p).setPageButtonBackground(Constants.Color.title));

        // subtitle
        getSubtitleLeft().add(
                JLabelBuilder.getInstance().text("聊天记录").horizontalAlignment(JLabel.CENTER)
                        .foreground(Color.white).font(Constants.Font.font1).build()
        );

        getSubtitleRight().add(
                JLabelBuilder.getInstance().text("在线人").horizontalAlignment(JLabel.CENTER)
                        .foreground(Color.white).font(Constants.Font.font1).build()
        );

        // foot
        addFootRight(
                JButtonBuilder.getInstance().text("返回").background(Constants.Color.title)
                        .listener(e -> cf.main()).build()
        );
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
            clear.setBackground(Constants.Color.title);
            clear.addActionListener(e->{
                input.setText("");
            });
            ciaBp.add("North",clear);
            send = new JButton("发送");
            send.setBackground(Constants.Color.title);
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
        public OnlineTablePanel(List<String> tableIds) {
            setOpaque(false);
            for(String id: tableIds) {
                addOnlineTable(id);
            }
        }
        private void addOnlineTable(String id){
            add(JButtonBuilder.getInstance().text(id).background(Constants.Color.title)
                    .listener(e->{
                        targetTableId = id;
                        loadSessionWithTargetTable();
                        if(e.getSource() instanceof JButton){
                            ((JButton)e.getSource()).setBackground(Constants.Color.title);
                        }
                    })
                    .border(ShadowBorder.newBuilder().buildSpecial(new Insets(0,0,5,0)))
                    .preferredSize(new Dimension(0, Constants.UISize.RecordHeight)).build()
            );
        }
        @Override
        public void newMessage(String tableId) {
            if(!targetTableId.equals(tableId)){
                JButton table = findTable(tableId);
                if(table != null){
                    table.setBackground(Color.green);
                }
            } else {
                loadSessionWithTargetTable();
            }
        }
        @Override
        public void offline(String tableId) {
            JButton table = findTable(tableId);
            if(table != null){
                remove(table);
                Utility.revalidate(this);
            }
        }
        @Override
        public void online(String tableId) {
            synchronized (this) {
                if(findTable(tableId) == null) {
                    addOnlineTable(tableId);
                    Utility.revalidate(this);
                }
            }
        }
        private JButton findTable(String tableId){
            Component[] components = getComponents();
            for (Component id : components) {
                if (id instanceof JButton){
                    JButton b = (JButton)id;
                    if(b.getText().equals(tableId)) {
                        return b;
                    }
                }
            }
            return null;
        }
    }


    public void start(){
        cf.getService().endAllSesion();
        targetTableId = "";
        loadSessionWithTargetTable();
    }
    public void stop(){

    }
}
