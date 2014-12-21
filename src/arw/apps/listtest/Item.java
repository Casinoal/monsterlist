package arw.apps.listtest;

public class Item {

	private String item;
	
	private Item() {}
	
	Item(String value){
		this.item = value;
	}
	
	public String getItem(){
		return item;
	}
}

