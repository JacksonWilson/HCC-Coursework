package labs.lab15;

public class Train extends Thread {
    private Tunnel tunnel;
    private long timeInTunnel;
    private long returnTimeToTunnel;
    private int trainNumber;

    public Train(long timeInTunnel, long returnTimeToTunnel) {
        this.timeInTunnel = timeInTunnel;
        this.returnTimeToTunnel = returnTimeToTunnel;
    }

    @Override
    public void run() {
        
    }
}