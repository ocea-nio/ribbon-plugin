package io.github.oceanio.ribbonplugin.feature.villagerfix;

import io.github.oceanio.ribbonplugin.core.Feature;
import org.bukkit.plugin.java.JavaPlugin;

public class FixVillagerFeature implements Feature {
    private FixVillagerCommand command;
    private FixVillagerListener listener;

    @Override
    public String getName() {
        return "FixVillager";
    }

    @Override
    public void enable(JavaPlugin plugin) {
        //インスタンス生成
        listener = new FixVillagerListener();
        command = new FixVillagerCommand();

        // イベント登録
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);

        //コマンド登録
        plugin.getCommand("villager_fix").setExecutor(command);

    }

    @Override
    public void disable() {
        org.bukkit.event.HandlerList.unregisterAll(listener);
    }
}
