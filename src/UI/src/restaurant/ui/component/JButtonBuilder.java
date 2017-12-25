package restaurant.ui.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class JButtonBuilder {
    private String text = "";
    private ActionListener listener = null;
    private Color background = null;
    private Color foreground = null;
    private Font font = null;
    private Boolean opaque = null;
    private Boolean focusPainted = null;
    private Boolean contentAreaFilled = null;
    private String actionCommand = null;
    private Dimension preferredSize = null;
    private JButtonBuilder(){}
    public JButtonBuilder text(String text){
        this.text = text;
        return this;
    }
    public JButtonBuilder listener(ActionListener listener){
        this.listener = listener;
        return this;
    }
    public JButtonBuilder background(Color background){
        this.background = background;
        return this;
    }
    public JButtonBuilder foreground(Color foreground){
        this.foreground = foreground;
        return this;
    }
    public JButtonBuilder font(Font font){
        this.font = font;
        return this;
    }
    public JButtonBuilder opaque(Boolean opaque){
        this.opaque = opaque;
        return this;
    }
    public JButtonBuilder focusPainted(Boolean focusPainted){
        this.focusPainted = focusPainted;
        return this;
    }
    public JButtonBuilder contentAreaFilled(Boolean contentAreaFilled){
        this.contentAreaFilled = contentAreaFilled;
        return this;
    }
    public JButtonBuilder actionCommand(String actionCommand){
        this.actionCommand = actionCommand;
        return this;
    }
    public JButtonBuilder preferredSize(Dimension preferredSize){
        this.preferredSize = preferredSize;
        return this;
    }
    public JButton build(){
        JButton button = new JButton(text);
        if(listener != null){
            button.addActionListener(listener);
        }
        if(foreground != null){
            button.setForeground(foreground);
        }
        if(background != null){
            button.setBackground(background);
        }
        if(font != null){
            button.setFont(font);
        }
        if(opaque != null){
            button.setOpaque(opaque);
        }
        if(focusPainted != null){
            button.setFocusPainted(focusPainted);
        }
        if(contentAreaFilled != null){
            button.setContentAreaFilled(contentAreaFilled);
        }
        if(actionCommand != null){
            button.setActionCommand(actionCommand);
        }
        if(preferredSize != null){
            button.setPreferredSize(preferredSize);
        }
        return button;
    }


    public static JButtonBuilder getInstance(){
        return new JButtonBuilder();
    }

}
