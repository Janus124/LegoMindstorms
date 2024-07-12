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
	
	//XCounter for new line
	static volatile int xCounter;
	
	//-------------
	//Music Printing
	
	private static final int linelenght = 1200;
	private static final int unit = 40;
	private static int notesInLine = 0;
	private static final int MaxNotesInLine = 15;
	
	//endet links unten (wo auch angefangen wird)(wenn blatt im drucker rechts oben)
	private static void musicGrid() {
		//1
		setPen();
		straight("left", 1200);
		liftPen();
		
		straight("up", unit);
		
		//2
		setPen();
		straight("right", 1200);
		liftPen();
		
		straight("up", unit);
		
		//3
		setPen();
		straight("left", 1200);
		liftPen();
		
		straight("up", unit);
		
		//4
		setPen();
		straight("right", 1200);
		liftPen();
		
		straight("up", unit);
		
		//5
		setPen();
		straight("left", 1200);
		liftPen();
		
		x0();

	}
	
	private static void spaceBetween() {
		straight("left", 2* unit);
	}

	private static void printNote() {
		setPen();
		straight("right", unit);
		straight("up", unit);
		straight("left", unit);
		straight("down", 2*unit);
		liftPen();
				
		straight("left", 2*unit);
		straight("up", unit);
	}
	
	private static void addCLine() {
		//print additional line
		straight("up", unit);
		straight("left", 15);
		setPen();
		straight("right", 70);
		liftPen();
		
		straight("left", 55);
		straight("down", unit);
	}
	
	private static void noteC(){
		addCLine();
		straight("up", 20);
		printNote();
		straight("down", 20);
	}
	
	private static void noteD(){
		printNote();
	}
	
	private static void noteE(){
		straight("down", 20);
		printNote();
		straight("up", 20);
	}
	
	private static void noteF(){
		straight("down", 40);
		printNote();
		straight("up", 40);
	}
	
	private static void noteG(){
		straight("down", 60);
		printNote();
		straight("up", 60);
	}
	
	private static void noteA(){
		straight("down", 80);
		printNote();
		straight("up", 80);
	}
	
	private static void noteH(){
		straight("down", 100);
		printNote();
		straight("up", 100);
	}
		
	public static void startPrintingNotes(List<String> notes) {
		
		//initialisieren
		musicGrid();
		
		spaceBetween();
		
		String word;
		for(int i = 0; i < notes.size(); i++) {
			//get word
			word = notes.get(i);
			//iterate over string and print every character
			for(int j = 0; j < word.length(); j++) {
				printNotes(word.charAt(j));
			}
		}

	}
		
	private static void printNotes(char note) {
		
		//check if note fits in this line
		if(notesInLine >= MaxNotesInLine) {
			newLineNotes(1);
			musicGrid();
			spaceBetween();
		}
		
		switch(note) {
		case 'c':
			notesInLine++;
			noteC();			
			break;
		case 'd':
			notesInLine++;
			noteD();			
			break;
		case 'e':
			notesInLine++;
			noteE();			
			break;
		case 'f':
			notesInLine++;
			noteF();			
			break;
		case 'g':
			notesInLine++;
			noteG();			
			break;
		case 'a':
			notesInLine++;
			noteA();			
			break;
		case 'h':
			notesInLine++;
			noteH();			
			break;
		}
	}
	
	public static void newLineNotes(int number) {
		for(int i = 0; i < number; i++) {
			straight("up", 220);	//normales newLine ist 180
		}
		
		Manager.motorX.forward();
		
		while(true) {
			if(Manager.penAdapter.isPressed()) {
				Manager.motorX.stop();
				break;
			}
		}		
	}
	
	private static void x0() {
		Manager.motorX.forward();
		
		while(true) {
			if(Manager.penAdapter.isPressed()) {
				Manager.motorX.stop();
				break;
			}
		}		
	}
	//---------------
	
	
	public static void initialize() {
		//-------- SYNC MOTOR X and Y -------- //
		
		EV3LargeRegulatedMotor[] syncList = {Manager.motorX};
		Manager.motorY.synchronizeWith(syncList);
		
		//-------- START FEEDING SHEET INTO PRINTER -------- //
		
		System.out.println("Press UP for forward.");
		System.out.println("Press DOWN for backward.");
		System.out.println("Press Enter when done.");
		
		while(true) {
			
			//sheet is fed well into printer, start reaching start position
			if(Button.ENTER.isDown()) {
				Manager.motorY.stop();
				break;
			}
			
			//move paper backwards
			if(Button.UP.isDown()) {
				Manager.motorY.forward();
				Delay.msDelay(1000);
			}
			
			//move paper forward
			if(Button.DOWN.isDown()) {
				Manager.motorY.backward();
				Delay.msDelay(1000);
			}
			
		}
		
		// -------- START REACHING START POSITION -------- //
		Manager.motorY.forward();
		
		while(true) {
			
			int i = Manager.paperVisibleAdapter.getColorID();
			
			if(i <= 2 || i >= 7) {
				Manager.motorY.stop();
				break;
			}
		}
		
		///Delay.msDelay(1000);
		
		//Gehe auf richtige Höhe bei Initializierung
		//Move sheet backwards
		Manager.motorY.rotate(180);
		Delay.msDelay(300);
		
		//Move to the right
		Manager.motorX.forward();
		
		while(true) {
			if(Manager.penAdapter.isPressed()) {
				Manager.motorX.stop();
				break;
			}
		}

		// -------- STOP MOTOR -------- //
		
		Manager.motorY.stop();
		Manager.motorX.stop();
	}
	
	public static void startPrinting(List<String> words) {
		//Initialize is called by the morse team before they start reading input
		
		//set position to first lower left corner
		//stift nach hinten
		Manager.motorY.rotate(-90);
		
		
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
	
	private static void printLetter(char a) {
		//print corresponding letter, for each letter check
		//if it still fits in this row, otherwise print a "-" and start in a newLine
		
		switch(a) {
		case 'a':
			calcXPos(90, true);
			printA();
			break;
		case 'b':
			calcXPos(50, true);
			printB();
			break;
		case 'c':
			calcXPos(45, true);
			printC();
			break;
		case 'd':
			calcXPos(50, true);
			printD();
			break;
		case 'e':
			calcXPos(45, true);
			printE();
			break;
		case 'f':
			calcXPos(45, true);
			printF();
			break;
		case 'g':
			calcXPos(45, true);
			printG();
			break;
		case 'h':
			calcXPos(45, true);
			printH();
			break;
		case 'i':
			calcXPos(10, true);
			printI();
			break;
		case 'j':
			calcXPos(45, true);
			printJ();
			break;
		case 'k':
			calcXPos(50, true); 
			printK();
			break;
		case 'l':
			calcXPos(45, true);
			printL();
			break;
		case 'm':
			calcXPos(90, true);
			printM();
			break;
		case 'n':
			calcXPos(90, true);
			printN();
			break;
		case 'o':
			calcXPos(45, true);
			printO();
			break;
		case 'p':
			calcXPos(45, true);
			printP();
			break;
		case 'q':
			calcXPos(45, true);
			printQ();
			break;
		case 'r':
			calcXPos(50, true);
			printR();
			break;
		case 's':
			calcXPos(45, true);
			printS();
			break;
		case 't':
			calcXPos(45, true);
			printT();
			break;
		case 'u':
			calcXPos(45, true);
			printU();
			break;
		case 'v':
			calcXPos(90, true);
			printV();
			break;
		case 'w':
			calcXPos(90, true);
			printW();
			break;
		case 'x':
			calcXPos(90, true);
			printX();
			break;
		case 'y':
			calcXPos(90, true);
			printY();
			break;
		case 'z':
			calcXPos(90, true);
			printZ();
			break;
		case '1':
			calcXPos(50, true);
			print1();
			break;
		case '2':
			calcXPos(45, true);
			print2();
			break;
		case '3':
			calcXPos(45, true);
			print3();
			break;
		case '4':
			calcXPos(45, true);
			print4();
			break;
		case '5':
			calcXPos(45, true);
			print5();
			break;
		case '6':
			calcXPos(45, true);
			print6();
			break;
		case '7':
			calcXPos(45, true);
			print7();
			break;
		case '8':
			calcXPos(45, true);
			print8();
			break;
		case '9':
			calcXPos(45, true);
			print9();
			break;
		case '0':
			calcXPos(50, true);
			print0();
			break;
		case '.': // .
			calcXPos(10, true);
			printDot();
			break;
		case ',': // ,
			calcXPos(10, true);
			printComma();
			break;
		case '?': // ?
			calcXPos(45, true);
			printQMark();
			break;
		case '\'':// '
			calcXPos(10, true);
			printApostrophe();
			break;
		case '/': // /
			calcXPos(50, true);
			printSlash();
			break;
		case ':': // :
			calcXPos(10, true);
			printColon();
			break;
		case ';': // ;
			calcXPos(10, true);
			printSemicolon();
			break;
		case '+':// +
			calcXPos(30, true);
			printPlus();
			break;
		case '-':// -
			calcXPos(30, true);
			printMinus();
			break;
		case '=':// =
			calcXPos(30, true);
			printEquals();
			break;
		case '!':// !
			calcXPos(10, true);
			printEMark();
			break;	
		}
	}
	
	public static void newLine(int number) {
		for(int i = 0; i < number; i++) {
			straight("up", 180);	
		}
		
		Manager.motorX.forward();
		
		while(true) {
			if(Manager.penAdapter.isPressed()) {
				Manager.motorX.stop();
				break;
			}
		}
		
		xCounter = 0;
		
	}
	
	private static void calcXPos(int degree, boolean character) {
		xCounter += degree;
		if(character) {
			xCounter+= 30;
			if(xCounter >= 1200) {
				printDash();
				newLine(1);
				xCounter += degree + 30;
			}
		} else {
			//space
			xCounter += 90;
			//additional space to avoid, that first letter would not fit 
			//and a dash is printed
			xCounter += 100;
			if(xCounter < 1180) {
				printSpaceBetweenWords();
				xCounter -= 100;
			} else {
				//Erster
				newLine(1);
				//xCounter += degree + 30;
			}
		}
		
	}
	
	private static void printSpace() {
		straight("left", 30);
	}
	
	private static void printSpaceBetweenWords() {
		straight("left", 60);
	}
	
	private static void setPen() {
		Manager.motorZ.rotate(180);
		Manager.motorZ.stop();
	}
	
	private static void liftPen() {
		Manager.motorZ.rotate(-180);
		Manager.motorZ.stop();
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
				Manager.motorY.startSynchronization();
				//stift nach hinten
				Manager.motorY.rotate(-degree);
				//stift nach links
				Manager.motorX.rotate(-degree);
				Manager.motorY.endSynchronization();
			} else if (lowerCorner == "Left" && upperCorner == "Right") {
				Manager.motorY.startSynchronization();
				//stift nach hinten
				Manager.motorY.rotate(-degree);
				//stift nach rechts
				Manager.motorX.rotate(degree);
				Manager.motorY.endSynchronization();
			}
		} else if (direction == "down"){
			if(upperCorner == "Left" && lowerCorner == "Right") {
				Manager.motorY.startSynchronization();
				//stift nach vorne
				Manager.motorY.rotate(degree);
				//stift nach rechts
				Manager.motorX.rotate(degree);
				Manager.motorY.endSynchronization();
			} else if (upperCorner == "Right" && lowerCorner == "Left") {
				Manager.motorY.startSynchronization();
				//stift nach vorne
				Manager.motorY.rotate(degree);
				//stift nach links
				Manager.motorX.rotate(-degree);
				Manager.motorY.endSynchronization();
			}
		}
		
		Delay.msDelay(500);
		
	}
	
	private static void straight(String direction, int degree) {
		
		//only 90° or 45°
		if(direction != "up" && direction != "down" && direction != "left" && direction != "right") {
			System.out.println("Wrong direction, only direction up, down, left, right possible!");
			return;
		}
		
		if(direction == "up") {
			Manager.motorY.rotate(-degree);
		} else if(direction == "down") {
			Manager.motorY.rotate(degree);
		} else if(direction == "right") {
			Manager.motorX.rotate(degree);
		} else if(direction == "left") {
			Manager.motorX.rotate(-degree);
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
		straight("left", 50);

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
		straight("down", 90);
		liftPen();
		straight("left", 50);
		setPen();
		
		diagonal("Left", "Right", "up", 45);
		diagonal("Right", "Left", "up", 45);
		
		liftPen();
		//positioning

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
		
		straight("down", 45);
		
		setPen();
		diagonal("Left", "Right", "down", 45);
		straight("up", 90);		
		liftPen();
		
		//positioning
		printSpace();
		
	}
	
	private static void print2() {
		straight("down", 90);
		
		setPen();
		straight("left", 45);
		straight("up", 45);
		straight("right", 45);
		straight("up", 45);
		straight("left", 45);
		liftPen();
		
		//positioning
		printSpace();
		
	}
	
	private static void print3() {
		setPen();
		straight("left", 45);
		straight("down", 45);
		straight("right", 45);
		
		liftPen();
		straight("down", 45);
		setPen();
		
		straight("left", 45);
		straight("up", 45);
		liftPen();
		
		//positioning
		straight("up", 45);
		printSpace();
		
	}
	
	private static void print4() {
		straight("left", 45);
		
		setPen();
		straight("down", 90);
		
		liftPen();
		straight("up", 45);
		setPen();
		
		straight("right", 45);
		straight("down", 45);
		liftPen();
		
		//positioning
		straight("left", 45);
		straight("up", 90);
		printSpace();
	}
	
	private static void print5() {
		
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
	
	private static void print6() {
		
		setPen();
		straight("left", 45);
		straight("down", 45);
		straight("right", 45);
		straight("up", 45);
		
		liftPen();
		straight("down", 45);
		setPen();
		
		straight("down", 45);
		straight("left", 45);
		liftPen();
		
		//positioning
		straight("up", 90);
		printSpace();
	}
	
	private static void print7() {
		
		straight("down", 90);
		setPen();
		straight("left", 45);
		straight("up", 90);
		liftPen();
		
		//positioning
		printSpace();
	}
	
	private static void print8() {
		
		setPen();
		straight("left", 45);
		straight("down", 90);
		straight("right", 45);
		straight("up", 90);
		
		liftPen();
		straight("down", 45);
		
		setPen();
		straight("left", 45);
		liftPen();
		
		//positioning
		straight("up", 45);
		printSpace();
	}
	
	private static void print9() {
		
		setPen();
		straight("left", 45);
		straight("down", 90);
		straight("right", 45);
		straight("up", 45);
		straight("left", 45);
		liftPen();
		
		//positioning
		straight("up", 45);
		printSpace();		
	}
	
	private static void print0() {
		
		setPen();
		//0
		straight("down", 90);
		straight("left", 50);
		straight("up", 90);
		straight("right", 50);
		
		//stich, dass die 0 erkannt wird
		
		liftPen();
		straight("down", 20);
		
		setPen();
		diagonal("Left", "Right", "down", 45);
		liftPen();
		
		//positioning
		straight("up", 50 + 20);
		printSpace();
	}
	
	private static void printDot() {
		
		setPen();
		Delay.msDelay(500);
		liftPen();
		
		//positioning
		printSpace();
	}
	
	private static void printComma() {
		
		straight("down", 10);
		setPen();
		straight("up", 20);
		liftPen();
				
		//positioning
		straight("down", 10);
		printSpace();
	}
	
	private static void printQMark() {
		straight("down", 60);
		setPen();
		straight("down", 30);
		straight("left", 45);
		straight("up", 30);
		straight("right", 22);
		straight("up", 22);
		
		//dot
		liftPen();
		straight("up", 38);
		setPen();
		Delay.msDelay(500);
		liftPen();
		
		//positioning
		straight("left", 22);
		printSpace();
	}
	
	private static void printApostrophe() {
		straight("down", 80);
		setPen();
		straight("down", 20);
		liftPen();
		
		//positioning
		straight("up", 100);
		printSpace();
	}
	
	private static void printSlash() {
		straight("down", 70);
		setPen();
		diagonal("Right", "Left", "up", 45);
		liftPen();
		
		//positioning
		straight("up", 20);
		printSpace();
	}
	
	private static void printColon() { //doppelpunkt
		straight("down", 22);
		
		setPen();
		Delay.msDelay(500);
		liftPen();
		
		straight("down", 22);
		
		setPen();
		Delay.msDelay(500);
		liftPen();
			
		//positioning
		straight("up", 45);
		printSpace();
	}

	private static void printPlus() {
		straight("down", 45);
		setPen();
		straight("left", 30);
		liftPen();
		straight("right", 15);
		straight("down", 15);
		setPen();
		straight("up", 30);
		liftPen();
		
		//positioning
		straight("up", 30);
		straight("left", 15);
		printSpace();
	}
	
	private static void printMinus() {
		
		printDash();
		printSpace();
		
	}
	
	private static void printEquals() {
		straight("down", 45);
		
		setPen();
		straight("left", 30);
		liftPen();
		
		straight("up", 22);
		setPen();
		straight("right", 30);
		liftPen();
	
		//positioning
		straight("up", 22);
		straight("left", 30);
		printSpace();
	}
	
	private static void printSemicolon() {
		straight("down", 45);
	
		setPen();
		Delay.msDelay(500);
		liftPen();
		
		straight("up", 35);
		setPen();
		straight("up", 20);
		liftPen();
		
		//positioning
		straight("down", 10);
		printSpace();
	}
	
	private static void printEMark() {
		setPen();
		Delay.msDelay(500);
		liftPen();
		
		straight("down", 25);
		
		setPen();
		straight("down", 65);
		liftPen();
		
		//positioning
		straight("up", 90);
		printSpace();
	}
	
	private static void printDash() {
		straight("down", 45);
		
		setPen();
		straight("left", 30);
		liftPen();
		
		straight("up", 45);
		
	}
}

//---------------------------------------------
