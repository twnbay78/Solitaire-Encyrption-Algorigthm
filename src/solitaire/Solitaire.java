package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	CardNode deckRear;

	public void makeDeckTest(){
		CardNode node = new CardNode();
		node.cardValue = 1;
		deckRear = node;
		
	}
	
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
		void jokerA() {	
		// Declare and initialize pointers
		CardNode ptr1 = deckRear;
		CardNode ptr2 = deckRear.next;
		CardNode prev = null;
		int counter = 0;
		
		if(ptr2.next.cardValue == 27 && ptr2.next == deckRear){
				CardNode temp = deckRear;
				ptr2.next = deckRear.next;
				deckRear = ptr2.next;		
				CardNode temp2 = deckRear.next;
				deckRear.next = temp;
				temp.next = temp2;
				return;
			}
		
		
		// Edge case - joker A is at rear
		if (deckRear.cardValue == 27){
			ptr2 = deckRear.next;
			CardNode ptr3 = deckRear.next.next;
			CardNode prev2 = null;
			while(ptr2.cardValue != deckRear.cardValue){
				prev2 = ptr2;
				ptr2 = ptr2.next;
			}
			deckRear = ptr2.next;
			System.out.println("prev2: " + prev2.next.cardValue);
			prev2.next = deckRear;
			deckRear.next = ptr2;
			ptr2.next = ptr3;
			return;
		}
		
		// Traverse and find joker A
		while (ptr1.cardValue != 27){
			prev = ptr1;
			ptr1 = ptr1.next;
		}

		// Pointer magic
		prev.next = ptr1.next;
		ptr1.next = prev.next.next;
		prev.next.next = ptr1;
	}
	
	void jokerB() {	
		// Ininitialize pointers
		CardNode prev = deckRear; 
		CardNode ptr1 = deckRear.next; 
		
		// Traverses through list
		while(ptr1 != deckRear){
			if(ptr1.cardValue == 28 && ptr1.next.next == deckRear){
				CardNode temp = ptr1;
				CardNode front = deckRear.next;
				prev.next= ptr1.next;
				deckRear.next = temp;
				deckRear = temp;
				temp.next = front;
				return;	
			}
			
			// If the first node in the list is jokerB
			else if(ptr1.cardValue == 28){
				CardNode temp = ptr1;
				CardNode temp2 = ptr1.next.next.next;
				prev.next = ptr1.next;
				prev.next.next.next = temp;
				temp.next = temp2;
				return;
			}
			
			// If the first node is jokerB and the last node is after it 
			else if(ptr1.cardValue == 28 && ptr1.next == deckRear){
				CardNode temp = ptr1;
				prev.next = ptr1.next;
				CardNode temp2 = deckRear.next;
				deckRear.next = temp;
				deckRear = temp;
				deckRear.next = temp2;
				temp = deckRear;
				prev.next.next = deckRear.next;
				deckRear = prev.next.next;
				temp2 = deckRear.next;
				deckRear.next = temp;
				temp.next = temp2;
				return;
			}
			
			// If second node is jokerB, and if it is the last
			else if(ptr1.next.cardValue == 28 && ptr1.next == deckRear){
				CardNode temp = deckRear;
				ptr1.next = deckRear.next;
				deckRear = ptr1.next;	
				CardNode temp2 = deckRear.next;
				deckRear.next = temp;
				temp.next = temp2;		
				CardNode temp3 = temp;
				deckRear.next = temp2;	
				CardNode temp4 = temp2.next;
				temp2.next = temp3;
				temp3.next = temp4;
				return;
			}
			
			
			ptr1=ptr1.next;
			prev = prev.next;
		}	
	}
	
	void tripleCut() {
		
		// Initialize and declare pointers
		CardNode ptr1 = deckRear;
		CardNode ptr2 = null;
		CardNode prev1 = null;
		CardNode prev2 = null;
		CardNode temp = null;
		CardNode ptr3 = null;
		CardNode ptr4 = null;
		CardNode temp2 = null;
		
		// Traverse through the list and locate first joker and first joker.prev
		while (ptr1.cardValue != 27 || ptr1.cardValue != 28){
			prev1 = ptr1;
			ptr1 = ptr1.next;
			if(ptr1.cardValue == 27 || ptr1.cardValue == 28){
				break;
			}
		}
		// Edge case - if a joker is at the end
		if (deckRear.cardValue == 27 || deckRear.cardValue == 28){
			prev1.next = ptr1.next;
			ptr1.next = deckRear.next;
			deckRear.next = ptr1;
			return;
		}
		// Traverse through the list and locate second joker (second joker.prev not needed)
		ptr2 = ptr1.next;
		while (ptr2.cardValue != 27 || ptr2.cardValue != 28){
			prev2 = ptr2;
			ptr2 = ptr2.next;
			if(ptr2.cardValue == 27 || ptr2.cardValue == 28){
				break;
			}
		}
		// Edge case - if a joker is at the beginning 
		if(deckRear.next.cardValue == 27 || deckRear.next.cardValue == 28){
			prev2.next = ptr2.next;
			ptr2.next = ptr1;
			deckRear.next = ptr2;
			deckRear = ptr2;
			return;
		}
		//Pointer magic
		ptr3 = ptr2.next;
		temp = deckRear.next;
		ptr4 = temp;
		deckRear.next = ptr1;
		while (temp.cardValue != prev1.cardValue){
			temp = temp.next;
		}
		temp2 = ptr3;
		while (temp2 != deckRear){
			temp2 = temp2.next;
		}
		temp2.next = ptr1;
		deckRear = temp;
		deckRear.next = ptr3;
		ptr2.next = ptr4;	
	}
	
	void countCut() {		
		
		// initialize pointers
		CardNode ptr1 = deckRear;
		CardNode ptr2 = deckRear;
		CardNode prev = deckRear.next;
		int rearValue = ptr1.cardValue;
		ptr1 = deckRear.next;
		
		// Edge case - 28 on end
		if(rearValue == 28){
			rearValue = 27;
			return;
		}
		
		// Find sublist
		for(int i = 0; i < rearValue; i++){
			ptr2 = ptr2.next;
		}
		
		// Find rear previous node
		while (prev.next != deckRear){
			prev = prev.next;
		}
		
		// Pointer magic
		deckRear.next = ptr2.next;
		ptr2.next = deckRear;
		prev.next = ptr1;
		
		
	}

	int getKey() {
		
		System.out.print("original ");
		printList(deckRear);
		jokerA();
		System.out.print("After jokerA ");
		printList(deckRear);
		jokerB();
		System.out.print("After jokerB ");
		printList(deckRear);
		tripleCut();
		System.out.print("After tripleCut ");
		printList(deckRear);
		countCut();
		System.out.print("After counCut ");
		printList(deckRear);
		
		// Looks at value of the first card
		CardNode ptr = deckRear;
		int value = deckRear.next.cardValue;
		
		// If this value is 28, it gets treated as 27 (because there are only 28 values in the deck)
		if(value == 28){
			value = 27;
		}
		
		// Traverses through the list 1 x (value) times
		for (int i = 0; i < value; i++){
			ptr = ptr.next;
		}
		
		int key = ptr.next.cardValue;
		
		// If the value IS NOT 27 or 28, repeat again (meaning the values will eventually be them)
		while (ptr.next.cardValue == 27 || ptr.next.cardValue == 28){
			jokerA();
			jokerB();
			tripleCut();
			countCut();
			ptr = deckRear;
			value = deckRear.next.cardValue;
			if(value == 28){
				value = 27;
			}
			for (int i = 0; i < value; i++){
				ptr = ptr.next;
			}
			key = ptr.next.cardValue;
			if(key!=27 || key!=28){	// values hit, so it will break
				break;
			}
			printList(deckRear);
		}
		return key;
	}

	public static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	public String encrypt(String message) {	
		String string = "";
		String input = message;
		for(int i=0; i<input.length(); i++){
			if(Character.isLetter(input.charAt(i)) == true){
			char letter = Character.toUpperCase(input.charAt(i));
			System.out.println("Letter: " + letter);
			int numLetter = letter-'A'+1;
			
			jokerA();
			jokerB();
			tripleCut();
			countCut();
			
			int key = getKey();
			int total = numLetter + key;
			
			if(total > 26){
				total = total-26;
			}
			
			letter = (char)(total-1+'A');
			System.out.print(letter);
			string = string + letter;
			}
		}
		System.out.print("eyy");
	    return string;
	}
	
	public String decrypt(String message) {	
		
		String string = "";
		String input = message;
		System.out.println(input);
		System.out.println(input);
		
		
		for(int i=0; i<input.length(); i++){
			if(Character.isLetter(input.charAt(i)) == true){
			char letter = Character.toUpperCase(input.charAt(i)); 
			
			System.out.println(letter);
			int numLetter = letter-'A'+1;
			
			jokerA();
			jokerB();
			tripleCut();
			countCut();
			
			int key = getKey();
			System.out.println("Key: " + key);
			int total = numLetter - key;
	
			if(total <= 0){
				total = total+26;
			}
		
			letter = (char)(total-1+'A');
			System.out.print(letter);
			string = string + letter;
			}
		}
	    return string;
	}
   
}
