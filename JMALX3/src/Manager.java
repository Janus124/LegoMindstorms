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
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.TouchAdapter;
import lejos.hardware.Sound;

public class Manager {
	
	static EV3LargeRegulatedMotor motorY = new EV3LargeRegulatedMotor(MotorPort.B);
	
    public static void main(String[] args) {
    	
    	//Printing.initialize();
    	
    	while(true) {
    		boolean modus = modusSelection();
    		MorseInput.clearOurDisplay(8);
    		
    		if(modus) {
    			//morsen
    		}else {
    			//music
        		MusicInput.readMusicInput();
    		}
    		
    		boolean endPrinting = endPrinting();
    		if(endPrinting){
    			//motorY.rotate(5 * 360);
    		}
    		

    	//play startmelody
		//idea 1: implement every single note for melody
		//idea 2: find a 10sec long melody in wav or mp3 format
	    //robot.playSample(<tag>, volume);
    	/*Sound.playTone(1175, 1000, 10);
    	

    	
    	
    	MorseInput.clearOurDisplay();
    	
    	
    	Printing.newLine(4);
    	
    	System.out.println("Start morsing");
    	Sound.playTone(1175, 1000, 10);
    	
    	
    	MorseInput.GetInput();
       	
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
    	

        Printing.startPrinting(MorseInput.normalWordArray);*/
    	}
    }
    
    private static boolean modusSelection(){
    	System.out.println("Modus:");
    	System.out.println("Left: MORSEN");
    	System.out.println("Right: MUSIC");
		while(true) {
			if(Button.LEFT.isDown()) {
				return true;
			} else if(Button.RIGHT.isDown()) {
				return false;
			}
		}
    }
    
    private static boolean endPrinting(){
    	System.out.println("Do you want to end?");
    	System.out.println("Left: YES");
    	System.out.println("Right: NO");
		while(true) {
			if(Button.LEFT.isDown()) {
				return true;
			} else if(Button.RIGHT.isDown()) {
				return false;
			}
		}
    }


}
