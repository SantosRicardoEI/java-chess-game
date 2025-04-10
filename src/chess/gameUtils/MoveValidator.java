package chess.gameUtils;

import chess.Board;
import chess.pieces.*;
import chess.pieces.Piece;

/**
 * Validates chess moves and enforces rules regarding legal movement,
 * check conditions, and turn-based logic.
 *
 * This class is responsible for determining if moves are valid, whether
 * a king is in check, and if a player can escape check. It will also
 * support future rule enforcement for AI decision-making.
 */
public class MoveValidator {
    private Board board;

    public MoveValidator(Board board) {
        this.board = board;
    }

    /**
     * Determines if the king of the specified color is currently in check.
     *
     * @param color The color of the king ("white" or "black").
     * @return True if the king is in check, false otherwise.
     */
    public boolean isKingInCheck(String color) {
        int kingRow = -1, kingCol = -1;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece instanceof King && piece.getColor().equals(color)) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        if (kingRow == -1 || kingCol == -1) {
            return false;
        }

        String opponentColor = color.equals("white") ? "black" : "white";


        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null && piece.getColor().equals(opponentColor)) {

                    if (piece.validMovement(kingRow, kingCol)) {
                        if (!(piece instanceof Knight) && board.hasObstacle(row, col, kingRow, kingCol)) {
                            continue;
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Simulates a move and checks whether it leaves the current player's king in check.
     *
     * @param piece The piece to move.
     * @param newRow The destination row.
     * @param newCol The destination column.
     * @return True if the move would leave the king in check, false otherwise.
     */
    public boolean moveLeavesKingInCheck(Piece piece, int newRow, int newCol) {
        int oldRow = piece.getLine();
        int oldCol = piece.getColumn();
        Piece capturedPiece = board.getPiece(newRow, newCol);

        board.setPiece(newRow, newCol, piece);
        board.setPiece(oldRow, oldCol, null);
        piece.setPosition(newRow, newCol);

        boolean kingInCheck = isKingInCheck(piece.getColor());

        board.setPiece(oldRow, oldCol, piece);
        board.setPiece(newRow, newCol, capturedPiece);
        piece.setPosition(oldRow, oldCol);

        return kingInCheck;
    }

    /**
     * Checks whether the player of the given color has any legal moves
     * to escape check.
     *
     * @param color The color of the player to test.
     * @return True if the player can escape check, false if checkmate.
     */
    public boolean canEscapeCheck(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);

                if (piece != null && piece.getColor().equals(color)) {

                    for (int newRow = 0; newRow < 8; newRow++) {
                        for (int newCol = 0; newCol < 8; newCol++) {

                            if (!piece.validMovement(newRow, newCol)) {
                                continue;
                            }

                            Piece capturedPiece = board.getPiece(newRow, newCol);
                            board.setPiece(newRow, newCol, piece);
                            board.setPiece(row, col, null);
                            piece.setPosition(newRow, newCol);

                            boolean stillInCheck = isKingInCheck(color);

                            board.setPiece(row, col, piece);
                            board.setPiece(newRow, newCol, capturedPiece);
                            piece.setPosition(row, col);

                            if (!stillInCheck) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Processes and validates a move entered by the player.
     * Verifies the move format, the piece color, legality of movement,
     * and whether the move puts the king in check.
     *
     * @param input The move command in algebraic notation (e.g., "e2 e4").
     * @param isWhiteTurn True if it's white's turn, false if black's.
     * @return True if the move is valid and executed, false otherwise.
     */
    public boolean processMove(String input, boolean isWhiteTurn) {
        if (!input.matches("[a-h][1-8] [a-h][1-8]")) {
            System.out.println("Invalid input format. Please use notation like \"e2 e4\".");
            return false;
        }

        String[] parts = input.split(" ");
        String start = parts[0];
        String end = parts[1];

        int startRow = 8 - Character.getNumericValue(start.charAt(1));
        int startCol = start.charAt(0) - 'a';
        int endRow = 8 - Character.getNumericValue(end.charAt(1));
        int endCol = end.charAt(0) - 'a';

        Piece piece = board.getPiece(startRow, startCol);

        if (piece == null) {
            System.out.println("No piece found at the selected position.");
            return false;
        }

        if ((isWhiteTurn && !piece.getColor().equals("white")) || (!isWhiteTurn && !piece.getColor().equals("black"))) {
            System.out.println("It's the other player's turn.");
            return false;
        }

        if (!piece.validMovement(endRow, endCol)) {
            System.out.println("Invalid move!");
            return false;
        }

        if (moveLeavesKingInCheck(piece, endRow, endCol)) {
            System.out.println("Illegal move: this would put your king in check.");
            return false;
        }

        boolean moveSuccessful = board.movePiece(startRow, startCol, endRow, endCol);
        if (!moveSuccessful) {
            System.out.println("Invalid move!");
            return false;
        }

        String opponentColor = isWhiteTurn ? "black" : "white";
        if (isKingInCheck(opponentColor)) {
            System.out.println("The " + (isWhiteTurn ? "Black" : "White") + " king is in check!");
        }

        return true;
    }
}