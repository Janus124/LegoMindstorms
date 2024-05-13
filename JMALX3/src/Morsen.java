package legotests;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.port.SensorPort;
import java.util.ArrayDeque;
import java.util.Deque;

public class Morsen {
	//Sensor initialisieren | Port S1
	EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S1);

	String[] language = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", 
            "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            ".", ",", "?", "´", "/", ":", ";", "+", "-", "=", "start", "end", "error"};
	String[] morse = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", 
            ".---", "-.-", ".-..", "--", "-.", "---", ".---.", "--.-", ".-.",
            "-", "...", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----",
            "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.",
            "-----", ".-.-.-", "--..--", "..--..", ".----.", "-..-.", "---...", "-.-.-.", ".-.-.", "-....-", "-...-", "-.-.-", "...-.-", "........"};
	
	//time
	long startPressed = 0;
	long timePressed = 0;
	long startReleased = 0;
	long timeReleased = 0;
	
	//storage structure
	//methods: addFirst, addLast, removeFirst, removeLast
	Deque<Integer> deque = new ArrayDeque<>();
	
	while(true) {
		//read-in signal from sensor
		SensorMode signal = touch.getTouchMode();
		
		if(signal) {
			
		}
		//translate to digital signal
		//translate digital signal to letters (mode 0)
	}
}
