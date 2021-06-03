import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static java.lang.Math.toDegrees;

public class Human extends GameObject {

    public static int shootingDelay = 40;
    public static int superShootingDelay = 10;
    private boolean superShot;
    private static final int height = 100;
    private static final int widht = 100;
    private static double Acceleration = 0;
    private double xSpeed = 0;
    private static double Angle;
    private static boolean onWay;

    public Human() {
        x = 475;
        y = 660;
        superShot = false;
    }

    public int getSuperShootingDelay() {
        return superShootingDelay;
    }

    public void setxSpeed(double val) {
        if (onWay) {
            if (this.xSpeed >= 10) {
                this.xSpeed = 10;
            } else if (this.xSpeed <= -10) {
                this.xSpeed = -10;
            } else {
                this.xSpeed += (val / 15);
            }
        } else {
            if (this.xSpeed > 0.1 || this.xSpeed < -0.1) {
                this.xSpeed += (3f / 15f) * (-(Math.signum(this.xSpeed)));
            }
            else {
                this.xSpeed = 0;
            }
        }
    }

    public double getAcceleration() {
        return Acceleration;
    }

    public void setAngle() {
        Angle = toDegrees(Math.atan(UtilityClass.human.getxSpeed() / 5.0));
    }

    public double getxSpeed() {
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
        this.superShot = superShot;
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
            Acceleration = value;
            onWay = a;
        }

    }

    public double getAngle() {
        return Angle;
    }
}