package restaurant.database.po;

import java.util.Date;

/**
 * Created by Dromatack on 2017/12/27.
 */
public class Order {
    //订单id
    private String id;
    //日期时间
    private Date date;
    //桌号
    private String seatId;
    //消费金额
    private Float expend;

    public Order(String id, Date date, String seatId, Float expend) {
        this.id = id;
        this.date = date;
        this.seatId = seatId;
        this.expend = expend;
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public Float getExpend() {
        return expend;
    }

    public void setExpend(Float expend) {
        this.expend = expend;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", seatId='" + seatId + '\'' +
                ", expend=" + expend +
                '}';
    }
}
