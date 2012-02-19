package cz.leftovers.geocatching;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GameListActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list_layout);
		
		ListView listView = (ListView) findViewById(R.id.games_listview);
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
			"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
			"Linux", "OS/2" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_list_item_1, android.R.id.text1, values);
		listView.setAdapter(adapter);
	}
}
