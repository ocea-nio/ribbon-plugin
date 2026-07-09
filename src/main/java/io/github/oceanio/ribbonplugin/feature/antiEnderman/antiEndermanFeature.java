package io.github.oceanio.ribbonplugin.feature.antiEnderman;

import io.github.oceanio.ribbonplugin.core.Feature;
import org.bukkit.plugin.java.JavaPlugin;

public class antiEndermanFeature implements Feature {
    private antiEndermanListener listener;

    @Override
    public String getName() {
        return "antiEnderman";
    }

    @Override
    public void enable(JavaPlugin plugin) {
        //インスタンス生成
        listener = new antiEndermanListener();

        //登録
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);

    }

    @Override
    public void disable() {
        org.bukkit.event.HandlerList.unregisterAll(listener);
    }
}
