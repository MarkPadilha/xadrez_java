package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cores;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

public class UI {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static PosicaoXadrez lerPosicaoXadrez(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro na leitura de posição. Valores validos são de a1 a h8.");
		}
	}
	
	public static void printPartida(PartidaDeXadrez partida, List<PecaDeXadrez> capturadas) {
		printDoTabuleiro(partida.getPeca());
		System.out.println();
		printPecasCapturadas(capturadas);
		System.out.println();
		System.out.println("Turno: " + partida.getTurno());
		System.out.println("Esperando jogador das: " + partida.getJogadorAtual());
		if(partida.getCheck()) {
			System.out.println("CHECK");
		}
	}

	public static void printDoTabuleiro(PecaDeXadrez[][] pecas) {
		for (int l = 0; l < pecas.length; l++) {
			System.out.print((8 - l) + " ");
			for (int c = 0; c < pecas.length; c++) {
				printPeca(pecas[l][c], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void printDoTabuleiro(PecaDeXadrez[][] pecas, boolean[][] movPossivel) {
		for (int l = 0; l < pecas.length; l++) {
			System.out.print((8 - l) + " ");
			for (int c = 0; c < pecas.length; c++) {
				printPeca(pecas[l][c], movPossivel[l][c]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	private static void printPeca(PecaDeXadrez peca, boolean fundo) {
		if(fundo) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (peca.getCor() == Cores.BRANCA) {
				System.out.print(ANSI_WHITE + peca + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
	
	private static void printPecasCapturadas(List<PecaDeXadrez> capturadas) {
		List<PecaDeXadrez> brancas = capturadas.stream().filter(x -> x.getCor() == Cores.BRANCA).collect(Collectors.toList());
		List<PecaDeXadrez> pretas = capturadas.stream().filter(x -> x.getCor() == Cores.PRETA).collect(Collectors.toList());
		System.out.println("Pecas capturadas:");
		System.out.print("Brancas: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(brancas.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Pretas: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(pretas.toArray()));
		System.out.print(ANSI_RESET);
	}
	
}