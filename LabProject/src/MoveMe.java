import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MoveMe {

    public MoveMe() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("CMP2004 Project");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                TestPane a = new TestPane();
                a.setBackground(Color.black);
                frame.add(a);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class MovementState {

        public int xDirection;

    }

    public class TestPane extends JPanel {

        private MovementState movementState;
        private Rectangle box;

        public TestPane() {
            movementState = new MovementState();
            InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
            ActionMap am = getActionMap();
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "left-pressed");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "left-released");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "right-pressed");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "right-released");

            boolean isMoving1 = false;
            am.put("left-pressed", new XDirectionAction(movementState, -5));
            am.put("left-pressed", new isMoving (true));
            am.put("left-released", new XDirectionAction(movementState, 0));
            am.put("left-released", new isMoving(false));
            am.put("right-pressed", new XDirectionAction(movementState, 5));
            am.put("right-pressed", new isMoving(true));
            am.put("right-released", new XDirectionAction(movementState, 0));
            am.put("right-released", new isMoving(false));

            box = new Rectangle(475, 680, 100, 100);

            Timer timer = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    box.x += movementState.xDirection;
                    if (box.x < 0) {
                        box.x = 0;
                    } else if (box.x + box.width > getWidth()) {
                        box.x = getWidth() - box.width;
                    }
                    repaint();
                }
            });
            timer.start();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1100, 800);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.RED);
            g2d.fill(box);
        }
    }


    public static class XDirectionAction extends AbstractAction {

        private final MovementState movementState;
        private final int value;

        public MovementState getMovementState() {
            return movementState;
        }

        public int getValue() {
            return value;
        }

        public XDirectionAction(MovementState movementState, int value) {
            this.movementState = movementState;
            this.value = value;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            getMovementState().xDirection = getValue();
        }

    }

    public static class isMoving extends AbstractAction {

        public boolean a;
        public static int Angle;

        public boolean getA(){
            return a;
        }

        public int getAngle(){
            return Angle;
        }

        public isMoving (boolean a) {
            this.a = a;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if (a) {
                Angle = 30;
            }
            else {
                Angle = 0;
            }
        }
    }
}
