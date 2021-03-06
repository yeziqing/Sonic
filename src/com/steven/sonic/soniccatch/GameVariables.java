package com.steven.sonic.soniccatch;

import android.app.Activity;
import android.os.Vibrator;

public class GameVariables extends Activity{

	//data arrays that are passed into resultsGraph.java
	public static float[] xRaw = new float[100];
	public static float[] yRaw = new float[100];
	public static float[] earRaw = new float[100];
	
	//graph labels
	//static String[] verlabels = new String[] { "100dB", "90dB", "80dB", "70dB","60dB", "50dB", "40dB", "30dB", "20dB", "10dB", "0dB" };
	static String[] verlabels = new String[] { "0dB", "10dB", "20dB", "30dB","40dB", "50dB", "60dB", "70dB", "80dB", "90dB", "100dB" };
	static String[] horlabels = new String[] { "0", "1k", "2k", "3k", "4k", "5k","6k", "7k", "8k", "9k", "10k", "11k", "12k", "13k", "14k","15k", "16k" };
	
	
	//interface and navigation stuff
	public static int s = 0; // determine if in need to restarting the app
	public static boolean showTips = true;
	public static boolean controlMethod = true; // default is tilt
	public static int earMethod = 0; // default is 0, which is test for both ears. 1 means test for left, 2 means test for right, 3 means RANDOM
	public static int earTesting = 0;
	public static boolean writeEnable = false;
	public static String selectedSuffix;
	
	//in-game stuff
	public static Vibrator shock;
	public static int isRunning = 0;
	public static int isSummoned = 0;
	public static int soundPlayed = 0;
	public static float[] enemyLocation = new float[2]; 
	public static int duration = 1;
	public static int randFreq = 0;
	public static int[] Freqs = {500,1000,3000,5000,8000,10000,14000};
	public static double frequency;
	public static int shotfrom = 0;
	public static long soundStarted = 0;
	public static long enemyAppear = 0;
	public static long lastPlayed = 0; 
	public static long glassStart = 0; 
	public static int volume = 0;
	public static int lives = 3;
	public static int hit = 0;
	public static double[][] score = new double[1000][3];
	public static int currentscore = 0;
	public static double[] miss = new double[4];
	public static int currentmiss = 0;
	public static int level = 1;
	public static int[] divide = new int[8];
	public static int gamecolor = 0;
	public static int speedModifier = 2;
	public static int storySpeed = 0;
	public static int playBack = 1;
	public static int playBackLevel = 1;
    public static int scoretime = 0;
    public static int levelchange = 0;
    public static int pressAt = 20;
    public static int tutorial = 0;
    public static int pause = 0;
    public static int help = 1;
    public static int storytime = 0;
    public static int story = 0;
    public static int where = 0; //0:=actually playing, 1:=doing tutorial
    public static int touchY;

	
    
	public static void ResetAfterResults () {

		isRunning = 0;
		isSummoned = 0;
		soundPlayed = 0;
		enemyLocation = new float[2];
		duration = 1;
		randFreq = 0;

		shotfrom = 0;
		soundStarted = 0;
		enemyAppear = 0;
		lastPlayed = 0;
		glassStart = 0; 
		volume = 0;
		lives = 3;
		hit = 0;
		score = new double[1000][3];
		currentscore = 0;
		miss = new double[4];
		currentmiss = 0;
		level = 2;
		divide = new int[8];
		gamecolor = 0;
	}
	
	

}
