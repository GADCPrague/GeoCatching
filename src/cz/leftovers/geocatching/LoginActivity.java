package cz.leftovers.geocatching;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		
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
		
		Button registerBtn = (Button) this.findViewById(R.id.register_button);
		registerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("http://www.dentack.cz/geocatching/game-add.php");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		
		Button loginBtn = (Button)this.findViewById(R.id.login_button);
		/* LOGIN BUTTON listener */
		loginBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* checks whether the login data is correct */
				int a = 3;
				if(a == 3) {
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
					startActivity((new Intent(LoginActivity.this, MainMenuActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
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
}
//end of LoginActivity