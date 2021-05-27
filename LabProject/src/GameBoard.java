import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


import javax.swing.*;

public class GameBoard extends JPanel implements ActionListener, KeyListener{

    public static int level = 1;

    private GameState state;
    private Timer timer;
    private int timerDelay = 20;

    private int playerDeathDelay = 200;

    private static int score = 0;
    private static int aliensKilled = 0;

    /**Creates GameBoard*/
    public GameBoard() {
        this.setFocusable(true);

        //init player
        UtilityClass.human = new Human();

        //init alien    s
        GameTasks.initAliens();

        //init Bullets list
        UtilityClass.Bullets = new ArrayList<Bullet>();

        //init barricades


        if(level == 1) {
            UtilityClass.barricades = new Barricade[] {
                    new Barricade(70, 400, Barricade.Type.barricade1),
                    new Barricade(100, 400, Barricade.Type.barricade3),
            };

        }
        else if(level == 2) {
            UtilityClass.barricades = new Barricade[] {
                    new Barricade(70, 400, Barricade.Type.barricade1),
                    new Barricade(100, 400, Barricade.Type.barricade2),
                    new Barricade(130, 400, Barricade.Type.barricade3),
            };

        }
        else if(level == 3) {
            UtilityClass.barricades = new Barricade[] {
                    new Barricade(70, 400, Barricade.Type.barricade1),
                    new Barricade(100, 400, Barricade.Type.barricade2),
                    new Barricade(130, 400, Barricade.Type.barricade2),
                    new Barricade(31, 31, Barricade.Type.barricade3)
            };

        }

        state = GameState.MAIN_MENU;

        //init timer
        timer = new Timer(timerDelay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        repaint(); revalidate();

        //background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, UtilityClass.startingWidth, UtilityClass.startingHeight);

        if(state == GameState.RUNNING) {
            graphicRunning(g);
        }
        else if(state == GameState.MAIN_MENU) {
            graphicMainMenu(g);
        }
        else if(state == GameState.GAME_OVER) {
            graphicGameOver(g);
        }
        else if(state == GameState.HIGHSCORES) {
            graphicHighscores(g);
        }
    }

    public static void addScore(int value) {score+=value;}


    public static class MovePlayer extends JPanel {

        public MovePlayer(Human a) {

            InputMap im = getInputMap();
            ActionMap am = getActionMap();
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "left-pressed");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "left-released");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "right-pressed");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "right-released");

            boolean isMoving1 = false;
            am.put("left-pressed", new Human.XDirectionAction(-5));
            am.put("left-pressed", new Human.isMoving(true));
            am.put("left-released", new Human.XDirectionAction(0));
            am.put("left-released", new Human.isMoving(false));
            am.put("right-pressed", new Human.XDirectionAction(5));
            am.put("right-pressed", new Human.isMoving(true));
            am.put("right-released", new Human.XDirectionAction(0));
            am.put("right-released", new Human.isMoving(false));

            Timer timer = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    a.x += a.getxSpeed();
                    if (a.x < 0) {
                        a.x = 0;
                    } else if (a.x + a.getWidht() > getWidth()) {
                        a.x = getWidth() - a.getWidht();
                    }
                    repaint();
                }
            });
            timer.start();
        }
    }

	@Override
	public void keyPressed(KeyEvent arg0) {

		if(state == GameState.RUNNING) {
		    MovePlayer user = new MovePlayer(UtilityClass.human);
            if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
                if(GameTasks.getPlayerDelay() > UtilityClass.human.getShootingDelay() || UtilityClass.human.hasSuperShot()) {
                    UtilityClass.Bullets.add(new Bullet(UtilityClass.human.getX()+19, UtilityClass.human.getY()-10, -10));
                    GameTasks.resetPlayerDelay();
                }
        }
		}
		else if(state == GameState.MAIN_MENU) {
			switch(arg0.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				state = GameState.RUNNING;
				break;

			case KeyEvent.VK_SPACE:
				state = GameState.HIGHSCORES;
				break;
			}
		}
		else if(state == GameState.GAME_OVER) {
			state = GameState.MAIN_MENU;

			if(score > UtilityClass.highscores[9]) {
				UtilityClass.highscores[9] = score;
				GameTasks.sortHighscores();
				VisualUtil.writeHighscores();
			}

			score = 0;
			GameTasks.restart();
		}
		else if(state == GameState.HIGHSCORES) {
			state = GameState.MAIN_MENU;
		}
	}

	@Override
    public void keyReleased(KeyEvent arg0) {}

    @Override
    public void keyTyped(KeyEvent arg0) {}

    @Override
    public void actionPerformed(ActionEvent arg0) {

        if(state == GameState.RUNNING) {
            //When player is alive
            if(!UtilityClass.human.isDead()) {
                //Changes delays
                GameTasks.changeDelays();

                //Moves all aliens
                GameTasks.moveAllAliens();

                //Makes aliens shoot
                GameTasks.alienShoot();

                //Moves Bullets
                GameTasks.movePrjs();

                //Checks collision
                if(GameTasks.checkCollisionAlienPrj()) {
                    aliensKilled++;
                }

                GameTasks.checkCollisionShieldPrj();

                //If player is shot
                if(GameTasks.checkCollisionPlayerPrj()) {
                    System.out.println("[GameBoard]: Detected player death");
                    UtilityClass.player.setDead(true);
                }

                //Checks if the aliens have landed on the planet
                if(GameTasks.checkAliensLanded())
                    state = GameState.GAME_OVER;

                //Creates the red ship on the screen
                GameTasks.strongAlienFlight();

                //Checks collision with red ship
                GameTasks.checkCollisionstrongAlienPrj();
            }

            //Count how many aliens has the player killed
            if(aliensKilled == UtilityClass.alienColumns*UtilityClass.alienRows) {
                aliensKilled = 0;
                GameTasks.initAliens();
                Alien.decreaseMotionDelay();
                UtilityClass.human.addLife();
            }
        }
        repaint(); revalidate();
    }
