// UserDAO.java
package com.bus.dao;
import com.bus.model.User; import com.bus.util.DB;
import java.sql.*;
public class UserDAO {
    public User findByUsername(String u) throws Exception {
        try(Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
          "SELECT id,username,password FROM users WHERE username=?")){
          ps.setString(1,u); ResultSet rs=ps.executeQuery();
          if(rs.next()){ User x=new User(); x.id=rs.getInt(1); x.username=rs.getString(2); x.password=rs.getString(3); return x;}
          return null;
        }
    }
    public int create(String u, String p) throws Exception {
        try(Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
          "INSERT INTO users(username,password) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS)){
          ps.setString(1,u); ps.setString(2,p); ps.executeUpdate();
          ResultSet k=ps.getGeneratedKeys(); k.next(); return k.getInt(1);
        }
    }
}
