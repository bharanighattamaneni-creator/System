package com.library.dao;
import com.library.model.Member;
import com.library.util.DBConnection;
import java.sql.*;
import java.util.*;

public class MemberDAO {
 public List<Member> findAll()throws SQLException{
  List<Member> list=new ArrayList<>();
  try(Connection c=DBConnection.getConnection();
      PreparedStatement p=c.prepareStatement("SELECT * FROM members ORDER BY id DESC");
      ResultSet r=p.executeQuery()){
   while(r.next()) list.add(new Member(r.getInt("id"),r.getString("name"),r.getString("email"),
    r.getString("phone"),r.getString("address")));
  }
  return list;
 }
 public void save(Member m)throws SQLException{
  try(Connection c=DBConnection.getConnection();
      PreparedStatement p=c.prepareStatement("INSERT INTO members(name,email,phone,address) VALUES(?,?,?,?)")){
   p.setString(1,m.name);p.setString(2,m.email);p.setString(3,m.phone);p.setString(4,m.address);p.executeUpdate();
  }
 }
 public void delete(int id)throws SQLException{
  try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM members WHERE id=?")){
   p.setInt(1,id);p.executeUpdate();
  }
 }
}