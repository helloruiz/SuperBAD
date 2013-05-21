package com.helloruiz.superbad.appint;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.helloruiz.superbad.R;

public class AppInteraction extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_interaction);
		// Ensures that the keyboard won't come up. May not work on specific devices.
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	public void dialNumber(View view) {

		// Take in phone number from EditText
		EditText editText = (EditText) findViewById (R.id.edit_dial_num);
		String phoneNumber = editText.getText().toString();

		if(phoneNumber.equals("")) {
			hideKeyboard(editText);
			
			Context context = getApplicationContext();
			Toast toastNoName = Toast.makeText(context, "Please enter a number.", durationShort);
			toastNoName.show();
			return;
		}

		Uri number = Uri.parse("tel:" + phoneNumber);
		Intent callIntent = new Intent(Intent.ACTION_DIAL, number);


		// Verify that there is at least one compatible app
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(callIntent, 0);

		if(activities.size() > 0) {
			editText.setText("");
			hideKeyboard(editText);
			startActivity(callIntent);
		} else {
			Context context = getApplicationContext();
			Toast toastNoName = Toast.makeText(context, "No dial apps found.", durationShort);
			toastNoName.show();
			return;
		}
	}

	public void composeEmail(View view) {

		// Take in email address from EditText
		EditText editText = (EditText) findViewById (R.id.edit_email_address);
		String emailAddress = editText.getText().toString();

		if(emailAddress.equals("")) {
			hideKeyboard(editText);
			
			Context context = getApplicationContext();
			Toast toastNoName = Toast.makeText(context, "Please enter an address.", durationShort);
			toastNoName.show();
			return;
		}

		Uri email = Uri.parse("mailto:"+ emailAddress);
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, email);

		// Verify that there is at least one compatible app
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(emailIntent, 0);

		if(activities.size() > 0) {
			editText.setText("");
			hideKeyboard(editText);
			startActivity(emailIntent);
		} else {
			Context context = getApplicationContext();
			Toast toastNoName = Toast.makeText(context, "No email apps found.", durationShort);
			toastNoName.show();
			return;
		}
	}

	public void openWebpage(View view) {

		// Take in web address from EditText
		EditText editText = (EditText) findViewById (R.id.edit_web_address);
		String webAddress = editText.getText().toString();

		if(webAddress.equals("")) {
			hideKeyboard(editText);
			
			Context context = getApplicationContext();
			Toast toastNoName = Toast.makeText(context, "Please enter an address.", durationShort);
			toastNoName.show();
			return;
		}

		// Adds 'http://' if necessary
		if(webAddress.length() >= 7) {
			if(!webAddress.substring(0, 7).equals("http://")) {
				webAddress = "http://" + webAddress;
			}
		} else {
			webAddress = "http://" + webAddress;
		}

		Uri webpage = Uri.parse(webAddress);
		Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);

		// Verify that there is at least one compatible app
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(webIntent, 0);

		if(activities.size() > 0) {
			editText.setText("");
			hideKeyboard(editText);
			startActivity(webIntent);
		} else {
			Context context = getApplicationContext();
			Toast toastNoName = Toast.makeText(context, "No web apps found.", durationShort);
			toastNoName.show();
			return;
		}
	}	

	public void findLocation (View view) {
		// Take in email address from EditText
		EditText editText = (EditText) findViewById (R.id.edit_loc_address);
		String location = editText.getText().toString();

		if(location.equals("")) {
			hideKeyboard(editText);
			
			Context context = getApplicationContext();
			Toast toastNoName = Toast.makeText(context, "Please enter a value, such as an address or location name.", durationLong);
			toastNoName.show();
			return;
		}

		Uri loc = Uri.parse("geo:0,0?q="+ location);
		Intent locationIntent = new Intent(Intent.ACTION_VIEW, loc);

		// Verify that there is at least one compatible app
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(locationIntent, 0);

		if(activities.size() > 0) {
			editText.setText("");
			hideKeyboard(editText);
			startActivity(locationIntent);
		} else {
			Context context = getApplicationContext();
			Toast toastNoName = Toast.makeText(context, "No map apps found.", durationShort);
			toastNoName.show();
			return;
		}
	}

	public void shareApp(View view) {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.extra_share_text));
		Intent chooser = Intent.createChooser(shareIntent, "Share this app via...");


		// Verify that there is at least one compatible app
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(chooser, 0);

		if(activities.size() > 0) {
			startActivity(chooser);
		} else {
			Context context = getApplicationContext();
			Toast toastNoName = Toast.makeText(context, "No compatible apps found.", durationShort);
			toastNoName.show();
			return;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_app_interaction, menu);
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
		String dialogAboutTitle = getString(R.string.title_activity_app_interaction);
		String dialogAbout = getString(R.string.dialog_about_demo_app_interaction);
		
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
	
	// Method to hide the keyboard
	public void hideKeyboard(EditText e) {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(e.getWindowToken(), 0);
	}

	public static final int durationShort = Toast.LENGTH_SHORT;
	public static final int durationLong = Toast.LENGTH_LONG;
}
