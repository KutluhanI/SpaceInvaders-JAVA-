import java.awt.*;

public class StrongAlien extends GameObject{
    public static int xSpeed = 10;
    public int hp = 2;
    private static final int height = 100;
    private static final int width = 100;

    public StrongAlien(){
        this.dead = true;
    }

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

    public void changexSpeed() {
        xSpeed = -xSpeed;
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

    public int getWidth() {
        return width;
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, VisualUtil.sAlien.getWidth(),VisualUtil.sAlien.getHeight());
    }
}
