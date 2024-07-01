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
    static List<String> morseLetterArray = new ArrayList<String>();
    static String morseLetter = "";
    static List<String> normalWordArray = new ArrayList<String>();
    static String normalWord= "";

    // Morse code symbols and pauses
    // . -> short press (1 unit)
    // - -> long press (3 units)
    // s -> short pause (1 unit)
    // l -> long pause (3 units)
    // w -> very long pause (7 units)

    // Time definitions in milliseconds
    static long unit = 1000;
    //static long ShortPress = 2 * unit;
    static long LongPress = unit;

    static long pauseSymbol = 2 * unit;
    static long pauseLetter = 4 * unit;
    //static long pauseWord = 7 * unit;

    static long tolerance = unit; //ToDo anpassen
    
    //TODO: fill out
    static int[] notes = {262, 294, 330, 349, 392, 440, 494, 
    		523, 587, 660, 698, 784, 880, 988, 
    		1047, 1175, 1319, 1397, 1568, 1760, 1976,
    		2093, 2349, 2637, 2794, 3136, 3520, 3951, 4186};
    
    //overview of notesname -> frequenz
    /*
    ---> (notation in english)
    notes[0]: NOTE_C4 = 262;
    notes[1]: NOTE_D4 = 294;
    notes[2]: NOTE_E4 = 330;
    notes[3]: NOTE_F4 = 349;
    notes[4]: NOTE_G4 = 392;
    notes[5]: NOTE_A4 = 440;
    notes[6]: NOTE_B4 = 494;
    notes[7]: NOTE_C5 = 523;
    notes[8]: NOTE_D5 = 587;
    notes[9]: NOTE_E5 = 660;
    notes[10]: NOTE_F5 = 698;
    notes[11]: NOTE_G5 = 784;
    notes[12]: NOTE_A5 = 880;
    notes[13]: NOTE_B5 = 988;
    notes[14]: NOTE_C6 = 1047;
    notes[15]: NOTE_D6 = 1175;
    notes[16]: NOTE_E6 = 1319;
    notes[17]: NOTE_F6 = 1397;
    notes[18]: NOTE_G6 = 1568;
    notes[19]: NOTE_A6 = 1760;
    notes[20]: NOTE_B6 = 1976;
    notes[21]: NOTE_C7 = 2093;
    notes[22]: NOTE_D7 = 2349;
    notes[23]: NOTE_E7 = 2637;
    notes[24]: NOTE_F7 = 2794;
    notes[25]: NOTE_G7 = 3136;
    notes[26]: NOTE_A7 = 3520;
    notes[27]: NOTE_B7 = 3951;  
    notes[28]: NOTE_C8 = 4186 
    */

    /*
    public static void main(String[] args) {
    
    	//play startmelody
		//idea 1: implement every single note for melody
    	//Star wars melody
    	starWarsMelody();
		//idea 2: find a 10sec long melody in wav or mp3 format
	    //robot.playSample(<tag>, volume);
    	
    	//Sound.playTone(1175, 1000, 10);
    	
    	//which mode?
    	//0: morse -> language
    	//1: morse -> notes
    	
    	Printing.initialize();
    	
    	
    	clearOurDisplay();
    	
    	
    	Printing.newLine(4);
    	
    	System.out.println("Start morsing");
    	Sound.playTone(1175, 1000, 10);
    	
    	
        GetInput();
       	
    	/*
    	normalWordArray.add("leo");
    	normalWordArray.add("strinkt!!");
    	normalWordArray.add("jonathan");
    	normalWordArray.add("strinkt");
    	normalWordArray.add("nicht?");
    	normalWordArray.add("nur");
    	normalWordArray.add("sonntags");
    	normalWordArray.add("nach");
    	normalWordArray.add("dem");
    	normalWordArray.add("duschen.");
    	

        Printing.startPrinting(normalWordArray);

    }
*/
    
    public static void ourprint() {
    	System.out.println("mw:" + morseLetter + ",nw:" + normalWord);
    	for(int i = 0; i < normalWordArray.size(); i ++) {
    		System.out.println(normalWordArray.get(i) + ", ");
    	}
		
    }
    

	//gets the input from the EV3 Touch Sensor and computes the duration of the time, when the sensor is pushed and paused 
    public static void GetInput() {
        //System.out.println("Starting");
        long start_press;
        long finish_press;

        long start_pause = System.currentTimeMillis();
        long finish_pause;

        while (true) {
        	//printTime(System.currentTimeMillis() - start_pause, "pause");
        	
        	//End listening to morser
        	if(Button.ENTER.isDown()) {
        		//ToDo
        		normalWordArray.add(normalWord);
				break;
			}
        	printCurrWord();
        	

            if (adapter.isPressed()) {
                // Sensor is pressed, measure time
            	
                // Calculate pause duration
                finish_pause = System.currentTimeMillis();
                long time_pause = finish_pause - start_pause;
                if(HandleInput("pause", time_pause) == 0) {
                	//endfire
                	
                	return;
                }

                //System.out.print("Pressed");
                start_press = System.currentTimeMillis();

                while (true) {
                	//playTone
                	Sound.playTone(1175, 300, 5);
                    if (!adapter.isPressed()) {
                    	//printTime(System.currentTimeMillis() - start_press, "pressed");
                        // Sensor is no longer pressed
                        finish_press = System.currentTimeMillis();
                        break;
                    }
                }
                // Calculate pressed duration
                long time_pressed = finish_press - start_press;
                //System.out.println(time_pressed);


                if(HandleInput("pressed", time_pressed) == 0) {
                	return;
                }
                start_pause = System.currentTimeMillis();
            }
        }
    }
    
    private static int translateAndAddToNormalWord(String letter) {
    	letter = translate(letter);
    	if(letter == "ende") {
    		return 0;
    	}
        normalWord = normalWord + letter;
    	System.out.println("letter finished: " + letter);
    	return 1;
    }
    
    private static int HandlePause(long time) {
    	//Handle Pause
        if (time <= pauseSymbol) {
        	//Info: normal Pause between Letters
            morseLetter += "";

        } else if (time <= pauseLetter) {
        	//
        	if(translateAndAddToNormalWord(morseLetter) == 0)
        		return 0;
        	
            morseLetter = "";
            
            //System.out.println(normalWord);
            ourprint();


        } else if (time > pauseLetter) {
            // Word finished            
        	if("...-.-l" == morseLetter || ("...-.-" == morseLetter) || morseLetter.length() > 8) {
        		//end
        		System.out.println("Ende");
        		morseLetter = "";
                normalWord = "";
        		return 0;
        	}
        	//finish word
        	if(translateAndAddToNormalWord(morseLetter) == 0)
        		return 0;
        	
        	System.out.println("Word finished: " + normalWord);
        	saveWordToArray();
        	
        	
        	/*
        	if(currWord.length() == 7 || currWord.length() >= 9 || (currWord.length() == 8 && currWord != "remove")) {
        		System.out.println("Error: wrong type");
        	}
        	*/
            normalWord = "";
            morseLetter = "";
            ourprint();
        }
       return 1;
    	
    }

    
    private static int HandlePress(long time) {
    	//Handle Press
        if (time < LongPress) {
        	//Sound.beep();
        	System.out.println("shortPress");
            // Dot
            morseLetter += ".";
        } else if (time >= LongPress) {
            // Dash
        	System.out.println("long press");
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
    	//System.out.println(type + ": " + time);Sound.playTone
    	
    	if (type.equals("pause")) {
    		return HandlePause(time);
    		
        } else if (type.equals("pressed")) {
        	return HandlePress(time);
 
        } else {
            //System.out.println("Error: wrong type");
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
	            /*, "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$",
	            "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$",
	            "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$",
	            "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$",
	            "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$",
	            "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$",
	            "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$",
	            "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$",
	            "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$", "$",
	            "$", "$", "$", "$"*/
	            };
		/*String[] morse = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", 
	            ".---", "-.-", ".-..", "--", "-.", "---", ".---.", "--.-", ".-.",
	            "-", "...", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----",
	            "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.",
	            "-----", ".-.-.-", "--..--", "..--..", ".----.", "-..-.", "---...", "-.-.-.", ".-.-.", "-....-", "-...-", "-.-.-", "........"};
	            */
		String[] morse = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", 
			    ".---", "-.-", ".-..", "--", "-.", "---", ".---.", "--.-", ".-.",
			    "-", "...", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----",
			    "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.",
			    "-----", ".-.-.-", "--..--", "..--..", ".----.", "-..-.", "---...", "-.-.-.", ".-.-.", "-....-", "-...-", "......", "........",
			    ".......", "..--"
			};
        int idx = Arrays.asList(morse).indexOf(letter);
        //System.out.print(idx);
        if(idx == -1) {
            //System.out.println("2");//Error translation: no idx");
            return "";
        } else {
        	String normal = language[idx];
        	if(normal == "removeLetter") {
        		//Remove last Letter
        		//System.out.println("rl: " + normalWord.length());
        		if(normalWord.length() == 0) { //Error Fall
        			normalWord = "";
        		} else {
        			normalWord = normalWord.substring(0, normalWord.length()-1);
        		}
        		return "";
	        } else if (normal == "removeWord"){
	        	//Remove last Word
	        		if (!normalWordArray.isEmpty()) {
	        			normalWordArray.remove(normalWordArray.size()-1);
	        		}
	        		return "";
	  
	        } else if(normal == "ende"){
	        	return "";
	        	
	        }else{
        	
	        }
	            return language[idx];
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
	
    private static void starWarsMelody() {
    	Sound.playTone(notes[0], 1000, 10);
    	Sound.playTone(notes[4], 1000, 10);
    	Sound.playTone(notes[3], 167, 10);
    	Sound.playTone(notes[2], 167, 10);
    	Sound.playTone(notes[1], 167, 10);
    	Sound.playTone(notes[7], 1000, 10);
    	Sound.playTone(notes[4], 500, 10);
    	
    	Sound.playTone(notes[3], 167, 10);
    	Sound.playTone(notes[2], 167, 10);
    	Sound.playTone(notes[1], 167, 10);
    	Sound.playTone(notes[7], 1000, 10);
    	Sound.playTone(notes[4], 500, 10);

    	Sound.playTone(notes[3], 167, 10);
    	Sound.playTone(notes[2], 167, 10);
    	Sound.playTone(notes[3], 167, 10);
    	Sound.playTone(notes[1], 1500, 10);
    }
}
