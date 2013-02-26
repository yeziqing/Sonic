package com.steven.sonic.soniccatch;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import android.os.Bundle;
import android.os.Vibrator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
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
        
        
        if (GameVariables.s == 1) { //no need to reset to 0, because after restart, it is initated to 0
        	restart(10);
        }
        
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
        
        InitializeOptions(); //load up the game options data. For now, its just control options.

        setContentView(R.layout.activity_main);
        
        Typeface font1 = Typeface.createFromAsset(getAssets(), "Backto1982.ttf");  
        
        Button bStart = (Button) findViewById(R.id.start);
        Button bOptions = (Button) findViewById(R.id.options);
        Button bScores = (Button) findViewById(R.id.scores);
        Button bExit = (Button) findViewById(R.id.exit);
        Button bSettings = (Button) findViewById(R.id.settings);
        Button bEarOption = (Button) findViewById(R.id.earOption);
        
        bStart.setTypeface(font1);
        bOptions.setTypeface(font1);
        bExit.setTypeface(font1);
        bScores.setTypeface(font1);
        bSettings.setTypeface(font1);
        bEarOption.setTypeface(font1);
        
        bStart.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				GameVariables.story = 0;
				GameVariables.help = 0;
				GameVariables.where = 0;
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
        		//GameVariables.story = 1;
        		GameVariables.help = 1;
        		GameVariables.where = 1;
        		Intent intentStart = new Intent (MainActivity.this, GameStart.class);
				startActivity(intentStart);
        		//finish();
        	}
        });
        
        bSettings.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				Intent settingsPage = new Intent (MainActivity.this, Settings.class);
				startActivity(settingsPage);
				
			}
		});
        
        bEarOption.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				Intent earPage = new Intent (MainActivity.this, EarOption.class);
				startActivity(earPage);
			}
		});
        
        
  
    }
	
	
	//force restart the app
	public void restart(int delay) {
	    PendingIntent intent = PendingIntent.getActivity(this.getBaseContext(), 0, new Intent(getIntent()), getIntent().getFlags());
	    AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
	    manager.set(AlarmManager.RTC, System.currentTimeMillis() + delay, intent);
	    System.exit(2);
	}

	private void InitializeOptions() {
		SharedPreferences settings = getSharedPreferences("OptionsFile", 0);
        final SharedPreferences.Editor editor = settings.edit();
        
        //initialize control option
        if (settings.contains("Control") == false) { //if preference don't exist yet, default to true (move by acc)
        	GameVariables.controlMethod = true;
        } else if (settings.contains("Control") == true){ //if preference does exist, load it up
        	GameVariables.controlMethod = settings.getBoolean("Control", true); //set the stored value. If no value exist, default to true.
        }
        
        //initialize ear option
		if (settings.contains("Ear") == false) { // if preference don't exist yet, default to 0 (test both ears)
			GameVariables.earMethod = 0;
		} else if (settings.contains("Ear") == true) { // if preference exists, load the saved preference value into earMethod
			GameVariables.earMethod = settings.getInt("Ear", 0); //get the saved value and set it to earMethod
		}
		
		
		//Toast.makeText(getApplicationContext(), String.valueOf(GameVariables.controlMethod), Toast.LENGTH_SHORT).show();
		//Toast.makeText(getApplicationContext(), String.valueOf(GameVariables.earMethod), Toast.LENGTH_SHORT).show();
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
		//recreate();
	}






	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//finish();
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
