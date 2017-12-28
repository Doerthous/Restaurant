package doerthous.ui;


import restaurant.ui.client.LoginFrame;
import restaurant.ui.component.JButtonBuilder;
import restaurant.ui.component.JComboBoxEx;
import restaurant.ui.component.JLabelBuilder;
import restaurant.ui.component.RectangleCard;
import restaurant.ui.management.component.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main {
    static class LoginFrameTest {
        public static void main(String[] args) {
            new LoginFrame(e -> {
                if(e.getSource() instanceof LoginFrame) {
                    LoginFrame lp = (LoginFrame)e.getSource();
                    System.out.println(lp.getText());
                    if(!lp.getText().equals("123")){
                        lp.setText("桌号不存在");
                    } else {
                        lp.dispose();
                    }
                }
            }).setVisible(true);
        }
    }

    static class ManagementOnlineTableTest{
        public static void main(String[] args) {
            AFrameForJPanelTest f = new AFrameForJPanelTest();
            f.getContentPane().setLayout(new FlowLayout());
            f.add(new OnlineTable("00").addActionListener(e-> System.out.println("00")));
            f.open();
        }
    }

    static class ManagementFreeTableInfoTest {
        public static void main(String[] args) {
            AFrameForJPanelTest f = new AFrameForJPanelTest();
            f.getContentPane().setLayout(new FlowLayout());
            FreeTableInfo fti = new FreeTableInfo("f", e->{
                System.out.println(((FreeTableInfo)e.getSource()).getCustomerCount());
            });
            fti.setPreferredSize(new Dimension(200,200));
            f.add(fti);
            f.open();
        }

    }

    static class ManagementBusyTableInfoTest {
        public static void main(String[] args) {
            AFrameForJPanelTest f = new AFrameForJPanelTest();
            f.getContentPane().setLayout(new FlowLayout());
            BusyTableInfo fti = new BusyTableInfo("f", 0,0f, e->{
                System.out.println(((BusyTableInfo)e.getSource()).getTableId());
            });
            fti.setPreferredSize(new Dimension(200,200));
            f.add(fti);
            f.open();
        }

    }

    static class ManagementDishCardTest {
        public static void main(String[] args) {
            AFrameForJPanelTest f = new AFrameForJPanelTest();
            f.getContentPane().setLayout(new FlowLayout());
            DishCard dc = new DishCard("f",1f, null);
            dc.setActionListener(e->{
                if(e.getActionCommand().equals(DishCard.MODIFY)){
                    System.out.println("m "+dc.getDishName());
                } else {
                    System.out.println("d "+dc.getDishName());
                }
            });
            f.add(dc);
            f.open();
        }
    }

    static class JComboxExTest {
        public static void main(String[] args) {
            AFrameForJPanelTest f = new AFrameForJPanelTest();
            f.getContentPane().setLayout(new FlowLayout());
            JComboBoxEx jcbe = new JComboBoxEx("tip", new String[]{"w","q"});
            jcbe.addActionListener(e->{
                if(e.getActionCommand().equals(JComboBoxEx.ITEM_SELETED)){
                    System.out.println(jcbe.getSelectedItem());
                }
            });
            f.add(jcbe);
            f.open();
        }
    }

    static class DishFormTest {
        public static void main(String[] args) {
            AFrameForJPanelTest f = new AFrameForJPanelTest();
            f.getContentPane().add(JButtonBuilder.getInstance().text("新增菜品").listener(e->{
                DishForm d = new DishForm(new String[]{"特色菜","荤菜"});
                d.setVisible(true);
                DishForm.Data da = d.getData();
                System.out.println(da.toString());
            }).build());
            f.open();
        }
    }
}
