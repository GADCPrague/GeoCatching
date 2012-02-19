package cz.leftovers.geocatching;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GameListActivity extends Activity {
	public String response;
	ListLoader ll;
	ArrayList<LineOfGame> gameLines;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list_layout);
		
		ListView listView = (ListView) findViewById(R.id.games_listview);
		ll = new ListLoader();
		try {
			if(ll.execute().get()){
				// Toast.makeText(this, gameLines.toString(), Toast.LENGTH_LONG).show();
				GamesAdapter adapter = new GamesAdapter(this, gameLines);
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						final AlertDialog al = new AlertDialog.Builder(GameListActivity.this).create();
						al.setTitle(getResources().getString(R.string.join_game_title));
						al.setMessage(getResources().getString(R.string.game_pressed));
						
						al.setButton(getResources().getString(R.string.join), new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								al.dismiss();
							}
						});
						al.setButton3(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								al.dismiss();
							}
						});
						al.show();
					}
				});
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class GamesAdapter extends BaseAdapter {
		Activity activity;
		List<LineOfGame> objects;
		int lastExpandedGroupPosition;
		
		public GamesAdapter(Activity activity, List<LineOfGame> objects) {
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
				sView.location_label = (TextView)rowView.findViewById(R.id.game_list_item_location_label);
				sView.location_value = (TextView)rowView.findViewById(R.id.game_list_item_location_value);
				sView.players_label = (TextView)rowView.findViewById(R.id.game_list_item_players_label);
				sView.players_value = (TextView)rowView.findViewById(R.id.game_list_item_players_value);
				sView.date_label = (TextView)rowView.findViewById(R.id.game_list_item_date_label);
				sView.date_value = (TextView)rowView.findViewById(R.id.game_list_item_date_value);

				rowView.setTag(sView);
			} else {
				sView = (SingleGameView) rowView.getTag();
			}
			sView.name.setText(objects.get(position).name);
			sView.location_label.setText( getString(R.string.game_list_item_location) + ": " );
			sView.location_value.setText( objects.get(position).area );
			sView.players_label.setText( getString(R.string.game_list_item_players) + ": " );
			sView.players_value.setText( objects.get(position).login + "/" + objects.get(position).capacity);
			sView.date_label.setText( getString(R.string.game_list_item_date) + ": ");
			sView.date_value.setText( objects.get(position).gamefrom + "-" + objects.get(position).gameto);
			
			return rowView;
		}
		
		protected class SingleGameView {
			protected TextView name;
			protected TextView location_label;
			protected TextView location_value;
			protected TextView players_label;
			protected TextView players_value;
			protected TextView date_label;
			protected TextView date_value;
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
				Gson gs = new Gson();
				Type typeOfJSON = new TypeToken<ArrayList<LineOfGame>>(){}.getType();
				gameLines = gs.fromJson(response, typeOfJSON); 
			} catch (ParseException e) {
				Log.e("GameListActivity", "Selhalo parsov‡n’", e);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(GameListActivity.this, "", "Loading", true);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			pd.dismiss();
		}
	}
	
	class LineOfGame {
		int id;
		String name;
		String area;
		int capacity;
		int login;
		String gamefrom;
		String gameto;
	}
}
