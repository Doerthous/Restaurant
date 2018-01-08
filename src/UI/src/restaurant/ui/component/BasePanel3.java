package restaurant.ui.component;

import restaurant.ui.Constants;
import restaurant.ui.component.border.AdvLineBorder;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.component.builder.JPanelBuilder;
import restaurant.ui.component.layout.PageLayout;
import restaurant.ui.component.thirdpart.ShadowBorder;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BasePanel3 extends BasePanel2 {
    private JPanel crt;
    private JPanel crb;
    public BasePanel3(){
        this(120);
    }
    public BasePanel3(int bottomHeight){
        sl.setLayout(new PageLayout().setSingleRow(true)
                .setHgap(0).setVgap(10).setPadding(new Insets(2,15,0,0)));

        crt = JPanelBuilder.getInstance().layout(new BorderLayout()).opaque(false).build();
        crb = JPanelBuilder.getInstance().layout(new BorderLayout()).opaque(false).build();

        setBottomHeight(bottomHeight);

        getContentRight().add(crt, BorderLayout.CENTER);
        getContentRight().add(crb, BorderLayout.SOUTH);
        getContentRight().setBorder(new AdvLineBorder().setLeftColor(Constants.Color.subtitle).setLeft(1));
        getContentRightBottom().setBorder(new AdvLineBorder().setTopColor(Constants.Color.subtitle).setTop(1));
    }
    // you shouldn't use this method
    public JPanel getContentRightTop(){ return crt; }
    public JPanel getContentRightBottom() { return crb; }
    public void setBottomHeight(int bottomHeight){
        setVisible(false);
        crb.setPreferredSize(new Dimension(0,bottomHeight));
        setVisible(true);
    }
    public void addSubtitleLeftButton(String text, ActionListener listener){
        if(!(sl.getLayout() instanceof PageLayout)){
            sl.setLayout(new PageLayout().setSingleRow(true)
                    .setHgap(0).setVgap(10).setPadding(new Insets(2,15,0,0)));
        }
        Border moveIn = BorderFactory.createCompoundBorder(
                ShadowBorder.newBuilder().buildSpecial(new Insets(2,0,0,0)),
                BorderFactory.createEmptyBorder(15,15,15,15));
        Border moveOut = BorderFactory.createCompoundBorder(
                ShadowBorder.newBuilder().buildSpecial(new Insets(0,0,2,0)),
                BorderFactory.createEmptyBorder(15,15,15,15));

        JButton button = JButtonBuilder.getInstance().text(text)
                .listener(listener).background(Constants.Color.background)
                .border(moveOut).build();

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                button.setBorder(moveIn);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                button.setBorder(moveOut);
            }
        });

        getSubtitleLeft().add(button);
    }
    public void setSubtitleLeft(String title){
        getSubtitleLeft().removeAll();
        if(!(sl.getLayout() instanceof BorderLayout)){
            sl.setLayout(new BorderLayout());
        }
        getSubtitleLeft().add(
                JLabelBuilder.getInstance().text(title).horizontalAlignment(JLabel.CENTER)
                        .foreground(Color.white).font(restaurant.ui.client.Constants.Font.font1).build()
        );
        Utility.revalidate(getSubtitleLeft());
    }
}
