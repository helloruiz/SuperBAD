package com.helloruiz.superbad.audio;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.helloruiz.superbad.R;

public class AudioDemo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_demo);

		// Initialize the mediaPlayer
		mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.world_map);
	}

	// When play button is pressed
	public void onPlayMusic(View view) {
		
		// If music isn't playing, then play it, otherwise return.
		if(!mediaPlayer.isPlaying()) {
			mediaPlayer.start();
			Toast.makeText(getApplicationContext(), "Playing.", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "Already playing.", Toast.LENGTH_SHORT).show();
		}

	}

	// If music is playing, stop it, otherwise return.
	public void onStopMusic(View view) {

		if(mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			mediaPlayer.seekTo(0);
			Toast.makeText(getApplicationContext(), "Stopped.", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "Already stopped", Toast.LENGTH_SHORT).show();
		}
	}


	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.activity_audio_demo);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		// Same as the stop button. 
		if(mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			mediaPlayer.seekTo(0);
			Toast.makeText(getApplicationContext(), "Stopped.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Release the mediaPlayer resources
		mediaPlayer.release();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_audio_demo, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    
		// Handle item selection
	    switch (item.getItemId()) {
	        case R.id.menu_about:
	            aboutSuperBad();
	            return true;
	        case R.id.menu_app_website:
	        	superBadWeb();
	        	return true;
	        case R.id.menu_about_demo:
	        	aboutDemo();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	// Called when 'About this demo' menu item is selected.
	public void aboutDemo() {
		String dialogAboutTitle = getString(R.string.title_activity_audio_demo);
		String dialogAbout = getString(R.string.dialog_about_demo_audio);
		
		// Displays a dialog
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(dialogAboutTitle).setMessage(dialogAbout).setPositiveButton("Close", dialogClickListener).show();
	}
	
	
	// Called when 'About SuperBAD' menu item is selected.
	public void aboutSuperBad() {

		String dialogAboutTitle = getString(R.string.menu_about);
		String dialogAbout = getString(R.string.dialog_about);
		
		// Displays a dialog
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(dialogAboutTitle).setMessage(dialogAbout).setPositiveButton("Close", dialogClickListener).show();
	}
	
	// Called when 'SuperBAD website' menu item is selected.
	public void superBadWeb() {
		
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://helloruiz.com/projects/superbad/"));

		// Verify that there is at least one compatible app to open a web page
		List<ResolveInfo> activities = getPackageManager().queryIntentActivities(webIntent, 0);
		if(activities.size() > 0) {
			startActivity(webIntent);
		} else {
			Toast.makeText(getApplicationContext(), "No web apps found.", Toast.LENGTH_SHORT).show();
		}
	}

	MediaPlayer mediaPlayer;
}

