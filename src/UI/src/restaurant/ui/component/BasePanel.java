package restaurant.ui.component;

import restaurant.ui.ColorConstants;
import restaurant.ui.component.thirdpart.ShadowBorder;

import javax.swing.*;
import java.awt.*;

public class BasePanel extends JPanel {
    private JPanel titles;
    private JPanel title;
    private JPanel subtitleBackground;
    private JPanel subtitle;
    private JPanel content;
    private JPanel foot;

    public BasePanel() {
        setLayout(new BorderLayout());
        titles = new JPanel(new BorderLayout());
        titles.setBackground(ColorConstants.subtitle);
        title = new JPanel(new BorderLayout());
        title.setPreferredSize(new Dimension(0, 60));
        title.setBorder(ShadowBorder.newBuilder().shadowSize(5).buildSpecial(new Insets(0,0,10,0)));
        title.setBackground(ColorConstants.title);
        subtitleBackground = new JPanel(new BorderLayout());
        subtitle = new JPanel(new BorderLayout());
        subtitle.setPreferredSize(new Dimension(0,60));
        subtitle.setBorder(ShadowBorder.newBuilder().shadowSize(5).buildSpecial(new Insets(0,0,10,0)));
        subtitleBackground.setBackground(ColorConstants.background);
        subtitleBackground.add(subtitle);
        subtitle.setBackground(ColorConstants.subtitle);
        titles.add("North",title);
        titles.add("South",subtitleBackground);
        content = new JPanel(new BorderLayout());
        content.setBackground(ColorConstants.background);
        foot = new JPanel(new BorderLayout());
        foot.setBackground(ColorConstants.title);
        foot.setPreferredSize(new Dimension(0,40));
        foot.setBorder(ShadowBorder.newBuilder().shadowSize(5).buildSpecial(new Insets(5,0,0,0)));
        add("North", titles);
        add("Center", content);
        add("South", foot);
        setBackground(ColorConstants.background);
    }

    public void hideFoot(){
        foot.setVisible(false);
    }
    public void hideSubTitle(){
        titles.setBackground(ColorConstants.background);
        subtitleBackground.setVisible(false);
    }

    public JPanel getTitle() {
        return title;
    }
    public JPanel getSubtitle() {
        return subtitle;
    }
    public JPanel getContent() {
        return content;
    }
    public JPanel getFoot() {
        return foot;
    }
}
