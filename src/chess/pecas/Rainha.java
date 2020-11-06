package chess.pecas;

import boardgame.Position;
import boardgame.Board;
import chess.Color;
import chess.ChessPiece;

public class Rainha extends ChessPiece {

	public Rainha(Board board, Color color) {
		super(board, color);

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Q");
		return builder.toString();
	}


	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];

		Position p = new Position(0, 0);

		// PRA CIMA
		p.setValues(position.getRow() - 1, position.getCollumn());
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
			p.setRow(p.getRow() - 1);

		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;

		}

		// ESQUERDA
		p.setValues(position.getRow(), position.getCollumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true; // peca pode mover.
			p.setCollumn(p.getCollumn() - 1);// linha da posicao andar pra cima enquanto tiver casas vazia
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true; // peca pode mover.

		}
		// DIREITA
		p.setValues(position.getRow(), position.getCollumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true; // peca pode mover.
			p.setCollumn(p.getCollumn() + 1);// linha da posicao andar pra cima enquanto tiver casas vazia
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true; // peca pode mover.

		}
		// PRA BAIXO
		p.setValues(position.getRow() + 1, position.getCollumn());
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true; // peca pode mover.
			p.setRow(p.getRow() + 1);// linha da posicao andar pra cima enquanto tiver casas vazia
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true; // peca pode mover.

		}
		// NOROESTE
		p.setValues(position.getRow() - 1, position.getCollumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
			p.setValues(p.getRow() - 1, p.getCollumn() - 1);

		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;

		}

		// nordeste
		p.setValues(position.getRow() - 1, position.getCollumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true; // peca pode mover.
			p.setValues(p.getRow() - 1, p.getCollumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true; // peca pode mover.

		}
		// sudeste
		p.setValues(position.getRow() + 1, position.getCollumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true; // peca pode mover.
			p.setValues(p.getRow() + 1, p.getCollumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true; // peca pode mover.

		}
		// PRA BAIXO
		p.setValues(position.getRow() + 1, position.getCollumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true; // peca pode mover.
			p.setValues(p.getRow() + 1, p.getCollumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true; // peca pode mover.

		}
		return mat;
	}
}
