package chess;

/**
 * Entry point for the Chess game application.
 *
 * This class contains the main method used to launch the program
 * via the terminal. It initializes and displays the main menu.
 */

public class Main {


    /**
     * The main method to start the Chess game.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {

        MainMenu menu = new MainMenu();
        menu.showMenu();
    }

}

