import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Human extends GameObject{

    public static int shootingDelay = 40;
    private static int xSpeed = 0;
    private boolean superShot;
    private static final int height = 100;
    private static final int widht = 100;
    private static int Angle = 0;

    public Human () {
        x = 475;
        y = 500;
        superShot = false;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return widht;
    }

    public int getShootingDelay() {
        return shootingDelay;
    }

    public void setSuperShot(boolean superShot) {
        this.superShot=superShot;
    }
    public boolean hasSuperShot() {
        return superShot;
    }

    @Override
    public Rectangle getBoundingBox() {
        return null;
    }

    public static class XDirectionAction extends AbstractAction {

        public int value;
        public boolean a;

        public XDirectionAction(int value1, boolean a) {
            this.value = value1;
            this.a = a;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            xSpeed = value;
            if (a) {
                Angle = 30;
            }
            else {
                Angle = 0;
            }
        }

    }
    public int getAngle(){
        return Angle;
    }
}
