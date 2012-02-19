package cz.leftovers.geocatching;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class DialogPrey extends Dialog{

	public DialogPrey(Context context) {
		super(context);
	}
	
	/**
	 * Creates this Dialog.
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dialog_prey);
		//setTitle(res.getString(R.string.about_title));
		
		Button btn = (Button)this.findViewById(R.id.prey_close_button);
		btn.setOnClickListener(new CloseListener(this));
	}
	
	/**
	 * Listener for Close button.
	 * @author Michal Svacha
	 *
	 */
	private class CloseListener implements android.view.View.OnClickListener {
		Dialog dialog;
		
		/**
		 * Constructor of this class
		 * @param dialog - sets the listener for this parameter
		 */
		public CloseListener(Dialog dialog) {
			this.dialog = dialog;
		}
		
		/**
		 * Listener for onClick.
		 */
		public void onClick(View v) {
			dialog.dismiss();
		}
	}
	// end of class CloseListener
	
	
	
}
