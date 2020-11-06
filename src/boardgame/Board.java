package boardgame;

public class Board {

	private int linhas; // rows
	private int colunas; // comumns
	private Piece[][] peças; // pieces

	public Board(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new BoardException(
					"Erro criando Tabuleiro: " + "é necessario que haja pelo menos 1 linha e 1 coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		peças = new Piece[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Piece peça(int linha, int coluna) {
		if(!positionExists(linha,coluna)) {
			throw new BoardException("Position não esta no Tabuleiro");
		}
		return peças[linha][coluna];
	}

	public Piece peça(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position não esta no Tabuleiro");
		}
		return peças[position.getRow()][position.getCollumn()];
	}

	public void placePiece(Piece peça, Position position) {
		
		if(thereIsAPiece(position)) {
			throw new BoardException("Já existe uma peça na posição"+ position);
		}
		peças[position.getRow()][position.getCollumn()] = peça;
		peça.position = position;
	}
	
	public Piece removePiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position não esta no Tabuleiro");
		}
		if((peça(position)==null)) {
			return null;
		}
		Piece aux = peça(position);
		aux.position=null;
		peças[position.getRow()][position.getCollumn()]=null;

		return aux;
	}
	
	
	public boolean positionExists(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}

	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getCollumn());
	}

	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position não esta no Tabuleiro");
		}
		return peça(position) != null;
	}
}
