package xadrez;

import tabuleiroDeJogo.Peca;
import tabuleiroDeJogo.Posicao;
import tabuleiroDeJogo.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	private int turno;
	private Cores jogadorAtual;
	private Tabuleiro tabuleiro;

	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cores.BRANCA;
		setupInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cores getJogadorAtual() {
		return jogadorAtual;
	}

	public PecaDeXadrez[][] getPeca() {
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int l = 0; l < tabuleiro.getLinhas(); l++) {
			for (int c = 0; c < tabuleiro.getColunas(); c++) {
				mat[l][c] = (PecaDeXadrez) tabuleiro.peca(l, c);
			}
		}
		return mat;
	}
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoInicial){
		Posicao posicao = posicaoInicial.toPosicao();
		validarSePecaExiste(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaDeXadrez movimentoPeca(PosicaoXadrez pecaMovimentada, PosicaoXadrez destinoPeca) {
		Posicao pecaMov = pecaMovimentada.toPosicao();
		Posicao destino = destinoPeca.toPosicao();
		validarSePecaExiste(pecaMov);
		validarDestino(pecaMov, destino);
		Peca capturada = fazerMovimento(pecaMov, destino);
		proximoTurno();
		return (PecaDeXadrez)capturada;
	}
	
	private Peca fazerMovimento(Posicao pecaMovimentada, Posicao destinoPeca) {
		Peca p = tabuleiro.removerPeca(pecaMovimentada);
		Peca cap = tabuleiro.removerPeca(destinoPeca);
		tabuleiro.colocarPeca(p, destinoPeca);
		return cap;
		
	}
	
	private void validarSePecaExiste(Posicao posicao) {
		if(!tabuleiro.possuiPeca(posicao)) {
			throw new XadrezException("Não existe peça nesta posição!");
		}
		if(jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peca escolhida e do adversario");
		}
		if(!tabuleiro.peca(posicao).existeUmMovimentoPossivel()) {
			throw new XadrezException("Não existe movimentos possiveis para a peca escolida.");
		}
	}
	
	private void validarDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).movimentosPossiveis(destino)) {
			throw new XadrezException("Este movimento nao e valido.");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cores.BRANCA) ? Cores.PRETA : Cores.BRANCA;
	}

	private void novaPecaLocal(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}

	private void setupInicial() {
		novaPecaLocal('c', 1, new Torre(tabuleiro, Cores.BRANCA));
		novaPecaLocal('c', 2, new Torre(tabuleiro, Cores.BRANCA));
		novaPecaLocal('d', 2, new Torre(tabuleiro, Cores.BRANCA));
		novaPecaLocal('e', 2, new Torre(tabuleiro, Cores.BRANCA));
		novaPecaLocal('e', 1, new Torre(tabuleiro, Cores.BRANCA));
		novaPecaLocal('d', 1, new Rei(tabuleiro, Cores.BRANCA));

		novaPecaLocal('c', 7, new Torre(tabuleiro, Cores.PRETA));
		novaPecaLocal('c', 8, new Torre(tabuleiro, Cores.PRETA));
		novaPecaLocal('d', 7, new Torre(tabuleiro, Cores.PRETA));
		novaPecaLocal('e', 7, new Torre(tabuleiro, Cores.PRETA));
		novaPecaLocal('e', 8, new Torre(tabuleiro, Cores.PRETA));
		novaPecaLocal('d', 8, new Rei(tabuleiro, Cores.PRETA));
	}

}
