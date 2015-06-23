package com.georg.pimatic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigActivity extends Activity {
	
	EditText et;
	EditText zo;
	CheckBox cb;
	CheckBox fc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String hostname = prefs.getString("host_value", "https://pimatic.tld");
		String zoom = String.valueOf(prefs.getInt("zoom_value", 0));
		boolean checkBoxValue = prefs.getBoolean("checkbox_value", false);
		boolean fullscreenValue = prefs.getBoolean("fullscreen_value", false);
		cb = (CheckBox) findViewById(R.id.ignoreSSL);
		fc = (CheckBox) findViewById(R.id.fullscreen);
		if (checkBoxValue) {
            cb.setChecked(true);
        } else {
        	cb.setChecked(false);
		}
		if (fullscreenValue) {
            fc.setChecked(true);
        } else {
        	fc.setChecked(false);
		}
		et = (EditText)findViewById(R.id.hostname);
		zo = (EditText) findViewById(R.id.zoom);
		et.setText(hostname);
		zo.setText(zoom);
		zo.setRawInputType(InputType.TYPE_CLASS_NUMBER);
		
	}
	
	public void sendMessage2(View v) {
	    
	    String host = et.getText().toString();
	    String zoom_string = zo.getText().toString();
	    int zoom;
	    if (host.length() == 0) {
	        new AlertDialog.Builder(ConfigActivity.this) 
	                .setMessage(R.string.error_hostname_missing)
	                .setNeutralButton(R.string.error_ok, null)
	                .show();
	        return;
	    }
	    if (zoom_string.length() == 0) {
		        new AlertDialog.Builder(ConfigActivity.this) 
		                .setMessage(R.string.error_zoom)
		                .setNeutralButton(R.string.error_ok, null)
		                .show();
		        return;
	    }
	    else {
	    	zoom = Integer.parseInt(zoom_string);
	    }
	    if (zoom > 800 || zoom < 0) {
	        new AlertDialog.Builder(ConfigActivity.this) 
	                .setMessage(R.string.error_zoom)
	                .setNeutralButton(R.string.error_ok, null)
	                .show();
	        return;
	    }
	        Context context = getApplicationContext();
	        Intent i = new Intent(context, MainActivity.class);
	        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    	SharedPreferences.Editor editor = prefs.edit();
	        editor.putString("host_value", host);
	        editor.putInt("zoom_value", zoom);
	        editor.putBoolean("checkbox_value", cb.isChecked());
	        editor.putBoolean("fullscreen_value", fc.isChecked());
	        editor.commit();
	        Toast.makeText(ConfigActivity.this, "Config saved!", Toast.LENGTH_LONG).show();
	        startActivity(i);
	}
		
	
}
