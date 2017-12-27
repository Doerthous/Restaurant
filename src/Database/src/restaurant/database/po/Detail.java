package restaurant.database.po;

/**
 * Created by Dromatack on 2017/12/27.
 */
public class Detail {
    //订单id
    private String orderId;
    //菜品id
    private String dishId;
    //菜品数量
    private Integer amount;

    public Detail(String orderId, String dishId, Integer amount) {
        this.orderId = orderId;
        this.dishId = dishId;
        this.amount = amount;
    }

    public Detail() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "orderId='" + orderId + '\'' +
                ", dishId='" + dishId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
