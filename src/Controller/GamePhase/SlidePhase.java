package Controller.GamePhase;

import Controller.BoardController;
import Controller.Turn;
import Model.*;
import Utility.Colour;
import Utility.IOManager;
import View.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SlidePhase class representing the slide move once all the tokens are placed on the board by the player. The class is
 * responsible for ensuring that the tokens are only moved to empty adjacent positions only.
 */

public class SlidePhase implements Phase {

    // attributes
    private IOManager ioManager = IOManager.getInstance();
    private View gameView;
    private View boardView;
    private View errorView;
    private Turn turn;

    private Mill mill;

    /**
     * Constructs a SlidePhase with the given list of views and turn.
     * @param views the list of views associated with the SlidePhase
     * @param turn the Turn object representing the current turn
     */
    public SlidePhase(List<View> views, Turn turn){
        this.turn = turn;
        mill = new Mill();
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
     * This method is the actual slide token action happens. Validation of the moves will also be validated in this method
     * It will first ask for the user input of the token position that they want to move and also the new position that
     * they want to move to. The method will then process and validate the input to check if the position is valid and if
     * there is a token in the old position input.
     * @param: aPlayer / Instance of the player that will move the position
     * @return: none
     * */

    public void setPositionSlide(Player aPlayer, Board currentBoard, BoardController boardController) {

        if (boardController.isGameOverOne(Colour.WHITE)) {
            this.ioManager.println("Congratulations! Player 1 (BLACK) wins the game!");
            System.exit(0);
        }
        if (boardController.isGameOverOne(Colour.BLACK)) {
            this.ioManager.println("Congratulations! Player 2 (WHITE) wins the game!");
            System.exit(0);
        }

        this.turn.drawTurn();
        this.ioManager.println("[ Current Phase: " + GamePhase.SLIDEPHASE + " ]" + "\n");

        List<Position> validPositions;
        List<Position> validMoves;

        if (aPlayer.getPlayerType() == PlayerType.AI) {
            validPositions = boardController.checkSlideValidOldPosition(Colour.WHITE);
            String playerAction = "AI is moving its token";

            Position oldPosition = aPlayer.play(validPositions, playerAction);
            validMoves = boardController.checkSlideValidMoves(oldPosition);
            Position newPosition = aPlayer.play(validMoves,"");

            this.ioManager.println("AI has slided the token from " + oldPosition + " to " + newPosition);

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
                this.ioManager.println("Click enter to continue the game");
                ioManager.pauseGame();
                this.boardView.draw();
            }

        }

        else if (aPlayer.getPlayerType() == PlayerType.HUMANPLAYER) {

            this.ioManager.println("Select which token you would like to move to the adjacent position");
            //Gets the existing token position input the players want to move
            Position currentPosition = this.ioManager.readPosition("");

            //Error checkpoint 1: check if there is any token at the chosen current position
            while (!currentPosition.isEmpty()) {
                this.ioManager.println("There is not token in this position. Please enter a valid position.");
                currentPosition = this.ioManager.readPosition("");
            }

            //Store a temporary copy of the old position
            Position tempOldPosition = currentPosition;
            //Finds the actual position of the old position
            Position oldPosition = currentBoard.findActualPosition(currentPosition);

            //Checks if the old position is valid
            if (oldPosition != null){

                //Gets the current token details of the position (e.g. colour, etc)
                Token currentToken = oldPosition.getToken();

                //Read the new position that the player wants to move the token to
                Position tempPosition = this.ioManager.readPosition("new ");

                //Gets the actual position of the new position in the board
                currentPosition = currentBoard.findActualPosition(tempPosition);

                boolean validatePosition = false;

                //Error Handling - Checks if the user enter the same position for both new and old position inputs
                if (currentPosition != null && (oldPosition.getX() == currentPosition.getX() && oldPosition.getY() == currentPosition.getY())){
                    this.boardView.draw();
                    this.errorView.draw();
                    setPositionSlide(aPlayer,currentBoard,boardController);
                }
                else if (currentPosition != null && (oldPosition.getX() == currentPosition.getX() || oldPosition.getY() == currentPosition.getY())){
                    //Validates if the position is an adjacent position by checking if it is in the same row
                    if (oldPosition.getX() == currentPosition.getX()){
                        validatePosition = boardController.validatePositionY(tempPosition,tempOldPosition);

                        //Validates if the position is an adjacent position by checking if it is in the same column
                    }else if (oldPosition.getY() == currentPosition.getY()) {
                        validatePosition = boardController.validatePositionX(tempPosition, tempOldPosition);
                    }

                    //If validation is successful, it will allow the update of the token's position on the board
                    if (validatePosition){
                        boolean updateOldPosition = currentBoard.updateBoardStar(oldPosition,aPlayer);
                        if(!updateOldPosition) {
                            this.boardView.draw();
                            this.ioManager.println("This is not your token. Please enter a valid position.");
                            setPositionSlide(aPlayer,currentBoard,boardController);
                        }else{
                            mill.removeMill(currentBoard.getPlayerMill(aPlayer.getColour()),oldPosition);
                            boolean updateNewPosition = currentBoard.updateBoardToken(currentPosition, currentToken);
                            if(!updateNewPosition){
                                currentBoard.updateBoardToken(oldPosition, currentToken);
                                this.boardView.draw();
                                this.ioManager.println("This position is already occupied. Please enter a valid position.");
                                setPositionSlide(aPlayer,currentBoard,boardController);
                            }
                            else{
                                this.boardView.draw();
                                boolean isMilled = boardController.millHandling(aPlayer,currentPosition);
                                if (isMilled){
                                    System.out.println("Milled");
                                    this.boardView.draw();
                                }
                            }
                        }
                    }
                    else {
                        //Error Handling in the case of validation of position fails
                        this.boardView.draw();
                        this.errorView.draw();
                        setPositionSlide(aPlayer,currentBoard,boardController);
                    }
                }
                else{
                    //Error Handling in the case of out of bound position input
                    this.boardView.draw();
                    this.errorView.draw();
                    setPositionSlide(aPlayer,currentBoard,boardController);
                }
            }
            else{
                //Error Handling in the case of invalid old position given
                this.boardView.draw();
                this.errorView.draw();
                setPositionSlide(aPlayer,currentBoard,boardController);
            }
        }

    }

    /**
     * This method will be called when the currentGamePhase is GamePhase.SLIDEPHASE
     * */
    @Override
    public void run (Player player, Board board, BoardController boardController){
        this.setPositionSlide(player, board, boardController);
    }
}