package boardgame;

public class Board {

	private int linhas; // rows
	private int colunas; // comumns
	private Piece[][] pe�as; // pieces

	public Board(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new BoardException(
					"Erro criando Tabuleiro: " + "� necessario que haja pelo menos 1 linha e 1 coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pe�as = new Piece[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Piece pe�a(int linha, int coluna) {
		if(!positionExists(linha,coluna)) {
			throw new BoardException("Position n�o esta no Tabuleiro");
		}
		return pe�as[linha][coluna];
	}

	public Piece pe�a(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position n�o esta no Tabuleiro");
		}
		return pe�as[position.getRow()][position.getCollumn()];
	}

	public void placePiece(Piece pe�a, Position position) {
		
		if(thereIsAPiece(position)) {
			throw new BoardException("J� existe uma pe�a na posi��o"+ position);
		}
		pe�as[position.getRow()][position.getCollumn()] = pe�a;
		pe�a.position = position;
	}
	
	public Piece removePiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position n�o esta no Tabuleiro");
		}
		if((pe�a(position)==null)) {
			return null;
		}
		Piece aux = pe�a(position);
		aux.position=null;
		pe�as[position.getRow()][position.getCollumn()]=null;

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
			throw new BoardException("Position n�o esta no Tabuleiro");
		}
		return pe�a(position) != null;
	}
}
