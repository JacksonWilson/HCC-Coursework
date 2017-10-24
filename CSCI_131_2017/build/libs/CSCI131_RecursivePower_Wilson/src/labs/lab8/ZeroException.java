package labs.lab8;

public class ZeroException extends Exception {
    
    public ZeroException() {
        super("Base and power are both zero. Resetting to default values.");
    }
}