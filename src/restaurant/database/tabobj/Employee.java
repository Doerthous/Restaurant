package restaurant.database.tabobj;

import java.util.Date;

public class Employee {
    // 员工id
    private String id;
    // 姓名
    private String name;
    // 年龄
    private Integer age;
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

    public Employee(String id, String name, Integer age, String sex, String nativePlace, String position, Integer salary, Date hiredate, String contactWay, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.nativePlace = nativePlace;
        this.position = position;
        this.salary = salary;
        this.hiredate = hiredate;
        this.contactWay = contactWay;
        this.address = address;
    }

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", hiredate=" + hiredate +
                ", contactWay='" + contactWay + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
