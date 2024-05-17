import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Morsen {
	public static void main(String[] args) {
		//storage structure
		//methods: addFirst, addLast, removeFirst, removeLast
		Deque<String> deque = new ArrayDeque<>();
		String poisonpill = "POISONPILL";
		
		while(true) {
			//input from sensor
			//translate to digital signal
			//@Jonathan
			String morseChar = "";
			//Leerzeichen einlesen durch Länge (Zeit)
			
			//translate digital signal to letters (mode 0)
			//@Lea
			String letter = translate(morseChar);
			if(letter == "error") {
				//error case, remove previous letter
				if(deque.size() >= 1) {
					deque.removeLast();
				}
			} else if(letter == "end") {
				//close everything
				deque.add(poisonpill);
				break;
			} else {
				deque.add(letter);
				//Print morsed letter
				System.out.println(letter);
			}
		}
		
		//start printer
		//Printing.startPrinting(deque);
	}
	
	public static String translate(String morseChar) {
		String[] language = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
	            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", 
	            "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
	            ".", ",", "?", "´", "/", ":", ";", "+", "-", "=", "start", "end", "error"};
		String[] morse = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", 
	            ".---", "-.-", ".-..", "--", "-.", "---", ".---.", "--.-", ".-.",
	            "-", "...", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----",
	            "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.",
	            "-----", ".-.-.-", "--..--", "..--..", ".----.", "-..-.", "---...", "-.-.-.", ".-.-.", "-....-", "-...-", "-.-.-", "...-.-", "........"};
		int idx = Arrays.asList(morse).indexOf(morseChar);
		return language[idx];
	}
}
