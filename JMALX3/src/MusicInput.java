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
	static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);
	static ColorAdapter musicInputAdapter = new ColorAdapter(colorSensor);
	
	static List<String> musicLetterArray = new ArrayList<String>();
	static String c = "c";
	static String d = "d";
	static String e = "e";
	static String f = "f";
	static String g = "g";
	static String a = "a";
	static String h = "h";
	
	static void readMusicInput() {
		
		while(musicLetterArray.size() < 10) {
			//red 0, green 1, blue 2, yellow 3, , white 6, black 7
			int color = musicInputAdapter.getColorID();
			
			//System.out.println("Color Before: " + colorBefore);
			//System.out.println("Color: " + color);
			
			if(color == 0) {
				askForConsent(c);
				musicLetterArray.add(c);
			}else if(color == 1){
				askForConsent(d);
				musicLetterArray.add(d);
			}else if(color == 2){
				askForConsent(e);
				musicLetterArray.add(e);
			}else if(color == 3){
				askForConsent(f);
				musicLetterArray.add(f);
			}else if(color == 6){
				askForConsent(g);
				musicLetterArray.add(g);
			}else if(color == 7){
				askForConsent(a);
				musicLetterArray.add(a);
			}
			
			MorseInput.clearOurDisplay(8);		
			
			for(int i = 0; i < musicLetterArray.size(); i++) {
				System.out.print(musicLetterArray.get(i));
			}
			
			System.out.println();
			
			Delay.msDelay(1000);
			
		}
		
		
		Delay.msDelay(5000);
	}
	
	static boolean askForConsent(String color) {
		System.out.println("Add note: \"" + color + "\"?");
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
