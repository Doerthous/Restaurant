package yms;

import doerthous.ui.AFrameForJPanelTest;
import restaurant.ui.component.JButtonBuilder;
import yms.component.DishCard;
import yms.component.EmployeeCard;
import yms.component.SetMeatCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by YMS on 2017/12/24.
 */
public class T {
    public static void main(String[] args) {
        AFrameForJPanelTest f = new AFrameForJPanelTest();
        f.setLayout(new FlowLayout());
        f.setBackground(Color.black);
        f.getContentPane().setBackground(Color.black);
        String s[] ={"1","2","3","4","5","6","7"};
        /*f.add(new EmployeeCard(600,200, "df",s,
                e -> {
                    System.out.println("改");
                }, new Listener()));
                */
        f.add(new SetMeatCard(400,400,"酱爆肉","20","url"));
        f.open();
    }
}

class Listener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("删");
    }
}


