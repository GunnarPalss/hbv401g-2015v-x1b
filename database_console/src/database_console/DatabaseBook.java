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
    
    /* Unimplemented:
    
    public final String publisher;
    public final int year;
    public final String category;
    public final String subcategory;
    
    */
    
    public DatabaseBook(int ISBN, String title, String authors, int edition, int price, String description, String pictureURL){
        this.ISBN = ISBN;
        this.title = title;
        this.authors = authors;
        this.edition = edition;
        this.price = price;
        this.description = description;
        this.imageURL = pictureURL;
    }
    
    private DatabaseBook(){
        ISBN = edition = price = 0;
        title = authors = description = imageURL = null;
    }
    
    private DatabaseBook(int ISBN, String title, String authors, int edition){
        this.ISBN = ISBN;
        this.title = title;
        this.authors = authors;
        this.edition = edition;
    }
    
    public static DatabaseBook stringToDBB(String[] data) throws IllegalArgumentException{
        if (data.length == 7){
            return new DatabaseBook(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]), data[5], data[6]);
        }
        else{
            throw new IllegalArgumentException("Input, String[], must have length 4 or 7.");
        }
    }
    
    public static void createBook(int ISBN, String title, String authors, int edition, int price, String description, String imageURL){
        if(existsISBN(ISBN)){deleteBook(ISBN);}
        
        String SQL = "INSERT INTO databasebook VALUES('" 
                + Integer.toString(ISBN) + "','" + title + "','"
                + authors + "','"
                + Integer.toString(edition) + "','"
                + Integer.toString(price) + "','"
                + description + "','"
                + imageURL + "')";
        DataPort.get().execute(SQL);
    }
    
    private static void deleteBook(int ISBN){
        String SQL = "DELETE FROM databasebooks WHERE isbn = " + Integer.toString(ISBN);
        DataPort.get().execute(SQL);
    }
    
    private static void deleteBook(String title){
        String SQL1 = "DELETE FROM databasebook WHERE title = '" + title + "'";
        DataPort.get().execute(SQL1);
    }
    
    //Ath. fallið createBook notar fallið deleteBook. Þess vegna er annað fall, eraseBook, til að fjarlægja einnig öll UserBook með sama ISBN.
    public static void eraseBook(int ISBN){
        deleteBook(ISBN);
        UserBook.deleteBook(ISBN);
    }
    
    public static DatabaseBook getBook(int ISBN){
        String SQL = "SELECT * FROM databasebook WHERE isbn = " + Integer.toString(ISBN);
        ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
        return stringToDBB(temp.get(0));
    }
    
    public static DatabaseBook getBook(String title){
        String SQL = "SELECT * FROM databasebook WHERE title = '" + title +"'";
        ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
        return stringToDBB(temp.get(0));
    }
    
    public static ArrayList<DatabaseBook> getAll(){
        String SQL = "SELECT * FROM databasebook";
        ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
        ArrayList<DatabaseBook> dbbArray = new ArrayList();
        for(String[] DBB: temp){
                dbbArray.add(stringToDBB(DBB));
            }
        return dbbArray;
    }
    
    public static ArrayList<DatabaseBook> searchTitle(String title){
        String SQL = "SELECT * FROM databasebook WHERE title LIKE '%" + title + "%'";
        ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
        ArrayList<DatabaseBook> dbbArray = new ArrayList();
        for(String[] DBB: temp){
                dbbArray.add(stringToDBB(DBB));
            }
        return dbbArray;
    }
    
    public static ArrayList<DatabaseBook> searchAuthor(String author){
        String SQL = "SELECT * FROM databasebook WHERE author LIKE '%" + author + "%'";
        ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
        ArrayList<DatabaseBook> dbbArray = new ArrayList();
        for(String[] DBB: temp){
                dbbArray.add(stringToDBB(DBB));
            }
        return dbbArray;
    }
    
    public static boolean existsISBN(int ISBN){
        String SQL = "SELECT * FROM databasebook WHERE isbn =" + Integer.toString(ISBN);
        ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
        return !temp.isEmpty();
    }
    
    public static boolean existsTitle(String title){
        String SQL = "SELECT * FROM databasebook WHERE isbn =" + title;
        ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
        return !temp.isEmpty();
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
        DataPort.get().connect();
        
    }
    
}
