// RouteDAO.java
package com.bus.dao;
import com.bus.model.Route; import com.bus.util.DB;
import java.sql.*; import java.util.*;
public class RouteDAO {
    public List<Route> listByDirection(String dir) throws Exception {
        List<Route> list=new ArrayList<>();
        try(Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
          "SELECT id,direction,name FROM routes WHERE direction=? ORDER BY name")){
          ps.setString(1, dir); ResultSet rs=ps.executeQuery();
          while(rs.next()){ Route r=new Route(); r.id=rs.getInt(1); r.direction=rs.getString(2); r.name=rs.getString(3); list.add(r); }
        }
        return list;
    }
    public Route get(int id) throws Exception {
        try(Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
          "SELECT id,direction,name FROM routes WHERE id=?")){
          ps.setInt(1,id); ResultSet rs=ps.executeQuery(); if(rs.next()){ Route r=new Route();
          r.id=rs.getInt(1); r.direction=rs.getString(2); r.name=rs.getString(3); return r;} return null;
        }
    }
}
