package Model;

import Utility.Colour;

/**
 * src.Game.Token class describing the properties and behaviours of the
 * tokens in the game
 */
public class Token {

    /**
     * src.Game.Colour of the token
     */
    private Colour colour;


    /**
     * Constructor
     * @param colour, colour of the token
     */
    public Token(Colour colour){
        this.colour = colour;
    }

    /**
     * Getter to fetch token's colout
     * @return token colour
     */
    public Colour getTokenColour(){
        return this.colour;
    }

}

