package Model;

import Utility.Colour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI implements Player{
    // all the methods here are the same as HumanPlayer except play()
    /**
     * List of player's tokens
     */
    private List<Token> tokenArray;
    /**
     * List of tokens on board
     */
    private List<Token> tokenOnBoardArray = new ArrayList<>();
    /**
     * Player colour
     */
    private Colour colour;

    public AI(ArrayList<Token> tokens){
        this.tokenArray = tokens;
        this.colour = this.tokenArray.get(0).getTokenColour();
    }
    @Override
    public int getTokenCount() {
        return tokenArray.size();
    }

    @Override
    public List<Token> getTokensArray() {
        return this.tokenArray;
    }

    @Override
    public int getTokenOnBoardCount() {
        return tokenOnBoardArray.size();
    }

    @Override
    public void addTokenArray(Token token) {
        this.tokenArray.add(token);
    }

    @Override
    public void popTokenOnBoardArray() {
        tokenOnBoardArray.remove(getTokenOnBoardCount()-1);
    }

    @Override
    public void addTokenOnBoardCount(Token token) {
        tokenOnBoardArray.add(token);
    }

    @Override
    public Token popTokenArray() {
        Token token = tokenArray.get(getTokenCount()-1);
        tokenArray.remove(token);
        return token;
    }

    @Override
    public String getName() {
        return "AI";
    }

    @Override
    public void setName(String name) {
    }


    @Override
    public char getColour() {
        return this.colour.getDisplayChar();
    }

    /**
     * this method accept list of valid moves that AI can make and randomly choose a move and return it
     * the program will stop for a really short time, to let the player has time to react on the AI move
     * */
    @Override
    public Position play(List<Position> validMoves, String playerAction) {
        Random random = new Random();
        int index = random.nextInt(validMoves.size());

        try {
            if(!playerAction.isEmpty()) {
                System.out.println(playerAction);
            }
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return validMoves.get(index);
    }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.AI;
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
