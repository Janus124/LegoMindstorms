import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.ColorAdapter;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.TouchAdapter;
import lejos.hardware.Sound;

public class Manager {
	//variable for music notes
	static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
	static ColorAdapter musicInputAdapter = new ColorAdapter(colorSensor);
	public static List<String> musicLetterArray = new ArrayList<String>();
	
	//variables for printing
	
	//music
    static int[] notes = {262, 294, 330, 349, 392, 440, 494, 
    		523, 587, 660, 698, 784, 880, 988, 
    		1047, 1175, 1319, 1397, 1568, 1760, 1976,
    		2093, 2349, 2637, 2794, 3136, 3520, 3951, 4186};

    public static void main(String[] args) {   	
    	
    	starWarsMelody();
    	
    	Printing.initialize();
		MorseInput.clearOurDisplay(8);
    	
    	while(true) {
    		boolean modus = modusSelection();
    		MorseInput.clearOurDisplay(8);
    		
    		if(modus) {
    			//morsen
    	    	System.out.println("Start morse code");
    	    	Sound.playTone(1175, 1000, 10);

    	    	MorseInput.GetInput();
    	    	
    	       	MorseInput.clearOurDisplay(8);

    	       	
    	        Printing.startPrinting(deleteEmptyStrings(MorseInput.normalWordArray));
    	        
    	        Printing.newLine(1);
    	        
    		}else {
    			//music
        		MusicInput.readMusicInput();
        		Printing.startPrintingNotes(musicLetterArray);   
        		
        		Printing.newLineNotes(1);
    		}
    		
    		boolean endPrinting = endPrinting();
    		if(endPrinting){
    			Printing.motorY.rotate(5 * 360);
    			return;
    		}
    		
    		Delay.msDelay(500);
    		
    		//reset lists
    		MorseInput.morseLetter = "";
    		MorseInput.normalWordArray = new ArrayList<String>();
    		MorseInput.normalWord= "";
    		
    	}
    }
    
    private static boolean modusSelection(){
    	System.out.println("Modus:");
    	System.out.println("Left: MUSIC");
    	System.out.println("Right: MORSEN");
    	System.out.println();
    	
		while(true) {
			if(Button.LEFT.isDown()) {
				return false;
			} else if(Button.RIGHT.isDown()) {
				return true;
			}
		}
    }
    
    private static boolean endPrinting(){
    	System.out.println("Want to end?");
    	System.out.println("Left: YES");
    	System.out.println("Right: NO");
    	System.out.println();
    	
		while(true) {
			if(Button.LEFT.isDown()) {
				return true;
			} else if(Button.RIGHT.isDown()) {
				return false;
			}
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

    private static List<String> deleteEmptyStrings(List<String> arr){
    	List<String> clearedArr = new ArrayList<String>();
    	
    	for (String s: arr) {
    		if (s.length() > 0)
    			clearedArr.add(s);
    	}
    	return clearedArr;
    }

}
