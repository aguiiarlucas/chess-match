package chess.pecas;

import boardgame.Position;
import boardgame.Board;
import chess.Color;
import chess.ChessPiece;

public class Cavalo extends ChessPiece {

	public Cavalo(Board tabuleiro, Color color) {
		super(tabuleiro, color);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("C");
		return builder.toString();
	}


	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().peça(position);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];

		Position p = new Position(0, 0);

 		p.setValues(position.getRow() - 1, position.getCollumn()-2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
 		p.setValues(position.getRow() -2, position.getCollumn()-1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
 		p.setValues(position.getRow()-2, position.getCollumn() +1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		//  
		p.setValues(position.getRow()-1, position.getCollumn() +2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		//  
		p.setValues(position.getRow() + 1, position.getCollumn() +2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		//   
		p.setValues(position.getRow() +2, position.getCollumn() +1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		//  
		p.setValues(position.getRow() +2, position.getCollumn() -1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		//  
		p.setValues(position.getRow() + 1, position.getCollumn() -2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		return mat;
	}
}
