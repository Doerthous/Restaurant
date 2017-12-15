package restaurant.ui.component;

import test.ui.component.SimpleFrame;

import javax.swing.*;
import java.awt.*;


public class BaseFrame extends SimpleFrame {
    private CardLayout card;
    public BaseFrame() throws HeadlessException {
        super();
        card = new CardLayout();
        getContentPane().setLayout(card);
    }

    public void add(JPanel ui, String name){
        getContentPane().add(name, ui);
    }
    public void show(String name){
        card.show(getContentPane(), name);
    }

    public void open(){
        setVisible(true);
    }

    public static void main(String[] args) {
        new BaseFrame().open();
    }
}
