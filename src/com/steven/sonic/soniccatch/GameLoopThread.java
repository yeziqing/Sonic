package com.steven.sonic.soniccatch;

import android.graphics.Canvas;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.ToneGenerator;
import android.os.Handler;

public class GameLoopThread extends Thread {
	
	
	private Object mPauseLock;
	private boolean mPaused;
	private boolean mFinished;

	AudioTrack track;
	static final long FPS = 60;
	private GameView view;
	private GraphView myGraph;
	private boolean running = false;
	private double duration = .5;
	private int sampleRate = 44100;
	private double numSamples = duration * sampleRate;
	private double samples[] = new double[(int) numSamples];
	private final byte buffer[] = new byte[2 * (int) numSamples];
	private double frequency = 1000;
	
	static private int threadkiller = 0;
       
       Handler handler = new Handler();
      
       public GameLoopThread(GameView view) {
             this.view = view;
             
             mPauseLock = new Object();
             mPaused = false;
             mFinished = false;
       }
 
       public void setRunning(boolean run) {
             running = run;
             GameVariables.isRunning = 1;
       }
 
       @Override
       public void run() {
    	   	long ticksPS = 1000/FPS; //100 is minimum in which each loop has to last
    	   	long startTime;
    	   	long sleepTime;
    	   	GameVariables.lastPlayed = System.currentTimeMillis() + 3000;
    	   	
             while (running) {
            	 
                    Canvas c = null;
                    startTime = System.currentTimeMillis();
                    
                    try {
                           c = view.getHolder().lockCanvas();
                           synchronized (view.getHolder()) {
                        	   
                        	   if (GameVariables.isRunning == 1){
                        		   if (GameVariables.help == 1){
                        			   view.beginning(c);
                        		   }
                        		   else if (GameVariables.playBack == 1 && GameVariables.level >= 2){
                        			   view.playBack(c);
                        		   }
                        		   else if (GameVariables.pause == 0){
                        			   if (GameVariables.levelchange != 0){
                        				   view.generateEnemy(c);
                        			   }
                        			   view.onDraw(c);
                        		   }
                        		   else if (GameVariables.pause == 1){
                        				view.drawBackground(c);
                        				view.drawBox(c);
                        				view.drawHUD(c);
                        				view.drawHelper(c);
                        				view.drawShip(c);
                        				view.drawTutorial(c);
                        		   }
                        		   
                        	   }
                        	  else{
                        		  view.printResults(c);
                        	   }
                        		  
                           }
                        //   if (GameVariables.isSummoned == 1 && GameVariables.soundPlayed == 1){ 
                        	//   genTone();
                           //}
                           if (GameVariables.isSummoned == 1 && GameVariables.soundPlayed == 1 && (System.currentTimeMillis()-GameVariables.soundStarted) > 150){
                        	   //genTone();
                        	 //play sound thread
                        	  // System.gc();
                        	   Thread thread = new Thread(new Runnable() {
                                   public void run() {
                                	  
                                       genTone2();
                                       handler.post(new Runnable() {

                                           public void run() {
                                               playSound();
                                           }
                                       });
                                   }
                               });
                               thread.start();
                             
								/*if (threadkiller == 1) {
									thread.interrupt();
									while (true) {
										try {
											thread.join(); // let thread die
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										break;
									}
									thread = null;
			
								}*/
                           }
                           
                           if (GameVariables.story == 15 && GameVariables.soundPlayed == 1){
                        	   //genTone();
                        	 //play sound thread
                        	  // System.gc();
                        	   Thread thread = new Thread(new Runnable() {
                                   public void run() {
                                	  
                                       genTone2();
                                       handler.post(new Runnable() {

                                           public void run() {
                                               playSound2();
                                           }
                                       });
                                   }
                               });
                               thread.start();
                             
								/*if (threadkiller == 1) {
									thread.interrupt();
									while (true) {
										try {
											thread.join(); // let thread die
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										break;
									}
									thread = null;
			
								}*/
                           }
                           else if (GameVariables.story == 15 && GameVariables.soundPlayed == 2){
                        		GameVariables.lastPlayed = System.currentTimeMillis(); 
                        		GameVariables.soundPlayed = 3;
                           }
                           else if (GameVariables.story == 15 && GameVariables.soundPlayed == 3){
                       			if (GameVariables.lastPlayed + 1000 <= System.currentTimeMillis()){ 
                       				GameVariables.soundPlayed = 0;
                       			}
                           }
                           
                           if (GameVariables.levelchange == 0 && GameVariables.lastPlayed + 3000 <= System.currentTimeMillis() && GameVariables.pause == 0){
                        	   	GameVariables.level++;
                        	   	GameVariables.levelchange = GameVariables.level + 3;
                        	   	GameVariables.playBack = 1;
                        	   	GameVariables.playBackLevel = 1;
                        	   	GameVariables.storytime = 0;
                           }
                           if (GameVariables.lives == -1){
                        	   GameVariables.isRunning = 0;
                           }
                           if (GameVariables.lives == 4){
                        	   GameVariables.isRunning = 1;
                        	   GameVariables.isSummoned = 0;
                        	   GameVariables.soundPlayed = 0;
                        	   GameVariables.soundStarted = 0;
                        	   GameVariables.volume = 0;
                        	   GameVariables.hit = 0;
                        	   GameVariables.currentscore = 0;
                        	   GameVariables.currentmiss = 0;
                        	   GameVariables.lives--;
                           }
                           if (GameVariables.level > 7){
                        	   GameVariables.level = 7;
                           }
                    } finally {
                           if (c != null) {
                                  view.getHolder().unlockCanvasAndPost(c);
                           }
                    }
                    
                    sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
                    try {
                        if (sleepTime > 0)
                               sleep(sleepTime);
                       // else
                               //sleep(10);
                 } catch (Exception e) {}
                    
                    
                    synchronized (mPauseLock) {
                        while (mPaused) {
                            try {
                                mPauseLock.wait();
                            } catch (InterruptedException e) {
                            }
                        }
                    }
             }
             
       }
       
