package xadrez.pecas;

import tabuleiroDeJogo.Posicao;
import tabuleiroDeJogo.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

	public Peao(Tabuleiro tabuleiro, Cores cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		if (getCor() == Cores.BRANCA) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().possuiPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().possuiPeca(p) && (getTabuleiro().posicaoExiste(p2) && !getTabuleiro().possuiPeca(p2) && getContadorDeMovimentos() == 0)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() -1 );
			if(getTabuleiro().posicaoExiste(p) && getTabuleiro().possuiPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1 );
			if(getTabuleiro().posicaoExiste(p) && getTabuleiro().possuiPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

		}else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().possuiPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().possuiPeca(p) && (getTabuleiro().posicaoExiste(p2) && !getTabuleiro().possuiPeca(p2) && getContadorDeMovimentos() == 0)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() -1 );
			if(getTabuleiro().posicaoExiste(p) && getTabuleiro().possuiPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1 );
			if(getTabuleiro().posicaoExiste(p) && getTabuleiro().possuiPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

		}

		return mat;
	}

}
