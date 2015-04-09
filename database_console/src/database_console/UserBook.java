/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database_console;

import java.util.ArrayList;

public class UserBook {
    
    public final int instanceID;
    public final int accountID;
    public final int ISBN;
    private int userPrice;
    private String condition;
    private String pictureURL;
    private static int id = 0;
    
    private UserBook(int instanceID, int accountID, int ISBN, int userPrice, String condition, String pictureURL){
        this.instanceID = instanceID;
        this.accountID = accountID;
        this.ISBN = ISBN;
        this.userPrice = userPrice;
        this.condition = condition;
        this.pictureURL = pictureURL;
    }
    
    public static UserBook stringToUB(String[] data) throws IllegalArgumentException{
        if(data.length == 6){
            return new UserBook(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), data[4], data[5]);
        }
        throw new IllegalArgumentException("Input, String[], must have length 6.");
    }
    
    public static void createBook(int accountID, int ISBN){
        //Ekki gá hvort að eintak með sama ISBN sé nú þegar til. Notandi má vera að selja tvö eintök af sömu bók.
        DatabaseBook copy = DatabaseBook.getBook(ISBN);
        String SQL = "INSERT INTO UserBook VALUES('"
                + Integer.toString(id) + "','"
                + Integer.toString(accountID) + "','"
                + Integer.toString(ISBN) + "','"
                + Integer.toString(0) + "','" //Látum verðið vera 0 sem default. Það er svosem hægt að láta töfluna UserBook gera það sjálfkrafa.
                + "','" //Tómur strengur fyrir condition
                + "'"; //Tómur strengur fyrir pictureURL
        DataPort.get().execute(SQL);
        id++;
    }
    
    public static ArrayList<UserBook> getBooks(int ISBN){
        String SQL = "SELECT * FROM UserBook WHERE isbn =" + Integer.toString(ISBN);
        ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
        ArrayList<UserBook> ubArray = new ArrayList();
        for(String[] UB: temp){
                ubArray.add(stringToUB(UB));
            }
        return ubArray;
    }
    
    public static ArrayList<UserBook> getBooks(String title){
        String SQL = "SELECT * FROM UserBook NATURAL JOIN DatabaseBook WHERE title ='" + title + "'";
        ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
        ArrayList<UserBook> ubArray = new ArrayList();
        for(String[] UB: temp){
                ubArray.add(stringToUB(UB));
            }
        return ubArray;
    }
    
    public static ArrayList<UserBook> getAll(){
        String SQL = "SELECT * FROM UserBook";
        ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
        ArrayList<UserBook> ubArray = new ArrayList();
        for(String[] UB: temp){
                ubArray.add(stringToUB(UB));
            }
        return ubArray;
    }
    
    //Ath. deleteBook eyðir einu eintaki af UserBook með ákv. ISBN, eraseBook eyðir öllum eintökum af UserBook með ákveðið ISBN.
    //Klasinn DatabaseBook notar fallið eraseBook.
    public static void deleteBook(int accountID, int ISBN){
        String SQL = "DELETE FROM UserBook WHERE accountID = " + Integer.toString(accountID) + " AND isbn = " + Integer.toString(ISBN);
        DataPort.get().execute(SQL);
    }
    
    public static void deleteBook(int instanceID){
        String SQL = "DELETE FROM UserBook WHERE instanceID = " + Integer.toString(instanceID);
        DataPort.get().execute(SQL);
    }
    
    //Ath. deleteBook eyðir einu eintaki af UserBook með ákv. ISBN, eraseBook eyðir öllum eintökum af UserBook með ákveðið ISBN.
    //Klasinn DatabaseBook notar fallið eraseBook.
    public static void eraseBook(int ISBN){
        String SQL = "DELETE FROM UserBook WHERE isbn = " + Integer.toString(ISBN);
        DataPort.get().execute(SQL);
    }
    
    public static ArrayList<UserBook> searchAuthor(String author){
        String SQL = "SELECT * FROM databasebooks NATURAL JOIN userbook WHERE title Like '%" + author + "%'";
        ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
        ArrayList<UserBook> ubArray = new ArrayList();
        for(String[] UB: temp){
                ubArray.add(stringToUB(UB));
            }
        return ubArray;
    }
    
    public static ArrayList<UserBook> searchTitle(String title){
        String SQL = "SELECT * FROM databasebooks NATURAL JOIN userbook WHERE title LIKE '%" + title + "%'";
        ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
        ArrayList<UserBook> ubArray = new ArrayList();
        for(String[] UB: temp){
                ubArray.add(stringToUB(UB));
            }
        return ubArray;
    }

    public static boolean existsISBN(int ISBN){
        String SQL = "SELECT * FROM UserBook WHERE isbn =" + Integer.toString(ISBN);
        ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
        return !temp.isEmpty();
    }
    
    public static boolean existsTitle(String title){
        String SQL = "SELECT * FROM UserBook WHERE title ='" + title + "'";
        ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
        return !temp.isEmpty();
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
    
    public static void main(String[] args){
        System.out.println("This is UserBook");
        DataPort.get().connect();
    }
    
}
