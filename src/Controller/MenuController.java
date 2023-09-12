package Controller;

import Utility.IOManager;
import View.View;

public class MenuController {
    private IOManager ioManager = IOManager.getInstance();;
    private View menuView;

    public MenuController(View menuView){
        this.menuView = menuView;
    }

    /**
     * This method will draw the menu and ask which game mode the user wants
     * */
    public int chooseGameMode(){
        menuView.draw();
        return ioManager.readInt();
    }

}
