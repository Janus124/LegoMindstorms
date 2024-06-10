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
		

		// ------- LIFT PEN ------- //
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
		
		Delay.msDelay(1000);
		
		//Gehe auf richtige Höhe bei Initializierung
		//Move sheet backwards
		motorY.rotate(180);
		Delay.msDelay(1000);
		
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
		
		//set position to first lower left corner
		//stift nach hinten
		motorY.rotate(-90);
		
		jumpRows(2);

		printLetter('a');
		printLetter('b');
		printLetter('c');
		printLetter('d');
		printLetter('e');
		
		
		/*while(inputQueue.peek() != null) {
			//remove letter from queues and print it
			inputLetter = inputQueue.poll();
			printLetter(a);
		}*/			
		
	}
	
	public static void printLetter(char a) {
		switch(a) {
		case 'a':
			printA();
			break;
		case 'b':
			printB();
			break;
		case 'c':
			printC();
			break;
		case 'd':
			printD();
			break;
		case 'e':
			printE();
			break;
		case 'f':
			printF();
			break;
		case 'g':
			printG();
			break;
		case 'h':
			printH();
			break;
		case 'i':
			printI();
			break;
		case 'j':
			printJ();
			break;
		case 'k':
			printK();
			break;
		case 'l':
			printL();
			break;
		case 'm':
			printM();
			break;
		case 'n':
			printN();
			break;
		case 'o':
			printO();
			break;
		case 'p':
			printP();
			break;
		case 'q':
			printQ();
			break;
		case 'r':
			printR();
			break;
		case 's':
			printS();
			break;
		case 't':
			printT();
			break;
		case 'u':
			printU();
			break;
		case 'v':
			printV();
			break;
		case 'w':
			printW();
			break;
		case 'x':
			printX();
			break;
		case 'y':
			printY();
			break;
		case 'z':
			printZ();
			break;
		case '1':
			print1();
			break;
		case '2':
			print2();
			break;
		case '3':
			print3();
			break;
		case '4':
			print4();
			break;
		case '5':
			print5();
			break;
		case '6':
			print6();
			break;
		case '7':
			print7();
			break;
		case '8':
			print8();
			break;
		case '9':
			print9();
			break;
		case '0':
			print0();
			break;
		case '.': // .
			printDot();
			break;
		case ',': // ,
			printComma();
			break;
		case '?': // ?
			printQMark();
			break;
		case '\'':// '
			printApostrophe();
			break;
		case '/': // /
			printSlash();
			break;
		case ':': // :
			printColon();
			break;
		case ';': // ;
			printSimile();
			break;
		case '+':// +
			printPlus();
			break;
		case '-':// -
			printMinus();
			break;
		case '=':// =
			printEquals();
			break;
		}
	}
	
	public static void jumpRows(int number) {
		for(int i = 0; i < number; i++) {
			straight("up", 180);	
		}
		
		motorX.forward();
		
		while(true) {
			if(penAdapter.isPressed()) {
				motorX.stop();
				break;
			}
		}
		
	}
	
	public static void printSpace() {
		straight("left", 30);
		//Delay.msDelay(1000);
	}
	
	private static void printSpaceBetweenWords() {
		straight("left", 60);
		//Delay.msDelay(1000);
	}
	
	public static void setPen() {

		motorZ.rotate(180);
		//Delay.msDelay(1000);
		motorZ.stop();
	}
	
	private static void liftPen() {

		motorZ.rotate(-180);
		//Delay.msDelay(1000);
		motorZ.stop();
	}
	
	private static void diagonal(String lowerCorner, String upperCorner, String direction, int degree) {
		
		//only 90° or 45°
		if(degree != 90 && degree != 45) {
			System.out.println("Wrong degree input, only 45° or 90°.");
			return;
		}
		
		//only 90° or 45°
		if(direction != "up" && direction != "down") {
			System.out.println("Wrong direction, only direction up or down possible!");
			return;
		}
		
		//lower
		/*if((lowerCorner != "Right" && upperCorner != "Left") || (lowerCorner != "Left" && upperCorner != "Right" )) {
			System.out.println("Wrong direction, only direction up or down possible!");
			return;
		}*/
		
		if(direction == "up") {
			if(lowerCorner == "Right" && upperCorner == "Left") {
				motorY.startSynchronization();
				//stift nach hinten
				motorY.rotate(-degree);
				//stift nach links
				motorX.rotate(-degree);
				motorY.endSynchronization();
			} else if (lowerCorner == "Left" && upperCorner == "Right") {
				motorY.startSynchronization();
				//stift nach hinten
				motorY.rotate(-degree);
				//stift nach rechts
				motorX.rotate(degree);
				motorY.endSynchronization();
			}
		} else if (direction == "down"){
			if(upperCorner == "Left" && lowerCorner == "Right") {
				motorY.startSynchronization();
				//stift nach vorne
				motorY.rotate(degree);
				//stift nach rechts
				motorX.rotate(degree);
				motorY.endSynchronization();
			} else if (upperCorner == "Right" && lowerCorner == "Left") {
				motorY.startSynchronization();
				//stift nach vorne
				motorY.rotate(degree);
				//stift nach links
				motorX.rotate(-degree);
				motorY.endSynchronization();
			}
		}
		
		Delay.msDelay(1000);
		
	}
	
	private static void straight(String direction, int degree) {
		
		//only 90° or 45°
		if(direction != "up" && direction != "down" && direction != "left" && direction != "right") {
			System.out.println("Wrong direction, only direction up, down, left, right possible!");
			return;
		}
		
		if(direction == "up") {
			motorY.rotate(-degree);
		} else if(direction == "down") {
			motorY.rotate(degree);
		} else if(direction == "right") {
			motorX.rotate(degree);
		} else if(direction == "left") {
			motorX.rotate(-degree);
		}

	}
	
	// --------------- PRINT LETTER FUNCTIONS --------------- //
	// Prints the letter given by the morse code input        //
	
	private static void printA() {
		
		//Character
		setPen();
		straight("down", 45);
		diagonal("Left", "Right", "down", 45);
		diagonal("Right", "Left", "up", 45);
		straight("up", 45);
		liftPen();
		
		straight("down", 45);

		//----- Mittellinie ----- //
		setPen();
		straight("right", 70);
		liftPen();
		
		//positioning
		straight("left", 70);
		straight("up", 45);
		
		printSpace();
	}
	
	private static void printB() {
		
		//Character
		setPen();
		straight("down", 90);
		straight("left", 45);
		diagonal("Left", "Right", "up", 45);
		diagonal("Right", "Left", "up", 45);
		straight("right", 45);
		liftPen();
		
		//positioning
		straight("left", 45);

		printSpace();
	}
	
	private static void printC() {
		//Character
		setPen();
		straight("left", 45);
		liftPen();
		straight("right", 45);
		setPen();
		
		straight("down", 90);
		straight("left", 45);
		liftPen();
		
		//positioning
		straight("up", 90);

		printSpace();
	}
	
	private static void printD() {
		//Character
		setPen();
		straight("down", 90);
		diagonal("Right", "Left", "up", 45);
		diagonal("Left", "Right", "up", 45);
		liftPen();
		
		//positioning
		straight("left", 45);

		printSpace();
	}
	
	private static void printE() {
		//Character
		setPen();
		straight("left", 45);
		liftPen();
		straight("right", 45);
		setPen();
		
		straight("down", 45);
		
		straight("left", 45);
		liftPen();
		straight("right", 45);
		setPen();
		
		straight("down", 45);
		
		straight("left", 45);
		liftPen();
		
		//positioning
		straight("up", 90);

		printSpace();
	}
	
	private static void printF() {
		//Character
		setPen();
		straight("left", 45);
		liftPen();
		straight("right", 45);
		setPen();
		
		straight("down", 45);
		
		straight("left", 45);
		liftPen();
		straight("right", 45);
		setPen();
		
		straight("down", 45);
		
		straight("left", 45);
		liftPen();
		
		//positioning
		straight("up", 90);

		printSpace();
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
