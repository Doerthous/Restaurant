package test.ui.component;

import javax.swing.*;

public class SpringLayoutOperators {
    public static Spring add(Spring o1, Spring o2){
        return Spring.sum(o1,o2);
    }
    public static Spring sub(Spring o1, Spring o2){
        return Spring.sum(o1, Spring.minus(o2));
    }
    public static Spring add(int o1, int o2){
        return Spring.sum(Spring.constant(o1), Spring.constant(o2));
    }
    public static Spring sub(int o1, int o2){
        return sub(Spring.constant(o1), Spring.constant(o2));
    }
    public static Spring max(Spring o1, Spring o2){
        return Spring.max(o1,o2);
    }
    public static Spring min(Spring o1, Spring o2){
        if(Spring.max(o1,o2).equals(o1)){
            return o2;
        } else {
            return o1;
        }
    }
}
