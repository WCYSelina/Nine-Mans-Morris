import Controller.BoardController;
import Controller.Game;
import Controller.GamePhase.GamePhase;
import Controller.MenuController;
import Controller.Turn;
import Model.*;
import Utility.Colour;
import View.BoardView;
import View.GameView;
import View.TurnView;
import View.View;
import View.ErrorView;
import View.MenuView;
import java.util.ArrayList;
import java.util.List;

/**
 * src.Application class is the main class.
 * The class is responsible for running the game.
 */
public class Application {
    private static List<View> view = new ArrayList<View>();
    private static MenuController menuController;

    /**
     * main function that runs the game
     * @param args
     */
    public static void main(String[]args){

        ArrayList<Player> players = new ArrayList<>();

        TurnModel turnModel = new TurnModel();
        TurnView turnView = new TurnView();
        Turn turn = new Turn(turnView,turnModel);

        View menuView = new MenuView();
        menuController = new MenuController(menuView);

        chooseGameMode(players,turnModel);
        view.add(new GameView(players));

        Board board = new Board();
        view.add(new BoardView(board));

        view.add(new ErrorView());
        Mill mill = new Mill();
        BoardController boardController = new BoardController(board,turn,mill);
        boardController.addIntersectionPoints();

        Game game = new Game(board,view,turn,boardController);
        game.startGame(GamePhase.PLACEPHASE);
        game.endGame();
    }
    public static void chooseGameMode(ArrayList<Player> players, TurnModel turnModel){
        int gameOption = menuController.chooseGameMode();

        switch (gameOption){
            case 1:
                playerVPlayer(players,turnModel);
                break;
            case 2:
                playerVAi(players,turnModel);
                break;
            default:
                chooseGameMode(players,turnModel);
        }
    }
    public static void playerVPlayer(ArrayList<Player> players, TurnModel turnModel){
        ArrayList<Token> tokenPlayer1 = createToken(Colour.BLACK);
        Player newPlayer1 = new HumanPlayer(tokenPlayer1);
        newPlayer1.setName("Player 1");
        players.add(newPlayer1);
        turnModel.addPlayer(newPlayer1);

        ArrayList<Token> tokenPlayer2 = createToken(Colour.WHITE);
        Player newPlayer2 = new HumanPlayer(tokenPlayer2);
        newPlayer2.setName("Player 2");
        players.add(newPlayer2);
        turnModel.addPlayer(newPlayer2);
    }

    public static void playerVAi(ArrayList<Player> players, TurnModel turnModel){
        ArrayList<Token> tokenPlayer1 = createToken(Colour.BLACK);
        Player newPlayer1 = new HumanPlayer(tokenPlayer1);
        newPlayer1.setName("Player 1");
        players.add(newPlayer1);
        turnModel.addPlayer(newPlayer1);

        ArrayList<Token> tokenPlayer2 = createToken(Colour.WHITE);
        Player AI = new AI(tokenPlayer2);
        players.add(AI);
        turnModel.addPlayer(AI);
    }

    public static ArrayList<Token> createToken(Colour colour){
        ArrayList<Token> token = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            token.add(new Token(colour));
        }
        return token;
    }
}
