import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class VisualUtil {

    private static final Path path = FileSystems.getDefault().getPath("0").toAbsolutePath();
    private static BufferedReader reader;
    private static PrintWriter writer;

    public static final BufferedImage player = readImage("player.png");
    public static final BufferedImage alien = readImage("alien.png");
    public static final BufferedImage barricade1 = readImage("barricade1.png");
    public static final BufferedImage barricade2 = readImage("barricade2.png");
    public static final BufferedImage barricade3 = readImage("barricade3.png");
    public static final BufferedImage sAlien = readImage("sAlien.png");
    public static final Icon powerup = new ImageIcon(path + "powerup.png");
    public static final Image bullet = readImage("bullet.png");
    public static final Icon musicI = new ImageIcon(path + "music.png");
    public static final BufferedImage level1 = readImage("level1.png");
    public static final BufferedImage level2 = readImage("level2.png");
    public static final BufferedImage level3 = readImage("level3.png");

    private static BufferedImage readImage(String fileName) {
        System.out.println("[VisualUtil]: Loading "+fileName);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path + fileName));
        } catch (IOException e) {
            System.out.println("[VisualUtil]: Exception when loading" +  path + fileName );
        }
        return img;
    }

    public static int[] readHighscores() {

        int[] array = new int[10];

        try {
            reader = new BufferedReader(new FileReader(path + "highscore.txt"));
        } catch (FileNotFoundException e1) {
            System.out.println("[VisualUtil]: File highscore.txt not found");
        }

        try {
            String str = reader.readLine();
            while(str!=null) {
                String[] strarray = str.split("-");
                array[Integer.parseInt(strarray[0])] = Integer.parseInt(strarray[1]);

                str = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("[VisualUtil]: IOException occurred when reading highscores");
        }
        try {
            reader.close();
        }
        catch(IOException e) {}
        return array;
    }

    public static void writeHighscores() {
        try {
            writer = new PrintWriter(new File(path + "highscore.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("[VisualUtil]: File highscore.txt not found");
        }

        for(int i=0; i<UtilityClass.highscores.length; i++) {
            writer.println(i+"-"+UtilityClass.highscores[i]);
        }
        writer.close();
    }
}
