package Controller.GamePhase;

import Controller.BoardController;
import Controller.Turn;
import Model.*;
import Utility.IOManager;
import View.View;

import java.util.ArrayList;
import java.util.List;

/**
 *  PalcePhase class represents the functionality for placing the tokens on the board for during the first nine moves
 *  for each player.
 *  */
public class PlacePhase implements Phase {

    // attributes
    private IOManager ioManager = IOManager.getInstance();

    private View boardView;

    private Turn turn;

    private View gameView;

    private View errorView;

    private boolean firstStart = true;

    /** Constructs a PlacePhase object with the given list of views and turn.
     @param views The list of views associated with the PlacePhase.
     @param turn The current turn in the game.
     */
    public PlacePhase(List<View> views, Turn turn){
        this.turn = turn;
        for (View view : views) {
            switch (view.getViewEnum()) {
                case GAMEVIEW:
                    this.gameView = view;
                    break;
                case BOARDVIEW:
                    this.boardView = view;
                    break;
                case ERRORVIEW:
                    this.errorView = view;
                    break;
            }
        }
    }

    @Override
    public void run(Player player, Board board, BoardController boardController) {
        /***
         * This method will initialise the players and the currentboard for the "Place" phase where it would call
         * the placeTokens function to start the place phase where once the place phase is done it would return
         * the next phase to start.
         * @param: players /A list of players created for the game
         * @param: board /The current board for the game
         * @return: GamePhase /The next phase of the game
         */

        if (firstStart) {
            this.boardView.draw();
            this.gameView.draw();
            firstStart = false;
        }
        this.setPosition(player,board, boardController);
    }

    public void setPosition(Player aPlayer, Board board, BoardController boardController) {
        /***
         * This method would set the position of the token on the board. It would ask for user input on
         * the position that the player want the token to be placed on the board. If the player entered a
         * valid position it would update the board else it would return an error message and ask the player
         * to re-enter a valid position.
         * @param: aPlayer /Player instance
         * @param: i /Counter for the index to access in the array
         */
        //Displays the player name
        this.turn.drawTurn();
        this.ioManager.println("[ Current Phase: " + GamePhase.PLACEPHASE + " ]" + "\n");
        //Get user input on which position to select to place the token on the board
        Position position = new Position();
        List<Position> validMoves = new ArrayList<>();
        // determine if current player is humanPlayer or AI
        switch (aPlayer.getPlayerType()){
            case HUMANPLAYER:
                position = aPlayer.play(validMoves,"");
                break;
            case AI:
                validMoves = boardController.checkValidMoves();
                String playerAction = "AI is moving its token";
                position = aPlayer.play(validMoves,playerAction);
                System.out.println("AI has moved its token to coordinate "+position.toString());
                System.out.println("Click enter to continue the game");
                ioManager.pauseGame();
                break;

        }

        position = board.findActualPosition(position);
        Token token = aPlayer.popTokenArray();
        boolean updateSuccess = board.updateBoardToken(position,token);
        //Error handling - When user input an invalid position
        if (!updateSuccess) {

            //Recursive call back to the function to ask the player to re-enter a valid input
            aPlayer.addTokenArray(token);
            this.boardView.draw();
            this.gameView.draw();
            this.errorView.draw();
            setPosition(aPlayer,board,boardController);
        } else {
            //Display the board with the updated position of the token
            this.boardView.draw();
            ///
            //Remove the token from the tokenOnBoardArray when a user place a token on the board
            aPlayer.addTokenOnBoardCount(token); //change
            //Display the current state of each player such as their number of tokens and token colour
            this.gameView.draw();
            boolean isMilled = boardController.millHandling(aPlayer,position);
            if (isMilled){
                System.out.println("Milled");
                this.boardView.draw();
                this.gameView.draw();
            }
        }
    }



}
