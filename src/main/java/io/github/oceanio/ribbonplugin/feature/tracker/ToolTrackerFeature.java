package io.github.oceanio.ribbonplugin.feature.tracker;

import io.github.oceanio.ribbonplugin.core.Feature;
import org.bukkit.plugin.java.JavaPlugin;

public class ToolTrackerFeature implements Feature {
    private final ToolTrackerService service;
    private ToolTrackerListener listener;

    public ToolTrackerFeature(ToolTrackerService service) {
        this.service = service;
    }

    @Override
    public String getName() {
        return "ToolTracker";
    }

    @Override
    public void enable(JavaPlugin plugin) {
        //DI
        this.listener = new ToolTrackerListener(service);

        //イベント登録
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public void disable() {

    }
}
