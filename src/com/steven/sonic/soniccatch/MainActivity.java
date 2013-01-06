package com.steven.sonic.soniccatch;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
	public boolean paused = false;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        int w = getWindowManager().getDefaultDisplay().getWidth();
        int h = getWindowManager().getDefaultDisplay().getHeight();
       // GameVariables.shock = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
       
        /*
        FrameLayout Game = new FrameLayout(this);
        GameView gameView;
        gameView = new GameView(this, w, h);
        LinearLayout GameWidgets = new LinearLayout(this);
        
        Button EndGameButton = new Button(this);
       
        
        EndGameButton.setWidth(700);
        EndGameButton.setText("Start Game");
        //MyText.setText("rIZ...i");
       
        GameWidgets.addView(EndGameButton);
        
        Game.addView(gameView);
        Game.addView(GameWidgets);
        
        setContentView(Game);
        EndGameButton.setOnClickListener(this);*/
        
        
        //setContentView(gameView);
        
        InitializeOptions();

        setContentView(R.layout.activity_main);
        
        Typeface font1 = Typeface.createFromAsset(getAssets(), "Backto1982.ttf");  
        
        Button bStart = (Button) findViewById(R.id.start);
        Button bOptions = (Button) findViewById(R.id.options);
        Button bScores = (Button) findViewById(R.id.scores);
        Button bExit = (Button) findViewById(R.id.exit);
        
        bStart.setTypeface(font1);
        bOptions.setTypeface(font1);
        bExit.setTypeface(font1);
        bScores.setTypeface(font1);
        
        bStart.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				
				Intent intentStart = new Intent (MainActivity.this, GameStart.class);
				startActivity(intentStart);
			}
		});
        
        bOptions.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				
				Intent intentOptions = new Intent (MainActivity.this, GameOptions.class);
				startActivity(intentOptions);
			}
		});
        
        bScores.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				
				GameVariables.writeEnable = false;
				Intent intentLoadScores = new Intent (MainActivity.this, SimpleList.class);
				startActivity(intentLoadScores);
			}
		});
        
        
        bExit.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View view) {
        		//finish();
        		Intent intent1 = new Intent (MainActivity.this, testLoadData.class);
				startActivity(intent1);
        	}
        });
        
        
        
    }

	private void InitializeOptions() {
		SharedPreferences settings = getSharedPreferences("OptionsFile", 0);
        final SharedPreferences.Editor editor = settings.edit();
        
        if (settings.contains("Control") == false) { //if preference don't exist yet, default to true (move by acc)
        	GameVariables.controlMethod = true;
        }
        
        else if (settings.contains("Control") == true){ //if preference does exist, load it up
        	GameVariables.controlMethod = settings.getBoolean("Control", true); //set the stored value. If no value exist, default to true.
        }
		
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}






	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}






	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}






	//over ride the back button to do nothing if pressed
	//prevents users from exiting by accident
	@Override
	public void onBackPressed() {
	}


	/*public void fStart(View v) {
		// TODO Auto-generated method stub
		//Intent intent = new Intent (this, MainActivity.class);
		//startActivity(intent);
		Intent intent = new Intent (this, GameStart.class);
		startActivity(intent);
		
	}*/

}
