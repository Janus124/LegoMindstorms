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

public class A180DegreeDrift {

	public static void main(String[] args) {
		
		System.out.println("Press any button to start");
		Button.waitForAnyPress();
		
		int turnCounter = 0;
		int maxSpeed = 150;
		
		//Create Motor & Sensor for Ultraschall
		RegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.B);
		motorRight.forward();
		
		RegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.C);
		motorLeft.forward();
		
		EV3UltrasonicSensor sensorForward = new EV3UltrasonicSensor(SensorPort.S1);
		EV3UltrasonicSensor sensorRight = new EV3UltrasonicSensor(SensorPort.S2);
		
		
		
		RangeFinder sonarForward = new RangeFinderAdapter(sensorForward);
		RangeFinder sonarRight = new RangeFinderAdapter(sensorRight);
		
		RegulatedMotor[] syncList = {motorLeft};
		
		motorRight.synchronizeWith(syncList);
		
		//Rotate
		//Drehen mit default-offset-wert = 10
		Wheel wheelL = WheeledChassis.modelWheel(motorLeft, 80).offset(10);
		Wheel wheelR = WheeledChassis.modelWheel(motorRight, 80).offset(-10);
		WheeledChassis chassis = new WheeledChassis(new Wheel[] {wheelL, wheelR}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		//best value 1400
		while(Button.getButtons() == 0) {
		
			float distanceValueF = sonarForward.getRange();
			float distanceValueR = sonarRight.getRange();
			
			if (turnCounter == 2) {
				//long wall with bin
				 if(10 <= distanceValueR && distanceValueR <= 15) {
					MoveForward( motorRight, motorLeft, maxSpeed);
				} else if(distanceValueR >= 15) {
					//turn right 
					motorRight.startSynchronization();
					motorRight.setSpeed(maxSpeed -30);
					motorLeft.setSpeed(maxSpeed);
					motorRight.endSynchronization();
				} else if(distanceValueR <= 10 || distanceValueF <= 15) {
					//turn left & handle bin
					motorRight.startSynchronization();
					motorRight.setSpeed(maxSpeed);
					motorLeft.setSpeed(maxSpeed -30);
					motorRight.endSynchronization();
				}
				
			}else if(10 <= distanceValueR && distanceValueR <= 15) {
				MoveForward( motorRight, motorLeft, maxSpeed);
			} else if(distanceValueR >= 15 && distanceValueR <= 25) {
				//turn right 
				motorRight.startSynchronization();
				motorRight.setSpeed(maxSpeed -30);
				motorLeft.setSpeed(maxSpeed);
				motorRight.endSynchronization();
			} else if(distanceValueR <= 10) {
				//turn left
				motorRight.startSynchronization();
				motorRight.setSpeed(maxSpeed);
				motorLeft.setSpeed(maxSpeed -30);
				motorRight.endSynchronization();
			} else if (distanceValueR > 25) {
				//180° kurve
				//turn right 
				MoveForward( motorRight, motorLeft, maxSpeed / 2);				
				Delay.msDelay(100);
				
				MoveForward( motorRight, motorLeft, 0);				
				Delay.msDelay(100);
				
				pilot.rotate(90);
				
				MoveForward( motorRight, motorLeft, maxSpeed / 2);	
				
				Delay.msDelay(1000);
				
				pilot.rotate(100);
				
				turnCounter++;
				
			}
		}
		
		motorRight.startSynchronization();
		motorRight.close();
		motorLeft.close();
		motorRight.endSynchronization();
	}
	
	
	
	public static void MoveForward(RegulatedMotor motorRight, RegulatedMotor motorLeft, int speed) {
		motorRight.startSynchronization();
		motorRight.setSpeed(speed);
		motorLeft.setSpeed(speed);
		motorRight.endSynchronization();

	}
}
