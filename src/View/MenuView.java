package View;

public class MenuView implements View{
    private String asciiArt =
            " __    __  __                            __       __                            __       __                                __\n"+
            "/  \\  /  |/  |                          /  \\     /  |                          /  \\     /  |                              /  |\n"+
            "$$  \\ $$ |$$/  _______    ______        $$  \\   /$$ |  ______   _______        $$  \\   /$$ |  ______    ______    ______  $$/   _______\n"+
            "$$$  \\$$ |/  |/       \\  /      \\       $$$  \\ /$$$ | /      \\ /       \\       $$$  \\ /$$$ | /      \\  /      \\  /      \\ /  | /       |\n"+
            "$$$$  $$ |$$ |$$$$$$$  |/$$$$$$  |      $$$$  /$$$$ |/$$$$$$  |$$$$$$$  |      $$$$  /$$$$ |/$$$$$$  |/$$$$$$  |/$$$$$$  |$$ |/$$$$$$$/\n"+
            "$$ $$ $$ |$$ |$$ |  $$ |$$    $$ |      $$ $$ $$/$$ |$$    $$ |$$ |  $$ |      $$ $$ $$/$$ |$$ |  $$ |$$ |  $$/ $$ |  $$/ $$ |$$      \\\n"+
            "$$ |$$$$ |$$ |$$ |  $$ |$$$$$$$$/       $$ |$$$/ $$ |$$$$$$$$/ $$ |  $$ |      $$ |$$$/ $$ |$$ \\__$$ |$$ |      $$ |      $$ | $$$$$$  |\n"+
            "$$ | $$$ |$$ |$$ |  $$ |$$       |      $$ | $/  $$ |$$       |$$ |  $$ |      $$ | $/  $$ |$$    $$/ $$ |      $$ |      $$ |/     $$/\n"+
            "$$/   $$/ $$/ $$/   $$/  $$$$$$$/       $$/      $$/  $$$$$$$/ $$/   $$/       $$/      $$/  $$$$$$/  $$/       $$/       $$/ $$$$$$$/\n";

    private String mainMenu =
            "---------------------------------------\n"+
            "1. Player vs Player\n"+
            "2. Player vs AI\n"+
            "---------------------------------------\n"+
            "Please select a game mode (in integer): ";

    @Override
    public void draw() {
        System.out.println(asciiArt);
        System.out.println(mainMenu);
    }

    @Override
    public ViewEnum getViewEnum() {
        return ViewEnum.MENUVIEW;
    }
}
