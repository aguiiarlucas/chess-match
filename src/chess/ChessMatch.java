package chess;

 
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Piece;
import boardgame.Position;
import boardgame.Board;
import chess.pecas.Bispo;
import chess.pecas.Cavalo;
import chess.pecas.Peao;
import chess.pecas.Rainha;
import chess.pecas.Rei;
import chess.pecas.Torre;
 
public class ChessMatch {

	private Board board;
	private Color currentPlayer;
	private int turn;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVunerable;
	private ChessPiece promoted;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public ChessPiece getEnpassantVunerable() {
		return enPassantVunerable;
	}

	public ChessPiece getPromoted() {
		return promoted;
	}

	public ChessPiece[][] getPeças() {
		ChessPiece[][] mat = new ChessPiece[board.getLinhas()][board.getColunas()];

		for (int i = 0; i < board.getLinhas(); i++) {
			for (int j = 0; j < board.getColunas(); j++) {
				mat[i][j] = (ChessPiece) board.peça(i, j);// downquest
			}
		}
		return mat;
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) { // imprime as posicoes possiveis a partir de uma
																		// posicao de origem
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.peça(position).possibleMoves();
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		// convertendo as duas operações
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlayer)) { // se colocou em check
			undoMove(source, target, capturedPiece);// desfaz o movimento
			throw new ChessException("Você não pode estar em CHECK!");
		}
		ChessPiece movedPiece = (ChessPiece) board.peça(target);

		// # PROMOTION
		promoted = null;
		if (movedPiece instanceof Peao) {
			if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				promoted = (ChessPiece)board.peça(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		check = (testCheck(opponent(currentPlayer))) ? true : false;

		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();
		}
		// Special Moved EN PASSANT
		if (movedPiece instanceof Peao && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) { // tratamento
			enPassantVunerable = movedPiece;
		} else {
			enPassantVunerable = null;
		}

		return (ChessPiece) capturedPiece;
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {// se nao existir peça nessa posição
			throw new ChessException("Nao existe peça na posicao de origem");
		}
		if (currentPlayer != ((ChessPiece) board.peça(position)).getColor()) {
			throw new ChessException("A Peca escolhi nao eh sua ! ");

		}

		if (!board.peça(position).isThereAnyPossibleMove()) {
			throw new ChessException("Nao existe movimentos possiveis para a peca escolhida");

		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.peça(source).possibleMoves(target)) {
			throw new ChessException("A peca escolhida nao pode se mover para posicao de destino");
		}
	}

	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("Nao ha peca a ser promovida");
		}
		if (!type.equalsIgnoreCase("B") && !type.equalsIgnoreCase("C") && !type.equalsIgnoreCase("T") && !type.equalsIgnoreCase("Q")) {
			return promoted;
		}
		Position pos  = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece  = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	private ChessPiece newPiece(String type, Color color) { //instancia a peca
	 
		if(type.equalsIgnoreCase("B"))	return new Bispo(board, color);
		if(type.equalsIgnoreCase("C"))	return new Cavalo(board, color);
		if(type.equalsIgnoreCase("Q"))	return new Rainha(board, color);
		 return new Torre(board, color);
	 
		 
		   
		
	}
 

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source);// retirada peça da posição de origem
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);// remover a possivel peça de destino
		board.placePiece(p, target); // colcou a peça removida

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		// #specialmove castling kingside rook
		if (p instanceof Rei && target.getCollumn() == source.getCollumn() + 2) { // andou 2 casas a direita
			Position sourceT = new Position(source.getRow(), source.getCollumn() + 3);
			Position targetT = new Position(source.getRow(), source.getCollumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT); // pega a torre do tabuleiro
			board.placePiece(rook, targetT);// moveu
			rook.increaseMoveCount();// tratou
		}

		// #specialmove castling queenside rook
		if (p instanceof Rei && target.getCollumn() == source.getCollumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getCollumn() - 4);// posicao de origem da torre
			Position targetT = new Position(source.getRow(), source.getCollumn() - 1);// posicao de destino da torre
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT); // retira
			board.placePiece(rook, targetT);// coloca
			rook.increaseMoveCount();// trata
		}
		// EN PASSANT
		if (p instanceof Peao) {
			if (source.getCollumn() != target.getCollumn() && capturedPiece == null) {
				Position peao;
				if (p.getColor() == Color.WHITE) {
					peao = new Position(target.getRow() + 1, target.getCollumn());
				} else {
					peao = new Position(target.getRow() - 1, target.getCollumn());
				}
				capturedPiece = board.removePiece(peao); // remove o peao do tabuleiro
				capturedPieces.add(capturedPiece); // adicona na lista de pecas capturadas
				piecesOnTheBoard.remove(capturedPiece);// r
			}
		}

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);// retirada peça da posição de origem
		p.decreaseMoveCount();
		board.placePiece(p, source);

		if (capturedPiece != null) {// troca de lista
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		// TRATANDO ROOK PEQUENDO ;
		// #specialmove castling kingside rook
		if (p instanceof Rei && target.getCollumn() == source.getCollumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getCollumn() + 3);
			Position targetT = new Position(source.getRow(), source.getCollumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}

		// #specialmove castling queenside rook
		if (p instanceof Rei && target.getCollumn() == source.getCollumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getCollumn() - 4);
			Position targetT = new Position(source.getRow(), source.getCollumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		// EN PASSANT
		if (p instanceof Peao) {
			if (source.getCollumn() != target.getCollumn() && capturedPiece == enPassantVunerable) {
				ChessPiece peao2 = (ChessPiece) board.removePiece(target);// tirou a peça do lugar errado
				Position peao;
				if (p.getColor() == Color.WHITE) {
					peao = new Position(3, target.getCollumn());
				} else {
					peao = new Position(4, target.getCollumn());
				}
				board.placePiece(peao2, peao);

			}
		}

	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof Rei) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getCollumn()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(Color color) {

		if (!testCheck(color)) { // posibilidade nao estar em check
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == (color))
				.collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getLinhas(); i++) {
				for (int j = 0; j < board.getColunas(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);// fez o movimento
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);// desfez o movimento
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char coluna, int linha, ChessPiece peça) {
		board.placePiece(peça, new ChessPosition(coluna, linha).toPosition());
		piecesOnTheBoard.add(peça);

	}

	private void initialSetup() {
		placeNewPiece('a', 1, new Torre(board, Color.WHITE));
		placeNewPiece('b', 1, new Cavalo(board, Color.WHITE));
		placeNewPiece('c', 1, new Bispo(board, Color.WHITE));
		placeNewPiece('d', 1, new Rainha(board, Color.WHITE));
		placeNewPiece('e', 1, new Rei(board, Color.WHITE, this));
		placeNewPiece('f', 1, new Bispo(board, Color.WHITE));
		placeNewPiece('g', 1, new Cavalo(board, Color.WHITE));
		placeNewPiece('h', 1, new Torre(board, Color.WHITE));
		placeNewPiece('a', 2, new Peao(board, Color.WHITE, this));
		placeNewPiece('b', 2, new Peao(board, Color.WHITE, this));
		placeNewPiece('c', 2, new Peao(board, Color.WHITE, this));
		placeNewPiece('d', 2, new Peao(board, Color.WHITE, this));
		placeNewPiece('e', 2, new Peao(board, Color.WHITE, this));
		placeNewPiece('f', 2, new Peao(board, Color.WHITE, this));
		placeNewPiece('g', 2, new Peao(board, Color.WHITE, this));
		placeNewPiece('h', 2, new Peao(board, Color.WHITE, this));

		placeNewPiece('a', 8, new Torre(board, Color.BLACK));
		placeNewPiece('b', 8, new Cavalo(board, Color.BLACK));
		placeNewPiece('c', 8, new Bispo(board, Color.BLACK));
		placeNewPiece('d', 8, new Rainha(board, Color.BLACK));
		placeNewPiece('e', 8, new Rei(board, Color.BLACK, this));
		placeNewPiece('f', 8, new Bispo(board, Color.BLACK));
		placeNewPiece('g', 8, new Cavalo(board, Color.BLACK));
		placeNewPiece('h', 8, new Torre(board, Color.BLACK));
		placeNewPiece('a', 7, new Peao(board, Color.BLACK, this));
		placeNewPiece('b', 7, new Peao(board, Color.BLACK, this));
		placeNewPiece('c', 7, new Peao(board, Color.BLACK, this));
		placeNewPiece('d', 7, new Peao(board, Color.BLACK, this));
		placeNewPiece('e', 7, new Peao(board, Color.BLACK, this));
		placeNewPiece('f', 7, new Peao(board, Color.BLACK, this));
		placeNewPiece('g', 7, new Peao(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Peao(board, Color.BLACK, this));
	}

}
