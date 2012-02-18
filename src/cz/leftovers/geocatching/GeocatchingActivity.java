package cz.leftovers.geocatching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * SplashScreen to show info, ads etc. First Activity to be run.
 * @author michal
 *
 */
public class GeocatchingActivity extends Activity {
	
	/**
	 * Starts this CommanderAppActivity.
	 * @param savedInstanceState
	 */
	private int timerValue = 0000;
	private TextView time;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // Sets the layout.
    	time = (TextView)this.findViewById(R.id.timer_label);
    	time.setText(timerValue+"");
        /**
         * Anonymous inner class for CountdownTimer
         */
    	new CountDownTimer(timerValue, 1000) { // Convenient timing object that can do certain actions on each tick
			
    		/**
    		 * Handler of each tick.
    		 * @param millisUntilFinished - millisecs until the end
    		 */
    		@Override
			public void onTick(long millisUntilFinished) {
				time.setText(getTime(millisUntilFinished));
			}
    		
    		public String getTime(long millisUntilFinished) {
    			int seconds = (int)millisUntilFinished/1000;    			
    			return "00:0"+seconds;
    		}
			
			/**
			 * Listener for CountDownTimer when done.
			 */
			@Override
			public void onFinish() {
				finish(); // Finishes this Activity and directs the user to Login screen.
				overridePendingTransition(0, 0); // Denies "sliding" animation while finishing this Activity.
				startActivity(new Intent(GeocatchingActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			}
		}.start(); // Starts the whole Timer.
		// end of inner class CountDownTimer
    }
}
// end of class CommanderAppActivity
