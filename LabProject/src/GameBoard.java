import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.*;


public class GameBoard extends JPanel implements KeyListener, ActionListener {

    public static int level = 1;
    public static boolean isFirst = false;

    private GameState state;
    private final Timer timer;
    public static int timeCounter = 0;
    public static int timeLeft = 10000;

    private static int score = 10100;
    private static int aliensKilled = 0;
    public static int lifecounter = 1;

    public static long time = System.nanoTime();
    public static long last_time = System.nanoTime();
    public static int delta_time = (int) ((time - last_time) / 1000000);
    private int timerDelay = delta_time;

    public static JButton powerup;
    public static JButton play;
    public static JButton highscores;
    public static JButton music;

    public GameBoard() {
        addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
        this.setLayout(null);
        last_time = time;

        music = new JButton(VisualUtil.musicI);
        this.add(music);
        music.setBounds(1030,340,30,30);
        music.addActionListener(this);
        music.setBorderPainted(false);
        music.setBorder(null);
        music.setContentAreaFilled(false);
        music.setVisible(true);

        powerup = new JButton(VisualUtil.powerup);
        this.add(powerup);
        powerup.setBounds(200,100,50,50);
        powerup.addActionListener(this);
        powerup.setBorderPainted(false);
        powerup.setBorder(null);
        powerup.setContentAreaFilled(false);
        powerup.setVisible(false);

        Font Mainmenufont = new Font("Algerian", Font.PLAIN, 20);

        play = new JButton("[Enter] Start Game");
        this.add(play);
        play.setBounds(410, 400, 220, 30);
        play.addActionListener(this);
        play.setBorderPainted(false);
        play.setBorder(null);
        play.setContentAreaFilled(false);
        play.setFont(Mainmenufont);
        play.setForeground(Color.WHITE);
        play.setVisible(true);

        highscores = new JButton("[Space] Highscores");
        this.add(highscores);
        highscores.setBounds(410,430,220,30);
        highscores.addActionListener(this);
        highscores.setBorderPainted(false);
        highscores.setBorder(null);
        highscores.setContentAreaFilled(false);
        highscores.setFont(Mainmenufont);
        highscores.setForeground(Color.WHITE);
        highscores.setVisible(true);

        //init player
        UtilityClass.human = new Human();

        //init Bullets list
        UtilityClass.Bullets = new ArrayList<>();

        state = GameState.MAIN_MENU;

        //init timer
        timer = new Timer(delta_time, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        repaint();
        revalidate();

        if (level == 1 && state == GameState.RUNNING){
            g.drawImage(VisualUtil.level1,0,0, 1100, 800, null);
        }
        /*
        else if (level == 2){
            g.drawImage(VisualUtil.level2,0,0,1100,800,null);
        }
        else if (level == 3){
            g.drawImage(VisualUtil.level3,0,0,1100,800,null);
        }
         */
        else {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,1100,800);
        }

        if(state == GameState.RUNNING) {
            graphicRunning(g);
        }
        else if(state == GameState.MAIN_MENU) {
            graphicMainMenu(g);
        }
        else if(state == GameState.YOU_WIN) {
            graphicYouWin(g);
        }
        else if(state == GameState.GAME_OVER) {
            graphicGameOver(g);
        }
        else if(state == GameState.HIGHSCORES) {
            graphicHighscores(g);
        }
    }

    public void MovePlayer(Human a) {

        InputMap im = getInputMap();
        ActionMap am = getActionMap();
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "left-pressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "left-released");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "right-pressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "right-released");

        am.put("left-pressed", new Human.XDirectionAction(-6, true));
        am.put("left-released", new Human.XDirectionAction(0, false));
        am.put("right-pressed", new Human.XDirectionAction(6, true));
        am.put("right-released", new Human.XDirectionAction(0, false));

