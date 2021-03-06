package restaurant.database.po;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class Employee {
    // 员工id
    private String id;
    // 姓名
    private String name;
    // 出生日期
    private Date birthday;
    // 性别
    private String sex;
    // 籍贯
    private String nativePlace;
    // 职位
    private String position;
    // 薪资
    private Integer salary;
    // 入职时间
    private Date hiredate;
    // 联系方式
    private String contactWay;
    // 住址
    private String address;
    //密码
    private String password;
    //照片
    private byte[] photo;

    public Employee() {
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public InputStream getPhoto() {
        if(photo != null) {
            return new ByteArrayInputStream(photo);
        }
        return null;
    }

    public void setPhoto(InputStream photo) {
        if(photo != null) {
            try {
                this.photo = new byte[1024];
                int len = photo.available();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while(len != 0) {
                    photo.read(this.photo);
                    baos.write(this.photo);
                    len = photo.available();
                }
                this.photo = baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", hiredate=" + hiredate +
                ", contactWay='" + contactWay + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", photo=" + photo +
                '}';
    }
}
