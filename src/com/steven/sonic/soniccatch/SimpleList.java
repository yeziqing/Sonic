package com.steven.sonic.soniccatch;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewDebug.FlagToString;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SimpleList extends ListActivity {

	int i = 0;
	int dbElements = 0;
	boolean dataSetChanged = false;
	//String[] tempArr = new String[100];//WARNING: This limits db label's size to 100 elements
	String selectedFromList;
	ArrayAdapter <String> adapter;
	String[] arr = new String[0];
	ListView lv;
	
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
		
		//String arr[] = new String[dbElements];
		
		arr = new String[dbElements];
		
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
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //Set fullscreen mode, removes the notification bar.
		//lv = (ListView) findViewById(R.id.MyListView);
		//lv.setAdapter(new ArrayAdapter <String>(this, android.R.layout.simple_list_item_1, arr)); //create array adapter of type String
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
		setListAdapter(adapter);

		lv = getListView();
		//lv.setBackgroundColor(15790320);
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View arg1, int pos, long id) {
            	selectedFromList = arr[pos];
            	registerForContextMenu(adapterView);
                return false;
            }
        }); 
		
		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	            getActionBar().setDisplayHomeAsUpEnabled(true);
	        }
		//tempArr = arr;
		
		 if (dbElements == 0) Toast.makeText(getApplicationContext(), "No saved scores were found :(", Toast.LENGTH_SHORT).show();
		 else Toast.makeText(getApplicationContext(), "Press and hold an item for more options...", Toast.LENGTH_LONG).show();
		//String testString = Integer.toString(dbElements); //for debugging
		//Toast.makeText(getApplicationContext(), testString + " saved scores found!", Toast.LENGTH_SHORT).show(); //for debugging
	}
	
	public void deleteFromList() {
		dbElements--;
		arr = new String[dbElements];
		int d = 0;
		try {//Write file's data into an array, EXCLUDING the item selected for deletion
			FileInputStream fIn = openFileInput("ScoreDatabase.txt");
			Scanner s = new Scanner(fIn); // Scanner that scans the input file
			s.useDelimiter(";");
			
			while (s.hasNext()) {
				String word = s.next();

				int x = word.compareTo(selectedFromList);
				if (x != 0) {
					arr[d] = word;
					d++;
				}
				else if (x == 0) {
					Toast.makeText(this, "FOUND AND SKIPPED",Toast.LENGTH_SHORT).show();
				}
				//Toast.makeText(getApplicationContext(), "|"+selectedFromList+"|", Toast.LENGTH_SHORT).show();
				//Toast.makeText(getApplicationContext(), "|"+arr[d]+"|", Toast.LENGTH_SHORT).show();
			}
			
			reloadListIntoDatabase(Concatenate(arr,dbElements), "ScoreDatabase.txt");
			s.close();
		}	catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
	}
	
	
	public String Concatenate(String[] arr, int size) {
		int i;
		StringBuilder mySB = new StringBuilder(100);
		for (i = 0; i < size; i++) {
			mySB.append((arr[i]) + ";");
		}
		return mySB.toString();
		
	}
	
	
	public void reloadListIntoDatabase(String str, String fileName){
		try{
			FileOutputStream ffOut = openFileOutput(fileName, MODE_PRIVATE); //Write data to end of existing file
			OutputStreamWriter fows = new OutputStreamWriter(ffOut);
			fows.write(str);
			fows.flush();
			fows.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	//When an item is (short) clicked, the position number is corresponded to the database's array index. The data is used for some other stuff in LoadScores.java
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		GameVariables.selectedSuffix = arr[position];
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
			GameVariables.s = 1; // yes, need to restart
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	
	
	@Override
	//Create context menu 
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle(selectedFromList);
		menu.add(0, v.getId(), 0, "Share");
		menu.add(0, v.getId(), 0, "Delete");
		menu.add(0, v.getId(), 0, "Fire Ze Missle!");
	}
	
	@Override
	//Setup what each item in context menu does
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle() == "Share") {
			Toast.makeText(getApplicationContext(), "Sharing...", Toast.LENGTH_SHORT).show();
		} else if (item.getTitle() == "Delete") {
			createDialog(); //Deletion confirmation dialog
		} else if (item.getTitle() == "Fire Ze Missle!") {
			Toast.makeText(getApplicationContext(), "Firing Missle...", Toast.LENGTH_SHORT).show();
		} else {
			return false;
		}
		return true;
	}
	
	//Deletion confirmation dialog
	public void createDialog() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
	    //final EditText input = new EditText(this);
	    alert.setTitle(selectedFromList);
	    alert.setMessage("Are you sure?");
	    //alert.setView(input);
	    alert.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {	
	        	//Toast.makeText(getApplicationContext(), "Deleting...", Toast.LENGTH_SHORT).show();
	        	deleteFromList();
	        	deleteFiles("x"+selectedFromList);
	        	deleteFiles("y"+selectedFromList);
	        	reloadActivity();
	        	//adapter.getAdapter().notifyDataSetChanged();
	        	//adapter.notifyDataSetInvalidated();
	        }
	    });
	    alert.setNegativeButton("No Wait!",
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                    dialog.cancel();
	                }
	            });
	    alert.show();
	}
	
	//reload the activity itself. Used to refresh the list after a change occurs.
	protected void reloadActivity() {
		Intent intentReloadThisActivity = new Intent(SimpleList.this, SimpleList.class);
		intentReloadThisActivity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intentReloadThisActivity);
		finish();
	}

	protected void deleteFiles(String FILENAME) {
		// TODO Auto-generated method stub
		File myFile = getBaseContext().getFileStreamPath(FILENAME);
		
		/*if(myFile.exists()){ //for bugging if file exits or was deleted
			Toast.makeText(getApplicationContext(), "File to be deleted is found", Toast.LENGTH_SHORT).show();
		}*/
		myFile.delete();	
		/*if(!myFile.exists()){ //for bugging if file exits or was deleted
			Toast.makeText(getApplicationContext(), "File has been deleted!!", Toast.LENGTH_SHORT).show();
		}*/
	}

	//Pressing back button goes to main menu
	@Override
	public void onBackPressed() {
		Intent intent_MainActivity = new Intent (this, MainActivity.class);
		intent_MainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent_MainActivity);
		finish();
	}
	
	
	
}
