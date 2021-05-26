import java.util.ArrayList;

public class UtilityClass {
    public static final int startingWidth = 900;
    public static final int startingHeight = 600;

    public static Human human;
    public static Alien aliens[];
    //public static MapGenerator map;
    public static ArrayList<Bullet> Bullets;
    public static Barricade barricades[];
    //public static StrongAlien strongAlien;

    public static final int alienRows = 5;
    public static final int alienColumns = 11;

    public static int[] highscores = VisualUtil.readHighscores();
}
