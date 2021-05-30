package com.jaeheonshim.chessboard.piece;

import com.jaeheonshim.chessboard.Board;
import com.jaeheonshim.chessboard.Spot;

import java.io.Serializable;

public class EnPassant extends Piece implements Serializable {
    private Spot parentPawn;
    private int liveTimer;

    @Override
    public boolean canMove (Board board, Spot start, Spot end) {
        return false;
    }

    @Override
    public String toString () {
        return " ";
    }

    public EnPassant (Spot parentPawn) {
        super(parentPawn.getPiece().isWhite());
        this.parentPawn = parentPawn;
        this.liveTimer = 2;
    }

    public Spot getParentPawn () {
        return parentPawn;
    }

    public int getLiveTimer () {
        return --liveTimer;
    }
}
