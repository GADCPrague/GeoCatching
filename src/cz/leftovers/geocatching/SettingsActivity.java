package cz.leftovers.geocatching;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		
		ImageView iv = (ImageView) this.findViewById(R.id.fb_icon);
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(SettingsActivity.this, "Facebook linking window", Toast.LENGTH_LONG);
			}
		});
		
		iv = (ImageView) this.findViewById(R.id.gplus_icon);
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(SettingsActivity.this, "Google+ linking window", Toast.LENGTH_LONG);
			}
		});
		
		iv = (ImageView) this.findViewById(R.id.tw_icon);
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(SettingsActivity.this, "Twitter linking window", Toast.LENGTH_LONG);
			}
		});
		
		iv = (ImageView) this.findViewById(R.id.fsq_icon);
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(SettingsActivity.this, "Foursquare linking window", Toast.LENGTH_LONG);
			}
		});
	}
}