       public void onPause() {
           synchronized (mPauseLock) {
               mPaused = true;
           }
       }
       
       public void onResume() {
           synchronized (mPauseLock) {
               mPaused = false;
               mPauseLock.notifyAll();
           }
       }
       
   
    
    //ToneGenerator tg = new ToneGenerator (AudioManager.STREAM_NOTIFICATION, 50);
   
/*
    void genTone(){
    	    frequency = GameVariables.randFreq;
  	  
			
			if(track != null)
	        	track.release();
	        
	       
	        
	        for( int i = 0; i < numSamples; ++i )
	        {
	     	   samples[i] = Math.sin(2 * Math.PI * i / (sampleRate/frequency));
	        }

	     	int idx = 0;
           for (final double dVal : samples) {
              // scale to maximum amplitude
              final short val = (short) ((dVal * 32767));
              // in 16 bit wav PCM, first byte is the low order byte
              buffer[idx++] = (byte) (val & 0x00ff);
              buffer[idx++] = (byte) ((val & 0xff00) >>> 8);
           }
           track = new AudioTrack( AudioManager.STREAM_MUSIC, (int)numSamples, 
	       		 AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, 
	       		buffer.length, AudioTrack.MODE_STATIC); 
           
	        int temp = track.write( buffer, 0, buffer.length );
	        if(temp != AudioTrack.ERROR_BAD_VALUE && temp != AudioTrack.ERROR_INVALID_OPERATION){
	        	if (GameVariables.volume == 0){
		        	GameVariables.enemyAppear = System.currentTimeMillis();
	        	}
	        	GameVariables.soundStarted = System.currentTimeMillis();
	        	if (GameVariables.soundPlayed == 1){
	        		GameVariables.volume++;
	        		track.setStereoVolume((float)(0.01*GameVariables.volume), (float)(0.01*GameVariables.volume));
	        		if (GameVariables.volume == 40){
	        			GameVariables.isSummoned = 0;
	        			GameVariables.soundPlayed = 0;
	        			GameVariables.lives--;
	        			GameVariables.miss[GameVariables.currentmiss] = GameVariables.randFreq;
	        			GameVariables.currentmiss++;
	        			GameVariables.hit = 1;
	        			track.pause();
	        			track.flush();
	        			track.stop();
	        		}
	        	}
	        	track.play();               		    
	        }
	        
    }
 */
       
