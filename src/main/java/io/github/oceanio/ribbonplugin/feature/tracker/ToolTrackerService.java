package io.github.oceanio.ribbonplugin.feature.tracker;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ToolTrackerService {
    private static final String PREFIX = "使用回数: ";
    private final NamespacedKey usageKey;

    private static final Set<Material> TOOLS = Set.of(
            Material.WOODEN_PICKAXE,
            Material.WOODEN_AXE,
            Material.WOODEN_SHOVEL,
            Material.STONE_PICKAXE,
            Material.STONE_AXE,
            Material.STONE_SHOVEL,
            Material.COPPER_PICKAXE,
            Material.COPPER_AXE,
            Material.COPPER_SHOVEL,
            Material.GOLDEN_PICKAXE,
            Material.GOLDEN_AXE,
            Material.IRON_PICKAXE,
            Material.IRON_AXE,
            Material.IRON_SHOVEL,
            Material.DIAMOND_PICKAXE,
            Material.DIAMOND_AXE,
            Material.DIAMOND_SHOVEL,
            Material.NETHERITE_PICKAXE,
            Material.NETHERITE_AXE,
            Material.NETHERITE_SHOVEL
    );

    public ToolTrackerService(JavaPlugin plugin) {
        this.usageKey = new NamespacedKey(plugin, "usage");
    }

    public boolean isTools (ItemStack item) {
        return TOOLS.contains(item.getType());
    }

    public int findLineIndex(List<Component> list, String string){
        if (list != null) {
            // 2. プレーンテキスト変換用のシリアライザーを用意
            PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();

            for (int i = 0; i < list.size(); i++) {
                Component lineComponent = list.get(i);
                String lineText = serializer.serialize(lineComponent);
                if (lineText.contains(string)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void addLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        // PDCの使用回数を更新
        int uses = pdc.getOrDefault(
                usageKey,
                PersistentDataType.INTEGER,
                0
        ) + 1;

        pdc.set(usageKey, PersistentDataType.INTEGER, uses);

        // Loreを取得。なければ新しく作る
        List<Component> lore = meta.lore();

        if (lore == null) {
            lore = new ArrayList<>();
        } else {
            lore = new ArrayList<>(lore);
        }

        Component usageLore = Component.text(PREFIX + uses)
                .color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false);

        int index = findLineIndex(lore, PREFIX);

        if (index >= 0) {
            // 既存の使用回数表示を更新
            lore.set(index, usageLore);
        } else {
            // なければ追加
            lore.add(usageLore);
        }

        meta.lore(lore);
        item.setItemMeta(meta);
    }
}
