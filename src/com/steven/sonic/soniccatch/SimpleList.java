package com.steven.sonic.soniccatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SimpleList extends ListActivity {

	int i = 0;
	int dbElements = 0;
	String[] tempArr = new String[100];//WARNING: This limits db label's size to 100 elements
	@TargetApi(11)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {// Determines the array size
			FileInputStream fIn1 = openFileInput("ScoreDatabase.txt");
			
			Scanner temp = new Scanner(fIn1);
			temp.useDelimiter(";");
			while (temp.hasNext()){
				temp.next();
				dbElements++;
			}
			temp.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String arr[] = new String[dbElements];
		
		try {//Write file's data into an array
			FileInputStream fIn2 = openFileInput("ScoreDatabase.txt");
			Scanner s = new Scanner(fIn2); // Scanner that scans the input file
			s.useDelimiter(";");
			while (s.hasNext()) {
				String word = s.next();
				arr[i] = word;		        
				i++;
			}	
			s.close();
		}	catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		setListAdapter(new ArrayAdapter <String>(this, android.R.layout.simple_list_item_1, arr)); //create array adapter of type String
		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	            getActionBar().setDisplayHomeAsUpEnabled(true);
	        }
		tempArr = arr;
		
		String testString = Integer.toString(dbElements);
		Toast.makeText(getApplicationContext(), testString + " scores found!", Toast.LENGTH_SHORT).show();
	}

	@Override
	//When an item is selected, the position number is corresponded to the database's array index. The data is used for some other stuff in LoadScores.java
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		GameVariables.selectedSuffix = tempArr[position];
		Intent myIntent = new Intent (SimpleList.this, LoadScores.class);
		startActivity(myIntent);
	}
	
	@Override
	// This is called when the Home (Up) button is pressed in the Action Bar.
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // Home button id
			Intent parentActivityIntent = new Intent(this, MainActivity.class); //go back to main menu
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
