package restaurant.ui.res;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Resource {
    public static ImageIcon getIcon(String path){
        URL url = Resource.class.getClassLoader().getResource("restaurant/ui/res/"+path);
        return new ImageIcon(url);
    }
}
