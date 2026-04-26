package io.github.oceanio.ribbonplugin.feature.tuning;

import org.bukkit.ChatColor;
import org.bukkit.Sound;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class TuningListener implements Listener {
    private final JavaPlugin plugin;
    private final TuningService service;
    private boolean crafting = false;

    public TuningListener(JavaPlugin plugin, TuningService service) {
        this.plugin = plugin;
        this.service = service;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPrepare(PrepareItemCraftEvent event) {
        TuningInput input = TuningInput.parse(plugin, event.getInventory());
        if (input == null) return;

        // 上限5つチェック
        if (input.targetItem().getEnchantments().size() >= 5) {
            return;
        }

        ItemStack result = input.targetItem().clone();
        var meta = result.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.YELLOW + "??? エンチャント付与");
            result.setItemMeta(meta);
        }
        event.getInventory().setResult(result);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (!(event.getInventory() instanceof CraftingInventory inv)) return;

        // 結果スロットだけ
        if (event.getSlotType() != InventoryType.SlotType.RESULT) return;

        TuningInput input = TuningInput.parse(plugin, inv);
        if (input == null) return;

        event.setCancelled(true); // ←これで完全に止める

        // 上限チェック
        if (input.targetItem().getEnchantments().size() >= 5) {
            player.sendMessage(ChatColor.RED + "このアイテムには既に5つのエンチャントが付いています！");
            return;
        }

        // エンチャ付与
        ItemStack result = input.targetItem().clone();
        var enchants = service.rollEnchantment(result);
        enchants.forEach(result::addUnsafeEnchantment);

        // 素材消費
        service.consumeIngredients(inv);

        // アイテム付与
        player.getInventory().addItem(result).values().forEach(drop ->
                player.getWorld().dropItemNaturally(player.getLocation(), drop));

        player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);
    }
}
