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
	
	static List<String> morseLetterArray = new ArrayList<String>();
	
	static void main(String args[]) {
		while(true) {
			int color = musicInputAdapter.getColorID();
			
			if(color < 3) {
				
			}else {
				
			}
		}
	}
}
