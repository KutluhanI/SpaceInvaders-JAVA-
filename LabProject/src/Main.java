import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        //Game game1 = new Game();
        //new MoveMe();

        Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
        System.out.println(path);
    }
}
