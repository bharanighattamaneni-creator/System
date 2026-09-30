package com.library.controller;
import com.library.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/returns")
public class ReturnsServlet extends HttpServlet {
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
  if(req.getSession(false)==null||req.getSession(false).getAttribute("user")==null){
   res.sendRedirect(req.getContextPath()+"/index.html");return;
  }
  String q="SELECT i.id,b.title,m.name,i.issue_date,i.due_date,i.return_date,i.fine,i.status FROM issued_books i JOIN books b ON b.id=i.book_id JOIN members m ON m.id=i.member_id ORDER BY i.id DESC";
  try(Connection c=DBConnection.getConnection();Statement s=c.createStatement()){
   req.setAttribute("records",s.executeQuery(q));req.getRequestDispatcher("/returns.jsp").forward(req,res);
  }catch(SQLException e){throw new ServletException(e);}
 }
}