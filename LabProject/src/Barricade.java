import java.awt.*;

public class Barricade extends GameObject {

    private Type type;

    public Barricade (int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
    public Type getType () {
        return type;
    }

    @Override
    public Rectangle getBoundingBox() {
        return null;
    }

    public enum Type {
        barricade1,
        barricade2,
        barricade3
    }
}
