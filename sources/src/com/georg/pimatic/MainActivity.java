package com.georg.pimatic;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector;




@SuppressLint("SetJavaScriptEnabled") public class MainActivity extends Activity {

	Intent intent;
	ActionBar ab;
	WebView view;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ab = getActionBar();
        ab.hide();
        final boolean isFullscreen = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("fullscreen_value", false);
        toggleFullscreenMode(isFullscreen);
        setContentView(R.layout.activity_main);
        String url = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("host_value", "http://www.google.com"); 
		int zoom =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("zoom_value", 0);
		final boolean ignoreSSL = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("checkbox_value", false); 
        view = (WebView) this.findViewById(R.id.webView);
        view.setWebViewClient(new WebViewClient() {
        	
	            @Override
	            public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
	            	if (ignoreSSL) {
	            		handler.proceed();
	            	}
	            	else {
	            		handler.cancel();
	            		String msg = error.toString();
	            		Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
	            	}
	            };
        });
        view.getSettings().setDomStorageEnabled(true);
	    File dir = getCacheDir();

	    if (!dir.exists()) {
	      dir.mkdirs();
	    }
	    view.setInitialScale(zoom);
	    view.getSettings().setAppCachePath(dir.getPath());
	    view.getSettings().setAllowFileAccess(true);
	    view.getSettings().setAppCacheEnabled(true);
        view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        @SuppressWarnings("deprecation")
		final GestureDetector gd = new GestureDetector(new MyGestureDetector());
        View.OnTouchListener gl = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gd.onTouchEvent(event);
            }
        };
        view.setOnTouchListener(gl);
        
        
	   
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_refresh:
	        	refreshWebview();
	            return true;
	        case R.id.action_settings:
	            openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void openSettings() 
    {
		intent = new Intent(MainActivity.this, ConfigActivity.class);
        startActivity(intent);
    }
	
	
	public void toggleFullscreenMode( boolean isFullscreen )
	{
	     // Build.VERSION.SDK_INT >= 16
	     View decorView = getWindow().getDecorView();
	    if (isFullscreen) {
	        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
	        decorView.setSystemUiVisibility(uiOptions);
	    } else {
	        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
	        decorView.setSystemUiVisibility(uiOptions);
	    }
	}
	
	public void refreshWebview() 
    {
		view.reload();
    }
    
	class MyGestureDetector extends SimpleOnGestureListener {    
	    @Override
	    public boolean onDoubleTap(MotionEvent e) {
	        if (ab.isShowing()) {
	        	ab.hide();
	        }
	        else if (!ab.isShowing()) {
	        	ab.show();
	        }
	        return true;
	        }   

	    }
}
