package labs.lab22;

public class Train extends Thread {
    private static Tunnel tunnel = Tunnel.getInstance();
    private static int trainCounter = 0;
    public static boolean allTrainsDeployed = false;
    private int timeInTunnel;
    private int returnTimeToTunnel;
    private int trainID;

    public Train(int timeInTunnel, int returnTimeToTunnel) {
        this.timeInTunnel = timeInTunnel;
        this.returnTimeToTunnel = returnTimeToTunnel;
        this.trainID = ++trainCounter;
        System.out.println("*** Instantiating train #" + this.trainID + " ***");
    }

    public static boolean isAllTrainsDeployed() {
        return allTrainsDeployed;
    }

    public static void setAllTrainsDeployed(boolean b) {
        allTrainsDeployed = b;
    }

    public static int getTrainCounter() {
        return trainCounter;
    }

    @Override
    public void run() {
        while (true) {
            tunnel.enter(trainID, timeInTunnel);
            try {
                sleep(returnTimeToTunnel);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}