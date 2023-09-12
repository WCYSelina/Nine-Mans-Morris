package View;

import Model.Board;
import Model.Position;
import Utility.BoardTemplate;
import Utility.IOManager;

public class ErrorView implements View{

    /**
     * position save in hashMap, where we can get the actual position(string) of the string board by the board position(game view)
     * The game will ask user where to move the token to, they can enter the position that is able to see in the game board
     * and we can use this position as a key to find the actual position in the String array and change the symbol in that position
     */
    private IOManager ioManager = IOManager.getInstance();

    /***
     * This method handles the display of the nine men morris board where it would print out the board in terminal
     * once this method is called.
     */
    @Override
    public void draw() {
        this.ioManager.println("[Invalid Token Position] Please input a valid token position");
    }


    @Override
    public ViewEnum getViewEnum() {
        return ViewEnum.ERRORVIEW;
    }

}
