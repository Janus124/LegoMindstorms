import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.TouchAdapter;
import lejos.hardware.Sound;


public class MorseInput {

    // Create and initialize the touch sensor
    static EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S3);
    static TouchAdapter adapter = new TouchAdapter(touchSensor);

	
    // List to store morsecode
    static String morseLetter = "";
    static List<String> normalWordArray = new ArrayList<String>();
    static String normalWord= "";
/*
    // Time definitions in milliseconds
    static long unit = 1000;
    //static long ShortPress = 2 * unit;
    static long LongPress = unit;

    static long pauseSymbol = 2 * unit;
    static long pauseLetter = 4 * unit;
    //static long pauseWord = 7 * unit;
     */

    
    public static void ourprint() {
    	System.out.println("curr W:"+ normalWord);
    	for(int i = 0; i < normalWordArray.size(); i ++) {
    		System.out.print(normalWordArray.get(i) + "$");
    	}
    	System.out.print("\n");
    }
    
	//gets the input from the EV3 Touch Sensor and computes the duration of the time, when the sensor is pushed or paused 
    public static void GetInput() {
        long start_press;
        long finish_press;

        long start_pause = System.currentTimeMillis();
        long finish_pause;

        while (true) {
        	//waiting for press
        	if(Button.ENTER.isDown()) {
        		normalWordArray.add(normalWord);
				break;
			}        	

            if (adapter.isPressed()) {
                // Sensor is pressed, measure time
            	
                // Calculate pause duration (can only be calculated here)
                finish_pause = System.currentTimeMillis();
                long time_pause = finish_pause - start_pause;
                if(HandleInput("pause", time_pause) == 0) {
                	//pause is calculated, call HandleInput
                	return;
                }
                
                //when morser is pressed
                start_press = System.currentTimeMillis();
                while (true) {
                	//playTone
                	Sound.playTone(1175, 300, 5);
                    if (!adapter.isPressed()) {
                        // Sensor is no longer pressed
                        finish_press = System.currentTimeMillis();
                        break;
                    }
                }
                
                // Calculate pressed duration
                long time_pressed = finish_press - start_press;
                if(HandleInput("pressed", time_pressed) == 0) {
                	return;
                }
                start_pause = System.currentTimeMillis();
            }
        }
    }
    
    //gets a String of "." and "-", translates and adds the morse word to the normalWord array
    //retuns 0 when morsing should be ended
    private static int translateAndAddToNormalWord(String letter) {
    	letter = translate(letter);
    	if(letter == "ende") {
    		return 0;
    	}
        normalWord = normalWord + letter;
    	System.out.println("new: " + letter + "\n");
    	return 1;
    }
   
    
    private static int HandlePause(long time) {
    	/*
    	0-2 sec 	= pause zwischen Morsezeichen
    	2-4 sec		= pause zwischen Buchstaben
    	4+			= pause zwischen Wörtern
    	*/
    	//Handle Pause
        if (time <= 2000) {
        	//Info: pause between morseinputs
            morseLetter += "";

        } else if (time <= 4000) {
        	//Info: pause between letters
        	if(translateAndAddToNormalWord(morseLetter) == 0)
        		return 0;
        	
            morseLetter = "";
            ourprint();


        } else if (time > 4000) {
        	//Info: pause between words
        	
            // Word finished            
        	if("...-.-l" == morseLetter || ("...-.-" == morseLetter) || morseLetter.length() > 8 || ("......" == morseLetter)) {
        		//end
            	saveWordToArray();
        		System.out.println("Ende");
        		morseLetter = "";
                normalWord = "";
        		return 0;
        	}
        	
        	//translate and save
        	if(translateAndAddToNormalWord(morseLetter) == 0)
        		return 0;
        	saveWordToArray();

            normalWord = "";
            morseLetter = "";
            ourprint();
        }
       return 1;
    }
   
    private static int HandlePress(long time) {
    	//Handle Press
    	/*
    	0-1 sec 	= kurz
    	1+ 		 	= lang
    	 */
        if (time < 1000) {
        	System.out.print("s ");
            // Dot
            morseLetter += ".";
        } else if (time >= 1000) {
            // Dash
        	System.out.print("l ");
            morseLetter += "-";
        }
    	return 1;
    }
    
    
	//Converts the time with the type into the correct symbols of the morsecode
	//type = "pause" or "pressed"
	//time = lenght of the duration pressed or not pressed
    //-1 error
    //0 end
    //1 continue
    public static int HandleInput(String type, long time) {
    	
    	if (type.equals("pause")) {
    		return HandlePause(time);
    		
        } else if (type.equals("pressed")) {
        	return HandlePress(time);
 
        } else {
            return -1;
        }
    }

    public static void saveWordToArray() {
    	//add word to final array
        normalWordArray.add(normalWord);
    }
    
    public static String translate(String letter) {
		String[] language = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
	            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", 
	            "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
	            ".", ",", "?", "´", "/", ":", ";", "+", "-", "=", "ende", "removeLetter", 
	            "removeWord", "!"
	            };
		String[] morse = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", 
			    ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.",
			    "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----",
			    "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.",
			    "-----", ".-.-.-", "..--..", ".----.", "-..-.", "---...", "-.-.-.", ".-.-.", "-....-", "-...-", "......", "........",
			    ".......", "-.-.--"
				};
		
        int idx = Arrays.asList(morse).indexOf(letter);
        if(idx == -1) {
            return "";
        } else {
        	//special cases
        	String translatedLetter = language[idx];
        	if(translatedLetter == "removeLetter") {
        		//Remove last Letter
        		System.out.println("deleted Letter");
        		if(normalWord.length() == 0) { //Error Fall
        			normalWord = "";
        		} else {
        			normalWord = normalWord.substring(0, normalWord.length()-1);
        		}
        		return "";
	        } else if (translatedLetter == "removeWord"){
	        	//Remove last Word
        		System.out.println("deleted Word");
	        	//IDEA: first save 2 last Word, then deleate the last two and add the last
	        		if (!normalWordArray.isEmpty()) {
	        			normalWordArray.remove(normalWordArray.size()-1);
	        		}
	        		return "";
	  
	        } else if(translatedLetter == "ende"){
	        	return "ende";
	        	
	        }
        	//normal case
	         return translatedLetter;
	    }
    }
    
    private static void printCurrWord() {
    	LCD.drawString(normalWord, 0, 0);
    	
    	//LCD.drawString(normalWordArray.get(normalWordArray.size()-1), 0, 1);
    	return;
    }
    
    public static void clearOurDisplay(int num) {
    	
    	for(int i = 0; i < num; i++) {
        	System.out.println("");
    	}
    }
	
}
