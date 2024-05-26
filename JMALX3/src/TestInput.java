import java.util.ArrayList;
import java.util.List;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.TouchAdapter;

//import lejos.utility.Delay;


public class TestInput {	

	//1 Dit zwischen zwei gesendeten Symbolen
	//3 Dit zwischen Buchstaben in einem Wort
	//7 Dit schwischen Wörtern

	// create used Sensors
	static EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
	static TouchAdapter adapter = new TouchAdapter(touchSensor);

	// List to store inpput from TouchSensor
	static List<String> morseList = new ArrayList<String>();

	static long unit = 600;
	static long maxShortPress = unit;
	static long maxLongPress = 3 * unit;

	static long minTime = 

	public static void main(String[] args) {
		System.out.println("starting");
		while (true) {
			long start_press;
			long finish_press;

			long start_pause;
			long finish_pause;
			
			start_pause = System.currentTimeMillis();
	
			
			if (adapter.isPressed()) {
				//compute pause
				finish_pause = System.currentTimeMillis();
				long time_pause = finish_pause - start_pause;
				HandlePause(time_pause);


				
				System.out.print("pressed");
				// Sensor is presed, measure Time
				start_press = System.currentTimeMillis();

				while (true) {
					if (!adapter.isPressed()) {
						// Sensor isn't pressed anymore
						finish_press = System.currentTimeMillis();
						break;
					}
					Delay.msDelay(200);
				}
				// calculate pressed Time
				long time_pressed = finish_press - start_press;
				System.out.println(time_pressed);

				// Save time or stops programm
				
				//HandleTime(time);
			}

			Delay.msDelay(200);
		}

	}

	public static void HandleTime(long time) {
		if (time < maxShortPress) {
			// short
			morseList.add(".");
			System.out.println(".");

		} else if (time > maxShortPress && time < maxLongPress) {
			// long
			morseList.add("-"); // ToDo: - oder _ ???
			System.out.println(".");

		} else if (time > maxLongPress) {
			// stop programm
			System.exit(0);
		}

	}

	public static void HandlePause(long time){
		//ToDo
	}

}
