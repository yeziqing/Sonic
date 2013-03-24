package com.steven.sonic.soniccatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class testLoadData extends Activity {
	public String TESTSTRING = "1989 98203.12 2131.39 123.12 23232.01 0970.98";
	public StringBuilder mySB = new StringBuilder(100);
	public String[] yRaw = new String[100];
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		
		//Button bSave = (Button) findViewById(R.id.button1);
		//Button bLoad = (Button) findViewById(R.id.button2);
		

		
	}

	public void fSave(View view) {
		EditText et1 = (EditText) findViewById(R.id.editText1);
		//TESTSTRING = et1.getText().toString();
		
		try {
			FileOutputStream fOut = openFileOutput("samplefile.txt", MODE_WORLD_READABLE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);

			osw.write(TESTSTRING);		
			osw.flush();
			osw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void fLoad(View view) {
		EditText et2 = (EditText) findViewById(R.id.editText2);
		/*
		
		try {
			FileInputStream fIn = openFileInput("samplefile.txt");
	        InputStreamReader isr = new InputStreamReader(fIn);
	        //Prepare a char-Array that will
	        // hold the chars we read back in.
	        char[] inputBuffer = new char[TESTSTRING.length()];
	        // Fill the Buffer with data from the file
	        isr.read(inputBuffer);
	        // Transform the chars to a String
	        String readString = new String(inputBuffer);
	        
			et2.setText(readString);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		//et2.setText(GameVariables.yRaw[1].toString());


	}
	
	public void fParse(View view){
		
		try {
		EditText et3 = (EditText) findViewById(R.id.editText3);
		FileInputStream fIn = openFileInput("ScoreDatabase.txt");
		Scanner s = new Scanner(fIn);
		int i = 0;
		
		    while (s.hasNext()) {
		        String word = s.next();
		        mySB.append(word);
		        yRaw[i] = word;
		        
		        //if (yRaw[i]<= 0){
		        	//yRaw[i] = 0;
				//}
		        et3.append(yRaw[i] + " ");
		        i++;
		        
		    }
		    //outputArray(yRaw);
		    s.close();
		    //et3.setText(mySB);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public void outputArray(float[] arr ){
		int i = 0;
		for (i = 0; i < arr.length-1; i++) {
			//arr[i] = (float) (20* Math.log10(arr[i]));
			
		}
	}
	

}
