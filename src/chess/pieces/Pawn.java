package chess.pieces;
import chess.Board;
import java.util.Objects;

/**
 * Represents the Pawn chess piece.
 *
 * Pawns move forward one square, with the option to move two squares
 * forward on their first move. They capture diagonally. This class
 * enforces all standard pawn movement rules.
 */
public class Pawn extends Piece {

    /**
     * Constructs a Pawn with the specified color, position, and board.
     *
     * @param color The color of the pawn ("white" or "black").
     * @param line The starting row.
     * @param column The starting column.
     * @param board The board to which the pawn belongs.
     */
    public Pawn(String color, int line, int column, Board board) {
        super(color, line, column,board);
    }

    /**
     * Validates the pawn's movement.
     * Pawns can move forward one square if unblocked, or two squares from their initial
     * position. They capture diagonally one square forward.
     *
     * @param newLine Destination row.
     * @param newColumn Destination column.
     * @return True if the movement is valid according to pawn rules, false otherwise.
     */
    @Override
    public boolean validMovement(int newLine, int newColumn) {
        if (newLine < 0 || newLine >= 8 || newColumn < 0 || newColumn >= 8) {
            return false;
        }
        int lineDiff = newLine - this.line;
        int columnDiff = Math.abs(this.column - newColumn);
        int direction = (Objects.equals(this.color, "white")) ? -1 : 1;
        int startRow = (Objects.equals(this.color, "white")) ? 6 : 1;

        if (lineDiff == direction && columnDiff == 0 && !board.isOccupied(newLine, newColumn)) {
            return true;
        }
        if (this.line == startRow && columnDiff == 0 && lineDiff == 2 * direction &&
                !board.isOccupied(newLine, newColumn) && !board.isOccupied(this.line + direction, this.column)) {
            return true;
        }

        if (lineDiff == direction && columnDiff == 1 && board.isOpponent(newLine, newColumn, this.color)) {
            return true;
        }

        return false;
    }

    /**
     * Returns the string representation of the pawn using a Unicode symbol.
     *
     * @return A colored Unicode pawn symbol depending on the piece color.
     */
    @Override
    public String toString() {
        return (Objects.equals(this.color, "white") ? BLUE + "\u2659" : RED + "\u2659");
    }
}