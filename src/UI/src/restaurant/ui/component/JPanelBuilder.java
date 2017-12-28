package restaurant.ui.component;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class JPanelBuilder {
    private JPanel panel;
    private JPanelBuilder(){
        panel = new JPanel();
    }
    public static JPanelBuilder getInstance(){
        return new JPanelBuilder();
    }
    public JPanelBuilder layout(LayoutManager layout){
        panel.setLayout(layout);
        return this;
    }
    public JPanelBuilder background(Color background){
        panel.setBackground(background);
        return this;
    }
    public JPanelBuilder border(Border border){
        panel.setBorder(border);
        return this;
    }
    public JPanelBuilder opaque(boolean opaque){
        panel.setOpaque(opaque);
        return this;
    }
    public JPanelBuilder preferredSize(Dimension preferredSize){
        panel.setPreferredSize(preferredSize);
        return this;
    }
    public JPanelBuilder add(Component component){
        panel.add(component);
        return this;
    }
    public JPanelBuilder add(Component component, int index){
        panel.add(component, index);
        return this;
    }
    public JPanelBuilder add(String name, Component component){
        panel.add(name, component);
        return this;
    }
    public JPanelBuilder add(Component component, String name){
        panel.add(name, component);
        return this;
    }
    public JPanel build(){
        return panel;
    }
}
