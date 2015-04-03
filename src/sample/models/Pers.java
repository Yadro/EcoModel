package sample.models;

public class Pers {

    public static final int WOLF = 1;
    public static final int WOLFW = 2;
    public static final int RABBIT = 3;

    public double half = 1;
    private final int how;
    public boolean checked = false;

    public Pers(int how) {
        this.how = how;
    }

    public boolean isLive() {
        return half > 0;
    }

    public void eat() {
        half += 1;
        check();
    }

    public void hungry() {
        half -= 0.2;
        check();
    }

    public void setHalf(double half) {
        this.half = half;
    }

    public int howIs() {
        return how;
    }

    public void check() {
        checked = true;
    }
}
