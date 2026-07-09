package io.github.oceanio.ribbonplugin.feature.antiEnderman;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.jetbrains.annotations.NotNull;

public class antiEndermanListener implements Listener {
    @EventHandler
    public void onTakeBlockEnderman(EntityChangeBlockEvent event){
        EntityType target = event.getEntityType();
        if (target == EntityType.ENDERMAN){
            event.setCancelled(true);
        }else return;
    }
}
