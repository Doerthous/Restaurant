package restaurant.database.po;

import java.io.*;
import java.nio.channels.FileLockInterruptionException;

public class Dish {
    // 菜品id
    private String id;
    // 菜名
    private String name;
    // 单价
    private Float price;
    // 品类
    private String type;
    // 是否售卖
    private Boolean isSaled;
    //图片
    private byte[] picture;
    // 一月销售量
    private Integer saledCount1;
    // 二月销售量
    private Integer saledCount2;
    // 三月销售量
    private Integer saledCount3;
    // 四月销售量
    private Integer saledCount4;
    // 五月销售量
    private Integer saledCount5;
    // 六月销售量
    private Integer saledCount6;
    // 七月销售量
    private Integer saledCount7;
    // 八月销售量
    private Integer saledCount8;
    // 九月销售量
    private Integer saledCount9;
    // 十月销售量
    private Integer saledCount10;
    // 十一月销售量
    private Integer saledCount11;
    // 十二月销售量
    private Integer saledCount12;

    public Dish() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSaled() {
        return isSaled;
    }

    public void setSaled(Boolean saled) {
        isSaled = saled;
    }

    public Integer getSaledCount1() {
        return saledCount1;
    }

    public void setSaledCount1(Integer saledCount1) {
        this.saledCount1 = saledCount1;
    }

    public Integer getSaledCount2() {
        return saledCount2;
    }

    public void setSaledCount2(Integer saledCount2) {
        this.saledCount2 = saledCount2;
    }

    public Integer getSaledCount3() {
        return saledCount3;
    }

    public void setSaledCount3(Integer saledCount3) {
        this.saledCount3 = saledCount3;
    }

    public Integer getSaledCount4() {
        return saledCount4;
    }

    public void setSaledCount4(Integer saledCount4) {
        this.saledCount4 = saledCount4;
    }

    public Integer getSaledCount5() {
        return saledCount5;
    }

    public void setSaledCount5(Integer saledCount5) {
        this.saledCount5 = saledCount5;
    }

    public Integer getSaledCount6() {
        return saledCount6;
    }

    public void setSaledCount6(Integer saledCount6) {
        this.saledCount6 = saledCount6;
    }

    public Integer getSaledCount7() {
        return saledCount7;
    }

    public void setSaledCount7(Integer saledCount7) {
        this.saledCount7 = saledCount7;
    }

    public Integer getSaledCount8() {
        return saledCount8;
    }

    public void setSaledCount8(Integer saledCount8) {
        this.saledCount8 = saledCount8;
    }

    public Integer getSaledCount9() {
        return saledCount9;
    }

    public void setSaledCount9(Integer saledCount9) {
        this.saledCount9 = saledCount9;
    }

    public Integer getSaledCount10() {
        return saledCount10;
    }

    public void setSaledCount10(Integer saledCount10) {
        this.saledCount10 = saledCount10;
    }

    public Integer getSaledCount11() {
        return saledCount11;
    }

    public void setSaledCount11(Integer saledCount11) {
        this.saledCount11 = saledCount11;
    }

    public Integer getSaledCount12() {
        return saledCount12;
    }

    public void setSaledCount12(Integer saledCount12) {
        this.saledCount12 = saledCount12;
    }

    public InputStream getPicture() {
        if(picture != null) {
            return new ByteArrayInputStream(picture);
        }
        return null;
    }

    public void setPicture(InputStream picture) {
        if(picture != null) {
            try {
                this.picture = new byte[1024];
                int len = picture.available();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while(len != 0) {
                    int count = picture.read(this.picture);
                    baos.write(this.picture);
                    for(int i = 0; i < count; ++i); // delay
                    len = picture.available();
                }
                this.picture = baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", isSaled=" + isSaled +
                ", picture=" + picture +
                ", saledCount1=" + saledCount1 +
                ", saledCount2=" + saledCount2 +
                ", saledCount3=" + saledCount3 +
                ", saledCount4=" + saledCount4 +
                ", saledCount5=" + saledCount5 +
                ", saledCount6=" + saledCount6 +
                ", saledCount7=" + saledCount7 +
                ", saledCount8=" + saledCount8 +
                ", saledCount9=" + saledCount9 +
                ", saledCount10=" + saledCount10 +
                ", saledCount11=" + saledCount11 +
                ", saledCount12=" + saledCount12 +
                '}';
    }
}
