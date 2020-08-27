import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryAllocator {

	public List<Warehouse> getBestShipment(List<Warehouse> warehouses, Map<String, Integer> order){
		//check for invalid order
		if (!isValidOrder(order))
			return new ArrayList<>();
		int itemsToFulfill = order.size(); //number of items remaining to ship, should be 0 at end
		
		Map<String, Integer> orderCopy = new HashMap<>();
		orderCopy.putAll(order); //order copy to allow parameter to be modified
		List<Warehouse> shipments = new ArrayList<>(); // result
		
		//iterate over warehouses starting from cheapest
		for (Warehouse warehouse : warehouses) {
			Warehouse shipment = new Warehouse(warehouse.getName()); //potential shipment from warehouse
			//iterate over items in order
			for (String itemOrdered : orderCopy.keySet()) {
				int orderAmount = orderCopy.get(itemOrdered);
				int warehouseQuantity = warehouse.getQuantity(itemOrdered);
				//if item not fulfilled yet and warehouse holds item
				if (orderAmount > 0 && warehouseQuantity > 0) {
					int addToShipment = 0;
					//warehouse does not carry the required amount
					if (orderAmount > warehouseQuantity) {
						orderAmount -= warehouseQuantity;
						addToShipment = warehouseQuantity;
					//warehouse carries the required amount 
					}else {
						addToShipment = orderAmount;
						orderAmount = 0;
						itemsToFulfill--;
					}
					orderCopy.put(itemOrdered, orderAmount); //update order for given item
					shipment.addItem(itemOrdered, addToShipment); //item and quantity for given warehouse
				}
			}
			//add warehouse to result only if it is supplying something 
			if (!shipment.getInventory().isEmpty())
				shipments.add(shipment);
			//stop checking once order is fulfilled
			if (itemsToFulfill == 0)
				return shipments;
		}
		return new ArrayList<>(); //order never fulfilled
		
	}
	
	//checks if order is not empty and items are of valid quantity
	public boolean isValidOrder(Map<String,Integer> order) {
		if (order.size() == 0)
			return false;
		for (String item : order.keySet()) {
			if (order.get(item) < 0)
				return false;
		}
		return true;
	}
	
}
