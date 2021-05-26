import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class Game {

    Action rightAction1;
    Action leftAction1;
    JLabel label;
    ImageIcon icon;
    MyFrame frame;

    private boolean goright = false;
    private boolean goleft = false;

    Game() {
        frame = new MyFrame();

        icon = new ImageIcon("a3.png");

        label = new JLabel();
        label.setBounds(5, 600, 121, 100);
        label.setIcon(icon);
        label.setOpaque(true);

        frame.add(label);

        rightAction1 = new RightAction1();
        leftAction1 = new LeftAction1();

        label.getInputMap().put(KeyStroke.getKeyStroke('d'), "RightAction1");
        label.getActionMap().put("RightAction", rightAction1);


        label.getInputMap().put(KeyStroke.getKeyStroke('a'), "LeftAction1");
        label.getActionMap().put("LeftAction", leftAction1);


        frame.setVisible(true);
    }

    public class RightAction1 extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            goright = true;
            label.setLocation(label.getX() + 10, label.getY());

        }
    }


    public class LeftAction1 extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            label.setLocation(label.getX() - 10, label.getY());
        }
    }
}

