import lejos.utility.Delay;

public class SignPrint extends Printing{
	
	public static final int unit = 40;
	//endet links unten (wo auch angefangen wird)(wenn blatt im drucker rechts oben)
	public static void musicGrid() {
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

	public static void printNote() {
		setPen();
		straight("right", unit);
		straight("up", unit);
		straight("left", unit);
		straight("down", 2*unit);
		liftPen();
				
		straight("left", 2*unit);
		straight("up", unit);
	}
	
	public static void addCLine() {
		//print additional line
		straight("up", unit);
		straight("left", 15);
		setPen();
		straight("right", 70);
		liftPen();
		
		straight("left", 55);
		straight("down", unit);
	}
	
	public static void noteC(){
		addCLine();
		straight("up", 20);
		printNote();
		straight("down", 20);
	}
	
	public static void noteD(){
		printNote();
	}
	
	public static void noteE(){
		straight("down", 20);
		printNote();
		straight("up", 20);
	}
	
	public static void noteF(){
		straight("down", 40);
		printNote();
		straight("up", 40);
	}
	
	public static void noteG(){
		straight("down", 60);
		printNote();
		straight("up", 60);
	}
	
	public static void noteA(){
		straight("down", 80);
		printNote();
		straight("up", 80);
	}
	
	public static void noteH(){
		straight("down", 100);
		printNote();
		straight("up", 100);
	}
	
	// --------------- PRINT LETTER FUNCTIONS --------------- //
		// Prints the letter given by the morse code input        //
		
		public static void printA() {
			
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
		
		public static void printB() {
			
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
		
		public static void printC() {
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
		
		public static void printD() {
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
		
		public static void printE() {
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
		
		public static void printF() {
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
		
		public static void printG() {
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
		
		public static void printH() {
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
		
		public static void printI() {
			//Character
			setPen();
			straight("down", 90);
			liftPen();
			
			//positioning
			straight("up", 90);

			printSpace();
		}
		
		public static void printJ() {
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
		
		public static void printK() {
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
		
		public static void printL() {
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
		
		public static void printM() {
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
		
		public static void printN() {
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

		public static void printO() {
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
		
		public static void printP() {
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
		
		public static void printQ() {
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
		
		public static void printR() {
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
		
		public static void printS() {
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
		
		public static void printT() {
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
		
		public static void printU() {
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
		
		public static void printV() {
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
		
		public static void printW() {
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
		
		public static void printX() {
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

		public static void printY() {
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
		
		public static void printZ() {
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
		
		public static void print1() {
			
			straight("down", 45);
			
			setPen();
			diagonal("Left", "Right", "down", 45);
			straight("up", 90);		
			liftPen();
			
			//positioning
			printSpace();
			
		}
		
		public static void print2() {
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
		
		public static void print3() {
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
		
		public static void print4() {
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
		
		public static void print5() {
			
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
		
		public static void print6() {
			
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
		
		public static void print7() {
			
			straight("down", 90);
			setPen();
			straight("left", 45);
			straight("up", 90);
			liftPen();
			
			//positioning
			printSpace();
		}
		
		public static void print8() {
			
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
		
		public static void print9() {
			
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
		
		public static void print0() {
			
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
		
		public static void printDot() {
			
			setPen();
			Delay.msDelay(500);
			liftPen();
			
			//positioning
			printSpace();
		}
		
		public static void printComma() {
			
			straight("down", 10);
			setPen();
			straight("up", 20);
			liftPen();
					
			//positioning
			straight("down", 10);
			printSpace();
		}
		
		public static void printQMark() {
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
		
		public static void printApostrophe() {
			straight("down", 80);
			setPen();
			straight("down", 20);
			liftPen();
			
			//positioning
			straight("up", 100);
			printSpace();
		}
		
		public static void printSlash() {
			straight("down", 70);
			setPen();
			diagonal("Right", "Left", "up", 45);
			liftPen();
			
			//positioning
			straight("up", 20);
			printSpace();
		}
		
		public static void printColon() { //doppelpunkt
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

		public static void printPlus() {
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
		
		public static void printMinus() {
			
			printDash();
			printSpace();
			
		}
		
		public static void printEquals() {
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
		
		public static void printSemicolon() {
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
		
		public static void printEMark() {
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
		
		public static void printDash() {
			straight("down", 45);
			
			setPen();
			straight("left", 30);
			liftPen();
			
			straight("up", 45);
		}
		

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
