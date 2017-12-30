package restaurant.ui.component;

import restaurant.ui.Constants;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.component.builder.JPanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TipDialog extends JDialog implements ActionListener {
    private boolean isDispose = false;
    private int delay;
    public TipDialog(Dimension size, String tip, int delay){
        this.delay = delay;

        setSize(size);
        getContentPane().setBackground(Constants.Color.subtitle);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);

        JPanel panel = JPanelBuilder.getInstance().opaque(false)
                .layout(new BorderLayout())
                .border(BorderFactory.createEmptyBorder(0,20,10,20))
                .build();

        panel.add(JButtonBuilder.getInstance().background(Constants.Color.background)
                .text("确定").focusPainted(false).listener(e->{
                    isDispose = true;
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
    public TipDialog(String tip, int delay) {
        this(new Dimension(240,180), tip, delay);
    }
    public void open(){
        new Timer(delay, this).start();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!isDispose) {
            setVisible(false);
            dispose();
        }
    }
}
