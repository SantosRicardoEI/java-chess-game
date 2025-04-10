package chess;

import chess.pieces.*;

/**
 * Represents the chess board and provides methods to manage pieces,
 * validate positions, and simulate moves.
 *
 * This class supports board setup, move logic, obstacle detection,
 * and board visualization. It acts as the central reference for all
 * piece interactions during gameplay.
 */
public class Board {
    final String RESET = "\u001B[0m";

    private Piece[][] board;
    private int size = 8;

    public Board() {
        board = new Piece[size][size];
        initializeBoard();
    }

    /**
     * Initializes the board to the standard chess starting position.
     */
    public void initializeBoard() {
        // Pecas brancas
        board[0][0] = new Rook("black", 0, 0,this);
        board[0][1] = new Knight("black", 0, 1,this);
        board[0][2] = new Bishop("black", 0, 2,this);
        board[0][3] = new Queen("black", 0, 3,this);
        board[0][4] = new King("black", 0, 4,this);
        board[0][5] = new Bishop("black", 0, 5,this);
        board[0][6] = new Knight("black", 0, 6,this);
        board[0][7] = new Rook("black", 0, 7,this);
        for (int i = 0; i < size; i++) {
            board[1][i] = new Pawn("black", 1, i,this);
        }

        // Pecas pretas
        board[7][0] = new Rook("white", 7, 0,this);
        board[7][1] = new Knight("white", 7, 1,this);
        board[7][2] = new Bishop("white", 7, 2,this);
        board[7][3] = new Queen("white", 7, 3,this);
        board[7][4] = new King("white", 7, 4,this);
        board[7][5] = new Bishop("white", 7, 5,this);
        board[7][6] = new Knight("white", 7, 6,this);
        board[7][7] = new Rook("white", 7, 7,this);
        for (int i = 0; i < size; i++) {
            board[6][i] = new Pawn("white", 6, i,this);
        }
    }

    /**
     * Retrieves the piece located at the specified coordinates.
     *
     * @param line Row index of the piece.
     * @param column Column index of the piece.
     * @return The piece at the specified location, or null if none.
     */
    public Piece getPiece(int line, int column) {
        if (line < 0 || column < 0 || line >= size || column >= size) {
            return null;
        }
        return board[line][column];
    }

    /**
     * Checks whether a given board position is occupied by a piece.
     *
     * @param line Row index.
     * @param column Column index.
     * @return True if the position is occupied, false otherwise.
     */
    public boolean isOccupied(int line, int column) {
        return getPiece(line, column) != null;
    }

    /**
     * Determines if the piece at the given position belongs to the opponent.
     *
     * @param line Row index.
     * @param column Column index.
     * @param color The current player's color.
     * @return True if the piece is an opponent's, false otherwise.
     */
    public boolean isOpponent(int line, int column, String color) {
        Piece piece = getPiece(line, column);
        return piece != null && piece.getColor() != color;
    }

    /**
     * Moves a piece from one position to another, if the move is valid.
     * Checks movement rules and captures if applicable.
     *
     * @param startLine Starting row.
     * @param startColumn Starting column.
     * @param endLine Destination row.
     * @param endColumn Destination column.
     * @return True if the move was valid and executed, false otherwise.
     */
    public boolean movePiece(int startLine, int startColumn, int endLine, int endColumn) {
        Piece piece = getPiece(startLine, startColumn);

        if (piece == null || !piece.validMovement(endLine, endColumn)) {
            return false;
        }

        if (isOccupied(endLine, endColumn) && !isOpponent(endLine, endColumn, piece.getColor())) {
            return false;
        }

        board[startLine][startColumn] = null;
        board[endLine][endColumn] = piece;
        piece.setPosition(endLine, endColumn);
        return true;
    }

    /**
     * Checks for obstacles in a straight or diagonal path between two positions.
     *
     * @param startLine Starting row.
     * @param startColumn Starting column.
     * @param endLine Destination row.
     * @param endColumn Destination column.
     * @return True if any pieces block the path, false otherwise.
     */
    public boolean hasObstacle(int startLine, int startColumn, int endLine, int endColumn) {
        int lineStep;
        if (endLine > startLine) {
            lineStep = 1;  // Para baixo
        } else if (endLine < startLine) {
            lineStep = -1; // para cima
        } else {
            lineStep = 0;  // sem movimento
        }

        int columnStep;
        if (endColumn > startColumn) {
            columnStep = 1;  // para direita
        } else if (endColumn < startColumn) {
            columnStep = -1; // para esquerda
        } else {
            columnStep = 0;  // sem movimento
        }

        int currentLine = startLine + lineStep;
        int currentColumn = startColumn + columnStep;

        while (currentLine != endLine || currentColumn != endColumn) {
            if (isOccupied(currentLine, currentColumn)) {
                return true;
            }
            currentLine += lineStep;
            currentColumn += columnStep;
        }
        return false;
    }

    /**
     * Prints the current board state to the console using ASCII formatting.
     */
    public void printBoard() {
        System.out.println("\n" + RESET + "      a     b     c     d     e     f     g     h  ");
        System.out.println("    +-----+-----+-----+-----+-----+-----+-----+-----+");

        for (int i = 0; i < 8; i++) {
            System.out.print(" " + (8 - i) + "  |");

            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                System.out.print("  " + (piece != null ? piece.toString() : " ") + RESET + "  |");
            }

            System.out.println(RESET + "  " + (8 - i));
            System.out.println("    +-----+-----+-----+-----+-----+-----+-----+-----+");
        }

        System.out.println("      a     b     c     d     e     f     g     h  ");
    }

    /**
     * Places a piece on the board at the specified position.
     *
     * @param newRow Target row.
     * @param newCol Target column.
     * @param capturedPiece The piece to place (can be null to clear the square).
     */
    public void setPiece(int newRow, int newCol, Piece capturedPiece) {
        board[newRow][newCol] = capturedPiece;

        if (capturedPiece != null) {
            capturedPiece.setPosition(newRow, newCol);
        }
    }

    /**
     * Clears all pieces from the board, resetting it to an empty state.
     */
    public void clearBoard() {
        this.board = new Piece[size][size];
    }
}
