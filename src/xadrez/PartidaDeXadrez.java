package xadrez;

import tabuleiroDeJogo.Tabuleiro;

public class PartidaDeXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
	}
	
	public PecaDeXadrez[][] getPeca() {
		PecaDeXadrez [][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int l=0; l<tabuleiro.getLinhas(); l++) {
			for(int c=0; c<tabuleiro.getColunas(); c++) {
				mat[l][c] = (PecaDeXadrez) tabuleiro.peca(l, c);
			}
		}
		return mat;
	}
	
}
