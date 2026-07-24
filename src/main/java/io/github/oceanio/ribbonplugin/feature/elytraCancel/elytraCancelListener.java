package io.github.oceanio.ribbonplugin.feature.elytraCancel;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;



public class elytraCancelListener implements Listener {
    @EventHandler
    public void onItemDamaged(PlayerItemDamageEvent event){
        ItemStack item = event.getItem();
        if (item.getType() != Material.ELYTRA && item.getType() != Material.MACE && item.getType() != Material.TRIDENT) {
            return;
        }
        event.setCancelled(true);
    }
}
