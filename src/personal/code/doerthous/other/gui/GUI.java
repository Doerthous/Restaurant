package personal.code.doerthous.other.gui;

import personal.code.doerthous.ui.AFrameForJPanelTest;
import programmer.test.ui.component.ScrollablePanel;
import programmer.test.ui.component.VFlowLayout;
import restaurant.communication.*;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;

interface IChatData {
    //
    String getTime();
    String getTableId(); //
    String getMessage();
}
class ChatDataImpl implements IChatData{
    private String tableId;
    private String time;
    private String message;

    public ChatDataImpl(String tableId, String time, String message) {
        this.tableId = tableId;
        this.time = time;
        this.message = message;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public String getTableId() {
        return tableId;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
class Service {
    public static java.util.List<IChatData> getSessionById(String id, IPeer peer){
        java.util.List<IData> pdata = peer.getSessionById(id);
        java.util.List l = new ArrayList();
        for(IData pd: pdata) {
            Byte[] B = pd.getData();
            byte[] b = new byte[B.length];
            for(int j = 0; j < b.length; ++j){
                b[j] = B[j];
            }
            l.add(new ChatDataImpl(pd.getFromId(),
                    new SimpleDateFormat("HH:mm").format(pd.getDate()),
                    new String(b)));
        }
        return l;
    }
}
class ChatBoxPanel extends JPanel implements ICommandObserver {
    private String self;
    private String other;
    private IPeer peer;

    private JPanel uiOthersPanel;
    private java.util.List<JButton> uiOtherBtns;
    private JButton uiClearBtn;
    private JButton uiSendBtn;
    private ScrollablePanel uiChatRecordsPanel;
    private JTextArea uiInputArea;

    /*
        监听新消息，更改界面，以提示用户有消息
     */
    @Override
    public void update(IData data) { // 更改按钮颜色或添加新消息
        if(data.getCommand().equals(CMD_NEW_MESSAGE)){
            for (JButton jb : uiOtherBtns) {
                if (jb.getText().equals(data.getFromId())) {
                    if (!other.equals(data.getFromId())) {
                        jb.setBackground(Color.green);
                        break;
                    } else {
                        loadRecordWith(data.getFromId());
                    }
                }
            }
        }
    }

    
    public ChatBoxPanel(String self, IPeer peer) {
        this.self = self;
        this.other = "";
        this.uiOtherBtns = new ArrayList<>();
        this.peer = peer;
        this.peer.addCommandObserver(this, CMD_NEW_MESSAGE);
        initUIComponent();
    }
    private void initUIComponent(){
        setLayout(new BorderLayout());
        uiOthersPanel = new JPanel(new BorderLayout());
        uiOthersPanel.add("North", new JLabel("在线人"));
        JPanel uiOtherCenter = new JPanel(new VFlowLayout());
        for(String other: peer.getAllPeersId()){
            JButton uiOhterCenterBtn = new JButton(other);
            uiOhterCenterBtn.setBackground(Color.gray);
            uiOhterCenterBtn.addActionListener(e->{
                uiOhterCenterBtn.setBackground(Color.gray);
                loadRecordWith(uiOhterCenterBtn.getText());
            });
            uiOtherCenter.add(uiOhterCenterBtn);
            uiOtherBtns.add(uiOhterCenterBtn);
        }
        uiOthersPanel.add("Center", uiOtherCenter);
        add("West", uiOthersPanel);
        JPanel uiChatBox = new JPanel(new BorderLayout());
        uiChatRecordsPanel = new ScrollablePanel(new VFlowLayout(VFlowLayout.TOP,0,0, true, false));
        JScrollPane csp = new JScrollPane(uiChatRecordsPanel);
        csp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        uiChatBox.add("Center", csp);
        JPanel cia = new JPanel(new BorderLayout());
        JPanel ciaBp = new JPanel(new BorderLayout());
        uiClearBtn = new JButton("清空");
        uiClearBtn.addActionListener(e->{
            uiInputArea.setText("");
        });
        ciaBp.add("North",uiClearBtn);
        uiSendBtn = new JButton("发送");
        uiSendBtn.addActionListener(e->{
            uiSendBtnMessage(uiInputArea.getText());
            uiInputArea.setText("");
            uiChatRecordsPanel.revalidate();
        });
        ciaBp.add("South",uiSendBtn);
        cia.add("East",ciaBp);
        uiInputArea = new JTextArea();
        uiInputArea.setLineWrap(true);
        uiInputArea.setWrapStyleWord(true);
        cia.add("Center",uiInputArea);
        uiChatBox.add("South", cia);
        add("Center", uiChatBox);
    }

    /*
        加载与某个id的聊天会话记录，调用Service接口
     */
    private void loadRecordWith(String tableId){
        other = tableId;
        uiChatRecordsPanel.removeAll();
        java.util.List<IChatData> icds = Service.getSessionById(tableId, peer);
        if(icds.size() == 0){
            uiChatRecordsPanel.add(new JLabel("暂无消息"));
        } else {
            for (IChatData icd : icds) {
                addRecord(icd);
            }
        }
        revalidate();
    }
    
    /*
        添加一条聊天记录到界面上
     */
    private void addRecord(IChatData record){
        int i = JLabel.LEFT;
        if(record.getTableId().equals(self)){
            i = JLabel.RIGHT;
        }
        uiChatRecordsPanel.add(new CRC(i, record.getTableId(),record.getTime(), record.getMessage()));
    }
    
    /*
        调用IPeer接口发送消息
     */
    public void uiSendBtnMessage(String message){
        if(!"".equals(other)) {
            peer.sendMessage(other, message);
            if(!other.equals(self)) { // 过滤掉属于自己的消息（此处与uiSendBtnMessage重复）
                loadRecordWith(other);
            }
        }
    }


    /*
        一条聊天记录的JPanel
        结构：
            JPanel (BorderLayout)
                JLabel (BorderLayout.North)                
                JTextPane (BorderLayout.Center)              
     */
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
}
public class GUI extends AFrameForJPanelTest implements ICommands, ICommandObserver {
    ChatBoxPanel cbp;
    IPeer peer;
    boolean isInited = false;

    @Override
    public void open() {
        peer = PeerFactory.newPeer(PeerFactory.T2);
        peer.addCommandObserver(this, CMD_EXIT);
        peer.addCommandObserver(this, CMD_INIT_FINISHED);
        peer.listen();
        while(!isInited){
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cbp = new ChatBoxPanel(peer.getId(), peer);
        add(cbp);
        super.open();
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.open();
    }

    @Override
    public void update(IData data) {
        switch (data.getCommand()){
            case CMD_EXIT:{
                peer.stop();
                exit(1);
            } break;
            case CMD_INIT_FINISHED:{
                isInited = true;
            }
        }
    }
}
