package restaurant.ui.component;

import restaurant.ui.Constants;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.component.builder.JPanelBuilder;

import javax.swing.*;
import java.awt.*;

public class ConfirmDialog extends JDialog {
    public static int YES_OPTION = 0;
    public static int NO_OPTION = 1;
    private int option;
    public ConfirmDialog(Dimension size, String tip){
        option = NO_OPTION;
        setModal(true);
        setSize(size);
        getContentPane().setBackground(Constants.Color.subtitle);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);

        JPanel panel = JPanelBuilder.getInstance().opaque(false)
                .layout(new GridLayout(1,2,10,0))
                    .border(BorderFactory.createEmptyBorder(0,20,10,20))
                .build();

        panel.add(JButtonBuilder.getInstance().background(Constants.Color.background)
                .text("确定").focusPainted(false).listener(e->{
                    option = YES_OPTION;
                    setVisible(false);
                    dispose();
                }).build());
        panel.add(JButtonBuilder.getInstance().background(Constants.Color.background)
                .text("取消") .focusPainted(false).listener(e->{
                    option = NO_OPTION;
                    setVisible(false);
                    dispose();
                }).build());

        add(JLabelBuilder.getInstance().text(tip).foreground(Color.white)
                .horizontalAlignment(JLabel.CENTER)
                .verticalAlignment(JLabel.CENTER)
                .border(BorderFactory.createEmptyBorder(0,20,0,20))
                .build(), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }
    public ConfirmDialog(String tip) {
        this(new Dimension(240,180), tip);
    }
    public int open(){
        setVisible(true);
        return option;
    }
}
