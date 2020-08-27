import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryAllocatorTest {

	public static void main(String[] args) {
		Map<String, Integer> order = new HashMap<>();
		order.put("Apple", 5);
		order.put("Orange", 5);
		order.put("Banana", 6);
		
		Warehouse w1 = new Warehouse("w1");
		Warehouse w2 = new Warehouse("w2");
		List<Warehouse> warehouses = new ArrayList<>();
		warehouses.add(w1);
		warehouses.add(w2);
		
		w1.addItem("Apple", 5);
		w1.addItem("Orange", 5);
		w2.addItem("Banana", 5);
		
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		List<Warehouse> actual;
		List<Warehouse> expected;
		
		//Test 1 - not enough inventory for one item
		 actual = inventoryAllocator.getBestShipment(warehouses, order);
		 expected = new ArrayList<>();
		 if (actual.isEmpty())
			 System.out.println("Test 1 Passed");
		 else
			 System.out.println("Test 1 Failed");
		 
		 //Test 2 - not enough inventory for any item
		 order.put("Apple", 6);
		 order.put("Orange", 6);
		 actual = inventoryAllocator.getBestShipment(warehouses, order);
		 if (actual.isEmpty())
			 System.out.println("Test 2 Passed");
		 else
			 System.out.println("Test 2 Failed");
		 
		 //Test 3 - exact amount for order
		 order.put("Banana", 5);
		 order.put("Apple", 5);
		 order.put("Orange", 5);
		 actual = inventoryAllocator.getBestShipment(warehouses, order);
		 expected.add(new Warehouse(w1.getName(), w1.getInventory()));
		 expected.add(new Warehouse(w2.getName(), w2.getInventory()));
		 if (isCorrectShipment(expected, actual))
			 System.out.println("Test 3 Passed");
		 else
			 System.out.println("Test 3 Failed");
		 
		 //Test 4 - excess inventory
		 w2.addItem("Banana", 10);
		 actual = inventoryAllocator.getBestShipment(warehouses, order);
		 if (isCorrectShipment(expected, actual))
			 System.out.println("Test 4 Passed");
		 else
			 System.out.println("Test 4 Failed");
		 
		 //Test 5 - unnecessary warehouse
		 Warehouse w3 = new Warehouse("w3");
		 w3.addItem("Avocado", 10);
		 warehouses.add(w3);
		 actual = inventoryAllocator.getBestShipment(warehouses, order);
		 if (isCorrectShipment(expected, actual))
			 System.out.println("Test 5 Passed");
		 else
			 System.out.println("Test 5 Failed");
		 
		 //Test 6 - split item across warehouses evenly
		 order.put("Avocado", 20);
		 w2.addItem("Avocado", 10);
		 expected.get(1).addItem("Avocado", 10);
		 expected.add(new Warehouse(w3.getName(), w3.getInventory()));
		 actual = inventoryAllocator.getBestShipment(warehouses, order);
		 if (isCorrectShipment(expected, actual))
			 System.out.println("Test 6 Passed");
		 else
			 System.out.println("Test 6 Failed");
		 
		 //Test 7 - split item across warehouses unevenly
		 order.put("Avocado", 15);
		 expected.get(2).addItem("Avocado", 5);
		 actual = inventoryAllocator.getBestShipment(warehouses, order);
		 if (isCorrectShipment(expected, actual))
			 System.out.println("Test 7 Passed");
		 else
			 System.out.println("Test 7 Failed");
		 
		 
		 //Test 8 - empty order
		 actual = inventoryAllocator.getBestShipment(warehouses, new HashMap<>());
		 if (actual.isEmpty())
			 System.out.println("Test 8 Passed");
		 else
			 System.out.println("Test 8 Failed");
		 
		 //Test 9 - no warehouses
		 actual = inventoryAllocator.getBestShipment(new ArrayList<>(), order);
		 if (actual.isEmpty())
			 System.out.println("Test 9 Passed");
		 else
			 System.out.println("Test 9 Failed");
		
		 //Test 10 - invalid order
		 order.put("Strawberry", -5);
		 actual = inventoryAllocator.getBestShipment(warehouses, order);
		 if (actual.isEmpty())
			 System.out.println("Test 10 Passed");
		 else
			 System.out.println("Test 10 Failed");
		 
		
	}
	
	// compares expected shipment to actual shipment
	public static boolean isCorrectShipment(List<Warehouse> expected, List<Warehouse> actual) {
		if (expected.size() != actual.size())
			return false;
		Collections.sort(expected, (a,b) -> a.getName().compareTo(b.getName()));
		Collections.sort(actual, (a,b) -> a.getName().compareTo(b.getName()));
		for (int i = 0; i < expected.size(); i++) {
			Warehouse expectedWarehouse = expected.get(i);
			Warehouse actualWarehouse = actual.get(i);
			if (!expectedWarehouse.getName().equals(actualWarehouse.getName())
					|| !expectedWarehouse.getInventory().equals(actualWarehouse.getInventory()))
				return false;	
		}
		return true;
	}

}
