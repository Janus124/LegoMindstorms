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








































public class MJ{








    public static void main(String[] args) {








        int maxSpeed = 300;
        int turnCounter = 0;


        // Create Motors & Sensor for Ultraschall
        RegulatedMotor mr = new EV3MediumRegulatedMotor(MotorPort.B);
        RegulatedMotor ml = new EV3MediumRegulatedMotor(MotorPort.C);
        /*EV3UltrasonicSensor sensorRight = new EV3UltrasonicSensor(SensorPort.S2);
        RangeFinder sonarRight = new RangeFinderAdapter(sensorRight);
        EV3UltrasonicSensor sensorForward = new EV3UltrasonicSensor(SensorPort.S2);
        RangeFinder sonarForward = new RangeFinderAdapter(sensorRight);
     
        float  i = sonarForward.getRange();
        float j = sonarRight.getRange();
        System.out.println(i + j);
        //!2. Sensor (nach vorne)*/








        Wheel wheelL = WheeledChassis.modelWheel(ml, 8).offset(7);
        Wheel wheelR = WheeledChassis.modelWheel(mr, 8).offset(-7);
        WheeledChassis chassis = new WheeledChassis(new Wheel[] {wheelL, wheelR}, WheeledChassis.TYPE_DIFFERENTIAL);
        MovePilot pilot = new MovePilot(chassis);
        pilot.setAngularSpeed(50); //gradProSekunde
        //! Zahlen anpassen








        //Synchronize
        RegulatedMotor[] syncList = {ml};
        mr.synchronizeWith(syncList);








        while(Button.getButtons() == 0) {




/*


            float distanceValueR = sonarRight.getRange();
            //System.out.println(distanceValueR);
            if (turnCounter != 2){
                if(10 <= distanceValueR && distanceValueR <= 40) {
                    forward(mr, ml, maxSpeed);
                } else if(distanceValueR >= 40) {
                    //turn right
                    turn(mr, ml, maxSpeed - maxSpeed/2, maxSpeed);
                } else if(distanceValueR <= 10) {
                    //turn left
                    turn(mr, ml, maxSpeed, maxSpeed - maxSpeed/2);
                }   else if(Float.isInfinite(distanceValueR) && turnCounter == 0){
                    //180°
                    firstDoor(mr, ml, maxSpeed, pilot);
                    turnCounter ++;
                } else if(Float.isInfinite(distanceValueR) && turnCounter == 1){
                    //180°
                    secondDoor(mr, ml, maxSpeed, pilot);
                    turnCounter ++;
                }
            } else{ //turnCounter == 2
                if(50 <= distanceValueR && distanceValueR <= 75) {
                    forward(mr, ml, maxSpeed);
                } else if(distanceValueR >= 75 || Float.isInfinite(distanceValueR)) {
                    //turn right
                    turn(mr, ml, maxSpeed - maxSpeed/2, maxSpeed);
                } else if(distanceValueR <= 50) {
                    //turn left
                    turn(mr, ml, maxSpeed, maxSpeed - maxSpeed/2);
                }
            }
     
        */
        }
        forward(mr, ml, maxSpeed/2);
        Delay.msDelay(2000);


        pilot.rotate(90);
     
        forward(mr, ml, maxSpeed/2);
        Delay.msDelay(2000);












    }








    public static void firstDoor(RegulatedMotor mr, RegulatedMotor ml, int speed, MovePilot pilot){
        System.out.println("first door");
        forward(mr, ml, speed/2);
        Delay.msDelay(100);








        forward(mr, ml, 0);
        Delay.msDelay(100);








        pilot.rotate(90);








        forward(mr, ml, speed/2);
        Delay.msDelay(2000);
        //wie lange?








        pilot.rotate(100);
        MoveForwardFor(mr, ml, speed, 2000);
    }
    public static void secondDoor(RegulatedMotor mr, RegulatedMotor ml, int speed, MovePilot pilot){
     System.out.println("second door");
        forward(mr, ml, speed/2);
        Delay.msDelay(100);


        forward(mr, ml, 0);
        Delay.msDelay(100);


        pilot.rotate(-90);


        forward(mr, ml, speed/2);
        Delay.msDelay(3000);
        //wie lange?


        pilot.rotate(-100);
        MoveForwardFor(mr, ml, speed, 2000);
    }








    // Methode zum Vorwärtsfahren
    public static void forward(RegulatedMotor mr, RegulatedMotor ml, int speed){
        mr.startSynchronization();
        mr.setSpeed(speed);
        ml.setSpeed(speed);
        mr.forward();
        ml.forward();
        mr.endSynchronization();








    }








    // Methode zum Drehen mit unterschiedlichen Geschwindigkeiten
    public static void turn(RegulatedMotor mr, RegulatedMotor ml, int speedr, int speedl){
        mr.startSynchronization();
        mr.setSpeed(speedr);
        ml.setSpeed(speedl);
        mr.forward();
        ml.forward();
        mr.endSynchronization();
    }








    public static void MoveForwardFor(RegulatedMotor mr, RegulatedMotor ml, int speed, int time){
        mr.startSynchronization();
        mr.setSpeed(speed);
        ml.setSpeed(speed);
        mr.forward();
        ml.forward();
        mr.endSynchronization();
        Delay.msDelay(time);








    }
































   
}












