//
//	@Override
//	public void keyReleased(KeyEvent arg0) {}
//
//	@Override
//	public void keyTyped(KeyEvent arg0) {}

    private static void graphicRunning(Graphics g) {
        //player	//posX, posY, width, height

        g.drawImage(VisualUtil.player, UtilityClass.human.getX(), UtilityClass.human.getY(),
                VisualUtil.player.getWidth(), VisualUtil.player.getHeight(), null);

        //strings
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString("Score: ", 500, 560);
        g.drawString(""+score, 700, 560);
        g.drawString("Lives: ", 40, 560);


        //line
        g.setColor(Color.GREEN);
        g.fillRect(5, 530, 880, 5);

        //draws Bullets
        g.setColor(Color.WHITE);
        for(int i=0; i<UtilityClass.Bullets.size(); i++) {
            g.fillRect(UtilityClass.Bullets.get(i).getX(), UtilityClass.Bullets.get(i).getY(), 2, 10);
        }

        //draw aliens TODO


        //draw red ship
        for (int i=0; i< UtilityClass.sAliens.length; i++) {
            if (UtilityClass.sAliens[i] != null)
                g.drawImage(VisualUtil.sAlien, UtilityClass.sAliens[i].getX(), UtilityClass.sAliens[i].getY(), VisualUtil.sAlien.getWidth() * 3, VisualUtil.sAlien.getHeight() * 3, null);
        }
    }

    private static void graphicMainMenu(Graphics g) {
        //title
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 90));
        g.drawString("SPACE", 50, 150);
        g.setColor(Color.GREEN);
        g.drawString("INVADERS", 400, 150);
        g.setColor(Color.WHITE);

        //points
        g.drawImage(VisualUtil.alien, 350, 200, VisualUtil.alien.getWidth(), VisualUtil.alien.getHeight(), null);
        g.drawImage(VisualUtil.alien, 350, 240, VisualUtil.alien.getWidth(), VisualUtil.alien.getHeight(), null);
        g.drawImage(VisualUtil.alien, 350, 280, VisualUtil.alien.getWidth(), VisualUtil.alien.getHeight(), null);
        g.drawImage(VisualUtil.sAlien, 340, 320, VisualUtil.sAlien.getWidth(), VisualUtil.sAlien.getHeight(), null);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("= 10 pts", 400, 220);
        g.drawString("= 20 pts", 400, 260);
        g.drawString("= 30 pts", 400, 300);
        g.drawString("= ??? :)", 410, 340);

        //buttons
        g.drawString("[Enter] Start Game", 330, 400);
        g.drawString("[Space] Highscores", 330, 430);
    }

    private static void graphicGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("GAME OVER", 300, 150);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Your score: "+score, 350, 200);
        g.drawString("Press any button to return to main menu", 300, 250);
    }

    private static void graphicHighscores(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("HIGHSCORES", 300, 100);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        for(int i=0; i<UtilityClass.highscores.length; i++) {
            g.drawString(""+(i+1)+" -", 320, 140+i*30);
            g.drawString(""+UtilityClass.highscores[i], 540, 140+i*30);
        }

        g.drawString("Press any button to return to main menu", 300, 500);
    }

}
