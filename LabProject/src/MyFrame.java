import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyFrame extends JFrame implements KeyListener {

    JLabel label;
    //ImageIcon icon;

    MyFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1100,800);
        this.setLayout(null);
        this.addKeyListener(this);

        //icon = new ImageIcon("a3.png");

        //label = new JLabel();
        //label.setBounds(0,0,121,100);
        //label.setIcon(icon);
        //label.setOpaque(true);

        this.getContentPane().setBackground(Color.black);
        //this.add(label);
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //switch (e.getKeyChar()) {
            //case 'a' -> label.setLocation(label.getX() - 5, label.getY());
            //case 's' -> label.setLocation(label.getX(), label.getY() + 5);
            //case 'd' -> label.setLocation(label.getX() + 5, label.getY());
            //case 'w' -> label.setLocation(label.getX(), label.getY() - 5);
        //}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("You released a key char: " + e.getKeyChar());
        //System.out.println("You released a key code: " + e.getKeyCode());
    }
}
