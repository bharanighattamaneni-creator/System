package com.library.controller;
import com.library.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
  String u=req.getParameter("username"),pw=req.getParameter("password");
  try(Connection c=DBConnection.getConnection();
      PreparedStatement p=c.prepareStatement("SELECT role FROM users WHERE username=? AND password=?")){
   p.setString(1,u);p.setString(2,pw);
   try(ResultSet r=p.executeQuery()){
    if(r.next()){
     req.getSession(true).setAttribute("user",u);
     res.sendRedirect(req.getContextPath()+"/dashboard");
    }else{
     res.sendRedirect(req.getContextPath()+"/index.html?error=1");
    }
   }
  }catch(SQLException e){res.sendRedirect(req.getContextPath()+"/index.html?error=db");}
 }
}