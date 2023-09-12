package Model;

import Utility.Colour;
import Utility.IOManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Player class describes properties and behaviours of the player such as player's name, and the player's assigned color.
 * The class is responsible for keep track of number of tokens owned by the player and the number of tokens that are
 * currently on the board.
 */
public class HumanPlayer implements Player{

    /**
     * List of player's tokens
     */
    private List<Token> tokenArray;
    /**
     * List of tokens on board
     */
    private List<Token> tokenOnBoardArray = new ArrayList<>();

    /**
     * Player name
     */
    protected String name;
    /**
     * Player colour
     */
    private Colour colour;

    private IOManager ioManager = IOManager.getInstance();


    /**
     * Constructor used to construct the tokens that are given to each player
     * @param tokens - The list of tokens each player has
     */
    public HumanPlayer(ArrayList<Token> tokens){
        this.tokenArray = tokens;
        this.colour = this.tokenArray.get(0).getTokenColour();
    }

    /**
     * Getter used to fetch the number of tokens in the player's token array.
     * @return number of tokens in the player's token array.
     */
    @Override
    public int getTokenCount() {
        return tokenArray.size();
    }

    /**
     * Getter used to fetch the player's token array.
     * @return player's token array
     */
    @Override
    public List<Token> getTokensArray(){
        return this.tokenArray;
    }

    /**
     * Getter used to fetch number of tokens on the board
     * @return number of tokens on the board
     */
    @Override
    public int getTokenOnBoardCount() {
        return tokenOnBoardArray.size();
    }

    /**
     * Getter used to fetch the tokens on board array
     * @return player's tokens on board array
     */
    public List<Token> getTokensOnBoardArray(){
        return this.tokenOnBoardArray;
    }

    /**
     * method to add token to the tokens array
     * @param token token
     */
    @Override
    public void addTokenArray(Token token) {
        this.tokenArray.add(token);
    }

    /**
     * Method to remove token from tokens placed on the board
     */
    @Override
    public void popTokenOnBoardArray(){
        tokenOnBoardArray.remove(getTokenOnBoardCount()-1);
    }

    /**
     * method to add to the count of tokens present on the board
     * @param token token
     */
    @Override
    public void addTokenOnBoardCount(Token token){
        tokenOnBoardArray.add(token);
    }

    /**
     * Method used to remove token from player's array of tokens
     */
    @Override
    public Token popTokenArray(){
        Token token = tokenArray.get(getTokenCount()-1);
        tokenArray.remove(token);
        return token;
    }

    /**
     * Getter to fetch player name
     * @return player name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Setter to set a name for the player
     * @param name - name that is to be assigned to the player
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter to fetch player's colour
     * @return colour of the player
     */
    @Override
    public char getColour(){
        return this.colour.getDisplayChar();
    }

    /**
     * it returns the position that the player has chose
     * */
    @Override
    public Position play(List<Position> validMoves,String playerAction) {
        return this.ioManager.readPosition("");
    }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.HUMANPLAYER;
    }

    /**
     * Getter to fetch player's colour raw
     * @return colour of the player
     */
    @Override
    public Colour getColourRaw(){
        return this.colour;
    }

}