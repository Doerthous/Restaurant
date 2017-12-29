package restaurant.service.core.vo;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

public class Dish {
    private String name;
    private Float price;
    private byte[]  picture; // 引入ui层会不会提高耦合？
    private String type;
    private Boolean isSaled;

    public Dish(String name, Float price, byte[]  picture, String type, Boolean isSaled) {
        this.name = name;
        this.price = price;
        this.picture = null;
        this.picture = picture;
        this.type = type;
        this.isSaled = isSaled;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public byte[]  getPicture() {
        return picture;
    }

    public String getType() {
        return type;
    }

    public Boolean getIsSaled() {
        return isSaled;
    }
}
