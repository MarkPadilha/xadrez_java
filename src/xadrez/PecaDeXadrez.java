package xadrez;

import tabuleiroDeJogo.Peca;
import tabuleiroDeJogo.Posicao;
import tabuleiroDeJogo.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {
	
	private Cores cor;
	private int contadorDeMovimentos;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cores cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cores getCor() {
		return cor;
	}
	
	public int getContadorDeMovimentos() {
		return contadorDeMovimentos;
	}
	
	public void acrescentarMovimento() {
		contadorDeMovimentos++;
	}
	
	public void decrescerMovimento() {
		contadorDeMovimentos--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.fromPosicao(posicao);
	}
	
	protected boolean existeUmaPecaRival(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}
}

