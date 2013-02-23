package com.steven.sonic.soniccatch;

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class TipsOverlay extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_tipsoverlay);
		
		
		
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e2) {
		//Context context = getBaseContext(); 

		int derp = e2.getAction();

		if (derp == MotionEvent.ACTION_DOWN) {
			
			//GameVariables.showTips = false;
			//Intent returnFromOverlay = new Intent(this, Settings.class);
			//returnFromOverlay.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			//startActivity(returnFromOverlay);
			finish();
		
		}
		
		return true;
	}
	

}
