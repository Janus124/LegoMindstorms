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
	static int xCounter;
	
	//-------------
	//Music Printing
	
	private static final int linelenght = 1200;
	private static final int unit = 40;
	private static int notesInLine = 0;
	private static final int MaxNotesInLine = 15;
	
	public static void spaceBetween() {
		straight("left", 2* unit);
	}
	
	public static void startPrintingNotes(List<String> notes) {
		
		//initialisieren
		SignPrint.musicGrid();
		
		SignPrint.spaceBetween();
		
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
			SignPrint.musicGrid();
			SignPrint.spaceBetween();
			notesInLine = 0;
		}
		
		switch(note) {
		case 'c':
			notesInLine++;
			SignPrint.noteC();			
			break;
		case 'd':
			notesInLine++;
			SignPrint.noteD();			
			break;
		case 'e':
			notesInLine++;
			SignPrint.noteE();			
			break;
		case 'f':
			notesInLine++;
			SignPrint.noteF();			
			break;
		case 'g':
			notesInLine++;
			SignPrint.noteG();			
			break;
		case 'a':
			notesInLine++;
			SignPrint.noteA();			
			break;
		case 'h':
			notesInLine++;
			SignPrint.noteH();			
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
	
	protected static void x0() {
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
			SignPrint.printA();
			break;
		case 'b':
			calcXPos(50, true);
			SignPrint.printB();
			break;
		case 'c':
			calcXPos(45, true);
			SignPrint.printC();
			break;
		case 'd':
			calcXPos(50, true);
			SignPrint.printD();
			break;
		case 'e':
			calcXPos(45, true);
			SignPrint.printE();
			break;
		case 'f':
			calcXPos(45, true);
			SignPrint.printF();
			break;
		case 'g':
			calcXPos(45, true);
			SignPrint.printG();
			break;
		case 'h':
			calcXPos(45, true);
			SignPrint.printH();
			break;
		case 'i':
			calcXPos(10, true);
			SignPrint.printI();
			break;
		case 'j':
			calcXPos(45, true);
			SignPrint.printJ();
			break;
		case 'k':
			calcXPos(50, true); 
			SignPrint.printK();
			break;
		case 'l':
			calcXPos(45, true);
			SignPrint.printL();
			break;
		case 'm':
			calcXPos(90, true);
			SignPrint.printM();
			break;
		case 'n':
			calcXPos(90, true);
			SignPrint.printN();
			break;
		case 'o':
			calcXPos(45, true);
			SignPrint.printO();
			break;
		case 'p':
			calcXPos(45, true);
			SignPrint.printP();
			break;
		case 'q':
			calcXPos(45, true);
			SignPrint.printQ();
			break;
		case 'r':
			calcXPos(50, true);
			SignPrint.printR();
			break;
		case 's':
			calcXPos(45, true);
			SignPrint.printS();
			break;
		case 't':
			calcXPos(45, true);
			SignPrint.printT();
			break;
		case 'u':
			calcXPos(45, true);
			SignPrint.printU();
			break;
		case 'v':
			calcXPos(90, true);
			SignPrint.printV();
			break;
		case 'w':
			calcXPos(90, true);
			SignPrint.printW();
			break;
		case 'x':
			calcXPos(90, true);
			SignPrint.printX();
			break;
		case 'y':
			calcXPos(90, true);
			SignPrint.printY();
			break;
		case 'z':
			calcXPos(90, true);
			SignPrint.printZ();
			break;
		case '1':
			calcXPos(50, true);
			SignPrint.print1();
			break;
		case '2':
			calcXPos(45, true);
			SignPrint.print2();
			break;
		case '3':
			calcXPos(45, true);
			SignPrint.print3();
			break;
		case '4':
			calcXPos(45, true);
			SignPrint.print4();
			break;
		case '5':
			calcXPos(45, true);
			SignPrint.print5();
			break;
		case '6':
			calcXPos(45, true);
			SignPrint.print6();
			break;
		case '7':
			calcXPos(45, true);
			SignPrint.print7();
			break;
		case '8':
			calcXPos(45, true);
			SignPrint.print8();
			break;
		case '9':
			calcXPos(45, true);
			SignPrint.print9();
			break;
		case '0':
			calcXPos(50, true);
			SignPrint.print0();
			break;
		case '.': // .
			calcXPos(10, true);
			SignPrint.printDot();
			break;
		case ',': // ,
			calcXPos(10, true);
			SignPrint.printComma();
			break;
		case '?': // ?
			calcXPos(45, true);
			SignPrint.printQMark();
			break;
		case '\'':// '
			calcXPos(10, true);
			SignPrint.printApostrophe();
			break;
		case '/': // /
			calcXPos(50, true);
			SignPrint.printSlash();
			break;
		case ':': // :
			calcXPos(10, true);
			SignPrint.printColon();
			break;
		case ';': // ;
			calcXPos(10, true);
			SignPrint.printSemicolon();
			break;
		case '+':// +
			calcXPos(30, true);
			SignPrint.printPlus();
			break;
		case '-':// -
			calcXPos(30, true);
			SignPrint.printMinus();
			break;
		case '=':// =
			calcXPos(30, true);
			SignPrint.printEquals();
			break;
		case '!':// !
			calcXPos(10, true);
			SignPrint.printEMark();
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
				SignPrint.printDash();
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
	
	protected static void printSpace() {
		straight("left", 30);
	}
	
	protected static void printSpaceBetweenWords() {
		straight("left", 60);
	}
	
	protected static void setPen() {
		Manager.motorZ.rotate(180);
		Manager.motorZ.stop();
	}
	
	protected static void liftPen() {
		Manager.motorZ.rotate(-180);
		Manager.motorZ.stop();
	}
	
	protected static void diagonal(String lowerCorner, String upperCorner, String direction, int degree) {
		
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
	
	protected static void straight(String direction, int degree) {
		
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

	
}

//---------------------------------------------