	void genTone2(){
	    	
	    	frequency = GameVariables.randFreq;
	    	
	    	if(track != null) track.release();
	        // fill out the array
	        for (int i = 0; i < numSamples; ++i) {
	            samples[i] = Math.sin(2 * Math.PI * i / (sampleRate/frequency));
	        }
	
	        // convert to 16 bit pcm sound array
	        // assumes the sample buffer is normalised.
	        int idx = 0;
	        for (final double dVal : samples) {
	            // scale to maximum amplitude
	            final short val = (short) ((dVal * 32767));
	            // in 16 bit wav PCM, first byte is the low order byte
	            buffer[idx++] = (byte) (val & 0x00ff);
	            buffer[idx++] = (byte) ((val & 0xff00) >>> 8);
	
	        }
	    }
	
	void playSound() {
		track = new AudioTrack(AudioManager.STREAM_MUSIC,
				(int) numSamples, AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, buffer.length,
				AudioTrack.MODE_STATIC);
		int temp = track.write(buffer, 0, buffer.length);
		if (temp != AudioTrack.ERROR_BAD_VALUE && temp != AudioTrack.ERROR_INVALID_OPERATION) {
			
			if (GameVariables.volume == 0) {
				GameVariables.enemyAppear = System.currentTimeMillis();
			}
			
			GameVariables.soundStarted = System.currentTimeMillis();
			
			if (GameVariables.soundPlayed == 1) {
				GameVariables.volume+=3;

				try {
					if (GameVariables.earTesting == 0){
						track.setStereoVolume( (0.05f * GameVariables.volume), (0.05f * GameVariables.volume));
					}
					else if (GameVariables.earTesting == 1){
						track.setStereoVolume( (0.05f * GameVariables.volume), 0);
					}
					else if (GameVariables.earTesting == 2){
						track.setStereoVolume( 0, (0.05f * GameVariables.volume));
					}
				} catch (IllegalStateException ese) {
					
				}
				
				/*if (GameVariables.volume == 20) {
					track.pause();
					track.stop();
					track.flush();
					track.release();
					System.gc();
				}*/
			}
			
			try {
				track.play();
			}catch (IllegalStateException ese) {
			}
			
		}
		
		if (GameVariables.volume*0.05f > 1f) {
			
			if(track != null) {	
				
				try {
					track.pause();
					track.stop();
					track.flush();
					//track.release();
					threadkiller = 1;
				} catch (IllegalStateException ese) {
					
				}
			}
			
		}
	}

	void playSound2() {
		track = new AudioTrack(AudioManager.STREAM_MUSIC,
				(int) numSamples, AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, buffer.length,
				AudioTrack.MODE_STATIC);
		int temp = track.write(buffer, 0, buffer.length);
		if (temp != AudioTrack.ERROR_BAD_VALUE && temp != AudioTrack.ERROR_INVALID_OPERATION) {
			
			if (GameVariables.volume == 0) {
				GameVariables.enemyAppear = System.currentTimeMillis();
			}
			
			GameVariables.soundStarted = System.currentTimeMillis();
			
			if (GameVariables.soundPlayed == 1) {
				GameVariables.volume+=1;
				
				try {
					track.setStereoVolume( (0.5f), (0.5f));
				} catch (IllegalStateException ese) {
					
				}
				
				/*if (GameVariables.volume == 20) {
					track.pause();
					track.stop();
					track.flush();
					track.release();
					System.gc();
				}*/
			}
			track.play();
			GameVariables.soundPlayed = 2;
		}
		
		if (GameVariables.volume > 100) {
			
			if(track != null) {	
				
				try {
					track.pause();
					track.stop();
					track.flush();
					//track.release();
					threadkiller = 1;
					GameVariables.soundPlayed = 0;
				} catch (IllegalStateException ese) {
					
				}
			}
			
		}
	}








}  
