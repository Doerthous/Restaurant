package restaurant.ui.utils;

import java.awt.*;

public class Utility {
    public static void revalidate(Component comp){
        comp.setVisible(false);
        comp.revalidate();
        comp.setVisible(true);
    }
}
