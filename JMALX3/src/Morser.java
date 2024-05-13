import lejos.hardware.sensor.NXTTouchSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.BrickFinder;


public class Morser {
	public static void main(String[] args) {
    	NXTTouchSensor touchSensor = new NXTTouchSensor(BrickFinder.getDefault().getPort("S1"));
   	 
    	long pressedStartTime = 0;
    	long pressedDuration = 0;
   	 
    	while (true) {
        	float[] sample = new float[touchSensor.sampleSize()];
        	touchSensor.fetchSample(sample, 0);
        	//System.out.println(sample[]);
       	 
        	float rawValue = sample[0]; // Rohwert des Sensors
       	 
        	if (rawValue < 0.5) {
            	if (pressedStartTime == 0) {
                	pressedStartTime = System.currentTimeMillis(); // Starte Zeitmessung, wenn der Sensor gedrückt wird
            	}
        	} else {
            	if (pressedStartTime != 0) {
                	pressedDuration = System.currentTimeMillis() - pressedStartTime; // Berechne die Zeit, wenn der Sensor nicht gedrückt wird
                	System.out.println("Sensor wurde " + pressedDuration + " Millisekunden lang gedrückt");
                	pressedStartTime = 0; // Setze die Startzeit zurück
            	}
        	}
       	 
        	try {
            	Thread.sleep(100); // Kurze Pause, um die CPU nicht zu überlasten
        	} catch (InterruptedException e) {
            	e.printStackTrace();
        	}
    	}
	}

}
