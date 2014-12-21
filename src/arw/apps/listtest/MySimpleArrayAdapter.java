package arw.apps.listtest;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;

public class MySimpleArrayAdapter extends ItemListAdapter<Item> {

	public MySimpleArrayAdapter(Query ref, Activity myListActivity,int rowlayout) {
		// TODO Auto-generated constructor stub
		super(ref,Item.class, rowlayout, myListActivity);
		
	}

	@Override
	protected void createView(View view, Item item) {
		// TODO Auto-generated method stub

		String value = item.getItem();
		TextView txtItem = (TextView)view.findViewById(R.id.label);
		
		txtItem.setText(value);
	}

	
	
}
