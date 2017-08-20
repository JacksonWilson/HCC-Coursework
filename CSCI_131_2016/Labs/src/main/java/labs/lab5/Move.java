package labs.lab5;

import java.util.Objects;

public class Move {
    private int startPegNum;
    private int destPegNum;

    public Move(int startPegNum, int destPegNum) {
        this.startPegNum = startPegNum;
        this.destPegNum = destPegNum;
    }

    @Override
    public String toString() {
        return startPegNum + "->" + destPegNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Move) {
            Move other = (Move)obj;
            return other.startPegNum == this.startPegNum && other.destPegNum == this.destPegNum;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPegNum, destPegNum);
    }
}