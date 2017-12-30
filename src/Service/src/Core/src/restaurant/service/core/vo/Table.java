package restaurant.service.core.vo;

public class Table {
    //餐桌id
    private String id;
    //类型
    private String type;
    //楼层
    private Integer floor;
    //容量
    private Integer capacity;

    public Table() {
    }

    public Table(String id, String type, Integer floor, Integer capacity) {
        this.id = id;
        this.type = type;
        this.floor = floor;
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
