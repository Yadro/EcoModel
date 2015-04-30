package sample.models;

public class Wolf extends Character {

    public int half = 10;

    /**
     * Character is live.
     * @return yes/no
     */
    public boolean isLive() {
        return half > 0;
    }

    /**
     * Increase the number of life.
     */
    public void eat() {
        half += 10;
    }

    /**
     * Reduce the number of life.
     */
    public void hungry() {
        half -= 2;
    }
}
