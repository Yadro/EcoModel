package sample.models;

public class Pers {

    public static final int WOLF = 1;
    public static final int WOLFW = 2;
    public static final int RABBIT = 3;

    public int half = 10;
    private final int how;
    public boolean pregnant = false;
    public boolean checked = false;

    public Pers(int how) {
        this.how = how;
    }

    public boolean isLive() {
        return half > 0;
    }

    public void eat() {
        half += 10;
        check();
    }

    public void hungry() {
        half -= 2;
        check();
    }

    public void setHalf(int half) {
        this.half = half;
    }

    public int howIs() {
        return how;
    }

    public void check() {
        checked = true;
    }
}
