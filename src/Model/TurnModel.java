package Model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * class is responsible for managing the turns and order of players in a game. It provides methods for playing a turn,
 * adding players to the game, and retrieving the next player.
 */
public class TurnModel {

    // attributes
    private Queue<Player> playersTurn = new LinkedList<Player>();

    /***
     * This method will return a different player every time the method is called. It will switch between the different
     * players in the game
     * @return player
     */
    public Player playTurn(){
        Player player = playersTurn.remove();
        playersTurn.add(player);
        return player;
    }

    /***
     * This method will add a player to the players turn array which defines the number of player in the game
     * @param player / The player to be added to the game
     */
    public void addPlayer(Player player){
        playersTurn.add(player);
    }

    /***
     * This method will get the next player details
     * @return playersTurn.peek() / The next player
     */
    public Player getNextPlayer(){
        return playersTurn.peek();
    }
}
