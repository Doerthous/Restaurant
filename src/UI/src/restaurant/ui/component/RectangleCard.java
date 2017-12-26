package restaurant.ui.component;

import restaurant.ui.Constants;
import restaurant.ui.component.thirdpart.ShadowBorder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RectangleCard extends JPanel {
    private Border moveIn;
    private Border moveOut;
    MouseAdapter ma;
    public RectangleCard() {
        setPreferredSize(new Dimension(0, restaurant.ui.Constants.UISize.RecordHeight));

        setBackground(Constants.Color.title);
        setLayout(new BorderLayout());
        moveOut = BorderFactory.createCompoundBorder(
                ShadowBorder.newBuilder().buildSpecial(new Insets(0,0,5,0)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20));
        moveIn = BorderFactory.createCompoundBorder(
                ShadowBorder.newBuilder().buildSpecial(new Insets(5,0,0,0)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setBorder(moveOut);

        ma = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(moveIn);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(moveOut);
            }
        };


    }

    private boolean enableMouseListener = false;
    public void setEnableMouseListenr(boolean enableMouseListener){
        if(!this.enableMouseListener && enableMouseListener){
            addMouseListener(ma);
            for(Component component: getComponents()){
                component.addMouseListener(ma);
            }
        } else if(this.enableMouseListener && !enableMouseListener){
            removeMouseListener(ma);
            for(Component component: getComponents()){
                component.addMouseListener(ma);
            }
        }
    }
}
