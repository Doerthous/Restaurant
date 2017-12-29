package restaurant.ui.component;

import restaurant.ui.component.builder.JPanelBuilder;

import javax.swing.*;
import java.awt.*;

public class BasePanel2 extends BasePanel {
    private JPanel sl;
    private JPanel sr;
    private JPanel cl;
    private JPanel cr;
    private JPanel fl;
    private JPanel fr;

    public BasePanel2(int rightWidth) {
        sl = JPanelBuilder.getInstance().opaque(false).layout(new BorderLayout()).build();
        sr = JPanelBuilder.getInstance().opaque(false).layout(new BorderLayout()).build();
        cl = JPanelBuilder.getInstance().opaque(false).layout(new BorderLayout()).build();
        cr = JPanelBuilder.getInstance().opaque(false).layout(new BorderLayout()).build();
        fl = JPanelBuilder.getInstance().opaque(false).layout(new BorderLayout()).build();
        fr = JPanelBuilder.getInstance().opaque(false).build();

        setRightWidth(rightWidth);

        // subtitle
        getSubtitle().setLayout(new BorderLayout());
        getSubtitle().add("East", sr);
        getSubtitle().add("Center", sl);

        // content
        getContent().setLayout(new BorderLayout());
        getContent().add("East", cr);
        getContent().add("Center", cl);

        // foot
        getFoot().setLayout(new BorderLayout());
        getFoot().add("East", fr);
        getFoot().add("Center", fl);
    }
    public BasePanel2(){
        this(200);
    }
    public JPanel getSubtitleLeft(){
        return sl;
    }
    public JPanel getSubtitleRight(){
        return sr;
    }
    public JPanel getContentLeft(){
        return cl;
    }
    public JPanel getContentRight(){
        return cr;
    }
    public JPanel getFootLeft(){
        return fl;
    }
    public void addFootRight(Component component){
        fr.setVisible(false);
        fr.setLayout(new GridLayout(1, fr.getComponentCount()+1));
        fr.add(component);
        fr.setVisible(true);
    }

    public void setRightWidth(int rightWidth){
        setVisible(false);
        // subtitle
        sr.setPreferredSize(new Dimension(rightWidth, 0));
        // content
        cr.setPreferredSize(new Dimension(rightWidth, 0));
        // foot
        fr.setPreferredSize(new Dimension(rightWidth, 0));
        setVisible(true);
    }
}
