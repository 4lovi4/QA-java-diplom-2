package models;

import java.util.List;

public class OrderList {
    private Boolean success;
    private List<OrderRequested> orders;
    private Long total;
    private Long totalToday;

    public OrderList(Boolean success, List<OrderRequested> orders, Long total, Long totalToday) {
        this.success = success;
        this.orders = orders;
        this.total = total;
        this.totalToday = totalToday;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<OrderRequested> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderRequested> orders) {
        this.orders = orders;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(Long totalToday) {
        this.totalToday = totalToday;
    }
}
