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
		
		System.out.println("Press any button to start");
		Button.waitForAnyPress();
		
		int maxSpeed = 250;
		
		//Create Motor & Sensor for Ultraschall
		RegulatedMotor motorRight = new EV3MediumRegulatedMotor(MotorPort.B);
		motorRight.backward();
		
		RegulatedMotor motorLeft = new EV3MediumRegulatedMotor(MotorPort.C);
		motorLeft.forward();
		
		EV3UltrasonicSensor sensorForward = new EV3UltrasonicSensor(SensorPort.S1);
		EV3UltrasonicSensor sensorRight = new EV3UltrasonicSensor(SensorPort.S2);
		
		RangeFinder sonarForward = new RangeFinderAdapter(sensorForward);
		RangeFinder sonarRight = new RangeFinderAdapter(sensorRight);
		
		//for rotation
		/*Wheel wheelL = WheeledChassis.modelWheel(motorLeft, 80).offset(15);
		Wheel wheelR = WheeledChassis.modelWheel(motorRight, 80).offset(-15);
		WheeledChassis chassis = new WheeledChassis(new Wheel[] {wheelL, wheelR}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		//set speed
		pilot.setAngularSpeed(3000);*/
		
		RegulatedMotor[] syncList = {motorLeft};
		
		motorRight.synchronizeWith(syncList);
		
		motorRight.startSynchronization();
		motorRight.setSpeed(maxSpeed);
		motorLeft.setSpeed(maxSpeed);
		motorRight.endSynchronization();
		
		//best value 1400
		while(Button.getButtons() == 0) {
		
			float distanceValueF = sonarForward.getRange();
			float distanceValueR = sonarRight.getRange();
			
			System.out.println("Sensor: " + distanceValueR);
			
			//forward full speed
			if(10 <= distanceValueR && distanceValueR <= 25) {
				motorRight.startSynchronization();
				motorRight.setSpeed(maxSpeed);
				motorLeft.setSpeed(maxSpeed);
				motorRight.endSynchronization();
			} else if(distanceValueR >= 25) {
				//turn right 
				motorRight.startSynchronization();
				//pilot.rotate(-30);
				//motorRight.backward();
				motorRight.setSpeed(maxSpeed -400);
				motorLeft.setSpeed(maxSpeed);
				motorRight.endSynchronization();
			} else if(distanceValueR <= 10) {
				//turn left
				motorRight.startSynchronization();
				//pilot.rotate(30);
				//motorLeft.backward();
				motorRight.setSpeed(maxSpeed);
				motorLeft.setSpeed(maxSpeed -400);
				motorRight.endSynchronization();
			}

		}
		
		motorRight.startSynchronization();
		motorRight.close();
		motorLeft.close();
		motorRight.endSynchronization();
		
	}
	
}
