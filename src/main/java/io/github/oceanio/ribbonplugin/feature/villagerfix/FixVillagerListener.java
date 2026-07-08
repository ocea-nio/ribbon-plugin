package io.github.oceanio.ribbonplugin.feature.villagerfix;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.inventory.MerchantRecipe;


public class FixVillagerListener implements Listener {
    @EventHandler
    public void onVillagerTrade(VillagerAcquireTradeEvent event) {
        MerchantRecipe recipe = event.getRecipe();
        if (recipe.getResult().getType() == Material.ENCHANTED_BOOK) {
            event.setCancelled(true);
        }
    }
}
