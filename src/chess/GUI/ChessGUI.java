package chess.GUI;

import chess.Board;
import chess.Game;
import chess.gameUtils.MoveValidator;
import chess.pieces.Piece;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Graphical User Interface for the Chess game using Java Swing.
 *
 * This class displays the chess board, handles player interactions,
 * processes move validation, updates game state, and provides visual
 * feedback for moves and game results.
 */
public class ChessGUI extends JFrame {
    private Board board;
    private JButton[][] buttons;
    private Game game;
    private int selectedRow = -1, selectedCol = -1;
    private JLabel statusLabel;
    private JTextArea consoleOutput;
    private MoveValidator moveValidator;


    /**
     * Constructs the Chess GUI window, initializes the board UI and game logic,
     * and sets up user interface components and event listeners.
     */
    public ChessGUI() {
        game = new Game();
        board = game.getBoard();
        this.moveValidator = new MoveValidator(board);

        setTitle("Chess Game");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel statusPanel = new JPanel();
        statusLabel = new JLabel("Current turn: White");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        buttons = new JButton[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.PLAIN, 30));
                button.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
                button.addActionListener(new ButtonClickListener(row, col));
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }


        add(boardPanel, BorderLayout.CENTER);

        consoleOutput = new JTextArea(5, 50);
        consoleOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(consoleOutput);
        add(scrollPane, BorderLayout.SOUTH);

        updateBoard();
        setVisible(true);
    }

    /**
     * Updates the visual representation of the board by iterating over each square
     * and setting the appropriate Unicode symbol for the current piece.
     */
    private void updateBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                buttons[row][col].setText(piece != null ? getPieceSymbol(piece) : "");
            }
        }
        repaint();
        revalidate();
    }

    /**
     * Returns the Unicode character representing a chess piece.
     *
     * @param piece The piece to convert to a symbol.
     * @return A string with the appropriate Unicode character for the piece.
     */
    private String getPieceSymbol(Piece piece) {
        switch (piece.getClass().getSimpleName()) {
            case "Pawn": return piece.getColor().equals("white") ? "\u2659" : "\u265F";
            case "Rook": return piece.getColor().equals("white") ? "\u2656" : "\u265C";
            case "Knight": return piece.getColor().equals("white") ? "\u2658" : "\u265E";
            case "Bishop": return piece.getColor().equals("white") ? "\u2657" : "\u265D";
            case "Queen": return piece.getColor().equals("white") ? "\u2655" : "\u265B";
            case "King": return piece.getColor().equals("white") ? "\u2654" : "\u265A";
            default: return "";
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedRow == -1 && selectedCol == -1) {
                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    selectedRow = row;
                    selectedCol = col;
                    buttons[row][col].setBackground(Color.YELLOW);
                }
            } else {
                String move = convertToChessNotation(selectedRow, selectedCol) + " " + convertToChessNotation(row, col);
                if (moveValidator.processMove(move, game.isWhiteTurn)) {
                    game.switchTurn();
                    updateStatusLabel();
                    logMessage("Move completed: " + move);
                } else {
                    logMessage("Invalid move: " + move);
                }
                selectedRow = -1;
                selectedCol = -1;
                resetButtonColors();
                updateBoard();

                if (game.isCheckmate()) {
                    logMessage("Checkmate! " + (game.isWhiteTurn() ? "Black" : "White") + " wins!");
                } else if (game.isStalemate()) {
                    logMessage("Draw by Stalemate!");
                }
            }
        }
    }

    /**
     * Updates the status label at the top of the GUI to reflect the current player's turn.
     */
    private void updateStatusLabel() {
        String player = game.isWhiteTurn() ? "White" : "Black";
        statusLabel.setText("Current turn: " + player);
    }

    /**
     * Appends a message to the console output area and scrolls to the bottom.
     *
     * @param message The message to display.
     */
    private void logMessage(String message) {
        consoleOutput.append(message + "\n");
        consoleOutput.setCaretPosition(consoleOutput.getDocument().getLength());
    }

    /**
     * Converts board coordinates to standard chess algebraic notation.
     *
     * @param row The row index (0-based).
     * @param col The column index (0-based).
     * @return A string representing the square in chess notation (e.g., "e4").
     */
    private String convertToChessNotation(int row, int col) {
        return (char) ('a' + col) + "" + (8 - row);
    }

    /**
     * Resets all board buttons to their original alternating background colors.
     */
    private void resetButtonColors() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
            }
        }
    }

    /**
     * Launches the Chess GUI application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGUI::new);
    }
}