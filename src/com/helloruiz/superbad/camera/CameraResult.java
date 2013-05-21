package com.helloruiz.superbad.camera;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.helloruiz.superbad.R;

public class CameraResult extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_result);

		Intent intent = getIntent();
		int resultCode = (int) intent.getIntExtra(CameraDemo.RESULT_CODE, -2);
		int requestCode = (int) intent.getIntExtra(CameraDemo.REQUEST_CODE, -3);

		TextView textViewA = (TextView) findViewById(R.id.camera_result_text_a);
		TextView textViewB = (TextView) findViewById(R.id.camera_result_text_b);

		switch (requestCode) {
		// If user tried to take a photo
		case 1: 
			if (resultCode == RESULT_OK) {

				// Look for the onWindowFocusChanged method, the image is set there.
				// The reasoning for this is because the ImageView defined in the XML
				// has to be drawn first. It cannot be drawn here yet, so we have to
				// wait to do it later.

				textViewA.setText("Fabulous.");
				textViewB.setText("Press back to return.");
			} else {
				textViewA.setText("Oops!");
				textViewB.setText("Photo not captured.\nPress back to return and try again.");
			}
			break;
			// If user tried to take a video
		case 2: 
			if (resultCode == RESULT_OK) {
				// Change the layout to one that displays a video instead of a picture.
				setContentView(R.layout.activity_camera_result_vid);
				textViewA = (TextView) findViewById(R.id.camera_result_text_vid_a);
				textViewB = (TextView) findViewById(R.id.camera_result_text_vid_b);

				// Get the the VideoView as well as the video URI; set it.
				VideoView videoView = (VideoView) findViewById(R.id.camera_video);
				Uri videoUri = intent.getData();
				videoView.setVideoURI(videoUri);

				// Listener that will loop the video.
				// http://stackoverflow.com/questions/4746075/seamless-video-loop-with-videoview
				videoView.setOnPreparedListener(new OnPreparedListener() {
					@Override
					public void onPrepared(MediaPlayer mp) {
						mp.setLooping(true);
					}
				});

				videoView.start();
				textViewA.setText("Beautiful.");
				textViewB.setText("Press back to return.");
			} else {
				textViewA.setText("Oops!");
				textViewB.setText("Video not captured.\nPress back to return and try again.");
			}
		}
	}



	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		// We only need to take action in this method if a photo was successfully taken.
		// We'll be setting the image in this method because the ImageView has been drawn
		// We'll get the dimensions for the ImageView, and then scale it down so that
		// the device can render it efficiently without using up unnecessary memory.

		Intent intent = getIntent();
		int resultCode = (int) intent.getIntExtra(CameraDemo.RESULT_CODE, -2);
		int requestCode = (int) intent.getIntExtra(CameraDemo.REQUEST_CODE, -3);

		if (requestCode == 1 && resultCode == RESULT_OK) {
			String currentPhotoPath = (String) intent.getStringExtra(CameraDemo.PHOTO_PATH);
			ImageView imageView = (ImageView) findViewById(R.id.camera_picture);

			// Get the dimensions of the View
			int targetW = imageView.getWidth();
			int targetH = imageView.getHeight();

			// Get the dimensions of the bitmap
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			bmOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
			int photoW = bmOptions.outWidth;
			int photoH = bmOptions.outHeight;

			// Determine how much to scale down the image
			int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

			// Decode the image file into a Bitmap sized to fill the View
			bmOptions.inJustDecodeBounds = false;
			bmOptions.inSampleSize = scaleFactor;
			bmOptions.inPurgeable = true;

			Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
			imageView.setImageBitmap(bitmap);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_camera_result, menu);
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

	public static int IMAGEVIEW_WIDTH;
	public static int IMAGEVIEW_HEIGHT;
}
