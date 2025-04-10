package chess.pieces;

import chess.Board;
import java.util.Objects;

/**
 * Represents the Queen chess piece.
 *
 * The Queen can move any number of squares along rank, file, or diagonal,
 * combining the movement abilities of both the rook and bishop.
 * This class validates that the path is unobstructed and the move is legal.
 */
public class Queen extends Piece {

    /**
     * Constructs a Queen with the specified color, position, and board.
     *
     * @param color The color of the queen ("white" or "black").
     * @param line The starting row.
     * @param column The starting column.
     * @param board The board the queen belongs to.
     */
    public Queen(String color, int line, int column, Board board) {
        super(color, line, column, board);
    }

    /**
     * Validates the queen's movement.
     * The queen moves diagonally, vertically, or horizontally with no obstacles in between.
     *
     * @param newLine The target row.
     * @param newColumn The target column.
     * @return True if the movement is valid, false otherwise.
     */
    @Override
    public boolean validMovement(int newLine, int newColumn) {
        if (newLine < 0 || newLine >= 8 || newColumn < 0 || newColumn >= 8) {
            return false;
        }
        int lineDiff = Math.abs(this.line - newLine);
        int columnDiff = Math.abs(this.column - newColumn);

        if (lineDiff == columnDiff || this.line == newLine || this.column == newColumn) {
            if (!board.hasObstacle(this.line, this.column, newLine, newColumn)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the queen using a Unicode symbol.
     *
     * @return A Unicode queen symbol with color coding.
     */
    @Override
    public String toString() {
        return (Objects.equals(this.color, "white") ? BLUE +  "\u2655" : RED + "\u2655");
    }

    public static void main(String[] args) {

    }
}