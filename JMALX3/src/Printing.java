import java.util.Deque;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
import lejos.robotics.TouchAdapter;
import lejos.robotics.Color;
import lejos.robotics.ColorAdapter;

public class Printing {
	
	static EV3LargeRegulatedMotor motorX = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor motorY = new EV3LargeRegulatedMotor(MotorPort.B);
	static EV3MediumRegulatedMotor motorZ = new EV3MediumRegulatedMotor(MotorPort.C);
	
	//to calibrate pen in zero-position on x-axis
	static EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
	static TouchAdapter penAdapter = new TouchAdapter(touchSensor);
	
	//Feed sheet into printer until it is not covering the sensor anymore, then reach start position
	static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
	static ColorAdapter paperVisibleAdapter = new ColorAdapter(colorSensor);
	
	public static void main(String[] args) {
		//liftPen();
		String[] a = new String[2];
		startPrinting(a);
		
		/*
		
		Printing print = new Printing();
		
		//print.setPen(motorZ);
		//rotate left with minus degree
		//right plus degree
		//4 Umdrehungen
		//motorX.rotate(-1440);
		//Delay.msDelay(5000);
		
		//positiv ist hinten
		//negativ vorne
		//motorY.rotate(360);
		//
		
		//print.liftPen(motorZ);
		
		//ende der seite y
		//5,5 umdrehungen
		motorY.rotate(-1980);
		Delay.msDelay(5000);

		/*eigene methode

		int i = 0;
		while(i<5) {
			motorZ.rotateTo(90);
			System.out.println("rotate");
			i++;
		}
  
		*/
	}
	
	public static void initialize() {
		//-------- SYNC MOTOR X and Y -------- //
		
		EV3LargeRegulatedMotor[] syncList = {motorX};
		motorY.synchronizeWith(syncList);
		
		//-------- START FEEDING SHEET INTO PRINTER -------- //
		
		System.out.println("Press UP for forward.");
		System.out.println("Press DOWN for backward.");
		System.out.println("Press Enter when done.");
		
		while(true) {
			
			//sheet is fed well into printer, start reaching start position
			if(Button.ENTER.isDown()) {
				motorY.stop();
				break;
			}
			
			//move paper backwards
			if(Button.UP.isDown()) {
				motorY.forward();
				Delay.msDelay(1000);
			}
			
			//move paper forward
			if(Button.DOWN.isDown()) {
				motorY.backward();
				Delay.msDelay(1000);
			}
			
		}
		
		// -------- START REACHING START POSITION -------- //
		motorY.forward();
		
		while(true) {
			
			int i = paperVisibleAdapter.getColorID();
			
			if(i <= 2 || i >= 7) {
				motorY.stop();
				break;
			}
		}
		
		Delay.msDelay(3000);
		
		//Gehe auf richtige Höhe bei Initializierung
		//Move sheet backwards
		motorY.rotate(180);
		Delay.msDelay(3000);
		
		//Move to the right
		motorX.forward();
		
		while(true) {
			if(penAdapter.isPressed()) {
				motorX.stop();
				break;
			}
		}

		// -------- STOP MOTOR -------- //
		
		motorY.stop();
		motorX.stop();
	}
	
	public static void startPrinting(String[] words) {
		
		char inputLetter;
		
		initialize();
		
		setPen();
		
		//links
		motorX.rotate(-90);
		//hinten
		motorY.rotate(-90);
		//rechts
		motorX.rotate(90);
		//vorne
		motorY.rotate(90);
		/*while(inputQueue.peek() != null) {
			//remove letter from queues and print it
			inputLetter = inputQueue.poll();
			printLetter(a);
		}*/
		
	}
	
	public static void printLetter(char a) {
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
	
	public static void printSpace() {
		
	}
	
	private static void returnToLowerLeftCorner() {
		
	}
	
	public static void setPen() {

		motorZ.rotate(180);
		Delay.msDelay(1000);
		motorZ.stop();
	}
	
	private static void liftPen() {

		motorZ.rotate(-180);
		Delay.msDelay(1000);
		motorZ.stop();
	}
	
	// --------------- PRINT LETTER FUNCTIONS --------------- //
	// Prints the letter given by the morse code input        //
	
	private static void printA() {
		
	}
	
	private static void printB() {
		
	}
	
	private static void printC() {
		
	}
	
	private static void printD() {
		
	}
	
	private static void printE() {
		
	}
	
	private static void printF() {
		
	}
	
	private static void printG() {
		
	}
	
	private static void printH() {
		
	}
	
	private static void printI() {
		
	}
	
	private static void printJ() {
		
	}
	
	private static void printK() {
		
	}
	
	private static void printL() {
		
	}
	
	private static void printM() {
		
	}
	
	private static void printN() {
		
	}

	private static void printO() {
		
	}
	
	private static void printP() {
		
	}
	
	private static void printQ() {
		
	}
	
	private static void printR() {
		
	}
	
	private static void printS() {
		
	}
	
	private static void printT() {
		
	}
	
	private static void printU() {
		
	}
	
	private static void printV() {
		
	}
	
	private static void printW() {
		
	}
	
	private static void printX() {
		
	}

	private static void printY() {
		
	}
	
	private static void printZ() {
		
	}
	
	private static void print1() {
		
	}
	
	private static void print2() {
		
	}
	
	private static void print3() {
		
	}
	
	private static void print4() {
		
	}
	
	private static void print5() {
		
	}
	
	private static void print6() {
		
	}
	
	private static void print7() {
		
	}
	
	private static void print8() {
		
	}
	
	private static void print9() {
		
	}
	
	private static void print0() {
		
	}
	
	private static void printDot() {
		
	}
	
	private static void printComma() {
		
	}
	
	private static void printQMark() {
		
	}
	
	private static void printApostrophe() {
		
	}
	
	private static void printSlash() {
		
	}
	
	private static void printColon() {
		
	}

	private static void printSimile() {
		
	}
	
	private static void printPlus() {
		
	}
	
	private static void printMinus() {
		
	}
	
	private static void printEquals() {
		
	}
}
