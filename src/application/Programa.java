package application;

import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partida = new PartidaDeXadrez();
		
		while(true) {
		UI.printDoTabuleiro(partida.getPeca());
		System.out.println();
		System.out.print("Posição que deseja movimentar: ");
		PosicaoXadrez pecaMov = UI.lerPosicaoXadrez(sc);
		
		System.out.println();
		System.out.print("Destino: ");
		PosicaoXadrez dest = UI.lerPosicaoXadrez(sc);
		
		PecaDeXadrez pecaCap = partida.movimentoPeca(pecaMov, dest);
		
		}
	}

}
