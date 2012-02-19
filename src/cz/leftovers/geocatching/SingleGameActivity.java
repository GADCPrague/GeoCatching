package cz.leftovers.geocatching;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class SingleGameActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_game_layout);
		
		Button btn = (Button) this.findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogPrey ad = new DialogPrey(SingleGameActivity.this);
				ad.requestWindowFeature(Window.FEATURE_NO_TITLE);
				ad.show();
				
			}
		});
		
		btn = (Button)this.findViewById(R.id.button2);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(SingleGameActivity.this, "You gave up, thanks for playing!", Toast.LENGTH_LONG);
				
			}
		});
	}

}
