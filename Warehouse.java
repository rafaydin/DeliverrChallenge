import java.util.HashMap;
import java.util.Map;

//represents a warehouse with a name and inventory of items
public class Warehouse {
	private String name;
	private Map<String, Integer> inventory; //maps item to its quantity
	
	//constructors
	public Warehouse(String name, Map<String, Integer> inventory) {
		this.name = name;
		this.inventory = new HashMap<>();
		for (String key : inventory.keySet()) {
			this.inventory.put(key, inventory.get(key));
		}
	}

	public Warehouse(String name) {
		this.name = name;
		inventory = new HashMap<>();
	}
	
	//getters
	public Map<String, Integer> getInventory(){
		return inventory;
	}
	
	public String getName() {
		return this.name;
	}
	
	//add item to inventory
	public void addItem(String item, int count) {
		if (count > 0)
			inventory.put(item, count);
	}
	
	//quantity of item in warehouse, 0 if not present
	public int getQuantity(String item) {
		return inventory.getOrDefault(item,0);
	}
	
	//is inventory empty
	public boolean isEmpty() {
		return inventory.isEmpty();
	}
	

	
}
