package Controller;

import Model.Player;
import Model.TurnModel;
import View.TurnView;

/**
 The Turn class represents a turn in the game. It manages the interaction between the TurnView and TurnModel.
 */
public class Turn {

    // attributes
    private TurnView turnView;
    private TurnModel turn;


    /**
     Constructs a Turn object with the given TurnView and TurnModel.
     @param turnView The view associated with the turn.
     @param turn The model representing the turn.
     */
    public Turn(TurnView turnView,TurnModel turn){
        this.turnView = turnView;
        this.turn = turn;
    }

    /**
     Plays the turn and returns the player who played the turn.
     @return The player who played the turn.
     */
    public Player playTurn(){
        Player player = turn.playTurn();
        this.turnView.setPlayer(player);
        return player;
    }

    /**

     Retrieves the next player in the turn sequence.
     @return The next player in the turn sequence.
     */
    public Player getNextPlayer(){
        return turn.getNextPlayer();
    }

    /**
     Draws the turn view.
     */
    public void drawTurn(){
        this.turnView.draw();
    }

}
