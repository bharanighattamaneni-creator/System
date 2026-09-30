package com.library.controller;
import com.library.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/return")
public class ReturnServlet extends HttpServlet {
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
  if(req.getSession(false)==null||req.getSession(false).getAttribute("user")==null){
   res.sendRedirect(req.getContextPath()+"/index.html");return;
  }
  int id=Integer.parseInt(req.getParameter("id"));
  try(Connection c=DBConnection.getConnection()){
   c.setAutoCommit(false);int bookId;
   try(PreparedStatement p=c.prepareStatement("SELECT book_id FROM issued_books WHERE id=? AND status='ISSUED' FOR UPDATE")){
    p.setInt(1,id);try(ResultSet r=p.executeQuery()){if(!r.next())throw new SQLException("Issue record not found");bookId=r.getInt(1);}
   }
   try(PreparedStatement p=c.prepareStatement("UPDATE issued_books SET return_date=CURDATE(),fine=GREATEST(DATEDIFF(CURDATE(),due_date),0)*5,status='RETURNED' WHERE id=?")){
    p.setInt(1,id);p.executeUpdate();
   }
   try(PreparedStatement p=c.prepareStatement("UPDATE books SET available_copies=available_copies+1 WHERE id=?")){
    p.setInt(1,bookId);p.executeUpdate();
   }
   c.commit();res.sendRedirect(req.getContextPath()+"/returns?returned=1");
  }catch(Exception e){throw new ServletException(e);}
 }
}