package application;

import tabuleiroDeJogo.Tabuleiro;
import xadrez.PartidaDeXadrez;

public class Programa {

	public static void main(String[] args) {
		
		PartidaDeXadrez partida = new PartidaDeXadrez();
		UI.printDoTabuleiro(partida.getPeca());
	}

}
