package store.dao;

import store.sec.Parms;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class CustomerDAO implements DataAccess<Customer>{
   
	Connection con;
    PreparedStatement pstmt;
        
    String dbuser = Parms.dbuser;
    String dbpass = Parms.dbpass;
    String dburl = Parms.dburl;
	
    public List<Customer> getAll(){
        
        List<Customer> list = new LinkedList<Customer>();
        
        try{
            Class.forName("com.ibm.db2.jcc.DB2Driver");    
            con = DriverManager.getConnection(dburl, dbuser, dbpass);
            
            String sql = "SELECT * FROM CUSTOMERS";

            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();             
            
            while(rs.next()){
                Customer cust = new Customer(rs.getInt(1), rs.getString(2));
                list.add(cust);
            }
            
            con.close();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return list;
    }
    
    public Customer getItem(int key){
        
        Customer cust = null;
        
        try{
            Class.forName("com.ibm.db2.jcc.DB2Driver");            
            con = DriverManager.getConnection(dburl, dbuser, dbpass);
            
            String sql = "SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID = (?)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(key));
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
            	cust = new Customer(rs.getInt(1), rs.getString(2));
            }
            
            con.close();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return cust;       
    }
    
    public void update(Customer item){
         
    }
    
    public void delete(Customer item){
        
    }
    
    public void insert(Customer item){
        
    }
}
