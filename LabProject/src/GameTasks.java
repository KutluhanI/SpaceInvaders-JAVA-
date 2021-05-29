import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.Timer;

public class GameTasks {

    public static int alienCount;
    public static int sAlienCount;
    private static int playerTimeDelay = 0;
    private static int superShotDelay = 0;

    public static void changeDelays() {
        playerTimeDelay++;
        if(UtilityClass.human.hasSuperShot()) superShotDelay++;
    }

    public static int getPlayerDelay() {return playerTimeDelay;}

    public static void resetPlayerDelay() {playerTimeDelay=0;}

    public static int getSuperShotDelay() {
        return superShotDelay;
    }

    public static void spawnAliens(boolean inGame, int level) {

        if (!inGame) {
            switch (level) {
                case 1 -> {
                    alienCount = 4;
                    sAlienCount = 0;
                }
                case 2 -> {
                    alienCount = 6;
                    sAlienCount = 1;
                }
                case 3 -> {
                    alienCount = 8;
                    sAlienCount = 3;
                }
            }
            UtilityClass.aliens = new Alien[alienCount];
            UtilityClass.sAliens = new StrongAlien[sAlienCount];
        }

        if (!GameBoard.isFirst) {
            for (int i = 0; i < sAlienCount; i++) {
                UtilityClass.sAliens[i] = new StrongAlien(i * 200, 1);
            }
            for (int i = 0; i < alienCount; i++) {
                UtilityClass.aliens[i] = new Alien(15 + (i * 100), 1);
            }
            GameBoard.isFirst = true;
        }
        else {
            for (int i = 0; i < sAlienCount; i++) {
                if (!UtilityClass.sAliens[i].isDead()) {
                    int x,y;
                    x = UtilityClass.sAliens[i].x;
                    y = UtilityClass.sAliens[i].y;
                    UtilityClass.sAliens[i] = new StrongAlien(x, y);
                }
                else {
                    UtilityClass.sAliens[i] = new StrongAlien();
                }
            }
            for (int i = 0; i < alienCount; i++) {
                if (!UtilityClass.aliens[i].isDead()) {
                    int x,y;
                    x = UtilityClass.aliens[i].x;
                    y = UtilityClass.aliens[i].y;
                    UtilityClass.aliens[i] = new Alien(x, y);
                }
                else {
                    UtilityClass.aliens[i] = new Alien();
                }
            }
        }
    }

    public static void moveAllAliens() {
        Timer timer = new Timer(5, Main.board); {
            for (int i=0; i < alienCount; i++) {
                if (!UtilityClass.aliens[i].isDead()) {
                    UtilityClass.aliens[i].x += UtilityClass.aliens[i].getxSpeed();
                    if (UtilityClass.aliens[i].x < 0) {
                        UtilityClass.aliens[i].x = 0;
                        UtilityClass.aliens[i].changexSpeed();
                    } else if (UtilityClass.aliens[i].x + UtilityClass.aliens[i].getWidth() > 1100) {
                        UtilityClass.aliens[i].x = 1100 - UtilityClass.aliens[i].getWidth();
                        UtilityClass.aliens[i].changexSpeed();
                    }
                }
            }
            for (int i=0; i < sAlienCount; i++) {
                if (!UtilityClass.sAliens[i].isDead()) {
                    UtilityClass.sAliens[i].x += UtilityClass.sAliens[i].getxSpeed();
                    if (UtilityClass.sAliens[i].x < 0) {
                        UtilityClass.sAliens[i].x = 0;
                        UtilityClass.sAliens[i].changexSpeed();
                    } else if (UtilityClass.sAliens[i].x + UtilityClass.sAliens[i].getWidth() > 1100) {
                        UtilityClass.sAliens[i].x = 1100 - UtilityClass.sAliens[i].getWidth();
                        UtilityClass.sAliens[i].changexSpeed();
                    }
                }
            }
        }
        timer.start();
    }

    public static void moveBullets() {
        Timer timer = new Timer(4, Main.board);
        {
            if (UtilityClass.human.getAngle() == 0) {
                for (int i = 0; i < UtilityClass.Bullets.size(); i++) {
                    UtilityClass.Bullets.get(i).y += UtilityClass.Bullets.get(i).getySpeed();
                }
            } else {
                for (int i = 0; i < UtilityClass.Bullets.size(); i++) {
                    UtilityClass.Bullets.get(i).x += UtilityClass.Bullets.get(i).getxSpeed();
                    UtilityClass.Bullets.get(i).y += UtilityClass.Bullets.get(i).getySpeed();
                }
            }
        }
        timer.start();
    }

    public static boolean collisionAlienBullet() {

        for(int j=0; j<alienCount; j++) {
            for(int k=0; k<UtilityClass.Bullets.size(); k++) {
                if(UtilityClass.Bullets.get(k).getBoundingBox().intersects(UtilityClass.aliens[j].getBoundingBox()) && !UtilityClass.aliens[j].isDead()) {
                    UtilityClass.aliens[j].setDead(true);
                    UtilityClass.Bullets.remove(k);
                    return true;
                }
            }
        }

        for(int j=0; j<sAlienCount; j++) {
            for(int k=0; k<UtilityClass.Bullets.size(); k++) {
                if(UtilityClass.Bullets.get(k).getBoundingBox().intersects(UtilityClass.sAliens[j].getBoundingBox()) && !UtilityClass.sAliens[j].isDead()) {
                    UtilityClass.sAliens[j].setDead(true);
                    UtilityClass.Bullets.remove(k);
                    return true;
                }
            }
        }
        return false;
    }

    public static void collisionBarricadeBullet() {
        for(int i=0; i<UtilityClass.barricades.length; i++) {
            for(int j=0; j<UtilityClass.Bullets.size(); j++) {
                if(UtilityClass.Bullets.get(j).getBoundingBox().intersects(UtilityClass.barricades[i].getBoundingBox())) {
                    UtilityClass.Bullets.remove(j);
                }
            }
        }
    }

    public static void restart() {
        UtilityClass.human = new Human();

        UtilityClass.Bullets = new ArrayList<Bullet>();

        UtilityClass.barricades = new Barricade[] {
                new Barricade(70, 400, Barricade.Type.barricade1),
                new Barricade(100, 400, Barricade.Type.barricade3),
        };

    }

    public static void sortHighscores() {
        int appo;

        for(int i=0; i<UtilityClass.highscores.length-1; i++) {
            for(int j=i+1; j<UtilityClass.highscores.length; j++) {
                if(UtilityClass.highscores[j]>UtilityClass.highscores[i]) {
                    appo = UtilityClass.highscores[i];
                    UtilityClass.highscores[i] = UtilityClass.highscores[j];
                    UtilityClass.highscores[j] = appo;
                }
            }
        }
    }

}
