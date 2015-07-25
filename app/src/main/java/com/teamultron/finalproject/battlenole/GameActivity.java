package com.teamultron.finalproject.battlenole;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.Random;

/**
 * Created by Aaron on 7/20/2015.
 */
public class GameActivity extends Activity {

    Context context;
    Random rand;
    String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
    String[] shipNames = {"", "patrol", "destroyer", "submarine", "battleship", "carrier"};
    int[] shipHealth = {0, 2, 3, 3, 4, 5};
    ImageButton[][] myGrid;
    String[][] shipGrid;

    Drawable[] shipArt;

    int carrierHealth = 5;
    int battleshipHealth = 4;
    int submarineHealth = 4;
    int destroyerHealth = 3;
    int patrolHealth = 3;
    private boolean valid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        shipArt = new Drawable[6];
        shipArt[0] = null;
        shipArt[1] = getResources().getDrawable(R.drawable.patrol);
        shipArt[2] = getResources().getDrawable(R.drawable.destroyer);
        shipArt[3] = getResources().getDrawable(R.drawable.submarine);
        shipArt[4] = getResources().getDrawable(R.drawable.battleship);
        shipArt[5] = getResources().getDrawable(R.drawable.carrier);


        context = this.getApplicationContext();
        rand = new Random();
        shipGrid = new String[10][10];
        //attempting to dynamically grab ids from the 100 block game grid.
        myGrid = new ImageButton[10][10];
        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                String myID = columns[i] + Integer.toString(j + 1);
                Log.e("TAG", myID);
                myGrid[i][j] = (ImageButton) findViewById(getResId(myID, context));
                shipGrid[i][j] = "";
            }
        }
        for (int newShip = 1; newShip <= 5; ++newShip) {
            placeMyShips(newShip);
        }
    }

    public void placeMyShips(int shipID){
        int runcount = 0;
        boolean ready = false, badstart = false;
        int dir = rand.nextInt(4) + 1; //1 North, 2 East, 3 South, 4 West
        int position = rand.nextInt(100);
        int myCol = position / 10;
        int myRow = position % 10;
//        if (shipGrid[myCol][myRow] == ""){
//            myGrid[myCol][myRow].setBackground(getResources().getDrawable(R.drawable.battleship));
//            shipGrid[myCol][myRow] = shipNames[shipID];
//        }
//        else{badstart = true;}

//        if (checkValidPlace(1, myCol, myRow, shipID)){
//            direction = 1;
//        }
//        else if(checkValidPlace(2, myCol, myRow, shipID)){
//            direction = 2;
//        }
//        else if (checkValidPlace(3, myCol, myRow, shipID)){
//            direction = 3;
//        }
//        else if (checkValidPlace(4, myCol, myRow, shipID)){
//            direction = 4;
//        }

       // if (!badstart){
            while (!ready){
                if (checkValidPlace(dir, myCol, myRow, shipID)){
                    ready = true;
                }
                else{
                    ++dir;
                    if (dir > 4){
                        dir = 1;
                    }
                }
                ++runcount;
                if (runcount == 4 && !ready){
                    break;
                }
            }
       // }
        if (!ready){
            myGrid[myCol][myRow].setBackground(getResources().getDrawable(R.drawable.buttonbg));
            shipGrid[myCol][myRow] = "";
            placeMyShips(shipID);
        }
        else {
            placeShip(dir, shipID, myCol, myRow);
        }

    }

    private void placeShip(int direction, int shipID, int myCol, int myRow){
        int increment  = 0;
        if(direction == 1){
            for (int x = 0; x < shipHealth[shipID]; ++x) {
                shipGrid[myCol + increment][myRow] = shipNames[shipID];
                myGrid[myCol + increment][myRow].setBackground(shipArt[shipID]);
                ++increment;
            }
        }
        else if (direction == 2){
            for (int x = 0; x < shipHealth[shipID]; ++x) {
                shipGrid[myCol][myRow + increment] = shipNames[shipID];
                myGrid[myCol][myRow + increment].setBackground(shipArt[shipID]);
                ++increment;
            }
        }
        else if (direction == 3){
            for (int x = 0; x < shipHealth[shipID]; ++x) {
                shipGrid[myCol - increment][myRow] = shipNames[shipID];
                myGrid[myCol - increment][myRow].setBackground(shipArt[shipID]);
                ++increment;
            }
        }
        else{
            for (int x = 0; x < shipHealth[shipID]; ++x) {
                shipGrid[myCol][myRow - increment] = shipNames[shipID];
                myGrid[myCol][myRow - increment].setBackground(shipArt[shipID]);
                ++increment;
            }
        }
    }

    private boolean checkValidPlace(int direction, int myCol, int myRow, int shipID){
        int increment = 0;
        valid = true;
        if (direction == 1){
            for (int x = 0; x < shipHealth[shipID]; ++x){
                if (myCol + increment < 0 || myCol + increment >= 10 || shipGrid[myCol + increment][myRow] != ""){
                    valid = false;
                    break;
                }
                ++increment;
            }
        }
        else if (direction == 2){
            for (int x = 0; x < shipHealth[shipID]; ++x){
                if (myRow + increment < 0 || myRow + increment >= 10 || shipGrid[myCol][myRow + increment] != ""){
                    valid = false;
                    break;
                }
                ++increment;
            }
        }
        else if (direction == 3){
            for (int x = 0; x < shipHealth[shipID]; ++x){
                if (myCol - increment < 0 || myCol - increment >= 10 || shipGrid[myCol - increment][myRow] != ""){
                    valid = false;
                    break;
                }
                ++increment;
            }
        }
        else if (direction == 4){
            for (int x = 0; x < shipHealth[shipID]; ++x){
                if (myRow - increment < 0 || myRow - increment >= 10 || shipGrid[myCol][myRow - increment] != ""){
                    valid = false;
                    break;
                }
                ++increment;
            }
        }
        return valid;
    }

    public void gridClick(View v){
        ImageButton bController = (ImageButton) findViewById(v.getId());

        bController.setBackground(getResources().getDrawable(R.drawable.gridhit));
    }

    public  int getResId(String resName, Context c) {

        return getResources().getIdentifier(resName, "id", getPackageName());
//        try {
//            Field idField = c.getDeclaredField(resName);
//            return idField.getInt(idField);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1;
//        }
    }
}
