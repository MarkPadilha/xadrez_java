package xadrez;

import tabuleiroDeJogo.Peca;
import tabuleiroDeJogo.Posicao;
import tabuleiroDeJogo.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {
	
	private Cores cor;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cores cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cores getCor() {
		return cor;
	}
	
	protected boolean existeUmaPecaRival(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}

}
