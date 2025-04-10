package chess;

import chess.pieces.*;
import chess.gameUtils.MoveValidator;
import java.util.Scanner;
import static chess.MainMenu.clearConsole;

/**
 * Represents a chess game session, managing game flow, player turns,
 * and validation of moves.
 *
 * This class handles the main game loop, including checkmate and stalemate
 * conditions, and allows evaluation of board state for advantage.
 */
public class Game {

    private Board board;
    private MoveValidator moveValidator;
    private Scanner scanner;
    public boolean isWhiteTurn;
    private boolean gameOver;

    /**
     * Constructs a Game instance with a specified board.
     *
     * @param board The board to use in the game session.
     */
    public Game(Board board) {
        this.board = board;
        this.moveValidator = new MoveValidator(board);
        scanner = new Scanner(System.in);
        isWhiteTurn = true;
        gameOver = false;
    }

    /**
     * Constructs a Game instance with a new board.
     */
    public Game() {
        board = new Board();
        moveValidator = new MoveValidator(board);
        scanner = new Scanner(System.in);
        isWhiteTurn = true;
        gameOver = false;
    }

    /**
     * Starts the game loop, handling user input and executing turns until the game ends.
     */
    public void start() {
        while (!gameOver) {
            String currentPlayer = isWhiteTurn ? "White" : "Black";

            if (isCheckmate()) {
                System.out.println("Checkmate! " + currentPlayer + " wins!");
                gameOver = true;
                break;
            }

            else if (isStalemate()) {
                System.out.println("Stalemate!");
                gameOver = true;
                break;
            }

            board.printBoard();
            System.out.println("Material advantage: " + evaluate(board));

            System.out.println("\n" + currentPlayer + "'s turn.");
            System.out.print("Enter your move (e.g., e2 e4): ");
            String input = scanner.nextLine().trim().toLowerCase();
            clearConsole();

            if (input.equals("exit")) {
                gameOver = true;
                break;
            }

            if (!moveValidator.processMove(input, isWhiteTurn)) {
                continue;
            }

            isWhiteTurn = !isWhiteTurn;
        }
    }

    /**
     * Returns the current game board.
     *
     * @return The Board object representing the current state.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Checks whether the current game state is a stalemate.
     *
     * @return True if stalemate conditions are met, false otherwise.
     */
    public boolean isStalemate() {
        String currentColor = isWhiteTurn ? "white" : "black";

        if (!moveValidator.isKingInCheck(currentColor) && !moveValidator.canEscapeCheck(currentColor)) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the current player is in checkmate.
     *
     * @return True if checkmate conditions are met, false otherwise.
     */
    public boolean isCheckmate() {
        String currentColor = isWhiteTurn ? "white" : "black";
        if (!moveValidator.isKingInCheck(currentColor)) {
            return false;
        }
        if (moveValidator.canEscapeCheck(currentColor)) {
            return false;
        }

        return true;
    }

    private int getPieceValue(Piece piece) {
        switch (piece.getClass().getSimpleName()) {
            case "Pawn": return 100;
            case "Knight": return 320;
            case "Bishop": return 330;
            case "Rook": return 500;
            case "Queen": return 900;
            case "King": return 20000;
            default: return 0;
        }
    }

    /**
     * Evaluates the current board and returns a score indicating material advantage.
     * Positive scores favor white; negative scores favor black.
     *
     * This method is currently used to display material advantage to the player,
     * but will later be used as part of the decision-making logic for a computer opponent.
     * Currently, the game only supports human vs. human play.
     *
     * @param board The current board state to evaluate.
     * @return Integer score of the board position.
     */
    public int evaluate(Board board) {
        int score = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    int pieceValue = getPieceValue(piece);
                    score += piece.getColor().equals("white") ? pieceValue : -pieceValue;
                }
            }
        }

        return score;
    }

    /**
     * Indicates whether it is white's turn.
     *
     * @return True if it is white's turn, false otherwise.
     */
    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    /**
     * Switches the current turn to the other player.
     */
    public void switchTurn() {
        isWhiteTurn = !isWhiteTurn;
    }
}