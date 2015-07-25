package com.teamultron.finalproject.battlenole;

/**
 * Created by davidperez on 7/25/2015.
 */
public class BattleShipMessage {
    private String column;
    private int row;
    private boolean isValidMessage;

    //Default constructor is invalid, used for checking purposes.
    public BattleShipMessage() {
        column = "";
        row = -1;
        isValidMessage = false;
    }
    public BattleShipMessage(int r, String col){
        //TODO:check bounds here for column and row
        column = col;
        row = r;
        isValidMessage = true;
    }

    public String getColumn() {return column;}
    public int getRow() {return row;}
    public boolean getisValidMessage() {return isValidMessage;}
}
