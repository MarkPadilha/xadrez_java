package tabuleiroDeJogo;

public class Peca {
	
	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}
	
	//Protected porque só quero que o pacote tabuleiroDeJogo tenha acesso a esse metodo
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}	
	
}
