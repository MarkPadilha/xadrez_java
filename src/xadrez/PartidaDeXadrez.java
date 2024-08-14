package xadrez;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiroDeJogo.Peca;
import tabuleiroDeJogo.Posicao;
import tabuleiroDeJogo.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	private int turno;
	private Cores jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaDeXadrez enPassantVulneravel;
	private PecaDeXadrez promocao;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

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

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}
	
	public PecaDeXadrez getEnPassantVulneravel(){
		return enPassantVulneravel;
	}
	
	public PecaDeXadrez getPromocao(){
		return promocao;
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

	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoInicial) {
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

		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(pecaMov, destino, capturada);
			throw new XadrezException("Voce nao pode se colocar em check.");
		}
		
		PecaDeXadrez pecaMovida = (PecaDeXadrez)tabuleiro.peca(destino);
		
		//Prmoção
		promocao = null;
		if(pecaMovida instanceof Peao) {
			if((pecaMovida.getCor() == Cores.BRANCA && destino.getLinha() == 0)  || (pecaMovida.getCor() == Cores.PRETA && destino.getLinha() == 7)) {
				promocao = (PecaDeXadrez)tabuleiro.peca(destino);
				promocao = colocarPecaPromovida("Q");
			}
		}

		check = (testarCheck(corOponente(jogadorAtual))) ? true : false;

		if (testarCheckMate(corOponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}
		
		//En Passant
		if(pecaMovida instanceof Peao && (destino.getLinha() == pecaMovimentada.getLinha() - 2 || destino.getLinha() == pecaMovimentada.getLinha() + 2) ) {
			enPassantVulneravel = pecaMovida;
		}else {
			enPassantVulneravel = null;
		}
		return (PecaDeXadrez) capturada;
	}
	
	public PecaDeXadrez colocarPecaPromovida(String type) {
		if(promocao == null) {
			throw new IllegalStateException("Nao ha uma peca a ser promovida");
		}
		if(!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Q")) {
			return promocao;
		}
		Posicao pos = promocao.getPosicaoXadrez().toPosicao();
		Peca p = tabuleiro.removerPeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaDeXadrez novaPeca = novaPeca(type, promocao.getCor());
		tabuleiro.colocarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private PecaDeXadrez novaPeca(String type, Cores cor) {
		if(type.equals("B")) return new Bispo(tabuleiro, cor);
		if(type.equals("C")) return new Cavalo(tabuleiro, cor);
		if(type.equals("T")) return new Torre(tabuleiro, cor);
		return new Rainha(tabuleiro, cor);
	}

	private Peca fazerMovimento(Posicao pecaMovimentada, Posicao destinoPeca) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removerPeca(pecaMovimentada);
		p.acrescentarMovimento();
		Peca cap = tabuleiro.removerPeca(destinoPeca);
		tabuleiro.colocarPeca(p, destinoPeca);
		if (cap != null) {
			pecasNoTabuleiro.remove(cap);
			pecasCapturadas.add(cap);
		}

		// Roque pequeno
		if (p instanceof Rei && destinoPeca.getColuna() == pecaMovimentada.getColuna() + 2) {
			Posicao origemTorre = new Posicao(pecaMovimentada.getLinha(), pecaMovimentada.getColuna() + 3);
			Posicao destinoTorre = new Posicao(pecaMovimentada.getLinha(), pecaMovimentada.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(origemTorre);
			tabuleiro.colocarPeca(torre, destinoTorre);
			torre.acrescentarMovimento();
		}

		// Roque grande
		if (p instanceof Rei && destinoPeca.getColuna() == pecaMovimentada.getColuna() - 2) {
			Posicao origemTorre = new Posicao(pecaMovimentada.getLinha(), pecaMovimentada.getColuna() - 4);
			Posicao destinoTorre = new Posicao(pecaMovimentada.getLinha(), pecaMovimentada.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(origemTorre);
			tabuleiro.colocarPeca(torre, destinoTorre);
			torre.acrescentarMovimento();
		}
		
		//En passant
		if(p instanceof Peao) {
			if(pecaMovimentada.getColuna() != destinoPeca.getColuna() && cap == null) {
				Posicao posicaoPeao;
				if(p.getCor() == Cores.BRANCA) {
					posicaoPeao = new Posicao(destinoPeca.getLinha() + 1, destinoPeca.getColuna());
				}else{
					posicaoPeao = new Posicao(destinoPeca.getLinha() - 1, destinoPeca.getColuna());
				}
				cap = tabuleiro.removerPeca(posicaoPeao);
				pecasCapturadas.add(cap);
				pecasNoTabuleiro.remove(cap);
			}
		}

		return cap;

	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca capturada) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removerPeca(destino);
		p.decrescerMovimento();
		tabuleiro.colocarPeca(p, origem);

		if (capturada != null) {
			tabuleiro.colocarPeca(capturada, destino);
			pecasCapturadas.remove(capturada);
			pecasNoTabuleiro.add(capturada);
		}
		// Roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(destinoTorre);
			tabuleiro.colocarPeca(torre, origem);
			torre.decrescerMovimento();
		}

		// Roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(destinoTorre);
			tabuleiro.colocarPeca(torre, origem);
			torre.decrescerMovimento();
		}
		
		if(p instanceof Peao) {
			if(origem.getColuna() != origem.getColuna() && capturada == enPassantVulneravel) {
				PecaDeXadrez peao = (PecaDeXadrez)tabuleiro.removerPeca(destino);
				Posicao posicaoPeao;
				if(p.getCor() == Cores.BRANCA) {
					posicaoPeao = new Posicao(3, destino.getColuna());
				}else {
					posicaoPeao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.colocarPeca(peao, posicaoPeao);
			}
		}
	}

	private void validarSePecaExiste(Posicao posicao) {
		if (!tabuleiro.possuiPeca(posicao)) {
			throw new XadrezException("Não existe peça nesta posição!");
		}
		if (jogadorAtual != ((PecaDeXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peca escolhida e do adversario");
		}
		if (!tabuleiro.peca(posicao).existeUmMovimentoPossivel()) {
			throw new XadrezException("Não existe movimentos possiveis para a peca escolida.");
		}
	}

	private void validarDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentosPossiveis(destino)) {
			throw new XadrezException("Este movimento nao e valido.");
		}
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cores.BRANCA) ? Cores.PRETA : Cores.BRANCA;
	}

	private void novaPecaLocal(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private Cores corOponente(Cores cor) {
		return (cor == Cores.BRANCA) ? Cores.PRETA : Cores.BRANCA;
	}

	private PecaDeXadrez rei(Cores cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaDeXadrez) p;
			}
		}
		throw new IllegalStateException("Nao existe o Rei " + cor + " no tabuleiro.");
	}

	private boolean testarCheck(Cores cor) {
		Posicao posicaoDoRei = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasDoOponente = pecasNoTabuleiro.stream()
				.filter(x -> ((PecaDeXadrez) x).getCor() == corOponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasDoOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testarCheckMate(Cores cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaDeXadrez) p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testeCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testeCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void setupInicial() {
		novaPecaLocal('a', 1, new Torre(tabuleiro, Cores.BRANCA));
		novaPecaLocal('e', 1, new Rei(tabuleiro, Cores.BRANCA, this));
		novaPecaLocal('h', 1, new Torre(tabuleiro, Cores.BRANCA));
		novaPecaLocal('a', 2, new Peao(tabuleiro, Cores.BRANCA, this));
		novaPecaLocal('b', 2, new Peao(tabuleiro, Cores.BRANCA, this));
		novaPecaLocal('c', 2, new Peao(tabuleiro, Cores.BRANCA, this));
		novaPecaLocal('d', 2, new Peao(tabuleiro, Cores.BRANCA, this));
		novaPecaLocal('e', 2, new Peao(tabuleiro, Cores.BRANCA, this));
		novaPecaLocal('f', 2, new Peao(tabuleiro, Cores.BRANCA, this));
		novaPecaLocal('g', 2, new Peao(tabuleiro, Cores.BRANCA, this));
		novaPecaLocal('h', 2, new Peao(tabuleiro, Cores.BRANCA, this));
		novaPecaLocal('c', 1, new Bispo(tabuleiro, Cores.BRANCA));
		novaPecaLocal('f', 1, new Bispo(tabuleiro, Cores.BRANCA));
		novaPecaLocal('b', 1, new Cavalo(tabuleiro, Cores.BRANCA));
		novaPecaLocal('g', 1, new Cavalo(tabuleiro, Cores.BRANCA));
		novaPecaLocal('d', 1, new Rainha(tabuleiro, Cores.BRANCA));

		novaPecaLocal('a', 8, new Torre(tabuleiro, Cores.PRETA));
		novaPecaLocal('e', 8, new Rei(tabuleiro, Cores.PRETA, this));
		novaPecaLocal('h', 8, new Torre(tabuleiro, Cores.PRETA));
		novaPecaLocal('a', 7, new Peao(tabuleiro, Cores.PRETA, this));
		novaPecaLocal('b', 7, new Peao(tabuleiro, Cores.PRETA, this));
		novaPecaLocal('c', 7, new Peao(tabuleiro, Cores.PRETA, this));
		novaPecaLocal('d', 7, new Peao(tabuleiro, Cores.PRETA, this));
		novaPecaLocal('e', 7, new Peao(tabuleiro, Cores.PRETA, this));
		novaPecaLocal('f', 7, new Peao(tabuleiro, Cores.PRETA, this));
		novaPecaLocal('g', 7, new Peao(tabuleiro, Cores.PRETA, this));
		novaPecaLocal('h', 7, new Peao(tabuleiro, Cores.PRETA, this));
		novaPecaLocal('c', 8, new Bispo(tabuleiro, Cores.PRETA));
		novaPecaLocal('f', 8, new Bispo(tabuleiro, Cores.PRETA));
		novaPecaLocal('b', 8, new Cavalo(tabuleiro, Cores.PRETA));
		novaPecaLocal('g', 8, new Cavalo(tabuleiro, Cores.PRETA));
		novaPecaLocal('d', 8, new Rainha(tabuleiro, Cores.PRETA));
	}

}
