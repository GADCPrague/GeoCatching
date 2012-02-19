package cz.leftovers.geocatching;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GameListActivity extends Activity {
	public String response;
	
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
				rowView = inflater.inflate(R.layout.game_list_item_layout, null);
				sView = new SingleGameView();
				
				sView.name = (TextView)rowView.findViewById(R.id.game_list_item_main_text);
				sView.location = (TextView)rowView.findViewById(R.id.game_list_item_location);
				sView.players = (TextView)rowView.findViewById(R.id.game_list_item_players);
				sView.date = (TextView)rowView.findViewById(R.id.game_list_item_date);

				rowView.setTag(sView);
			} else {
				sView = (SingleGameView) rowView.getTag();
			}
			sView.name.setText( MainMenuActivity.dh.pags.names.get(position) );
			sView.location.setText( R.string.game_list_item_location + ": " + MainMenuActivity.dh.pags.locations.get(position) );
			sView.players.setText( R.string.game_list_item_players+ ": " + MainMenuActivity.dh.pags.connected.get(position) + "/" + MainMenuActivity.dh.pags.capacity.get(position) );
			sView.date.setText( R.string.game_list_item_date + ": " + MainMenuActivity.dh.pags.starts.get(position) + "-" + MainMenuActivity.dh.pags.ends.get(position) );

			
			return rowView;
		}
		
		protected class SingleGameView {
			protected TextView name;
			protected TextView location;
			protected TextView players;
			protected TextView date;
		}
	}
	
	
	
	
	
	
	
	
	private class ListLoader extends AsyncTask<Integer, Void, Boolean>{
		ProgressDialog pd;
		HttpClient client;
		HttpPost post;
		HttpResponse resp;
		
		public ListLoader(){
			client = new DefaultHttpClient();
			post = new HttpPost("http://www.dentack.cz/geocatching/game-show.php");
		}
		
		@Override
		protected Boolean doInBackground(Integer... params) {	
			try {
				post.setEntity(new UrlEncodedFormEntity(new ArrayList<NameValuePair>()));
				resp = client.execute(post);
				HttpEntity ent = resp.getEntity();
				response = EntityUtils.toString(ent, "UTF-8");
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(GameListActivity.this, "","Loading",true);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			pd.dismiss();
		}
	}
	
	
	
	
	
	
	
	
}
