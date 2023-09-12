package Model;

import Utility.Colour;

import java.util.ArrayList;
import java.util.List;

public interface Player {
    int getTokenCount();
    List<Token> getTokensArray();
    int getTokenOnBoardCount();
    void addTokenArray(Token token);
    void popTokenOnBoardArray();
    void addTokenOnBoardCount(Token token);
    Token popTokenArray();
    String getName();
    void setName(String name);
    char getColour();
    Position play(List<Position> validMoves,String playerAction);
    PlayerType getPlayerType();
    Colour getColourRaw();
}
