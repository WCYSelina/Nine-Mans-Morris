package Controller.GamePhase;

import Controller.BoardController;
import Controller.Turn;
import Model.*;
import Utility.Colour;
import Utility.IOManager;
import View.View;

import java.util.ArrayList;
import java.util.List;

/**
 * FlyPhase class representing the fly move functionality as per the game rules, when the token number is exactly three
 */

public class FlyPhase implements Phase {

    // attributes
    private IOManager ioManager = IOManager.getInstance();
    private View boardView;
    private View gameView;
    private Turn turn;
    private View errorView;
    private Mill mill;

    /**
     * Constructs a FlyPhase with the given list of views and turn.
     * @param views the list of views associated with the FlyPhase
     * @param turn the Turn object representing the current turn
     */
    public FlyPhase(List<View> views, Turn turn) {
        this.mill = new Mill();
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


    /**
     * Sets the position of a player's token during the FlyPhase.
     * @param aPlayer the Player object whose token's position is being set
     * @param currentBoard the Board object representing the current game board
     * @param boardController the BoardController object used for game board operations
     */
    public void setPositionFly(Player aPlayer, Board currentBoard, BoardController boardController)  {
        // check if the player's token is less than 3
        if (boardController.isGameOverTwo()){
            this.ioManager.println("Game over. Less than three tokens left");
            System.exit(0);
        }

        this.turn.drawTurn();
        this.ioManager.println("[ Current Phase: " + GamePhase.FLYPHASE + " ]" + "\n");


        List<Position> validMovesFrom;
        List<Position> validMovesTo;

        if (aPlayer.getPlayerType() == PlayerType.AI){
            // randomly choose the position from where the AI will fly
            validMovesFrom = boardController.checkValidOldPosition(Colour.WHITE);
            String playerAction = "AI is moving its token";
            Position oldPosition = aPlayer.play(validMovesFrom,playerAction);

            // randomly choose the position where AI will fly to
            validMovesTo = boardController.checkValidMoves();
            Position newPosition = aPlayer.play(validMovesTo,"");

            // update user with what move the AI made
            this.ioManager.println("AI is flying the token at position " + oldPosition + " to " + newPosition );

            Position actualOldPosition = currentBoard.findActualPosition(oldPosition);
            Position actualNewPosition = currentBoard.findActualPosition(newPosition);

            Token oldToken = actualOldPosition.getToken();

            currentBoard.updateBoardStar(actualOldPosition, aPlayer);
            currentBoard.updateBoardToken(actualNewPosition, oldToken);
            mill.removeMill(currentBoard.getPlayerMill(aPlayer.getColour()), actualOldPosition);

            System.out.println("Click enter to continue the game");
            ioManager.pauseGame();
            this.boardView.draw();

            boolean isMilled = boardController.millHandling(aPlayer, actualNewPosition);

            if (isMilled) {
                this.ioManager.println("You have milled!");
                System.out.println("Click enter to continue the game");
                ioManager.pauseGame();
                this.boardView.draw();
            }
        }

        else if (aPlayer.getPlayerType() == PlayerType.HUMANPLAYER) {
            this.ioManager.println("Select which token you would like to fly to any position");
            //src.src.Display.Display src.Game.Player Name
            Position currentPosition = this.ioManager.readPosition("");
            //Error checkpoint 1: check if there is any token at the chosen current position
            while (!currentPosition.isEmpty()){
                this.ioManager.println("There is not token in this position. Please enter a valid position.");
                currentPosition = this.ioManager.readPosition("");
            }

            currentPosition = currentBoard.findActualPosition(currentPosition);
            if (currentPosition != null){
                Position oldPosition = currentPosition;
                Token currentToken = currentPosition.getToken();
                boolean updateOldPosition = currentBoard.updateBoardStar(currentPosition,aPlayer);

                if(!updateOldPosition){
                    this.boardView.draw();
                    this.ioManager.println("This is not your token. Please enter a valid position.");
                    setPositionFly(aPlayer, currentBoard, boardController);
                }
                else {
                    // remove the mill that the token was in, if there is any
                    this.mill.removeMill(currentBoard.getPlayerMill(aPlayer.getColour()),oldPosition);
                    currentPosition = this.ioManager.readPosition("new ");
                    // find the actual position in the board
                    currentPosition = currentBoard.findActualPosition(currentPosition);
                    // update the board, if able to update
                    boolean updateNewPosition = currentBoard.updateBoardToken(currentPosition, currentToken);
                    // unable to update
                    if(!updateNewPosition){
                        currentBoard.updateBoardToken(oldPosition, currentToken);
                        this.boardView.draw();
                        this.ioManager.println("This position is already occupied. Please enter a valid position.");
                        setPositionFly(aPlayer, currentBoard, boardController);
                    }
                    else{
                        this.boardView.draw();
                        // check if the token that was just moved/placed forms a mill
                        boolean isMilled = boardController.millHandling(aPlayer,currentPosition);
                        if (isMilled){
                            System.out.println("Milled");
                            this.boardView.draw();
                        }
                    }
                }
            } else {
                this.boardView.draw();
                this.errorView.draw();
                setPositionFly(aPlayer, currentBoard, boardController);
            }
        }

    }


    /**
     * This method will be called when the currentGamePhase is GamePhase.SLIDEPHASE
     * */
    @Override
    public void run (Player player, Board board, BoardController boardController){
        this.setPositionFly(player, board, boardController);
    }


}