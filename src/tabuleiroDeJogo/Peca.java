package tabuleiroDeJogo;

public abstract class Peca {
	
	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}
	
	//Protected porque só quero que o pacote tabuleiroDeJogo tenha acesso a esse metodo
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}	
	
	public abstract boolean[][] movimentosPossiveis();
	
	public boolean movimentosPossiveis(Posicao posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}
	
	public boolean existeUmMovimentoPossivel() {
		boolean[][] mat = movimentosPossiveis();
		for(int i=0; i<mat.length; i++) {
			for(int j=0; j<mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
}
