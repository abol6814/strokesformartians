package se.combitech.strokesformartians.credits;

import se.combitech.strokesformartians.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class Credits extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CreditsView creditsView = new CreditsView(this);
        setContentView(creditsView);
        
        Thread t = new Thread( creditsView );
        t.start();
	}
}
