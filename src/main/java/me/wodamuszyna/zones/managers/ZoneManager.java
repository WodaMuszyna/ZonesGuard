package me.wodamuszyna.zones.managers;

import me.wodamuszyna.zones.Main;
import me.wodamuszyna.zones.data.StateFlag;
import me.wodamuszyna.zones.data.Zone;

import java.security.InvalidParameterException;
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

    public void updateFlag(Zone z, StateFlag flag, StateFlag.State state){
        z.getFlags().replace(flag, state);
    }

    public StateFlag.State parseInput(String s){
        if(s.equalsIgnoreCase("allow")){
            return StateFlag.State.ALLOW;
        }
        if(s.equalsIgnoreCase("deny")){
            return StateFlag.State.DENY;
        }
        if(s.equalsIgnoreCase("none")){
            return null;
        }
        throw new InvalidParameterException("Invalid parameter, expected allow/deny/none");
    }

    public boolean allows(Zone z, StateFlag... flags){
        boolean allows = false;
        for(StateFlag flag : flags){
            if(z.getFlags().get(flag).equals(StateFlag.State.DENY)){
                return false;
            }
            if(z.getFlags().get(flag).equals(StateFlag.State.ALLOW)){
                allows = true;
            }
        }
        return allows;
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
