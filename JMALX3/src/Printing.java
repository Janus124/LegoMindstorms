import java.util.Deque;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Printing {
	
	public static void startPrinting(Deque<String> deque) {
		float speedX = 50;
		float speedY = 50;
		float speedZ = 50;
		
		char inputLetter;
		
		EV3LargeRegulatedMotor motorX = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor motorY = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3MediumRegulatedMotor motorZ = new EV3MediumRegulatedMotor(MotorPort.C);
		
		motorY.setSpeed(speedY);
		motorX.setSpeed(speedX);
		
		motorY.stop();
		motorX.stop();
		
		/*while(inputQueue.peek() != null) {
			//remove letter from queues and print it
			inputLetter = inputQueue.poll();
			printLetter(a);
		}*/
		
	}
	
	public void printLetter(char a) {
		switch(a) {
		case 1:
			printA();
			break;
		case 2:
			printB();
			break;
		case 3:
			printC();
			break;
		case 4:
			printD();
			break;
		case 5:
			printE();
			break;
		case 6:
			printF();
			break;
		case 7:
			printG();
			break;
		case 8:
			printH();
			break;
		case 9:
			printI();
			break;
		case 10:
			printJ();
			break;
		case 11:
			printK();
			break;
		case 12:
			printL();
			break;
		case 13:
			printM();
			break;
		case 14:
			printN();
			break;
		case 15:
			printO();
			break;
		case 16:
			printP();
			break;
		case 17:
			printQ();
			break;
		case 18:
			printR();
			break;
		case 19:
			printS();
			break;
		case 20:
			printT();
			break;
		case 21:
			printU();
			break;
		case 22:
			printV();
			break;
		case 23:
			printW();
			break;
		case 24:
			printX();
			break;
		case 25:
			printY();
			break;
		case 26:
			printZ();
			break;
		case 27:
			print1();
			break;
		case 28:
			print2();
			break;
		case 29:
			print3();
			break;
		case 30:
			print4();
			break;
		case 31:
			print5();
			break;
		case 32:
			print6();
			break;
		case 33:
			print7();
			break;
		case 34:
			print8();
			break;
		case 35:
			print9();
			break;
		case 36:
			print0();
			break;
		case 37: // .
			printDot();
			break;
		case 38: // ,
			printComma();
			break;
		case 39: // ?
			printQMark();
			break;
		case 40:// '
			printApostrophe();
			break;
		case 41: // /
			printSlash();
			break;
		case 42: // :
			printColon();
			break;
		case 43: // ;
			printSimile();
			break;
		case 44:// +
			printPlus();
			break;
		case 45:// -
			printMinus();
			break;
		case 46:// =
			printEquals();
			break;
		}
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
	
	// --------------- PRINT LETTER FUNCTIONS --------------- //
	// Prints the letter given by the morse code input        //
	
	private void printA() {
		
	}
	
	private void printB() {
		
	}
	
	private void printC() {
		
	}
	
	private void printD() {
		
	}
	
	private void printE() {
		
	}
	
	private void printF() {
		
	}
	
	private void printG() {
		
	}
	
	private void printH() {
		
	}
	
	private void printI() {
		
	}
	
	private void printJ() {
		
	}
	
	private void printK() {
		
	}
	
	private void printL() {
		
	}
	
	private void printM() {
		
	}
	
	private void printN() {
		
	}

	private void printO() {
		
	}
	
	private void printP() {
		
	}
	
	private void printQ() {
		
	}
	
	private void printR() {
		
	}
	
	private void printS() {
		
	}
	
	private void printT() {
		
	}
	
	private void printU() {
		
	}
	
	private void printV() {
		
	}
	
	private void printW() {
		
	}
	
	private void printX() {
		
	}

	private void printY() {
		
	}
	
	private void printZ() {
		
	}
	
	private void print1() {
		
	}
	
	private void print2() {
		
	}
	
	private void print3() {
		
	}
	
	private void print4() {
		
	}
	
	private void print5() {
		
	}
	
	private void print6() {
		
	}
	
	private void print7() {
		
	}
	
	private void print8() {
		
	}
	
	private void print9() {
		
	}
	
	private void print0() {
		
	}
	
	private void printDot() {
		
	}
	
	private void printComma() {
		
	}
	
	private void printQMark() {
		
	}
	
	private void printApostrophe() {
		
	}
	
	private void printSlash() {
		
	}
	
	private void printColon() {
		
	}

	private void printSimile() {
		
	}
	
	private void printPlus() {
		
	}
	
	private void printMinus() {
		
	}
	
	private void printEquals() {
		
	}
}