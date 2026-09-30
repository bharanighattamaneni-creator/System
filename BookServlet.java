package com.library.controller;
import com.library.dao.BookDAO;
import com.library.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
 private final BookDAO dao=new BookDAO();
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
  guard(req,res); if(res.isCommitted())return;
  try{req.setAttribute("books",dao.findAll());req.getRequestDispatcher("/books.jsp").forward(req,res);}
  catch(Exception e){throw new ServletException(e);}
 }
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
  guard(req,res); if(res.isCommitted())return;
  try{
   if("delete".equals(req.getParameter("action"))) dao.delete(Integer.parseInt(req.getParameter("id")));
   else{
    Book b=new Book();b.title=req.getParameter("title");b.author=req.getParameter("author");
    b.isbn=req.getParameter("isbn");b.category=req.getParameter("category");
    b.totalCopies=Integer.parseInt(req.getParameter("copies"));dao.save(b);
   }
   res.sendRedirect(req.getContextPath()+"/books");
  }catch(Exception e){throw new ServletException(e);}
 }
 private void guard(HttpServletRequest req,HttpServletResponse res)throws IOException{
  if(req.getSession(false)==null||req.getSession(false).getAttribute("user")==null)
   res.sendRedirect(req.getContextPath()+"/index.html");
 }
}