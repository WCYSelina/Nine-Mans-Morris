package Controller;

import Controller.GamePhase.*;
import Model.Board;
import Model.Player;
import Utility.Colour;
import View.View;

import java.util.List;

/**
 * Game class is responsible for the main logic of the 9MM game. The class is responsible for starting the game,
 * initialising the board, creating the players and running the game until the game finishes.
 */
public class Game {
    /**
     * Current game board
     */
    private Board currentBoard; // Board Model

    private Turn turn;

    private BoardController boardController;

    private List<View> viewList;

    /**
     Constructs a Game object with the given parameters.
     @param currentBoard The current board of the game.
     @param views The list of views associated with the game.
     @param turn The current turn in the game.
     @param boardController The controller responsible for managing the game board.
     */
    public Game(Board currentBoard, List<View> views,Turn turn,BoardController boardController) {
        this.viewList = views;
        this.currentBoard = currentBoard;
        this.turn = turn;
        this.boardController = boardController;
    }

    /**
     * Method responsible for starting the game by creating the game board and players.
     */
    public void startGame(GamePhase currentGamePhase) {
        boolean flag = false;
        boolean tokenflag = true;

        Phase placePhase = new PlacePhase(viewList, this.turn);
        Phase slidePhase = new SlidePhase(viewList,this.turn);
        Phase flyPhase = new FlyPhase(viewList, this.turn);
        int i = 0;
        int roundCounter = 9;
        while (!flag) {
            Player player = this.turn.playTurn();
            //Once it reaches 9 rounds it will change to the Slide Phase
            if (i == (roundCounter*2)){
                currentGamePhase = GamePhase.SLIDEPHASE;
                tokenflag = false;
                if (!tokenflag && player.getTokenOnBoardCount() <= 3){
                    currentGamePhase = GamePhase.FLYPHASE;
                }
            }

            else if (!tokenflag && player.getTokenOnBoardCount() <= 3){
                currentGamePhase = GamePhase.FLYPHASE;
            }
            else if (!tokenflag && player.getTokenOnBoardCount() > 3){
                currentGamePhase = GamePhase.SLIDEPHASE;
            }
            switch (currentGamePhase) {
                case PLACEPHASE:
                    placePhase.run(player, currentBoard, boardController);

                    break;
                case SLIDEPHASE:
                    slidePhase.run(player,currentBoard, boardController);

                    break;
                case FLYPHASE:
                    flyPhase.run(player,currentBoard, boardController);
                    break;
            }
            i++;
        }
    }

    /**
     * endGame checks if any of the gameover conditions are satisfied, if they are, then the game exits
     */
    public void endGame(){
        if (boardController.isGameOverTwo()) {
            System.exit(0);
        }
        if (boardController.isGameOverOne(Colour.WHITE)) {
            System.exit(0);
        }
        if (boardController.isGameOverOne(Colour.BLACK)) {
            System.exit(0);
        }
    }


}



