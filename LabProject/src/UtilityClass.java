import java.awt.*;
import java.util.ArrayList;

public class UtilityClass {
    public static final int startingWidth = 1100;
    public static final int startingHeight = 800;

    public static Human human;
    public static Alien[] aliens;
    public static ArrayList<Bullet> Bullets;
    public static Barricade[] barricades;
    public static StrongAlien[] sAliens;

    public static Rectangle frames = new Rectangle(-1000,0,5000,1);

    public static int[] highscores = VisualUtil.readHighscores();
}
