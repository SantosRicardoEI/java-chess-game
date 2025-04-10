package chess;
import java.util.Scanner;

/**
 * Main menu interface for the Chess game application.
 *
 * This class handles user interaction through a text-based menu,
 * allowing the player to start a new game, access challenges,
 * or exit the application.
 */

public class MainMenu {
    private Scanner scanner;
    private ChallengeManager challengeManager;

    /**
     * Constructs the MainMenu and initializes the scanner and challenge manager.
     */
    public MainMenu() {
        scanner = new Scanner(System.in);
        challengeManager = new ChallengeManager();
    }

    /**
     * Clears the console by printing several new lines.
     * This is a simple workaround to simulate a clear screen in the terminal.
     */
    public static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    /**
     * Displays the main menu and handles user input to navigate options.
     */
    public void showMenu() {
        boolean showMenu = true;
        while (true) {
            if (showMenu) {
                clearConsole();
                System.out.println("\n======================================");
                System.out.println("            MAIN MENU ");
                System.out.println("======================================");
                System.out.println(" 1️⃣  Start New Game");
                System.out.println(" 2️⃣  Challenges");
                System.out.println(" 3️⃣  Exit");
                System.out.println("======================================");
                System.out.print(" ▶ Select an option: ");
            }
            showMenu= false;
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    clearConsole();
                    startGame(new Board());
                    showMenu = true;
                    break;
                case "2":
                    clearConsole();
                    Board challengeBoard = challengeManager.challengeMenu();
                    if (challengeBoard == null) {
                        break;
                    }
                    startGame(challengeBoard);
                    showMenu = true;
                    break;
                case "3":
                    break;
                case "4":
                    System.out.println("See you next time!");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    /**
     * Starts a new game session with the given board.
     *
     * @param board The game board to use for the session.
     */
    private void startGame(Board board) {
        Game game = new Game(board);
        game.start();
    }

    /**
     * Displays the rules of the chess game.
     * Waits for user confirmation to return to the menu.
     */
    private void showRules() {
        System.out.println("\n=== GAME RULES ===");
        System.out.println("1. The game follows the standard chess rules.");
        System.out.println("2. White moves first.");
        System.out.println("3. To make a move, type it in the format \"e2 e4\".");
        System.out.println("4. The game ends with checkmate, stalemate, or resignation.");
        System.out.println("5. Type \"exit\" during a game to quit.");
        System.out.println("\nPress ENTER to return to the menu...");
        scanner.nextLine();
    }
}
