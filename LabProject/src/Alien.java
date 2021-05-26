public class Alien extends GameObject{
    public static int xSpeed = 10;
    private static int frame = 0;

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

    public static void changeFrame() {
        if(frame == 0)
            frame = 1;
        else if(frame == 1)
            frame = 0;
    }
    public static int getFrame() {
        return frame;
    }
}
