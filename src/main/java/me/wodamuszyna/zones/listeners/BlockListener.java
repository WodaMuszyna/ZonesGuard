package me.wodamuszyna.zones.listeners;

import me.wodamuszyna.zones.Main;
import me.wodamuszyna.zones.data.StateFlag;
import me.wodamuszyna.zones.data.Zone;
import me.wodamuszyna.zones.util.LocationUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        Zone z = LocationUtil.isInZone(e.getBlock().getLocation());
        if(z != null){
            if(!Main.getManager().allows(z, StateFlag.Registry.BUILD, StateFlag.Registry.BLOCK_PLACE)){
                e.setCancelled(true);
                p.sendMessage(Main.getInstance().config.notAllowed);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        Zone z = LocationUtil.isInZone(e.getBlock().getLocation());
        if(z != null){
            if(!Main.getManager().allows(z, StateFlag.Registry.BUILD, StateFlag.Registry.BLOCK_BREAK)){
                e.setCancelled(true);
                p.sendMessage(Main.getInstance().config.notAllowed);
            }
        }
    }

    @EventHandler
    public void onBreak(PlayerHarvestBlockEvent e){
        Player p = e.getPlayer();
        Zone z = LocationUtil.isInZone(e.getHarvestedBlock().getLocation());
        if(z != null){
            if(!Main.getManager().allows(z, StateFlag.Registry.BUILD, StateFlag.Registry.BLOCK_BREAK)){
                e.setCancelled(true);
                p.sendMessage(Main.getInstance().config.notAllowed);
            }
        }
    }

    @EventHandler
    public void onBreak(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Zone z = LocationUtil.isInZone(e.getPlayer().getEyeLocation());
        if(z != null){
            if(e.getClickedBlock() != null){
            if(e.getClickedBlock().getType().equals(Material.CHEST) ||
                    e.getClickedBlock().getType().equals(Material.TRAPPED_CHEST) ||
                    e.getClickedBlock().getType().equals(Material.CHEST_MINECART) ||
                    e.getClickedBlock().getType().equals(Material.ENDER_CHEST) ||
                    e.getClickedBlock().getType().equals(Material.BARREL) ||
                    e.getClickedBlock().getType().equals(Material.SHULKER_BOX)) {
                if (!Main.getManager().allows(z, StateFlag.Registry.OPEN_CHEST, StateFlag.Registry.USE)) {
                    e.setCancelled(true);
                    p.sendMessage(Main.getInstance().config.notAllowed);
                }
            }
            }
            if(!Main.getManager().allows(z, StateFlag.Registry.BUILD, StateFlag.Registry.USE)){
                e.setCancelled(true);
                p.sendMessage(Main.getInstance().config.notAllowed);
            }
        }
    }
}
