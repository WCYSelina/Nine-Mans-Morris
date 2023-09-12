package Utility;

import Model.Position;

import java.util.Scanner;

/**
 * class responsible for managing input and output for the game
 */
public class IOManager {
    private final Scanner keyboard = new Scanner(System.in);
    private static IOManager instance;

    /**
     * constructor
     */
    private IOManager(){}

    public static IOManager getInstance(){
        /***
         * This method would return the instance of the IOManager class which is based on the singleton pattern.
         * This ensures that there is only one instance of the IOManager class.
         * @return: instance //The instance of the IOManager class
         */
        if(instance == null){   //always make sure that there is no timePerceptionManager before creating it
            instance = new IOManager();
        }
        return instance;
    }
    public void println(String s){
        /***
         * This method will print the string with next line where it would accept a string parameter
         * @param: s /A string to print
         */
        System.out.println(s);
    }
    public void print(String s){
        /***
         * This method will print the string without next line where it would accept a string parameter
         * @param: s /A string to print
         */
        System.out.print(s);
    }
    public int readIntX(){
        /***
         * This method will read the user input of position x where it would convert the letter to a numeric number
         * @return: charX-96 / This is returned number of converting the letter to a numeric value
         */
        char charX = keyboard.next().charAt(0);

        return charX-96;
    }
    public int readIntY(){
        /***
         * This method will read the user input of position Y where it would accept a character input where it would
         * then be converted and returned as an integer.
         * @return: number / This is the position Y in numeric form
         */
        char charY = keyboard.next().charAt(0);
        keyboard.nextLine();

        return Character.getNumericValue(charY);
    }
    public Position readPosition(String n){
        /***
         * This method will handle the user inputs for both x and y position where x would be a letter and y would be
         * an integer. This method will call readIntX and readIntY to get the correct position and return a new
         * instance of position with the positions
         * @param: n / The position to accept (e.g. new, old)
         * @return: new Position(x,y) /An instance of the position with the updated positions
         */
        this.println("Please enter "+n+"position x:");
        int x = this.readIntX();
        this.println("Please enter "+n+"position y:");
        int y = this.readIntY();
        return new Position(x,y);
    }

    /**
     * read the integer from the input
     * */
    public int readInt(){
        char charInt = keyboard.next().charAt(0);
        keyboard.nextLine();

        return Character.getNumericValue(charInt);
    }

    /**
     * pause the game until user enter "Enter"
     * */
    public void pauseGame(){
        keyboard.nextLine();
    }

}

