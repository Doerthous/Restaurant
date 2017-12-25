package restaurant.database.po;

/**
 * Created by Dromatack on 2017/12/23.
 */
public class Seat {
    //餐桌id
    private String id;
    //类型
    private String type;
    //楼层
    private Integer floor;
    //容量
    private Integer capacity;
    //状态
    private String status;
    //当月使用次数
    private Integer usedtimes;

    public Seat(String id, String type, Integer floor, Integer capacity, String status, Integer usedtimes){
        this.id = id;
        this.type = type;
        this.floor = floor;
        this.capacity = capacity;
        this.status = status;
        this.usedtimes = usedtimes;
    }

    public Seat(){

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUsedtimes() {
        return usedtimes;
    }

    public void setUsedtimes(Integer usedtimes) {
        this.usedtimes = usedtimes;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", floor=" + floor +
                ", capacity=" + capacity +
                ", status='" + status + '\'' +
                ", usedtimes=" + usedtimes +
                '}';
    }
}
