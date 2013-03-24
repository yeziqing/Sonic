package com.steven.sonic.soniccatch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import java.util.Random;

//import Accelerometer
import com.steven.sonic.soniccatch.Accelerometer;

public class GameView extends SurfaceView implements SensorEventListener, SurfaceHolder.Callback {
	
	private Context mContext;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Display mDisplay;
	private WindowManager mWindowManager;
	private long mSensorTimeStamp;
	private long mCpuTimeStamp;
	private float[] mSensorX;
	private float[] mSensorY;
	private int reboot;

	
	private Paint p;
	public static Bitmap bg;
	public static Bitmap ship;
	public static Bitmap heartfull;
	public static Bitmap heartempty;
	public static Bitmap redhit;
	public static Bitmap glass;
	public static Bitmap crash;
	public static Bitmap lefthud;
	public static Bitmap righthud;
	public static Bitmap midhud;
	public static Bitmap warninggrey;
	public static Bitmap warningred;
	public static Bitmap thousand;
	public static Bitmap hundred;
	public static Bitmap ten;
	public static Bitmap story1;
	public static Bitmap story2;
	public static Bitmap story3;

	public static Bitmap tut;



	
	public int width, height;
	public int x, y; 
	public int ship_x = width/2;
	public int ship_y = height/2;
	private int totalSpeed;
	private int[] extraSpeed = {0,0};
	
	public float[] shot_x = {0,0};
	public float[] shot_y = {0,0};
	public float[] shot_size_place = {0,0};
	public int shot_fired;
	public int shooting;
	public int boosting = 0;
	private Paint shot = new Paint();
	private Path shot_path = new Path();
	
	private Paint storyPaint = new Paint();
	private int storyAlpha = 255;
	private Paint storyFade = new Paint();
	
	private SurfaceHolder holder;
	public static GameLoopThread gameLoopThread;
	private int xSpeed = 0;
	
	private Paint helperPaint = new Paint();
	private Path helperLine = new Path();
	
	private static int centerx;
	private static int centery;
	private int lastcenterx = 0;
	private int lastcentery = 0;
	private int lastshipx = 0;
	private int lastshipy = 0;
	private int gettingHit = 0;
	private int opa = 0;
	private int count = 0;
	private int glassopa = 0;
	private int heartopa = 0;
	private int spawning = 0;
	private Paint middle = new Paint();
	private Paint edges = new Paint();
	private Path lines = new Path();
	private Paint scorePaint = new Paint(); 
	private Paint heartpaint = new Paint();
	private int backgroundColour = 0x000000;
	private int storycount = 0;

	
	private Random r = new Random();
	private float[] botSquareStart = new float[26];
	private float[] botSquareEnd = new float[26];
	private float[] botScreenStart = new float[26];
	private float[] botScreenEnd = new float[26];
	private float[] botPercent = {5,15,25,27,30,35,40,45,50,55,57,60,67,70,73,80,90,93,95,100,103,105,115,123,125,135,145,150,152,155,160,162,165,169,175,180,185,187,195,199,10,40,50,90,80,120,140,180,140,180};
	private Integer[] botTiming = {100,50,60,45,70,80,95,65,105,15,45,75,35,40,55,40,35,50,65,75,135,320,60,215,230};
	private Integer[] botSize = {50,45,30,65,70,100,55,55,45,50,25,35,30,35,40,30,150,60,15,40,5,10,7,3,8,2} ;
	private Integer[] botCurrentTiming = {55,20,55,0,55,0,10,15,0,25,15,65,10,0,0,55,10,5,75,0,40,40,15,60,30,15};
	private Integer[] botCurrentSize = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private Integer[] botCurrentPlace = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	private float[] topSquareStart = new float[26];
	private float[] topSquareEnd = new float[26];
	private float[] topScreenStart = new float[26];
	private float[] topScreenEnd = new float[26];
	private float[] topPercent = {3,5,9,10,13,16,21,26,28,33,35,39,45,50,60,65,70,78,84,90,95,105,114,119,126,131,139,140,150,156,161,162,168,175,179,185,187,189,194,199,10,40,50,90,80,120,140,180,140,180};
	private Integer[] topTiming = {100,50,60,45,70,80,95,65,105,15,45,75,35,40,55,40,35,50,65,75,145,50,420,240,300};
	private Integer[] topSize = {60,45,36,67,71,10,64,19,45,60,75,35,40,25,46,70,200,160,95,46,5,7,10,4,8,7} ;
	private Integer[] topCurrentTiming = {75,10,55,0,35,10,10,15,30,55,10,75,20,6,0,56,19,15,45,15,30,50,70,10,30,0};
	private Integer[] topCurrentSize = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private Integer[] topCurrentPlace = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	private float[] leftSquareStart = new float[26];
	private float[] leftSquareEnd = new float[26];
	private float[] leftScreenStart = new float[26];
	private float[] leftScreenEnd = new float[26];
	private float[] leftPercent = {1,2,4,7,13,17,21,27,37,40,43,48,55,60,70,74,82,90,95,101,102,103,105,108,115,121,127,134,140,145,156,160,161,163,167,178,180,190,195,199,10,40,50,90,80,120,140,180,120,160};
	private Integer[] leftTiming = {55,60,15,20,0,80,0,15,70,35,63,10,23,75,82,29,48,38,94,10,145,460,200,75,180};
	private Integer[] leftSize = {10,9,58,70,37,90,10,80,74,150,80,27,69,73,82,57,35,37,84,73,7,8,5,3,6} ;
	private Integer[] leftCurrentTiming = {55,20,55,0,55,0,10,15,0,25,15,65,10,0,0,55,10,5,75,0,40,0,50,60,25,5};
	private Integer[] leftCurrentSize = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private Integer[] leftCurrentPlace = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	private float[] rightSquareStart = new float[26];
	private float[] rightSquareEnd = new float[26];
	private float[] rightScreenStart = new float[26];
	private float[] rightScreenEnd = new float[26];
	private float[] rightPercent = {5,10,14,17,21,25,32,38,45,50,58,63,68,71,72,78,82,88,93,100,110,111,119,123,128,130,135,138,145,150,157,162,164,167,170,176,180,182,188,193,40,90,70,120,90,140,130,170,150,195};
	private Integer[] rightTiming = {75,20,45,100,20,50,10,41,50,25,63,11,25,175,22,99,58,68,194,40,400,215,100,315,160};
	private Integer[] rightSize = {60,91,81,170,207,10,45,80,54,50,40,75,29,53,22,47,95,17,34,73,6,3,8,6,4} ;
	private Integer[] rightCurrentTiming = {55,20,55,0,55,0,10,15,0,25,15,65,10,0,0,55,10,5,75,0,40,0,60,160,80,90};
	private Integer[] rightCurrentSize = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private Integer[] rightCurrentPlace = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	private int summontime = 0;
	private float[] dimensions = new float[2];  
	private int score = 0;
	
	public GameView(Context context, int w, int h) {
		super(context);
		gameLoopThread = new GameLoopThread(this);
		mContext = context;

		width = w;
		height = h;
		centerx = width/2;
		centery = (height/2);
		shot_fired = 0;
		shooting = 0;
		
		int i = 0;
		for (i = 2; i < 8; i++){
			GameVariables.divide[i] = width/i; 
		}
		
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inSampleSize = 8;
		
		
		
		ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipfront);
		heartfull = BitmapFactory.decodeResource(getResources(), R.drawable.heartfull);
		heartempty = BitmapFactory.decodeResource(getResources(), R.drawable.heartempty);
		redhit = BitmapFactory.decodeResource(getResources(), R.drawable.redhit);
		glass = BitmapFactory.decodeResource(getResources(), R.drawable.glass);
		crash = BitmapFactory.decodeResource(getResources(), R.drawable.crash);
		lefthud = BitmapFactory.decodeResource(getResources(), R.drawable.lefthud);
		righthud = BitmapFactory.decodeResource(getResources(), R.drawable.righthud);
		midhud = BitmapFactory.decodeResource(getResources(), R.drawable.midhud);
		warninggrey = BitmapFactory.decodeResource(getResources(), R.drawable.warninggrey);
		warningred = BitmapFactory.decodeResource(getResources(), R.drawable.warningred);
		thousand = BitmapFactory.decodeResource(getResources(), R.drawable.thousandzero);
		hundred = BitmapFactory.decodeResource(getResources(), R.drawable.hundredzero);
		ten = BitmapFactory.decodeResource(getResources(), R.drawable.tenzero);
		story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title1);


		
		
		//x = centerx - (ship.getHeight()/2);
		y = height;
		x = width/2;
		GameVariables.touchY = height/2;
		
		ship_x = centerx - (ship.getHeight()/2);;
		ship_y = height-70;
		
		//gameLoopThread = new GameLoopThread(this);
		
		SurfaceHolder holder = getHolder();
        holder.addCallback(this);
		//holder = getHolder();
		//holder.addCallback(new Callback() {});
		
		
		mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		mDisplay = mWindowManager.getDefaultDisplay();
		mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		
		 
		mSensorX = new float[10];
        mSensorX[0] = 0f;
        mSensorX[1] = 0f;
        mSensorX[2] = 0f;
        mSensorX[3] = 0f;
        mSensorX[4] = 0f;
        mSensorX[5] = 0f;
        mSensorX[6] = 0f;
        mSensorX[7] = 0f;
        mSensorX[8] = 0f;
        mSensorX[9] = 0f;
        mSensorY = new float[10];
        mSensorY[0] = 0f;
        mSensorY[1] = 0f;
        mSensorY[2] = 0f;
        mSensorY[3] = 0f;
        mSensorY[4] = 0f;
        mSensorY[5] = 0f;
        mSensorY[6] = 0f;
        mSensorY[7] = 0f;
        mSensorY[8] = 0f;
        mSensorY[9] = 0f;
	}


		
			public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				//gameLoopThread.setRunning(true);
				
				//mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
			}

		
			// reserves and locks the canvas
			public void surfaceCreated(SurfaceHolder holder) {

				gameLoopThread.setRunning(true);
				gameLoopThread.start();
				mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

				// Canvas c = holder.lockCanvas(null);
				// onDraw(c);
				// holder.unlockCanvasAndPost(c);
				
				
			}

			@SuppressWarnings("deprecation")
			
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				mSensorManager.unregisterListener(this);
				
				/*
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while (retry) {
					try {
							gameLoopThread.join();
							retry = false;
					} catch (InterruptedException e) {}
				}
				*/
				

			}

	

	

	@Override
	protected void onDraw(Canvas canvas) {
		
		totalSpeed = 0;
		if (y == height && gettingHit == 0 && shot_fired == 0){
			//if (shooting == 0){
				/*for (int i = 0; i < 8; i++){
					if (mSensorX[9] < -1) { //if phone tiled RIGHT
						if (ship_x >= (width-50)) { // if arrived at RIGHT BORDER
							xSpeed = 0; // stop moving
							ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipfront);
						} else {
							xSpeed = 2; // if not arrived yet, increment to move right
							ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipright);
						}
					} 
					else if (mSensorX[9] > 1) { // if phone is tiled LEFT
						if (ship_x <= 0) { // if arrived at LEFT BORDER
							xSpeed = 0;
							ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipfront);
						} else {
							xSpeed = -2; // if not arrived yet, move left
							ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipleft);
						}
					}
					else {
						xSpeed = 0;
						ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipfront);
					}
					
						ship_x = ship_x + xSpeed;
						totalSpeed+=xSpeed;
						centerx = centerx + totalSpeed/5;
					
				}*/
			//}
			moveShip(GameVariables.controlMethod); // 2 options to move the ship in here		
		}
		else if (gettingHit == 0){
			//ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipfront);
			y = height;
			if (shot_fired == 0 && boosting > 195 && GameVariables.tutorial > 1){
				//x = ship_x;
				
				if (GameVariables.controlMethod == true) {
					GameVariables.shotfrom = ship_x;
					shot_fired = 1;
					shooting = 1;
					GameVariables.pressAt = GameVariables.volume;
				}
				//if using touch controls, shooting = 1 only of touch is top half of the screen
				else if (GameVariables.controlMethod == false){
					if (GameVariables.touchY < (height/2 -1)  ) {
						GameVariables.shotfrom = ship_x;
						//ship_x = x;
						xSpeed = 0;
						x = ship_x;
						ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipfront);
						shot_fired = 1;
						shooting = 1;
						GameVariables.pressAt = GameVariables.volume;
					}
					else {
						shot_fired = 0;
						shooting = 0;
					}
				}
				
			}
			else{
				GameVariables.tutorial++;
			}
			
		}
		
		drawBackground(canvas);
		drawBox(canvas);
		drawHUD(canvas);
		modifyBoxes();
		drawHelper(canvas);
		//drawGlass(canvas);


		
