package assignments.a4;

public class WorkOrder {
    private String orderNumber;
    private String jobCode;
    private String componentCode;

    public WorkOrder(String orderNumber, String jobCode, String componentCode) {
        this.orderNumber = orderNumber;
        this.jobCode = jobCode;
        this.componentCode = componentCode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getJobCode() {
        return jobCode;
    }

    public String getComponentCode() {
        return componentCode;
    }
}