package com.steven.sonic.soniccatch;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

//This contains all options for the game, including:
//Control Option: Touch | Tilt
//Ear Option: 0 | 1 | 2 | 3
//Navigation to Calibration Page

public class GameSettings extends ListActivity {

	static final String[] settingsList = new String[] { "Control", "Ear", "Calibration"};
	static SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		SharedPreferences settings = getSharedPreferences("OptionsFile", 0);
		editor = settings.edit();
        
        GameVariables.controlMethod = settings.getBoolean("Control", true);
        GameVariables.earMethod = settings.getInt("Ear", 0); //0 (both) == Value to return if this preference does not exist.
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_gamesettings, settingsList));
		 
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		setTitle("Game Options");
 
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			    //Toast.makeText(getApplicationContext(),((TextView) view).getText() + " " + String.valueOf(position), Toast.LENGTH_SHORT).show();
			    if (position == 0) controlDialog();
			    else if (position == 1) earDialog();
			    else if (position == 2) calibrationPage();
			 
			}
		});
		
		
		
	}
	
	public void controlDialog() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
	    //final EditText input = new EditText(this);
	    alert.setTitle("Control Options");
	    alert.setMessage("Choose how you would like to control:");

	    alert.setPositiveButton("Touch", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {	
	        	GameVariables.controlMethod = false;
		    	editor.putBoolean("Control", false);
		        editor.commit();
		        Toast.makeText(getApplicationContext(),"GameVariables.controlMethod = false", Toast.LENGTH_SHORT).show();
	        }
	    });
	    
		alert.setNegativeButton("Tilt", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				GameVariables.controlMethod = true;
		    	editor.putBoolean("Control", true);
		        editor.commit();
		        Toast.makeText(getApplicationContext(),"GameVariables.controlMethod = true", Toast.LENGTH_SHORT).show();
			}
		});
	    alert.show();
	}
	
	public void earDialog() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
	    //final EditText input = new EditText(this);
	    alert.setTitle("Ear Options");
	    alert.setMessage("Choose which ear(s) you would like to test: " );

	    
	    alert.setPositiveButton("Both", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {	
	        	GameVariables.earMethod = 0;
				editor.putInt("Ear", 0);
				editor.commit();
	        }
	    });
	    
	    
		alert.setNegativeButton("Left", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				GameVariables.earMethod = 1;
				editor.putInt("Ear", 1);
				editor.commit();
			}
		});
		
		alert.setNeutralButton("Right", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				GameVariables.earMethod = 2;
				editor.putInt("Ear", 2);
				editor.commit();
			}
		});
		
		
	    alert.show();
	}
	
	public void calibrationPage() {	
		Intent gotoCalibrationPage = new Intent(GameSettings.this, Settings.class);
		startActivity(gotoCalibrationPage);	
	}
	
	@Override
	public void onBackPressed() {
		Intent intentStart = new Intent (GameSettings.this, MainActivity.class);
		intentStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intentStart);
		finish();
	}
	
	@Override
	// This is called when the Home (Up) button is pressed in the Action Bar.
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: //action bar home button id	
			Intent parentActivityIntent = new Intent(this, MainActivity.class);
			parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(parentActivityIntent);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	

	

}


