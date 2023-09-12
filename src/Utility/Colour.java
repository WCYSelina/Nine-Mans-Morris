package Utility;

/**
 * An enumeration defining the two possible colors for the tokens in the game.
 * Each color is portrayed by character symbol to signify the coloour of the token
 */
public enum Colour implements ChPrintable {
    /**
     * Black colour
     */
    BLACK('B'),
    /**
     * White colour
     */
    WHITE('W');

    /**
     * character defining a symbol;
     */
    private final char symbol;

    /**
     * Constructor used to construct the new colour with a character
     * @param symbol - the character symbol used to signify the colour
     */
    Colour(char symbol){
        this.symbol = symbol;
    }

    /**
     * Method to fetch character symbol associated with the color
     * @return character symbol associated with the color
     */
    @Override
    public char getDisplayChar() {
        return this.symbol;
    }
}

