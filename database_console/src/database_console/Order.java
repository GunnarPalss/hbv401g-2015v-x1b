/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database_console;

import java.util.ArrayList;

public class Order {
    
    private ArrayList<SubOrder> orders = new ArrayList();

    private class SubOrder{
        
        public final String SQL;
        public final int returnSize;
        
        public SubOrder(String SQL, int size){
            this.SQL = SQL;
            this.returnSize = size;
        }
    }
    
    public void UAtoOrder(UserAccount UA){
        
    }
    public void DBBtoOrder(DatabaseBook DBB){
        
    }
    
    public String[] receiveDataToOrder(String data, int size){
        return new String[0];
    }
    
}
