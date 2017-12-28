package restaurant.service.core.vo;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

public class Dish {
    private String name;
    private Float price;
    private ImageIcon picture; // 引入ui层会不会提高耦合？
    private String type;

    public Dish(String name, Float price, InputStream picture, String type) {
        this.name = name;
        this.price = price;
        this.picture = null;
        if(picture != null) {
            try {
                int len = picture.available();
                byte[] data = new byte[len];
                for (int rl = 0;
                     (rl = picture.read(data, rl, len)) != -1;
                     len -= rl);
                this.picture = new ImageIcon(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public ImageIcon getPicture() {
        return picture;
    }

    public String getType() {
        return type;
    }
}
