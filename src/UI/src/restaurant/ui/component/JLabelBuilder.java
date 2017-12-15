package restaurant.ui.component;

import javax.swing.*;
import java.awt.*;

public class JLabelBuilder {
    private String text = null;
    private Integer horizontalAlignment = null;
    private Font font = null;
    private Color foreground = null;
    private JLabelBuilder(){}
    public JLabelBuilder text(String text){
        this.text = text;
        return this;
    }
    public JLabelBuilder horizontalAlignment(int horizontalAlignment){
        this.horizontalAlignment = horizontalAlignment;
        return this;
    }
    public JLabelBuilder font(Font font){
        this.font = font;
        return this;
    }
    public JLabelBuilder foreground(Color foreground){
        this.foreground = foreground;
        return this;
    }
    public JLabel build(){
        JLabel label = new JLabel();
        if(text != null){
            label.setText(text);
        }
        if(horizontalAlignment != null){
            label.setHorizontalAlignment(horizontalAlignment);
        }
        if(font != null){
            label.setFont(font);
        }
        if(foreground != null){
            label.setForeground(foreground);
        }
        return label;
    }
    public static JLabelBuilder getInstance(){
        return new JLabelBuilder();
    }
}
