package com.library.controller;
import com.library.dao.MemberDAO;
import com.library.model.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/members")
public class MemberServlet extends HttpServlet {
 private final MemberDAO dao=new MemberDAO();
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
  guard(req,res);if(res.isCommitted())return;
  try{req.setAttribute("members",dao.findAll());req.getRequestDispatcher("/members.jsp").forward(req,res);}
  catch(Exception e){throw new ServletException(e);}
 }
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
  guard(req,res);if(res.isCommitted())return;
  try{
   if("delete".equals(req.getParameter("action")))dao.delete(Integer.parseInt(req.getParameter("id")));
   else{
    Member m=new Member();m.name=req.getParameter("name");m.email=req.getParameter("email");
    m.phone=req.getParameter("phone");m.address=req.getParameter("address");dao.save(m);
   }
   res.sendRedirect(req.getContextPath()+"/members");
  }catch(Exception e){throw new ServletException(e);}
 }
 private void guard(HttpServletRequest req,HttpServletResponse res)throws IOException{
  if(req.getSession(false)==null||req.getSession(false).getAttribute("user")==null)
   res.sendRedirect(req.getContextPath()+"/index.html");
 }
}