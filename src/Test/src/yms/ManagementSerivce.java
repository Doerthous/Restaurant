package yms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YMS on 2017/12/27.
 */
public class ManagementSerivce {
    static public class DishInfo {
        public String name;
        public Float price;
        public String url;

        public DishInfo(String name, Float price, String url) {
            this.name = name;
            this.price = price;
            this.url = url;
        }
    }
    public static List<DishInfo> getDishInfo(){
        List l = new ArrayList();
        int i =0;
        while(i<50) {
            l.add(new DishInfo("骚鸡", 20f, ""));
            i++;
        }
        return l;
    }
}
