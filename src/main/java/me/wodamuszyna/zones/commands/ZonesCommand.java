package me.wodamuszyna.zones.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                //zone creation code
            } catch (IncompleteRegionException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
