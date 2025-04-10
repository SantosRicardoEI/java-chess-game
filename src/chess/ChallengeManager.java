package chess;

import chess.pieces.*;
import java.util.Scanner;

/**
 * Manages the chess challenge scenarios available to the player.
 *
 * This class provides a text-based menu where users can select predefined
 * board challenges for training or testing. Each scenario sets up the board
 * with specific piece arrangements.
 */
public class ChallengeManager {
    private Scanner scanner;

    /**
     * Displays the challenge selection menu and initializes the chosen board scenario.
     *
     * @return A Board configured for the selected challenge, or null to return to the main menu.
     */
    public Board challengeMenu() {
        scanner = new Scanner(System.in);

        System.out.println("\n======================================");
        System.out.println("         🌟 CHESS CHALLENGES 🌟");
        System.out.println("======================================");
        System.out.println(" 1️⃣  Challenge 1");
        System.out.println(" 2️⃣  Challenge 2");
        System.out.println(" 3️⃣  Challenge 3");
        System.out.println(" 4️⃣  Challenge 4");
        System.out.println(" 5️⃣  Challenge 5");
        System.out.println(" 6️⃣  Challenge 6");
        System.out.println(" 7️⃣  Challenge 7");
        System.out.println(" 8️⃣  Challenge 8");
        System.out.println(" 9️⃣  Challenge 9");
        System.out.println(" 🔟  Challenge 10");
        System.out.println(" 0️⃣  Return to Main Menu");
        System.out.println("======================================");
        System.out.print(" ▶ Select a challenge (1-10) or 0 to return: ");

        String choice = scanner.nextLine().trim();

        Board board = new Board();
        board.clearBoard();

        switch (choice) {
            case "1":
                initializeCheckmateScenario(board);
                break;
            case "2":
                initializeEndgameScenario(board);
                break;
            case "3":
                initializePuzzle1(board);
                break;
            case "4":
                break;
            case "5":
                break;
            case "6":
                break;
            case "7":
                break;
            case "8":
                break;
            case "9":
                break;
            case "10":
                break;
            case "0":
                return null;
            default:
                System.out.println("\nInvalid option! Please try again.");
                return challengeMenu();
        }

        return board;
    }

    /**
     * Initializes a board scenario where the white king is in checkmate.
     *
     * @param board The board to be configured for this scenario.
     */
    private static void initializeCheckmateScenario(Board board) {
        board.setPiece(7, 0, new King("white", 7, 0, board));
        board.setPiece(4, 2, new Queen("black", 4, 2, board));
        board.setPiece(6, 2, new Rook("black", 6, 2, board));
        board.setPiece(5, 2, new Rook("black", 5, 2, board));

    }

    /**
     * Initializes a simplified endgame scenario with kings and a white queen.
     *
     * @param board The board to be configured for this scenario.
     */
    private static void initializeEndgameScenario(Board board) {
        board.setPiece(7, 4, new King("white", 7, 4, board));
        board.setPiece(0, 4, new King("black", 0, 4, board));
        board.setPiece(3, 3, new Queen("white", 3, 3, board));

    }

    /**
     * Initializes a custom puzzle scenario for training purposes.
     *
     * @param board The board to be configured for this scenario.
     */
    private static void initializePuzzle1(Board board) {
        board.setPiece(7, 4, new King("white", 7, 4, board));
        board.setPiece(0, 4, new King("black", 0, 4, board));
        board.setPiece(5, 5, new Rook("white", 5, 5, board));
        board.setPiece(1, 3, new Pawn("black", 1, 3, board));

    }
}