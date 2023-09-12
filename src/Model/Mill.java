package Model;

import Utility.Colour;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  The class is responsible for the mills functionality and behaviour. It is responsible for finding adjacent mill
 *  positions, checking for mills and removing mills.
 */
public class Mill {

    /**
     Finds adjacent mill positions based on the player colour, current position, board snapshot, and player mills.
     @param playerColour The colour of the player.
     @param position The current position.
     @param boardSnapshot The snapshot of the board.
     @param playerMill The list of player mills.
     @return True if adjacent mill positions are found, false otherwise.
     */
    public boolean findAdjacentMillPosition(char playerColour, Position position, Position[] boardSnapshot,List<List<Position>> playerMill){
        // get data that stored in properties
        ResourceBundle resourceBundle = ResourceBundle.getBundle("millsPosition");
        int millNumbers = Integer.parseInt(resourceBundle.getString("millNumbers"));
        int coordinateCounter = Integer.parseInt(resourceBundle.getString("coordinateCounter"));
        final int COORDINATEX = Integer.parseInt(resourceBundle.getString("coordinateX"));
        final int COORDINATEY = Integer.parseInt(resourceBundle.getString("coordinateY"));
        List<Position> adjacentMillPositions = new ArrayList<>();
        boolean isMill = false;


        // loop through all the possible mills
        for (int i = 0; i < millNumbers; i++) {
            adjacentMillPositions.clear(); // we only need a set of mill each iteration
            String key = String.valueOf(i);
            int x = -1; //x coordinate
            int y = -1; //y coordinate

            // format the data since it is store in string
            for (String s : resourceBundle.getString(key).split(",")) {
                s = s.replaceAll("\\(|\\)", "");
                if(coordinateCounter == COORDINATEX){
                    x = Integer.parseInt(s);
                    coordinateCounter = COORDINATEY;
                }else{
                    y = Integer.parseInt(s);
                    coordinateCounter = COORDINATEX;
                }
                if (x != -1 && y != -1){
                    adjacentMillPositions.add(new Position(x,y));
                    x = -1;
                    y = -1;
                }
            }

            // once we got the mill positions
            // we need to get the position's token data
            for (Position adjacentMillPosition : adjacentMillPositions) {
                for (Position snapshot : boardSnapshot) {
                    if (snapshot.equals(adjacentMillPosition) && !snapshot.isEmpty()){
                        adjacentMillPosition.setToken(snapshot.getToken());
                    }
                }
            }

            // if this set of mill positions has position that just moved/placed
            for (Position adjacentMillPosition:adjacentMillPositions) {
                if(position.equals(adjacentMillPosition)){
                    // we need to continue the loop even though it is a mill, hence we make a mill copy and save the isMill(true) for return the boolean
                    boolean isMillCopy;
                    if (!isMill) {
                        isMill = isMill(playerColour, adjacentMillPositions);
                        isMillCopy = isMill;
                    }
                    else{
                        isMillCopy = isMill(playerColour, adjacentMillPositions);
                    }
                    if (isMillCopy) {
                        List<Position> temp = new ArrayList<>(adjacentMillPositions);
                        playerMill.add(temp); // add the set of mill into playerMill
                    }
                }
            }
        }
        checkDuplicateMill(playerMill); //remove duplicate
        if(isMill){
            return true;
        }
        return false;
    }


    /**
     * the function is responsible for removing the player's mills.
     * @param playerMill refers to the mill formed with three tokens by a player
     */
    private void checkDuplicateMill(List<List<Position>> playerMill){
        List<List<Position>> uniqueLists = new ArrayList<>();
        Set<List<Position>> seen = new HashSet<>();

        for (List<Position> sublist : playerMill) {
            // Check if the sublist has been seen before
            if (!seen.contains(sublist)) {
                // Add the sublist to the list of unique lists
                uniqueLists.add(sublist);
                // Add the sublist to the set of seen sublists
                seen.add(sublist);
            }
        }
        playerMill.clear();
        playerMill.addAll(uniqueLists);
    }

    /**
     * a list with 3 positions will be passed, this function will check if these 3 position have tokens, and if these token is belongs to the player that just placed or moved their token.
     * This two conditions must match in all three position, otherwise, the player hasn't milled.
     * */
    private boolean isMill(char playerColour, List<Position> positions){

        for (Position position:positions) {
            if(position.isEmpty() || position.getToken().getTokenColour().getDisplayChar() != playerColour){
                return false;
            }
        }
        return true;
    }

    /**
     * This function if the token is removeAble, it will first call the isAllTokenInMill to check if all the player's token is in the mill, then it is able to remove
     * if not, it is not able to move
     * */
    public boolean removeAble(Position tokenToRemove, List<List<Position>> playerMill,int playerTokenNum){
        for (List<Position> positions : playerMill) {
            for (Position position : positions) {
                if (isAllTokenInMill(playerMill,playerTokenNum)){
                    removeMill(playerMill,tokenToRemove);
                    return true;
                }
                else if(position.equals(tokenToRemove)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This function remove the mill that the token that has been removed or moved was in
     * */
    public void removeMill(List<List<Position>> playerMill, Position tokenMoveOrRemove){
        List<List<Position>> copyPlayerMill = new ArrayList<>(playerMill);
        for (int i = 0; i < copyPlayerMill.size(); i++) {
            for (int j = 0; j < copyPlayerMill.get(i).size(); j++) {
                if(playerMill.get(i).get(j).equals(tokenMoveOrRemove)){
                    playerMill.remove(i);
                    return;
                }
            }
        }
    }

    /**
     * This function check if the player's all token are in mills
     * */
    private boolean isAllTokenInMill(List<List<Position>> playerMill, int playerTokenNum){
        List<Position> allPosition = new ArrayList<>();
        for (List<Position> positions : playerMill) {
            for (Position position : positions) {
                allPosition.add(position);
            }
        }
        List<Position> filteredPosition = allPosition.stream().distinct().collect(Collectors.toList());
        if(filteredPosition.size() == playerTokenNum){
            return true;
        }
        return false;
    }

}

