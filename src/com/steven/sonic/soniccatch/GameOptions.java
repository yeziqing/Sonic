package com.steven.sonic.soniccatch;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameOptions extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        SharedPreferences settings = getSharedPreferences("OptionsFile", 0);
        final SharedPreferences.Editor editor = settings.edit();
        
        if (settings.contains("Control") == false) { //if preference don't exist yet, default to true (move by acc)
        	GameVariables.controlMethod = true;
        }
        
        else if (settings.contains("Control") == true){ //if preference does exist, load it up
        	GameVariables.controlMethod = settings.getBoolean("Control", true);
        }
        
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_options);
        
        Typeface font1 = Typeface.createFromAsset(getAssets(), "Backto1982.ttf");  
        
        //Button bControlOption = (Button) findViewById(R.id.controlOption);
        TextView tvControlOption = (TextView) findViewById(R.id.controlOption);
        final TextView tvSelector1 = (TextView) findViewById(R.id.selector1);
        final TextView tvSelector2 = (TextView) findViewById(R.id.selector2);
        
        final Button bMoveByAcc = (Button) findViewById(R.id.moveByAcc);
        final Button bMoveByTouch = (Button) findViewById(R.id.moveByTouch);
        Button bBack = (Button) findViewById(R.id.backFromOptions);
        
       // bControlOption.setTypeface(font1);
        tvControlOption.setTypeface(font1);
        tvSelector1.setTypeface(font1);
        tvSelector2.setTypeface(font1);
        
        if (GameVariables.controlMethod == true) {
        	tvSelector2.setVisibility(View.GONE);
        	tvSelector1.setShadowLayer(10, 3, 3, Color.RED);
        	bMoveByAcc.setShadowLayer(10,3,3,Color.RED);
        	bMoveByTouch.setShadowLayer(10, 3, 3, Color.BLUE); //#0040FF - blue
        }
        else if (GameVariables.controlMethod == false) {
        	tvSelector1.setVisibility(View.GONE);
        	tvSelector2.setShadowLayer(10, 3, 3, Color.RED);
        	bMoveByTouch.setShadowLayer(10,3,3,Color.RED);
        	bMoveByAcc.setShadowLayer(10, 3, 3, Color.BLUE); //#0040FF - blue
        }
        
        bMoveByAcc.setTypeface(font1);
        bMoveByTouch.setTypeface(font1);
        bBack.setTypeface(font1);
        
        bMoveByAcc.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				GameVariables.controlMethod = true;
				tvSelector2.setVisibility(View.GONE);
				tvSelector1.setVisibility(View.VISIBLE);
		    	tvSelector1.setShadowLayer(10, 3, 3, Color.RED);
		    	bMoveByAcc.setShadowLayer(10,3,3,Color.RED);
		    	bMoveByTouch.setShadowLayer(10, 3, 3, Color.BLUE);
		    	editor.putBoolean("Control", true);
		        editor.commit();
			}
		});
        
        bMoveByTouch.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				GameVariables.controlMethod = false;
				tvSelector1.setVisibility(View.GONE);
				tvSelector2.setVisibility(View.VISIBLE);
		    	tvSelector2.setShadowLayer(10, 3, 3, Color.RED);
		    	bMoveByTouch.setShadowLayer(10,3,3,Color.RED);
		    	bMoveByAcc.setShadowLayer(10, 3, 3, Color.BLUE);
		    	editor.putBoolean("Control", false);
		        editor.commit();
			}
		});

        bBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});
	}

}
