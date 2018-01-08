package restaurant.ui.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Validate {
    // format: 20170101
    public static Boolean isDate1(String date){
        try {
            new SimpleDateFormat("yyyyMMdd").parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }







    public static Boolean digits(String digits){
        if(isNullOrEmpty(digits)){
            return false;
        }
        for(int i = 0; i < digits.length(); ++i){
            if(!isDigit(digits.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public static Boolean isDigit(String digit){
        if(isNullOrEmpty(digit)){
            return false;
        }
        if(digit.length() != 1){
            return false;
        }
        return isDigit(digit.charAt(0));
    }
    public static boolean isDigit(char isDigit){
        return isDigit >= '0' && isDigit <= '9';
    }
    public static Boolean isNull(String isNull){
        return isNull == null;
    }
    public static Boolean isEmpty(String isEmpty){
        return !isNull(isEmpty) && isEmpty.equals("");
    }
    public static Boolean isNullOrEmpty(String isNullOrEmpty){
        return isNullOrEmpty == null || isNullOrEmpty.equals("");
    }
}
