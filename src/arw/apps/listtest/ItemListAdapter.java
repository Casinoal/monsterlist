package arw.apps.listtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

public abstract class ItemListAdapter<T> extends BaseAdapter{

	private Query ref;
private Class<T> itemClass;
private int layout;
private LayoutInflater inflator;
private List<T> items;
private Map<String, T> itemsName;
ChildEventListener listener;

public ItemListAdapter(Query ref2, Class<T> class1, int rowlayout, Activity activity) {
	// TODO Auto-generated constructor stub
		this.ref = ref2;
		this.itemClass = class1;
		this.layout = rowlayout;
		this.inflator = activity.getLayoutInflater();
		items = new ArrayList<T>();
		itemsName = new HashMap<String, T>();
		
		listener= this.ref.addChildEventListener(new ChildEventListener() {
			
			@Override
			public void onChildRemoved(DataSnapshot arg0) {
				// TODO Auto-generated method stub
				String itemName = arg0.getName();
				T oldItem = itemsName.get(itemName);
				items.remove(oldItem);
				itemsName.remove(itemName);
				notifyDataSetChanged();
			}
			
			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub
				
				String itemName = arg0.getName();
				T oldItem = itemsName.get(itemName);
				T newItem = arg0.getValue(ItemListAdapter.this.itemClass);
				
				int index = items.indexOf(oldItem);
				items.set(index, newItem);
				itemsName.put(itemName, newItem);
				
				notifyDataSetChanged();
			}
			
			@Override
			public void onChildAdded(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub
				
				 T item = arg0.getValue(ItemListAdapter.this.itemClass);
				 itemsName.put(arg0.getName(), item);
				 
				 if(arg1 == null){
					 items.add(0,item);
				 } else {
					 T previousItem = itemsName.get(arg1);
					 int preIndex = items.indexOf(previousItem);
					 int nextIndex = preIndex+1;
					 if(nextIndex == items.size()){
						 items.add(item);
					 }else{
						 items.add(nextIndex, item);
					 }
					 
				 }
				 
				 notifyDataSetChanged();
			}
			
			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
}


	public void ClearUp(){
		ref.removeEventListener(listener);
		items.clear();
		itemsName.clear();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub`
		return items.get(arg0);
	}

	public String getKey(String value) {
		// TODO Auto-generated method stub
		
		Set<String> mKeySet = itemsName.keySet();
		
		for(String key : mKeySet)   {
            if(((Item) itemsName.get(key)).getItem().equals(value)) return key;
            
        }
		
		return null;
	}
	
	
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		
		if(view  == null){
			view = inflator.inflate(layout, viewGroup,false);
			}
		T item = items.get(i);
		createView(view,item);
		return view;
	}

	protected abstract
	
	
	
	void createView(View view, T item);

}
