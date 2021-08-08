package me.wodamuszyna.zones.util;

import me.wodamuszyna.zones.Main;
import me.wodamuszyna.zones.data.Zone;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {
    public static boolean isInZone(Zone z, Location loc) {
        int minX = Math.min(z.getMin().getBlockX(), z.getMax().getBlockX());
        int maxX = Math.max(z.getMin().getBlockX(), z.getMax().getBlockX());
        int minY = Math.min(z.getMin().getBlockY(), z.getMax().getBlockY());
        int maxY = Math.max(z.getMin().getBlockY(), z.getMax().getBlockY());
        int minZ = Math.min(z.getMin().getBlockZ(), z.getMax().getBlockZ());
        int maxZ = Math.max(z.getMin().getBlockZ(), z.getMax().getBlockZ());
        if((loc.getBlockX() >= minX && loc.getBlockX() <= maxX)
                && (loc.getBlockY() >= minY && loc.getBlockY() <= maxY)
                && (loc.getBlockZ() >= minZ && loc.getBlockZ() <= maxZ)){
            return true;
        }
        return false;
    }

    public static Zone isInZone(Location loc){
        for(Zone z : Main.getManager().getZones()){
            if(isInZone(z, loc)){
                return z;
            }
        }
        return null;
    }

    public static Location getLocation(String str) {
        String[] s = str.split(":");
        return new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]), Float.parseFloat(s[4]), Float.parseFloat(s[5]));
    }

    public static String toString(Location loc) { return loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getPitch() + ":" + loc.getYaw(); }
}
