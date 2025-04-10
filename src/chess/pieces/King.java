package chess.pieces;

import chess.Board;
import java.util.Objects;

/**
 * Represents the King chess piece.
 *
 * The King moves one square in any direction unless blocked or captured.
 * This class checks valid movements and handles symbol representation.
 */
public class King extends Piece {

    /**
     * Constructs a King with the specified color, position, and board.
     *
     * @param color The color of the king ("white" or "black").
     * @param line The initial row position.
     * @param column The initial column position.
     * @param board The board the king belongs to.
     */
    public King(String color, int line, int column, Board board) {
        super(color, line, column, board);
    }

    /**
     * Validates the movement of the king.
     * The king can move one square in any direction, provided the move does not
     * stay in the same place, exceed the board limits, or move into an occupied
     * space by a friendly piece.
     *
     * @param newLine Destination row.
     * @param newColumn Destination column.
     * @return True if the move is valid, false otherwise.
     */
    @Override
    public boolean validMovement(int newLine, int newColumn) {
        if (newLine < 0 || newLine >= 8 || newColumn < 0 || newColumn >= 8) {
            return false;
        }
        int lineDiff = Math.abs(this.line - newLine);
        int columnDiff = Math.abs(this.column - newColumn);

        if ((lineDiff <= 1 && columnDiff <= 1) && !(lineDiff == 0 && columnDiff == 0)) {
            if (!board.isOccupied(newLine, newColumn) || board.isOpponent(newLine, newColumn, this.color)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the string representation of the king using a Unicode symbol.
     *
     * @return A string with the colored Unicode king symbol.
     */
    @Override
    public String toString() {
        return (Objects.equals(this.color, "white") ? BLUE + "\u2654" : RED + "\u2654");
    }

}