import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.port.SensorPort;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Morsen {
	public static void main(String[] args) {
		//Sensor initialisieren | Port S1
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S1);
		
		//time
		long startPressed = 0;
		long timePressed = 0;
		long startReleased = 0;
		long timeReleased = 0;
		
		//storage structure
		//methods: addFirst, addLast, removeFirst, removeLast
		Deque<String> deque = new ArrayDeque<>();
		long deqSize = 0;
		
		while(true) {
			//input from sensor
			//translate to digital signal
			//@Jonathan
			String morseChar = "";
			
			//translate digital signal to letters (mode 0)
			//@Lea
			String letter = translate(morseChar);
			if(letter == "start") {
				//init Printer
			} else if(letter == "error") {
				//error case, remove previous letter
				if(deqSize >= 1) {
					deque.removeLast();
					deqSize--;
				}
			} else if(letter == "end") {
				//close everything
			} else {
				deque.addLast(letter);
				deqSize++;
			}
		}
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
