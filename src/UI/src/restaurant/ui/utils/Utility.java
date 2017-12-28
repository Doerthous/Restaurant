package restaurant.ui.utils;

import java.awt.*;
import java.util.regex.Pattern;

public class Utility {
    public static void revalidate(Component comp){
        comp.setVisible(false);
        comp.revalidate();
        comp.setVisible(true);
    }
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[1-9][0-9]*");
        return pattern.matcher(str).matches();
    }
}
