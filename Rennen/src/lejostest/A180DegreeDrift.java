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
		int maxSpeed = 300;
		
		//Create Motor & Sensor for Ultraschall
		RegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.B);
		RegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.C);
		
		EV3UltrasonicSensor sensorForward = new EV3UltrasonicSensor(SensorPort.S1);
		EV3UltrasonicSensor sensorRight = new EV3UltrasonicSensor(SensorPort.S2);
		
		RangeFinder sonarForward = new RangeFinderAdapter(sensorForward);
		RangeFinder sonarRight = new RangeFinderAdapter(sensorRight);
		
		RegulatedMotor[] syncList = {motorLeft};
		
		motorRight.synchronizeWith(syncList);
		
		//Rotate
		//Drehen mit default-offset-wert = 10
		Wheel wheelL = WheeledChassis.modelWheel(motorLeft, 80).offset(15);
		Wheel wheelR = WheeledChassis.modelWheel(motorRight, 80).offset(-15);
		WheeledChassis chassis = new WheeledChassis(new Wheel[] {wheelL, wheelR}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		pilot.setAngularSpeed(maxSpeed);
		
		motorRight.forward();
		motorLeft.forward();
		
		//best value 1400
		while(Button.getButtons() == 0) {
		
			float distanceValueF = sonarForward.getRange();
			float distanceValueR = sonarRight.getRange();
			
			if (turnCounter == 2) {
				//long wall with bin
				 if(10 <= distanceValueR && distanceValueR <= 25) {
					MoveForward( motorRight, motorLeft, maxSpeed);
				} else if(distanceValueR >= 25) {
					//turn right 
					motorRight.startSynchronization();
					pilot.setLinearSpeed(50);  // cm per second
					pilot.rotate(-30);   
					pilot.travel(50);         // cm
					motorRight.endSynchronization();
				} else if(distanceValueR <= 10 || distanceValueF <= 15) {
					//turn left & handle bin
					motorRight.startSynchronization();
					pilot.setLinearSpeed(50);  // cm per second
					pilot.rotate(30);
					pilot.travel(50);  
					motorRight.endSynchronization();
				}
				
			}else if(10 <= distanceValueR && distanceValueR <= 25) {
				MoveForward( motorRight, motorLeft, maxSpeed);
			} else if(distanceValueR >= 25 && distanceValueR <= 35) {
				//turn right 
				motorRight.startSynchronization();
				pilot.setLinearSpeed(50);  // cm per second
				pilot.rotate(-30);   
				pilot.travel(50);         // cm
				motorRight.endSynchronization();
			} else if(distanceValueR <= 10) {
				//turn left
				motorRight.startSynchronization();
				pilot.setLinearSpeed(50);  // cm per second
				pilot.rotate(30);
				pilot.travel(50);         // cm
				motorRight.endSynchronization();
			} else if (distanceValueR > 35 || Float.isInfinite(distanceValueR)) {
				//180° kurve
				//turn right 
				
				//90°
				pilot.setLinearSpeed(50);  // cm per second
				pilot.rotateRight();
				pilot.travel(50);   
				
				//90°
				pilot.rotate(-100);
				pilot.travel(50); 
				
				turnCounter++;
				System.out.println("Turns: " + turnCounter);
				
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
