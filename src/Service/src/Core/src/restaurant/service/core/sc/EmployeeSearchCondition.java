package restaurant.service.core.sc;

public class EmployeeSearchCondition {
    public static final String FILTER_ALL = "";
    private String name;
    private String sex;
    private String position;
    private String code;

    private EmployeeSearchCondition(String name, String sex, String position, String code) {
        this.name = name;
        this.sex = sex;
        this.position = position;
        this.code = code;
    }

    public static EmployeeSearchCondition getInstance(){
        return new EmployeeSearchCondition(FILTER_ALL,FILTER_ALL,FILTER_ALL,FILTER_ALL);
    }
    public EmployeeSearchCondition name(String name){
        this.name = name;
        return this;
    }
    public EmployeeSearchCondition sex(String sex) {
        this.sex = sex;
        return this;
    }
    public EmployeeSearchCondition position(String position){
        this.position = position;
        return this;
    }
    public EmployeeSearchCondition code(String code){
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getPosition() {
        return position;
    }

    public String getCode() {
        return code;
    }
}
