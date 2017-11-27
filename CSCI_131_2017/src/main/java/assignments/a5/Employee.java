package assignments.a5;

public class Employee {
    private long id;
    private String lastName;
    private String firstName;
    private int partTimeYoS;
    private int fullTimeYoS;
    private String state;
    private long primaryCustId;
    private long secondaryCustId;

    public Employee(long id, String lastName, String firstName, int partTimeYoS, int fullTimeYoS, String state, long primaryCustId, long secondaryCustId) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.partTimeYoS = partTimeYoS;
        this.fullTimeYoS = fullTimeYoS;
        this.state = state;
        this.primaryCustId = primaryCustId;
        this.secondaryCustId = secondaryCustId;
    }
}