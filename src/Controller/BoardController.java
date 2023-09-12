package Controller;

import Model.*;
import Utility.BoardTemplate;
import Utility.Colour;
import Utility.IOManager;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Board controller class is responsible for managing the game board, including handling positions, validating moves,
 * checking for mills, and determining the game's state.
 */

public class BoardController {

    private Board board;

    private Mill mill;

    private Turn turn;
    private IOManager ioManager = IOManager.getInstance();

    /**
     Creates a new instance of the BoardController class.
     @param board The board object representing the game board.
     @param turn The Turn object representing the current turn in the game.
     @param mill The Mill object responsible for handling mills in the game.
     */
    public BoardController(Board board, Turn turn, Mill mill) {
        this.board = board;
        this.turn = turn;
        this.mill = mill;
    }

    /**
     * This method is finding all the intersection point of all the actual array string position
     * For example the intersection point is (a,1) corresponding to the actual array string position (20,50)
     * So that the actual array string position to can be found when the user input the intersection point
     */
    //Updated Version of addIntersectionPoints Method - <<<<<<Should be Working>>>>>>
    public void addIntersectionPoints() {
        Map<Position, Position> positionPositionMap = new HashMap<>();
        int lineNumber = 0; // lineNumber - y-axis of the board
        String boardNumbering = BoardTemplate.boardString.get(BoardTemplate.boardString.size() - 1); // BoardNumbering - Stores the x-axis numbering for reference
        int x = 0; // Consist of the position of the "*" symbol
        int i = 0; // Index
        for (String line : BoardTemplate.boardString) { //get each line of the string
            while (i != -1) {
                x = line.indexOf("*", x + 1); //Return position whenever there is an occurrence of "*" symbol
                if (x == -1) {
                    break;
                }
                // Stores the position of the board based on the interface
                Position boardPosition = new Position(boardNumbering.charAt(x) - 96, Character.getNumericValue(line.charAt(0)));
                // Stores the actual position in the board
                Position actualPosition = new Position(x, lineNumber);
                positionPositionMap.put(boardPosition, actualPosition);
            }
            lineNumber += 1; //Increments the y-axis by one
        }
        this.board.setPositionPositionMap(positionPositionMap);
        this.board.actualPointToPoint();
    }

