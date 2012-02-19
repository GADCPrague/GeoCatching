package cz.leftovers.geocatching;

import java.util.List;

import cz.casablanca.android.commanderapp.DataLoaderBeta;
import cz.casablanca.android.commanderapp.R;
import cz.casablanca.android.commanderapp.TripsActivity.TripsListExpandableAdapter.SingleTripView;
import cz.casablanca.android.commanderapp.TripsActivity.TripsListExpandableAdapter.SingleTripViewDetails;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
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
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}	
		
		
		protected class SingleTripView {
			protected TextView date;
			protected TextView duration;
			protected TextView distance;
			protected TextView driver;
		}
		
		protected class SingleTripViewDetails {
			protected TextView fromToData;
			protected TextView tachoData;
			protected TextView originData;
			protected TextView destinationData;
		}
	}
	
	
	
	
	
	
	
	
	
}
