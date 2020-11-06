package chess.pecas;

import boardgame.Position;
import boardgame.Board;
import chess.Color;
import chess.ChessMatch;
import chess.ChessPiece;

public class Peao extends ChessPiece {

	private  ChessMatch chessMatch; //dependencia 
	
	public Peao(Board board, Color color,ChessMatch chassMatch) {
		super(board, color);
		this.chessMatch=chassMatch;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
		Position p = new Position(0, 0);

		if (getColor() == Color.WHITE) { // Peças branças
			p.setValues(position.getRow() - 1, position.getCollumn()); // se a possição de 1 linha exister
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {// e tiver vazia
				mat[p.getRow()][p.getCollumn()] = true;// ele pode andar
			}
			p.setValues(position.getRow() - 2, position.getCollumn());
			Position p2 = new Position(position.getRow() - 1, position.getCollumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)
					&& getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {// testa
					mat[p.getRow()][p.getCollumn()] = true;// ele pode andar
			}
			p.setValues(position.getRow() - 1, position.getCollumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getCollumn()] = true;// ele pode andar
			}
			p.setValues(position.getRow() - 1, position.getCollumn() + 1); // se a possição de 1 linha exister
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {// e tiver vazia
				mat[p.getRow()][p.getCollumn()] = true;// ele pode andar
			}
							//#ESPECIAL MOVED EN PASSANT 
			
			if(position.getRow()==3) {//Peao esta na linha 5 do tabuleiro
				Position left= new Position(position.getRow(),position.getCollumn()-1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left)&& getBoard().peça(left)==chessMatch.getEnpassantVunerable()) {
					mat[left.getRow()-1][left.getCollumn()]=true;//pode capturar. UMA LINHA PRA CIMA da posicao 
				}
				Position right= new Position(position.getRow(),position.getCollumn()+1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right)&& getBoard().peça(right)==chessMatch.getEnpassantVunerable()) {
					mat[right.getRow()-1][right.getCollumn()]=true;
				}
			
			
			}
			
			
		} else {
			p.setValues(position.getRow() + 1, position.getCollumn()); // se a possição de 1 linha exister
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {// e tiver vazia
				mat[p.getRow()][p.getCollumn()] = true;// ele pode andar
			}
			p.setValues(position.getRow() + 2, position.getCollumn());
			Position p2 = new Position(position.getRow() + 1, position.getCollumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)
					&& getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {// testa
																														 
				mat[p.getRow()][p.getCollumn()] = true;// ele pode andar
			}
			p.setValues(position.getRow() + 1, position.getCollumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getCollumn()] = true;// ele pode andar
			}
			p.setValues(position.getRow() + 1, position.getCollumn() + 1); // se a possição de 1 linha exister
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {// e tiver vazia
				mat[p.getRow()][p.getCollumn()] = true;// ele pode andar
			}
					//ESPECIAL MOVED RIGHT EN PASSANT BLACK
			if(position.getRow()==4) {
				Position left= new Position(position.getRow(),position.getCollumn()-1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left)&& getBoard().peça(left)==chessMatch.getEnpassantVunerable()) {
					mat[left.getRow() +1][left.getCollumn()]=true;
				}
				Position right= new Position(position.getRow(),position.getCollumn()+1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right)&& getBoard().peça(right)==chessMatch.getEnpassantVunerable()) {
					mat[right.getRow()+1][right.getCollumn()]=true;
				}
			
			
			}
		}

		return mat;
	}
	@Override
	public String toString() {
		return "P";
	}
}
