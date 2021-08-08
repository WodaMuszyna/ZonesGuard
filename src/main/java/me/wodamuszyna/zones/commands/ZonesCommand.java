package me.wodamuszyna.zones.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.regions.Region;
import me.wodamuszyna.zones.Main;
import me.wodamuszyna.zones.data.StateFlag;
import me.wodamuszyna.zones.data.Zone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class ZonesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        Player p = Bukkit.getPlayer(s.getName());
        if(a[0].equalsIgnoreCase("define")){
            if(a.length < 2){
                s.sendMessage("Correct usage: /zg define <name>");
                return false;
            }
            BukkitPlayer bPlayer = BukkitAdapter.adapt(p);
            try {
                Region region = WorldEdit.getInstance().getSessionManager().get(bPlayer).getSelection(bPlayer.getWorld());
                if(region == null){
                    s.sendMessage("Error! Select the region before defining.");
                    return false;
                }
                Location min = new Location(BukkitAdapter.adapt(region.getWorld()), region.getMinimumPoint().getBlockX(), region.getMinimumPoint().getBlockY(), region.getMinimumPoint().getZ());
                Location max = new Location(BukkitAdapter.adapt(region.getWorld()), region.getMaximumPoint().getBlockX(), region.getMaximumPoint().getBlockY(), region.getMaximumPoint().getZ());
                Zone z = new Zone(a[1], min, max);
                s.sendMessage(String.format("Zone %s has been created", z.getName()));
                return true;
            } catch (IncompleteRegionException e) {
                s.sendMessage("Error! Select the region before defining.");
                return false;
            }
        }
        if(a[0].equalsIgnoreCase("flag")){
            if(a.length < 4){
                s.sendMessage("Correct usage: /zg flag <name> <flag> <allow/deny>");
                return false;
            }
            Zone z = Main.getManager().getZone(a[1]);
            if(z == null){
                s.sendMessage(String.format("Zone %s does not exist", a[1]));
                return false;
            }
            StateFlag flag = Main.getManager().getFlag(z, a[2]);
            if(flag == null){
                s.sendMessage("Invalid flag");
                return false;
            }
            Main.getManager().updateFlag(z, flag, Main.getManager().parseInput(a[3]));
            z.markDirty();
            s.sendMessage(String.format("Value of flag %s has been set to %s", flag.getName(), Main.getManager().parseInput(a[3]).name()));
        }
        if(a[0].equalsIgnoreCase("flags")){
            if(a.length < 2){
                s.sendMessage("Correct usage: /zg flags <name>");
                return false;
            }
            Zone z = Main.getManager().getZone(a[1]);
            if(z == null){
                s.sendMessage(String.format("Zone %s does not exist", a[1]));
                return false;
            }
            StringBuilder flags = new StringBuilder();
            for(Map.Entry<StateFlag, StateFlag.State> entry : z.getFlags().entrySet()){
                flags.append(entry.getKey().getName()).append(": ").append(Main.getManager().parseOutput(entry.getValue())).append(", ");
            }
            s.sendMessage(flags.toString());
        }
        return false;
    }
}
