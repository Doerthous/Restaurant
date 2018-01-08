package restaurant.ui.component;

import restaurant.ui.Constants;
import restaurant.ui.component.anima.Notification;
import restaurant.ui.utils.Utility;

import javax.swing.*;

public class BasePanel4 extends BasePanel3 {
    public BasePanel4() {
    }
    public void setNotification(String notification){
        JPanel sr = getSubtitleRight();
        sr.removeAll();
        sr.setBackground(Constants.Color.subtitle);
        sr.add(new Notification(notification, 1500));
        Utility.revalidate(sr);
    }
}
