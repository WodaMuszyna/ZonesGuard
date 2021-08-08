package me.wodamuszyna.zones.listeners;

import me.wodamuszyna.zones.Main;
import me.wodamuszyna.zones.data.StateFlag;
import me.wodamuszyna.zones.data.Zone;
import me.wodamuszyna.zones.util.LocationUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){
            Zone z = LocationUtil.isInZone(e.getDamager().getLocation());
            if(z != null){
                if(!Main.getManager().allows(z, StateFlag.Registry.PVP)){
                    e.setCancelled(true);
                    e.getEntity().sendMessage(Main.getInstance().config.notAllowed);
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Zone z = LocationUtil.isInZone(e.getPlayer().getLocation());
        if(z != null){
            if(!Main.getManager().allows(z, StateFlag.Registry.ITEM_DROP)){
                e.setCancelled(true);
                e.getPlayer().sendMessage(Main.getInstance().config.notAllowed);
            }
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e){
        if(e.getEntity() instanceof Player){
            Zone z = LocationUtil.isInZone(e.getEntity().getLocation());
            if(z != null){
                if(!Main.getManager().allows(z, StateFlag.Registry.ITEM_PICKUP)){
                    e.setCancelled(true);
                    e.getEntity().sendMessage(Main.getInstance().config.notAllowed);
                }
            }
        }
    }

}
