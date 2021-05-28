import javax.swing.*;


public class Main {

    private static JFrame window;
    public static GameBoard board;

    public static void main(String[] args) {

        createFrame();
        createGameBoard();
    }

    private static void createFrame() {
        System.out.println("[Main]: Creating Frame");

        window = new JFrame("CMP2204 PROJECT");
        window.setVisible(true);
        window.setBounds(100, 100, UtilityClass.startingWidth, UtilityClass.startingHeight);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**Creates a GameBoard object and adds it to the JFrame*/
    private static void createGameBoard() {
        System.out.println("[Main]: Creating game board");

        board = new GameBoard();
        window.add(board);
        board.requestFocusInWindow();
    }
}
