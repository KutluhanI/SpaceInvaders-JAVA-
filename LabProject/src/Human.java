
public class Human extends GameObject{

    public static int shootingDelay = 40;
    private boolean superShot;

    public Human () {
        x = 475;
        y = 680;
        superShot = false;
    }

    public void setSuperShot(boolean superShot) {
        this.superShot=superShot;
    }
    public boolean hasSuperShot() {
        return superShot;
    }
}
