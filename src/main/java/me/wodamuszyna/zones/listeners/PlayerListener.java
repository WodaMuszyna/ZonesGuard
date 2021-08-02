package me.wodamuszyna.zones.listeners;

import me.wodamuszyna.zones.Main;
import me.wodamuszyna.zones.data.StateFlag;
import me.wodamuszyna.zones.data.Zone;
import me.wodamuszyna.zones.util.LocationUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){
            Zone z = LocationUtil.isInZone(e.getDamager().getLocation());
            if(z != null){
                if(!Main.getManager().allows(z, StateFlag.Registry.PVP)){
                    e.setCancelled(true);
                }
            }
        }
    }

}
