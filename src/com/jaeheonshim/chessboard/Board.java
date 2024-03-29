package com.jaeheonshim.chessboard;

import com.jaeheonshim.chessboard.piece.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the chessboard and methods for manipulating entities of the board
 *
 * @author jaeheonshim
 */
public class Board implements Serializable {
    private Spot[][] board = new Spot[8][8];
    private List<Move> moves = new ArrayList<>();

    private int halfmoves_count = 0;

    public Board() {
        resetBoard();
    }

    /**
     * Resets the board to the state of a classic chess game
     */
    public void resetBoard() {
        Spot[][] blankBoard = new Spot[8][8];
        for (int i = 0; i < blankBoard.length; i++) {
            for (int j = 0; j < blankBoard.length; j++) {
                blankBoard[i][j] = new Spot(j, i, null);
            }
        }

        setBoard(blankBoard);

        setBoard(true);
        setBoard(false);

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Spot(j, i, null);
            }
        }

        moves = new ArrayList<>();
    }
    /**
     * Helper method for setting the board to a classic chess state
     *
     * @param white Whether you are setting white pieces
     */
    private void setBoard(boolean white) {
        int pawnRow = white ? 1 : 6;
        int pieceRow = white ? 0 : 7;

        for (int i = 0; i < 8; i++) {
            board[pawnRow][i] = new Spot(i, pawnRow, new Pawn(white));
        }

        board[pieceRow][0] = new Spot(0, pieceRow, new Rook(white));
        board[pieceRow][7] = new Spot(7, pieceRow, new Rook(white));

        board[pieceRow][1] = new Spot(1, pieceRow, new Knight(white));
        board[pieceRow][6] = new Spot(6, pieceRow, new Knight(white));

        board[pieceRow][2] = new Spot(2, pieceRow, new Bishop(white));
        board[pieceRow][5] = new Spot(5, pieceRow, new Bishop(white));

        board[pieceRow][3] = new Spot(3, pieceRow, new Queen(white));
        board[pieceRow][4] = new Spot(4, pieceRow, new King(white));
    }

    /**
     * Returns a Spot on the board based on given coordinates
     * (0, 0) is defined as the bottom left corner
     *
     * @param x x coordinate of spot
     * @param y y coordinate of spot
     * @return Spot at x and y coordinate of board.
     */
    public Spot getSpot(int x, int y) {
        try {
            return board[y][x];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Spot out of bounds", e);
        }
    }

    /**
     * Returns a spot given a Square enumeration type
     *
     * @param square Square to return
     * @return Spot retrieved from board using square parameter
     */
    public Spot getSpot(Square square) {
        return board[square.getY()][square.getX()];
    }

    /**
     * Returns the king from the board with the specified color. Returns null if no king is available.
     *
     * @param white Whether to return the white king
     * @return Piece King from the Board.
     */
    public King getKing(boolean white) {
        for (Spot[] spots : board) {
            for (Spot spot : spots) {
                if (spot.getPiece() instanceof King && spot.getPiece().isWhite() == white) {
                    return (King) spot.getPiece();
                }
            }
        }

        return null;
    }

    /**
     * Sets the current state of the board from a two-dimensional array of type Spot.
     *
     * @param board Two-dimensional array of spot with pieces.
     */
    public void setBoard(Spot[][] board) {
        this.board = board;
    }

    /**
     * Performs a piece move on the current board, if that move is allowed.
     *
     * @param begin Spot in board of the piece to move
     * @param end   Spot to attempt moving piece to
     * @return returns true if move is valid and move has been executed, false if move is invalid.
     */
    public boolean move(Spot begin, Spot end) {
        return move(begin, end, false);
    }
    private boolean move(Spot begin, Spot end, boolean ignoreTurn) {
        if((begin.getPiece() instanceof EnPassant)){
            return false;
        }
        Move move = new Move(begin, end, isWhiteTurn());
        if (!ignoreTurn && begin.getPiece().isWhite() != isWhiteTurn()) {
            return false;
        }

        if (begin.getPiece() instanceof King) {
            if(end.getY()==begin.getY() && end.getX()==2){
                if(((King) begin.getPiece()).canCastleQueenside(this))  castle(begin, end);
                halfmoves_count++;
                moves.add(move);
                updateEnPassant();
                return true;
            }
            if(end.getY()==begin.getY() && end.getX()==6){
                if(((King) begin.getPiece()).canCastleKingside(this))  castle(begin, end);
                halfmoves_count++;
                moves.add(move);
                updateEnPassant();
                return true;
            }
        }
        if (begin.getPiece() != null && begin.getPiece().canMove(this, begin, end)) {
            boolean halfmove_flag = begin.getPiece() instanceof Pawn;
            if (end.getPiece() != null) {
                if(end.getPiece() instanceof EnPassant && end.getPiece().isWhite() != begin.getPiece().isWhite()){
                    ((EnPassant) end.getPiece()).getParentPawn().getPiece().setKilled(true);
                    ((EnPassant) end.getPiece()).getParentPawn().setPiece(null);
                }else {
                    end.getPiece().setKilled(true);
                    end.setPiece(null);
                }
                halfmove_flag = true;
            }
            end.setPiece(begin.getPiece());
            begin.getPiece().setMoved();
            begin.setPiece(null);

            if ((end.getPiece() instanceof Pawn) && (end.getPiece().isWhite() ? end.getY() == 7 : end.getY() == 0)) {
                end.setPiece(new Queen(end.getPiece().isWhite(), true));
            }
            if (end.getPiece() instanceof Pawn && Math.abs(end.getY()-begin.getY()) == 2){
                this.getSpot(begin.getX(), end.getY()+(end.getPiece().isWhite()?-1:1)).setPiece(new EnPassant(end));
            }

            if(halfmove_flag){
                halfmoves_count = 0;
            }else {
                halfmoves_count++;
            }

            moves.add(move);
            updateEnPassant();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Performs a piece move on the current board, if that move is allowed.
     *
     * @param begin Square piece to move is located
     * @param end   Square to move piece to
     * @return returns true if move is valid and move has been executed, false if move is invalid.
     */
    public boolean move(Square begin, Square end) {
        if (this.getSpot(begin.getX(), begin.getY()).getPiece() != null) {
            return this.move(this.getSpot(begin.getX(), begin.getY()), this.getSpot(end.getX(), end.getY()));
        } else {
            return false;
        }
    }

    /**
     * Check if a piece can be moved, without actually making the move.
     *
     * @param begin Spot containing piece to move
     * @param end   Spot containing destination
     * @return returns true if move is valid, false if move is invalid.
     */
    public boolean canMove(Spot begin, Spot end) {
        if (begin.getPiece() != null) {
            return begin.getPiece().canMove(this, begin, end);
        } else {
            return false;
        }
    }

    /**
     * Check if a piece can be moved, without actually making the move.
     *
     * @param begin Square containing piece to move
     * @param end   Square containing destination
     * @return returns true if move is valid, false if move is invalid.
     */
    public boolean canMove(Square begin, Square end) {
        if (this.getSpot(begin.getX(), begin.getY()).getPiece() != null) {
            return this.canMove(this.getSpot(begin.getX(), begin.getY()), this.getSpot(end.getX(), end.getY()));
        } else {
            return false;
        }
    }

    /**
     * Checks if either king on the board is in checkmate
     *
     * @return true if a king is in checkmate, false if king is not in checkmate.
     */
    public boolean kingInCheckmate() {
        return getKing(true).inCheckmate(this) ^ getKing(false).inCheckmate(this);
    }

    /**
     * Get a visual string representation of the board in its current state
     *
     * @return String visualizing the board. White pieces are capitalized and black pieces are lowercase.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getPiece() != null) {
                    stringBuilder.append(board[i][j].getPiece().toString());
                } else {
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    /**
     * Removes all pieces from the board
     */
    public void clear() {
        Arrays.asList(board).forEach(m -> Arrays.asList(m).forEach(e -> e.setPiece(null)));
    }

    /**
     * Returns the board as a two-dimensional array of Spots
     *
     * @return Two dimensional array of type Spot
     */
    public Spot[][] getBoard() {
        return board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.move(Square.A2, Square.A4);
        board.move(Square.A7, Square.A6);

        System.out.println(board.getFenRecord());
    }

    public String getFenRecord() {
        StringBuilder fenBuilder = new StringBuilder();
        fenBuilder.append(getFenPiecePlacement());
        fenBuilder.append(" ");
        fenBuilder.append(isWhiteTurn() ? "w" : "b");
        fenBuilder.append(" ");

        boolean canCastle = false;
        if (
                getSpot(4, 0).getPiece() != null &&
                        getSpot(7, 0).getPiece() != null &&
                        getSpot(0, 0).getPiece() != null &&
                        getSpot(4, 7).getPiece() != null &&
                        getSpot(7, 7).getPiece() != null &&
                        getSpot(0, 7).getPiece() != null
        ){
            if (! getSpot(4, 0).getPiece().isMoved() && ! getSpot(7, 0).getPiece().isMoved()) {
                canCastle = true;
                fenBuilder.append("K");
            }
            if (! getSpot(4, 0).getPiece().isMoved() && ! getSpot(0, 0).getPiece().isMoved()) {
                canCastle = true;
                fenBuilder.append("Q");
            }
            if (! getSpot(4, 7).getPiece().isMoved() && ! getSpot(7, 7).getPiece().isMoved()) {
                canCastle = true;
                fenBuilder.append("k");
            }
            if (! getSpot(4, 7).getPiece().isMoved() && ! getSpot(0, 7).getPiece().isMoved()) {
                canCastle = true;
                fenBuilder.append("q");
            }
        }

        if(!canCastle) {
            fenBuilder.append("-");
        }

        fenBuilder.append(" ");
        fenBuilder.append(getFenEnPassant());
        fenBuilder.append(" ");
        fenBuilder.append(halfmoves_count);
        fenBuilder.append(" ");
        fenBuilder.append(moves.size() / 2 + 1);


        return fenBuilder.toString();
    }

    public boolean isWhiteTurn() {
        if (moves.size() == 0) {
            return true;
        }

        int whiteMoves = 0;
        int blackMoves = 0;

        for (Move move : moves) {
            if (move.isWhiteMove()) {
                whiteMoves++;
            } else {
                blackMoves++;
            }
        }

        return whiteMoves <= blackMoves;
    }

    private String getFenPiecePlacement() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 7; i >= 0; i--) {
            stringBuilder.append(getRowAsString(i));
            if (i > 0) {
                stringBuilder.append("/");
            }
        }

        return stringBuilder.toString();
    }
    private String getRowAsString(int row) {
        StringBuilder sb = new StringBuilder(8);
        int blankCount = 0;
        for (int i = 0; i < board[row].length; i++) {
            Piece piece = board[row][i].getPiece();
            if (piece == null || (piece instanceof EnPassant)) {
                blankCount++;
            } else if (blankCount > 0) {
                sb.append(blankCount+piece.toString());
                blankCount = 0;
            } else {
                sb.append(piece);
            }

            if (i + 1 == board[row].length && blankCount > 0) {
                sb.append(blankCount);
            }
        }

        return sb.toString();
    }

    private void castle(Spot start, Spot end){
        if(end.getX()==2){
            getSpot(end.getX(), end.getY()).setPiece(start.getPiece());
            getSpot(3, end.getY()).setPiece(getSpot(0, end.getY()).getPiece());
            getSpot(0, end.getY()).setPiece(null);
            getSpot(start.getX(), start.getY()).setPiece(null);
        }
        if(end.getX()==6){
            getSpot(end.getX(), end.getY()).setPiece(start.getPiece());
            getSpot(5, end.getY()).setPiece(getSpot(7, end.getY()).getPiece());
            getSpot(7, end.getY()).setPiece(null);
            getSpot(start.getX(), start.getY()).setPiece(null);
        }
    }

    private void updateEnPassant(){
        for(Spot[] spots:this.getBoard()){
            for(Spot spot:spots){
                if((spot.getPiece() instanceof EnPassant) && ((EnPassant) spot.getPiece()).getLiveTimer() == 0){
                    spot.setPiece(null);
                }
            }
        }
    }
    private String getFenEnPassant(){
        String out = "-";
        for(Spot[] spots:getBoard()){
            for(Spot spot:spots){
                if(spot.getPiece() instanceof EnPassant){
                    switch (spot.getX()){
                        case 0:
                            out = "a";
                            break;
                        case 1:
                            out = "b";
                            break;
                        case 2:
                            out = "c";
                            break;
                        case 3:
                            out = "d";
                            break;
                        case 4:
                            out = "e";
                            break;
                        case 5:
                            out = "f";
                            break;
                        case 6:
                            out = "g";
                            break;
                        case 7:
                            out = "h";
                            break;
                    }
                    switch (spot.getY()){
                        case 0:
                            out += "1";
                            break;
                        case 1:
                            out += "2";
                            break;
                        case 2:
                            out += "3";
                            break;
                        case 3:
                            out += "4";
                            break;
                        case 4:
                            out += "5";
                            break;
                        case 5:
                            out += "6";
                            break;
                        case 6:
                            out += "7";
                            break;
                        case 7:
                            out += "8";
                            break;
                    }
                    return out;
                }
            }
        }
        return out;
    }

    public int getHalfmovesCount () {
        return halfmoves_count;
    }
}
