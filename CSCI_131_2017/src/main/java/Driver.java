import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ArrayList<labs.lab10.SoldItem> newItems = new ArrayList<>();
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("solditemswithnull.dat"))) {
            SoldItem item;

            while ((item = (SoldItem)ois.readObject()) != null) {
                Field privateSalePrice = SoldItem.class.getDeclaredField("salePrice");
                privateSalePrice.setAccessible(true);
                double salePrice = privateSalePrice.getDouble(item);

                Field privateDealerCost = SoldItem.class.getDeclaredField("dealerCost");
                privateDealerCost.setAccessible(true);
                double dealerCost = privateDealerCost.getDouble(item);

                newItems.add(new labs.lab10.SoldItem(item.getItemType(), salePrice, dealerCost, item.getAgentLastName()));
            }

        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("solditemswithnull_fix.dat"))) {
            for (labs.lab10.SoldItem item : newItems) {
                oos.writeObject(item);
            }
            oos.writeObject(null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}