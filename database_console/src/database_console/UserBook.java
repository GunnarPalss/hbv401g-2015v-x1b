/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database_console;

import java.util.ArrayList;

public class UserBook {
    
    public final String instanceID;
    public final String accountID;
    public final int ISBN;
    private int userPrice;
    private String condition;
    private String pictureURL;
    
    public UserBook(String instanceID, String accountID, int ISBN){
        this.instanceID = instanceID;
        this.accountID = accountID;
        this.ISBN = ISBN;
    }
    
    public UserBook(String instanceID, String accountID, int ISBN, int userPrice, String condition, String pictureURL){
        this.instanceID = instanceID;
        this.accountID = accountID;
        this.ISBN = ISBN;
        this.userPrice = userPrice;
        this.condition = condition;
        this.pictureURL = pictureURL;
    }
    
    public void editPrice(int price){
        this.userPrice = price;
    }
    
    public void editCondition(String condition){
        this.condition = condition;
    }
    
    public void editPictureURL(String pictureURL){
        this.pictureURL = pictureURL;
    }
    
    public int getPrice(){
        return this.userPrice;
    }
    
    public String getCondition(){
        return this.condition;
    }
    
    public String getPictureURL(){
        return this.pictureURL;
    }
    
}
