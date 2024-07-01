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

public class Manager {
	
	
    public static void main(String[] args) {
        
    	//play startmelody
	//idea 1: implement every single note for melody
	//idea 2: find a 10sec long melody in wav or mp3 format
	    //robot.playSample(<tag>, volume);
    	Sound.playTone(1175, 1000, 10);
    	
    	Printing.initialize();
    	
    	
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
    	*/

        Printing.startPrinting(MorseInput.normalWordArray);

    }


}
