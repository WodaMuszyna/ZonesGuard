package me.wodamuszyna.zones.util;

import me.wodamuszyna.zones.data.Zone;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {
    public static boolean isInZone(Zone z, Location loc) {
        return loc.toVector().isInAABB(z.getMin().toVector(), z.getMax().toVector());
    }

    public static Location getLocation(String str) {
        String[] s = str.split(":");
        return new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]), Float.parseFloat(s[4]), Float.parseFloat(s[5]));
    }

    public static String toString(Location loc) { return loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getPitch() + ":" + loc.getYaw(); }
}
