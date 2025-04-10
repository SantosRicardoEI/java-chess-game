package chess.pieces;

import chess.Board;
import java.util.Objects;

/**
 * Represents a Bishop chess piece.
 *
 * The Bishop moves diagonally across the board without jumping over other pieces.
 * This class checks for diagonal movement and path obstacles.
 */
public class Bishop extends Piece {

    /**
     * Constructs a Bishop with a specific color, position, and board reference.
     *
     * @param color The color of the bishop ("white" or "black").
     * @param line The starting row of the bishop.
     * @param column The starting column of the bishop.
     * @param board The board the bishop belongs to.
     */
    public Bishop(String color, int line, int column, Board board) {
        super(color, line, column, board);
    }

    /**
     * Checks if a move is valid for a bishop.
     * Validates that the movement is diagonal, the path is clear,
     * and the destination is either empty or occupied by an opponent.
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
        if (Math.abs(this.line - newLine) == Math.abs(this.column - newColumn)) {
            if (!board.hasObstacle(this.line, this.column, newLine, newColumn)) {
                if (!board.isOccupied(newLine, newColumn) || board.isOpponent(newLine, newColumn, this.color)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the string representation of the bishop using a Unicode symbol.
     *
     * @return A Unicode bishop symbol colored based on the piece's color.
     */
    @Override
    public String toString() {
        return (Objects.equals(this.color, "white") ? BLUE + "\u2657" : RED + "\u2657");
    }
}