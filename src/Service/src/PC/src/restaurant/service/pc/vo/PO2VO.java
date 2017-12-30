package restaurant.service.pc.vo;

import restaurant.service.core.vo.Dish;
import restaurant.service.core.vo.Employee;
import restaurant.service.core.vo.Table;

import java.io.*;
import java.util.Date;

/*
    放到core会增加core与关系库的依赖

    数据库中的null与ui的null在此处处理
 */
public class PO2VO {
    private static final String NULL_IMAGE = "no image";

    // po 转 vo
    public static Employee employee(restaurant.database.po.Employee e){
        return employee(e, 0);
    }
    public static Employee employee(restaurant.database.po.Employee e, int delay){
        byte[] pic = streamToByteArray(e.getPhoto(), delay);
        if(pic.length < 32){
            if(NULL_IMAGE.equals(new String(pic))) {
                pic = new byte[0];
            }
        }
        return new Employee(e.getName(), e.getId(), e.getPosition(),
                e.getSex(), pic, e.getContactWay(),
                e.getNativePlace(), e.getPassword(), e.getSalary());
    }
    // po 转 vo
    public static Dish dish(restaurant.database.po.Dish d){
        return dish(d, 0);
    }
    public static Dish dish(restaurant.database.po.Dish d, int delay){
        byte[] pic = streamToByteArray(d.getPicture(), delay);
        if(pic.length < 32){
            if(pic.length == 0 || NULL_IMAGE.equals(new String(pic))) {
                pic = new byte[0];
            }
        }
        return new Dish(d.getName(), d.getPrice(), pic, d.getType(), d.getSaled());
    }
    //
    public static Table table(restaurant.database.po.Seat s){
        return new Table(s.getId(), s.getType(), s.getFloor(), s.getCapacity());
    }

    private static restaurant.database.po.Dish newDishPo(
            String id, String name, Float price, String type, Boolean isSaled, String pictureUrl,
            Integer saledCount1, Integer saledCount2, Integer saledCount3,
            Integer saledCount4, Integer saledCount5, Integer saledCount6,
            Integer saledCount7, Integer saledCount8, Integer saledCount9,
            Integer saledCount10, Integer saledCount11, Integer saledCount12){
        restaurant.database.po.Dish dp = new restaurant.database.po.Dish();
        dp.setId(id);
        dp.setName(name);
        dp.setPrice(price);
        dp.setType(type);
        dp.setSaled(isSaled);
        dp.setPicture(getFileInputStream(pictureUrl));
        dp.setSaledCount1(saledCount1);
        dp.setSaledCount2(saledCount2);
        dp.setSaledCount3(saledCount3);
        dp.setSaledCount4(saledCount4);
        dp.setSaledCount5(saledCount5);
        dp.setSaledCount6(saledCount6);
        dp.setSaledCount7(saledCount7);
        dp.setSaledCount8(saledCount8);
        dp.setSaledCount9(saledCount9);
        dp.setSaledCount10(saledCount10);
        dp.setSaledCount11(saledCount11);
        dp.setSaledCount12(saledCount12);
        return dp;
    }

    public static restaurant.database.po.Dish newDishPo(
            String id, String name, Float price, String type, Boolean isSaled,
            String pictureUrl) {
        restaurant.database.po.Dish dp = new restaurant.database.po.Dish();
        return newDishPo(id, name, price, type, isSaled, pictureUrl,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0);
    }

    public static restaurant.database.po.Dish newDishPo(String name){
        return newDishPo(name, name, 0f, null, null, null);
    }

    public static restaurant.database.po.Dish setDishPo(
            String id, String name, Float price, String type, Boolean isSaled,
            String pictureUrl, restaurant.database.po.Dish d) {
        if(d != null) {
            // d.setName(name);
            d.setPrice(price);
            d.setType(type);
            d.setSaled(isSaled);
            d.setPicture(getFileInputStream(pictureUrl));
        }
        return d;
    }

    private static restaurant.database.po.Employee newEmployeePo(
            String id, String name, Date birthday, String sex, String nativePlace,
            String position, Integer salary, Date hiredate, String contactWay,
            String address, String password, String photoUrl) {
        restaurant.database.po.Employee employee = new restaurant.database.po.Employee();
        employee.setId(id);
        employee.setName(name);
        employee.setBirthday(birthday);
        employee.setSex(sex);
        employee.setNativePlace(nativePlace);
        employee.setPosition(position);
        employee.setSalary(salary);
        employee.setHiredate(hiredate);
        employee.setContactWay(contactWay);
        employee.setAddress(address);
        employee.setPassword(password);
        employee.setPhoto(getFileInputStream(photoUrl));
        return employee;
    }

    public static restaurant.database.po.Employee newEmployeePo(String id){
        return newEmployeePo(id, null, null, null, null,
                null, null, null, null);
    }

    public static restaurant.database.po.Employee newEmployeePo(
            String id, String name, String photoUrl, String position, Integer salary,
            String password, String sex, String nativePlace, String contactWay) {
        return newEmployeePo(id, name, new Date(), sex, nativePlace, position,
                salary, new Date(), contactWay, "", password, photoUrl);
    }

    public static restaurant.database.po.Employee setEmployeePo(
            String id, String name, String position, Integer salary, String sex,
            String password, String phone, String nativePlace, String photoUrl,
            restaurant.database.po.Employee e) {
        // e.setId(id);
        e.setName(name);
        e.setPosition(position);
        e.setSalary(salary);
        e.setSex(sex);
        e.setPassword(password);
        e.setContactWay(phone);
        e.setNativePlace(nativePlace);
        e.setPhoto(getFileInputStream(photoUrl));
        return e;
    }

    public static restaurant.database.po.Seat newSeatPo(String id, String type, Integer floor,
                                                        Integer capacity){
        restaurant.database.po.Seat sp = new restaurant.database.po.Seat();
        sp.setId(id);
        sp.setType(type);
        sp.setFloor(floor);
        sp.setCapacity(capacity);
        sp.setStatus("");
        sp.setUsedtimes(0);
        return sp;
    }
    public static restaurant.database.po.Seat newSeatPo(String id){
        return newSeatPo(id, null, null, null);
    }
    public static restaurant.database.po.Seat setSeatPo(String id, String type, Integer floor,
                                                        Integer capacity, restaurant.database.po.Seat s){
        // s.setId(id);
        s.setType(type);
        s.setFloor(floor);
        s.setCapacity(capacity);
        return s;
    }

    // utils
    private static byte[] streamToByteArray(InputStream stream, int delay){
        byte[] data = new byte[0];
        if(stream != null) {
            try {
                int len = stream.available();
                data = new byte[len];
                for (int rl = 0;
                     (rl = stream.read(data, rl, len)) != -1;
                     len -= rl);
                for(int i = 0; i < delay; ++i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
    private static InputStream getFileInputStream(String url){
        InputStream is;
        if(url != null) {
            try {
                is = new FileInputStream(new File(url));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                is = new ByteArrayInputStream(NULL_IMAGE.getBytes());
            }
        } else {
            is = new ByteArrayInputStream(NULL_IMAGE.getBytes());
        }
        return is;
    }
}
