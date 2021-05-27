import java.awt.*;

public class StrongAlien extends GameObject{
    public static int xSpeed = 10;
    public int hp = 2;
    private static final int height = 100;
    private static final int widht = 100;

    public StrongAlien (int x, int y) {
        this.x = x;
        this.y = y;
        this.dead = false;
    }

    public boolean checkDir() {
        if(x<20) {
            xSpeed=10;
            return true;
        }
        else if(x>810) {
            xSpeed=-10;
            return true;
        }
        return false;
    }
    public int getHp() {
        return hp;
    }

    public void getDamage() {
        this.hp -=1 ;
        if (hp == 0) {
            dead = true;
        }
    }
    public int getxSpeed() {
        return xSpeed;
    }

    public int getHeight() {
        return height;
    }

    public int getWidht() {
        return widht;
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(VisualUtil.alien.getWidth(),VisualUtil.alien.getHeight());
    }
}
