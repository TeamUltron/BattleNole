package com.teamultron.finalproject.battlenole;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Aaron on 7/20/2015.
 */
public class GameActivity extends Activity {

    Context context;
    Random rand;
    SmsManager smsManager;

    String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
    String[] shipNames = {"", "patrol", "destroyer", "submarine", "battleship", "carrier"};

    int[] shipHealth = {0, 2, 3, 3, 4, 5};

    ImageButton[][] myGrid;
    String[][] shipGrid;
    int[][] enemyGrid;
    Switch gridSwitch;
    Drawable[] shipArt;
    Drawable[] enemyGridArt;

    Button fireButton;

    int attackX, attackY;
    String enemyNum;

    int carrierHealth = 5;
    int battleshipHealth = 4;
    int submarineHealth = 4;
    int destroyerHealth = 3;
    int patrolHealth = 3;
    private boolean valid;

    /*Multiplayer Game Key:*/
    String attackKey = "%";
    String responseKey = "&";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        attackX = attackY = -1;
        fireButton = (Button) findViewById(R.id.fire);
        enemyNum = NumberConf.myNum;
        smsManager = SmsManager.getDefault();

        shipArt = new Drawable[6];
        shipArt[0] = getResources().getDrawable(R.drawable.buttonbg);
        shipArt[1] = getResources().getDrawable(R.drawable.patrol);
        shipArt[2] = getResources().getDrawable(R.drawable.destroyer);
        shipArt[3] = getResources().getDrawable(R.drawable.submarine);
        shipArt[4] = getResources().getDrawable(R.drawable.battleship);
        shipArt[5] = getResources().getDrawable(R.drawable.carrier);

        enemyGridArt = new Drawable[3];
        enemyGridArt[0] = getResources().getDrawable(R.drawable.buttonbg);
        enemyGridArt[1] = getResources().getDrawable(R.drawable.gridhit);
        enemyGridArt[2] = getResources().getDrawable(R.drawable.gridmiss);

        gridSwitch = (Switch) findViewById(R.id.viewSwitch);


        context = this.getApplicationContext();
        rand = new Random();
        shipGrid = new String[10][10];
        enemyGrid = new int[10][10];
        //attempting to dynamically grab ids from the 100 block game grid.
        myGrid = new ImageButton[10][10];
        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                String myID = columns[i] + Integer.toString(j + 1);
                Log.e("TAG", myID);
                myGrid[i][j] = (ImageButton) findViewById(getResId(myID, context));
                shipGrid[i][j] = "";
                enemyGrid[i][j] = 0;
            }
        }
        for (int newShip = 1; newShip <= 5; ++newShip) {
            placeMyShips(newShip);
        }

        gridSwitch.setChecked(true);
        gridSwitch.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        gridSwitch.setText("Defend Mode");
        gridSwitch.getThumbDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        gridSwitch.getTrackDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        gridSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (gridSwitch.isChecked()) {
                    paintMyGrid();
                    gridSwitch.setText("Defend Mode");
                    gridSwitch.getThumbDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                    gridSwitch.getTrackDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);

                } else {
                    attackX = attackY = -1;
                    paintEnemyGrid();
                    gridSwitch.setText("Attack Mode");
                    gridSwitch.getThumbDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    gridSwitch.getTrackDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                }
            }
        });

        fireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attackX != -1 && attackY != -1){
                    String myAttack = attackKey + ";" + attackX + ";" + attackY;
                    smsManager.sendTextMessage(enemyNum, null, myAttack, null, null);
                }
            }
        });
    }

    private void paintEnemyGrid(){
        for (int i = 0; i < 10; ++i){
            for (int j = 0; j < 10; ++j){
                myGrid[i][j].setClickable(true);
                myGrid[i][j].setBackground(enemyGridArt[enemyGrid[i][j]]);
            }
        }
    }

    private void paintMyGrid(){
        for (int i = 0; i < 10; ++i){
            for (int j = 0; j < 10; ++j){
                myGrid[i][j].setClickable(false);
                myGrid[i][j].setBackground(shipArt[Arrays.asList(shipNames).indexOf(shipGrid[i][j])]);
            }
        }
    }

    private void placeMyShips(int shipID){
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
        paintEnemyGrid();
        ImageButton bController = (ImageButton) findViewById(v.getId());

        bController.setBackground(getResources().getDrawable(R.drawable.gridhit));

        breakpoint:
        for (int i = 0; i < 10; ++i){
            for (int j = 0; j < 10; ++j){
                if(myGrid[i][j] == bController){
                    attackX = i; attackY = j;
                    break breakpoint;
                }
            }
        }
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

    //TODO -- ON RECEIVE --
    //if response to attack (&), check for hit or miss.
    //      mark enemyGrid[attackX][attackY] with the proper value
    //      1 for hit, 2 for miss
    //      repaint enemygrid with paintEnemyGrid();
    //      keep track of health of hit enemy ships (hide this from user)
    //      i.e. variable in background such as enemySubHealth -= 1, if 0 ship destroyed
    //      user should only know if they've destroyed a ship, not necessarily which
    //      ship they've actually hit
    //if incoming attack (%), check for hit or miss using myGrid, shipNames, w/e
    //check out code above to get the gist of it
    //      if hit, assign gridhit drawable to incoming coordinate and repaint the friendly grid
    //      decrease health of the ship that was hit
    //      otherwise same for miss
    //
}
