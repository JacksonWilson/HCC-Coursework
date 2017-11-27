package assignments.a5;

public class Customer {
    private Long id;
    private String companyName;
    private Integer revenueAmount;
    private Integer numYears;

    public Customer() {
        this(null, null, null, null);
    }

    public Customer(Long id) {
        this(id, null, null, null);
    }
    
    public Customer(Long id, String companyName, Integer revenueAmount, Integer numYears) {
        this.id = id;
        this.companyName = companyName;
        this.revenueAmount = revenueAmount;
        this.numYears = numYears;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setRevenueAmount(int revenueAmount) {
        this.revenueAmount = revenueAmount;
    }

    public void setNumYears(int numYears) {
        this.numYears = numYears;
    }
}