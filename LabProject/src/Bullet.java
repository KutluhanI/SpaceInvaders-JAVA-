import java.awt.*;

public class Bullet extends GameObject{
    private int ySpeed;
    private int xSpeed;

    public Bullet (int x, int y, int ySpeed, int xSpeed) {
        this.x=x;
        this.y=y;
        this.ySpeed=ySpeed;
        this.xSpeed=xSpeed;
    }

    public void ymovement() {
        this.y += ySpeed;
    }
    public void xmovement() {
        this.x += xSpeed;
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, 2, 10);
    }

    public int getySpeed() {
        return ySpeed;
    }

    public int getxSpeed () {
        return xSpeed;
    }
}
