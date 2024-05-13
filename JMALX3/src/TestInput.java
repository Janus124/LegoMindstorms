import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.TouchAdapter;

//import lejos.utility.Delay;


public class TestInput {	
	public static void main(String[] args) {
		EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
		
		TouchAdapter adapter = new TouchAdapter(touchSensor);
		while(true) {
			System.out.println(adapter.isPressed());
			
			Delay.msDelay(1000);
		}
		
		
	}

}