//		drawRedHit(canvas);

		if (GameVariables.hit == 1 && GameVariables.isSummoned == 0)
		{
			//GameVariables.shock.vibrate(10);
			drawHit();
			drawRedHit(canvas);
			
		}
		//Removed due to debugging
		if (GameVariables.isSummoned == 1){
			middle.setColor(Color.WHITE);
		//	canvas.drawRect(GameVariables.enemyLocation[0], (float)height-10, GameVariables.enemyLocation[1], (float)height, middle);
			
			drawGlass(canvas);

		}

		drawShip(canvas);
		if (shot_fired == 1){
			speedBoost();
			//shot_firing(canvas);
		}
		
	}
	protected void beginning(Canvas canvas){
		GameVariables.storytime+=5;		
		storyPaint.setStyle(Paint.Style.FILL);
		storyPaint.setColor(Color.BLACK);
		canvas.drawRect(0, height-story1.getHeight(), width, height, storyPaint);
		if (GameVariables.story == 0){
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title1);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else 
			{
				if (GameVariables.storytime >= story1.getWidth()+60){
					GameVariables.storytime = 0;
					GameVariables.story = 1;
				}
			}
		}	
		else if (GameVariables.story == 1){
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title2);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else 
			{
				if (GameVariables.storytime >= story1.getWidth()+60){
					GameVariables.storytime = 0;
					GameVariables.story = 2;
				}
			}
		}
		else if (GameVariables.story == 2){
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title3);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else 
			{
				if (GameVariables.storytime >= story1.getWidth()+60){
					GameVariables.storytime = 0;
					GameVariables.story = 3;
				}
			}
		}
		else if (GameVariables.story == 3){
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title4);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else 
			{
				if (GameVariables.storytime >= story1.getWidth() && GameVariables.storytime <= (story1.getWidth()+75)){
					GameVariables.speedModifier = 3;
					canvas.drawRect(0, 0, width, height-story1.getHeight(), storyFade);
					drawBox(canvas);
					modifyBoxes();
					canvas.drawRect(0, 0, width, height-story1.getHeight(), storyFade);
					
				}
				else if (GameVariables.storytime > (story1.getWidth()+75)){
					storyFade.setAlpha(storyAlpha);
					canvas.drawRect(0, 0, width, height-story1.getHeight(), storyFade);
					drawBox(canvas);
					canvas.drawRect(0, 0, width, height-story1.getHeight(), storyFade);
					storyAlpha-=5;
				}
				if (storyAlpha <= 10){
					storyAlpha = 255;
					GameVariables.speedModifier = 0;
					GameVariables.storytime = 0;
					GameVariables.story = 4;
				}
			}
		}
		
		else if (GameVariables.story == 4){
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title5);
			canvas.drawRect(0, 0, width, height-story1.getHeight(), middle);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			drawBox(canvas);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else 
			{
				if (GameVariables.storytime >= story1.getWidth() && GameVariables.storytime <= 2000){
					storyFade.setAlpha(storyAlpha);
					canvas.drawBitmap(ship, ship_x-10, ship_y, null);
					canvas.drawRect(ship_x-10, ship_y, ship_x-10+ship.getWidth(), ship_y+ship.getHeight(), storyFade);
					storyAlpha-=4;
					if (storyAlpha <=6){
						GameVariables.storytime = 2000;
					}
				}
				else if (GameVariables.storytime >= 2000 && GameVariables.storytime <= 3000){
					storyFade.setAlpha(storyAlpha);
					canvas.drawBitmap(ship, ship_x-10, ship_y, null);
					canvas.drawRect(ship_x-10, ship_y, ship_x-10+ship.getWidth(), ship_y+ship.getHeight(), storyFade);
					storyAlpha+=4;
					if (storyAlpha >= 249){
						GameVariables.storytime = 4000;
					}
				}
				else if (GameVariables.storytime >= 3000 && GameVariables.storytime <= 4000){
					storyFade.setAlpha(storyAlpha);
					canvas.drawBitmap(ship, ship_x-10, ship_y, null);
					canvas.drawRect(ship_x-10, ship_y, ship_x-10+ship.getWidth(), ship_y+ship.getHeight(), storyFade);
					storyAlpha-=4;
					if (storyAlpha <= 6){
						GameVariables.storytime = 4000;
					}
				}
				else if (GameVariables.storytime >= 4000 && GameVariables.storytime <= 5000){
					storyFade.setAlpha(storyAlpha);
					canvas.drawBitmap(ship, ship_x-10, ship_y, null);
					canvas.drawRect(ship_x-10, ship_y, ship_x-10+ship.getWidth(), ship_y+ship.getHeight(), storyFade);
					storyAlpha+=4;
					if (storyAlpha >=249){
						GameVariables.storytime = 5000;
					}
				}
				else if (GameVariables.storytime >= 5000 && GameVariables.storytime <= 6000){
					storyFade.setAlpha(storyAlpha);
					canvas.drawBitmap(ship, ship_x-10, ship_y, null);
					canvas.drawRect(ship_x-10, ship_y, ship_x-10+ship.getWidth(), ship_y+ship.getHeight(), storyFade);
					storyAlpha-=4;
					if (storyAlpha <=6){
						GameVariables.storytime = 6000;
					}
				}
				else if (GameVariables.storytime >= 6000){
					storyAlpha = 0;
					GameVariables.storytime = 0;
					GameVariables.storySpeed = 1;
					GameVariables.story = 5;
				}
			}
		}
		else if (GameVariables.story == 5){ // Engine Online
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title6);
			canvas.drawRect(0, 0, width, height-story1.getHeight(), middle);
			drawBox(canvas);
			modifyBoxes();
			canvas.drawBitmap(ship, ship_x-10, ship_y, null);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);

			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), story1.getWidth(), height, storyPaint);
			}
			else 
			{
				if (GameVariables.storytime >= story1.getWidth()+100 && GameVariables.storytime <= story1.getWidth()+200){
					GameVariables.speedModifier = 32;
					GameVariables.storySpeed = 0;
				}
				else if (GameVariables.storytime >= story1.getWidth() + 200 && GameVariables.storytime <= story1.getWidth()+300){
					GameVariables.speedModifier = 16;
				}
				else if (GameVariables.storytime >= story1.getWidth() + 300 && GameVariables.storytime <= story1.getWidth()+400){
					GameVariables.speedModifier = 8;
				}
				else if (GameVariables.storytime >= story1.getWidth() + 400 && GameVariables.storytime <= story1.getWidth()+500){
					GameVariables.speedModifier = 4;
				}
				else if (GameVariables.storytime >= story1.getWidth() + 500 && GameVariables.storytime <= story1.getWidth()+600){
					GameVariables.speedModifier = 2;
				}
				else if (GameVariables.storytime >= 600){
					GameVariables.storytime = 0;
					GameVariables.story = 6;
				}
			}
		}
		else if (GameVariables.story == 6){
			totalSpeed = 0;
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title7);
			canvas.drawRect(0, 0, width, height-story1.getHeight(), middle);
			drawBox(canvas);
			modifyBoxes();
			canvas.drawBitmap(ship, ship_x-10, ship_y, null);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), story1.getWidth(), height, storyPaint);
			}
			else 
			{
				if (GameVariables.storytime >= story1.getWidth()){
					moveShip(GameVariables.controlMethod);
				}
				if (GameVariables.storytime < 10000){
					edges.setColor(Color.RED);
					edges.setStyle(Paint.Style.STROKE);
					canvas.drawRect(50, ship_y-10, 100, ship_y+40, edges);
					if (ship_x < 100){
						GameVariables.storytime = 10001;
					}
				}
				else if (GameVariables.storytime > 10000 && GameVariables.storytime < 20000){
					edges.setColor(Color.RED);
					edges.setStyle(Paint.Style.STROKE);
					canvas.drawRect(width - 200, ship_y-10, width - 150, ship_y+40, edges);
					if (ship_x > width - 200){
						GameVariables.storytime = 20001;
					}
				}
				else if (GameVariables.storytime > 20000)
				{
					GameVariables.storytime = 0;
					GameVariables.story = 7;
				}
			}
		}
		else if (GameVariables.story == 7){
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title8);
			canvas.drawRect(0, 0, width, height-story1.getHeight(), middle);
			drawBox(canvas);
			modifyBoxes();
			canvas.drawBitmap(ship, ship_x-10, ship_y, null);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else 
			{
				if (GameVariables.storytime >= story1.getWidth()+60 && centerx <= width/2){
					centerx+=1;
				}
				if (GameVariables.storytime >= story1.getWidth()+60 && ship_x >= width/2){
					ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipleft);
					ship_x-=5;
				}
				if (ship_x <= width/2 && centerx >= width/2){
					ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipfront);
					GameVariables.storytime = 0;
					GameVariables.story = 8;
					boosting = 0;
				}
			}
		}
		else if (GameVariables.story == 8){
			int i;

			int HUDcolor = 0xFFFFFF00; 
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title9);
			canvas.drawRect(0, 0, width, height-story1.getHeight(), middle);
			drawBox(canvas);
			modifyBoxes();
			canvas.drawBitmap(ship, ship_x-10, ship_y, null);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else 
			{
				if (GameVariables.storytime >= story1.getWidth()+60){
					if (GameVariables.storytime == story1.getWidth()+60){
						boosting = 0;
					}
					storyPaint.setAlpha(175);
					boosting += 2;
					for (i = 0; i < boosting*width/392; i+=2){
						canvas.drawRect(width/2 + i, 1, width/2 + i + 1 , 19, storyPaint);
						canvas.drawRect(width/2 - i, 1, width/2 - i - 1 , 19, storyPaint);
						storyPaint.setColor(HUDcolor);
						if (HUDcolor != 0xFFFF0000){
							HUDcolor = HUDcolor - (1 << 8);
						}
					}	
					if (boosting >= 196){
						GameVariables.storytime = 0;
						GameVariables.story = 9;
					}
				}

			}
		}
		else if (GameVariables.story == 9){
			int i;
			int HUDcolor = 0xFFFFFF00; 
			
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title10);
			canvas.drawRect(0, 0, width, height-story1.getHeight(), middle);
			drawBox(canvas);
			modifyBoxes();
			canvas.drawBitmap(ship, ship_x-10, ship_y, null);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			
			for (i = 0; i < boosting*width/392; i+=2){
				canvas.drawRect(width/2 + i, 1, width/2 + i + 1 , 19, storyPaint);
				canvas.drawRect(width/2 - i, 1, width/2 - i - 1 , 19, storyPaint);
				storyPaint.setColor(HUDcolor);
				if (HUDcolor != 0xFFFF0000){
					HUDcolor = HUDcolor - (1 << 8);
				}
			}	
			
			storyPaint.setColor(Color.BLACK);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else 
			{
				if (GameVariables.storytime >= story1.getWidth()+120){
					GameVariables.storytime = 0;
					GameVariables.story = 10;
				}
			}
		}
		else if (GameVariables.story == 10){
			int i;
			int HUDcolor = 0xFFFFFF00; 
			
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title11);
			canvas.drawRect(0, 0, width, height-story1.getHeight(), middle);
			drawBox(canvas);
			modifyBoxes();
			canvas.drawBitmap(ship, ship_x-10, ship_y, null);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			for (i = 0; i < boosting*width/392; i+=2){
				canvas.drawRect(width/2 + i, 1, width/2 + i + 1 , 19, storyPaint);
				canvas.drawRect(width/2 - i, 1, width/2 - i - 1 , 19, storyPaint);
				storyPaint.setColor(HUDcolor);
				if (HUDcolor != 0xFFFF0000){
					HUDcolor = HUDcolor - (1 << 8);
				}
			}	
			storyPaint.setColor(Color.BLACK);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else 
			{
				if (GameVariables.storytime >= story1.getWidth()+60){
					if (shot_fired == 1){
						speedBoost();
					}
					if (boosting <=1){
						GameVariables.speedModifier = 2;
						GameVariables.storytime = 0;
						GameVariables.story = 11;
					}
				}
			}
		}
		else if (GameVariables.story == 11){
			int i, j;
			int HUDcolor = 0xFFFFFF00; 
			Paint HUDpaint = new Paint();
			HUDpaint.setAlpha(100);
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title12);
			canvas.drawRect(0, 0, width, height-story1.getHeight(), middle);
			drawBox(canvas);
			modifyBoxes();
			canvas.drawBitmap(ship, ship_x-10, ship_y, null);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			if (boosting < 196){
				boosting +=4;
			}
			for (i = 0; i < boosting*width/392; i+=2){
				canvas.drawRect(width/2 + i, 1, width/2 + i + 1 , 19, storyPaint);
				canvas.drawRect(width/2 - i, 1, width/2 - i - 1 , 19, storyPaint);
				storyPaint.setColor(HUDcolor);
				if (HUDcolor != 0xFFFF0000){
					HUDcolor = HUDcolor - (1 << 8);
				}
			}	
			storyPaint.setColor(Color.BLACK);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else 
			{
				if (GameVariables.storytime >= story1.getWidth()+60 && GameVariables.storytime < 10000){

					
					canvas.drawBitmap(lefthud, 0 - lefthud.getWidth() + (GameVariables.storytime - 60 - story1.getWidth()), 20, HUDpaint);
					canvas.drawBitmap(righthud, width- (GameVariables.storytime - 60 - story1.getWidth()), 20, HUDpaint);
					canvas.drawBitmap(warninggrey, width- (GameVariables.storytime - 60 - story1.getWidth()), 15, HUDpaint);
					canvas.drawBitmap(thousand, 0 - lefthud.getWidth() + (GameVariables.storytime - 60 - story1.getWidth()), 20, null);
					canvas.drawBitmap(hundred, 0 - lefthud.getWidth() + (GameVariables.storytime - 60 - story1.getWidth()), 20, null);
					canvas.drawBitmap(ten, 0 - lefthud.getWidth() + (GameVariables.storytime - 60 - story1.getWidth()), 20, null);

					if (0 - lefthud.getWidth() + GameVariables.storytime - 60 - story1.getWidth() >= 0){
						GameVariables.storytime = 10000;
						storycount = lefthud.getWidth();
					}
				}
				else if (GameVariables.storytime > 10000 && GameVariables.storytime < 20000){
					canvas.drawBitmap(lefthud, 0, 20, HUDpaint);
					canvas.drawBitmap(righthud, width- righthud.getWidth(), 20, HUDpaint);
					canvas.drawBitmap(warninggrey, width- righthud.getWidth(), 15, HUDpaint);
					canvas.drawBitmap(thousand, 0, 20, HUDpaint);
					canvas.drawBitmap(hundred, 0 , 20, HUDpaint);
					canvas.drawBitmap(ten, 0, 20, HUDpaint);

					for (j=lefthud.getWidth(); (j < width-righthud.getWidth()-midhud.getWidth()) && j <= storycount; j+=midhud.getWidth()){
							canvas.drawBitmap(midhud, j, 20, HUDpaint);
							j+=midhud.getWidth();
							canvas.drawBitmap(midhud, j, 20, HUDpaint);
							j+=midhud.getWidth();
							canvas.drawBitmap(midhud, j, 20, HUDpaint);
							j+=midhud.getWidth();
							canvas.drawBitmap(midhud, j, 20, HUDpaint);
							j+=midhud.getWidth();
							canvas.drawBitmap(midhud, j, 20, HUDpaint);
							j+=midhud.getWidth();
							canvas.drawBitmap(midhud, j, 20, HUDpaint);
							j+=midhud.getWidth();
							canvas.drawBitmap(midhud, j, 20, HUDpaint);
							j+=midhud.getWidth();
							canvas.drawBitmap(midhud, j, 20, HUDpaint);
							j+=midhud.getWidth();
							canvas.drawBitmap(midhud, j, 20, HUDpaint);
					}
					storycount = j;
					canvas.drawBitmap(midhud, width-righthud.getWidth()-midhud.getWidth(), 20, HUDpaint);
					if (storycount >= width-righthud.getWidth()-midhud.getWidth()){
						GameVariables.storytime = 0;
						GameVariables.story = 12;
					}
				}
			}
		}
		else if (GameVariables.story == 12){
			int i, j;
			int HUDcolor = 0xFFFFFF00; 
			Paint HUDpaint = new Paint();
			HUDpaint.setAlpha(100);
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title13);
			canvas.drawRect(0, 0, width, height-story1.getHeight(), middle);
			drawBox(canvas);
			modifyBoxes();
			canvas.drawBitmap(ship, ship_x-10, ship_y, null);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			if (boosting < 196){
				boosting +=4;
			}
			for (i = 0; i < boosting*width/392; i+=2){
				canvas.drawRect(width/2 + i, 1, width/2 + i + 1 , 19, storyPaint);
				canvas.drawRect(width/2 - i, 1, width/2 - i - 1 , 19, storyPaint);
				storyPaint.setColor(HUDcolor);
				if (HUDcolor != 0xFFFF0000){
					HUDcolor = HUDcolor - (1 << 8);
				}
			}	
			canvas.drawBitmap(lefthud, 0, 20, HUDpaint);
			canvas.drawBitmap(righthud, width- righthud.getWidth(), 20, HUDpaint);
			canvas.drawBitmap(warninggrey, width- righthud.getWidth(), 15, HUDpaint);
			canvas.drawBitmap(thousand, 0, 20, null);
			canvas.drawBitmap(hundred, 0 , 20, null);
			canvas.drawBitmap(ten, 0, 20, null);
			for (i=lefthud.getWidth(); i < width-righthud.getWidth()-midhud.getWidth()+1; i+=midhud.getWidth()){
				canvas.drawBitmap(midhud, i, 20, HUDpaint);
			}
			
			
			storyPaint.setColor(Color.BLACK);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
				if (GameVariables.storytime % 10 == 0){
					GameVariables.scoretime = r.nextInt(999);
					updateScore();
				}
			}
			else 
			{
				storyPaint.setAlpha(100);
				storyPaint.setColor(0xFFCCFFF);
				storyPaint.setStyle(Style.STROKE);
				if (GameVariables.storytime < story1.getWidth() + 50){
					if (GameVariables.storytime % 10 == 0){
						GameVariables.scoretime = r.nextInt(999);
						updateScore();
					}
				}
				else if (GameVariables.storytime > 200){
					GameVariables.scoretime = 0;
					updateScore();
					GameVariables.storytime = 0;
					storyAlpha = 0;
					GameVariables.story = 13;
				}
			}
		}
		else if (GameVariables.story == 13){
			int i, j;
			int HUDcolor = 0xFFFFFF00; 
			Paint HUDpaint = new Paint();
			HUDpaint.setAlpha(100);
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title14);
			canvas.drawRect(0, 0, width, height-story1.getHeight(), middle);
			drawBox(canvas);
			modifyBoxes();
			canvas.drawBitmap(ship, ship_x-10, ship_y, null);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			if (boosting < 196){
				boosting +=4;
			}
			for (i = 0; i < boosting*width/392; i+=2){
				canvas.drawRect(width/2 + i, 1, width/2 + i + 1 , 19, storyPaint);
				canvas.drawRect(width/2 - i, 1, width/2 - i - 1 , 19, storyPaint);
				storyPaint.setColor(HUDcolor);
				if (HUDcolor != 0xFFFF0000){
					HUDcolor = HUDcolor - (1 << 8);
				}
			}	
			canvas.drawBitmap(lefthud, 0, 20, HUDpaint);
			canvas.drawBitmap(righthud, width- righthud.getWidth(), 20, HUDpaint);
			canvas.drawBitmap(warninggrey, width- righthud.getWidth(), 15, HUDpaint);
			canvas.drawBitmap(thousand, 0, 20, null);
			canvas.drawBitmap(hundred, 0 , 20, null);
			canvas.drawBitmap(ten, 0, 20, null);
			for (i=lefthud.getWidth(); i < width-righthud.getWidth()-midhud.getWidth()+1; i+=midhud.getWidth()){
				canvas.drawBitmap(midhud, i, 20, HUDpaint);
			}
			
			
			storyPaint.setColor(Color.BLACK);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else 
			{
				
				if (GameVariables.storytime >= story1.getWidth() && GameVariables.storytime < 10000){
					if (storyAlpha >= 0 && storyAlpha <= 250){
						storyFade.setAlpha(storyAlpha);
						canvas.drawBitmap(heartempty, width/2 - heartfull.getWidth()/2 - 10 - heartfull.getWidth(), 25, storyFade);
					}
					else if (storyAlpha >= 250)
					{
						storyFade.setAlpha(250);
						canvas.drawBitmap(heartempty, width/2 - heartfull.getWidth()/2 - 10 - heartfull.getWidth(), 25, storyFade);
					}
					if (storyAlpha >= 50 && storyAlpha <= 300){
						storyFade.setAlpha(storyAlpha-50);
						canvas.drawBitmap(heartempty, width/2 - heartfull.getWidth()/2, 25, storyFade);
					}
					else if (storyAlpha >= 300)
					{
						storyFade.setAlpha(250);
						canvas.drawBitmap(heartempty, width/2 - heartfull.getWidth()/2, 25, storyFade);
					}
					if (storyAlpha >= 100 && storyAlpha <= 350){
						storyFade.setAlpha(storyAlpha-100);
						canvas.drawBitmap(heartempty, width/2 - heartfull.getWidth()/2 + heartfull.getWidth() + 10, 25, storyFade);
					}
					else if (storyAlpha >= 350)
					{
						storyFade.setAlpha(250);
						canvas.drawBitmap(heartempty, width/2 - heartfull.getWidth()/2 + heartfull.getWidth() + 10, 25, storyFade);
					}
					if (storyAlpha >= 350){
						GameVariables.storytime = 10000;
						storyAlpha = 0;
					}
					storyAlpha += 2;
				}
				else if (GameVariables.storytime >= 10000 && GameVariables.storytime < 20000){


					if (storyAlpha >= 0 && storyAlpha <= 250){
						storyFade.setAlpha(storyAlpha);
						canvas.drawBitmap(heartfull, width/2 - heartfull.getWidth()/2 - 10 - heartfull.getWidth(), 25, storyFade);
					}
					else if (storyAlpha >= 250)
					{
						storyFade.setAlpha(250);
						canvas.drawBitmap(heartfull, width/2 - heartfull.getWidth()/2 - 10 - heartfull.getWidth(), 25, storyFade);
					}
					if (storyAlpha >= 50 && storyAlpha <= 300){
						storyFade.setAlpha(storyAlpha-50);
						canvas.drawBitmap(heartfull, width/2 - heartfull.getWidth()/2, 25, storyFade);
					}
					else if (storyAlpha >= 300)
					{
						storyFade.setAlpha(250);
						canvas.drawBitmap(heartfull, width/2 - heartfull.getWidth()/2, 25, storyFade);
					}
					if (storyAlpha >= 100 && storyAlpha <= 350){
						storyFade.setAlpha(storyAlpha-100);
						canvas.drawBitmap(heartfull, width/2 - heartfull.getWidth()/2 + heartfull.getWidth() + 10, 25, storyFade);
					}
					else if (storyAlpha >= 350)
					{
						storyFade.setAlpha(250);
						canvas.drawBitmap(heartfull, width/2 - heartfull.getWidth()/2 + heartfull.getWidth() + 10, 25, storyFade);
					}
					if (storyAlpha >=350){
						GameVariables.storytime = 20000;
					}
					canvas.drawBitmap(heartempty, width/2 - heartfull.getWidth()/2 - 10 - heartfull.getWidth(), 25, null);
					canvas.drawBitmap(heartempty, width/2 - heartfull.getWidth()/2, 25, null);
					canvas.drawBitmap(heartempty, width/2 - heartfull.getWidth()/2 + heartfull.getWidth() + 10, 25, null);

					storyAlpha += 2;

				}
				else if (GameVariables.storytime > 20000){
					GameVariables.storytime = 0;
					GameVariables.story = 14;
				}
			}
		}
		else if (GameVariables.story == 14){
			if (GameVariables.storytime >= 0 && GameVariables.storytime <= 600){
				story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title15);
			}
			drawBackground(canvas);
			drawBox(canvas);
			drawHUD(canvas);
			modifyBoxes();
			drawShip(canvas);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			storyPaint.setColor(Color.BLACK);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else 
			{
				helperPaint.setColor(0xFF0000FF);
				//canvas.drawRect(0, height-20, GameVariables.divide[2], height, middle);

				helperPaint.setStyle(Paint.Style.FILL);
				helperPaint.setAlpha(100);
				 
				helperLine.moveTo(centerx-100, centery+100);
				helperLine.lineTo(centerx, centery+100);
				helperLine.lineTo(centerx, centery+100 + GameVariables.storytime - story1.getWidth());
				helperLine.lineTo(centerx-100 - (((GameVariables.storytime-story1.getWidth())*(centerx-100))/(height - centery - 100)), centery+100 + GameVariables.storytime - story1.getWidth());
				canvas.drawPath(helperLine, helperPaint);
				helperLine.reset();
				helperPaint.setColor(0xFFFF0000);
				helperPaint.setStyle(Paint.Style.FILL);
				helperPaint.setAlpha(100);
				helperLine.moveTo(centerx+100, centery+100);
				helperLine.lineTo(centerx, centery+100);
				helperLine.lineTo(centerx, centery+100 + GameVariables.storytime - story1.getWidth());
				helperLine.lineTo(centerx+100 + (((GameVariables.storytime-story1.getWidth())*(centerx-100))/(height - centery - 100)), centery+100 + GameVariables.storytime - story1.getWidth());
				canvas.drawPath(helperLine, helperPaint);
				helperLine.reset();
				if ((centery+100 + GameVariables.storytime - story1.getWidth()) >= height){
					GameVariables.storytime = 0;
					GameVariables.story = 15;
					storyAlpha = 0;
				}
			}
		}
		else if (GameVariables.story == 15){
			if (GameVariables.storytime >= 0 && GameVariables.storytime <= 600){
				story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title16);
			}
			drawBackground(canvas);
			drawBox(canvas);
			drawHUD(canvas);
			modifyBoxes();
			drawHelper(canvas);
			drawShip(canvas);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			storyPaint.setColor(Color.BLACK);
			if (GameVariables.storytime <= story1.getWidth()){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else 
			{
				if (GameVariables.storytime >= story1.getWidth() && GameVariables.storytime <= 2000){
					GameVariables.randFreq = 500;
					GameVariables.volume = 0;
					GameVariables.soundPlayed = 1;
					GameVariables.storytime = 2001;
				}
				else if (GameVariables.storytime >= 2001 && GameVariables.storytime <= 3000){
					if (storyAlpha <=240){
						storyAlpha += 10;
					}
					else{
						GameVariables.storytime = 3001;
					}
					storyPaint.setColor(0xFFFFFFFF);
					//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
					storyPaint.setStyle(Paint.Style.FILL);
					storyPaint.setAlpha(storyAlpha);
					helperLine.moveTo(GameVariables.divide[7]*0, height-1);
					helperLine.lineTo(centerx-100 + (200/7)*0, centery+100);
					helperLine.lineTo(centerx-100 + (200/7)*1, centery+100);
					helperLine.lineTo(GameVariables.divide[7]*1, height-1);
					canvas.drawPath(helperLine, storyPaint);
					helperLine.reset();
					if (GameVariables.soundPlayed == 0){
						GameVariables.storytime = 4001;
					}
				}
				else if (GameVariables.storytime >= 3001 && GameVariables.storytime <= 4000){
					if (storyAlpha >=10){
						storyAlpha -= 10;
					}
					else{
						GameVariables.storytime = 2001;
					}
					storyPaint.setColor(0xFFFFFFFF);
					//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
					storyPaint.setStyle(Paint.Style.FILL);
					storyPaint.setAlpha(storyAlpha);
					helperLine.moveTo(GameVariables.divide[7]*0, height-1);
					helperLine.lineTo(centerx-100 + (200/7)*0, centery+100);
					helperLine.lineTo(centerx-100 + (200/7)*1, centery+100);
					helperLine.lineTo(GameVariables.divide[7]*1, height-1);
					canvas.drawPath(helperLine, storyPaint);
					helperLine.reset();
					if (GameVariables.soundPlayed == 0){
						GameVariables.storytime = 4001;
					}
				}
				else if (GameVariables.storytime >= 4001 && GameVariables.storytime <= 5000){
					GameVariables.randFreq = 1000;
					storyAlpha = 0;
					GameVariables.volume = 0;
					GameVariables.soundPlayed = 1;
					GameVariables.storytime = 5001;
				}
				else if (GameVariables.storytime >= 5001 && GameVariables.storytime <= 6000){
					if (storyAlpha <=240){
						storyAlpha += 10;
					}
					else{
						GameVariables.storytime = 6001;
					}
					storyPaint.setColor(0xFFFFFFFF);
					//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
					storyPaint.setStyle(Paint.Style.FILL);
					storyPaint.setAlpha(storyAlpha);
					helperLine.moveTo(GameVariables.divide[7]*1, height-1);
					helperLine.lineTo(centerx-100 + (200/7)*1, centery+100);
					helperLine.lineTo(centerx-100 + (200/7)*2, centery+100);
					helperLine.lineTo(GameVariables.divide[7]*2, height-1);
					canvas.drawPath(helperLine, storyPaint);
					helperLine.reset();
					if (GameVariables.soundPlayed == 0){
						GameVariables.storytime = 7001;
					}
				}
				else if (GameVariables.storytime >= 6001 && GameVariables.storytime <= 7000){
					if (storyAlpha >=10){
						storyAlpha -= 10;
					}
					else{
						GameVariables.storytime = 5001;
					}
					storyPaint.setColor(0xFFFFFFFF);
					//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
					storyPaint.setStyle(Paint.Style.FILL);
					storyPaint.setAlpha(storyAlpha);
					helperLine.moveTo(GameVariables.divide[7]*1, height-1);
					helperLine.lineTo(centerx-100 + (200/7)*1, centery+100);
					helperLine.lineTo(centerx-100 + (200/7)*2, centery+100);
					helperLine.lineTo(GameVariables.divide[7]*2, height-1);
					canvas.drawPath(helperLine, storyPaint);
					helperLine.reset();
					if (GameVariables.soundPlayed == 0){
						GameVariables.storytime = 7001;
					}
				}
				else if (GameVariables.storytime >= 7001 && GameVariables.storytime <= 8000){
					GameVariables.randFreq = 3000;
					GameVariables.volume = 0;
					storyAlpha = 0;
					GameVariables.soundPlayed = 1;
					GameVariables.storytime = 8001;
				}
				else if (GameVariables.storytime >= 8001 && GameVariables.storytime <= 9000){
					if (storyAlpha <=240){
						storyAlpha += 10;
					}
					else{
						GameVariables.storytime = 9001;
					}
					storyPaint.setColor(0xFFFFFFFF);
					//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
					storyPaint.setStyle(Paint.Style.FILL);
					storyPaint.setAlpha(storyAlpha);
					helperLine.moveTo(GameVariables.divide[7]*2, height-1);
					helperLine.lineTo(centerx-100 + (200/7)*2, centery+100);
					helperLine.lineTo(centerx-100 + (200/7)*3, centery+100);
					helperLine.lineTo(GameVariables.divide[7]*3, height-1);
					canvas.drawPath(helperLine, storyPaint);
					helperLine.reset();
					if (GameVariables.soundPlayed == 0){
						GameVariables.storytime = 10001;
					}
				}
				else if (GameVariables.storytime >= 9001 && GameVariables.storytime <= 10000){
					if (storyAlpha >=10){
						storyAlpha -= 10;
					}
					else{
						GameVariables.storytime = 8001;
					}
					storyPaint.setColor(0xFFFFFFFF);
					//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
					storyPaint.setStyle(Paint.Style.FILL);
					storyPaint.setAlpha(storyAlpha);
					helperLine.moveTo(GameVariables.divide[7]*2, height-1);
					helperLine.lineTo(centerx-100 + (200/7)*2, centery+100);
					helperLine.lineTo(centerx-100 + (200/7)*3, centery+100);
					helperLine.lineTo(GameVariables.divide[7]*3, height-1);
					canvas.drawPath(helperLine, storyPaint);
					helperLine.reset();
					if (GameVariables.soundPlayed == 0){
						GameVariables.storytime = 10001;
					}
				}
				else if (GameVariables.storytime >= 10001 && GameVariables.storytime <= 11000){
					GameVariables.randFreq = 5000;
					GameVariables.volume = 0;
					storyAlpha = 0;
					GameVariables.soundPlayed = 1;
					GameVariables.storytime = 11001;
				}
				else if (GameVariables.storytime >= 11001 && GameVariables.storytime <= 12000){
					if (storyAlpha <=240){
						storyAlpha += 10;
					}
					else{
						GameVariables.storytime = 12001;
					}
					storyPaint.setColor(0xFFFFFFFF);
					//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
					storyPaint.setStyle(Paint.Style.FILL);
					storyPaint.setAlpha(storyAlpha);
					helperLine.moveTo(GameVariables.divide[7]*3, height-1);
					helperLine.lineTo(centerx-100 + (200/7)*3, centery+100);
					helperLine.lineTo(centerx-100 + (200/7)*4, centery+100);
					helperLine.lineTo(GameVariables.divide[7]*4, height-1);
					canvas.drawPath(helperLine, storyPaint);
					helperLine.reset();
					if (GameVariables.soundPlayed == 0){
						GameVariables.storytime = 13001;
					}
				}
				else if (GameVariables.storytime >= 12001 && GameVariables.storytime <= 13000){
					if (storyAlpha >=10){
						storyAlpha -= 10;
					}
					else{
						GameVariables.storytime = 11001;
					}
					storyPaint.setColor(0xFFFFFFFF);
					//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
					storyPaint.setStyle(Paint.Style.FILL);
					storyPaint.setAlpha(storyAlpha);
					helperLine.moveTo(GameVariables.divide[7]*3, height-1);
					helperLine.lineTo(centerx-100 + (200/7)*3, centery+100);
					helperLine.lineTo(centerx-100 + (200/7)*4, centery+100);
					helperLine.lineTo(GameVariables.divide[7]*4, height-1);
					canvas.drawPath(helperLine, storyPaint);
					helperLine.reset();
					if (GameVariables.soundPlayed == 0){
						GameVariables.storytime = 13001;
					}
				}
				else if (GameVariables.storytime >= 13001 && GameVariables.storytime <= 14000){
					GameVariables.randFreq = 8000;
					GameVariables.volume = 0;
					storyAlpha = 0;
					GameVariables.soundPlayed = 1;
					GameVariables.storytime = 14001;
				}
				else if (GameVariables.storytime >= 14001 && GameVariables.storytime <= 15000){
					if (storyAlpha <=240){
						storyAlpha += 10;
					}
					else{
						GameVariables.storytime = 15001;
					}
					storyPaint.setColor(0xFFFFFFFF);
					//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
					storyPaint.setStyle(Paint.Style.FILL);
					storyPaint.setAlpha(storyAlpha);
					helperLine.moveTo(GameVariables.divide[7]*4, height-1);
					helperLine.lineTo(centerx-100 + (200/7)*4, centery+100);
					helperLine.lineTo(centerx-100 + (200/7)*5, centery+100);
					helperLine.lineTo(GameVariables.divide[7]*5, height-1);
					canvas.drawPath(helperLine, storyPaint);
					helperLine.reset();
					if (GameVariables.soundPlayed == 0){
						GameVariables.storytime = 16001;
					}
				}
				else if (GameVariables.storytime >= 15001 && GameVariables.storytime <= 16000){
					if (storyAlpha >=10){
						storyAlpha -= 10;
					}
					else{
						GameVariables.storytime = 14001;
					}
					storyPaint.setColor(0xFFFFFFFF);
					//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
					storyPaint.setStyle(Paint.Style.FILL);
					storyPaint.setAlpha(storyAlpha);
					helperLine.moveTo(GameVariables.divide[7]*4, height-1);
					helperLine.lineTo(centerx-100 + (200/7)*4, centery+100);
					helperLine.lineTo(centerx-100 + (200/7)*5, centery+100);
					helperLine.lineTo(GameVariables.divide[7]*5, height-1);
					canvas.drawPath(helperLine, storyPaint);
					helperLine.reset();
					if (GameVariables.soundPlayed == 0){
						GameVariables.storytime = 16001;
					}
				}
				else if (GameVariables.storytime >= 16001 && GameVariables.storytime <= 17000){
					GameVariables.randFreq = 10000;
					GameVariables.volume = 0;
					storyAlpha = 0;
					GameVariables.soundPlayed = 1;
					GameVariables.storytime = 17001;
				}
				else if (GameVariables.storytime >= 17001 && GameVariables.storytime <= 18000){
					if (storyAlpha <=240){
						storyAlpha += 10;
					}
					else{
						GameVariables.storytime = 18001;
					}
					storyPaint.setColor(0xFFFFFFFF);
					//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
					storyPaint.setStyle(Paint.Style.FILL);
					storyPaint.setAlpha(storyAlpha);
					helperLine.moveTo(GameVariables.divide[7]*5, height-1);
					helperLine.lineTo(centerx-100 + (200/7)*5, centery+100);
					helperLine.lineTo(centerx-100 + (200/7)*6, centery+100);
					helperLine.lineTo(GameVariables.divide[7]*6, height-1);
					canvas.drawPath(helperLine, storyPaint);
					helperLine.reset();
					if (GameVariables.soundPlayed == 0){
						GameVariables.storytime = 19001;
					}
				}
				else if (GameVariables.storytime >= 18001 && GameVariables.storytime <= 19000){
					if (storyAlpha >=10){
						storyAlpha -= 10;
					}
					else{
						GameVariables.storytime = 17001;
					}
					storyPaint.setColor(0xFFFFFFFF);
					//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
					storyPaint.setStyle(Paint.Style.FILL);
					storyPaint.setAlpha(storyAlpha);
					helperLine.moveTo(GameVariables.divide[7]*5, height-1);
					helperLine.lineTo(centerx-100 + (200/7)*5, centery+100);
					helperLine.lineTo(centerx-100 + (200/7)*6, centery+100);
					helperLine.lineTo(GameVariables.divide[7]*6, height-1);
					canvas.drawPath(helperLine, storyPaint);
					helperLine.reset();
					if (GameVariables.soundPlayed == 0){
						GameVariables.storytime = 19001;
					}
				}
				else if (GameVariables.storytime >= 19001 && GameVariables.storytime <= 20000){
					GameVariables.randFreq = 14000;
					GameVariables.volume = 0;
					storyAlpha = 0;
					GameVariables.soundPlayed = 1;
					GameVariables.storytime = 20001;
				}
				else if (GameVariables.storytime >= 20001 && GameVariables.storytime <= 21000){
					if (storyAlpha <=240){
						storyAlpha += 10;
					}
					else{
						GameVariables.storytime = 21001;
					}
					storyPaint.setColor(0xFFFFFFFF);
					//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
					storyPaint.setStyle(Paint.Style.FILL);
					storyPaint.setAlpha(storyAlpha);
					helperLine.moveTo(GameVariables.divide[7]*6, height-1);
					helperLine.lineTo(centerx-100 + (200/7)*6, centery+100);
					helperLine.lineTo(centerx-100 + (200/7)*7, centery+100);
					helperLine.lineTo(GameVariables.divide[7]*7, height-1);
					canvas.drawPath(helperLine, storyPaint);
					helperLine.reset();
					if (GameVariables.soundPlayed == 0){
						GameVariables.storytime = 22001;
					}
				}
				else if (GameVariables.storytime >= 21001 && GameVariables.storytime <= 22000){
					if (storyAlpha >=10){
						storyAlpha -= 10;
					}
					else{
						GameVariables.storytime = 20001;
					}
					storyPaint.setColor(0xFFFFFFFF);
					//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
					storyPaint.setStyle(Paint.Style.FILL);
					storyPaint.setAlpha(storyAlpha);
					helperLine.moveTo(GameVariables.divide[7]*6, height-1);
					helperLine.lineTo(centerx-100 + (200/7)*6, centery+100);
					helperLine.lineTo(centerx-100 + (200/7)*7, centery+100);
					helperLine.lineTo(GameVariables.divide[7]*7, height-1);
					canvas.drawPath(helperLine, storyPaint);
					helperLine.reset();
					if (GameVariables.soundPlayed == 0){
						GameVariables.storytime = 22001;
					}
				}
				else if (GameVariables.storytime >= 22001 ){
					GameVariables.storytime = 0;
					GameVariables.story = 16;
				}		
			}
		}
		else if (GameVariables.story == 16){
			if (GameVariables.storytime >= 0 && GameVariables.storytime <= 600){
				story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title17);
			}
			drawBackground(canvas);
			drawBox(canvas);
			drawHUD(canvas);
			modifyBoxes();
			drawHelper(canvas);
			drawShip(canvas);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			storyPaint.setColor(Color.BLACK);
			if (GameVariables.storytime <= (story1.getWidth())){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else if (GameVariables.storytime >= story1.getWidth() && GameVariables.storytime <= 10000){
				generateEnemy(canvas);
				GameVariables.storytime = 10001;
				moveShip(GameVariables.controlMethod);

			}
			else if (GameVariables.storytime >= 10001 && GameVariables.storytime <= 20000){
				totalSpeed = 0;

				if (GameVariables.isSummoned == 1){
					middle.setColor(Color.WHITE);
					canvas.drawRect(GameVariables.enemyLocation[0], (float)height-10, GameVariables.enemyLocation[1], (float)height, middle);
					drawGlass(canvas);
				}
				moveShip(GameVariables.controlMethod);
			}
			else if (GameVariables.storytime >= 20001 && GameVariables.storytime <= 30000){
				GameVariables.storytime = 0;
				GameVariables.story = 17;
			}
			else if (GameVariables.storytime >= 30001 && GameVariables.storytime <= 40000){
				GameVariables.storytime = 10000;
				GameVariables.story = 17;
			}
		}
		else if (GameVariables.story == 17){
			if (GameVariables.storytime >= 0 && GameVariables.storytime <= 9999){
				story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title18a);
			}
			else{
				story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title18b);
			}
			drawBackground(canvas);
			drawBox(canvas);
			drawHUD(canvas);
			modifyBoxes();
			drawHelper(canvas);
			drawShip(canvas);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			storyPaint.setColor(Color.BLACK);
			if (GameVariables.storytime <= (story1.getWidth())){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else if (GameVariables.storytime >= story1.getWidth() && GameVariables.storytime <= 10000){
				GameVariables.storytime = 0;
				GameVariables.story = 16;
			}
			else if (GameVariables.storytime >= 10000 && GameVariables.storytime <= (story1.getWidth()+10000)){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else if (GameVariables.storytime >= story1.getWidth()+10000){
				GameVariables.storytime = 0;
				GameVariables.story = 18;
			}
		}
		else if (GameVariables.story == 18){
			if (GameVariables.storytime >= 0 && GameVariables.storytime <= 600){
				story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title19);
			}
			else if (GameVariables.storytime >= 600 && GameVariables.storytime <= 750){
				story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title20);
			}
			else if (GameVariables.storytime >= 750 && GameVariables.storytime <= 900){
				story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title21);
			}
			else if (GameVariables.storytime >= 900 && GameVariables.storytime <= 1050){
				story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title22);
			}
			drawBackground(canvas);
			drawBox(canvas);
			drawHUD(canvas);
			modifyBoxes();
			drawHelper(canvas);
			drawShip(canvas);
			canvas.drawBitmap(story1, 0, height-story1.getHeight(), null);
			if (GameVariables.storytime <= 1050){
				canvas.drawRect(GameVariables.storytime, height-story1.getHeight(), width, height, storyPaint);
			}
			else 
			{
					GameVariables.storytime = 0;
					GameVariables.help = 0;
					GameVariables.where = 0;
					ship_x = centerx - (ship.getHeight()/2);;
					ship_y = height-70;
					drawShip(canvas);
				
			}
		}
	}
	public void playBack(Canvas canvas) {
		GameVariables.story=15;
		totalSpeed = 0;
		if (y == height && gettingHit == 0 && shot_fired == 0){

			moveShip(GameVariables.controlMethod); // 2 options to move the ship in here		
		}
		else if (gettingHit == 0){
			//ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipfront);
			y = height;
			if (shot_fired == 0 && boosting > 195 && GameVariables.tutorial > 1){
				//x = ship_x;
				
				if (GameVariables.controlMethod == true) {
					GameVariables.shotfrom = ship_x;
					shot_fired = 1;
					shooting = 1;
					GameVariables.pressAt = GameVariables.volume;
				}
				//if using touch controls, shooting = 1 only of touch is top half of the screen
				else if (GameVariables.controlMethod == false){
					if (GameVariables.touchY < (height/2 -1)  ) {
						GameVariables.shotfrom = ship_x;
						//ship_x = x;
						xSpeed = 0;
						x = ship_x;
						ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipfront);
						shot_fired = 1;
						shooting = 1;
						GameVariables.pressAt = GameVariables.volume;
					}
					else {
						shot_fired = 0;
						shooting = 0;
					}
				}
				
			}
			else{
				GameVariables.tutorial++;
			}
			
		}
		
		drawBackground(canvas);
		drawBox(canvas);
		drawHUD(canvas);
		modifyBoxes();
		drawHelper(canvas);
		drawShip(canvas);
		//drawGlass(canvas);

		if (GameVariables.storytime >= 0 && GameVariables.storytime <= 600){
			story1 = BitmapFactory.decodeResource(getResources(), R.drawable.title16);
		}
		drawBackground(canvas);
		drawBox(canvas);
		drawHUD(canvas);
		modifyBoxes();
		drawHelper(canvas);
		drawShip(canvas);
		storyPaint.setColor(Color.BLACK);
		if (GameVariables.storytime >= 0 && GameVariables.storytime <= 2000){
			if (GameVariables.playBackLevel == 1){
				GameVariables.randFreq = 500;
			}
			else if (GameVariables.playBackLevel == GameVariables.level){
				GameVariables.randFreq = 14000;
			}
			else if (GameVariables.level == 3){
				if (GameVariables.playBackLevel == 2){
					GameVariables.randFreq = 5000;
				}
			}
			else if (GameVariables.level == 4){
				if (GameVariables.playBackLevel == 2){
					GameVariables.randFreq = 3000;
				}
				else if (GameVariables.playBackLevel == 3){
					GameVariables.randFreq = 10000;
				}
			}
			else if (GameVariables.level == 5){
				if (GameVariables.playBackLevel == 2){
					GameVariables.randFreq = 3000;
				}
				else if (GameVariables.playBackLevel == 3){
					GameVariables.randFreq = 5000;
				}
				else if (GameVariables.playBackLevel == 4){
					GameVariables.randFreq = 8000;
				}
			}
			else if (GameVariables.level == 6){
				if (GameVariables.playBackLevel == 2){
					GameVariables.randFreq = 1000;
				}
				else if (GameVariables.playBackLevel == 3){
					GameVariables.randFreq = 3000;
				}
				else if (GameVariables.playBackLevel == 4){
					GameVariables.randFreq = 5000;
				}
				else if (GameVariables.playBackLevel == 3){
					GameVariables.randFreq = 10000;
				}
			}
			else if (GameVariables.level == 7){
				if (GameVariables.playBackLevel == 2){
					GameVariables.randFreq = 1000;
				}
				else if (GameVariables.playBackLevel == 3){
					GameVariables.randFreq = 3000;
				}
				else if (GameVariables.playBackLevel == 4){
					GameVariables.randFreq = 5000;
				}
				else if (GameVariables.playBackLevel == 3){
					GameVariables.randFreq = 8000;
				}
				else if (GameVariables.playBackLevel == 4){
					GameVariables.randFreq = 10000;
				}
			}
			GameVariables.volume = 0;
			GameVariables.soundPlayed = 1;
			GameVariables.storytime = 2001;
		}
		else if (GameVariables.storytime >= 2001 && GameVariables.storytime <= 3000){
			if (storyAlpha <=240){
				storyAlpha += 10;
			}
			else{
				GameVariables.storytime = 3001;
			}
			storyPaint.setColor(0xFFFFFFFF);
			storyPaint.setStyle(Paint.Style.FILL);
			storyPaint.setAlpha(storyAlpha);
			helperLine.moveTo(GameVariables.divide[GameVariables.level]*(GameVariables.playBackLevel-1), height-1);
			helperLine.lineTo(centerx-100 + (200/GameVariables.level)*(GameVariables.playBackLevel-1), centery+100);
			helperLine.lineTo(centerx-100 + (200/GameVariables.level)*(GameVariables.playBackLevel), centery+100);
			helperLine.lineTo(GameVariables.divide[GameVariables.level]*(GameVariables.playBackLevel), height-1);
			canvas.drawPath(helperLine, storyPaint);
			helperLine.reset();
			if (GameVariables.soundPlayed == 0 && storyAlpha <= 240){
				GameVariables.storytime = 0;
				GameVariables.playBackLevel ++;
			}
		}
		else if (GameVariables.storytime >= 3001 && GameVariables.storytime <= 4000){
			if (storyAlpha >=10){
				storyAlpha -= 10;
			}
			else{
				GameVariables.storytime = 2001;
			}
			storyPaint.setColor(0xFFFFFFFF);
			//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
			storyPaint.setStyle(Paint.Style.FILL);
			storyPaint.setAlpha(storyAlpha);
			helperLine.moveTo(GameVariables.divide[GameVariables.level]*(GameVariables.playBackLevel-1), height-1);
			helperLine.lineTo(centerx-100 + (200/GameVariables.level)*(GameVariables.playBackLevel-1), centery+100);
			helperLine.lineTo(centerx-100 + (200/GameVariables.level)*(GameVariables.playBackLevel), centery+100);
			helperLine.lineTo(GameVariables.divide[GameVariables.level]*(GameVariables.playBackLevel), height-1);
			canvas.drawPath(helperLine, storyPaint);
			helperLine.reset();
			if (GameVariables.soundPlayed == 0 && storyAlpha >= 10){
				GameVariables.storytime = 0;
				GameVariables.playBackLevel ++;
			}
		}
		if (GameVariables.playBackLevel > GameVariables.level){
			GameVariables.playBack = 0;
			GameVariables.story = 0;

		}
		
		

		drawShip(canvas);

	}

	public void moveShip(boolean controlMethod) {
		
		//MOVE BY ACCELEROMETER
		if (controlMethod == true) {
			
			int length = mSensorX.length;
			int i = 0;
			float totalSensorX = 0f;
			while (i < length){
				totalSensorX += mSensorX[i] * 5f;
				i += 1;
			}
			xSpeed = (int) (totalSensorX / length);
			ship_x = (int) ship_x - xSpeed;
			if (xSpeed < 0) ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipright);
			else if (xSpeed > 0) ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipleft);
			else if (xSpeed == 0) ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipfront);
		}
		
		//MOVE BY TOUCH
		if (controlMethod == false && GameVariables.touchY > (height/2 + 1)) {
			if (x > ship_x+51) { // if tap is at right of robot
				
					//xSpeed = (-width/128); // if not arrived yet, increment to move right
					xSpeed = -10;
					ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipright);//T_T
					ship_x = (int) ship_x - xSpeed;	
				
			} 
			else if (x < ship_x-51) { // if tap is at left of robot
				
					//xSpeed = width/128; // if not arrived yet, move left
					xSpeed = 10;
					ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipleft);//T_T
					ship_x = (int) ship_x - xSpeed;
				
			}
			else{
				ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipfront);
				xSpeed = 0;
			}
		}
		
		
		//if (xSpeed < 0) ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipright);
		//else if (xSpeed > 0) ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipleft);
		//else if (xSpeed == 0) ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipfront);
		
		//ship_x = (int) ship_x - xSpeed;
		
		if (ship_x <= 0) {
			xSpeed = 0;
			ship_x = 0;
		}
		else if (ship_x >= (width-50)) {
			xSpeed = 0;
			ship_x = (width-50);
		}
		//if (ship_x >= (width-50)) xSpeed = 0; 
		totalSpeed+=xSpeed;
		centerx = centerx + totalSpeed/5;
	}

	protected void generateEnemy(Canvas canvas) {
		if ((GameVariables.isSummoned == 0 && (System.currentTimeMillis() - GameVariables.lastPlayed) > 4000) || GameVariables.story == 16){
			summontime=r.nextInt(100);
			if (GameVariables.story == 16){
				summontime = 50;
				GameVariables.tutorial = 1;
			}
			if (summontime == 50 && GameVariables.tutorial > 0){
				float calc = 0;
				GameVariables.duration = 1;
				count = 0;
				calc = r.nextInt(GameVariables.level);
				//GameVariables.randFreq = (int)(r.nextInt(17500/GameVariables.level) + calc*(17500/GameVariables.level) + 500);
				if (GameVariables.level == 2){
					if (calc == 0){
						GameVariables.gamecolor = 1;
						GameVariables.randFreq = 500;
					}
					else if (calc == 1){
						GameVariables.gamecolor = 7;
						GameVariables.randFreq = 14000;
					}
				}
				if (GameVariables.level == 3){
					if (calc == 0){
						GameVariables.gamecolor = 1;
						GameVariables.randFreq = 500;
					}
					else if (calc == 1){
						GameVariables.gamecolor = 4;
						GameVariables.randFreq = 5000;
					}
					else if (calc == 2){
						GameVariables.gamecolor = 7;
						GameVariables.randFreq = 14000;
					}
				}
				if (GameVariables.level == 4){
					if (calc == 0){
						GameVariables.gamecolor = 1;
						GameVariables.randFreq = 500;
					}
					else if (calc == 1){
						GameVariables.gamecolor = 3;
						GameVariables.randFreq = 3000;
					}
					else if (calc == 2){
						GameVariables.gamecolor = 6;
						GameVariables.randFreq = 10000;
					}
					else if (calc == 3){
						GameVariables.gamecolor = 7;
						GameVariables.randFreq = 14000;
					}
				}
				if (GameVariables.level == 5){
					if (calc == 0){
						GameVariables.gamecolor = 1;
						GameVariables.randFreq = 500;
					}
					else if (calc == 1){
						GameVariables.gamecolor = 3;
						GameVariables.randFreq = 3000;
					}
					else if (calc == 2){
						GameVariables.gamecolor = 4;
						GameVariables.randFreq = 5000;
					}
					else if (calc == 3){
						GameVariables.gamecolor = 6;
						GameVariables.randFreq = 8000;
					}
					else if (calc == 4){
						GameVariables.gamecolor = 7;
						GameVariables.randFreq = 14000;
					}
				}
				if (GameVariables.level == 6){
					if (calc == 0){
						GameVariables.gamecolor = 1;
						GameVariables.randFreq = 500;
					}
					else if (calc == 1){
						GameVariables.gamecolor = 2;
						GameVariables.randFreq = 1000;
					}
					else if (calc == 2){
						GameVariables.gamecolor = 3;
						GameVariables.randFreq = 3000;
					}
					else if (calc == 3){
						GameVariables.gamecolor = 4;
						GameVariables.randFreq = 5000;
					}
					else if (calc == 4){
						GameVariables.gamecolor = 6;
						GameVariables.randFreq = 10000;
					}
					else if (calc == 5){
						GameVariables.gamecolor = 7;
						GameVariables.randFreq = 14000;
					}
				}
				if (GameVariables.level == 7){
					if (calc == 0){
						GameVariables.gamecolor = 1;
						GameVariables.randFreq = 500;
					}
					else if (calc == 1){
						GameVariables.gamecolor = 2;
						GameVariables.randFreq = 1000;
					}
					else if (calc == 2){
						GameVariables.gamecolor = 3;
						GameVariables.randFreq = 3000;
					}
					else if (calc == 3){
						GameVariables.gamecolor = 4;
						GameVariables.randFreq = 5000;
					}
					else if (calc == 5){
						GameVariables.gamecolor = 5;
						GameVariables.randFreq = 8000;
					}
					else if (calc == 6){
						GameVariables.gamecolor = 6;
						GameVariables.randFreq = 10000;
					}
					else if (calc == 7){
						GameVariables.gamecolor = 7;
						GameVariables.randFreq = 14000;
					}
				}
				
				if (GameVariables.earMethod == 0){
					GameVariables.earTesting = 0;
				}
				else if (GameVariables.earMethod == 1){
					GameVariables.earTesting = 1;
				}
				else if (GameVariables.earMethod == 2){
					GameVariables.earTesting = 2;
				}
				else if (GameVariables.earMethod == 3){
					GameVariables.earTesting = r.nextInt(3);
				}
				//dimensions[0]=(width/7)*(GameVariables.randFreq);
				//dimensions[1]=(width/7)*(GameVariables.randFreq+1);
				dimensions[0]=((float)((float)GameVariables.randFreq/20000)*width)-50;
				dimensions[1]=((float)((float)GameVariables.randFreq/20000)*width)+50;
				GameVariables.enemyLocation[0] = width/GameVariables.level*calc;
				GameVariables.enemyLocation[1] = width/GameVariables.level*(calc+1);
				glassopa = 0;
				spawning = 0;
				//GameVariables.randFreq = (int)(r.nextInt(17500/GameVariables.level) + calc*(17500/GameVariables.level) + 500);

				GameVariables.glassStart = System.currentTimeMillis();
				GameVariables.volume = 0;
				GameVariables.isSummoned = 1;
				GameVariables.pressAt = 20;
			}
			else if (summontime == 50 && GameVariables.tutorial == 0){
				GameVariables.pause = 1;
			}
		}
	}
	
	protected void speedBoost(){
		if (boosting > 0){
			GameVariables.speedModifier = 8;
			boosting = boosting - 2;
		}
		else if (boosting == 0)
		{
			GameVariables.speedModifier = 2;
			shot_fired = 0;
			shooting = 0;
		}
	}
	
	protected void printResults(Canvas c){
		int i;
		String freq, time, vol;
		String score =  "";
		scorePaint.setColor(Color.WHITE);
		scorePaint.setStyle(Style.FILL);
		c.drawPaint(scorePaint);
		scorePaint.setColor(Color.BLACK);
		scorePaint.setTextSize(20);

		scorePaint.setTypeface(Typeface.SERIF);

		for (i = 0; i < GameVariables.currentscore; i++){
			freq = String.valueOf(GameVariables.score[i][0]);
			time = String.valueOf(GameVariables.score[i][1]);
			vol = String.valueOf(GameVariables.score[i][2]);
			score = score.concat("Hit ");
			score = score.concat(String.valueOf(i+1));
			score = score.concat("- Frequency: ");
			score = score.concat(freq);
			score = score.concat(" Time: ");
			score = score.concat(time);
			score = score.concat(" Volume: ");
			score = score.concat(vol);


			

			c.drawText(score, 10, 40 + i*40, scorePaint);
			score = "";
		}
		for (i = 0; i < 3; i++){
			freq = String.valueOf(GameVariables.miss[i]);
			score = score.concat("Miss ");
			score = score.concat(String.valueOf(i+1));
			score = score.concat("- Frequency: ");
			score = score.concat(freq);
			c.drawText(score, 10, 40 + GameVariables.currentscore* 40 + i*40, scorePaint);
			score = "";
		}
		
		//test code to see if I can modify global variables
		//GameVariables.xRaw[3] = 2000;
		//GameVariables.yRaw[3] = 90;
		
		//Code below starts a new activity (scores page)
		//resultsGraph then creates an GraphView object
		//setting that object as setContentView allows me to display the graph
		GameVariables.writeEnable = true;
		Context context = getContext(); // from MySurfaceView/Activity
		Intent intent_scores = new Intent(context, resultsGraph.class);
		context.startActivity(intent_scores);
	    gameLoopThread.setRunning(false);
        gameLoopThread.interrupt();
       
		
	}
	public static void recycleAllBitmaps() {
		
		bg.recycle();
		ship.recycle();
		heartfull.recycle();
		heartempty.recycle();
		redhit.recycle();
		glass.recycle();
		crash.recycle();
		lefthud.recycle();
		righthud.recycle();
		midhud.recycle();
		warninggrey.recycle();
		warningred.recycle();
		thousand.recycle();
		hundred.recycle();
		ten.recycle();
		tut.recycle();
		
		bg = null;
		ship = null;
		heartfull = null;
		heartempty = null;
		redhit = null;
		glass = null;
		crash = null;
		lefthud = null;
		righthud = null;
		midhud = null;
		thousand = null;
		hundred = null;
		ten = null;
		tut = null;
		
	}



	/*
	 * //moving the character if (x > getWidth() - bmp.getWidth()) { //if
	 * arrived at right border xSpeed = -1; } if (x == 0) { //if arrived at left
	 * border xSpeed = 1; }
	 */

	public boolean onTouchEvent(MotionEvent event) {
		// get touched coordinates
		GameVariables.touchY = (int) event.getY();
		
		if (GameVariables.help == 1 && GameVariables.story == 10)
		{
			shot_fired = 1;
		}
		
		if (GameVariables.pause == 1){// unpause game by touching screen
			GameVariables.pause = 0;
			shot_fired = 0;
			shooting = 0;
			GameVariables.tutorial++;
			return true;
		}
		
		if (shooting == 0){
			y = (int)event.getY();
			if (GameVariables.controlMethod == true){
				x = (int)event.getX();
			}		
			else if (GameVariables.controlMethod == false){
				if (GameVariables.touchY > (height/2 + 1)) { //only update x coordinate if touchY is lower half of the screen
					x = (int)event.getX();
				}
			}
		}
		
		
		if (GameVariables.isRunning == 0){
			reboot++;
			if (reboot == 2){
				reboot = 0;
				GameVariables.lives = 4;
			}
		}
		
		// set boundaries
		/*if (x < 1)
			x = 0;
		if (x > width)
			x = width;
		if (y < 1)
			y = 0;
		if (y > height)
			y = height;
		*/
		return true;
	}
	
	public void drawShip (Canvas canvas){
		canvas.drawBitmap(ship, ship_x-10, ship_y, null);
	}
	
	public void drawTutorial (Canvas canvas){
		
		Paint help = new Paint();
		help.setAlpha(125);
		canvas.drawRect(0,0,width,height, help);
		tut = BitmapFactory.decodeResource(getResources(), R.drawable.lefthelp);
		canvas.drawBitmap(tut, 0, -5, null);
		tut = BitmapFactory.decodeResource(getResources(), R.drawable.midhelp);
		canvas.drawBitmap(tut, width/2 - tut.getWidth()/2, heartfull.getHeight() + 20, null);
		tut = BitmapFactory.decodeResource(getResources(), R.drawable.righthelp);
		canvas.drawBitmap(tut, width - tut.getWidth(), -5, null);
		tut = BitmapFactory.decodeResource(getResources(), R.drawable.shiphelp);
		canvas.drawBitmap(tut, ship_x - tut.getWidth()/2 + ship.getWidth()/2, ship_y - tut.getHeight() - 5, null);
		tut = BitmapFactory.decodeResource(getResources(), R.drawable.lowhelp);
		canvas.drawBitmap(tut, 0, height - 30 - tut.getHeight(), null);
		tut = BitmapFactory.decodeResource(getResources(), R.drawable.highhelp);
		canvas.drawBitmap(tut, width - tut.getWidth(), height - 30 - tut.getHeight(), null);
		tut = BitmapFactory.decodeResource(getResources(), R.drawable.taphelp);
		canvas.drawBitmap(tut, width/2 - tut.getWidth()/2, height - 35, null);
	}
	
	public void drawGlass (Canvas canvas){
		Paint glasspaint = new Paint();
		Path glassline = new Path();
		int glassopa2 = 0;
		int glassopa3 = 0;
		
		if (spawning == 0){
			GameVariables.lastPlayed = System.currentTimeMillis() - 1000;
			GameVariables.soundPlayed = 1;
		}
		if (spawning >= 0 && spawning < 25){
			glassopa = glassopa + 5*GameVariables.speedModifier;
		}
		else if (spawning >= 25 && spawning < 50){
			glassopa = glassopa - 5*GameVariables.speedModifier;
		}
		else if (spawning >= 50 && spawning < 75){
			glassopa = glassopa + 5*GameVariables.speedModifier;
		}
		else if (spawning >= 75 && spawning < 100){
			glassopa = glassopa - 5*GameVariables.speedModifier;
		}
		else if (spawning >= 100){
			if (glassopa < 250){
				glassopa = glassopa + 5*GameVariables.speedModifier;
			}
		}
		if (spawning > 100){
			GameVariables.lastPlayed = System.currentTimeMillis() - 1000;
			GameVariables.soundPlayed = 1;
		}
		if (spawning < 120){
			glasspaint.setAlpha(glassopa*2);
			canvas.drawBitmap(warningred, width-righthud.getWidth(), 15, glasspaint);
		}
		else if (spawning > 120){
			
			glasspaint.setAlpha(glassopa);
			canvas.drawBitmap(warningred, width-righthud.getWidth(), 15, glasspaint);
		}
		if (spawning > 120){
			count = count + 2*GameVariables.speedModifier;
			if ((centerx - 95 - count*(centerx-95)/(centery-95)) < width/4 || (centerx+95 + count*(width-centerx-95)/(height-centery-95)) > 3*width/4){
				count = count + 2*GameVariables.speedModifier;
			}
			//glassnew = Bitmap.createScaledBitmap(glass, 200 + (spawning-120)*5, 200 + (spawning-120)*5, false);
			//glassnew = Bitmap.createScaledBitmap(glass, 500, 500, false);

			//glassnew = Bitmap.createBitmap(glassnew, glassnew.getWidth()/2 - width/2, glass.getHeight()/2 - height/2, width, height);
			//if (glassnew.getWidth() > width || glassnew.getHeight() > height){
				//glassnew = Bitmap.createScaledBitmap(glass, 200, 200, false);
			//}
		}
		if (spawning > 175){
			//glassnew = Bitmap.createScaledBitmap(glass, 200 + (spawning-175)*, 200 + (spawning-175)*200, false);
				glassopa3 = (spawning-175);
			
			//if (glassnew.getWidth() > width || glassnew.getHeight() > height){
				//glassnew = Bitmap.createScaledBitmap(glass, 200, 200, false);
			//}
		}
		glasspaint.setColor(Color.WHITE);
		glasspaint.setStyle(Paint.Style.STROKE);
		glasspaint.setAlpha(glassopa);
		canvas.drawRect(centerx - 100 - count*(centerx-100)/(centery-100), centery - 100 - count,centerx+100 + count*(width-centerx-100)/(height-centery-100),centery+100+count,glasspaint);
		if (glassopa <= 100){
			glassopa2 = 0;
		}
		else if (glassopa <= 200){
			glassopa2 = glassopa - 100;
		}
		else {
			glassopa2 = 100;
		}
		
		glasspaint.setStyle(Paint.Style.FILL);
		glasspaint.setAlpha(glassopa2);
		canvas.drawRect(centerx - 95 - count*(centerx-95)/(centery-95), centery - 95 - count,centerx+95 + count*(width-centerx-95)/(height-centery-95),centery+95+count,glasspaint);
		//canvas.drawBitmap(glassnew, centerx - (glassnew.getWidth()/2), centery - (glassnew.getHeight()/2), glasspaint);
		
		if (spawning > 120){
			if (GameVariables.gamecolor == 1){
				glasspaint.setColor(0xFF0000FF);
			}
			else if (GameVariables.gamecolor == 2){
				glasspaint.setColor(0xFF0099FF);
			}
			else if (GameVariables.gamecolor == 3){
				glasspaint.setColor(0xFF00FFFF);
			}
			else if (GameVariables.gamecolor == 4){
				glasspaint.setColor(0xFF00FF00);
			}
			else if (GameVariables.gamecolor == 5){
				glasspaint.setColor(0xFFFF9900);
			}
			else if (GameVariables.gamecolor == 6){
				glasspaint.setColor(0xFFFFFF00);
			}
			else if (GameVariables.gamecolor == 7){
				glasspaint.setColor(0xFFFF0000);
			}
			glasspaint.setStyle(Paint.Style.FILL);
			glasspaint.setAlpha(glassopa3);
			canvas.drawRect(centerx - 95 - count*(centerx-95)/(centery-95), centery - 95 - count,centerx+95 + count*(width-centerx-95)/(height-centery-95),centery+95+count,glasspaint);
		}
		
		if (((centerx - 95 - count*(centerx-95)/(centery-95)) < 0) || (centerx+95 + count*(width-centerx-95)/(height-centery-95)) > width){
			if (GameVariables.isSummoned == 1 && GameVariables.story != 16){
				if ((ship_x > GameVariables.enemyLocation[0]) && (ship_x < GameVariables.enemyLocation[1])){
					GameVariables.score[GameVariables.currentscore][0] = GameVariables.randFreq;
					GameVariables.score[GameVariables.currentscore][1] = (System.currentTimeMillis() - GameVariables.enemyAppear);
					GameVariables.score[GameVariables.currentscore][2] = GameVariables.volume;
					GameVariables.xRaw[GameVariables.currentscore] = GameVariables.randFreq;
					GameVariables.yRaw[GameVariables.currentscore] = GameVariables.volume;
					GameVariables.earRaw[GameVariables.currentscore] = GameVariables.earTesting; 
					//GameVariables.yRaw[GameVariables.currentscore] = (2000 - GameVariables.volume*100);
					GameVariables.currentscore++;
					GameVariables.isSummoned = 0;
        			GameVariables.soundPlayed = 0;
        			GameVariables.levelchange --;
        			GameVariables.scoretime += (21 - GameVariables.pressAt); 
        			GameVariables.scoretime += 10;
        			updateScore();

        					//(GameVariables.pressAt - GameVariables.volume);

				}
				else{
					GameVariables.isSummoned = 0;
					GameVariables.soundPlayed = 0;
					GameVariables.lives--;
					GameVariables.miss[GameVariables.currentmiss] = GameVariables.randFreq;
					GameVariables.currentmiss++;
					GameVariables.hit = 1;
				}
			}
			else if (GameVariables.isSummoned == 1 && GameVariables.story == 16){
				if ((ship_x > GameVariables.enemyLocation[0]) && (ship_x < GameVariables.enemyLocation[1])){
					GameVariables.storytime = 30000;
					GameVariables.isSummoned = 0;
        			GameVariables.soundPlayed = 0;
				}
				else{
					GameVariables.storytime = 20000;
					GameVariables.isSummoned = 0;
        			GameVariables.soundPlayed = 0;
				}
			}
			
			//canvas.drawBitmap(crash, ship_x - (ship.getWidth()/2), ship_y - (ship.getHeight()/2), null);
		}
		//canvas.drawBitmap(glassnew, centerx - (glassnew.getWidth()/2), centery - (glassnew.getHeight()/2), null);
		spawning+= 1*GameVariables.speedModifier;
		
	}
	public void drawRedHit(Canvas canvas){
		Paint red = new Paint();
		red.setAlpha(opa);
		canvas.drawBitmap(redhit, 0, 0, red);
		if (gettingHit <= 25){
			opa = opa + 5;
		}
		else if (gettingHit < 50){
			opa = opa - 5;
		}
	}
	public void drawHit(){
		if (gettingHit == 0){
			lastcenterx = centerx;
			lastcentery = centery;
			lastshipx = ship_x;
			lastshipy = ship_y;
		}
		int rand = r.nextInt(4);
		if (gettingHit > 50){
			gettingHit++;
			if (centerx < lastcenterx){
				centerx = centerx + 20;
				ship_x = ship_x + 5;
			}
			else if (centerx > lastcenterx){
				centerx = centerx - 20;
				ship_x = ship_x - 5;
			}
			if (centery > lastcentery){
				centery = centery - 20;
				ship_y = ship_y - 5;
			}
			else if (centery < lastcentery){
				centery = centery + 20;
				ship_y = ship_y + 5;
			}
			if (centerx == lastcenterx && centery == lastcentery)
			{
				opa = 0;
				gettingHit = 0;
			    GameVariables.hit = 0;
				if (GameVariables.lives == 0){
					GameVariables.lives = -1;
				}
			}
		}
		else if (rand == 0){
			centerx = centerx - 20;
			ship_x = ship_x -5;
			gettingHit++;
		}
		else if (rand == 1){
			centerx = centerx + 20;
			ship_x = ship_x + 5;
			gettingHit++;
		}
		else if (rand == 2){
			centery = centery - 20;
			ship_y = ship_y - 5;
			gettingHit++;
		}
		else if (rand == 3){
			centery = centery + 20;
			ship_y = ship_y + 5;
			gettingHit++;
		}
	}
	
	public void drawHUD(Canvas canvas){
		int i;
		int heartx, hearty;
		Paint HUDpaint = new Paint();
		HUDpaint.setAlpha(100);
		
		canvas.drawBitmap(lefthud, 0, 20, HUDpaint);
		canvas.drawBitmap(righthud, width-righthud.getWidth(), 20, HUDpaint);
		canvas.drawBitmap(warninggrey, width-righthud.getWidth(), 15, HUDpaint);

		for (i=lefthud.getWidth(); i < width-righthud.getWidth()-midhud.getWidth(); i+=midhud.getWidth()){
			canvas.drawBitmap(midhud, i, 20, HUDpaint);
		}
		canvas.drawBitmap(midhud, width-righthud.getWidth()-midhud.getWidth(), 20, HUDpaint);
		

		
		heartx = width/2 - heartfull.getWidth()/2 - 10 - heartfull.getWidth();
		hearty = 25;
		if (heartpaint.getAlpha() >= 244){
			heartopa = 1;
		}
		else if (heartpaint.getAlpha() <= 11){
			heartopa = 0;
		}
		if (heartopa == 1){
			heartpaint.setAlpha(heartpaint.getAlpha()-10);
		}
		else if (heartopa == 0){
			heartpaint.setAlpha(heartpaint.getAlpha()+10);
		}
		for (i = 0; i < GameVariables.lives; i++){
			canvas.drawBitmap(heartfull, heartx, hearty, heartpaint);
			heartx = heartx + heartfull.getWidth() + 10;
		}
		heartx = width/2 - heartfull.getWidth()/2 - 10 - heartfull.getWidth();
		for (i = 0; i < 3; i++){
			canvas.drawBitmap(heartempty, heartx, hearty, null);
			heartx = heartx + heartfull.getWidth() + 10;
		}
		

		HUDpaint.setAlpha(175);

		int HUDcolor = 0xFFFFFF00; 

		HUDpaint.setColor(HUDcolor);
		
		for (i = 0; i < boosting*width/392; i+=2){
			canvas.drawRect(width/2 + i, 1, width/2 + i + 1 , 19, HUDpaint);
			canvas.drawRect(width/2 - i, 1, width/2 - i - 1 , 19, HUDpaint);
			HUDpaint.setColor(HUDcolor);
			if (HUDcolor != 0xFFFF0000){
				HUDcolor = HUDcolor - (1 << 8);
			}
		}		
		HUDpaint.setAlpha(100);
		HUDcolor = 0xFFCCFFFF; 
		HUDpaint.setColor(HUDcolor);
		HUDpaint.setStyle(Style.STROKE);

		canvas.drawBitmap(thousand, 0, 20, null);
		canvas.drawBitmap(hundred, 0, 20, null);
		canvas.drawBitmap(ten, 0, 20, null);


		
	}
	
	public void updateScore(){
		
			int thousands = (GameVariables.scoretime/100) % 10;
			int hundreds = (GameVariables.scoretime/10) % 10;
			int tens = (GameVariables.scoretime) % 10;
			long time = System.currentTimeMillis()%10;
			switch(tens){
			case 0:
				 ten = BitmapFactory.decodeResource(getResources(), R.drawable.tenzero);
				 break;
			case 1:
				 ten = BitmapFactory.decodeResource(getResources(), R.drawable.tenone);
				 break;
			case 2:
				 ten = BitmapFactory.decodeResource(getResources(), R.drawable.tentwo);
				 break;
			case 3:
				 ten = BitmapFactory.decodeResource(getResources(), R.drawable.tenthree);
				 break;
			case 4:
				 ten = BitmapFactory.decodeResource(getResources(), R.drawable.tenfour);
				 break;
			case 5:
				 ten = BitmapFactory.decodeResource(getResources(), R.drawable.tenfive);
				 break;
			case 6:
				 ten = BitmapFactory.decodeResource(getResources(), R.drawable.tensix);
				 break;
			case 7:
				 ten = BitmapFactory.decodeResource(getResources(), R.drawable.tenseven);
				 break;
			case 8:
				 ten = BitmapFactory.decodeResource(getResources(), R.drawable.teneight);
				 break;
			case 9:
				 ten = BitmapFactory.decodeResource(getResources(), R.drawable.tennine);
				 break;					 
		}
				switch(hundreds){
					case 0:
						 hundred = BitmapFactory.decodeResource(getResources(), R.drawable.hundredzero);
						 break;
					case 1:
						 hundred = BitmapFactory.decodeResource(getResources(), R.drawable.hundredone);
						 break;
					case 2:
						 hundred = BitmapFactory.decodeResource(getResources(), R.drawable.hundredtwo);
						 break;
					case 3:
						 hundred = BitmapFactory.decodeResource(getResources(), R.drawable.hundredthree);
						 break;
					case 4:
						 hundred = BitmapFactory.decodeResource(getResources(), R.drawable.hundredfour);
						 break;
					case 5:
						 hundred = BitmapFactory.decodeResource(getResources(), R.drawable.hundredfive);
						 break;
					case 6:
						 hundred = BitmapFactory.decodeResource(getResources(), R.drawable.hundredsix);
						 break;
					case 7:
						 hundred = BitmapFactory.decodeResource(getResources(), R.drawable.hundredseven);
						 break;
					case 8:
						 hundred = BitmapFactory.decodeResource(getResources(), R.drawable.hundredeight);
						 break;
					case 9:
						 hundred = BitmapFactory.decodeResource(getResources(), R.drawable.hundrednine);
						 break;					 
				}

					switch(thousands){
						case 0:
							 thousand = BitmapFactory.decodeResource(getResources(), R.drawable.thousandzero);
							 break;
						case 1:
							 thousand = BitmapFactory.decodeResource(getResources(), R.drawable.thousandone);
							 break;
						case 2:
							 thousand = BitmapFactory.decodeResource(getResources(), R.drawable.thousandtwo);
							 break;
						case 3:
							 thousand = BitmapFactory.decodeResource(getResources(), R.drawable.thousandthree);
							 break;
						case 4:
							 thousand = BitmapFactory.decodeResource(getResources(), R.drawable.thousandfour);
							 break;
						case 5:
							 thousand = BitmapFactory.decodeResource(getResources(), R.drawable.thousandfive);
							 break;
						case 6:
							 thousand = BitmapFactory.decodeResource(getResources(), R.drawable.thousandsix);
							 break;
						case 7:
							 thousand = BitmapFactory.decodeResource(getResources(), R.drawable.thousandseven);
							 break;
						case 8:
							 thousand = BitmapFactory.decodeResource(getResources(), R.drawable.thousandeight);
							 break;
						case 9:
							 thousand = BitmapFactory.decodeResource(getResources(), R.drawable.thousandnine);
							 break;					 
					}
				
		



	}
	public void drawBackground(Canvas canvas){
		middle.setColor(Color.BLACK);
		edges.setColor(Color.BLACK);
		lines.moveTo(0, 0);
		lines.lineTo(width, 0);
		lines.lineTo(width, height);
		lines.lineTo(0, height);
		lines.close();
		edges.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawPath(lines, edges);
		edges.setColor(Color.BLACK);
		lines.reset();
		lines.moveTo(centerx-100, centery+100);
		lines.lineTo(0, height);
		lines.lineTo(width, height);
		lines.lineTo(centerx+100, centery+100);
		canvas.drawPath(lines, edges);
		canvas.drawRect((float)centerx-100, (float)centery-100, (float)centerx+100, (float)centery+100, middle);
		lines.reset();
		lines.moveTo(0, 0);
		lines.lineTo(centerx-100,centery-100);
		edges.setStyle(Paint.Style.STROKE);
		edges.setColor(Color.BLACK);
		canvas.drawPath(lines, edges);
		lines.reset();
		lines.moveTo(0, height);
		lines.lineTo(centerx-100,centery+100);
		canvas.drawPath(lines, edges);
		lines.reset();
		lines.moveTo(width, 0);
		lines.lineTo(centerx+100,centery-100);
		canvas.drawPath(lines, edges);
		lines.reset();
		lines.moveTo(width, height);
		lines.lineTo(centerx+100,centery+100);
		canvas.drawPath(lines, edges);

	}
	

