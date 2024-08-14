package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partida = new PartidaDeXadrez();
		List<PecaDeXadrez> capturdas = new ArrayList<>();

		while (!partida.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printPartida(partida, capturdas);
				System.out.println();
				System.out.print("Posição que deseja movimentar: ");
				PosicaoXadrez pecaMov = UI.lerPosicaoXadrez(sc);

				boolean[][] movPossivel = partida.movimentosPossiveis(pecaMov);
				UI.clearScreen();
				UI.printDoTabuleiro(partida.getPeca(), movPossivel);

				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez dest = UI.lerPosicaoXadrez(sc);
				PecaDeXadrez pecaCap = partida.movimentoPeca(pecaMov, dest);

				if (pecaCap != null) {
					capturdas.add(pecaCap);
				}
				
				if(partida.getPromocao() != null) {
					System.out.print("Entre com a letra da peca que deseja promover: (B/T/C/Q): ");
					String type = sc.nextLine().toUpperCase();
					while(!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Q")) {
						System.out.print("Letra invalida, entre com a letra da peca que deseja promover: (B/T/C/Q): ");
						type = sc.nextLine().toUpperCase();
					}
					partida.colocarPecaPromovida(type);
				}

			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printPartida(partida, capturdas);
	}

}
