package se.combitech.strokesformartians;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class StrokesForMartians extends Activity {
	
	private Intent danceIntent = null;
	
    public static final int MENU_LOAD_ID = 0;
    public static final int MENU_DRAW_ID = 1;
    public static final int MENU_CREDITS_ID = 2;
    public static final int MENU_EXIT_ID = 3;
    
    
    public static final int DANCE_REQUEST = 0;
    public static final int PAINT_REQUEST = 1;
    
    
    public static final int EXIT_RESULT_CODE = 0;
	
	
	
	private void init()
	{
		danceIntent = SFMIntentFactory.createDancerIntent(this);
	}
	
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    	if( requestCode == DANCE_REQUEST ) {
    		if ( resultCode == EXIT_RESULT_CODE ) {
    			
    			finish();
    		}
    	}
    }
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        init();
        
        // Removes the grey title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Removes Androids status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        startActivityForResult( danceIntent, DANCE_REQUEST );
    }
}