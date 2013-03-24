package com.steven.sonic.soniccatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;



import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class LoadScores extends Activity{

	float[] xRaw1 = new float[101];
	float[] yRaw1 = new float[101];
	float[] earRaw = new float[101];
	
	@TargetApi(11)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		//LoadData("score_tab2_y.txt"); //debugging
		//xRaw1 = LoadData("score_tab2_x.txt");
		//yRaw1 = LoadData("score_tab2_y.txt");
		
		//Append the corresponding coordinate type to the front of the suffix name, in order to identify the corresponding file names to be loaded/read.
		xRaw1 = LoadData("x"+GameVariables.selectedSuffix);
		yRaw1 = LoadData("y"+GameVariables.selectedSuffix);
		earRaw = LoadData("ear"+GameVariables.selectedSuffix);
		
		setTitle("Saved Scores for "+GameVariables.selectedSuffix); //set the title on the action bar to be this
		GraphView myGraph = new GraphView(this, xRaw1, yRaw1, earRaw, "Audiogram", GameVariables.horlabels, GameVariables.verlabels, GraphView.LINE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //Set fullscreen mode, removes the notification bar.
		
		setContentView(myGraph);
		//GameView.recycleAllBitmaps();
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	//general method for writing data a file named FILENAME
	public float[] LoadData(String FILENAME){
		
		float[] arr = new float[100];
		try {
			FileInputStream fIn = openFileInput(FILENAME);
			Scanner s = new Scanner(fIn); // Scanner that scans the input file
			int i = 0;
			// StringBuilder mySB = new StringBuilder(100);
			/*File file = getBaseContext().getFileStreamPath(FILENAME);
			if(file.exists()){
				Toast.makeText(getApplicationContext(), "msg msg", Toast.LENGTH_SHORT).show();
			}*/
			
			while (s.hasNext()) {
				String word = s.next();
				arr[i] = Float.valueOf(word);
				//Toast.makeText(getApplicationContext(), word, Toast.LENGTH_SHORT).show();
				//arr[i] = Float.valueOf(word);
		        
		        //if (arr[i]<= 0f){
		        	//arr[i] = 0f;
				//}
		        
				i++;
			}
			
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return arr;
	}
	
	@Override
	// This is called when the Home (Up) button is pressed in the Action Bar.
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: //action bar home button id	
			Intent parentActivityIntent = new Intent(this, SimpleList.class);
			parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(parentActivityIntent);
			finish();
			GameVariables.ResetAfterResults();
			GameVariables.isRunning = 1; // set game to be is running
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
