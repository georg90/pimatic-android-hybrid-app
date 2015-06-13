package com.georg.pimatic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigActivity extends Activity {
	
	EditText et;
	CheckBox cb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String hostname = prefs.getString("host_value", "https://pimatic.tld");
		boolean checkBoxValue = prefs.getBoolean("checkbox_value", false);
		cb = (CheckBox) findViewById(R.id.ignoreSSL);
		if (checkBoxValue) {
            cb.setChecked(true);
        } else {
        	cb.setChecked(false);
		}
		et = (EditText)findViewById(R.id.hostname);
		et.setText(hostname);
		
		
	}
	
	public void sendMessage2(View v) {
	    
	    String host = et.getText().toString();
	    
	    if (host.length() == 0) {
	        new AlertDialog.Builder(ConfigActivity.this) 
	                .setMessage(R.string.error_hostname_missing)
	                .setNeutralButton(R.string.error_ok, null)
	                .show();
	        return;
	    }
	        Context context = getApplicationContext();
	        Intent i = new Intent(context, MainActivity.class);
	        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    	SharedPreferences.Editor editor = prefs.edit();
	        editor.putString("host_value", host);
	        editor.putBoolean("checkbox_value", cb.isChecked());
	        editor.commit();
	        Toast.makeText(ConfigActivity.this, "Config saved!", Toast.LENGTH_LONG).show();
	        startActivity(i);
	}
		
	
}
