import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.Timer;

public class GameTasks {

    public static int alienCount;
    public static int sAlienCount;
    private static int alienTimeDelay = 0;
    private static int playerTimeDelay = 0;
    private static int superShotDelay = 0;

    public static void changeDelays() {
        alienTimeDelay++;
        playerTimeDelay++;
        if(UtilityClass.human.hasSuperShot()) superShotDelay++;
    }

    public static int getAlienDelay() {return alienTimeDelay;}

    public static int getPlayerDelay() {return playerTimeDelay;}

    public static void resetPlayerDelay() {playerTimeDelay=0;}

    public static int getSuperShotDelay() {
        return superShotDelay;
    }

    public static void spawnAliens() {

        switch (GameBoard.level) {
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

        UtilityClass.aliens = new Alien[alienCount+sAlienCount];

        if (GameBoard.level == 1) {

            //TODO Change initial values
            UtilityClass.aliens[0]= new Alien(1,1);
            UtilityClass.aliens[1]= new Alien(1,1);
            UtilityClass.aliens[2]= new Alien(1,1);
            UtilityClass.aliens[3]= new Alien(1,1);

        }
        else if(GameBoard.level == 2) {

            UtilityClass.sAliens[0] = new StrongAlien(1,1);

            UtilityClass.aliens[0]= new Alien(1,1);
            UtilityClass.aliens[1]= new Alien(1,1);
            UtilityClass.aliens[2]= new Alien(1,1);
            UtilityClass.aliens[3]= new Alien(1,1);
            UtilityClass.aliens[4]= new Alien(1,1);
            UtilityClass.aliens[5]= new Alien(1,1);


        }
        else if(GameBoard.level == 3) {

            UtilityClass.aliens[0]= new Alien(1,1);
            UtilityClass.aliens[1]= new Alien(1,1);
            UtilityClass.aliens[2]= new Alien(1,1);
            UtilityClass.aliens[3]= new Alien(1,1);
            UtilityClass.aliens[4]= new Alien(1,1);
            UtilityClass.aliens[5]= new Alien(1,1);
            UtilityClass.aliens[6]= new Alien(1,1);
            UtilityClass.aliens[7]= new Alien(1,1);

            UtilityClass.sAliens[0]= new StrongAlien(1,1);
            UtilityClass.sAliens[1]= new StrongAlien(1,1);
            UtilityClass.sAliens[2]= new StrongAlien(1,1);

        }

    }

    public static void moveAllAliens() {
        Timer timer = new Timer(4, Main.board); {
            for (int i=0; i < alienCount; i++) {
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
        timer.start();
    }

    public static void moveBullets() {
        Timer timer = new Timer(5, Main.board);
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
                    alienCount--;
                    UtilityClass.Bullets.remove(k);
                    return true;
                }
            }
        }

        for(int j=0; j<sAlienCount; j++) {
            for(int k=0; k<UtilityClass.Bullets.size(); k++) {
                if(UtilityClass.Bullets.get(k).getBoundingBox().intersects(UtilityClass.sAliens[j].getBoundingBox()) && !UtilityClass.sAliens[j].isDead()) {
                    UtilityClass.sAliens[j].setDead(true);
                    sAlienCount--;
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
