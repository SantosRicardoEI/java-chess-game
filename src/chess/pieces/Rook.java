package chess.pieces;

import chess.Board;
import java.util.Objects;

/**
 * Represents a Rook chess piece.
 *
 * The Rook moves in straight lines vertically or horizontally,
 * without jumping over pieces. This class validates rook movement
 * based on those rules.
 */
public class Rook extends Piece {

    /**
     * Constructs a Rook with the specified color, position, and board.
     *
     * @param color The color of the piece ("white" or "black").
     * @param line The initial row position.
     * @param column The initial column position.
     * @param board The game board the piece belongs to.
     */
    public Rook(String color, int line, int column, Board board) {
        super(color, line, column, board);
    }

    /**
     * Validates the movement of the rook.
     * The move is valid if it is in a straight line (horizontal or vertical),
     * the path is not blocked, and the destination is either empty or occupied by an opponent.
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
        if (this.line == newLine || this.column == newColumn) {
            if (!board.hasObstacle(this.line, this.column, newLine, newColumn)) {
                if (!board.isOccupied(newLine, newColumn) || board.isOpponent(newLine, newColumn, this.color)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the rook using a Unicode symbol.
     *
     * @return A string containing the Unicode rook symbol, colored by piece.
     */
    @Override
    public String toString() {
        return (Objects.equals(this.color, "white") ? BLUE + "\u2656" : RED +"\u2656");
    }
}