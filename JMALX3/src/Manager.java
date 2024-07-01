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
	static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);
	static ColorAdapter musicInputAdapter = new ColorAdapter(colorSensor);
	public static List<String> musicLetterArray = new ArrayList<String>();
	
	//variables for printing
	
	//music
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
    	       	
    	        Printing.startPrinting(MorseInput.normalWordArray);
    		}else {
    			//music
        		MusicInput.readMusicInput();
        		Printing.startPrintingNotes(musicLetterArray);        		
    		}
    		
    		boolean endPrinting = endPrinting();
    		if(endPrinting){
    			//motorY.rotate(5 * 360);
    			return;
    		}
    		
    		Delay.msDelay(500);

    	//play startmelody
		//idea 1: implement every single note for melody
		//idea 2: find a 10sec long melody in wav or mp3 format
	    //robot.playSample(<tag>, volume);
    	/*Sound.playTone(1175, 1000, 10);*/	
    	}
    }
    
    private static boolean modusSelection(){
    	System.out.println("Modus:");
    	System.out.println("Left: MORSEN");
    	System.out.println("Right: MUSIC");
    	System.out.println();
    	
		while(true) {
			if(Button.LEFT.isDown()) {
				return true;
			} else if(Button.RIGHT.isDown()) {
				return false;
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


}
