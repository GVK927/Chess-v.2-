package com.jaeheonshim.chessboard.piece;

import com.jaeheonshim.chessboard.Board;
import com.jaeheonshim.chessboard.Spot;

public class Queen extends Piece {
	 public Queen (boolean white) {
		  super(white);
	 }
	 public Queen (boolean white, boolean moved){
	 	super(white);
	 	this.moved = moved;
	 }

	 @Override public boolean canMove (Board board, Spot start, Spot end) {
		  if (end.getPiece() != null && !(end.getPiece() instanceof EnPassant) && end.getPiece().isWhite() == this.isWhite()) {
				return false;
		  }

		  if (checkKingInCheck) {
				Spot tempSpot = this.getSpot(board);
				Piece tempPiece = end.getPiece();

				end.setPiece(this);
				start.setPiece(null);

				if (board.getKing(isWhite()) != null && (board.getKing(isWhite()).inCheck(board))) {
					 end.setPiece(tempPiece);
					 tempSpot.setPiece(this);
					 return false;
				}

				end.setPiece(tempPiece);
				tempSpot.setPiece(this);
		  }

		  checkKingInCheck = true;

		  if (start.getX() == end.getX()) {
				for (int i = Math.min(start.getY(), end.getY()) + 1; i < Math.max(start.getY(), end.getY()); i++) {
					 if (board.getSpot(start.getX(), i).getPiece() != null && !(board.getSpot(start.getX(), i).getPiece() instanceof EnPassant)) {
						  return false;
					 }
				}
				return true;
		  } else if (start.getY() == end.getY()) {
				for (int i = Math.min(start.getX(), end.getX()) + 1; i < Math.max(start.getX(), end.getX()); i++) {
					 if (board.getSpot(i, start.getY()).getPiece() != null && !(board.getSpot(i, start.getY()).getPiece() instanceof EnPassant)) {
						  return false;
					 }
				}
				return true;
		  } else if (Math.abs(start.getX() - end.getX()) == Math.abs(start.getY() - end.getY())) {
			  int xMutator;
			  int yMutator;

			  if(start.getX() < end.getX()) {
				  // Moving to the right
				  xMutator = 1;
			  } else {
				  xMutator = -1;
			  }

			  if(start.getY() < end.getY()) {
				  // if moving up
				  yMutator = 1;
			  } else {
				  yMutator = -1;
			  }


			  int xIndex = start.getX() + xMutator;
			  int yIndex = start.getY() + yMutator;

			  while (xIndex != end.getX() && yIndex != end.getY()) {
				  if (board.getSpot(xIndex, yIndex).getPiece() != null && !(board.getSpot(xIndex, yIndex).getPiece() instanceof EnPassant) && xIndex != end.getX() && yIndex != end.getY()) {
					  return false;
				  }

				  xIndex += xMutator;
				  yIndex += yMutator;
			  }
				return true;
		  } else {
				return false;
		  }
	 }

	 @Override public String toString () {
		  if (isWhite()) {
				return "Q";
		  } else {
				return "q";
		  }
	 }
}
