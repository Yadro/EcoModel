package sample.models;

public class Pers {

    public static final int WOLF = 1;
    public static final int WOLFW = 2;
    public static final int RABBIT = 3;

    private double half = 1;
    private final int how;

    public Pers(int how) {
        this.how = how;
    }

    public double getHalf() {
        return half;
    }

    public void setHalf(double half) {
        this.half = half;
    }

    public int howIs() {
        return how;
    }
}
