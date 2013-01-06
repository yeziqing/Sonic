package com.steven.sonic.soniccatch;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;

public class Scores extends TabActivity {
	private TabHost mTabHost;

    @TargetApi(11)
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
  
        setContentView(R.layout.activity_scores);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
       
        
        mTabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
      
        
		// Tab 1
		intent = new Intent(this, resultsGraph.class);
		spec = mTabHost.newTabSpec("Tab1").setIndicator("Audiogram")
				.setContent(intent);
		mTabHost.addTab(spec);
		
		// Tab 2
		intent = new Intent(this, LoadScores.class);
		spec = mTabHost.newTabSpec("Tab2").setIndicator("DataLoadTest")
				.setContent(intent);
		mTabHost.addTab(spec);
		
		// Tab 3
		intent = new Intent(this, resultsGraph.class);
		spec = mTabHost.newTabSpec("Tab3").setIndicator("Tab3Indicator")
				.setContent(intent);
		mTabHost.addTab(spec);
		
		/*if (GameVariables.werp == true) {
			mTabHost.setCurrentTab(1);
		}
		else { 
			mTabHost.setCurrentTab(0);
		}*/
		mTabHost.setCurrentTab(0);
    }
    
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed
                // in the Action Bar.
                Intent parentActivityIntent = new Intent(this, SimpleList.class);
                parentActivityIntent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(parentActivityIntent);
                finish();
                GameVariables.ResetAfterResults();
    			GameVariables.isRunning = 1; //set game to be is running
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    

}

