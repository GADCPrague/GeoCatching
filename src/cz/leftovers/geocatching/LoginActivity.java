package cz.leftovers.geocatching;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends Activity{
	public static final String PREFS_NAME = "PrefLogFile";
	public static final String PREF_USERNAME = "username";
	public static final String PREF_PASSWORD = "password";
	public static final String PREF_CHECKED = "checked";
	public static String userId;
	public static String userName;
	private HttpClient client;
	private HttpPost post;
	private HttpResponse resp;
	private List<NameValuePair> nvp;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		/* finding the elements in the Layout XML */
		final EditText loginText = (EditText)this.findViewById(R.id.login_text);
		final EditText passwdText = (EditText)this.findViewById(R.id.password_text);
		final CheckBox chck = (CheckBox)this.findViewById(R.id.checkBox_remember);
		
		/* asks for SHaredPreferences and checks whether there are any, then it sets the EditText boxes to found data */
		final SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		boolean checked = pref.getBoolean(PREF_CHECKED, false); // in case no data found, the CheckBox will be unchecked
		if(checked) {
			String username = pref.getString(PREF_USERNAME, null); // in case of no data found, the EditBox will be blank
			String password = pref.getString(PREF_PASSWORD, null); // in case of no data found, the EditBox will be blank	
			/* setting the data */
			loginText.setText(username);
			passwdText.setText(password);
			chck.setChecked(checked);
		}
		
		Button loginBtn = (Button)this.findViewById(R.id.login_button);
		/* LOGIN BUTTON listener */
		loginBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* checks whether the login data is correct */
				Logger log = new Logger(loginText.getText().toString().toLowerCase(), md5(passwdText.getText().toString().toLowerCase()));
				try {
					if(log.execute().get()) {
						/* if it is correct and CheckBox is checked, save the data */
						if(chck.isChecked()){
							pref
							.edit()
							.putString(PREF_USERNAME, loginText.getText().toString())
							.putString(PREF_PASSWORD, passwdText.getText().toString())
							.putBoolean(PREF_CHECKED, true)
							.commit();
						}else{
							/* if not checked though, erase ANY data left behind */
							pref.edit().clear().commit();
						}
						/* wraps up and starts working */
						finish();
						overridePendingTransition(0, 0);
						startActivity((new Intent(LoginActivity.this, CommTabActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
					} else {
						/* in case of wrong login, AlertDialog pops out */
						final AlertDialog ad = new AlertDialog.Builder(LoginActivity.this).create();
						Resources res = getResources();
						ad.setTitle(res.getString(R.string.error));
						ad.setMessage(res.getString(R.string.wrong_parameters));
						ad.setButton(res.getString(R.string.close), new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) { // listener for dismissing the dialog and trying again
								passwdText.setText(""); // clear the password, just for the heck of it, probably it was the one to be wrong
								ad.dismiss();
							}
						});
						ad.show();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Transforms given String to MD5 encoded String.
	 * @param s - String for encoding.
	 * @return encoded String or empty String in case of failure.
	 */
	public String md5(String s) { // method for encoding a String via MD5
	    try {
	        MessageDigest m = MessageDigest.getInstance("MD5");
	        byte[] data = s.getBytes();
	        m.update(data, 0, data.length);
	        BigInteger i = new BigInteger(1, m.digest());
	        return String.format("%1$032X", i).toLowerCase();
	        
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return ""; // in case of failure, returns an empty string, oh well...MLIA.
	}
	
	/**
	 * 
	 * @author michal
	 *
	 */
	private class Logger extends AsyncTask<Void, Void, Boolean>{
		ProgressDialog pd;
		String login;
		String passwd;
		String response;
		
		LoginHandler lh;
		
		/**
		 * 
		 * @param login
		 * @param passwd
		 */
		public Logger(String login, String passwd){
			this.login = login;
			this.passwd = passwd;
			lh = new LoginHandler();
		}
		
		/**
		 * 
		 * @param login
		 * @param passwd
		 * @return
		 */
		public String connect(String login, String passwd){
			client = new DefaultHttpClient();
			post = new HttpPost(getResources().getString(R.string.url));
			
			nvp = new ArrayList<NameValuePair>(3);
			
			nvp.add(new BasicNameValuePair("datatype", "auth"));
			nvp.add(new BasicNameValuePair("user", login));
			nvp.add(new BasicNameValuePair("password", passwd));
					
			try {
				post.setEntity(new UrlEncodedFormEntity(nvp));
				resp = client.execute(post);
				HttpEntity ent = resp.getEntity();
				
				return EntityUtils.toString(ent, "UTF-8");
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
				response = this.connect(login, passwd);
				if (response.equals("denied")) {
					return false;
				} else {
					try {
						Xml.parse(connect(login, passwd), lh);
						return true;
					} catch (SAXException e) {
						return false;
					}
				}
		}
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(LoginActivity.this, "","Loading",true);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			pd.dismiss();
		}
		/**
		 * 
		 * @author michal
		 *
		 */
		class LoginHandler extends DefaultHandler{

			@Override
			public void startElement(String uri, String localName, String qName,
					Attributes attributes) throws SAXException {
				if(localName.equals("loggedUser")){
				}else if(localName.equals("info")){
					userId = attributes.getValue("userId");
					userName = attributes.getValue("userName");
				}
			}
			
			@Override
			public void endElement(String uri, String localName, String qName)
					throws SAXException {
				if(localName.equals("loggedUser")){
				}
			}
		}
	}
	//end of class ListRefresher
}
//end of LoginActivity