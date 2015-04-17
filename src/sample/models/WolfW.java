package sample.models;

public class WolfW extends Wolf {
    public boolean pregnant = false;

    public boolean isPregnant() {
        return pregnant;
    }

    public void pregnant() {
        pregnant = true;

    }
}
