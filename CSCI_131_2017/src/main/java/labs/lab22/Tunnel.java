package labs.lab22;

public class Tunnel extends Thread {
    private static Tunnel instance;

    private Tunnel() {}
    
    public static Tunnel getInstance() {
        return (instance == null) ? (instance = new Tunnel()) : instance;
    }

    public synchronized void enter(int trainNum, int duration) {
        System.out.println("Train " + trainNum + " of " + (Train.isAllTrainsDeployed() ? Train.getTrainCounter() : "an unknown number") + " entering tunnel.");
        try {
            sleep(duration);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        leave(trainNum);
    }

    private void leave(int trainNum) {
        System.out.println("Train " + trainNum + " leaving tunnel.");
    }
}