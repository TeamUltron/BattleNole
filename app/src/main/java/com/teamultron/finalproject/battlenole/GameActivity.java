package com.teamultron.finalproject.battlenole;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.lang.reflect.Field;

/**
 * Created by Aaron on 7/20/2015.
 */
public class GameActivity extends Activity {

    String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
    ImageButton[][] myGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //attempting to dynamically grab ids from the 100 block game grid.
        myGrid = new ImageButton[10][10];
        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                String myID = columns[i] + Integer.toString(j + 1);
                myGrid[i][j] = (ImageButton) findViewById(getResId(myID, GameActivity.class));
            }
        }


    }

    public void gridClick(View v){
        ImageButton bController = (ImageButton) findViewById(v.getId());

        bController.setBackground(getResources().getDrawable(R.drawable.gridhit));
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
