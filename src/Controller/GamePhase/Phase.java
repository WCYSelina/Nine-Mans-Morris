package Controller.GamePhase;

import Controller.BoardController;
import Model.Board;
import Model.HumanPlayer;
import Model.Player;

/* Interface src.Move with the functionality of moving a token*/

 /**
 * This interface will be implements by the phases in the game
 * The run method will be overridden by all the phases that implement this interface
 * */
 public interface Phase {

 void run(Player player, Board board, BoardController boardController);
}

