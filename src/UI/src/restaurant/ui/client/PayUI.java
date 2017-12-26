package restaurant.ui.client;

import restaurant.ui.component.thirdpart.VFlowLayout;
import restaurant.ui.component.BasePanel;

import javax.swing.*;
import java.awt.*;

public class PayUI extends BasePanel {
    private ClientFrame cf;
    public PayUI(ClientFrame cf) {
        this.cf = cf;
        initUIComponent();
    }
    private void initUIComponent() {
        // subtitle
        hideSubTitle();

        // content
        getContent().setLayout(new VFlowLayout());
        JPanel sm = new TitlePanel("扫码支付", Constants.Color.subtitle);
        sm.add(new PictureLabel(Constants.QRSize,Constants.QRSize, "res/qrtest.jpg"));
        sm.add(new PictureLabel(Constants.QRSize,Constants.QRSize, "res/qrtest.jpg"));
        getContent().add(sm);
        JPanel xj = new TitlePanel("现金支付", Constants.Color.subtitle);
        xj.add(new JLabel("呼叫服务员收款或到前台付款"));
        getContent().add(xj);

        // foot
        JPanel p = new JPanel(new BorderLayout());
        p.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        p.setOpaque(false);
        JButton confirm = new JButton("呼叫服务");
        confirm.setBackground(Constants.Color.title);
        confirm.setPreferredSize(new Dimension(Constants.ContentEastWidth/2, 0));
        confirm.addActionListener(e -> { });
        p.add("West", confirm);
        JButton ret = new JButton("返回");
        ret.setPreferredSize(new Dimension(Constants.ContentEastWidth/2, 0));
        ret.setBackground(Constants.Color.title);
        ret.addActionListener(e -> {
            cf.main();
        });
        p.add("East", ret);
        getFoot().add("East", p);
        getFoot().add("Center", new JLabel("小票请到前台领取"));
    }

    class TitlePanel extends JPanel{
        public TitlePanel(String title, Color color) {
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(color), title));
            setOpaque(false);
            setLayout(new FlowLayout(FlowLayout.CENTER,80,0));
        }
        public TitlePanel(String title, Color color, int jusification, int position){
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(color),
                    title, jusification, position));
            setOpaque(false);
        }
    }
    class PictureLabel extends JPanel{
        private String path;
        public PictureLabel(int width, int height, String path) {
            this.path = path;
            setPreferredSize(new Dimension(width, height));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon ii = new ImageIcon(path);
            g.drawImage(ii.getImage(), 0, 0, getWidth(), getHeight(), ii.getImageObserver());
        }
    }
}
