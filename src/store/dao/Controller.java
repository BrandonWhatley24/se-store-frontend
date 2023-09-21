package store.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    
 @Autowired
 private CustomerDAO cDao;
 private InventoryDAO iDao;
 private OrderDAO oDao;
 
 @GetMapping("/customer/getAll")
 public List<Customer> getAllCustomers() {
     return cDao.getAll();
 }
 
 @GetMapping("/customer/getItem")
 public Customer getCustomer(int key) {
	 return cDao.getItem(key);
 }
 
 @GetMapping("/inventory/getAll")
 public List<Inventory> getAllInventory() {
     return iDao.getAll();
 }
 
 @GetMapping("/inventory/getItem")
 public Inventory get(int key) {
     return iDao.getItem(key);
 }
 
 @GetMapping("/inventory/updateQty")
 public void updateQtyOnHand(int invID, int qty){
	 iDao.updateQtyOnHand(invID, qty);
 } 
	 
 @GetMapping("/order/getAll")
 public List<Order> getAllOrders() {
     return oDao.getAll();
 }
 
 @GetMapping("/order/getCustOrders")
 public List<Order> getCustOrders(int key){
     return oDao.getCustOrders(key);
 }
 
 @GetMapping("/order/getItem")
 public Order getOrder(int key) {
	 return oDao.getItem(key);
 }
 
 @GetMapping("/order/insert")
 public void insert(Order item){
     insert(item);
 }
}