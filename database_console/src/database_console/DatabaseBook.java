/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database_console;

import java.util.ArrayList;

public class DatabaseBook {
    
    public final int ISBN;
    public final String title;
    public final String authors;
    public final int edition;
    private int price;
    private String description;
    private String imageURL;
    
    private static DataPort port = new DataPort();
    
    public DatabaseBook(int ISBN, String title, String authors, int edition, int price, String description, String pictureURL){
        this.ISBN = ISBN;
        this.title = title;
        this.authors = authors;
        this.edition = edition;
        this.price = price;
        this.description = description;
        this.imageURL = pictureURL;
    }
    
    public DatabaseBook(){
        ISBN = edition = price = 0;
        title = authors = description = imageURL = null;
    }
    
    public DatabaseBook(int ISBN, String title, String authors, int edition){
        this.ISBN = ISBN;
        this.title = title;
        this.authors = authors;
        this.edition = edition;
    }
    
    public static DatabaseBook stringToDBB(String[] data) throws IllegalArgumentException{
        if(data.length == 4){
            return new DatabaseBook(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]));
        }
        else if (data.length == 7){
            return new DatabaseBook(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]), data[5], data[6]);
        }
        else{
            throw new IllegalArgumentException("Input, String[], must have length 4 or 7.");
        }
    }
    
    public static void createBook(int ISBN, String title, String authors, int edition, int price, String description, String imageURL){
        String SQL = "INSERT INTO databasebooks VALUES('" 
                + Integer.toString(ISBN) + "','" + title + "','"
                + authors + "','"
                + Integer.toString(edition) + "','"
                + Integer.toString(price) + "','"
                + description + "','"
                + imageURL + "')";
        DatabaseBook.port.execute(SQL);
    }
    
    public static void createBook(int ISBN, String title, String authors, int edition){
        String SQL = "INSERT INTO databasebooks VALUES('" 
                + Integer.toString(ISBN) + "','" + title + "','"
                + authors + "','"
                + Integer.toString(edition) + "')";
        DatabaseBook.port.execute(SQL);
    }
    
    public static void deleteBook(int ISBN){
        String SQL = "DELETE FROM databasebooks WHERE isbn = " + Integer.toString(ISBN);
        DatabaseBook.port.execute(SQL);
    }
    
    public static void deleteBook(String title){
        String SQL1 = "DELETE FROM databasebooks WHERE title = '" + title + "'";
        DatabaseBook.port.execute(SQL1);
        //UserBook.deleteBook(title);
    }
    
    public static DatabaseBook getBook(int ISBN){
        String SQL = "SELECT * FROM databasebooks WHERE isbn = " + Integer.toString(ISBN);
        ArrayList<String[]> temp = DatabaseBook.port.executeAndReturn(SQL, 7);
        return stringToDBB(temp.get(0));
    }
    
    public static DatabaseBook getBook(String title){
        String SQL = "SELECT * FROM databasebooks WHERE title = '" + title +"'";
        ArrayList<String[]> temp = DatabaseBook.port.executeAndReturn(SQL, 7);
        return stringToDBB(temp.get(0));
    }
    
    public static ArrayList<DatabaseBook> getAll(){
        String SQL = "SELECT * FROM databasebooks";
        ArrayList<String[]> temp = DatabaseBook.port.executeAndReturn(SQL, 7);
        ArrayList<DatabaseBook> dbbArray = new ArrayList();
        for(String[] DBB: temp){
                dbbArray.add(stringToDBB(DBB));
            }
        return dbbArray;
    }
    
    public static ArrayList<DatabaseBook> searchTitle(String title){
        String SQL = "SELECT * FROM databasebooks WHERE title Like '%" + title + "%'";
        ArrayList<String[]> temp = DatabaseBook.port.executeAndReturn(SQL, 7);
        ArrayList<DatabaseBook> dbbArray = new ArrayList();
        for(String[] DBB: temp){
                dbbArray.add(stringToDBB(DBB));
            }
        return dbbArray;
    }
    
    public static ArrayList<DatabaseBook> searchAuthor(String author){
        String SQL = "SELECT * FROM databasebooks WHERE title Like '%" + author + "%'";
        ArrayList<String[]> temp = DatabaseBook.port.executeAndReturn(SQL, 7);
        ArrayList<DatabaseBook> dbbArray = new ArrayList();
        for(String[] DBB: temp){
                dbbArray.add(stringToDBB(DBB));
            }
        return dbbArray;
    }
    
    public void editPrice(int price){
        String SQL = "UPDATE databasebooks SET price = " + Integer.toString(price) + " WHERE isbn = " + this.ISBN;
        DatabaseBook.port.execute(SQL);
    }
    
    public void editDescription(String description){
        String SQL = "UPDATE databasebooks SET description = '" + description + "' WHERE isbn = " + this.ISBN;
        DatabaseBook.port.execute(SQL);
    }
    
    public void editImageURL(String imageURL){
        String SQL = "UPDATE databasebooks SET imageurl = '" + imageURL + "' WHERE isbn = " + this.ISBN;
        DatabaseBook.port.execute(SQL);
    }
    
    public int getPrice(){
        return this.price;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public String getImageURL(){
        return this.imageURL;
    }
    
    public static void main(String[] args){
        
        System.out.println("This is DatabaseBook");
        DatabaseBook.port.connect();
        
    }
    
}
