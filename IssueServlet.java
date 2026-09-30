package com.library.controller;
import com.library.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/issue")
public class IssueServlet extends HttpServlet {
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
  guard(req,res);if(res.isCommitted())return;
  try(Connection c=DBConnection.getConnection();Statement s=c.createStatement()){
   req.setAttribute("books",s.executeQuery("SELECT id,title,available_copies FROM books WHERE available_copies>0"));
   req.setAttribute("members",s.executeQuery("SELECT id,name FROM members ORDER BY name"));
   req.getRequestDispatcher("/issue.jsp").forward(req,res);
  }catch(SQLException e){throw new ServletException(e);}
 }
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
  guard(req,res);if(res.isCommitted())return;
  int book=Integer.parseInt(req.getParameter("book_id")),member=Integer.parseInt(req.getParameter("member_id"));
  try(Connection c=DBConnection.getConnection()){
   c.setAutoCommit(false);
   try(PreparedStatement p=c.prepareStatement("SELECT available_copies FROM books WHERE id=? FOR UPDATE")){
    p.setInt(1,book);try(ResultSet r=p.executeQuery()){if(!r.next()||r.getInt(1)<=0)throw new SQLException("Book unavailable");}
   }
   try(PreparedStatement p=c.prepareStatement("INSERT INTO issued_books(book_id,member_id,issue_date,due_date) VALUES(?,?,CURDATE(),DATE_ADD(CURDATE(),INTERVAL 14 DAY))")){
    p.setInt(1,book);p.setInt(2,member);p.executeUpdate();
   }
   try(PreparedStatement p=c.prepareStatement("UPDATE books SET available_copies=available_copies-1 WHERE id=?")){
    p.setInt(1,book);p.executeUpdate();
   }
   c.commit();res.sendRedirect(req.getContextPath()+"/returns?issued=1");
  }catch(Exception e){throw new ServletException(e);}
 }
 private void guard(HttpServletRequest req,HttpServletResponse res)throws IOException{
  if(req.getSession(false)==null||req.getSession(false).getAttribute("user")==null)
   res.sendRedirect(req.getContextPath()+"/index.html");
 }
}