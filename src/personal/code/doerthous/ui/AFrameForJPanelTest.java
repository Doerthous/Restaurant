package personal.code.doerthous.ui;

import personal.code.doerthous.service.IChatObserver;
import personal.code.doerthous.service.ChatServiceTest;
import personal.code.doerthous.service.IChatData;
import programmer.test.ui.component.*;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class AFrameForJPanelTest extends JFrame{
    public AFrameForJPanelTest() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
    }
    public void open(){
        setVisible(true);
    }
}

class ChatBoxPanel extends JPanel {
    private String self;
    private String other;
    private JButton clear;
    private JButton send;
    private ScrollablePanel records;
    private JTextArea input;
    private class CRC extends JPanel {
        public CRC(int JLabelLeftOrRight, String name, String time, String message){
            setLayout(new BorderLayout());
            add("North", new JLabel(name + "   " + time + "", JLabelLeftOrRight));
            JTextArea ta = new JTextArea(message);
            ta.setLineWrap(true);        //激活自动换行功能
            ta.setWrapStyleWord(true);            // 激活断行不断字功能
            ta.setEditable(false);
            add("Center", ta);
        }
    }
    public ChatBoxPanel(String self, String other, java.util.List<IChatData> records) {
        initUIComponent();
        this.self = self;
        this.other = other;
        for(IChatData icd: records){
            addRecord(icd);
        }
    }
    private void initUIComponent(){
        setLayout(new BorderLayout());
        this.records = new ScrollablePanel(new VFlowLayout(VFlowLayout.TOP,0,0, true, false));
        JScrollPane csp = new JScrollPane(this.records);
        csp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add("Center", csp);
        JPanel cia = new JPanel(new BorderLayout());
        JPanel ciaBp = new JPanel(new BorderLayout());
        clear = new JButton("清空");
        clear.addActionListener(e->{
            input.setText("");
        });
        ciaBp.add("North",clear);
        send = new JButton("发送");
        send.addActionListener(e->{
            sendMessage(input.getText());
            input.setText("");
            records.revalidate();
        });
        ciaBp.add("South",send);
        cia.add("East",ciaBp);
        input = new JTextArea();
        input.setLineWrap(true);
        input.setWrapStyleWord(true);
        cia.add("Center",input);
        add("South", cia);
    }

    private void addRecord(IChatData record){
        int i = JLabel.LEFT;
        if(record.getTableId().equals(self)){
            i = JLabel.RIGHT;
        }
        records.add(new CRC(i, record.getTableId(),record.getTime(), record.getMessage()));
    }
    public void sendMessage(String message){
        records.add(new CRC(JLabel.RIGHT, self,"16:55",message));
    }

    public static void main(String[] args) {
        AFrameForJPanelTest f = new AFrameForJPanelTest();
        ChatBoxPanel acb = new ChatBoxPanel("2T","1T",new ChatServiceTest().getChatRecordWith("1T"));
        f.add(acb);
        f.open();
    }
}

class ChatBoxPanel2 extends JPanel  implements IChatObserver {
    private String self;
    private String currTarget;
    private ChatServiceTest cst;

    private JPanel uiOthers;
    private JButton clear;
    private JButton send;
    private java.util.List<JButton> others;
    private ScrollablePanel records;
    private JTextArea input;

    @Override
    public void newMessage(String id) {
        for(JButton jb:others){
            if(jb.getText().equals(id)){
                if(!currTarget.equals(id)) {
                    jb.setBackground(Color.green);
                    break;
                } else {
                    loadRecordWith(id);
                }
            }
        }
    }

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
    public ChatBoxPanel2(String self, ChatServiceTest cst) {
        this.self = self;
        this.currTarget = "";
        this.others = new ArrayList<>();
        this.cst = cst;
        initUIComponent();
    }
    private void initUIComponent(){
        setLayout(new BorderLayout());
        uiOthers = new JPanel(new BorderLayout());
        uiOthers.add("North", new JLabel("在线人"));
        JPanel uiOtherCenter = new JPanel(new VFlowLayout());
        for(String other: cst.getOhterTableIds()){
            JButton uiOhterCenterBtn = new JButton(other);
            uiOhterCenterBtn.setBackground(Color.gray);
            uiOhterCenterBtn.addActionListener(e->{
                uiOhterCenterBtn.setBackground(Color.gray);
                loadRecordWith(uiOhterCenterBtn.getText());
            });
            uiOtherCenter.add(uiOhterCenterBtn);
            others.add(uiOhterCenterBtn);
        }
        uiOthers.add("Center", uiOtherCenter);
        add("West", uiOthers);
        JPanel uiChatBox = new JPanel(new BorderLayout());
        records = new ScrollablePanel(new VFlowLayout(VFlowLayout.TOP,0,0, true, false));
        JScrollPane csp = new JScrollPane(records);
        csp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        uiChatBox.add("Center", csp);
        JPanel cia = new JPanel(new BorderLayout());
        JPanel ciaBp = new JPanel(new BorderLayout());
        clear = new JButton("清空");
        clear.addActionListener(e->{
            input.setText("");
        });
        ciaBp.add("North",clear);
        send = new JButton("发送");
        send.addActionListener(e->{
            sendMessage(input.getText());
            input.setText("");
            records.revalidate();
        });
        ciaBp.add("South",send);
        cia.add("East",ciaBp);
        input = new JTextArea();
        input.setLineWrap(true);
        input.setWrapStyleWord(true);
        cia.add("Center",input);
        uiChatBox.add("South", cia);
        add("Center", uiChatBox);
    }

    private void loadRecordWith(String tableId){
        currTarget = tableId;
        records.removeAll();
        java.util.List<IChatData> icds = cst.getChatRecordWith(tableId);
        if(icds.size() == 0){
            records.add(new JLabel("暂无消息"));
        } else {
            for (IChatData icd : icds) {
                addRecord(icd);
            }
        }
        revalidate();
    }
    private void addRecord(IChatData record){
        int i = JLabel.LEFT;
        if(record.getTableId().equals(self)){
            i = JLabel.RIGHT;
        }
        records.add(new ChatBoxPanel2.CRC(i, record.getTableId(),record.getTime(), record.getMessage()));
    }
    public void sendMessage(String message){
        if(!"".equals(currTarget)) {
            cst.sendMessage(self, currTarget, message);
            loadRecordWith(currTarget);
        }
    }

    private static class TestFrame extends AFrameForJPanelTest {
        ChatServiceTest cst;
        ChatBoxPanel2 cbp;
        public TestFrame(){
            cst = new ChatServiceTest();
            cbp = new ChatBoxPanel2("2T", cst);
            add(cbp);
            cst.addChatObserver(cbp);
            open();
        }
    }
    public static void main(String[] args) {
        new TestFrame().open();
    }
}
