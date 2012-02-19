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
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
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
				Toast.makeText(this, gameLines.toString(), Toast.LENGTH_LONG).show();
				GamesAdapter adapter = new GamesAdapter(this, gameLines);
				listView.setAdapter(adapter);
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
				
				sView.name = (TextView)rowView.findViewById(R.id.game_list_name_label);
				sView.location = (TextView)rowView.findViewById(R.id.game_list_location_label);
				sView.capacity = (TextView)rowView.findViewById(R.id.game_list_capacity_label);
				sView.connected = (TextView)rowView.findViewById(R.id.game_list_connected_label);
				sView.start = (TextView)rowView.findViewById(R.id.game_list_start_label);
				sView.end = (TextView)rowView.findViewById(R.id.game_list_end_label);
				rowView.setTag(sView);
			} else {
				sView = (SingleGameView) rowView.getTag();
			}
			sView.name.setText(objects.get(position).name);
			sView.location.setText(objects.get(position).area);
			sView.capacity.setText(objects.get(position).capacity+"");
			sView.connected.setText("0");
			sView.start.setText(objects.get(position).gamefrom);
			sView.end.setText(objects.get(position).gameto);
			
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
			pd = ProgressDialog.show(GameListActivity.this, "","Loading",true);
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
		//int connected;
		String gamefrom;
		String gameto;
	}
}
