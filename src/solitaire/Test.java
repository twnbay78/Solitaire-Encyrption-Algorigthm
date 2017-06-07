package solitaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) 
	throws IOException {
		
		Solitaire ss = new Solitaire();
		Scanner input = new Scanner(System.in);
		ss.makeDeck(input);
		ss.printList(ss.deckRear);



	}
}
