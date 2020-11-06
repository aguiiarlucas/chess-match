package chess;

import boardgame.Position;

public class ChessPosition {

	private char collum;
	private int row;
	
	public ChessPosition(char collum, int linha) {
		if (collum < 'a' || collum > 'h' || linha < 1 || linha > 8) {
			throw new ChessException("Erro ao instanciar ChessPosition. Os valores validos sao de a1 a h8.");
		}
		this.collum = collum;
		this.row = linha;
	}

	public char getColumn() {
		return collum;
	}

	public int getRow() {
		return row;
	}

	protected Position toPosition() {
		return new Position(8 - row, collum - 'a');
	}
	
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a' + position.getCollumn()), 8 - position.getRow());
	}
	
	@Override
	public String toString() {
		return "" + collum + row;
	}
}
