import java.awt.*;

public class Bullet extends GameObject{
    private int ySpeed;
    private double xSpeed;
    private double Angle;

    public Bullet (int x, int y, int ySpeed, double xSpeed, double Angle) {
        this.x=x;
        this.y=y;
        this.ySpeed=ySpeed;
        this.xSpeed=xSpeed;
        this.Angle=Angle;
    }

    public double getAngle() {
        return Angle;
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, 2, 10);
    }

    public int getySpeed() {
        return ySpeed;
    }

    public double getxSpeed () {
        return xSpeed;
    }
}
