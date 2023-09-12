package Model;

import Utility.Colour;

import java.util.*;

/**
 * Board class is resposible for handling the behaviour and functionality of the Board. It's main responsibilities
 * include getting token from positions, updating the board when changes with tokens are made.
 */
public class Board {
    /**
     * This method update the board with the parameter of token passed where the token has been moved to,
     * We then display the token in that position. The token's position will be board position.
     */
    private Map<Position, Position> positionPositionMap = new HashMap<>(); // The key is the coordinate input by user, the value is the actual index of the boardString
    private Map<Position, Integer> positionIndex = new HashMap<>(); // The key is the actual index of the boardString, the integer the index corresponding to the index of the positions array below. For example <(48,18),1>, then you can find (48,18) on positions[1]
    private Position[] positions = new Position[24]; // the 24 positions on the board, it has the data of the token

    private List<List<Position>> blackMill = new ArrayList<>();

    private List<List<Position>> whiteMill = new ArrayList<>();


    /**
     * The method returns the positions on the board map
     * @return position on the map
     */
    public Map<Position, Position> getPositionPositionMap() {
        return this.positionPositionMap;
    }


    /**
     * The player mill is a list of lists that save all the mills that each player has at the board
     * */
    public List<List<Position>> getPlayerMill(char playerColour){
        if (playerColour == Colour.BLACK.getDisplayChar()){
            return blackMill;
        }
        else{
            return whiteMill;
        }
    }


    /**
     * The method updates the board with the changed token positions.
     * @param setPosition is the new position that is being
     * @param token
     * @return
     */
    public boolean updateBoardToken(Position setPosition, Token token) {
        int index = 0;
        for (Position position : this.positionIndex.keySet()) {
            if (position.equals(setPosition)) {
                index = this.positionIndex.get(position); //position index
                if (this.positions[index].isEmpty()) {
                    this.positions[index].setToken(token);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public ArrayList<Position> getPlayerAllTokensPosition(Colour colour){
        ArrayList<Position> allTokenPositions = new ArrayList<>();
        for (Position position : positions) {
            if (!position.isEmpty()) {
                if (position.getToken().getTokenColour() == colour) {
                    allTokenPositions.add(position);
                }
            }
        }
        return allTokenPositions;
    }

    /**
     * This function will be called when the token is moved or removed to recover the position to *
     * */
    public boolean updateBoardStar(Position setPosition, Player player) {
        int index = 0;
        for (Position position : this.positionIndex.keySet()) {
            if (position.equals(setPosition)) {
                index = this.positionIndex.get(position); //position index
                if (this.positions[index].getToken() != null) {
                    if (this.positions[index].getToken().getTokenColour().getDisplayChar() == player.getColour()) {
                        this.positions[index].clearToken();
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    /**
     * This function will be given a position where inputted by player, and convert it to the actual position corresponding to the board
     * */
    public Position findActualPosition(Position actualPosition) {
        for (Position position : this.positionPositionMap.keySet()) {
            if (position.equals(actualPosition)) {
                int index = this.positionIndex.get(this.positionPositionMap.get(position));
                actualPosition = this.positions[index];
                return actualPosition;
            }
        }
        return null;
    }

    /**
     * This function set the position into positions array by index, this index in store in positionIndex as the value
     * When we need to access the position's token, we will get a 'virtual' coordinate, and we can then find the actual position
     * by using the virtual coordinate as a key, when we get the actual position, we can find the index, then use this index to access the board positions
     * */
    public void actualPointToPoint() {
        int counter = 0;
        for (Position position : this.positionPositionMap.values()) {
            this.positionIndex.put(position, counter);
            this.positions[counter] = position;
            counter += 1;
        }
    }

    public Position getVirtualPoint(Position position){
        for (Position virtualPosition : positionPositionMap.keySet()) {
            if (findActualPosition(virtualPosition).equals(position)){
                return virtualPosition;
            }
        }
        return null;
    }

    /**
     Sets the position-position map, which maps a position to its corresponding position.
     @param positionPositionMap The map containing the position-position mappings.
     */
    public void setPositionPositionMap(Map<Position, Position> positionPositionMap) {
        this.positionPositionMap = positionPositionMap;
    }

    /**
     Retrieves the positions array.
     @return An array of positions.
     */
    public Position[] getPositions() {
        return this.positions;
    }



}