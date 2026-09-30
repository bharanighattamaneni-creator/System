package com.library.dao;
import com.library.model.Book;
import com.library.util.DBConnection;
import java.sql.*;
import java.util.*;

public class BookDAO {
 public List<Book> findAll() throws SQLException {
  List<Book> list=new ArrayList<>();
  String q="SELECT * FROM books ORDER BY id DESC";
  try(Connection c=DBConnection.getConnection();
      PreparedStatement p=c.prepareStatement(q);
      ResultSet r=p.executeQuery()){
   while(r.next()) list.add(new Book(r.getInt("id"),r.getString("title"),r.getString("author"),
    r.getString("isbn"),r.getString("category"),r.getInt("total_copies"),r.getInt("available_copies")));
  }
  return list;
 }
 public void save(Book b)throws SQLException{
  String q="INSERT INTO books(title,author,isbn,category,total_copies,available_copies) VALUES(?,?,?,?,?,?)";
  try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(q)){
   p.setString(1,b.title);p.setString(2,b.author);p.setString(3,b.isbn);p.setString(4,b.category);
   p.setInt(5,b.totalCopies);p.setInt(6,b.totalCopies);p.executeUpdate();
  }
 }
 public void delete(int id)throws SQLException{
  try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM books WHERE id=?")){
   p.setInt(1,id);p.executeUpdate();
  }
 }
}