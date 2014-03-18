package org.wazeradartweak;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	final Context context = this;
	
	private Button disableButton;
	private Button enableButton;
	private Button exitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		disableButton = (Button) findViewById(R.id.button1);
		enableButton = (Button) findViewById(R.id.button2);
		exitButton = (Button) findViewById(R.id.button3);

		// add exit button listener
		exitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				MainActivity.this.finish();
			}
		});

		// add enable button listener
		enableButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				// set title
				alertDialogBuilder.setTitle("Enable Radars in Waze");
				// set dialog message
				alertDialogBuilder
						.setMessage(
								"Are you sure you want to enable Radars? Please check the applicable laws in your country first.")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, close
										// current activity
										dialog.dismiss();
										displayRadarsStatus(WazeRadarSettings.setRadarsEnabled(true));
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
									}
								});
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();
			}
		});

		// add disable button listener
		disableButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				// set title
				alertDialogBuilder.setTitle("Disable Radars in Waze");
				// set dialog message
				alertDialogBuilder
						.setMessage(
								"You are about to disable radars. Continue?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, close
										// current activity
										dialog.dismiss();
										displayRadarsStatus(WazeRadarSettings.setRadarsEnabled(false));
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
									}
								});
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();
			}
		});
	}
	
	private void displayRadarsStatus(boolean status) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		String text = "Radars are now ";
		if (status == false)
			text+="DISABLED";
			else
				text+="ENABLED";
			
		// set title
		alertDialogBuilder.setTitle("Radars status");
		// set dialog message
		alertDialogBuilder
				.setMessage(text)
				.setCancelable(false)
				.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
								// if this button is clicked, close
								// current activity
								dialog.dismiss();
							}
						});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
