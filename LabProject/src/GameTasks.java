import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class GameTasks {

    public static int alienCount;
    public static int sAlienCount;
    private static int playerTimeDelay = 0;
    public static double random;

    public static void changeDelays() {
        playerTimeDelay++;
    }

    public static int getPlayerDelay() {return playerTimeDelay;}

    public static void resetPlayerDelay() {playerTimeDelay=0;}

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
                UtilityClass.sAliens[i] = new StrongAlien(25 + i * 200, 1);
            }
            for (int i = 0; i < alienCount; i++) {
                if (i > 3 && i <= 7) {
                    UtilityClass.aliens[i] = new Alien (1100 - (i-3)*200,1, false);
                }
                else if (i>7) {
                    UtilityClass.aliens[i] = new Alien (59*i,1, true);
                }
                else {
                    UtilityClass.aliens[i] = new Alien(90 + (i * 400), 1, true);
                }
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
                    UtilityClass.aliens[i] = new Alien(x, y, true);
                }
                else {
                    UtilityClass.aliens[i] = new Alien();
                }
            }
        }
    }

    public static void spawnBarricades(int level) {

        if(level == 1) {
            UtilityClass.barricades = new Barricade[] {
                    new Barricade(900, 120, Barricade.Type.barricade1),
                    new Barricade(450,300, Barricade.Type.barricade2),
                    new Barricade(520,300, Barricade.Type.barricade2),
                    new Barricade(100, 120, Barricade.Type.barricade3),
            };

        }
        else if(level == 2) {
            UtilityClass.barricades = new Barricade[] {
                    new Barricade(900, 120, Barricade.Type.barricade1),
                    new Barricade(400, 350, Barricade.Type.barricade2),
                    new Barricade(470, 350, Barricade.Type.barricade2),
                    new Barricade(540, 350, Barricade.Type.barricade2),
                    new Barricade(610, 350, Barricade.Type.barricade2),
                    new Barricade(100, 120, Barricade.Type.barricade3),
            };

        }
        else if(level == 3) {
            UtilityClass.barricades = new Barricade[] {
                    new Barricade(950, 120, Barricade.Type.barricade1),
                    new Barricade(120, 120, Barricade.Type.barricade1),
                    new Barricade(260, 280, Barricade.Type.barricade2),
                    new Barricade(330, 280, Barricade.Type.barricade2),
                    new Barricade(500, 440, Barricade.Type.barricade2),
                    new Barricade(740, 280, Barricade.Type.barricade2),
                    new Barricade(670, 280, Barricade.Type.barricade2),
                    new Barricade(880, 120, Barricade.Type.barricade3),
                    new Barricade(50, 120, Barricade.Type.barricade3),
            };
        }
    }

    public static void moveAllAliens() {
        for (int i=0; i < alienCount; i++) {
            if (!UtilityClass.aliens[i].isDead()) {
                if (UtilityClass.aliens[i].getrightDirection()) {
                    UtilityClass.aliens[i].x += UtilityClass.aliens[i].getxSpeed();
                }
                else if (!UtilityClass.aliens[i].getrightDirection()) {
                    UtilityClass.aliens[i].x -= UtilityClass.aliens[i].getxSpeed();
                }
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

    public static void moveBullets() {
        for (int i = 0; i < UtilityClass.Bullets.size(); i++) {
            if (UtilityClass.Bullets.get(i).getAngle() == 0) {
                UtilityClass.Bullets.get(i).y += UtilityClass.Bullets.get(i).getySpeed();
            } else if (UtilityClass.Bullets.get(i).getAngle() == -45) {
                UtilityClass.Bullets.get(i).x -= UtilityClass.Bullets.get(i).getxSpeed();
                UtilityClass.Bullets.get(i).y += UtilityClass.Bullets.get(i).getySpeed();
            } else {
                UtilityClass.Bullets.get(i).x += UtilityClass.Bullets.get(i).getxSpeed();
                UtilityClass.Bullets.get(i).y += UtilityClass.Bullets.get(i).getySpeed();
            }
        }
    }

    public static void MovePowerup(boolean isIt) {
        if (isIt && GameBoard.powerup.getY() < 550) {
            GameBoard.powerup.setLocation(GameBoard.powerup.getX()+3, GameBoard.powerup.getY()+3);
            if(GameBoard.powerup.getX() > 1030) {
                GameBoard.powerup.setLocation(1030, GameBoard.powerup.getY()+4);
            }

        }
    }
    public static boolean collisionAlienBullet() {

        for(int j=0; j<alienCount; j++) {
            for(int k=0; k<UtilityClass.Bullets.size(); k++) {
                if(UtilityClass.Bullets.get(k).getBoundingBox().intersects(UtilityClass.aliens[j].getBoundingBox()) && !UtilityClass.aliens[j].isDead()) {
                    random = Math.random()*100;
                    if (random > 90) {
                        GameBoard.powerup.setLocation(UtilityClass.aliens[j].getX(),UtilityClass.aliens[j].getY());
                        GameBoard.powerup.setVisible(true);
                    }
                    UtilityClass.aliens[j].setDead(true);
                    UtilityClass.Bullets.remove(k);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean collisionStrongAlienBullet(){
        for(int j=0; j<sAlienCount; j++) {
            for(int k=0; k<UtilityClass.Bullets.size(); k++) {
                if(UtilityClass.Bullets.get(k).getBoundingBox().intersects(UtilityClass.sAliens[j].getBoundingBox()) && !UtilityClass.sAliens[j].isDead()) {
                    if (UtilityClass.sAliens[j].getHp() > 1){
                        UtilityClass.sAliens[j].getDamage();
                    }
                    else {
                        random = Math.random()*100;
                        if (random > 90) {
                            GameBoard.powerup.setLocation(UtilityClass.sAliens[j].getX(),UtilityClass.sAliens[j].getY());
                            MovePowerup(true);
                            GameBoard.powerup.setVisible(true);
                        }
                        UtilityClass.sAliens[j].setDead(true);
                    }
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

    public static void removalBullet () {
        for (int i=0; i<UtilityClass.Bullets.size(); i++) {
            if (UtilityClass.Bullets.get(i).getBoundingBox().intersects(UtilityClass.frames)) {
                UtilityClass.Bullets.remove(i);
            }
        }
    }

    public static void restart() {
        UtilityClass.human = new Human();

        UtilityClass.Bullets = new ArrayList<>();
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
