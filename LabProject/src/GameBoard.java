import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


import javax.swing.*;

public class GameBoard extends JPanel implements KeyListener, ActionListener {

    public static int level = 1;

    private GameState state;
    private final Timer timer;
    private final int timerDelay = 20;

    private static int score = 0;
    private static int aliensKilled = 0;

    public GameBoard() {
        this.setFocusable(true);
        this.requestFocus();

        //init player
        UtilityClass.human = new Human();

        //init alien    s
        GameTasks.spawnAliens();

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

            Timer timer1 = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    a.x += a.getxSpeed();
                    if (a.x < 0) {
                        a.x = 0;
                    } else if (a.x + a.getWidth() > getWidth()) {
                        a.x = getWidth() - a.getWidth();
                    }
                    repaint();
                }
            });
            timer1.start();
        }
    }

	@Override
	public void keyPressed(KeyEvent e) {
        System.out.println("i got here 1");
		if(state == GameState.RUNNING) {
		    MovePlayer user = new MovePlayer(UtilityClass.human);
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if(GameTasks.getPlayerDelay() > UtilityClass.human.getShootingDelay() || UtilityClass.human.hasSuperShot()) {
                    UtilityClass.Bullets.add(new Bullet(UtilityClass.human.getX()+19, UtilityClass.human.getY()-10, -10, -5));
                    GameTasks.resetPlayerDelay();
                }
            }
		}
		else if(state == GameState.MAIN_MENU) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER -> state = GameState.RUNNING;
                case KeyEvent.VK_SPACE -> state = GameState.HIGHSCORES;
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                System.out.println("i got here2");
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
    public void keyTyped(KeyEvent arg0) {
        System.out.println("i got here 3");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        if(state == GameState.RUNNING) {
            //When player is alive
            if(!UtilityClass.human.isDead()) {
                //Changes delays
                GameTasks.changeDelays();

                //Moves all aliens
                GameTasks.moveAllAliens();

                //Moves Bullets
                GameTasks.moveBullets();

                //Checks collision
                if(GameTasks.collisionAlienBullet()) {
                    aliensKilled++;
                }

                GameTasks.collisionBarricadeBullet();

            }

            //Count how many aliens has the player killed
            if(aliensKilled == GameTasks.alienCount+GameTasks.sAlienCount) {
                aliensKilled = 0;
                GameTasks.spawnAliens();
            }
        }
        repaint();
        revalidate();
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
            g.drawImage(VisualUtil.bullet , UtilityClass.Bullets.get(i).getX(), UtilityClass.Bullets.get(i).getY(),
                    VisualUtil.bullet.getWidth(), VisualUtil.bullet.getHeight(), null);
        }

        for (int i=0; i<UtilityClass.barricades.length; i++) {
            if (UtilityClass.barricades[i].getType() == Barricade.Type.barricade1) {
                g.drawImage(VisualUtil.barricade1, UtilityClass.barricades[i].getX(), UtilityClass.barricades[i].getY(),
                        VisualUtil.barricade1.getWidth(), VisualUtil.barricade1.getHeight(), null);
            }
            if (UtilityClass.barricades[i].getType() == Barricade.Type.barricade2) {
                g.drawImage(VisualUtil.barricade2, UtilityClass.barricades[i].getX(), UtilityClass.barricades[i].getY(),
                        VisualUtil.barricade2.getWidth(), VisualUtil.barricade2.getHeight(), null);
            }
            if (UtilityClass.barricades[i].getType() == Barricade.Type.barricade3) {
                g.drawImage(VisualUtil.barricade3, UtilityClass.barricades[i].getX(), UtilityClass.barricades[i].getY(),
                        VisualUtil.barricade3.getWidth(), VisualUtil.barricade3.getHeight(), null);
            }
        }

        //draw aliens
        for (int i=0; i<UtilityClass.aliens.length; i++) {
            if (UtilityClass.aliens[i] != null) {
                g.drawImage(VisualUtil.alien, UtilityClass.aliens[i].getX(), UtilityClass.aliens[i].getY(),
                        VisualUtil.alien.getWidth(), VisualUtil.alien.getHeight(), null);
            }
        }

        //draw red ship
        for (int i=0; i< UtilityClass.sAliens.length; i++) {
            if (UtilityClass.sAliens[i] != null)
                g.drawImage(VisualUtil.sAlien, UtilityClass.sAliens[i].getX(), UtilityClass.sAliens[i].getY(),
                        VisualUtil.sAlien.getWidth(), VisualUtil.sAlien.getHeight(), null);
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
