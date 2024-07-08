import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.TouchAdapter;
import lejos.hardware.Sound;
import lejos.robotics.Color;
import lejos.robotics.ColorAdapter;

public class MusicInput {

	static String[] noteList = {"c", "d", "e", "f", " ", " ", "g", "a", " ", " ", " ", " ", " ", "h"};
	
	public static void readMusicInput() {
		
		while(true) {
			//red 0 = c, green 1 = d, blue 2 = e, yellow 3 = f, , white 6 = g, black 7 = a, brown = 13
			int color = Manager.musicInputAdapter.getColorID();
			System.out.println(color);
			
			if(color == 0 || color == 1 || color == 2 || color == 3 || color == 6 || color == 7 || color == 13) {
				String note = noteList[color];
				int selection = askForConsent(note);
				if(selection == 0) {
					Manager.musicLetterArray.add(note);
				} else if(selection == 2) {
					return;
				}
			}
			
			MorseInput.clearOurDisplay(8);		
			
			for(int i = 0; i < Manager.musicLetterArray.size(); i++) {
				System.out.print(Manager.musicLetterArray.get(i));
			}
			
			System.out.println();
			
			Delay.msDelay(1000);
			
		}

	}
	
	static int askForConsent(String color) {
		System.out.println("Add note: \"" + color + "\"?");
		System.out.println("Left: YES");
		System.out.println("Right: NO");
		System.out.println("Up: PRINT");
    	System.out.println();
		
		while(true) {
			if(Button.LEFT.isDown()) {
				return 0;
			} else if(Button.RIGHT.isDown()) {
				return 1;
			} else if(Button.UP.isDown()) {
				return 2;
			}
		}
		
	}

}
