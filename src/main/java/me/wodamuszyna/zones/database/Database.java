package me.wodamuszyna.zones.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.bukkit.configuration.Configuration;

import java.sql.*;

public class Database {
    public Connection conn;

    public Database(Configuration c) { this.c = c; }
    private Configuration c;

    private ResultSet _query(String var1, Object... args) throws SQLException {
        if (isNotConnected())
            connect();
        PreparedStatement st = this.conn.prepareStatement(var1);
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Timestamp) {
                st.setTimestamp(i + 1, (Timestamp)args[i]);
            } else {
                st.setObject(i + 1, args[i]);
            }
        }
        return st.executeQuery();
    }

    private int _update(String request, Object... args) throws SQLException {
        if (isNotConnected())
            connect();
        PreparedStatement st = this.conn.prepareStatement(request);
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Timestamp) {
                st.setTimestamp(i + 1, (Timestamp)args[i]);
            } else {
                st.setObject(i + 1, args[i]);
            }
        }
        int r = st.executeUpdate();
        st.close();
        return r;
    }

    public boolean connect() {
        MysqlDataSource mysql = new MysqlDataSource();
        mysql.setServerName(this.c.getString("mysql.hostname"));
        mysql.setDatabaseName(this.c.getString("mysql.database"));
        mysql.setUser(this.c.getString("mysql.username"));
        mysql.setPassword(this.c.getString("mysql.password"));
        mysql.setPort(this.c.getInt("mysql.port"));
        try {
            this.conn = mysql.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isNotConnected();
    }

    public void disconnect() {
        try {
            this.conn.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }
    }

    public Connection getConnection() { return this.conn; }

    public boolean isNotConnected() {
        try {
            return (this.conn == null || this.conn.isClosed());
        } catch (SQLException var2) {
            var2.printStackTrace();
            return false;
        }
    }
    public ResultSet query(String var1, Object... vars) {
        try {
            return _query(var1, vars);
        } catch (SQLException localSQLException1) {
            try {
                return _query(var1, vars);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    } public int update(String request, Object... args) {
        try {
            return _update(request, args);
        } catch (SQLException localSQLException1) {
            try {
                return _update(request, args);
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            }
        }
    }
}
