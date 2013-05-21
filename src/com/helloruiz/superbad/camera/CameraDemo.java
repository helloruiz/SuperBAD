package com.helloruiz.superbad.camera;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.helloruiz.superbad.R;

public class CameraDemo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_demo);
	}

	public void takePhoto(View view) {

		// Check to see if there is a camera application available by making a list.
		PackageManager packageManager = getApplicationContext().getPackageManager();
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

		// If there's a camera app available, go ahead and start the activity.
		if (list.size() > 0) {
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
			// Set a working directory on the SD card.
			File sdCard = Environment.getExternalStorageDirectory();
			File fileDir = new File(sdCard.getAbsolutePath() + "/Pictures/SuperBAD");
			fileDir.mkdirs();
			
			// Create an image file name
		    String timeStamp = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss_").format(new Date());
		    String imageFileName = "SuperBAD_" + timeStamp;
		    try {
				File image = File.createTempFile(imageFileName, ".jpg", fileDir);
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
				currentPhotoPath = image.getAbsolutePath();
				startActivityForResult(takePictureIntent, 1);
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), "Unable to create image for photo capture.", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "No apps available for photo capture.", Toast.LENGTH_SHORT).show();
		}
	}

	public void takeVideo(View view) {
		// Check to see if there is a camera application available by making a list.
		PackageManager packageManager = getApplicationContext().getPackageManager();
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

		// If there's a camera app available, go ahead and start the activity.
		if (list.size() > 0) {
			startActivityForResult(new Intent(MediaStore.ACTION_VIDEO_CAPTURE), 2);
		} else {
			Toast.makeText(getApplicationContext(), "No apps available for video capture.", Toast.LENGTH_SHORT).show();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Intent intent = new Intent(this, CameraResult.class);
		intent.putExtra(REQUEST_CODE, requestCode);
		intent.putExtra(RESULT_CODE, resultCode);
		
		if (requestCode == 1) {
			
			if(resultCode == RESULT_OK){
				
				// Add image to the Android gallery.
				Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			    File f = new File(currentPhotoPath);
			    Uri contentUri = Uri.fromFile(f);
			    mediaScanIntent.setData(contentUri);
			    this.sendBroadcast(mediaScanIntent);
			    
			    intent.putExtra(PHOTO_PATH, currentPhotoPath);
				startActivity(intent);
			}

			if (resultCode == RESULT_CANCELED) {
				startActivity(intent);
			}
		}
		
		if (requestCode == 2) {
			
			if(resultCode == RESULT_OK){
				
				// The video is a URI, we can't add a URI using putExtra().
				// We'll use the 'data' intent instead, closely resembling 'intent'.
				// Alternatively you can convert the URI to a string, and use putExtra();
				// http://stackoverflow.com/questions/8017374/how-to-pass-a-uri-to-an-intent
				// Either method works, so pick your poison.
				data.setClass(this, CameraResult.class);
				data.putExtra(REQUEST_CODE, requestCode);
				data.putExtra(RESULT_CODE, resultCode);
				startActivity(data);
			}

			if (resultCode == RESULT_CANCELED) {
				startActivity(intent);
			}
		}
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_camera_demo, menu);
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
		String dialogAboutTitle = getString(R.string.title_activity_camera_demo);
		String dialogAbout = getString(R.string.dialog_about_demo_camera);
		
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
	
	public static String currentPhotoPath;
	public final static String PHOTO_PATH = "com.helloruiz.superbad.camera.photopath";
	public final static String REQUEST_CODE = "com.helloruiz.superbad.camera.requestCode";
	public final static String RESULT_CODE = "com.helloruiz.superbad.camera.resultCode";
	public final static String CAMERA_DATA = "com.helloruiz.superbad.camera.data";
}
