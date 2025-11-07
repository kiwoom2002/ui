// TripDAO.java
package com.bus.dao;
import com.bus.model.Trip; import com.bus.util.DB;
import java.sql.*; import java.util.*;
public class TripDAO {
    public List<Trip> findTrips(int routeId, java.sql.Date fromDate) throws Exception {
        List<Trip> list=new ArrayList<>();
        try(Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
          "SELECT t.id,t.depart_date,t.depart_time,t.kind,t.fare,t.seats_left,r.name,r.direction " +
          "FROM trips t JOIN routes r ON t.route_id=r.id " +
          "WHERE t.route_id=? AND t.depart_date>=? ORDER BY t.depart_date,t.depart_time")){
          ps.setInt(1,routeId); ps.setDate(2,fromDate); ResultSet rs=ps.executeQuery();
          while(rs.next()){ Trip t=new Trip();
            t.id=rs.getInt(1); t.date=rs.getDate(2).toString(); t.time=rs.getTime(3).toString();
            t.kind=rs.getString(4); t.fare=rs.getInt(5); t.seatsLeft=rs.getInt(6);
            t.routeName=rs.getString(7); t.direction=rs.getString(8); list.add(t);
          }
        }
        return list;
    }
}
