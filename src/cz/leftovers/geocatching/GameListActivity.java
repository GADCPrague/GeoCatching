package cz.leftovers.geocatching;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GameListActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
	
	class GamesAdapter extends BaseAdapter {
		Activity activity;
		List<String> objects;
		int lastExpandedGroupPosition;
		
		public GamesAdapter(Activity activity, List<String> objects) {
			this.activity = activity;
			this.objects = objects;
		}
		
		@Override
		public int getCount() {
			return objects.size();
		}

		@Override
		public Object getItem(int position) {
			return objects.get(position);
		}

		@Override
		public long getItemId(int position) {
			return objects.get(position).hashCode();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			SingleGameView sView = null;
			
			if (rowView == null) {
				LayoutInflater inflater = activity.getLayoutInflater();
				rowView = inflater.inflate(R.layout., null);
				sView = new SingleGameView();
				
				sView.name = (TextView)rowView.findViewById(R.id.);
				sView.location = (TextView)rowView.findViewById(R.id.);
				sView.capacity = (TextView)rowView.findViewById(R.id.);
				sView.connected = (TextView)rowView.findViewById(R.id.);
				sView.start = (TextView)rowView.findViewById(R.id.);
				sView.end = (TextView)rowView.findViewById(R.id.);
				rowView.setTag(sView);
			} else {
				sView = (SingleGameView) rowView.getTag();
			}
			sView.name.setText(MainMenuActivity.dh.pags.names.get(position));
			sView.location.setText(MainMenuActivity.dh.pags.locations.get(position));
			sView.capacity.setText(MainMenuActivity.dh.pags.capacity.get(position)+"");
			sView.connected.setText(MainMenuActivity.dh.pags.connected.get(position)+"");
			sView.start.setText(MainMenuActivity.dh.pags.starts.get(position));
			sView.end.setText(MainMenuActivity.dh.pags.ends.get(position));
			
			return rowView;
		}
		
		protected class SingleGameView {
			protected TextView name;
			protected TextView location;
			protected TextView capacity;
			protected TextView connected;
			protected TextView start;
			protected TextView end;
		}
	}
	
	
	
	
	
	
	
	
	
}
