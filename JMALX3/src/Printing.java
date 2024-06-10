import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

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
	
	//XCounter for new line
	static int xCounter;
	
	
	/*TODO:
	 * 1. Erster und letzter Buchstabe eines neuen Worts bei Zeilenumbruch beachten
	 * 2. Zahlen 0-9 & Sonderzeichen coden
	 * 3. 1180 nach links für ende x-Achse
	 * 4. xPos fuer alle switch cases
	 * 5. Schnittstelle Testlauf
	*/
	public static void main(String[] args) {
		
		List<String> normalWordArray = new ArrayList<String>();
		normalWordArray.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		startPrinting(normalWordArray);
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
	
	public static void startPrinting(List<String> words) {
		initialize();
		
		//set position to first lower left corner
		//stift nach hinten
		motorY.rotate(-90);
		
		// ----------- START READING INPUT --------- //
		String word;
		for(int i = 0; i < words.size(); i++) {
			//get word
			word = words.get(i);
			//iterate over string and print every character
			for(int j = 0; j < word.length(); j++) {
				printLetter(word.charAt(j));
			}
			
			//when word was printed, 
			//print the bigger space between two words only if end of row is not reached yet,
			//otherwise, start in a new line
			calcXPos(0, false);
				
		}	
		
	}
	
	public static void printLetter(char a) {
		//print corresponding letter, for each letter check
		//if it still fits in this row, otherwise print a "-" and start in a newLine
		switch(a) {
		case 'a':
			calcXPos(70, true);
			printA();
			break;
		case 'b':
			calcXPos(45, true);
			printB();
			break;
		case 'c':
			//calcXPos(45);
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
	
	public static void newLine(int number) {
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
		
		xCounter = 0;
		
	}
	
	public static void calcXPos(int degree, boolean character) {
		xCounter += degree;
		if(character) {
			xCounter+= 30;
			if(xCounter >= 1180) {
				printDash();
				newLine(1);
				xCounter += degree + 30;
			}
		} else {
			//space
			xCounter += 60;
			//additional space to avoid, that first letter would not fit 
			//and a dash is printed
			xCounter += 100;
			if(xCounter < 1180) {
				printSpaceBetweenWords();
				xCounter -= 100;
			} else {
				//Erster
				newLine(1);
				xCounter += degree + 30;
			}
		}
		
	}
	
	public static void printSpace() {
		straight("left", 30);
	}
	
	private static void printSpaceBetweenWords() {
		straight("left", 30);
	}
	
	public static void setPen() {
		motorZ.rotate(180);
		motorZ.stop();
	}
	
	private static void liftPen() {
		motorZ.rotate(-180);
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
		straight("left", 50);
		diagonal("Left", "Right", "up", 45);
		diagonal("Right", "Left", "up", 45);
		straight("right", 50);
		liftPen();
		
		//positioning
		straight("left", 50);

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
		straight("down", 90);
		
		straight("left", 45);
		liftPen();
		
		straight("right", 45);
		straight("up", 45);
		
		setPen();
		straight("left", 45);
		liftPen();
		
		//positioning
		straight("up", 45);

		printSpace();
	}
	
	private static void printG() {
		//Character
		setPen();
		straight("left", 45);
		straight("down", 30);
		straight("right", 30);
		liftPen();
		
		straight("left", 30);
		straight("up", 30);
		straight("right", 45);
		setPen();
		
		straight("down", 90);
		
		straight("left", 45);
		liftPen();
		
		//positioning
		straight("up", 90);

		printSpace();
	}
	
	private static void printH() {
		//Character
		setPen();
		straight("down", 90);
		liftPen();
		straight("up", 45);
		
		setPen();
		straight("left", 45);
		liftPen();
		
		straight("down", 45);
		
		setPen();
		straight("up", 90);
		liftPen();
		
		//positioning

		printSpace();
	}
	
	private static void printI() {
		//Character
		setPen();
		straight("down", 90);
		liftPen();
		
		//positioning
		straight("up", 90);

		printSpace();
	}
	
	private static void printJ() {
		//Character
		setPen();
		straight("down", 30);
		liftPen();
		
		straight("up", 30);
		
		setPen();
		straight("left", 45);
		straight("down", 90);
		straight("right", 45);
		liftPen();
		
		//positioning
		straight("left", 45);
		straight("up", 90);

		printSpace();
	}
	
	private static void printK() {
		//Character
		setPen();
		straight("down", 45);
		diagonal("Right", "Left", "up", 45);
		liftPen();
		diagonal("Right", "Left", "down", 45);
		
		setPen();
		diagonal("Left", "Right", "down", 45);
		liftPen();
		diagonal("Left", "Right", "up", 45);
		setPen();
		straight("down", 45);
		liftPen();
		
		//positioning
		straight("left", 35);
		straight("up", 90);

		printSpace();
	}
	
	private static void printL() {
		//Character
		setPen();
		straight("down", 90);
		liftPen();
		
		straight("up", 90);
		
		setPen();
		straight("left", 45);
		liftPen();
		
		//positioning

		printSpace();
	}
	
	private static void printM() {
		//Character
		setPen();
		straight("down", 90);
		diagonal("Right", "Left", "up", 45);
		diagonal("Left", "Right", "down", 45);
		straight("up", 90);
		liftPen();

		//positioning

		printSpace();
	}
	
	private static void printN() {
		//Character
		setPen();
		straight("down", 90);
		diagonal("Right", "Left", "up", 90);
		straight("down", 90);
		liftPen();

		//positioning
		straight("up", 90);

		printSpace();
	}

	private static void printO() {
		//Character
		setPen();
		straight("down", 90);
		straight("left", 45);
		straight("up", 90);
		straight("right", 45);
		liftPen();

		//positioning
		straight("left", 45);

		printSpace();
	}
	
	private static void printP() {
		//Character
		setPen();
		straight("down", 90);
		straight("left", 45);
		straight("up", 45);
		straight("right", 45);
		liftPen();

		//positioning
		straight("left", 45);
		straight("up", 45);

		printSpace();
	}
	
	private static void printQ() {
		//Character
		setPen();
		straight("down", 90);
		straight("left", 45);
		straight("up", 90);
		
		//Line in Q
		liftPen();
		straight("up", 20);
		straight("left", 20);
		setPen();
		diagonal("Right", "Left", "down", 45);
		liftPen();
		straight("up", 20);
		straight("left", 20);
		
		setPen();
		straight("right", 45);
		liftPen();

		//positioning
		straight("left", 45);

		printSpace();
	}
	
	private static void printR() {
		//Character
		setPen();
		straight("down", 90);
		straight("left", 45);
		straight("up", 45);
		straight("right", 45);
		diagonal("Right", "Left", "up", 45);
		liftPen();

		//positioning

		printSpace();
	}
	
	private static void printS() {
		//Character
		setPen();
		straight("left", 45);
		straight("down", 45);
		straight("right", 45);
		straight("down", 45);
		straight("left", 45);
		liftPen();
		
		//positioning
		straight("up", 90);
		
		printSpace();
	}
	
	private static void printT() {
		//Character
		straight("down", 90);
		setPen();
		
		straight("left", 45);
		liftPen();
		
		straight("right", 22);
		setPen();
		
		straight("up", 90);
		liftPen();
		
		//positioning
		straight("left", 22);
		
		printSpace();
	}
	
	private static void printU() {
		//Character
		straight("down", 90);
		setPen();
		
		straight("up", 90);
		straight("left", 45);
		straight("down", 90);
		
		liftPen();
		
		//positioning
		straight("up", 90);
		
		printSpace();
		
	}
	
	private static void printV() {
		//Character
		straight("down", 90);
		setPen();
		
		straight("up", 45);
		diagonal("Right", "Left", "up", 45);
		diagonal("Left", "Right", "down", 45);
		straight("down", 45);
		liftPen();
		
		//positioning
		straight("up", 90);
		
		printSpace();
	}
	
	private static void printW() {
		//Character
		straight("down", 90);
		setPen();
		
		straight("up", 90);
		diagonal("Left", "Right", "down", 45);
		diagonal("Right", "Left", "up", 45);
		straight("down", 90);
		liftPen();
		
		//positioning
		straight("up", 90);
		
		printSpace();		
	}
	
	private static void printX() {
		//Character
		setPen();
		diagonal("Left", "Right", "down", 90);
		
		liftPen();
		straight("right", 70);
		setPen();
		
		diagonal("Right", "Left", "up", 90);
		liftPen();
		
		//positioning
		
		printSpace();
	}

	private static void printY() {
		//Character
		straight("down", 90);
		setPen();
		
		diagonal("Right", "Left", "up", 45);
		straight("up", 45);
		
		liftPen();
		straight("down", 45);
		setPen();
		
		diagonal("Left", "Right", "down", 45);
		liftPen();
		
		//positioning
		straight("up", 90);
		
		printSpace();
	}
	
	private static void printZ() {
		//Character
		setPen();
		straight("left", 90);
		
		liftPen();
		straight("right", 90);
		setPen();
		
		diagonal("Left", "Right", "down", 90);
		
		straight("right", 90);
		liftPen();
		
		//positioning
		diagonal("Right", "Left", "up", 90);
		
		printSpace();
		
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
	
	private static void printDash() {
		straight("down", 45);
		
		setPen();
		straight("left", 30);
		liftPen();
		
		straight("up", 45);
		
	}
}
