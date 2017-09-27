import java.io.Serializable;

public class SoldItem implements Serializable {
    private String itemType;
    private double salePrice;
    private double dealerCost;
    private String agentLastName;

    public SoldItem () {
        itemType = "Unknown Vehicle";
        salePrice = 0.0;
        dealerCost = 0.0;
        agentLastName = "Unknown Agent";
    }

    public SoldItem (String it, double sp, double dc, String aln) {
        if (it.equals("Truck") || it.equals("Car")) {
            itemType = it;
        }
        else {
            itemType = "Unknown Vehicle";
        }
        
        salePrice = sp;
        dealerCost = dc;
        agentLastName = aln;
    }
    
    public String getItemType () {
        return itemType;
    }

    public String getAgentLastName () {
        return agentLastName;
    }

    public double getProfit () {
        return salePrice - dealerCost;
    }
}