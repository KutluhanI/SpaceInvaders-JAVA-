import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.Timer;

public class GameTasks {

    private static int alienCount;
    private static int sAlienCount;


    public static void spawnAliens() {
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

        switch(GameBoard.level) {
            case 1:
                alienCount = 4;
                sAlienCount = 0;
                break;
            case 2:
                alienCount = 6;
                sAlienCount = 1;
                break;
            case 3:
                alienCount = 8;
                sAlienCount = 3;
                break;

        }


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




}
