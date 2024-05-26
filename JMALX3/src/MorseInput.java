// If the code is inefficient, consider using StringBuilder
import java.util.ArrayList;
import java.util.List;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.TouchAdapter;

public class MorseInput {

    // Create and initialize the touch sensor
    static EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
    static TouchAdapter adapter = new TouchAdapter(touchSensor);

    // List to store morsecode
    static List<String> morseWords = new ArrayList<String>();
    static String currWord = "";

    // Morse code symbols and pauses
    // . -> short press (1 unit)
    // - -> long press (3 units)
    // s -> short pause (1 unit)
    // l -> long pause (3 units)
    // w -> very long pause (7 units)

    // Time definitions in milliseconds
    static long unit = 600;
    static long ShortPress = unit;
    static long LongPress = 3 * unit;

    static long pauseSymbol = unit;
    static long pauseLetter = 3 * unit;
    static long pauseWord = 7 * unit;

    static long tolerance = unit / 2;

    public static void main(String[] args) {
        GetInput();
    }

	//gets the input from the EV3 Touch Sensor and computes the duration of the time, when the sensor is pushed and paused 
    public static void GetInput() {
        System.out.println("Starting");
        while (true) {
            long start_press;
            long finish_press;

            long start_pause;
            long finish_pause;

            start_pause = System.currentTimeMillis();

            if (adapter.isPressed()) {
                // Sensor is pressed, measure time

                // Calculate pause duration
                finish_pause = System.currentTimeMillis();
                long time_pause = finish_pause - start_pause;
                HandleInput("pause", time_pause);

                System.out.print("Pressed");
                start_press = System.currentTimeMillis();

                while (true) {
                    if (!adapter.isPressed()) {
                        // Sensor is no longer pressed
                        finish_press = System.currentTimeMillis();
                        break;
                    }
                }
                // Calculate pressed duration
                long time_pressed = finish_press - start_press;
                System.out.println(time_pressed);


                HandleInput("pressed", time_pressed);
            }
        }
    }
	//Converts the time with the type into the correct symbos of the morsecode
	//type = "pause" or "pressed"
	//time = lenght of the duration pressed or not pressed
    public static void HandleInput(String type, long time) {
        if (type.equals("pause")) {
            if (LongInRadius(time, pauseSymbol)) {
                currWord += "s";
            } else if (LongInRadius(time, pauseLetter)) {
                // Letter finished
                currWord += "l";
            } else if (LongInRadius(time, pauseWord)) {
                // Word finished
                morseWords.add(currWord);
                currWord = "";
            } else {
                System.out.println("Error: wrong time in HandleInput pause");
            }
        } else if (type.equals("pressed")) {
            if (LongInRadius(time, ShortPress)) {
                // Dot
                currWord += ".";
            } else if (LongInRadius(time, LongPress)) {
                // Dash
                currWord += "-";
            } else {
                System.out.println("Error: wrong time in HandleInput pressed");
            }
        } else {
            System.out.println("Error: wrong type");
        }
    }

	//checks if the number num is withing a tolerance of expected
    public static boolean LongInRadius(long num, long expected) {
        return expected - tolerance < num && expected + tolerance > num;
    }
}
