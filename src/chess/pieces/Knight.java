package chess.pieces;

import chess.Board;
import java.util.Objects;

/**
 * Represents the Knight chess piece.
 *
 * The Knight moves in an L-shape: two squares in one direction and
 * then one square perpendicular to that. It can jump over other pieces.
 */
public class Knight extends Piece {

    /**
     * Constructs a Knight with the specified color, position, and board.
     *
     * @param color The color of the knight ("white" or "black").
     * @param line The initial row position.
     * @param column The initial column position.
     * @param board The board the knight belongs to.
     */
    public Knight(String color, int line, int column, Board board) {
        super(color, line, column, board);
    }

    /**
     * Validates the movement of the knight.
     * The knight moves in an L-shape and can jump over other pieces.
     *
     * @param newLine The target row.
     * @param newColumn The target column.
     * @return True if the move is valid, false otherwise.
     */
    @Override
    public boolean validMovement(int newLine, int newColumn) {
        if (newLine < 0 || newLine >= 8 || newColumn < 0 || newColumn >= 8) {
            return false;
        }
        int lineDiff = Math.abs(this.line - newLine);
        int columnDiff = Math.abs(this.column - newColumn);

        if ((lineDiff == 2 && columnDiff == 1) || (lineDiff == 1 && columnDiff == 2)) {
            if (!board.isOccupied(newLine, newColumn) || board.isOpponent(newLine, newColumn, this.color)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the knight using a Unicode symbol.
     *
     * @return A colored Unicode knight symbol based on the piece's color.
     */
    @Override
    public String toString() {
        return (Objects.equals(this.color, "white") ? BLUE + "\u2658" : RED + "\u2658");
    }
}