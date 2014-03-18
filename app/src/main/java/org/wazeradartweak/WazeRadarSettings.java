/**
 * 
 */
package org.wazeradartweak;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;

/**
 * @author nicolas
 * 
 */
public class WazeRadarSettings {

	public static final String key1 = "GeoConfig.Ignore server config";
	public static final String key2 = "Alerts.Enable Enforcement Alerts";

	/**
	 * @return
	 */
	public static boolean isWazeInstalled() {
		File sdcard = Environment.getExternalStorageDirectory();
		File wazeFolder = new File(sdcard, "waze");
		if (!wazeFolder.exists())
			return false;

		File wazePreferences = new File(wazeFolder, "preferences");
		if (!wazePreferences.exists())
			return false;

		return true;
	}

	/**
	 * @return
	 */
	public static boolean areRadarsEnabled() {
		boolean key1Check = false;
		boolean key2Check = false;

		if (isWazeInstalled()) {
			File sdcard = Environment.getExternalStorageDirectory();
			File wazePreferences = new File(sdcard.getAbsolutePath() + "/waze/preferences");

			// Read text from file
			StringBuilder text = new StringBuilder();
			try {
				BufferedReader br = new BufferedReader(new FileReader(wazePreferences));
				String line;
				while ((line = br.readLine()) != null) {
					text.append(line);
					text.append('\n');
					if (line.startsWith(key1) && line.lastIndexOf("yes") != -1) {
						key1Check = true;
					}
					if (line.startsWith(key2) && line.lastIndexOf("yes") != -1) {
						key2Check = true;
					}
				}
			} catch (IOException e) {
				// You'll need to add proper error handling here
			}
		}
		return (key1Check && key2Check);
	}

	/**
	 * @return
	 */
	public static boolean setRadarsEnabled(boolean value) {
		String oldValue;
		String newValue;
		
		if (value) {
			oldValue = ": no";
			newValue = ": yes";
		} else {
			oldValue = ": yes";
			newValue = ": no";
		}

		if (isWazeInstalled()) {
			File sdcard = Environment.getExternalStorageDirectory();			
			File wazePreferences = new File(sdcard.getAbsolutePath() + "/waze/preferences");
			File wazePreferencesTmp = new File(sdcard.getAbsolutePath() + "/waze/preferences.tmp");
			
			// Read text from file
			try {
				// reader
				BufferedReader br = new BufferedReader(new FileReader(wazePreferences));
				// writer
				if (!wazePreferencesTmp.exists())
					wazePreferencesTmp.createNewFile();	
				FileWriter fw = new FileWriter(wazePreferencesTmp.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);	
				String line;
				while ((line = br.readLine()) != null) {					
					if (line.startsWith(key1)) {
						line = line.replace(oldValue, newValue);
					}
					if (line.startsWith(key2)) {
						line = line.replace(oldValue, newValue);
					}
					bw.write(line);
					bw.write("\n");
				}
				br.close();
				bw.close();
				
				File waze = new File(sdcard, "waze");
				wazePreferences.renameTo(new File(waze,"preferences.bak"));
				wazePreferencesTmp.renameTo(new File(waze,"preferences"));
			} catch (IOException e) {
				// You'll need to add proper error handling here
			}
			
		}
		return value;
	}

}
