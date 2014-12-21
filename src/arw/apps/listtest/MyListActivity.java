package arw.apps.listtest;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class MyListActivity extends ListActivity implements OnItemClickListener, OnSharedPreferenceChangeListener {

	Firebase ref, refa;
	String ln;
	SharedPreferences sharedPrefs;
	ListView listView;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		sharedPrefs.registerOnSharedPreferenceChangeListener(this);
		StringBuilder listname = new StringBuilder();

		String ln = listname.append(sharedPrefs.getString("shared_name", "NULL")).toString();

		if(ln.isEmpty()) {

			Intent launchactivity= new Intent(this,QuickPrefsActivity.class);                               
			startActivity(launchactivity);

		} else {


			Toast.makeText(this, ln ,Toast.LENGTH_SHORT).show();
		}


		ref = new Firebase("https://arwshoppingapp.firebaseio.com").child(ln);

		View header = getLayoutInflater().inflate(R.layout.activity_main, null);
		final ListView listView = getListView();
		listView.addHeaderView(header);
		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(ref.limit(50),this,R.layout.rowlayout);
		listView.setAdapter(adapter);


		listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			public void onItemClick(AdapterView<?> adapter, View view,int position, long id) {



				String str = ((Item) listView.getAdapter().getItem(position)).getItem();

				String key =  ((ItemListAdapter) ((HeaderViewListAdapter) listView.getAdapter()).getWrappedAdapter()).getKey(str);

				ref.child(key).removeValue();

				//	Toast.makeText(getApplicationContext(), str ,Toast.LENGTH_SHORT).show();	
				//	Toast.makeText(getApplicationContext(), key ,Toast.LENGTH_SHORT).show();

			};
		});
		}
	
		

	public void add(View view){

		//Grab item from text entry box
		EditText etItem = (EditText)findViewById(R.id.editText1);
		String zoom = etItem.getText().toString();

		// First we get a reference to the location of the user's name data:
		Firebase checkers = ref;

		//Generate a reference to a new location with push()
		Firebase newPushRef = checkers.push();

		Item item = new Item(zoom);

		// Set some data to the generated location
		newPushRef.setValue(item);

		//Get the name generated by push
		final String pushedName = newPushRef.getName();


		etItem.setText(null);

	}
	;

	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_settings:

			Intent intent = new Intent(this, QuickPrefsActivity.class);
			startActivity(intent);	

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}



	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPref, String shared_name) {

		Toast.makeText(getApplicationContext(), "change" ,Toast.LENGTH_SHORT).show();	
		//StringBuilder listname = new StringBuilder();
		//String ln = listname.append(sharedPrefs.getString("shared_name", "NULL")).toString();

		//ref = new Firebase("https://arwshoppingapp.firebaseio.com").child(ln);

		//Toast.makeText(getApplicationContext(), ln ,Toast.LENGTH_SHORT).show();

			}



}









