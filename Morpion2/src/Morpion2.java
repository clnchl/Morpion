import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Morpion2 extends JFrame {

    private JButton[][] buttons;
    private char currentPlayer;
    private boolean gameWon;
    private int scoreX;
    private int scoreO;
    private String playerXName;
    private String playerOName;
    private String winningPlayerName;

    public Morpion2() {
        buttons = new JButton[3][3];
        currentPlayer = 'X';
        gameWon = false;
        scoreX = 0;
        scoreO = 0;
        playerXName = "";
        playerOName = "";
        winningPlayerName = "";

        initializePlayers();
        initializeUI();
    }

    private void initializePlayers() {
        playerXName = JOptionPane.showInputDialog("Nom du Joueur X :");
        playerOName = JOptionPane.showInputDialog("Nom du Joueur O :");
    }

    private void initializeUI() {
        setTitle("Morpion 2024");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(buttons[i][j].getFont().deriveFont(30.0f));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                add(buttons[i][j]);
            }
        }
    }

    private void checkWin(int row, int col) {
        boolean horizontalWin = true;
        for (int i = 0; i < 3; i++) {
            if (buttons[i][col].getText().isEmpty() || buttons[i][col].getText().charAt(0) != currentPlayer) {
                horizontalWin = false;
                break;
            }
        }

        boolean verticalWin = true;
        for (int i = 0; i < 3; i++) {
            if (buttons[row][i].getText().isEmpty() || buttons[row][i].getText().charAt(0) != currentPlayer) {
                verticalWin = false;
                break;
            }
        }

        boolean diagonalWin = true;
        if (row == col || row + col == 2) {
            for (int i = 0; i < 3; i++) {
                if (buttons[i][i].getText().isEmpty() || buttons[i][i].getText().charAt(0) != currentPlayer) {
                    diagonalWin = false;
                    break;
                }
            }
            if (diagonalWin == false) {
                diagonalWin = true;
                for (int i = 0; i < 3; i++) {
                    if (buttons[i][2 - i].getText().isEmpty() || buttons[i][2 - i].getText().charAt(0) != currentPlayer) {
                        diagonalWin = false;
                        break;
                    }
                }
            }
        } else {
            diagonalWin = false;
        }

        if (horizontalWin || verticalWin || diagonalWin) {
            gameWon = true;
            winningPlayerName = getPlayerName(currentPlayer);
            JOptionPane.showMessageDialog(this, "Le joueur " + winningPlayerName + " gagne !");
        }

        if (!gameWon) {
            boolean draw = true;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().isEmpty()) {
                        draw = false;
                        break;
                    }
                }
            }

            if (draw) {
                int option = JOptionPane.showConfirmDialog(null, "Match nul ! Voulez-vous redémarrer la partie ?", "Match nul", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    restartGame();
                } else {
                    gameWon = true;
                }
            }
        }
    }

    private class ButtonClickListener implements ActionListener {

        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameWon && buttons[row][col].getText().equals("")) {
                buttons[row][col].setText(String.valueOf(currentPlayer));
                buttons[row][col].setForeground((currentPlayer == 'X') ? Color.RED : Color.BLUE);
                checkWin(row, col);
                if (gameWon) {
                    updateScore();
                    int option = JOptionPane.showConfirmDialog(null, "Voulez-vous redémarrer la partie ?", "Redémarrer la partie", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        restartGame();
                    }
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            }
        }
    }

    private void updateScore() {
        if (currentPlayer == 'X') {
            scoreX++;
        } else {
            scoreO++;
        }
        JOptionPane.showMessageDialog(this, "Le joueur " + winningPlayerName + " (" + getPlayerName(currentPlayer) + ") gagne !\nScore : " + playerXName + " - " + scoreX + ", " + playerOName + " - " + scoreO);
        winningPlayerName = ""; // Reset winning player name for the next game
    }

    private String getPlayerName(char player) {
        return (player == 'X') ? playerXName : playerOName;
    }

    private void restartGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setForeground(Color.BLACK);
            }
        }
        currentPlayer = 'X';
        gameWon = false;
    }

    public static void main(String[] args) {
        Morpion2 game = new Morpion2();
        game.setVisible(true);
    }
}
