package View;

import Model.Player;
import Model.Token;
import Utility.IOManager;

import java.util.List;

public class GameView implements View{

    /**
     *  creating a single instance of the "IOManager" class
     */
    private IOManager ioManager = IOManager.getInstance();

    private List<Player> players;

    public GameView(List<Player> players){
        this.players = players;
    }

    /**
     * This method would show the player's token colour and number of tokens left after placing a token on the
     * board. It would display both players current status based on the number of tokens.
     * @param: players /A list consisting all the players of the game
     */

    @Override
    public void draw() {
        this.ioManager.println("\n---------------------Tokens-----------------------");
        for (Player player : players) {
            this.ioManager.print(player.getName() + ": ");
            for (Token token : player.getTokensArray()) {
                this.ioManager.print(token.getTokenColour().getDisplayChar() + " ");
            }
            this.ioManager.println(" (" + player.getTokenCount() + ") tokens");
        }
        this.ioManager.println("--------------------------------------------------");
    }

    @Override
    public ViewEnum getViewEnum() {
        return ViewEnum.GAMEVIEW;
    }

}

