package models;

public class CreateOrderResponse {
    private String name;
    private Boolean success;
    private OrderCreated order;

    public CreateOrderResponse(String name, Boolean success, OrderCreated order) {
        this.name = name;
        this.success = success;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public OrderCreated getOrder() {
        return order;
    }

    public void setOrder(OrderCreated order) {
        this.order = order;
    }
}
