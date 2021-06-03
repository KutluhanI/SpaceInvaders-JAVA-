import javax.swing.*;


public class Main {

    private static JFrame window;
    public static GameBoard board;
    public static Music musicObject;

    public static void main(String[] args) {
        musicObject = new Music();

        musicObject.startMusic();

        createFrame();
        createGameBoard();
    }

    private static void createFrame() {
        System.out.println("[Main]: Creating Frame");

        window = new JFrame("CMP2004 PROJECT");
        window.setVisible(true);
        window.setBounds(100, 100, UtilityClass.startingWidth, UtilityClass.startingHeight);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void createGameBoard() {
        System.out.println("[Main]: Creating game board");

        board = new GameBoard();
        window.add(board);
        board.requestFocusInWindow();
    }
}
