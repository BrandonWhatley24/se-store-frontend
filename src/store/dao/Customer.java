package store.dao;

public class Customer {    
    
    private int custID;
    private String custPass;
    
    public Customer(int custID, String custPass){
        this.custID = custID;
        this.custPass = custPass;
    }
        
    public Customer(int custID){
        this.custID = custID;
    }    
    
    public int getCustID() {
        return custID;
    }

    public String getCustPass() {
        return custPass;
    }

    public void setCustPass(String custPass) {
        this.custPass = custPass;
    }
    
    // Print Test
    public String toString(){
		return custID + ": " + custPass;
    }

}