        a.setxSpeed((int) a.getAcceleration());
        a.setAngle();
        a.x += a.getxSpeed();
            if(a.x< 0)
        {
            a.x = 0;
        } else if(a.x +a.getWidth()>
        getWidth())
        {
            a.x = getWidth() - a.getWidth();
        }
    }

	@Override
	public void keyPressed(KeyEvent e) {
		if(state == GameState.RUNNING) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (UtilityClass.human.hasSuperShot()) {
                    if (GameTasks.getPlayerDelay() > UtilityClass.human.getSuperShootingDelay()) {
                        UtilityClass.Bullets.add(new Bullet(UtilityClass.human.getX()+47, UtilityClass.human.getY()-10, -10,
                        UtilityClass.human.getxSpeed()
                        , UtilityClass.human.getAngle()));
                        GameTasks.resetPlayerDelay();
                    }
                }
                else if (GameTasks.getPlayerDelay() > UtilityClass.human.getShootingDelay()) {
                    UtilityClass.Bullets.add(new Bullet(UtilityClass.human.getX()+47, UtilityClass.human.getY()-10, -10,
                    UtilityClass.human.getxSpeed()
                    , UtilityClass.human.getAngle()));
                    GameTasks.resetPlayerDelay();
                }
            }
		}
		else if(state == GameState.MAIN_MENU) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER -> {
                    state = GameState.RUNNING;
                    play.setVisible(false);
                    highscores.setVisible(false);
                    level = 1;
                    timeCounter = 0;
                    timeLeft = 10000;
                    GameTasks.spawnAliens(false, level);
                    GameTasks.spawnBarricades(level);
                }
                case KeyEvent.VK_SPACE -> {state = GameState.HIGHSCORES;
                    play.setVisible(false);
                    highscores.setVisible(false);}
            }
		}
		else if(state == GameState.GAME_OVER || state == GameState.YOU_WIN) {
			state = GameState.MAIN_MENU;
            play.setVisible(true);
            highscores.setVisible(true);

			if(score > UtilityClass.highscores[9]) {
				UtilityClass.highscores[9] = score;
				GameTasks.sortHighscores();
				VisualUtil.writeHighscores();
			}

			score = 10000;
			GameTasks.restart();
		}
		else if(state == GameState.HIGHSCORES) {
			state = GameState.MAIN_MENU;
            play.setVisible(true);
            highscores.setVisible(true);
		}
	}

	@Override
    public void keyReleased(KeyEvent arg0) {}

    @Override
    public void keyTyped(KeyEvent arg0) {}

    @Override
    public void actionPerformed(ActionEvent arg0) {
        time = System.nanoTime();
        delta_time = (int) ((time - last_time) / 1000000);
        last_time = time;

        if (arg0.getSource()==music) {
            Main.musicObject.musicButton();
            music.setVisible(false);
            music.setVisible(true);
        }

        if(state == GameState.RUNNING) {

            //When player is alive
            if(!UtilityClass.human.isDead()) {

                if (arg0.getSource()==powerup) {
                    UtilityClass.human.setSuperShot(true);
                    powerup.setVisible(false);
                    GameTasks.MovePowerup(false);
                }

                if (GameTasks.random > 90) {
                    GameTasks.MovePowerup(true);
                }
                GameTasks.changeDelays();

                GameTasks.removalBullet();

                MovePlayer(UtilityClass.human);

                //Moves all aliens
                GameTasks.moveAllAliens();

                //Moves Bullets
                GameTasks.moveBullets();

                //Checks collision
                if(GameTasks.collisionAlienBullet()) {
                    aliensKilled++;
                }

                if(GameTasks.collisionStrongAlienBullet()) {
                    if (lifecounter == 2) {
                        aliensKilled++;
                        lifecounter = 1;
                    }
                    else {
                        lifecounter++;
                    }
                }

                GameTasks.collisionBarricadeBullet();

                timeCounter += (delta_time)/8;
                timeLeft -= ((delta_time)/8);
                score -= (delta_time)/8;
                if (timeLeft <= 0) {
                    state = GameState.GAME_OVER;
                    aliensKilled = 0;
                    UtilityClass.Bullets.clear();
                    UtilityClass.human.setSuperShot(false);
                    UtilityClass.aliens = null;
                    UtilityClass.sAliens = null;
                    powerup.setVisible(false);
                    isFirst = false;
                }
            }

            if(aliensKilled == GameTasks.alienCount+GameTasks.sAlienCount) {
                aliensKilled = 0;
                level++;
                UtilityClass.Bullets.clear();
                UtilityClass.human.setSuperShot(false);
                UtilityClass.aliens = null;
                UtilityClass.sAliens = null;
                powerup.setVisible(false);
                isFirst = false;
                if (level == 4){
                    state = GameState.YOU_WIN;
                }
                else {
                    GameTasks.spawnAliens(false, level);
                    GameTasks.spawnBarricades(level);
                }
            }
        }
        else if (state == GameState.MAIN_MENU) {
            if (arg0.getSource()==play) {
                state = GameState.RUNNING;
                level = 1;
                timeCounter = 0;
                timeLeft = 10000;
                GameTasks.spawnAliens(false, level);
                GameTasks.spawnBarricades(level);
                play.setVisible(false);
                highscores.setVisible(false);
            }
            else if (arg0.getSource()==highscores) {
                state = GameState.HIGHSCORES;
                play.setVisible(false);
                highscores.setVisible(false);
            }
        }
        revalidate();
        repaint();
    }

    private static void graphicRunning(Graphics g) {
        //player	//posX, posY, width, height
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform a = new AffineTransform();
        a.rotate(Math.toRadians(UtilityClass.human.getAngle()), UtilityClass.human.getX()+47, UtilityClass.human.getY());
        a.translate(UtilityClass.human.getX(), UtilityClass.human.getY());
        g2d.drawImage(VisualUtil.player, a, null);

        //strings
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);

        g.drawString("Time: ", 10, 17);
        g.drawString(""+ (timeCounter/100) , 70, 17);

        g.drawString("TimeLeft: ", 10, 67);
        g.drawString(""+ timeLeft/100 , 100, 67);

        g.drawString("Level: ", 1000, 17);
        g.drawString(""+level, 1070, 17);

        g.drawString("Velocity: ",930, 400);
        g.drawString(""+UtilityClass.human.getxSpeed(), 1030, 400);

        g.drawString("Angle: ",930, 430);
        g.drawString(""+UtilityClass.human.getAngle(), 1030, 430);

        //draws Bullets
        g.setColor(Color.WHITE);
        for(int i=0; i<UtilityClass.Bullets.size(); i++) {
            g2d.drawImage(VisualUtil.bullet , UtilityClass.Bullets.get(i).getX(), UtilityClass.Bullets.get(i).getY(),
                    2, 10, null );
        }

        for (int i=0; i<UtilityClass.barricades.length; i++) {
            if (UtilityClass.barricades[i].getType() == Barricade.Type.barricade1) {
                g2d.drawImage(VisualUtil.barricade1, UtilityClass.barricades[i].getX(), UtilityClass.barricades[i].getY(),
                        VisualUtil.barricade1.getWidth(), VisualUtil.barricade1.getHeight(), null);
            }
            if (UtilityClass.barricades[i].getType() == Barricade.Type.barricade2) {
                g2d.drawImage(VisualUtil.barricade2, UtilityClass.barricades[i].getX(), UtilityClass.barricades[i].getY(),
                        VisualUtil.barricade2.getWidth(), VisualUtil.barricade2.getHeight(), null);
            }
            if (UtilityClass.barricades[i].getType() == Barricade.Type.barricade3) {
                g2d.drawImage(VisualUtil.barricade3, UtilityClass.barricades[i].getX(), UtilityClass.barricades[i].getY(),
                        VisualUtil.barricade3.getWidth(), VisualUtil.barricade3.getHeight(), null);
            }
        }

        //draw aliens
        for (int i=0; i<UtilityClass.aliens.length; i++) {
            if (!UtilityClass.aliens[i].isDead()) {
                g2d.drawImage(VisualUtil.alien, UtilityClass.aliens[i].getX(), UtilityClass.aliens[i].getY(),
                        VisualUtil.alien.getWidth(), VisualUtil.alien.getHeight(), null);
            }
        }

        //draw strong aliens
        for (int i=0; i< UtilityClass.sAliens.length; i++) {
            if (!UtilityClass.sAliens[i].isDead()) {
                g2d.drawImage(VisualUtil.sAlien, UtilityClass.sAliens[i].getX(), UtilityClass.sAliens[i].getY(),
                        VisualUtil.sAlien.getWidth(), VisualUtil.sAlien.getHeight(), null);
            }
        }
    }

    private static void graphicMainMenu(Graphics g) {
        //title
        g.setColor(Color.WHITE);
        g.setFont(new Font("Algerian", Font.BOLD, 90));
        g.drawString("CMP2004", 50, 150);
        g.setColor(Color.GREEN);
        g.drawString("PROJECT", 500, 150);
        g.setColor(Color.WHITE);

        //points
        g.drawImage(VisualUtil.alien, 150, 240, VisualUtil.alien.getWidth(), VisualUtil.alien.getHeight(), null);
        g.drawImage(VisualUtil.alien, 300, 240, VisualUtil.alien.getWidth(), VisualUtil.alien.getHeight(), null);
        g.drawImage(VisualUtil.alien, 450, 240, VisualUtil.alien.getWidth(), VisualUtil.alien.getHeight(), null);
        g.drawImage(VisualUtil.sAlien, 700, 240, VisualUtil.sAlien.getWidth(), VisualUtil.sAlien.getHeight(), null);

        g.setFont(new Font("Algerian", Font.PLAIN, 20));

        //buttons
        //g.drawString("[Enter] Start Game", 330, 400);
        //g.drawString("[Space] Highscores", 330, 430);
    }

    private static void graphicYouWin(Graphics g) {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Algerian", Font.BOLD, 100));
        g.drawString("YOU WIN", 330, 250);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Algerian", Font.PLAIN, 40));
        g.drawString("Your score: "+ score, 350, 350);
        g.drawString("Time: "+ timeCounter/100,350,400);
        g.drawString("Press any button to return to main menu", 150, 500);
    }

    private static void graphicGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Algerian", Font.BOLD, 100));
        g.drawString("GAME OVER", 300, 250);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Algerian", Font.PLAIN, 40));
        g.drawString("Your score: "+ score, 350, 350);
        g.drawString("Time: "+ timeCounter/100,350,400);
        g.drawString("Press any button to return to main menu", 150, 500);
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
