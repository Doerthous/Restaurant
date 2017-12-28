package restaurant.ui.component;

import restaurant.ui.Constants;
import restaurant.ui.component.border.AdvLineBorder;

import javax.swing.*;
import java.awt.*;

public class BasePanel3 extends BasePanel2 {
    private JPanel crt;
    private JPanel crb;
    public BasePanel3(){
        this(100);
    }
    public BasePanel3(int bottomHeight){
        crt = JPanelBuilder.getInstance().layout(new BorderLayout()).opaque(false).build();
        crb = JPanelBuilder.getInstance().layout(new BorderLayout()).opaque(false).build();

        setBottomHeight(bottomHeight);

        getContentRight().add(crt, BorderLayout.CENTER);
        getContentRight().add(crb, BorderLayout.SOUTH);
        getContentRight().setBorder(new AdvLineBorder().setLeftColor(Constants.Color.subtitle).setLeft(1));
    }
    public BasePanel3(int rightWidth, int topHeight){
        super(rightWidth);
    }
    // you shouldn't use this method
    public JPanel getContentRightTop(){ return crt; }
    public JPanel getContentRightBottom() { return crb; }
    public void setBottomHeight(int bottomHeight){
        setVisible(false);
        crb.setPreferredSize(new Dimension(0,bottomHeight));
        setVisible(true);
    }
}
