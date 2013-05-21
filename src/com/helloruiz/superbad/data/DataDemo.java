package com.helloruiz.superbad.data;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.helloruiz.superbad.R;

public class DataDemo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_demo);
		// Ensures that the keyboard won't come up. May not work on specific devices.
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	public void fileCreate(View view) {

		// Creates the file directory at (SD card)/Download/Ruiz_Test_App
		Context context = getApplicationContext();
		File sdCard = Environment.getExternalStorageDirectory();
		File fileDir = new File(sdCard.getAbsolutePath() + "/Download/Ruiz_Test_App");
		fileDir.mkdirs();

		// Take in value from EditText
		EditText editText = (EditText) findViewById (R.id.edit_create);
		String fileName = editText.getText().toString();
		File file = new File(fileDir, fileName);

		// If user did not input a file name
		if (fileName.equals("")) {

			hideKeyboard(editText);
			editText.setText("");

			Toast toastNoName = Toast.makeText(context, "File not created. Please enter a file name.", durationShort);
			toastNoName.show();

			return;
		}

		hideKeyboard(editText);
		editText.setText("");

		// Check to see if file already exists.
		if(file.exists()) {

			Toast toastFileExist = Toast.makeText(context, "File already exists.", durationShort);
			toastFileExist.show();
			return;
		}
		
		// Create file
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			Toast toastCantWrite = Toast.makeText(context, "File cannot be written", durationShort);
			toastCantWrite.show();
			return;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					Toast toastCantClose = Toast.makeText(context, "Failed to close file.", durationShort);
					toastCantClose.show();
					return;
				}
			}
		}

		// Celebrate victory
		String success = "File '" + fileName + "' has been created at " + fileDir.getAbsolutePath();
		Toast toastSuccess = Toast.makeText(context, success, durationLong);
		toastSuccess.show();
	}

	public void fileWrite(View view) {

		// Creates the file directory at (SD card)/Download/Ruiz_Test_App
		Context context = getApplicationContext();
		File sdCard = Environment.getExternalStorageDirectory();
		File fileDir = new File(sdCard.getAbsolutePath() + "/Download/Ruiz_Test_App");
		fileDir.mkdirs();

		// Take in value from EditTexts
		EditText editText = (EditText) findViewById (R.id.edit_write);
		EditText editTextD = (EditText) findViewById (R.id.edit_write_message);
		String fileName = editText.getText().toString();
		String fileData = editTextD.getText().toString() + "\n";
		File file = new File(fileDir.getAbsolutePath() + "/" + fileName);

		// If user did not input a file name or data to write
		if (fileName.equals("") || fileData.equals("\n")) {

			hideKeyboard(editText);
			
			Toast toastNoName = Toast.makeText(context, "Please enter the file data to write, as well as the file name.", durationLong);
			toastNoName.show();

			return;
		}

		hideKeyboard(editText);
		editText.setText("");


		// Check if file exists. Exit if it doesn't.
		if(!file.exists()) {

			Toast toastFileNoExist = Toast.makeText(context, "File does not exist.", durationShort);
			toastFileNoExist.show();
			return;
		}

		// Write to file.
		try {

			BufferedWriter bW = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
			bW.write(fileData);
			bW.close();

			// We can finally clear the file data EditText, because the write was successful.
			editTextD.setText("");

			// Celebrate victory.
			Toast toast = Toast.makeText(context, "Data has been written to file " + fileName + ".", durationShort);
			toast.show();
		} catch (IOException e) {

			Toast toastCantWrite = Toast.makeText(context, "Cannot write to file " + fileName + ".", durationShort);
			toastCantWrite.show();
		}
	}

	public void fileRead(View view) {
		// Creates the file directory at (SD card)/Download/Ruiz_Test_App
		final Context context = getApplicationContext();
		File sdCard = Environment.getExternalStorageDirectory();
		File fileDir = new File(sdCard.getAbsolutePath() + "/Download/Ruiz_Test_App");
		fileDir.mkdirs();

		// Take in value from EditText
		final EditText editText = (EditText) findViewById (R.id.edit_read);
		String fileName = editText.getText().toString();
		String fileData = "";
		final File file = new File(fileDir.getAbsolutePath() + "/" + fileName);
		
		hideKeyboard(editText);
		editText.setText("");
		
		// If user did not input a file name
		if (fileName.equals("")) {

			Toast toastNoName = Toast.makeText(context, "Please enter a file name.", durationShort);
			toastNoName.show();

			return;
		}

		// Check if file exists. Exit if it doesn't.
		if(!file.exists()) {

			editText.setText("");
			Toast toastFileNoExist = Toast.makeText(context, "File does not exist.", durationShort);
			toastFileNoExist.show();
			return;
		}

		// Read file
		BufferedReader bR;
		String fileDataBuffer = null;
		try {
			bR = new BufferedReader(new FileReader(file));
			if(file.length() != 0) {
				while ((fileDataBuffer = bR.readLine()) != null) {
					fileData += fileDataBuffer + "\n";
				}
			} else {
				fileName = fileName + " (empty)";
			}
			bR.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// The remainder of the method displays a dialog with the contents of the file
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(fileName).setMessage(fileData).setPositiveButton("Close", dialogClickListener).show();
	}

	public void fileDelete(View view) {

		// Creates the file directory at (SD card)/Download/Ruiz_Test_App
		final Context context = getApplicationContext();
		File sdCard = Environment.getExternalStorageDirectory();
		File fileDir = new File(sdCard.getAbsolutePath() + "/Download/Ruiz_Test_App");
		fileDir.mkdirs();

		// Take in value from EditText
		final EditText editText = (EditText) findViewById (R.id.edit_delete);
		String fileName = editText.getText().toString();
		final File file = new File(fileDir.getAbsolutePath() + "/" + fileName);

		// If user did not input a file name
		if (fileName.equals("")) {

			hideKeyboard(editText);
			editText.setText("");

			Toast toastNoName = Toast.makeText(context, "Please enter a file name.", durationShort);
			toastNoName.show();

			return;
		}

		hideKeyboard(editText);

		// Check if file exists. Exit if it doesn't.
		if(!file.exists()) {

			editText.setText("");
			Toast toastFileNoExist = Toast.makeText(context, "File does not exist.", durationShort);
			toastFileNoExist.show();
			return;
		}

		// The remainder of the method creates a dialog box and will delete  the file if the user selects 'Yes'.
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which){
				case DialogInterface.BUTTON_POSITIVE:

					//Yes button clicked
					file.delete();
					editText.setText("");
					Toast toast = Toast.makeText(context, "File deleted.", durationShort);
					toast.show();
					break;

				case DialogInterface.BUTTON_NEGATIVE:

					//No button clicked; do nothing
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Confirm Delete").setMessage("Are you sure you want to delete the file " + fileName + "?").setPositiveButton("Yes", dialogClickListener)
		.setNegativeButton("No", dialogClickListener).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_data_demo, menu);
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
		String dialogAboutTitle = getString(R.string.title_activity_data_demo);
		String dialogAbout = getString(R.string.dialog_about_demo_data);
		
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
	
	// Method to hide the keyboard.
	public void hideKeyboard(EditText e) {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(e.getWindowToken(), 0);
	}
	
	public static final int durationShort = Toast.LENGTH_SHORT;
	public static final int durationLong = Toast.LENGTH_LONG;
}
