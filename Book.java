package com.library.model;
public class Book {
 public int id,totalCopies,availableCopies;
 public String title,author,isbn,category;
 public Book(){}
 public Book(int id,String title,String author,String isbn,String category,int total,int available){
  this.id=id;this.title=title;this.author=author;this.isbn=isbn;this.category=category;
  this.totalCopies=total;this.availableCopies=available;
 }
}