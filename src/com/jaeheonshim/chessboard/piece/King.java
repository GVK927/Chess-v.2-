package com.jaeheonshim.chessboard.piece;

import com.jaeheonshim.chessboard.Board;
import com.jaeheonshim.chessboard.Spot;

import java.io.Serializable;

public class King extends Piece implements Serializable {
    public King(boolean white) {
        super(white);
    }

    public boolean canCastleQueenside(Board board) {
        if(
                !inCheck(board) &&
                !isMoved() &&
                (board.getSpot(0, getSpot(board).getY()).getPiece() instanceof Rook) &&
                !board.getSpot(0, getSpot(board).getY()).getPiece().isMoved()
        ){
            for(int i = 2; i<4; i++){
                if(board.getSpot(i, getSpot(board).getY()).getPiece() != null) return false;
                for(Spot[] spots:board.getBoard()){
                    for(Spot spot:spots){
                        if(spot.getPiece() != null && spot.getPiece().isWhite() != isWhite() && spot.getPiece().canMove(board, board.getSpot(i, this.getSpot(board).getY())))
                            return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    public boolean canCastleKingside(Board board) {
        if(
                !inCheck(board) &&
                !isMoved() &&
                (board.getSpot(7, getSpot(board).getY()).getPiece() instanceof Rook) &&
                !board.getSpot(7, getSpot(board).getY()).getPiece().isMoved()
        ){
            for(int i = 5; i<7; i++){
                if(board.getSpot(i, getSpot(board).getY()).getPiece() != null) return false;
                for(Spot[] spots:board.getBoard()){
                    for(Spot spot:spots){
                        if(spot.getPiece() != null && spot.getPiece().isWhite() != isWhite() && spot.getPiece().canMove(board, board.getSpot(i, this.getSpot(board).getY())))
                            return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canMove(Board board, Spot start, Spot end) {
        if(end.getY() == start.getY()&&!isMoved()){
            if(end.getX()==2) return canCastleQueenside(board);
            if(end.getX()==6) return canCastleKingside(board);
        }

        if (end.getPiece() != null && !(end.getPiece() instanceof EnPassant) && end.getPiece().isWhite() == this.isWhite()) {
            return false;
        }

        Piece endTemp = end.getPiece();
        Spot tempSpot = this.getSpot(board);
        end.setPiece(this);
        this.getSpot(board).setPiece(null);
        for (Spot[] spots : board.getBoard()) {
            for (Spot spot : spots) {
                if (spot.getPiece() != null && !(spot.getPiece() instanceof King) && spot.getPiece().isWhite() != isWhite() && spot.getPiece().canMove(board, spot, end)) {
                    end.setPiece(endTemp);
                    tempSpot.setPiece(this);
                    return false; //if any piece on the board can kill the king after it has made its move, you will be unable to make the move.
                }
            }
        }
        end.setPiece(endTemp);
        tempSpot.setPiece(this);

        if (start.getX() == end.getX()) {
            return Math.abs(start.getY() - end.getY()) <= 1;
        } else if (start.getY() == end.getY()) {
            return Math.abs(start.getX() - end.getX()) <= 1;
        } else if (Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY()) > 2) {
            return false;
        }

        return true;
    }

    public boolean inCheck(Board board) {
        for (Spot[] spots : board.getBoard()) {
            for (Spot spot : spots) {
                if (spot.getPiece() != null && !(spot.getPiece() instanceof EnPassant) && !(spot.getPiece() instanceof King) && spot.getPiece() != this && spot.getPiece().isWhite() != isWhite() && spot.getPiece().canMove(board, spot, this.getSpot(board), false)) {
                    return true; //if any piece on the board can kill the king after it has made its move, you will be unable to make the move.
                }
            }
        }

        return false;
    }
    public boolean inCheckmate(Board board) {
        if (this.inCheck(board)) {
            for (Spot[] spots : board.getBoard()) {
                for (Spot spot : spots) {

                    /*if(spot.getPiece()!=null && spot.getPiece().canMove(board, board.findCheckPiece()))
                        return false;*/


                    if (this.canMove(board, spot)) {
                        Piece tempPiece = spot.getPiece();
                        Spot tempSpot = getSpot(board);

                        getSpot(board).setPiece(null);
                        spot.setPiece(this);
                        if (!this.inCheck(board)) {
                            spot.setPiece(tempPiece);
                            tempSpot.setPiece(this);
                            return false;
                        }
                        spot.setPiece(tempPiece);
                        tempSpot.setPiece(this);
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (isWhite()) {
            return "K";
        } else {
            return "k";
        }
    }
}
