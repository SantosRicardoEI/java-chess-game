package chess.pieces;
import chess.Board;

/**
 * Abstract base class for chess pieces.
 *
 * This class defines common attributes and methods shared by all pieces,
 * including position, color, and the game board reference. Subclasses must
 * implement the movement validation logic.
 */
public abstract class Piece {

    final String RED = "\u001B[31m";
    final String BLUE = "\u001B[34m";

    protected String color;
    protected int line;
    protected int column;
    protected Board board;

    /**
     * Constructs a Piece with specified color, position, and board.
     *
     * @param color The color of the piece ("white" or "black").
     * @param line The row position of the piece.
     * @param column The column position of the piece.
     * @param board The board the piece belongs to.
     */
    public Piece(String color, int line, int column,Board board) {
        this.color = color;
        this.line = line;
        this.column = column;
        this.board = board;
    }

    public String getColor() {
        return color;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    /**
     * Updates the position of the piece on the board.
     *
     * @param line New row position.
     * @param column New column position.
     */
    public void setPosition(int line, int column) {
        this.line = line;
        this.column = column;
    }

    /**
     * Checks if a move to the specified position is valid for the piece.
     * The actual validation is implemented by each specific piece subclass.
     *
     * @param novaLinha Destination row.
     * @param novaColuna Destination column.
     * @return True if the move is valid, false otherwise.
     */
    public abstract boolean validMovement(int novaLinha, int novaColuna);
}