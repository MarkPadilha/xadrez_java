package xadrez;

import tabuleiroDeJogo.Posicao;

public class PosicaoXadrez {
	
	private char coluna;
	private int linha;
	
	
	public PosicaoXadrez(char coluna, int linha) {
		if(coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezException("Erro na posição, os valores são de A1 a H8.");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	 protected Posicao toPosicao() {
		 return new Posicao(8 - linha, coluna - 'a');
	 }
	 
	 protected static PosicaoXadrez fromPosicao(Posicao posicao) {
		 return new PosicaoXadrez((char)('a' + posicao.getColuna()), 8 - posicao.getLinha());
	 }
	 public String toString() {
		 return "" + coluna + linha;
	 }
}
