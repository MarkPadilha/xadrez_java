package xadrez.pecas;

import tabuleiroDeJogo.Posicao;
import tabuleiroDeJogo.Tabuleiro;
import xadrez.Cores;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

	private PartidaDeXadrez partidaDeXadrez;

	public Peao(Tabuleiro tabuleiro, Cores cor, PartidaDeXadrez partida) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partida;
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
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().possuiPeca(p) && (getTabuleiro().posicaoExiste(p2)
					&& !getTabuleiro().possuiPeca(p2) && getContadorDeMovimentos() == 0)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && getTabuleiro().possuiPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && getTabuleiro().possuiPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// En Passant
			if (posicao.getLinha() == 3) {
				Posicao esq = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esq) && existeUmaPecaRival(esq)
						&& getTabuleiro().peca(esq) == partidaDeXadrez.getEnPassantVulneravel()) {
					mat[esq.getLinha() - 1][esq.getColuna()] = true;
				}

				Posicao dir = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(dir) && existeUmaPecaRival(dir)
						&& getTabuleiro().peca(dir) == partidaDeXadrez.getEnPassantVulneravel()) {
					mat[dir.getLinha() - 1][dir.getColuna()] = true;
				}

			}


		} else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().possuiPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().possuiPeca(p) && (getTabuleiro().posicaoExiste(p2)
					&& !getTabuleiro().possuiPeca(p2) && getContadorDeMovimentos() == 0)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && getTabuleiro().possuiPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && getTabuleiro().possuiPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// En Passant
			if (posicao.getLinha() == 4) {
				Posicao esq = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esq) && existeUmaPecaRival(esq)
						&& getTabuleiro().peca(esq) == partidaDeXadrez.getEnPassantVulneravel()) {
					mat[esq.getLinha() + 1][esq.getColuna()] = true;
				}

				Posicao dir = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(dir) && existeUmaPecaRival(dir)
						&& getTabuleiro().peca(dir) == partidaDeXadrez.getEnPassantVulneravel()) {
					mat[dir.getLinha() + 1][dir.getColuna()] = true;
				}

			}

		}

		return mat;
	}

}
