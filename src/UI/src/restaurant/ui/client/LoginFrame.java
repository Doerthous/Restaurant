package restaurant.ui.client;

import restaurant.ui.Constants;
import restaurant.ui.component.JButtonBuilder;
import restaurant.ui.component.JLabelBuilder;
import restaurant.ui.component.JPanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JDialog {
    private String input;
    private JLabel tip;
    public LoginFrame(ActionListener listener) throws HeadlessException {
        setModal(true);
        setUndecorated(true);
        setSize(new Dimension(200,150));
        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        JTextField jtf = new JTextField();
        tip = JLabelBuilder.getInstance().build();
        JPanel jPanel = JPanelBuilder.getInstance().layout(new BorderLayout(5,0))
                .background(restaurant.ui.Constants.Color.background)
                .border(BorderFactory.createEmptyBorder(30,20,10,20))
                .add(JPanelBuilder.getInstance().layout(new BorderLayout()).opaque(false)
                        .add(
                                JLabelBuilder.getInstance().text("餐桌号：")
                                        .build(),
                                BorderLayout.WEST)
                        .add(jtf, BorderLayout.CENTER)
                        .build(), BorderLayout.NORTH)
                .add(JPanelBuilder.getInstance().layout(new BorderLayout())
                        .add(
                                JButtonBuilder.getInstance().text("确定")
                                        .listener(e->{
                                            input = jtf.getText();
                                            listener.actionPerformed(
                                                    new ActionEvent(this,1,""));
                                        })
                                        .background(Constants.Color.title)
                                        .build()
                        )
                        .build(), BorderLayout.SOUTH)
                .add(tip, BorderLayout.CENTER)
                .build();
        add(jPanel);
    }
    public String getText(){
        return input;
    }
    public void setText(String text){
        tip.setText(text);
    }

}
