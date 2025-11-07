// FavoriteDAO.java
package com.bus.dao;
import com.bus.model.Route; import com.bus.util.DB;
import java.sql.*; import java.util.*;
public class FavoriteDAO {
    public void add(int userId,int routeId) throws Exception {
        try(Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
          "INSERT IGNORE INTO favorites(user_id,route_id) VALUES(?,?)")){
          ps.setInt(1,userId); ps.setInt(2,routeId); ps.executeUpdate();
        }
    }
    public void remove(int userId,int routeId) throws Exception {
        try(Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
          "DELETE FROM favorites WHERE user_id=? AND route_id=?")){
          ps.setInt(1,userId); ps.setInt(2,routeId); ps.executeUpdate();
        }
    }
    public List<Route> list(int userId,String dir) throws Exception {
        List<Route> list=new ArrayList<>();
        String sql="SELECT r.id,r.direction,r.name FROM favorites f JOIN routes r ON f.route_id=r.id WHERE f.user_id=? ";
        if(dir!=null) sql += "AND r.direction=? ";
        sql += "ORDER BY r.name";
        try(Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(sql)){
          ps.setInt(1,userId); if(dir!=null) ps.setString(2,dir); ResultSet rs=ps.executeQuery();
          while(rs.next()){ Route r=new Route(); r.id=rs.getInt(1); r.direction=rs.getString(2); r.name=rs.getString(3); list.add(r);}
        }
        return list;
    }
}
