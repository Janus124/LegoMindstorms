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

	// create used Sensors
	static EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
	static TouchAdapter adapter = new TouchAdapter(touchSensor);

	// List to store inpput from TouchSensor
	static List<String> morseList = new ArrayList<String>();
	
	static int maxShortPress = 700;
	static int maxLongPress = 1500;

	public static void main(String[] args) {
		System.out.println("starting");
		while (true) {
			long start;
			long finish;

			if (adapter.isPressed()) {
				System.out.print("pressed");
				// Sensor is presed, measure Time
				start = System.currentTimeMillis();

				while (true) {
					if (!adapter.isPressed()) {
						// Sensor isn't pressed anymore
						finish = System.currentTimeMillis();
						break;
					}
					Delay.msDelay(200);
				}
				// calculate pressed Time
				long time = finish - start;
				System.out.println(time);

				// Save time or stops programm
				
				//HandleTime(time);
			}

			Delay.msDelay(200);
		}

	}

	public static void HandleTime(long time) {
		if (time < 500) {
			// short
			morseList.add(".");
			System.out.println(".");

		} else if (time > 500 && time < 2000) {
			// long
			morseList.add("-"); // ToDo: - oder _ ???
			System.out.println(".");

		} else if (time > 2000) {
			// stop programm
			System.exit(0);
		}

	}

}
