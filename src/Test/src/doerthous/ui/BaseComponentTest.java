package doerthous.ui;

import restaurant.ui.Constants;
import restaurant.ui.component.*;

import javax.swing.*;
import java.awt.*;

public class BaseComponentTest {

    static class BasePanelTest{
        public static void main(String[] args) {
            BaseFrame f = new BaseFrame();
            BasePanel p = new BasePanel();
            f.add(p);
            f.open();
        }
    }
    static class BasePanel2Test {
        public static void main(String[] args) {
            BaseFrame f = new BaseFrame();
            BasePanel2 p = new BasePanel2();
            p.addFootRight(JButtonBuilder.getInstance().text("add")
                    .listener(e->{
                        p.setRightWidth(300);
                        p.addFootRight(JButtonBuilder.getInstance().text("inc")
                                .listener(e2->{
                                    p.setRightWidth(400);
                                    p.addFootRight(JButtonBuilder.getInstance().text("inc")
                                            .listener(e3->{
                                                p.setRightWidth(500);
                                                p.addFootRight(JButtonBuilder.getInstance().text("inc")
                                                        .build());
                                            }).build());
                                }).build());
                    }).build());
            f.add(p);
            f.open();
        }
    }
    static class BasePanel3Test {
        public static void main(String[] args) {
            BaseFrame f = new BaseFrame();
            BasePanel3 p = new BasePanel3();
            p.getContentRightTop().setBackground(Color.green);
            p.getContentRightBottom().add(new JButton("add"));
            f.add(p);
            f.open();
        }
    }


}
