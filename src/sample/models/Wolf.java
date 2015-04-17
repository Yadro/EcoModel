package sample.models;

public class Wolf extends Character {

    public int half = 10;

    public boolean isLive() {
        return half > 0;
    }

    public void eat() {
        half += 10;
    }

    public void hungry() {
        half -= 2;
    }
}
