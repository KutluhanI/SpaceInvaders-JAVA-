import java.awt.*;

public class Alien extends GameObject{
    public static int xSpeed = 5;
    private static final int height = 100;
    private static final int widht = 100;

    public Alien() {
        this.dead = true;
    }

    public Alien (int x, int y) {
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

    public int getxSpeed() {
        return xSpeed;
    }

    public void changexSpeed() {
        xSpeed = -xSpeed;
    }

    public int getWidth() {
        return widht;
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, VisualUtil.alien.getWidth(), VisualUtil.alien.getHeight());
    }
}
