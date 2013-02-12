package com.steven.sonic.soniccatch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class GameStart extends Activity{
	public boolean paused = false;
	
	Intent starterIntent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Intent intentStart = getIntent();
		starterIntent = getIntent();
		
		/*if (GameVariables.s == 1) {
			restartActivity();
			GameVariables.s = 0;
		}*/
		
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
		
		
        
	}
	
	void restartActivity() 
	{
		GameStart.this.finish();
		Intent mIntent = new Intent(GameStart.this, GameStart.class);
		startActivity(mIntent);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		int w = getWindowManager().getDefaultDisplay().getWidth();
        int h = getWindowManager().getDefaultDisplay().getHeight();
        
        GameVariables.isRunning = 1;
        GameView gameView;
        gameView = new GameView (this, w, h);
        setContentView(gameView);
		
	}


	@Override
    protected void onPause() {
        super.onPause();
        GameVariables.pause = 1;
        GameView.gameLoopThread.setRunning(false);
        //finish();
        
        /*synchronized (GameView.gameLoopThread){
	        try {
				GameView.gameLoopThread.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }*/
        GameView.gameLoopThread.onPause();
        
       // GameView.redhit.recycle();
       
        
    }
	
	@Override
    protected void onResume(){
    	super.onResume();
    	GameVariables.pause = 0;
    	GameVariables.isRunning = 1;
    	//GameView.gameLoopThread.setRunning(true);
    	
    	//synchronized (GameView.gameLoopThread) {    
          //  GameView.gameLoopThread.notify();
        //}
    	
    	GameView.gameLoopThread.setRunning(true);
    	
    	GameView.gameLoopThread.onResume();
    	
    }
	
	/*@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.gc();
		finish();
		System.gc();
	}*/


	@Override
    protected void onStop() {
        super.onStop();
        GameView.gameLoopThread.setRunning(false);
       // GameVariables.pause = 1;
        /*GameView.redhit.recycle();
        GameView.redhit = null;
        
        GameView.heartempty.recycle();
        GameView.heartempty = null;
        
        GameView.heartfull.recycle();
        GameView.heartfull = null;
        
        GameView.warninggrey.recycle();
        GameView.warninggrey = null;
        
        GameView.warningred.recycle();
        GameView.warningred = null;
        */
       // System.gc();
      // finish();     
       System.gc();
    }
	
	@Override
    protected void onRestart(){
    	super.onRestart();
    	GameView.gameLoopThread.setRunning(true);
    	
    	GameVariables.pause = 0;
    	GameVariables.isRunning = 1;
    	
    	//GameView.gameLoopThread.setRunning(true);
    	
     }
	
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		menu.clear();
		if (GameVariables.where == 0) {
			
			//if (paused == false) {
				menu.add(0, 0, 0, "PAUSE");
			//}
			//else { 
			//	menu.add(0, 0, 0, "RESUME");
			//}
			menu.add(0, 1, 1, "MAIN MENU");
		}
		else if (GameVariables.where == 1) {
			menu.add(0, 0, 0, "MAIN MENU");
		}
		return super.onPrepareOptionsMenu(menu);
	}
	

	@Override //menu inflater
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (GameVariables.where == 0) { //if we're currently in playing mode, NOT training mode
			switch (item.getItemId()) {
			case 0:
				//if (paused == false) {
					Toast.makeText(GameStart.this, "GAME PAUSED - TAP THE SCREEN TO RESUME", Toast.LENGTH_LONG).show();
					//GameView.gameLoopThread.setRunning(false);
					GameVariables.pause = 1;
					//GameVariables.soundStarted = 0;
					GameVariables.soundPlayed = 0;
					GameVariables.soundStarted = 0;
					paused = true;
				//}
				//else {
					/*if (paused == true){
						GameVariables.pause = 0;
						Toast.makeText(MainActivity.this, "RESUMING SUCCESSFUL", Toast.LENGTH_LONG).show();
					}
					else if (paused == false) {
						Toast.makeText(MainActivity.this, "RESUMING FAILED", Toast.LENGTH_LONG).show();
					}
					
					GameView.gameLoopThread.setRunning(true);
					//GameView.gameLoopThread.resume();
					paused = false;	
					*/
				//}
				break;
			case 1:
				//Toast.makeText(GameStart.this, "button2 Selected",Toast.LENGTH_LONG).show();
				GameView.gameLoopThread.setRunning(false);
				GameVariables.soundPlayed = 0;
				System.gc();
				GameVariables.s = 1; //yes, need to restart the app
				finish();
				Intent returnMain = new Intent (GameStart.this, MainActivity.class);
				returnMain.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity (returnMain);
				
				GameVariables.ResetAfterResults();
				System.gc();
				break;
			}
		}
		
		else if (GameVariables.where == 1) { //if we're currently in training mode
			GameView.gameLoopThread.setRunning(false);
			GameVariables.soundPlayed = 0;
			GameVariables.s = 1; //yes, need to restart the app
			finish();
			Intent returnMain = new Intent (GameStart.this, MainActivity.class);
			startActivity (returnMain);
			GameVariables.ResetAfterResults();
		}
		
		
		return true;
	}
	
	//over ride the back button to do nothing if pressed
	//prevents users from exiting by accident
	@Override
	public void onBackPressed() {
		
		//GameStart.finishActivity(0);
	}



}
