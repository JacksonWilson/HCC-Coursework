import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) {
        ArrayList<labs.lab10.SoldItem> newItems = new ArrayList<>();
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("solditems.ser"))) {
            SoldItem item;

            while ((item = (SoldItem)ois.readObject()) != null) {
                labs.lab10.SoldItem newItem;
            }

        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("solditemsnew.ser"))) {
            for (labs.lab10.SoldItem item : newItems) {
                oos.writeObject(item);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}