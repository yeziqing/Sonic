package com.steven.sonic.soniccatch;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.Math;
import java.text.DateFormat;
import java.util.Date;

public class resultsGraph extends Activity {

	boolean currentSessionScoresSaved = false;
	
	@TargetApi(11)	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		// Remove title bar
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Remove notification bar
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		

		//Intent intent_resltsGraph = getIntent(); // gets the intent

		//String message = intent.getStringExtra(GameView.EXTRA_MESSAGE);
		
		//Disabled sorting because not using graph fill anymore, as well as to make it work with earValues
		//BubbleSort(GameVariables.xRaw, GameVariables.yRaw); //sort the 2 arrays
		
		//ConvertToLog(GameVariables.yRaw); //convert to log10
		
		//debug code: dummy data for testing different colors for different ear preferences
		/*GameVariables.earRaw[0] = 0;
		GameVariables.earRaw[1] = 1;
		GameVariables.earRaw[2] = 2;
		GameVariables.earRaw[3] = 0;
		GameVariables.earRaw[4] = 1;
		GameVariables.earRaw[5] = 2;*/
		

		GraphView newGraph = new GraphView(this, GameVariables.xRaw, GameVariables.yRaw, GameVariables.earRaw, "This is an Audiogram", GameVariables.horlabels, GameVariables.verlabels, GraphView.LINE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //Set fullscreen mode, removes the notification bar.
		
		setContentView(newGraph);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		Toast.makeText(getApplicationContext(), "Touch the screen to save your score...", Toast.LENGTH_LONG).show();
	       	
		//FOR DEBUGGING: Check if text file exists
		/*File file = getBaseContext().getFileStreamPath("ScoreDatabase.txt");
		if(file.exists()){
			Toast.makeText(getApplicationContext(), "ScoreDatabase.txt Exists", Toast.LENGTH_SHORT).show();
		}
		*/
		
		/*if (GameVariables.writeEnable == true) { //Write to file only if write is enabled. This will overwrite any existing files of the same name.
			SaveData(Concatenate(GameVariables.xRaw), true); //write x data to file
			SaveData(Concatenate(GameVariables.yRaw), false);//write y data to file
		}
		*/
		
		
	}

	public String Concatenate(float[] arr) {
		int i;
		StringBuilder mySB = new StringBuilder(100);
		for (i = 0; i < arr.length-1; i++) {
			
			if (arr[i] > 0) { // ONLY APPEND IF VALUE > 0
			mySB.append((arr[i]) + " ");
			}
		}
		
		return mySB.toString();
		
	}
	
