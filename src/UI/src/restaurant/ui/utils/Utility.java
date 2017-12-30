package restaurant.ui.utils;

import restaurant.ui.component.ConfirmDialog;
import restaurant.ui.component.TipDialog;

import java.awt.*;
import java.util.regex.Pattern;

public class Utility {
    public static void showTipDialog(String tip, int delay){
        new TipDialog(tip, delay).open();
    }
    public static int showConfirmDialog(String tip){
        return new ConfirmDialog(tip).open();
    }
    public static void revalidate(Component comp){
        comp.setVisible(false);
        comp.revalidate();
        comp.setVisible(true);
    }
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[1-9][0-9]*");
        return pattern.matcher(str).matches();
    }
    public static boolean isFloat(String str){
        Pattern pattern = Pattern.compile("[1-9][0-9]*");
        return pattern.matcher(str).matches();
    }
}
