package assignments.a4;

/**
 * A work order consists of a order number, job code, and component code.
 * 
 * @author Jackson Wilson
 */
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