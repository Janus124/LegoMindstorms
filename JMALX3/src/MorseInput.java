// If the code is inefficient, consider using StringBuilder
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
    static long ShortPress = unit;
    static long LongPress = 3 * unit;

    static long pauseSymbol = unit;
    static long pauseLetter = 3 * unit;
    static long pauseWord = 7 * unit;

    static long tolerance = unit; //ToDo anpassen

    public static void main(String[] args) {
    	
        GetInput();
        
    	/*
    	//ale ee/remove ==> ale e
    	
    	//a
    	HandleInput("pressed", unit);
    	HandleInput("pause", unit );
    	HandleInput("pressed", 3 * unit );
    	HandleInput("pause", 3 * unit);
    	ourprint();
    	
    	//l
    	HandleInput("pressed", unit);
    	HandleInput("pause", unit );
    	HandleInput("pressed", 3 * unit);
    	HandleInput("pause", unit );
    	HandleInput("pressed", unit);
    	HandleInput("pause", unit );
    	HandleInput("pressed", unit);
    	HandleInput("pause", 3 * unit );
    	ourprint();
    	
    	//e
    	HandleInput("pressed", unit);
    	HandleInput("pause", 7 * unit );
    	ourprint();
    	
    	//e
    	HandleInput("pressed", unit);
    	HandleInput("pause", 3 * unit );
    	ourprint();
    	
    	//e
    	HandleInput("pressed", unit);
    	HandleInput("pause", 7 * unit );
    	ourprint();
    	
    	//removeWord
    	HandleInput("pressed", unit);
    	HandleInput("pause", unit );
    	HandleInput("pressed", unit);
    	HandleInput("pause", unit );
    	HandleInput("pressed", unit);
    	HandleInput("pause", unit );
    	HandleInput("pressed", unit);
    	HandleInput("pause", unit );
    	HandleInput("pressed", unit);
    	HandleInput("pause", unit );
    	HandleInput("pressed", unit);
    	HandleInput("pause", unit );
    	HandleInput("pressed", unit);
    	System.out.println(morseLetter);
    	HandleInput("pause", 3* unit );
    	ourprint();
    	
    	//l
    	HandleInput("pressed", unit);
    	HandleInput("pause", unit );
    	HandleInput("pressed", 3 * unit);
    	HandleInput("pause", unit );
    	HandleInput("pressed", unit);
    	HandleInput("pause", unit );
    	HandleInput("pressed", unit);
    	HandleInput("pause", 3 * unit );
    	ourprint();
    	
    	//e
    	HandleInput("pressed", unit);
    	HandleInput("pause", 7 * unit );
    	ourprint();
    	
    	//e
    	HandleInput("pressed", unit);
    	HandleInput("pause", 3 * unit );
    	ourprint();
    	
    	//e
    	HandleInput("pressed", unit);
    	HandleInput("pause", 3 * unit );
    	ourprint();
    	
    	//
    	//HandleInput("pressed", );
    	//HandleInput("pause", );
    	//HandleInput("pressed", );
    	//HandleInput("pause", );
    	
     	
       	Delay.msDelay(500000);
       	//print
       	 */

        Printing.startPrinting(normalWordArray);

    }
    
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
                    if (!adapter.isPressed()) {
                    	printTime(System.currentTimeMillis() - start_press, "pressed");
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

	//Converts the time with the type into the correct symbols of the morsecode
	//type = "pause" or "pressed"
	//time = lenght of the duration pressed or not pressed
    //-1 error
    //0 end
    //1 continue
    public static int HandleInput(String type, long time) {
    	System.out.println(type + ": " + time);
    	
    	if (type.equals("pause")) {
            if (LongInRadius(time, pauseSymbol)) {
                morseLetter += "";

            } else if (LongInRadius(time, pauseLetter)) {
            	//System.out.println("puaseLetter");
                //Letter finished
                String letter = morseLetter;
                letter = translate(letter);
                normalWord = normalWord + letter;
            	//System.out.println("letter finished: " + letter);
                morseLetter = "";
                //System.out.println(normalWord);
                ourprint();


            } else if (time >= pauseWord) {
                // Word finished            
            	if("...-.-l" == morseLetter || ("...-.-" == morseLetter) || morseLetter.length() > 8) {
            		//end
            		morseLetter = "";
                    normalWord = "";
            		return 0;
            	}
            	//finish word
                String letter = morseLetter;
                letter = translate(letter);
                normalWord = normalWord + letter;
            	
            	//System.out.println("Word finished: " + normalWord);
            	
            	//add word to final array
                normalWordArray.add(normalWord);
            	/*
            	if(currWord.length() == 7 || currWord.length() >= 9 || (currWord.length() == 8 && currWord != "remove")) {
            		System.out.println("Error: wrong type");
            	}
            	*/
                normalWord = "";
                morseLetter = "";
                ourprint();
            } else {
                //System.out.println("Error 1");
                return -1;
            }
        } else if (type.equals("pressed")) {
            if (time < LongPress) {
            	//System.out.println("shortPress");
                // Dot
                morseLetter += ".";
            } else if (time >= LongPress) {
                // Dash
            	//System.out.println("long press");
                morseLetter += "-";
            } else {
                //System.out.println("Error: wrong time in HandleInput pressed");
                
            }
        } else {
            //System.out.println("Error: wrong type");
            return -1;
        }
        
    	//System.out.println("ml: " + morseLetter + "$" + normalWord);
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
	            ".", ",", "?", "´", "/", ":", ";", "+", "-", "=", "start", "removeLetter", 
	            "removeWord", "ü", "ö", "ä"
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
			    "-----", ".-.-.-", "--..--", "..--..", ".----.", "-..-.", "---...", "-.-.-.", ".-.-.", "-....-", "-...-", "-.-.-", "........",
			    ".......", "..--", "---.", ".-.-"
			};
		String normalLetter = "";
        int idx = Arrays.asList(morse).indexOf(letter);
        //System.out.print(idx);
        if(idx == -1) {
            //System.out.println("2");//Error translation: no idx");
            return "";
        } else {
        	String normal = language[idx];
        	if(normal == "removeLetter") {
        		//System.out.println("rl: " + normalWord.length());
        		if(normalWord.length() == 0) { //Error Fall
        			normalWord = "";
        		} else {
        			normalWord = normalWord.substring(0, normalWord.length()-1);
        		}
        		return "";
	        } else if (normal == "removeWord"){
	        	if(normalWord.length() == 0) {
	        		if (!normalWordArray.isEmpty()) {
	        			normalWordArray.remove(normalWordArray.size()-1);
	        		}
	        	} else {
	        		normalWord = "";
	        	}
	        	return "";
	        } else {
	            return language[idx];
	        }
        }
	}
    
    public static void printTime(long l, String type) {
    	if(l % 100 == 0) {
	    	//System.out.println("printTime1");
	    	if(l < 1000) {
	    		//System.out.println("printTime2");
	    		LCD.drawString("0" + Long.toString(l) + "$" + type, 0, 0);
	    	}else {
	    		//System.out.println("printTime3");
	    		LCD.drawString(Long.toString(l) + "$" + type, 0, 0);
	    	}
	    	LCD.drawString(morseLetter, 0, 1);
	    	LCD.drawString(normalWord, 0, 2);
    	}
    }
    
    public static void resetDisplay() {
       	LCD.drawString("                                       ", 0, 0);
        LCD.drawString("                                       ", 0, 1);
        LCD.drawString("                                       ", 0, 2);
}
	
}
