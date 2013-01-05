package com.steven.sonic.soniccatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;



import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class LoadScores extends Activity{

	float[] xRaw1 = new float[101];
	float[] yRaw1 = new float[101];
	//@TargetApi(11)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		//LoadData("score_tab2_y.txt");
		xRaw1 = LoadData("score_tab2_x.txt");
		yRaw1 = LoadData("score_tab2_y.txt");
		
		GraphView myGraph = new GraphView(this, xRaw1, yRaw1, "Loaded Data", GameVariables.horlabels, GameVariables.verlabels, GraphView.LINE);
		setContentView(myGraph);
	}
	
	
	public float[] LoadData(String FILENAME){
		
		float[] arr = new float[100];
		try {
			FileInputStream fIn = openFileInput(FILENAME);
			Scanner s = new Scanner(fIn); // Scanner that scans the input file
			int i = 0;
			// StringBuilder mySB = new StringBuilder(100);
			File file = getBaseContext().getFileStreamPath(FILENAME);
			if(file.exists()){
				Toast.makeText(getApplicationContext(), "msg msg", Toast.LENGTH_SHORT).show();
			}
			
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

}
