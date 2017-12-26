package restaurant.ui.component;

import restaurant.ui.Constants;
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
        titles.setBackground(Constants.Color.subtitle);
        title = new JPanel(new BorderLayout());
        title.setPreferredSize(new Dimension(0, 60));
        title.setBorder(ShadowBorder.newBuilder().shadowSize(5).buildSpecial(new Insets(0,0,10,0)));
        title.setBackground(Constants.Color.title);
        subtitleBackground = new JPanel(new BorderLayout());
        subtitle = new JPanel(new BorderLayout());
        subtitle.setPreferredSize(new Dimension(0,60));
        subtitle.setBorder(
                BorderFactory.createCompoundBorder(
                        ShadowBorder.newBuilder().shadowSize(5).buildSpecial(new Insets(0,0,10,0)),
                        BorderFactory.createEmptyBorder(0,0,10,0)
                ));
        subtitleBackground.setBackground(Constants.Color.background);
        subtitleBackground.add(subtitle);
        subtitle.setBackground(Constants.Color.subtitle);
        titles.add("North",title);
        titles.add("South",subtitleBackground);
        content = new JPanel(new BorderLayout());
        content.setBackground(Constants.Color.background);
        foot = new JPanel(new BorderLayout());
        foot.setBackground(Constants.Color.title);
        foot.setPreferredSize(new Dimension(0,40));
        foot.setBorder(ShadowBorder.newBuilder().shadowSize(5).buildSpecial(new Insets(5,0,0,0)));
        add("North", titles);
        add("Center", content);
        add("South", foot);
        setBackground(Constants.Color.background);
    }

    public void hideFoot(){
        foot.setVisible(false);
    }
    public void hideSubTitle(){
        titles.setBackground(Constants.Color.background);
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
