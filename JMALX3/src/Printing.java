import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Printing {
	
	public static void main(String[] args) {
		float speedX = 50;
		float speedY = 50;
		float speedZ = 50;
		
		EV3LargeRegulatedMotor motorX = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor motorY = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3MediumRegulatedMotor motorZ = new EV3MediumRegulatedMotor(MotorPort.C);
		
		motorY.setSpeed(speedY);
		motorX.setSpeed(speedX);
		
		motorY.stop();
		motorX.stop();
		/*while(queue nicht leer) {
			lies aus und printletter
		}*/
		
	}
	
	public void printLetter(char a) {
		
	}
	
	public void printSpace() {
		
	}
	
	private void returnToLowerLeftCorner(EV3LargeRegulatedMotor motorY, EV3LargeRegulatedMotor motorX) {
		
	}
	
	public void setPen(EV3MediumRegulatedMotor motorZ) {
		motorZ.setSpeed(50);
		motorZ.stop();
	}
	
	private void liftPen(EV3MediumRegulatedMotor motorZ) {
		motorZ.setSpeed(-50);
		motorZ.stop();
	}
	
}
