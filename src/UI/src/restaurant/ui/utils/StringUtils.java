package restaurant.ui.utils;

import java.util.regex.Pattern;

public class StringUtils {
    public static boolean isNumber(String str) {
        //采用正则表达式的方式来判断一个字符串是否为数字，这种方式判断面比较全
        //可以判断正负、整数小数

        return isInteger(str) || isDouble(str);
    }
    public static boolean isInteger(String str){
        return Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
    }
    public static boolean isDouble(String str){
        return Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$").matcher(str).find();
    }
    public static boolean isDigits(String str){
        return Pattern.compile("^\\d?$").matcher(str).find();
    }
}
