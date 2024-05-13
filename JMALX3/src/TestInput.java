import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.TouchAdapter;

//import lejos.utility.Delay;


public class TestInput {	

	// create used Sensors
	// ! Geht das?
	EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
	TouchAdapter adapter = new TouchAdapter(touchSensor);

	// List to store inpput from TouchSensor
	List<String> morseList = new ArrayList<String>();

	public static void main(String[] args) {

		while (true) {
			long start;
			long finish;

			if (adapter.isPressed()) {
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
				HandleTime(time);
			}

			Delay.msDelay(1000);
		}

	}

	public void HandleTime(long time) {
		if (time < 500) {
			// short
			morseList.add(".");

		} else if (time > 500 && time < 2000) {
			// long
			morseList.add("-"); // ToDo: - oder _ ???

		} else if (time > 2000) {
			// stop programm
			System.exit(0);
		}
	}

}