public void drawHelper(Canvas canvas){
		
		helperPaint.setStyle(Paint.Style.FILL);
		if (GameVariables.level == 2){
			helperPaint.setColor(0xFF0000FF);
			//canvas.drawRect(0, height-20, GameVariables.divide[2], height, middle);

			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);

			helperLine.moveTo(0,height-1);
			helperLine.lineTo(centerx-100 + (200/2)*0, centery+100);
			helperLine.lineTo(centerx-100 + (200/2)*1, centery+100);
			helperLine.lineTo(GameVariables.divide[2], height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFFFF0000);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[2],height-1);
			helperLine.lineTo(centerx-100 + (200/2)*1, centery+100);
			helperLine.lineTo(centerx-100 + (200/2)*2, centery+100);
			helperLine.lineTo(GameVariables.divide[2]*2, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			//canvas.drawRect(GameVariables.divide[2], height-20, 2*GameVariables.divide[2], height, middle);
		}
		else if (GameVariables.level == 3){
			helperPaint.setColor(0xFF0000FF);
			//canvas.drawRect(0, height-20, GameVariables.divide[3], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);

			helperLine.moveTo(0,height-1);
			helperLine.lineTo(centerx-100 + (200/3)*0, centery+100);
			helperLine.lineTo(centerx-100 + (200/3)*1, centery+100);
			helperLine.lineTo(GameVariables.divide[3], height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
		
			helperPaint.setColor(0xFF00FF00);
			//canvas.drawRect(GameVariables.divide[3], height-20, 2*GameVariables.divide[3], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[3], height-1);
			helperLine.lineTo(centerx-100 + (200/3)*1, centery+100);
			helperLine.lineTo(centerx-100 + (200/3)*2, centery+100);
			helperLine.lineTo(GameVariables.divide[3]*2, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			
			helperPaint.setColor(0xFFFF0000);
			//canvas.drawRect(2*GameVariables.divide[3], height-20, 3*GameVariables.divide[3], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[3]*2, height-1);
			helperLine.lineTo(centerx-100 + (200/3)*2, centery+100);
			helperLine.lineTo(centerx-100 + (200/3)*3, centery+100);
			helperLine.lineTo(GameVariables.divide[3]*3, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
		}
		else if (GameVariables.level == 4){
			helperPaint.setColor(0xFF0000FF);
			//canvas.drawRect(0, height-20, GameVariables.divide[4], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[4]*0, height-1);
			helperLine.lineTo(centerx-100 + (200/4)*0, centery+100);
			helperLine.lineTo(centerx-100 + (200/4)*1, centery+100);
			helperLine.lineTo(GameVariables.divide[4]*1, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFF00FFFF);
			//canvas.drawRect(GameVariables.divide[4], height-20, 2*GameVariables.divide[4], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[4]*1, height-1);
			helperLine.lineTo(centerx-100 + (200/4)*1, centery+100);
			helperLine.lineTo(centerx-100 + (200/4)*2, centery+100);
			helperLine.lineTo(GameVariables.divide[4]*2, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFFFFFF00);
			//canvas.drawRect(2*GameVariables.divide[4], height-20, 3*GameVariables.divide[4], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[4]*2, height-1);
			helperLine.lineTo(centerx-100 + (200/4)*2, centery+100);
			helperLine.lineTo(centerx-100 + (200/4)*3, centery+100);
			helperLine.lineTo(GameVariables.divide[4]*3, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFFFF0000);
			//canvas.drawRect(3*GameVariables.divide[4], height-20, 4*GameVariables.divide[4], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[4]*3, height-1);
			helperLine.lineTo(centerx-100 + (200/4)*3, centery+100);
			helperLine.lineTo(centerx-100 + (200/4)*4, centery+100);
			helperLine.lineTo(GameVariables.divide[4]*4, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
		}
		else if (GameVariables.level == 5){
			helperPaint.setColor(0xFF0000FF);
			canvas.drawRect(0, height-20, GameVariables.divide[5], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[5]*0, height-1);
			helperLine.lineTo(centerx-100 + (200/5)*0, centery+100);
			helperLine.lineTo(centerx-100 + (200/5)*1, centery+100);
			helperLine.lineTo(GameVariables.divide[5]*1, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFF00FFFF);
			//canvas.drawRect(GameVariables.divide[5], height-20, 2*GameVariables.divide[5], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[5]*1, height-1);
			helperLine.lineTo(centerx-100 + (200/5)*1, centery+100);
			helperLine.lineTo(centerx-100 + (200/5)*2, centery+100);
			helperLine.lineTo(GameVariables.divide[5]*2, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFF00FF00);
			//canvas.drawRect(2*GameVariables.divide[5], height-20, 3*GameVariables.divide[5], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[5]*2, height-1);
			helperLine.lineTo(centerx-100 + (200/5)*2, centery+100);
			helperLine.lineTo(centerx-100 + (200/5)*3, centery+100);
			helperLine.lineTo(GameVariables.divide[5]*3, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFFFFFF00);
			//canvas.drawRect(3*GameVariables.divide[5], height-20, 4*GameVariables.divide[5], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[5]*3, height-1);
			helperLine.lineTo(centerx-100 + (200/5)*3, centery+100);
			helperLine.lineTo(centerx-100 + (200/5)*4, centery+100);
			helperLine.lineTo(GameVariables.divide[5]*4, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFFFF0000);
			//canvas.drawRect(4*GameVariables.divide[5], height-20, 5*GameVariables.divide[5], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[5]*4, height-1);
			helperLine.lineTo(centerx-100 + (200/5)*4, centery+100);
			helperLine.lineTo(centerx-100 + (200/5)*5, centery+100);
			helperLine.lineTo(GameVariables.divide[5]*5, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
		}
		else if (GameVariables.level == 6){
			helperPaint.setColor(0xFF0000FF);
			//canvas.drawRect(0, height-20, GameVariables.divide[6], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[6]*0, height-1);
			helperLine.lineTo(centerx-100 + (200/6)*0, centery+100);
			helperLine.lineTo(centerx-100 + (200/6)*1, centery+100);
			helperLine.lineTo(GameVariables.divide[6]*1, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFF0099FF);
			//canvas.drawRect(GameVariables.divide[6], height-20, 2*GameVariables.divide[6], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[6]*1, height-1);
			helperLine.lineTo(centerx-100 + (200/6)*1, centery+100);
			helperLine.lineTo(centerx-100 + (200/6)*2, centery+100);
			helperLine.lineTo(GameVariables.divide[6]*2, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFF00FFFF);
			//canvas.drawRect(2*GameVariables.divide[6], height-20, 3*GameVariables.divide[6], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[6]*2, height-1);
			helperLine.lineTo(centerx-100 + (200/6)*2, centery+100);
			helperLine.lineTo(centerx-100 + (200/6)*3, centery+100);
			helperLine.lineTo(GameVariables.divide[6]*3, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFF00FF00);
			//canvas.drawRect(3*GameVariables.divide[6], height-20, 4*GameVariables.divide[6], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[6]*3, height-1);
			helperLine.lineTo(centerx-100 + (200/6)*3, centery+100);
			helperLine.lineTo(centerx-100 + (200/6)*4, centery+100);
			helperLine.lineTo(GameVariables.divide[6]*4, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFFFFFF00);
			//canvas.drawRect(4*GameVariables.divide[6], height-20, 5*GameVariables.divide[6], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[6]*4, height-1);
			helperLine.lineTo(centerx-100 + (200/6)*4, centery+100);
			helperLine.lineTo(centerx-100 + (200/6)*5, centery+100);
			helperLine.lineTo(GameVariables.divide[6]*5, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFFFF0000);
			//canvas.drawRect(5*GameVariables.divide[6], height-20, 6*GameVariables.divide[6], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[6]*5, height-1);
			helperLine.lineTo(centerx-100 + (200/6)*5, centery+100);
			helperLine.lineTo(centerx-100 + (200/6)*6, centery+100);
			helperLine.lineTo(GameVariables.divide[6]*6, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
		}
		else if (GameVariables.level == 7){
			helperPaint.setColor(0xFF0000FF);
			//canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[7]*0, height-1);
			helperLine.lineTo(centerx-100 + (200/7)*0, centery+100);
			helperLine.lineTo(centerx-100 + (200/7)*1, centery+100);
			helperLine.lineTo(GameVariables.divide[7]*1, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFF0099FF);
			//canvas.drawRect(GameVariables.divide[7], height-20, 2*GameVariables.divide[7], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[7]*1, height-1);
			helperLine.lineTo(centerx-100 + (200/7)*1, centery+100);
			helperLine.lineTo(centerx-100 + (200/7)*2, centery+100);
			helperLine.lineTo(GameVariables.divide[7]*2, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFF00FFFF);
			//canvas.drawRect(2*GameVariables.divide[7], height-20, 3*GameVariables.divide[7], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[7]*2, height-1);
			helperLine.lineTo(centerx-100 + (200/7)*2, centery+100);
			helperLine.lineTo(centerx-100 + (200/7)*3, centery+100);
			helperLine.lineTo(GameVariables.divide[7]*3, height-1);			
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFF00FF00);
			//canvas.drawRect(3*GameVariables.divide[7], height-20, 4*GameVariables.divide[7], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[7]*3, height-1);
			helperLine.lineTo(centerx-100 + (200/7)*3, centery+100);
			helperLine.lineTo(centerx-100 + (200/7)*4, centery+100);
			helperLine.lineTo(GameVariables.divide[7]*4, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFFFFFF00);
			//canvas.drawRect(4*GameVariables.divide[7], height-20, 5*GameVariables.divide[7], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[7]*4, height-1);
			helperLine.lineTo(centerx-100 + (200/7)*4, centery+100);
			helperLine.lineTo(centerx-100 + (200/7)*5, centery+100);
			helperLine.lineTo(GameVariables.divide[7]*5, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFFFF9900);
			//canvas.drawRect(5*GameVariables.divide[7], height-20, 6*GameVariables.divide[7], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[7]*5, height-1);
			helperLine.lineTo(centerx-100 + (200/7)*5, centery+100);
			helperLine.lineTo(centerx-100 + (200/7)*6, centery+100);
			helperLine.lineTo(GameVariables.divide[7]*6, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
			helperPaint.setColor(0xFFFF0000);
			//canvas.drawRect(6*GameVariables.divide[7], height-20, 7*GameVariables.divide[7], height, middle);
			helperPaint.setStyle(Paint.Style.FILL);
			helperPaint.setAlpha(100);
			helperLine.moveTo(GameVariables.divide[7]*6, height-1);
			helperLine.lineTo(centerx-100 + (200/7)*6, centery+100);
			helperLine.lineTo(centerx-100 + (200/7)*7, centery+100);
			helperLine.lineTo(GameVariables.divide[7]*7, height-1);
			canvas.drawPath(helperLine, helperPaint);
			helperLine.reset();
		}
	}
	/*
	public void drawHelper(Canvas canvas){
		middle.setStyle(Paint.Style.FILL);
		if (GameVariables.level == 2){
			middle.setColor(0xFF0000FF);
			canvas.drawRect(0, height-20, GameVariables.divide[2], height, middle);
			middle.setColor(0xFFFF0000);
			canvas.drawRect(GameVariables.divide[2], height-20, 2*GameVariables.divide[2], height, middle);
		}
		else if (GameVariables.level == 3){
			middle.setColor(0xFF0000FF);
			canvas.drawRect(0, height-20, GameVariables.divide[3], height, middle);
			middle.setColor(0xFF00FF00);
			canvas.drawRect(GameVariables.divide[3], height-20, 2*GameVariables.divide[3], height, middle);
			middle.setColor(0xFFFF0000);
			canvas.drawRect(2*GameVariables.divide[3], height-20, 3*GameVariables.divide[3], height, middle);
		}
		else if (GameVariables.level == 4){
			middle.setColor(0xFF0000FF);
			canvas.drawRect(0, height-20, GameVariables.divide[4], height, middle);
			middle.setColor(0xFF00FFFF);
			canvas.drawRect(GameVariables.divide[4], height-20, 2*GameVariables.divide[4], height, middle);
			middle.setColor(0xFFFFFF00);
			canvas.drawRect(2*GameVariables.divide[4], height-20, 3*GameVariables.divide[4], height, middle);
			middle.setColor(0xFFFF0000);
			canvas.drawRect(3*GameVariables.divide[4], height-20, 4*GameVariables.divide[4], height, middle);
		}
		else if (GameVariables.level == 5){
			middle.setColor(0xFF0000FF);
			canvas.drawRect(0, height-20, GameVariables.divide[5], height, middle);
			middle.setColor(0xFF00FFFF);
			canvas.drawRect(GameVariables.divide[5], height-20, 2*GameVariables.divide[5], height, middle);
			middle.setColor(0xFF00FF00);
			canvas.drawRect(2*GameVariables.divide[5], height-20, 3*GameVariables.divide[5], height, middle);
			middle.setColor(0xFFFFFF00);
			canvas.drawRect(3*GameVariables.divide[5], height-20, 4*GameVariables.divide[5], height, middle);
			middle.setColor(0xFFFF0000);
			canvas.drawRect(4*GameVariables.divide[5], height-20, 5*GameVariables.divide[5], height, middle);
		}
		else if (GameVariables.level == 6){
			middle.setColor(0xFF0000FF);
			canvas.drawRect(0, height-20, GameVariables.divide[6], height, middle);
			middle.setColor(0xFF0099FF);
			canvas.drawRect(GameVariables.divide[6], height-20, 2*GameVariables.divide[6], height, middle);
			middle.setColor(0xFF00FFFF);
			canvas.drawRect(2*GameVariables.divide[6], height-20, 3*GameVariables.divide[6], height, middle);
			middle.setColor(0xFF00FF00);
			canvas.drawRect(3*GameVariables.divide[6], height-20, 4*GameVariables.divide[6], height, middle);
			middle.setColor(0xFFFFFF00);
			canvas.drawRect(4*GameVariables.divide[6], height-20, 5*GameVariables.divide[6], height, middle);
			middle.setColor(0xFFFF0000);
			canvas.drawRect(5*GameVariables.divide[6], height-20, 6*GameVariables.divide[6], height, middle);
		}
		else if (GameVariables.level == 7){
			middle.setColor(0xFF0000FF);
			canvas.drawRect(0, height-20, GameVariables.divide[7], height, middle);
			middle.setColor(0xFF0099FF);
			canvas.drawRect(GameVariables.divide[7], height-20, 2*GameVariables.divide[7], height, middle);
			middle.setColor(0xFF00FFFF);
			canvas.drawRect(2*GameVariables.divide[7], height-20, 3*GameVariables.divide[7], height, middle);
			
			middle.setColor(0xFF00FF00);
			canvas.drawRect(3*GameVariables.divide[7], height-20, 4*GameVariables.divide[7], height, middle);
			middle.setColor(0xFFFFFF00);
			canvas.drawRect(4*GameVariables.divide[7], height-20, 5*GameVariables.divide[7], height, middle);
			middle.setColor(0xFFFF9900);
			canvas.drawRect(5*GameVariables.divide[7], height-20, 6*GameVariables.divide[7], height, middle);
			middle.setColor(0xFFFF0000);
			canvas.drawRect(6*GameVariables.divide[7], height-20, 7*GameVariables.divide[7], height, middle);
		}
	}*/
	public void drawBox(Canvas canvas){
		if (shot_fired == 1){
			edges.setAlpha(100);
		}
		else if (GameVariables.help != 1){
			edges.setAlpha(100);
			if (boosting < 196){
				boosting +=4;
			}
		}
		for (int i = 0; i < 20; i++){
			lines.reset();
			lines.moveTo(botSquareStart[i]-(((botSquareStart[i]-botScreenStart[i])/(height-centery-100))*(botCurrentPlace[i])), (centery+100)+botCurrentPlace[i]);
			lines.lineTo(botSquareStart[i]-(((botSquareStart[i]-botScreenStart[i])/(height-centery-100))*(botCurrentSize[i]+botCurrentPlace[i])), (centery+100)+botCurrentSize[i]+botCurrentPlace[i]);
			lines.lineTo(botSquareEnd[i]-(((botSquareEnd[i]-botScreenEnd[i])/(height-centery-100))*(botCurrentSize[i]+botCurrentPlace[i])), (centery+100)+botCurrentSize[i]+botCurrentPlace[i]);
			lines.lineTo(botSquareEnd[i]-(((botSquareEnd[i]-botScreenEnd[i])/(height-centery-100))*(botCurrentPlace[i])), (centery+100)+botCurrentPlace[i]);
			edges.setColor(Color.DKGRAY);
			edges.setStyle(Paint.Style.FILL);
			canvas.drawPath(lines, edges);
		}
		
		for (int i = 20; i < 25; i++){
			lines.reset();
			lines.moveTo(botSquareStart[i]-(((botSquareStart[i]-botScreenStart[i])/(height-centery-100))*(botCurrentPlace[i])), (centery+100)+botCurrentPlace[i]);
			lines.lineTo(botSquareStart[i]-(((botSquareStart[i]-botScreenStart[i])/(height-centery-100))*(botCurrentSize[i]+botCurrentPlace[i])), (centery+100)+botCurrentSize[i]+botCurrentPlace[i]);
			lines.lineTo(botSquareEnd[i]-(((botSquareEnd[i]-botScreenEnd[i])/(height-centery-100))*(botCurrentSize[i]+botCurrentPlace[i])), (centery+100)+botCurrentSize[i]+botCurrentPlace[i]);
			lines.lineTo(botSquareEnd[i]-(((botSquareEnd[i]-botScreenEnd[i])/(height-centery-100))*(botCurrentPlace[i])), (centery+100)+botCurrentPlace[i]);
			edges.setColor(Color.CYAN);
			edges.setStyle(Paint.Style.FILL);
			canvas.drawPath(lines, edges);
		}
		for (int i = 0; i < 20; i++){
			lines.reset();
			lines.moveTo(topSquareStart[i]-(((topSquareStart[i]-topScreenStart[i])/(centery-100))*(topCurrentPlace[i])), (centery-100)-topCurrentPlace[i]);
			lines.lineTo(topSquareStart[i]-(((topSquareStart[i]-topScreenStart[i])/(centery-100))*(topCurrentSize[i]+topCurrentPlace[i])), (centery-100)-topCurrentPlace[i]-topCurrentSize[i]);
			lines.lineTo(topSquareEnd[i]-(((topSquareEnd[i]-topScreenEnd[i])/(centery-100))*(topCurrentSize[i]+topCurrentPlace[i])), (centery-100)-topCurrentSize[i]-topCurrentPlace[i]);
			lines.lineTo(topSquareEnd[i]-(((topSquareEnd[i]-topScreenEnd[i])/(centery-100))*(topCurrentPlace[i])), (centery-100)-topCurrentPlace[i]);
			edges.setColor(Color.DKGRAY);
			edges.setStyle(Paint.Style.FILL);
			canvas.drawPath(lines, edges);
		}
		for (int i = 20; i < 25; i++){
			lines.reset();
			lines.moveTo(topSquareStart[i]-(((topSquareStart[i]-topScreenStart[i])/(centery-100))*(topCurrentPlace[i])), (centery-100)-topCurrentPlace[i]);
			lines.lineTo(topSquareStart[i]-(((topSquareStart[i]-topScreenStart[i])/(centery-100))*(topCurrentSize[i]+topCurrentPlace[i])), (centery-100)-topCurrentPlace[i]-topCurrentSize[i]);
			lines.lineTo(topSquareEnd[i]-(((topSquareEnd[i]-topScreenEnd[i])/(centery-100))*(topCurrentSize[i]+topCurrentPlace[i])), (centery-100)-topCurrentSize[i]-topCurrentPlace[i]);
			lines.lineTo(topSquareEnd[i]-(((topSquareEnd[i]-topScreenEnd[i])/(centery-100))*(topCurrentPlace[i])), (centery-100)-topCurrentPlace[i]);
			edges.setColor(Color.CYAN);
			edges.setStyle(Paint.Style.FILL);
			canvas.drawPath(lines, edges);
		}
		for (int i = 0; i < 20; i++){
			lines.reset();
			lines.moveTo(centerx-100-leftCurrentPlace[i], leftSquareStart[i]-(((leftSquareStart[i]-leftScreenStart[i])/(centerx-100))*leftCurrentPlace[i]));
			lines.lineTo((centerx-100)-leftCurrentPlace[i]-leftCurrentSize[i], leftSquareStart[i]-(((leftSquareStart[i]-leftScreenStart[i])/(centerx-100))*(leftCurrentSize[i]+leftCurrentPlace[i])));
			lines.lineTo((centerx-100)-leftCurrentSize[i]-leftCurrentPlace[i], leftSquareEnd[i]-(((leftSquareEnd[i]-leftScreenEnd[i])/(centerx-100))*(leftCurrentSize[i]+leftCurrentPlace[i])));
			lines.lineTo((centerx-100)-leftCurrentPlace[i], leftSquareEnd[i]-(((leftSquareEnd[i]-leftScreenEnd[i])/(centerx-100))*(leftCurrentPlace[i])));
			edges.setColor(Color.DKGRAY);
			edges.setStyle(Paint.Style.FILL);
			canvas.drawPath(lines, edges);
		}
		for (int i = 20; i < 25; i++){
			lines.reset();
			lines.moveTo(centerx-100-leftCurrentPlace[i], leftSquareStart[i]-(((leftSquareStart[i]-leftScreenStart[i])/(centerx-100))*leftCurrentPlace[i]));
			lines.lineTo((centerx-100)-leftCurrentPlace[i]-leftCurrentSize[i], leftSquareStart[i]-(((leftSquareStart[i]-leftScreenStart[i])/(centerx-100))*(leftCurrentSize[i]+leftCurrentPlace[i])));
			lines.lineTo((centerx-100)-leftCurrentSize[i]-leftCurrentPlace[i], leftSquareEnd[i]-(((leftSquareEnd[i]-leftScreenEnd[i])/(centerx-100))*(leftCurrentSize[i]+leftCurrentPlace[i])));
			lines.lineTo((centerx-100)-leftCurrentPlace[i], leftSquareEnd[i]-(((leftSquareEnd[i]-leftScreenEnd[i])/(centerx-100))*(leftCurrentPlace[i])));
			edges.setColor(Color.CYAN);
			edges.setStyle(Paint.Style.FILL);
			canvas.drawPath(lines, edges);
		}
		for (int i = 0; i < 20; i++){
			lines.reset();
			lines.moveTo(centerx+100+rightCurrentPlace[i], rightSquareStart[i]-(((rightSquareStart[i]-rightScreenStart[i])/(width-centerx-100))*rightCurrentPlace[i]));
			lines.lineTo((centerx+100)+rightCurrentPlace[i]+rightCurrentSize[i], rightSquareStart[i]-(((rightSquareStart[i]-rightScreenStart[i])/(width-centerx-100))*(rightCurrentSize[i]+rightCurrentPlace[i])));
			lines.lineTo((centerx+100)+rightCurrentSize[i]+rightCurrentPlace[i], rightSquareEnd[i]-(((rightSquareEnd[i]-rightScreenEnd[i])/(width-centerx-100))*(rightCurrentSize[i]+rightCurrentPlace[i])));
			lines.lineTo((centerx+100)+rightCurrentPlace[i], rightSquareEnd[i]-(((rightSquareEnd[i]-rightScreenEnd[i])/(width-centerx-100))*(rightCurrentPlace[i])));
			edges.setColor(Color.DKGRAY);
			edges.setStyle(Paint.Style.FILL);
			canvas.drawPath(lines, edges);
		}
		for (int i = 20; i < 25; i++){
			lines.reset();
			lines.moveTo(centerx+100+rightCurrentPlace[i], rightSquareStart[i]-(((rightSquareStart[i]-rightScreenStart[i])/(width-centerx-100))*rightCurrentPlace[i]));
			lines.lineTo((centerx+100)+rightCurrentPlace[i]+rightCurrentSize[i], rightSquareStart[i]-(((rightSquareStart[i]-rightScreenStart[i])/(width-centerx-100))*(rightCurrentSize[i]+rightCurrentPlace[i])));
			lines.lineTo((centerx+100)+rightCurrentSize[i]+rightCurrentPlace[i], rightSquareEnd[i]-(((rightSquareEnd[i]-rightScreenEnd[i])/(width-centerx-100))*(rightCurrentSize[i]+rightCurrentPlace[i])));
			lines.lineTo((centerx+100)+rightCurrentPlace[i], rightSquareEnd[i]-(((rightSquareEnd[i]-rightScreenEnd[i])/(width-centerx-100))*(rightCurrentPlace[i])));
			edges.setColor(Color.CYAN);
			edges.setStyle(Paint.Style.FILL);
			canvas.drawPath(lines, edges);
		}
	}
	
	public void modifyBoxes (){
		for (int i = 0; i < 25; i++){
			int j = i*2;
			botSquareStart[i] = (float)(centerx-100+botPercent[j]);
			botSquareEnd[i] = (float)(centerx-100+botPercent[j+1]);
			botScreenStart[i] = (float)(width*(botPercent[j]/200));
			botScreenEnd[i] = (float)(width*(botPercent[j+1]/200));
			topSquareStart[i] = (float)(centerx-100+topPercent[j]);
			topSquareEnd[i] = (float)(centerx-100+topPercent[j+1]);
			topScreenStart[i] = (float)(width*(topPercent[j]/200));
			topScreenEnd[i] = (float)(width*(topPercent[j+1]/200));
			leftSquareStart[i] = (float)(centery-100+leftPercent[j]);
			leftSquareEnd[i] = (float)(centery-100+leftPercent[j+1]);
			leftScreenStart[i] = (float)(height*(leftPercent[j]/200));
			leftScreenEnd[i] = (float)(height*(leftPercent[j+1]/200));
			rightSquareStart[i] = (float)(centery-100+leftPercent[j]);
			rightSquareEnd[i] = (float)(centery-100+leftPercent[j+1]);
			rightScreenStart[i] = (float)(height*(leftPercent[j]/200));
			rightScreenEnd[i] = (float)(height*(leftPercent[j+1]/200));
		}
		
		for (int i=0; i < 25; i++){
			if (botCurrentTiming[i] > botTiming[i]){
				if (i < 20){
					if (botCurrentPlace[i]+centery+100 >= height){
						botCurrentPlace[i] = 0;
						botCurrentSize[i] = 0;
						botCurrentTiming[i] = 0;
					}
					else if ((botCurrentSize[i]+botCurrentPlace[i]+centery+100) >= height){
						botCurrentPlace[i]+=4* GameVariables.speedModifier*2;
						botCurrentSize[i]+=4* GameVariables.speedModifier*2;
					}
					else if (botCurrentSize[i] < botSize[i]){
						if (botCurrentSize[i]+centery+100 > height-(centery+100)/2){
							botCurrentSize[i]+=1* GameVariables.speedModifier*2;
						}
						botCurrentSize[i]+=1* GameVariables.speedModifier*2 + GameVariables.storySpeed;
					}
					else if (botCurrentSize[i] >= botSize[i]){
						if (botCurrentPlace[i]+centery+100 > height-(centery+100)/2){
							botCurrentPlace[i]+=1* GameVariables.speedModifier*2;
							botCurrentSize[i]+=1* GameVariables.speedModifier*2;
						}
						botCurrentSize[i]+=1* GameVariables.speedModifier*2;
						botCurrentPlace[i]+=1* GameVariables.speedModifier*2;
					}
				}
				if (i>=20){
					botCurrentSize[i]=botSize[i];
					if (botCurrentPlace[i]+centery+100 >= height){
						botCurrentPlace[i] = 0;
						botCurrentSize[i] = 0;
						botCurrentTiming[i] = 0;
					}
					else if ((botCurrentSize[i]+botCurrentPlace[i]+centery+100) >= height){
						botCurrentPlace[i]+=16* GameVariables.speedModifier*2;
					}
					else if (botCurrentSize[i] >= botSize[i]){
						botCurrentPlace[i]+=8* GameVariables.speedModifier*2;
					}
				}
			}
			botCurrentTiming[i]+=r.nextInt(3);
			if (i>=20){
				botCurrentTiming[i]+=r.nextInt(10);
			}
		}
		for (int i=0; i < 25; i++){
			if (topCurrentTiming[i] > topTiming[i]){
				if (i < 20){
					if ((centery-100)-topCurrentPlace[i] <= 0){
						topCurrentPlace[i] = 0;
						topCurrentSize[i] = 0;
						topCurrentTiming[i] = 0;
					}
					else if ((centery-100)-(topCurrentSize[i]+topCurrentPlace[i]) <= 0){
						topCurrentPlace[i]+=4* GameVariables.speedModifier*2;
						topCurrentSize[i]+=4* GameVariables.speedModifier*2;
					}
					else if (topCurrentSize[i] < topSize[i]){
						if (centery-100-topCurrentSize[i] < (centery-100)/2){
							topCurrentSize[i]+=1* GameVariables.speedModifier*2;
						}
						topCurrentSize[i]+=1* GameVariables.speedModifier*2 + GameVariables.storySpeed;
					}
					else if (topCurrentSize[i] >= topSize[i]){
						if (centery+100-topCurrentPlace[i] < (centery-100)/2){
							topCurrentPlace[i]+=1* GameVariables.speedModifier*2;
							topCurrentSize[i]+=1* GameVariables.speedModifier*2;
						}
						topCurrentSize[i]+=1* GameVariables.speedModifier*2;
						topCurrentPlace[i]+=1* GameVariables.speedModifier*2;
					}
				}
				if (i>=20){
					topCurrentSize[i]=topSize[i];
					if ((centery-100)-topCurrentPlace[i] <= 0){
						topCurrentPlace[i] = 0;
						topCurrentSize[i] = 0;
						topCurrentTiming[i] = 0;
					}
					else if ((centery-100)-(topCurrentSize[i]+topCurrentPlace[i]) <= 0){
						topCurrentPlace[i]+=16* GameVariables.speedModifier*2;
					}
					else if (topCurrentSize[i] >= topSize[i]){
						topCurrentPlace[i]+=8* GameVariables.speedModifier*2;
					}
				}
			}
			topCurrentTiming[i]+=r.nextInt(3);
			if (i>=20){
				topCurrentTiming[i]+=r.nextInt(10);
			}
		}
		
		for (int i=0; i < 25; i++){
			if (leftCurrentTiming[i] > leftTiming[i]){
				if (i < 20){
					if ((centerx-100)-leftCurrentPlace[i] <= 0){
						leftCurrentPlace[i] = 0;
						leftCurrentSize[i] = 0;
						leftCurrentTiming[i] = 0;
					}
					else if ((centerx-100)-(leftCurrentSize[i]+leftCurrentPlace[i]) <= 0){
						leftCurrentPlace[i]+=4* GameVariables.speedModifier*2;
					}
					else if (leftCurrentSize[i] < leftSize[i]){
						if (centerx-100-leftCurrentSize[i] < (centerx-100)/2){
							leftCurrentSize[i]+=2* GameVariables.speedModifier*2;
						}
						leftCurrentSize[i]+=2* GameVariables.speedModifier*2 + GameVariables.storySpeed;
					}
					else if (leftCurrentSize[i] >= leftSize[i]){
						if (centerx-100-leftCurrentPlace[i] < (centerx-100)/2){
							leftCurrentPlace[i]+=2* GameVariables.speedModifier*2;
							leftCurrentSize[i]+=2* GameVariables.speedModifier*2;
						}
						leftCurrentSize[i]+=2* GameVariables.speedModifier*2;
						leftCurrentPlace[i]+=2* GameVariables.speedModifier*2;
					}
				}
				if (i>=20){
					leftCurrentSize[i]=leftSize[i];
					if ((centerx-100)-leftCurrentPlace[i] <= 0){
						leftCurrentPlace[i] = 0;
						leftCurrentSize[i] = 0;
						leftCurrentTiming[i] = 0;
					}
					else if ((centerx-100)-(leftCurrentSize[i]+leftCurrentPlace[i]) <= 0){
						leftCurrentPlace[i]+=16* GameVariables.speedModifier*2;
					}
					else if (leftCurrentSize[i] >= leftSize[i]){
						leftCurrentPlace[i]+=8* GameVariables.speedModifier*2;
					}
				}
			}
			leftCurrentTiming[i]+=r.nextInt(3);
			if (i>=20){
				leftCurrentTiming[i]+=r.nextInt(10);
			}
		}
		for (int i=0; i < 25; i++){
			if (rightCurrentTiming[i] > rightTiming[i]){
				if (i < 20){
					if (rightCurrentPlace[i]+centerx+100 >= width){
						rightCurrentPlace[i] = 0;
						rightCurrentSize[i] = 0;
						rightCurrentTiming[i] = 0;
					}
					else if ((rightCurrentSize[i]+rightCurrentPlace[i]+centerx+100) >= width){
						rightCurrentPlace[i]+=8* GameVariables.speedModifier*2;
					}
					else if (rightCurrentSize[i] < rightSize[i]){
						if (rightCurrentSize[i]+centerx+100 > width-(centerx+100)/2){
							rightCurrentSize[i]+=2* GameVariables.speedModifier*2;
						}
						rightCurrentSize[i]+=2* GameVariables.speedModifier*2 + GameVariables.storySpeed;
					}
					else if (rightCurrentSize[i] >= rightSize[i]){
						if (rightCurrentPlace[i]+centerx+100 > width-(centerx+100)/2){
							rightCurrentPlace[i]+=2* GameVariables.speedModifier*2;
							rightCurrentSize[i]+=2* GameVariables.speedModifier*2;
						}
						rightCurrentSize[i]+=2* GameVariables.speedModifier*2;
						rightCurrentPlace[i]+=2* GameVariables.speedModifier*2;
					}
				}
				if (i>=20){
					rightCurrentSize[i]=rightSize[i];
					if (rightCurrentPlace[i]+centerx+100 >= width){
						rightCurrentPlace[i] = 0;
						rightCurrentSize[i] = 0;
						rightCurrentTiming[i] = 0;
					}
					else if ((rightCurrentSize[i]+rightCurrentPlace[i]+centerx+100) >= width){
						rightCurrentPlace[i]+=16* GameVariables.speedModifier*2;
					}
					else if  (rightCurrentSize[i] >= rightSize[i]){
						rightCurrentPlace[i]+=8* GameVariables.speedModifier*2;
					}
				}
			}
			rightCurrentTiming[i]+=r.nextInt(3);
			if (i>=20){
				rightCurrentTiming[i]+=r.nextInt(10);
			}
		}
	}

	public void shot_firing (Canvas canvas){
		shot_x[1] = (float)(((shot_x[0]*200)/width)+(centerx-100));
		shot.setColor(Color.BLUE);
		shot.setStyle(Paint.Style.STROKE);
		shot_path.reset();
		shot_path.moveTo(shot_x[0]-((shot_x[0]-shot_x[1])/centery)*shot_size_place[1], shot_y[0]-shot_size_place[1]);
		shot_path.lineTo(shot_x[0]-((shot_x[0]-shot_x[1])/centery)*(shot_size_place[0]+shot_size_place[1]), shot_y[0]-shot_size_place[0]-shot_size_place[1]);
		canvas.drawPath(shot_path, shot);
		shot.setColor(Color.RED);
		shot.setStyle(Paint.Style.STROKE);
		shot_path.reset();
		shot_x[0]-=19;
		shot_path.moveTo(shot_x[0]-((shot_x[0]-shot_x[1])/centery)*shot_size_place[1], shot_y[0]-shot_size_place[1]);
		shot_path.lineTo(shot_x[0]-((shot_x[0]-shot_x[1])/centery)*(shot_size_place[0]+shot_size_place[1]), shot_y[0]-shot_size_place[0]-shot_size_place[1]);
		canvas.drawPath(shot_path, shot);
		shot_path.reset();
		shot_x[0]+=36;
		shot_path.moveTo(shot_x[0]-((shot_x[0]-shot_x[1])/centery)*shot_size_place[1], shot_y[0]-shot_size_place[1]);
		shot_path.lineTo(shot_x[0]-((shot_x[0]-shot_x[1])/centery)*(shot_size_place[0]+shot_size_place[1]), shot_y[0]-shot_size_place[0]-shot_size_place[1]);
		canvas.drawPath(shot_path, shot);
		shot_x[0]-=17;
		if ((shot_y[0]-shot_size_place[1]) <= centery){
			shot_size_place[0] = 0;
			shot_size_place[1] = 0;
			shot_fired = 0;
			if (GameVariables.isSummoned == 1){
				if ((GameVariables.shotfrom > GameVariables.enemyLocation[0]) && (GameVariables.shotfrom < GameVariables.enemyLocation[1])){
					GameVariables.score[GameVariables.currentscore][0] = GameVariables.randFreq;
					GameVariables.score[GameVariables.currentscore][1] = (System.currentTimeMillis() - GameVariables.enemyAppear);
					GameVariables.score[GameVariables.currentscore][2] = GameVariables.volume;
					GameVariables.xRaw[GameVariables.currentscore] = GameVariables.randFreq;
					GameVariables.yRaw[GameVariables.currentscore] = GameVariables.volume;
					GameVariables.currentscore++;
					GameVariables.isSummoned = 0;
        			GameVariables.soundPlayed = 0;
        			GameVariables.level++;

				}
			}
		}
		else if ((shot_y[0])-(shot_size_place[0]+shot_size_place[1]) <= centery){
			shot_size_place[0]-=25;
			shot_size_place[1]+=25;
		}
		else if (shot_size_place[0] < 25){
			shot_size_place[0]+=25;
		}
		else if (shot_size_place[0] >= 25){
			shot_size_place[1]+=25;
			shooting = 0;
		}
		
		
	}
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) {
		
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER){
		    return;
	    }
		/*
         * record the accelerometer data, the event's timestamp as well as
         * the current time. The latter is needed so we can calculate the
         * "present" time during rendering. In this application, we need to
         * take into account how the screen is rotated with respect to the
         * sensors (which always return data in a coordinate space aligned
         * to with the screen in its native orientation).
         */

        //switch (mDisplay.getRotation()) {
            //case Surface.ROTATION_0:
              //  addSensorValue(event.values[0],-event.values[1]);
            	//break;
            
            
            //case Surface.ROTATION_90:
        addSensorValue(-event.values[1],event.values[0]);
            	//break;
            /*
            case Surface.ROTATION_180:
            	addSensorValue(-event.values[0],-event.values[1]);
            	break;
            case Surface.ROTATION_270:
            	addSensorValue(event.values[1],-event.values[0]);
            	break;
            	*/
       // }
        mSensorTimeStamp = event.timestamp;
        mCpuTimeStamp = System.nanoTime();
		
	}
	
	private void addSensorValue(float x, float y){
		int length = mSensorX.length;
		int i = 0;
		if (GameVariables.hit == 0){
			while (i < length - 1){
				mSensorX[i] = mSensorX[i+1];
				mSensorY[i] = mSensorY[i+1];
				i += 1;
			}
			mSensorX[9] = x;
			mSensorY[9] = y;
		}
		
		
		
	}


}
