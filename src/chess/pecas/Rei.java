package chess.pecas;

import boardgame.Position;
import boardgame.Board;
import chess.Color;
import chess.ChessMatch;
import chess.ChessPiece;

public class Rei extends ChessPiece {

	private ChessMatch chessMatch; // dependecia para a partida

	public Rei(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch; // inclue a depencia;. associou
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("R");
		return builder.toString();
	}


	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().peça(position);
		return p == null || p.getColor() != getColor();
	}

	private boolean testCasting(Position position) { // quantidade de movimentos igual a zero , testa se a torre esta
		// apta para Roocky
		ChessPiece p = (ChessPiece) getBoard().peça(position);
		return p != null && p instanceof Torre && p.getColor() == getColor() && p.getMoveCount() == 0;
		// p!=null - testa se a posicao esta vazia
		// p instanceof Torre = se a peca é a Rei
		// p.getColor()==getColor = verifica a cor do Rei
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];

		Position p = new Position(0, 0);

		// acima
		p.setValues(position.getRow() - 1, position.getCollumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		// abaixo
		p.setValues(position.getRow() + 1, position.getCollumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		// esquerda
		p.setValues(position.getRow(), position.getCollumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		// direita
		p.setValues(position.getRow(), position.getCollumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		// nw
		p.setValues(position.getRow() - 1, position.getCollumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		// ne
		p.setValues(position.getRow() - 1, position.getCollumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		// sw
		p.setValues(position.getRow() + 1, position.getCollumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		// se
		p.setValues(position.getRow() + 1, position.getCollumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		// CASTLING SPECIALMOVE
		if (getMoveCount() == 0 && !chessMatch.getCheck()) { // partida nao estar em check e se a torre nao se moveu
			// Rook pequeno/ Rook kingSide
			Position posT1 = new Position(position.getRow(), position.getCollumn() + 3); // pega a pos do rei
			if (testCasting(posT1)) {
				Position p1 = new Position(position.getRow(), position.getCollumn() + 1);
				Position p2 = new Position(position.getRow(), position.getCollumn() + 2);
				if (getBoard().peça(p1) == null && getBoard().peça(p2) == null) { // verifica se a p1,p2 esta
																							// vazia
					mat[position.getRow()][position.getCollumn() + 2] = true; // fazer o castilng Rook
				}
			}
			// roque grande
			Position posT2 = new Position(position.getRow(), position.getCollumn() - 4);
			if (testCasting(posT2)) {
				Position p1 = new Position(position.getRow(), position.getCollumn() - 1);
				Position p2 = new Position(position.getRow(), position.getCollumn() - 2);
				Position p3 = new Position(position.getRow(), position.getCollumn() - 3);
				if (getBoard().peça(p1) == null && getBoard().peça(p2) == null
						&& getBoard().peça(p3) == null) {
					
					mat[position.getRow()][position.getCollumn() - 2] = true;
				}
			}
		}
		return mat;
	}
}
