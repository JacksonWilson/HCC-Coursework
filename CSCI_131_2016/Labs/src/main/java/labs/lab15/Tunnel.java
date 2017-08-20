package labs.lab15;

public class Tunnel {
    private static Tunnel instance;

    private Tunnel() {

    }

    public static Tunnel getInstance() {
        if (instance == null)
            instance = new Tunnel();
        return instance;
    }

    public void enter(int trainNumber, long timeInTunnel) {

    }

    public void leave(int trainNumber) {

    }
}