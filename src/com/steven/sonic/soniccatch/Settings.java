package com.steven.sonic.soniccatch;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Switch;
import android.widget.Toast;

public class Settings extends Activity{
	
	private AudioManager audioManager;
	NumberPicker npHeadphone, npSpeaker;
	public static int vHeadphone = 7;
	static public int vSpeaker = 7;
	
	static private boolean isPlaying = false;
	static private boolean inline = true;
	private MediaPlayer tone;
	
	NoisyAudioStreamReceiver myNASR = new NoisyAudioStreamReceiver();
	private IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);

	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //Set fullscreen mode, removes the notification bar.
		setContentView(R.layout.activity_settings);

		setTitle("Calibration Your Earphones");
		npHeadphone= (NumberPicker) findViewById(R.id.npHeadphone);
		npSpeaker= (NumberPicker) findViewById(R.id.npSpeaker);
		Button play = (Button) findViewById(R.id.bPlay);
		Button stop = (Button) findViewById(R.id.bStop);
		
		Button tips = (Button) findViewById(R.id.bTips);
		
		
		int version = android.os.Build.VERSION.SDK_INT;
		if (true) {
		    // get your Switch view and set up listeners etc
			Button flip = (Button) findViewById(R.id.bFlip);

			flip.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {

					if (inline == true) {//switch to speakers
						audioManager.setMode(AudioManager.MODE_IN_CALL);
						audioManager.setSpeakerphoneOn(true);
						audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, vSpeaker, AudioManager.FLAG_PLAY_SOUND);
						inline = false;
						npHeadphone.setEnabled(false);
						npSpeaker.setEnabled(true);
					}

					else if (inline == false) {//switch to headphones
						audioManager.setMode(AudioManager.MODE_NORMAL);
						audioManager.setSpeakerphoneOn(false);
						audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, vHeadphone, AudioManager.FLAG_PLAY_SOUND);
						inline = true;
						npHeadphone.setEnabled(true);
						npSpeaker.setEnabled(false);
					}

				}
			});
		}
		else if (false){
		    // get your CheckBox view and set up listeners etc
			Switch sw = (Switch) findViewById(R.id.switch1);

			/*sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			        if (isChecked) {
			            // The toggle is enabled
			        	Toast.makeText(getApplicationContext(), "A", Toast.LENGTH_SHORT).show();
			        } else {
			            // The toggle is disabled
			        	Toast.makeText(getApplicationContext(), "B", Toast.LENGTH_SHORT).show();
			        }
			    }
			});*/
		}

		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		final int vMax = initializeStuff();
		
		if(tone != null) tone.release();
		
		tone = MediaPlayer.create(Settings.this, R.raw.test);
		tone.setLooping(true);
		
		npHeadphone.setOnValueChangedListener(new OnValueChangeListener() {
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				//get the newVal and set it as the current volume level for headphone
				vHeadphone = newVal;
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVal, 0);
			}
		});
		
		npSpeaker.setOnValueChangedListener(new OnValueChangeListener() {
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				//get the newVal and set it as the current volume level for speaker
				vSpeaker = newVal;
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVal, 0);
			}
		});
		
		play.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
					audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, vMax/2, 0);
					tone.start();
					isPlaying = true;
			}
		});
		
		stop.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					tone.pause();
					isPlaying = false;
			}
		});
		
		
		tips.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent showTipsOverlay = new Intent(Settings.this, TipsOverlay.class);
				//showTipsOverlay.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
				startActivity(showTipsOverlay);

			}
		});
	
	}
	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) //API 14 and above
	public void onToggleClicked(View view) {
	    // Is the toggle on?
	    boolean on = ((Switch) view).isChecked();
	    
	    if (on) {
	        // Enable vibrate
	    } else {
	        // Disable vibrate
	    }
	}
	
	public int initializeStuff() {
		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		
		npHeadphone.setMaxValue(maxVolume);
		npHeadphone.setMinValue(1);
		npSpeaker.setMaxValue(maxVolume);
		npSpeaker.setMinValue(1);
		npHeadphone.setWrapSelectorWheel(false);
		npSpeaker.setWrapSelectorWheel(false);
		
		npHeadphone.setValue(maxVolume/2); //initalize value in the picker
		npSpeaker.setValue(maxVolume/2);
		
		//npHeadphone.setEnabled(false); //disable changes for headphone on initialization
		npSpeaker.setEnabled(false);
		
		return maxVolume;
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	//	finish(); //commented this out because this activity would be destroyed whenever you go into another activity, i.e. tips overlay
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
	
	@Override
    protected void onDestroy() {
		super.onDestroy();
        if(tone != null) {
            tone.stop();
            tone.release();
            tone = null;
        }
    }

	
	
	@Override
	public void onBackPressed() {
		
		 if(tone != null) {
	            tone.stop();
	            tone.release();
	            tone = null;
	        }
		 
		Intent parentActivityIntent = new Intent(this, GameSettings.class); //go back to main menu
		parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(parentActivityIntent);
		
		
		finish();
	}
	

	@Override
	// This is called when the Home (Up) button is pressed in the Action Bar.
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // Home button id
			if (tone != null) {
				tone.stop();
				tone.release();
				tone = null;
			}
			 
			Intent parentActivityIntent = new Intent(this, GameSettings.class); //go back to main menu
			parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(parentActivityIntent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class NoisyAudioStreamReceiver extends BroadcastReceiver { //http://developer.android.com/training/managing-audio/audio-output.html
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
	            tone.pause();
	        }
	    }
	}
	
	
	

}

