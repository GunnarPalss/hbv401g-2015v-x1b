/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database_console;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
//import java.sql.PreparedStatement;
import java.util.ArrayList;

public class DataPort {

    /**
     * @param args the command line arguments
     */

    private final String host = "jdbc:derby://localhost:1527/BookExchangeDatabase";
    private final String uName = "administrator";
    private final String uPass = "administrator";
    
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    /*private PreparedStatement prepStmt;
    
    private final String add = "INSERT INTO ? VALUES(?)";
    private final String selectAll = "SELECT * FROM ?"; 
    private final String selectSome = "SELECT * FROM ? WHERE ?";
    private final String update = "UPDATE ? SET ? WHERE ?";
    private final String delete = "DELETE FROM ? WHERE ?";*/
    
    public DataPort(){}
    
    public void connect(){
        
      try{

        con = DriverManager.getConnection( host, uName, uPass );
        stmt = con.createStatement();
        //prepStmt = con.prepareStatement(add);
        //prepStmt.setString(1,"asdf");
        
        }
        catch (SQLException err) {
            System.out.println( err.getMessage());
        }  
      
    }
    
    public void disconnect(){
        try{
            if (stmt != null) { stmt.close(); }
        } catch (SQLException err) {
            System.out.println( err.getMessage());
        }  
        
    }
    
    public static boolean isWhiteListed(String s){
        String acceptableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789-";
        for(int i = 0; i < s.length(); i++){
            if(!acceptableChars.contains(Character.toString(s.charAt(i)))){return false;}
        }
        return true;
    }
    
    //Notað til að fá upplýsingar úr gagnagrunni:
    public ArrayList<String[]> executeAndReturn(String SQL, int size){
        
        try{
            String[] data = new String[size];
            ArrayList<String[]> list = new ArrayList();
            this.rs = stmt.executeQuery(SQL);
            while(rs.next()){
                for(int i = 0; i < size; i++){
                    data[i] = rs.getString(i+1);
                }
                list.add(data);
            }
            return list;
        } catch (SQLException err) {
            System.out.println( err.getMessage());
            return null;
        }
    }
    
    //Notað til að setja upplýsingar inn í gagnagrunn/breyta upplýsingum í gagnagrunni:
    public void execute(String SQL){
        try{
            stmt.executeUpdate(SQL);
        } catch (SQLException err) {
            System.out.println( err.getMessage());
        }
        
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        DataPort port = new DataPort();
        port.connect();
        //connection.executeAndReturn("SELECT * FROM accounts WHERE username = 'shs'", 3);
        System.out.println("This is DataPort");
    }
    
}
