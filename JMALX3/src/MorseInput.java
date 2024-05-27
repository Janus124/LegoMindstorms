// If the code is inefficient, consider using StringBuilder
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.TouchAdapter;

public class MorseInput {

    // Create and initialize the touch sensor
    static EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
    static TouchAdapter adapter = new TouchAdapter(touchSensor);

    // List to store morsecode
    static List<String> morseWordArray = new ArrayList<String>();
    static String morseWord = "";
    static List<String> normalWordArray = new ArrayList<String>();
    static String normalWord= "";

    // Morse code symbols and pauses
    // . -> short press (1 unit)
    // - -> long press (3 units)
    // s -> short pause (1 unit)
    // l -> long pause (3 units)
    // w -> very long pause (7 units)

    // Time definitions in milliseconds
    static long unit = 600;
    static long ShortPress = unit;
    static long LongPress = 3 * unit;

    static long pauseSymbol = unit;
    static long pauseLetter = 3 * unit;
    static long pauseWord = 7 * unit;

    static long tolerance = unit / 2;

    public static void main(String[] args) {
        GetInput();


    }

	//gets the input from the EV3 Touch Sensor and computes the duration of the time, when the sensor is pushed and paused 
    public static void GetInput() {
        System.out.println("Starting");
        while (true) {
            long start_press;
            long finish_press;

            long start_pause;
            long finish_pause;

            start_pause = System.currentTimeMillis();

            if (adapter.isPressed()) {
                // Sensor is pressed, measure time

                // Calculate pause duration
                finish_pause = System.currentTimeMillis();
                long time_pause = finish_pause - start_pause;
                if(HandleInput("pause", time_pause) == 0) {
                	return;
                }

                System.out.print("Pressed");
                start_press = System.currentTimeMillis();

                while (true) {
                    if (!adapter.isPressed()) {
                        // Sensor is no longer pressed
                        finish_press = System.currentTimeMillis();
                        break;
                    }
                }
                // Calculate pressed duration
                long time_pressed = finish_press - start_press;
                System.out.println(time_pressed);


                if(HandleInput("pressed", time_pressed) == 0) {
                	return;
                }
            }
        }
    }

	//Converts the time with the type into the correct symbols of the morsecode
	//type = "pause" or "pressed"
	//time = lenght of the duration pressed or not pressed
    //-1 error
    //0 end
    //1 continue
    public static int HandleInput(String type, long time) {


/*        if (type.equals("pause")) {
            if (LongInRadius(time, pauseSymbol)) {
                morseWord += "s";

            } else if (LongInRadius(time, pauseLetter)) {
                //Letter finished
                String letter = morseWord;
                letter.replace("s", "");
                normalWord += translate(letter);
                morseWord = "";

            } else if (LongInRadius(time, pauseWord)) {
                // Word finished            
            	if(".s.s.s-s.s-l" == morseWord || (".s.s.s-s.s-" == morseWord) || time > 5000) {
            		//end start translation

            		//normalWords.add(normalLetters);
            		normalLetters = "";
                    normalWord = "";
            		return 0;
            	}

                normalWordArray.add(normalWord);
            	/*
            	if(currWord.length() == 7 || currWord.length() >= 9 || (currWord.length() == 8 && currWord != "remove")) {
            		System.out.println("Error: wrong type");
            	}
            	//kommentar zu
                normalWord = "";
                morseWord = "";
            } else {
                System.out.println("Error: wrong time in HandleInput pause");
                return -1;
            }
        } else if (type.equals("pressed")) {
            if (LongInRadius(time, ShortPress)) {
                // Dot
                morseWord += ".";
            } else if (LongInRadius(time, LongPress)) {
                // Dash
                morseWord += "-";
            } else {
                System.out.println("Error: wrong time in HandleInput pressed");
                return -1;
            }
        } else {
            System.out.println("Error: wrong type");
            return -1;
        }
        */
        return 1;
    }

	//checks if the number num is withing a tolerance of expected
    public static boolean LongInRadius(long num, long expected) {
        return expected - tolerance < num && expected + tolerance > num;
    }
    
    public static String translate(String letter) {
		String[] language = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
	            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", 
	            "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
	            ".", ",", "?", "´", "/", ":", ";", "+", "-", "=", "start", "remove"};
		String[] morse = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", 
	            ".---", "-.-", ".-..", "--", "-.", "---", ".---.", "--.-", ".-.",
	            "-", "...", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----",
	            "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.",
	            "-----", ".-.-.-", "--..--", "..--..", ".----.", "-..-.", "---...", "-.-.-.", ".-.-.", "-....-", "-...-", "-.-.-", "........"};
		String normalLetter = "";
        int idx = Arrays.asList(morse).indexOf(letter);
        if(idx == -1) {
            System.out.println("Error translation: no idx");
        } else {
            return language[idx];
        }
		
	}
}
