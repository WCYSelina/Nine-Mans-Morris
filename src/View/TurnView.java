package View;

import Model.Player;
import Utility.IOManager;

public class TurnView implements View{

    private IOManager ioManager = IOManager.getInstance();

    private Player player;

    public void setPlayer(Player player){
        this.player = player;
    }

    /***
     *
     */
    @Override
    public void draw() {
        this.ioManager.println("\n[ " + player.getName() + " Turn , Your token colour is: "+player.getColour()+"]");
    }

    @Override
    public ViewEnum getViewEnum() {
        return ViewEnum.TURNVIEW;
    }
}
