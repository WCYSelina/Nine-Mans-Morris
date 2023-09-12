package Model;

import java.util.*;


/**
 * src.Model.Position class represents positions on the game board
 * The purpose of the class is to provide intersections with
 * properties so that position information can be determined
 * for various game functionalities
 * */
public class Position {

    /**
     * private integer describing the x co-ordinate of the board
     * */
    private int x;
    /**
     * private integer expressing the y co-ordinate of the board
     */
    private int y;

    private Token token;

    public Position(){

    }

    /**
     * Constructor
     * @param x is the x co-ordinate
     * @param y is the y co-ordinate
     */
    public Position(int x,int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Getter to fetch x co-ordinate
     * @return x, x-value of the position
     */
    public int getX(){
        return x;
    }

    /**
     * Setter to update the position
     * @param _xPosition, updated x position
     */
    public void setX(int _xPosition){
        this.x = _xPosition;
    }

    /**
     * Getter to fetch y co-ordinate
     * @return y-value of the position
     */
    public int getY(){
        return y;
    }

    /**
     * Setter to update the position
     * @param _yPosition, updated y position
     */
    public void setY(int _yPosition){
        this.y = _yPosition;
    }

    /**
     * method to fetch the token
     * @return token
     */
    public Token getToken(){
        return this.token;
    }

    /**
     * setter to set the token
     * @param token token
     */
    public void setToken(Token token){
        this.token = token;
    }

    /**
     * method to clear the token
     */
    public void clearToken(){
        this.token = null;
    }

    /**
     * method to check if token is present
     * @return true, if token is not present, false otherwise
     */
    public boolean isEmpty(){
        if(this.token == null){
            return true;
        }
        return false;
    }

    /**
     * override equal method to check if both position has the same x and y
     * @param obj object for comparison with the position
     * @return true, if position has the same x and y co-ordinates,
     * otherwise, returns false
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(!(obj instanceof Position)){
            return false;
        }
        if(obj == this){
            return true;
        }
        Position that = (Position) obj;
        return this.x == that.x && this.y == that.y;
    }

    /**
     * override method for hashcode to represent hash values
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }

    /**
     * returns a string of the position
     * @return string of the position
     */
    @Override
    public String toString() {
        return "x=" + (char) ('a' + x-1) + ", y=" + y;
    }
}

