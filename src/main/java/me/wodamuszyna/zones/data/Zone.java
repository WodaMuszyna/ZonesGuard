package me.wodamuszyna.zones.data;

import me.wodamuszyna.zones.Main;
import me.wodamuszyna.zones.util.DbUtil;
import me.wodamuszyna.zones.util.LocationUtil;
import org.bukkit.Location;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class Zone {
    private String name;
    private Location min;
    private Location max;
    private Map<StateFlag, StateFlag.State> flags;
    private boolean dirty;

    public Zone(ResultSet rs) throws SQLException{
        this.name = rs.getString("name");
        this.min = LocationUtil.getLocation(rs.getString("min"));
        this.max = LocationUtil.getLocation(rs.getString("max"));
        Blob blob = rs.getBlob("flags");
        this.flags = DbUtil.deserializeFlags(blob.getBytes(1L, (int)blob.length()));
        blob.free();
        this.dirty = false;
        Main.getManager().addZone(this);
    }

    public Zone(String name, Location min, Location max){
        this.name = name;
        this.min = min;
        this.max = max;
        this.flags = StateFlag.Registry.getFlags();
        try {
            insert();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        this.dirty = false;
        Main.getManager().addZone(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getMin() {
        return min;
    }

    public void setMin(Location min) {
        this.min = min;
    }

    public Location getMax() {
        return max;
    }

    public void setMax(Location max) {
        this.max = max;
    }

    public Map<StateFlag, StateFlag.State> getFlags() {
        return flags;
    }

    public void setFlags(Map<StateFlag, StateFlag.State> flags) {
        this.flags = flags;
    }

    public boolean isDirty(){ return dirty; }

    public void markDirty(){ this.dirty = true; }

    public void insert() throws SQLException { Main.getInstance().db.update("INSERT INTO `zonesguard_zones` (`name`, `min`, `max`, `flags`) VALUES (?, ?, ?, ?)", this.name, LocationUtil.toString(this.min), LocationUtil.toString(this.max), new SerialBlob(DbUtil.serializeFlags(flags)));}
}
