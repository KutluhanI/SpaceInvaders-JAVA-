public class Bullet extends GameObject{
    private int ySpeed;
    private int xSpeed;

    public Bullet (int x, int y, int ySpeed) {
        this.x=x;
        this.y=y;
        this.ySpeed=ySpeed;
    }

    public void ymovement() {
        this.y += ySpeed;
    }
    public void xmovement() {
        this.x += xSpeed;
    }
}