    /**
     * This method will handle the validation of y-axis positions where it will check if there is a valid position in
     * between the given old and new position inputs
     *
     * @param setPosition
     * @param oldPosition
     * @return
     */
    public boolean validatePositionY(Position setPosition, Position oldPosition) {
        //Centre position declaration
        Position centrePosition = new Position(4, 4);
        //Goes through the all the positions stored
        for (Position position : this.board.getPositionPositionMap().keySet()) {
            //Filters the positions that are in the same x-axis
            if (position.getX() == setPosition.getX() && position.getX() == oldPosition.getX()) {
                //Checks if there is a valid position in between the two given inputs for the position
                if (setPosition.getY() > position.getY() && position.getY() > oldPosition.getY()) {
                    return false;
                } else if (setPosition.getY() < position.getY() && position.getY() < oldPosition.getY()) {
                    return false;
                }
                //Checks if the given positions consist of the centre position which is invalid for players
                if (setPosition.getY() > centrePosition.getY() && centrePosition.getY() > oldPosition.getY()) {
                    return false;
                }
                else if (setPosition.getY() < centrePosition.getY() && centrePosition.getY() < oldPosition.getY()) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * This method will handle the validation of x-axis positions where it will check if there is a valid position in
     * between the given old and new position inputs
     *
     * @param setPosition
     * @param oldPosition
     * @return
     */
    public boolean validatePositionX(Position setPosition, Position oldPosition) {
        //Centre position declaration
        Position centrePosition = new Position(4, 4);
        //Goes through the all the positions stored
        for (Position position : this.board.getPositionPositionMap().keySet()) {
            //Filters the positions that are in the same x-axis
            if (position.getY() == setPosition.getY() && position.getY() == oldPosition.getY()) {
                //Checks if there is a valid position in between the two given inputs for the position
                if (setPosition.getX() > position.getX() && position.getX() > oldPosition.getX()) {
                    return false;
                } else if (setPosition.getX() < position.getX() && position.getX() < oldPosition.getX()) {
                    return false;
                }
                //Checks if the given positions consist of the centre position which is invalid for players
                if (setPosition.getX() > centrePosition.getX() && centrePosition.getX() > oldPosition.getX()) {
                    return false;
                }
                else if (setPosition.getX() < centrePosition.getX() && centrePosition.getX() < oldPosition.getX()) {
                    return false;
                }
            }
        }
        return true;
    }

    /***
     * This method will check all the possible moves available in Place and Fly Phase where it will return a list of
     * possible valid moves available to the player.
     *
     * @return validPositionsList / A list of possible moves the player could make
     */
    public List<Position> checkValidMoves(){

        Position currentUserPosition = null;
        //Declaration of an arraylist to store list of valid positions
        List<Position> validPositionsList = new ArrayList<>();

        //Loops through all the positions in the board and checks all empty positions
        for (Position position : this.board.getPositions()) {
            //Checks for the user input format of the current position
            for (Position actualPosition : this.board.getPositionPositionMap().keySet()) {
                if (this.board.getPositionPositionMap().get(actualPosition).equals(position)) {
                    currentUserPosition = actualPosition;
                }
            }
            //Checks for all empty positions and add the positions to the "validPositionsList" array
            if (position.getToken() == null){
                validPositionsList.add(currentUserPosition);
            }
        }
        return validPositionsList;
    }

    /***
     * This method will check all the valid moves available for the player based on the token position given in the
     * Slide phase where it will check what are the valid positions available for the token to move in a list.
     *
     * @param oldUserPosition / The position of the token the player want to move
     * @return validPositionsList / A list of possible moves the player could make
     */
    public List<Position> checkSlideValidMoves(Position oldUserPosition) {

        //Temporary variables to set the old and current position in the user format input (e.g. {x=1, y=1} equivalent to x=a, y=1)
        Position currentUserPosition = null;
        //Declaration of an arraylist to store list of valid positions
        List<Position> validPositionsList = new ArrayList<>();
        //Find teh actual position of the given oldUserPosition input
        Position oldPosition = this.board.findActualPosition(oldUserPosition);

        //Looping through the second loop which compares and validates all the other possible positions
        for (Position currentPosition : this.board.getPositions()) {

            boolean validatePosition = false;
            //Gets the user format input of the token position
            for (Position actualPosition : this.board.getPositionPositionMap().keySet()) {
                if (this.board.getPositionPositionMap().get(actualPosition).equals(currentPosition)) {
                    currentUserPosition = actualPosition;
                }
            }
            //Validates the position to check if there are any valid possible moves available for the player
            if (currentPosition != null && currentPosition.getToken() == null && !(oldPosition.getX() == currentPosition.getX() && oldPosition.getY() == currentPosition.getY())){
                if (oldPosition.getX() == currentPosition.getX()){
                    validatePosition = this.validatePositionY(currentUserPosition,oldUserPosition);
                }
                else if (oldPosition.getY() == currentPosition.getY()) {
                    validatePosition = this.validatePositionX(currentUserPosition, oldUserPosition);
                }
                //Adds the possible moves into an array to store all the valid moves available to the player
                if (validatePosition){
                    validPositionsList.add(currentUserPosition);
                }
            }
        }
        return validPositionsList;
    }

    /***
     * This method will look for all the positions that the player can select to move to another position. It will
     * return a list of positions that are valid for slide phase conditions.
     *
     * @param colour / The colour of the player
     * @return validPositionsList / A list of possible moves the player could make
     */
    public List<Position> checkSlideValidOldPosition(Colour colour) {

        //Temporary variables to set the old and current position in the user format input (e.g. {x=1, y=1} equivalent to x=a, y=1)
        Position oldUserPosition = null;
        Position currentUserPosition = null;

        //Declaration of an arraylist to store list of valid positions
        List<Position> validPositionsList = new ArrayList<>();

        //Looping through all the token on the board based on the turn
        for (Position oldPosition : this.board.getPositions()) {
            //Checks if there is a token in existing position and if the colour matches the player's token colour
            if (oldPosition.getToken() != null && oldPosition.getToken().getTokenColour() == colour){
                //Gets the user format input of the token position
                for (Position actualPosition : this.board.getPositionPositionMap().keySet()) {
                    if (this.board.getPositionPositionMap().get(actualPosition).equals(oldPosition)) {
                        oldUserPosition = actualPosition;
                    }
                }
                //Looping through the second loop which compares and validates all the other possible positions
                for (Position currentPosition : this.board.getPositions()) {
                    boolean validatePosition = false;

                    //Gets the user format input of the token position
                    for (Position actualPosition : this.board.getPositionPositionMap().keySet()) {
                        if (this.board.getPositionPositionMap().get(actualPosition).equals(currentPosition)) {
                            currentUserPosition = actualPosition;
                        }
                    }

                    //Validates the position to check if there are any valid possible moves available for the player
                    if (currentPosition != null && currentPosition.getToken() == null && !(oldPosition.getX() == currentPosition.getX() && oldPosition.getY() == currentPosition.getY())){
                        if (oldPosition.getX() == currentPosition.getX()){
                            validatePosition = this.validatePositionY(currentUserPosition,oldUserPosition);
                        }
                        else if (oldPosition.getY() == currentPosition.getY()) {
                            validatePosition = this.validatePositionX(currentUserPosition, oldUserPosition);
                        }
                        //Adds the positions to an array that have a possibility to move to another position
                        if (validatePosition){
                            validPositionsList.add(oldUserPosition);
                            break;
                        }
                    }
                }
            }
        }
        return validPositionsList;
    }

    /***
     * This method will look for all the positions that the player can select to move to another position. It will
     * return a list of positions that are valid for fly phase conditions.
     *
     * @param colour / The colour of the player
     * @return validPositionsList / A list of possible moves the player could make
     */
    public List<Position> checkValidOldPosition(Colour colour) {

        //Temporary variables to set the old and current position in the user format input (e.g. {x=1, y=1} equivalent to x=a, y=1)
        Position currentUserPosition = null;

        //Declaration of an arraylist to store list of valid positions
        List<Position> validPositionsList = new ArrayList<>();

        //Loops through all the positions in the board and checks all empty positions
        for (Position position : this.board.getPositions()) {
            //Gets all token position on the board based on the player's colour
            if (position.getToken() != null && position.getToken().getTokenColour() == colour){
                //Checks for the user input format of the current position
                for (Position actualPosition : this.board.getPositionPositionMap().keySet()) {
                    if (this.board.getPositionPositionMap().get(actualPosition).equals(position)) {
                        currentUserPosition = actualPosition;
                    }
                }
                validPositionsList.add(currentUserPosition);
            }
        }
        return validPositionsList;
    }



    /**
     The method is responsible for checking if the game is over for a specific color by checking if there are no more
     valid moves left for that colour.

     @param colour The color of the player to check for game over.
     @return true, if the game is over for the specified color, false otherwise.
     */
    public boolean isGameOverOne(Colour colour) {
        //The temporary position used to find the token nearest (adjacent) to the current token
        Position tempPositionForX, tempPositionForY;

        //Check for first position saved into temporary position
        boolean flagForX, flagForY;

        //First Condition - Checking if there are tokens vertically
        //Checks all the position based on x-axis, loops through all the position in the board
        for (Position position : this.board.getPositions()) {

            //Set flag to true and initialise the current position into the temporary position
            flagForX = true;
            tempPositionForX = position;

            for (Position secondPosition : this.board.getPositions()) {

                //Checks if the compared position is the same and if it is a token position
                //Filters the colour of the token for position comparison
                if (!position.equals(secondPosition) && position.getToken() != null && position.getToken().getTokenColour() == colour) {

                    //If it is in the same x-axis it will run the comparison
                    if (position.getX() == secondPosition.getX()) {

                        //Checks if it is the first initialisation of the temporary position
                        if (flagForX){
                            tempPositionForX = secondPosition;
                            flagForX = false;   //Sets flag to false once the first temporary position is set
                        }

                        //Determines the nearest token position to the current token position
                        else if (abs(tempPositionForX.getY() - position.getY()) > abs(secondPosition.getY() - position.getY())){
                            tempPositionForX = secondPosition;
                        }

                        //Checks if the comparison between both token position is the same where it would check both positions for a token
                        else if (abs(tempPositionForX.getY() - position.getY()) == abs(secondPosition.getY()- position.getY())) {
                            if (tempPositionForX.isEmpty()){
                                return false;
                            }
                            if (secondPosition.isEmpty()){
                                return false;
                            }
                        }
                    }
                }
            }
            //Checks if the token next to the current token position is empty
            if (!flagForX && tempPositionForX.isEmpty()){
                return false;
            }
        }

        //Second Condition - Checking if there are tokens horizontally
        //Checks all the position based on y-axis, loops through all the position in the board
        for (Position position : this.board.getPositions()) {

            //Set flag to true and initialise the current position into the temporary position
            flagForY = true;
            tempPositionForY = position;

            for (Position secondPosition : this.board.getPositions()) {
                //Checks if the compared position is the same and if it is a token position
                //Filters the colour of the token for position comparison

                if (!position.equals(secondPosition) && position.getToken() != null && position.getToken().getTokenColour() == colour) {

                    //If it is in the same y-axis it will run the comparison
                    if (position.getY() == secondPosition.getY()) {

                        //Checks if it is the first initialisation of the temporary position
                        if (flagForY) {
                            tempPositionForY = secondPosition;
                            flagForY = false;  //Sets flag to false once the first temporary position is set
                        }

                        //Determines the nearest token position to the current token position
                        else if (abs(tempPositionForY.getX() - position.getX()) > abs(secondPosition.getX() - position.getX())) {
                            tempPositionForY = secondPosition;
                        }

                        //Checks if the comparison between both token position is the same where it would check both positions for a token
                        else if (abs(tempPositionForY.getX() - position.getX()) == abs(secondPosition.getX() - position.getX())) {
                            if (tempPositionForY.isEmpty()) {
                                return false;
                            }
                            if (secondPosition.isEmpty()) {
                                return false;
                            }
                        }
                    }
                }
            }
            //Checks if the token next to the current token position is empty
            if (!flagForY && tempPositionForY.isEmpty()){
                return false;
            }
        }
        //Returns true which means that no more valid moves are left for the player to move
        return true;
    }

    /**
     * this function is the main mill handling function
     * */
    public boolean millHandling(Player player, Position currentPosition) {
        // check if the moved/placed token forms a mill
        boolean isMill = this.mill.findAdjacentMillPosition(player.getColour(), currentPosition, board.getPositions(),board.getPlayerMill(player.getColour())); //check if it is mill
        if (isMill) {
            Position removeTokenPosition = new Position();
            ArrayList<Position> validRemovePosition = new ArrayList<>();
            switch (player.getPlayerType()) {
                case HUMANPLAYER:
                    this.ioManager.println("You have milled !!!!..... Select an opposite token to remove");
                    removeTokenPosition = player.play(validRemovePosition,"");
                    //Error checkpoint 1: check if there is any token at the chosen current position
                    removeTokenPosition = setupRemovedPosition(removeTokenPosition);
                    break;
                case AI:
                    this.ioManager.println("AI have milled");
                    String playerAction = "AI is removing your token...";
                    Player opponent = turn.getNextPlayer();
                    ArrayList<Position> allOpponentToken = board.getPlayerAllTokensPosition(opponent.getColourRaw());
                    for (Position removePosition : allOpponentToken) {
                        if(mill.removeAble(removePosition,board.getPlayerMill(opponent.getColour()),opponent.getTokenOnBoardCount())){
                            validRemovePosition.add(removePosition);
                        }
                    }
                    removeTokenPosition = player.play(validRemovePosition,playerAction);
                    System.out.println("AI has removed your token that was on "+board.getVirtualPoint(removeTokenPosition).toString());
                    System.out.println("Click enter to continue the game");
                    ioManager.pauseGame();
                    break;

            }
            while (removeTokenPosition == null || removeTokenPosition.isEmpty()) {
                this.ioManager.println("There is no token in this position. Please enter a valid position.");
                removeTokenPosition = this.ioManager.readPosition("");
                removeTokenPosition = setupRemovedPosition(removeTokenPosition);
            }

            while (true) {
                // the player tried to move their own token, which are not allow, hence ask for the input again
                if (removeTokenPosition != null && !removeTokenPosition.isEmpty() && removeTokenPosition.getToken().getTokenColour().getDisplayChar() == player.getColour()) {
                    this.ioManager.println("You can't remove your token. Please enter a valid position");
                    removeTokenPosition = this.ioManager.readPosition("");
                    removeTokenPosition = setupRemovedPosition(removeTokenPosition);
                }
                // the player moved the opposite player's token
                if (removeTokenPosition != null && !removeTokenPosition.isEmpty() && removeTokenPosition.getToken().getTokenColour().getDisplayChar() != player.getColour()) {
                    // get the opposite player
                    Player oppositePlayer = turn.getNextPlayer();
                    // check if the token is removeable
                    if(mill.removeAble(removeTokenPosition,board.getPlayerMill(oppositePlayer.getColour()),oppositePlayer.getTokenOnBoardCount())) {
                        // then remove the mill that the token was in, if there is any
                        mill.removeMill(board.getPlayerMill(player.getColour()),removeTokenPosition); //every time check if the token that been moved is from a mill
                        // after removed it, update the board
                        this.board.updateBoardStar(removeTokenPosition, oppositePlayer);
                        // remove the token from the player
                        oppositePlayer.popTokenOnBoardArray();
                        return true;
                    }
                    // it is not removeable, hence ask for the input again
                    else{
                        this.ioManager.println("You can't remove the token that is in the mill");
                        removeTokenPosition = this.ioManager.readPosition("");
                        removeTokenPosition = setupRemovedPosition(removeTokenPosition);
                    }
                }
                if(removeTokenPosition == null || removeTokenPosition.isEmpty()){
                    this.ioManager.println("There is no token in this position. Please enter a valid position.");
                    removeTokenPosition = this.ioManager.readPosition("");
                    removeTokenPosition = setupRemovedPosition(removeTokenPosition);
                }
            }
        }
        return false;
    }

    /** The methos id responsible for setting up a removed position by finding the actual position and assigning the
     * corresponding token to it
     @param position The position to set up as a removed position.
     @return The updated position object with the assigned token, or {@code null} if the position is not found on
     the board.
     */
    public Position setupRemovedPosition(Position position) {
        if(board.getPositionPositionMap().containsKey(position)) {
            position = this.board.findActualPosition(position);
            for (Position snapshot : this.board.getPositions()) {
                if (snapshot.equals(position) && !snapshot.isEmpty()) {
                    position.setToken(snapshot.getToken());
                }
            }
            return position;
        }
        return null;
    }

    /**
     * The method check sif the game is over by checking if the number of tokens for a player is less than three.
     * @return true, if the game is over; false otherwise
     */
    public Boolean isGameOverTwo () {
            int blackPlayerTokens = 0;
            int whitePlayerTokens = 0;
            for (Position position : board.getPositions()) {
                if (!position.isEmpty() && position.getToken().getTokenColour() == Colour.BLACK) {
                    blackPlayerTokens++;
                }
                if (!position.isEmpty() && position.getToken().getTokenColour() == Colour.WHITE) {
                    whitePlayerTokens++;
                }
            }
            if (blackPlayerTokens < 3) {
                this.ioManager.println("WHITE WON");
                return true;
            }
            if (whitePlayerTokens < 3) {
                this.ioManager.println("BLACK WON");
                return true;
            } else {
                return false;
            }
        }

}