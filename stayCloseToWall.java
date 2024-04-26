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

public class stayCloseToWall {

	public static void main(String[] args) {
		
		System.out.println("Press any button to start");
		Button.waitForAnyPress();
		
		int maxSpeed = 300;
		
		//Create Motor & Sensor for Ultraschall
		RegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.B);
		motorRight.forward();
		
		RegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.C);
		motorLeft.forward();
		
		//EV3UltrasonicSensor sensorForward = new EV3UltrasonicSensor(SensorPort.S1);
		EV3UltrasonicSensor sensorRight = new EV3UltrasonicSensor(SensorPort.S2);
		
		//RangeFinder sonarForward = new RangeFinderAdapter(sensorForward);
		RangeFinder sonarRight = new RangeFinderAdapter(sensorRight);
		
		RegulatedMotor[] syncList = {motorLeft};
		
		motorRight.synchronizeWith(syncList);
		
		motorRight.startSynchronization();
		motorRight.setSpeed(maxSpeed);
		motorLeft.setSpeed(maxSpeed);
		motorRight.endSynchronization();
		
		//best value 1400
		while(Button.getButtons() == 0) {
		
			//float distanceValueF = sonarForward.getRange();
			float distanceValueR = sonarRight.getRange();
			
			System.out.println("Sensor: " + distanceValueR);
			
			if(10 <= distanceValueR && distanceValueR <= 15) {
				motorRight.startSynchronization();
				motorRight.setSpeed(maxSpeed);
				motorLeft.setSpeed(maxSpeed);
				motorRight.endSynchronization();
			} else if(distanceValueR >= 15) {
				//turn right 
				motorRight.startSynchronization();
				motorRight.setSpeed(maxSpeed -200);
				motorLeft.setSpeed(maxSpeed);
				motorRight.endSynchronization();
			} else if(distanceValueR <= 10) {
				//turn left
				motorRight.startSynchronization();
				motorRight.setSpeed(maxSpeed);
				motorLeft.setSpeed(maxSpeed -200);
				motorRight.endSynchronization();
			}
		}
		
		motorRight.startSynchronization();
		motorRight.close();
		motorLeft.close();
		motorRight.endSynchronization();
		
	}
}
