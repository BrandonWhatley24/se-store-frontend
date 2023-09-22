package store.dao;

import store.sec.Parms;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.LinkedList;

public class OrderDAO implements DataAccess<Order>{
    
    Connection con;
    PreparedStatement pstmt;
        
    String dbuser = Parms.dbuser;
    String dbpass = Parms.dbpass;
    String dburl = Parms.dburl;    

    String driver = "org.postgresql.Driver";
    
    // Returns a List of ALL Orders
    public List<Order> getAll(){
        
        List<Order> list = new LinkedList<Order>();
        
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(dburl, dbuser, dbpass);
            
            String sql = "SELECT * FROM STOREDB.ORDERS";
            
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                Order order = new Order(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getDate(7), rs.getDate(8));
                list.add(order);
            }
            
            con.close();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return list;
    }
    
    // Returns a List of a specific customer's orders given their CUSTOMER_ID
    public List<Order> getCustOrders(int key){
        
        List<Order> list = new LinkedList<Order>();
        
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(dburl, dbuser, dbpass);
            
            String sql = "SELECT * FROM STOREDB.ORDERS WHERE CUSTOMER_ID = (?) ORDER BY ORDER_ID";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(key));
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                Order order = new Order(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getDate(7), rs.getDate(8));
                list.add(order);
            }
            
            con.close();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return list;
    }
    
    // Returns an Order object from an ORDER_ID
    public Order getItem(int key){
        
        Order order = null;
        
        try{
            Class.forName(driver);                
            con = DriverManager.getConnection(dburl, dbuser, dbpass);           

            String sql = "SELECT * FROM STOREDB.ORDERS WHERE ORDER_ID = (?)";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(key));
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                order = new Order(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getDate(7), rs.getDate(8));
            }
                           
            con.close();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return order;       
    }

    public void update(Order item){
         
    }
    
    public void delete(Order item){

    }
    
    public void insert(Order item){
                
        try{
        	
            Class.forName(driver);    
            con = DriverManager.getConnection(dburl, dbuser, dbpass);
            
            String sql = "INSERT INTO STOREDB.ORDERS (CUSTOMER_ID, INV_ID, QTY, STATUS, PENDING, DATE_ORDERED, DATE_EXPECTED) VALUES( ? , ? , ? , ? , ? , ? , ? )";
        
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, item.getCustID());
            pstmt.setInt(2, item.getInvID());
            pstmt.setInt(3, item.getQty());
            pstmt.setBoolean(4, item.getStatus());
            pstmt.setBoolean(5, item.getPending());
            pstmt.setDate(6, item.getOrderDate());
            pstmt.setDate(7, item.getExpectedDate());
            
            pstmt.execute();
            
            con.close();
            
        }catch(Exception ex){
            ex.printStackTrace();
        } 
        
    }
    
    
}
