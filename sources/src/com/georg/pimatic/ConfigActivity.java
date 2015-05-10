package com.georg.pimatic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigActivity extends Activity {
	
	EditText et;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String hostname = prefs.getString("host_value", "https://pimatic.tld");
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
	        editor.commit();
	        Toast.makeText(ConfigActivity.this, "Config saved!", Toast.LENGTH_LONG).show();
	        startActivity(i);
	}
		
	
}
