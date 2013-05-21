package com.helloruiz.superbad;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.helloruiz.superbad.appint.AppInteraction;
import com.helloruiz.superbad.audio.AudioDemo;
import com.helloruiz.superbad.camera.CameraDemo;
import com.helloruiz.superbad.data.DataDemo;
import com.helloruiz.superbad.frag.FragmentTest;
import com.helloruiz.superbad.textinput.TypeAMessage;

public class MainActivity extends Activity {

	// Called when activity is first created
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	//Called when user clicks the Text Input Demo button
	public void textInputDemo(View view) {

		Intent intent = new Intent(this, TypeAMessage.class);
		startActivity(intent);
	}

	//Called when user clicks the Text Input Demo button
	public void fragmentDemo(View view) {

		Intent intent = new Intent(this, FragmentTest.class);
		startActivity(intent);
	}

	//Called when user clicks the Data Demo button
	public void dataDemo(View view) {

		Intent intent = new Intent(this, DataDemo.class);
		startActivity(intent);
	}

	//Called when user clicks the App Interaction Demo button
	public void appIntDemo(View view) {

		Intent intent = new Intent(this, AppInteraction.class);
		startActivity(intent);
	}

	//Called when user clicks the Audio Demo button
	public void audioDemo(View view) {

		Intent intent = new Intent(this, AudioDemo.class);
		startActivity(intent);
	}

	//Called when user clicks the Camera Demo button
	public void cameraDemo(View view) {

		// Do not open activity if rear camera isn't found.
		if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Toast.makeText(getApplicationContext(), "No rear camera found.", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent(this, CameraDemo.class);
		startActivity(intent);
	}

	// The following handles the Android menu. More about the menu can be found here:
	// http://developer.android.com/guide/topics/ui/menus.html
	
	// Creates settings menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_main, menu);
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
	        default:
	            return super.onOptionsItemSelected(item);
	    }
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
	
}