	public void SaveScoreSuffix(String str, String fileName){
		try{
			FileOutputStream ffOut = openFileOutput(fileName, MODE_APPEND); //Write data to end of existing file
			OutputStreamWriter fows = new OutputStreamWriter(ffOut);
			fows.write(str + ";"); //USE SEMI COLON , AS DELIMITER
			fows.flush();
			fows.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SaveData(String str, int axis, String fileName) {

		try {
			if (axis == 0){ //if true, write x axis data to x axis file
				FileOutputStream fOut = openFileOutput(fileName, MODE_WORLD_READABLE);
				OutputStreamWriter osw = new OutputStreamWriter(fOut);
				osw.write(str);		
				osw.flush();
				osw.close();
			}
			else if (axis == 1) { //else, write y axis data to y axis file
				FileOutputStream fOut2 = openFileOutput(fileName, MODE_WORLD_READABLE);
				OutputStreamWriter osw2 = new OutputStreamWriter(fOut2);
				osw2.write(str);		
				osw2.flush();
				osw2.close();
			}
			
			else if (axis == 2) { //else, write ear data to ear file
				FileOutputStream fOut3 = openFileOutput(fileName, MODE_WORLD_READABLE);
				OutputStreamWriter osw3 = new OutputStreamWriter(fOut3);
				osw3.write(str);		
				osw3.flush();
				osw3.close();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
													
		
	}

	/*public boolean onTouchEvent(MotionEvent e2) {
		//Context context = getBaseContext(); 

		int derp = e2.getAction();

		if (derp == MotionEvent.ACTION_DOWN) {
			// return to main activity
			// reset some game variables
			GameVariables.ResetAfterResults();
			GameVariables.isRunning = 1; //set game to be is running
			Intent intent_mainmenu = new Intent(this, MainActivity.class);
			this.startActivity(intent_mainmenu);
		}
		return false;
	}*/
	
	
	public boolean onTouchEvent(MotionEvent e2) {
		//Context context = getBaseContext(); 

		int derp = e2.getAction();

		if (derp == MotionEvent.ACTION_DOWN && currentSessionScoresSaved == false) {
			
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);  
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
		    final EditText input = new EditText(this);
		    alert.setTitle("Save Your Scores");
		    alert.setMessage("Enter your name...");
		    alert.setView(input);
		    alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            String userName = input.getText().toString().trim();
		            String currentDateTimeString = "("+DateFormat.getDateTimeInstance().format(new Date())+")";
		           // Toast.makeText(getApplicationContext(), userName + " " + currentDateTimeString, Toast.LENGTH_LONG).show();
		            Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
		            String scoreSuffix = userName + " " + currentDateTimeString;
		            
		            SaveScoreSuffix(userName + " " + currentDateTimeString, "ScoreDatabase.txt"); //append to the list of score names
		            SaveData(Concatenate(GameVariables.xRaw), 0, "x"+scoreSuffix); //write x data to file
					SaveData(Concatenate(GameVariables.yRaw), 1, "y"+scoreSuffix);//write y data to file
					SaveData(Concatenate(GameVariables.earRaw), 2, "ear"+scoreSuffix);//write ear data to file
		        }
		    });
		    alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int whichButton) {
		                    dialog.cancel();
		                }
		            });
		    alert.show();
		    currentSessionScoresSaved = true; //indicate current session's score has been saved 
		}
		else if (derp == MotionEvent.ACTION_DOWN && currentSessionScoresSaved == true)
			Toast.makeText(getApplicationContext(), "Scores have already been saved...", Toast.LENGTH_LONG).show();
		return false;
	}
	
	//Sorts the xArray, which is frequencies
	//Also sorts the yArray, which is the dBs, based on how frequencies are sorted
	public static void BubbleSort(float[] xArray, float[] yArray) {
		int j;
		boolean flag = true; // set flag to true to begin first pass
		float temp, temp2; // holding variable

		while (flag) {
			flag = false; // set flag to false awaiting a possible swap
			for (j = 0; j < xArray.length - 1; j++) {
				if (xArray[j] < xArray[j + 1]) // change to > for ascending sort
				{
					temp = xArray[j]; // swap elements
					temp2 = yArray[j];
			
					
					xArray[j] = xArray[j + 1];
					yArray[j] = yArray[j + 1];
					
					
					xArray[j + 1] = temp;
					yArray[j + 1] = temp2;

					flag = true; // shows a swap occurred
				}
			}
		}
	} 
	
	public static void ConvertToLog(float[] arr) {
		int i;
		for (i = 0; i < arr.length-1; i++) {
			
			arr[i] = (float) (20* Math.log10(arr[i]));
		}
	}
	
	@Override
	//Pressing back button goes to score database
	public void onBackPressed() {
		Intent intent_SimpleList = new Intent (this, SimpleList.class);
		startActivity(intent_SimpleList);
		finish();
		GameVariables.ResetAfterResults(); //reset game lives and other data
		//reset the scores: x, y, and ear
		GameVariables.xRaw = new float[100]; 
		GameVariables.yRaw = new float[100];
		GameVariables.earRaw = new float [100];
		GameVariables.isRunning = 1; // set game to be is running
	}
	
	// This is called when the Home (Up) button is pressed in the Action Bar.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			
			Intent parentActivityIntent = new Intent(this, SimpleList.class);
			parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(parentActivityIntent);
			finish();
			GameVariables.ResetAfterResults(); //reset game lives and other data
			GameVariables.xRaw = new float[100]; //reset the scores
			GameVariables.yRaw = new float[100];
			GameVariables.earRaw = new float [100];
			GameVariables.isRunning = 1; // set game to be is running
			GameVariables.s = 1; //yes, need to restart app
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	    

}
