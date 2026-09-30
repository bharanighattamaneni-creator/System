package com.library.model;
public class Member {
 public int id;
 public String name,email,phone,address;
 public Member(){}
 public Member(int id,String name,String email,String phone,String address){
  this.id=id;this.name=name;this.email=email;this.phone=phone;this.address=address;
 }
}