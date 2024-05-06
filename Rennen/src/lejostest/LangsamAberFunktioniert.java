package lejostest;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.RangeFinder;
import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.Gyroscope;
import lejos.robotics.GyroscopeAdapter;
import lejos.utility.Delay;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.navigation.MovePilot;

public class LangsamAberFunktioniert {

	public static void main(String[] args) {
		
		//Create Motor & Sensor for Ultraschall
		RegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.B);
		RegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.C);
		
		//worked with 650
		int maxSpeed = 650;
		//(int) motorRight.getMaxSpeed();
		
		System.out.println(maxSpeed);
		
		EV3UltrasonicSensor sensorForward = new EV3UltrasonicSensor(SensorPort.S1);
		EV3UltrasonicSensor sensorRight = new EV3UltrasonicSensor(SensorPort.S2);
		
		RangeFinder sonarForward = new RangeFinderAdapter(sensorForward);
		RangeFinder sonarRight = new RangeFinderAdapter(sensorRight);
		
		System.out.println("Press any button to start");
		Button.waitForAnyPress();
		
		RegulatedMotor[] syncList = {motorLeft};
		
		motorRight.synchronizeWith(syncList);
		
		motorRight.forward();
		motorLeft.forward();
		
		motorRight.startSynchronization();
		motorRight.setSpeed(maxSpeed);
		motorLeft.setSpeed(maxSpeed);
		motorRight.endSynchronization();
		
		//best value 1400
		while(Button.getButtons() == 0) {
		
			float distanceValueF = sonarForward.getRange();
			float distanceValueR = sonarRight.getRange();
			
			System.out.println("Sensor right: " + distanceValueR);
			//System.out.println("Sensor forward: " + distanceValueF);
			
			if(distanceValueF <= 30) {
				//turn left avoid wall
				motorRight.startSynchronization();
				motorRight.setSpeed(maxSpeed);
				//worked with -225
				motorLeft.setSpeed(maxSpeed -225);
				motorRight.endSynchronization();
			}else if(20 <= distanceValueR && distanceValueR <= 50) {
				//forward full speed
				motorRight.startSynchronization();
				motorRight.setSpeed(maxSpeed);
				motorLeft.setSpeed(maxSpeed);
				motorRight.endSynchronization();
			} else if(Float.isInfinite(distanceValueR)){
				//turn right door
				motorRight.startSynchronization();
				//worked with -225
				motorRight.setSpeed(maxSpeed -225);
				motorLeft.setSpeed(maxSpeed);
				motorRight.endSynchronization();
			} else if(distanceValueR >= 50) {
				//turn right light
				motorRight.startSynchronization();
				//worked with -50
				motorRight.setSpeed(maxSpeed -50);
				motorLeft.setSpeed(maxSpeed);
				motorRight.endSynchronization();
			}else if(distanceValueR <= 20) {
				//turn left light
				motorRight.startSynchronization();
				motorRight.setSpeed(maxSpeed);
				//worked with -50
				motorLeft.setSpeed(maxSpeed -50);
				motorRight.endSynchronization();
			}
			//best time: 0:53:00 min
			
			//TODO: Abstand zu Wand anpassen für F & R, differenzieren zw. F ist nah = fährt frontal auf wand und R ist nah, nur leicht korrigieren

		}
		
		motorRight.startSynchronization();
		motorRight.close();
		motorLeft.close();
		motorRight.endSynchronization();
		
	}
	
}
