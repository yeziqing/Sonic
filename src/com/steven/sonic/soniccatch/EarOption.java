package com.steven.sonic.soniccatch;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class EarOption extends Activity {

	TextView tvCurrentSetting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		SharedPreferences settings = getSharedPreferences("OptionsFile", 0);
		final SharedPreferences.Editor editor = settings.edit();

		if (settings.contains("Ear") == false) { // if preference don't
														// exist yet, default to 0 (test both ears)
			GameVariables.earMethod = 0;
		}
		else if (settings.contains("Ear") == true) { //load the saved preference value into earMethod
			GameVariables.earMethod = settings.getInt("Ear", 0); //0 == Value to return if this preference does not exist.
		}

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.activity_options_ear);
		
		tvCurrentSetting = (TextView) findViewById(R.id.currentSetting);
		Button bBoth = (Button) findViewById(R.id.both);
		Button bLeft = (Button) findViewById(R.id.left);
		Button bRight = (Button) findViewById(R.id.right);
		
		getCurrentSetting();
	
		bBoth.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				GameVariables.earMethod = 0;
				editor.putInt("Ear", 0);
				editor.commit();
				getCurrentSetting();
			}
		});
		
		bLeft.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				GameVariables.earMethod = 1;
				editor.putInt("Ear", 1);
				editor.commit();
				getCurrentSetting();
			}
		});
		
		bRight.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				GameVariables.earMethod = 2;
				editor.putInt("Ear", 2);
				editor.commit();
				getCurrentSetting();
			}
		});
		
	}

	private void getCurrentSetting() {
		// TODO Auto-generated method stub
		if (GameVariables.earMethod == 0) {
			tvCurrentSetting.setText("Current Setting: BOTH");
		}
		else if (GameVariables.earMethod == 1) {
			tvCurrentSetting.setText("Current Setting: LEFT");
		}
		else if (GameVariables.earMethod == 2) {
			tvCurrentSetting.setText("Current Setting: RIGHT");
		}
	}

}
