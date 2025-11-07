package com.bus.util;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class DB {
    private static DataSource ds;
    static {
        try { ds = (DataSource)new InitialContext().lookup("java:/comp/env/jdbc/busfav"); }
        catch(Exception e){ throw new RuntimeException(e); }
    }
    public static Connection get() throws Exception { return ds.getConnection(); }
}
