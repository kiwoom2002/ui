package com.bus.dao;

import com.bus.model.Booking;
import com.bus.util.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public void create(int userId, int tripId) throws Exception {
        try (Connection c = DB.get()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps1 = c.prepareStatement(
                        "UPDATE trips SET seats_left = seats_left - 1 " +
                        "WHERE id = ? AND seats_left > 0");
                 PreparedStatement ps2 = c.prepareStatement(
                        "INSERT INTO bookings(user_id, trip_id) VALUES(?, ?)")) {

                ps1.setInt(1, tripId);
                int ok = ps1.executeUpdate();
                if (ok == 0) throw new SQLException("좌석이 없습니다.");

                ps2.setInt(1, userId);
                ps2.setInt(2, tripId);
                ps2.executeUpdate();

                c.commit();
            } catch (Exception e) {
                c.rollback();
                throw e;
            }
        }
    }

    /** 
     * includeCancelled = false  → CONFIRMED만
     * includeCancelled = true   → 모든 상태
     */
    public List<Booking> listByUser(int userId, boolean includeCancelled) throws Exception {
        List<Booking> list = new ArrayList<>();
        String sql =
            "SELECT b.id, b.status, t.id, t.depart_date, t.depart_time, t.fare, r.name " +
            "FROM bookings b " +
            "JOIN trips t ON b.trip_id = t.id " +
            "JOIN routes r ON t.route_id = r.id " +
            "WHERE b.user_id = ? ";

        if (!includeCancelled) {
            sql += "AND b.status = 'CONFIRMED' ";
        }
        sql += "ORDER BY b.id DESC";

        try (Connection c = DB.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking();
                b.id = rs.getInt(1);
                b.status = rs.getString(2);
                b.tripId = rs.getInt(3);
                b.date = rs.getDate(4).toString();
                b.time = rs.getTime(5).toString();
                b.fare = rs.getInt(6);
                b.routeName = rs.getString(7);
                list.add(b);
            }
        }
        return list;
    }

    public void cancel(int bookingId, int userId) throws Exception {
        try (Connection c = DB.get()) {
            c.setAutoCommit(false);
            try (PreparedStatement incSeat = c.prepareStatement(
                        "UPDATE trips t " +
                        "JOIN bookings b ON b.trip_id = t.id " +
                        "SET t.seats_left = t.seats_left + 1 " +
                        "WHERE b.id = ? AND b.user_id = ? AND b.status = 'CONFIRMED'");
                 PreparedStatement cancel = c.prepareStatement(
                        "UPDATE bookings SET status = 'CANCELLED' " +
                        "WHERE id = ? AND user_id = ? AND status = 'CONFIRMED'")) {

                incSeat.setInt(1, bookingId);
                incSeat.setInt(2, userId);
                incSeat.executeUpdate();

                cancel.setInt(1, bookingId);
                cancel.setInt(2, userId);
                cancel.executeUpdate();

                c.commit();
            } catch (Exception e) {
                c.rollback();
                throw e;
            }
        }
    }
    public int deleteIfCancelled(int bookingId, int userId) throws Exception {
        try (Connection c = DB.get();
             PreparedStatement ps = c.prepareStatement(
                "DELETE FROM bookings WHERE id=? AND user_id=? AND status='CANCELLED'")) {
            ps.setInt(1, bookingId);
            ps.setInt(2, userId);
            return ps.executeUpdate(); // 1이면 삭제됨, 0이면 조건 불일치
        }
    }

    // 일괄 삭제: 내 계정의 CANCELLED 모두 삭제
    public int purgeCancelledByUser(int userId) throws Exception {
        try (Connection c = DB.get();
             PreparedStatement ps = c.prepareStatement(
                "DELETE FROM bookings WHERE user_id=? AND status='CANCELLED'")) {
            ps.setInt(1, userId);
            return ps.executeUpdate(); // 삭제된 행 수 반환
        }
    }
}
