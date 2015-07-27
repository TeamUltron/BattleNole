package com.teamultron.finalproject.battlenole;

/**
 * Created by davidperez on 7/25/2015.
 */
public class BattleShipMessage {
    private String column;
    private String response;
    private int row;
    private boolean isValidMessage;
    private boolean isResponse;

    //Default constructor is invalid, used for checking purposes.
    public BattleShipMessage() {
        column = null;
        response = null;
        row = -1;
        isValidMessage = false;
        isResponse = false;
    }
    public BattleShipMessage(int r, String col){
        //TODO:check bounds here for column and row
        column = col;
        row = r;
        isValidMessage = true;
        response = null;
    }
    //Will contain strings like "Hit!" or "Miss!"
    public BattleShipMessage(String r) {
        response = r;
        isValidMessage = true;
        isResponse = true;
        column = null;
        row = -1;
    }

    public String getColumn() {return column;}
    public int getRow() {return row;}
    public boolean getisValidMessage() {return isValidMessage;}
}
