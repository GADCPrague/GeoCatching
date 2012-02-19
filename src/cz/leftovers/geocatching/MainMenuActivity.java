package cz.leftovers.geocatching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuActivity extends Activity {
	private Button buttonGameList;
	private Button buttonPlayerGameList;
	private Button buttonPlayerHistory;
	private Button buttonSettings;
	private Button buttonTMP;
	private Button buttonExit;
	
	public static DataHandler dh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_layout);
		
		buttonGameList = (Button)this.findViewById(R.id.buttonGameList);
		buttonGameList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity((new Intent(MainMenuActivity.this, GameListActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			}
		});
		
		buttonPlayerGameList = (Button)this.findViewById(R.id.buttonPlayerGameList);
		buttonPlayerGameList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity((new Intent(MainMenuActivity.this, MyGamesActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			}
		});
		
		buttonPlayerHistory = (Button)this.findViewById(R.id.buttonPlayerHistory);
		buttonPlayerHistory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity((new Intent(MainMenuActivity.this, HistoryActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));	
			}
		});
		
		buttonSettings = (Button)this.findViewById(R.id.buttonSettings);
		buttonSettings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity((new Intent(MainMenuActivity.this, SettingsActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			}
		});
		
		buttonTMP = (Button)this.findViewById(R.id.buttonTMP);
		buttonTMP.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
		
		buttonExit = (Button)this.findViewById(R.id.buttonExit);
		buttonExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
