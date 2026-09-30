package com.library.controller;
import com.library.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
  if(req.getSession(false)==null||req.getSession(false).getAttribute("user")==null){
   res.sendRedirect(req.getContextPath()+"/index.html"); return;
  }
  try(Connection c=DBConnection.getConnection();Statement s=c.createStatement()){
   ResultSet r=s.executeQuery("SELECT COUNT(*),COALESCE(SUM(total_copies),0),COALESCE(SUM(available_copies),0) FROM books");
   if(r.next()){req.setAttribute("bookTitles",r.getInt(1));req.setAttribute("totalCopies",r.getInt(2));req.setAttribute("available",r.getInt(3));}
   r=s.executeQuery("SELECT COUNT(*) FROM members");if(r.next())req.setAttribute("members",r.getInt(1));
   r=s.executeQuery("SELECT COUNT(*) FROM issued_books WHERE status='ISSUED'");if(r.next())req.setAttribute("issued",r.getInt(1));
   req.getRequestDispatcher("/dashboard.jsp").forward(req,res);
  }catch(SQLException e){throw new ServletException(e);}
 }
}