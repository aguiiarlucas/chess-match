package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessPosition;
import chess.ChessMatch;
import chess.ChessPiece;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();

		while (!chessMatch.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch,captured);
				System.out.println();

				System.out.println("Source: ");
				ChessPosition source = UI.readChessPosition(sc);

				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPe�as(), possibleMoves);

				System.out.println();
				System.out.println("Target: ");
				ChessPosition target = UI.readChessPosition(sc);

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				 if(capturedPiece!=null) {
					 captured.add(capturedPiece);
				 }
				if(chessMatch.getPromoted()!=null) {
					System.out.print("Enter a piece for promotion (B/C/T/Q): ");
					String type= sc.nextLine();
					while (!type.equalsIgnoreCase("B") && !type.equalsIgnoreCase("C") && !type.equalsIgnoreCase("T") && !type.equalsIgnoreCase("Q")) {
						
						System.out.print("Invalid value! Enter a piece for promotion (B/C/T/Q): ");
						 type= sc.nextLine();
					}
					chessMatch.replacePromotedPiece(type); //faz a troca
				}
				 
				 
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}

		}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
	}

}