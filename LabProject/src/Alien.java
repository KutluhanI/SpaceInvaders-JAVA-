import java.awt.*;

public class Alien extends GameObject{
    public int xSpeed = 8;
    private static final int widht = 100;
    private boolean rightDirection = true;

    public Alien() {
        this.dead = true;
    }

    public Alien (int x, int y, boolean rightDirection) {
        this.x = x;
        this.y = y;
        this.dead = false;
        this.rightDirection = rightDirection;
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

    public boolean getrightDirection(){return rightDirection;}

    public int getxSpeed() {
        return xSpeed;
    }

    public void changexSpeed() {
        this.xSpeed = -xSpeed;
    }

    public int getWidth() {
        return widht;
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, VisualUtil.alien.getWidth(), VisualUtil.alien.getHeight());
    }
}
