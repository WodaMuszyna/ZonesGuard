package me.wodamuszyna.zones.managers;

import me.wodamuszyna.zones.Main;
import me.wodamuszyna.zones.data.StateFlag;
import me.wodamuszyna.zones.data.Zone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZoneManager {
    private List<Zone> zones = new ArrayList<>();

    public List<Zone> getZones(){ return zones; }

    public Zone getZone(String name){
        for(Zone z : zones){
            if(z.getName().equalsIgnoreCase(name)){
                return z;
            }
        }
        return null;
    }

    public void addZone(Zone z){
        if(!zones.contains(z)) {
            zones.add(z);
        }
    }

    public void removeZone(Zone z){
        zones.remove(z);
    }

    public Map<StateFlag, StateFlag.State> getFlags(Zone z){
        return z.getFlags();
    }

    public StateFlag getFlag(Zone z, String name){
        for(StateFlag entry : z.getFlags().keySet()){
            if(entry.getName().equalsIgnoreCase(name)) return entry;
        }
        return null;
    }

    public StateFlag.State getState(Zone z, String name){
        return z.getFlags().get(getFlag(z, name));
    }

    public void updateFlag(Zone z, String name, StateFlag.State state){
        StateFlag flag = StateFlag.Registry.verify(name);
        if(flag != null){
            z.getFlags().replace(flag, state);
        }
    }

    public void init(){
        try {
            ResultSet rs = Main.getInstance().db.query("SELECT * FROM `zonesguard_zones`");
            while (rs.next()){
                new Zone(rs);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
