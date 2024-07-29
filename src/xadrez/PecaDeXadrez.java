package xadrez;

import tabuleiroDeJogo.Peca;
import tabuleiroDeJogo.Tabuleiro;

public class PecaDeXadrez extends Peca {
	
	private Cores cor;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cores cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cores getCor() {
		return cor;
	}

}
