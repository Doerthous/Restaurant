package restaurant.service.core.vo;

import java.util.Map;

public class Order {
    private String orderId;
    private String tableId;
    private String date;
    private Map<String, Integer> detail;

    public String getOrderId() {
        return orderId;
    }

    public String getTableId() {
        return tableId;
    }

    public String getDate() {
        return date;
    }

    public Map<String, Integer> getDetail() {
        return detail;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDetail(Map<String, Integer> detail) {
        this.detail = detail;
    }

    public Order(String orderId, String tableId, String date, Map<String, Integer> detail) {


        this.orderId = orderId;
        this.tableId = tableId;
        this.date = date;
        this.detail = detail;
    }
}
