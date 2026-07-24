package io.github.oceanio.ribbonplugin.feature.elytraCancel;

import io.github.oceanio.ribbonplugin.core.Feature;
import org.bukkit.plugin.java.JavaPlugin;

public class elytraCancelFeature implements Feature {
    private  elytraCancelListener listener;


    @Override
    public String getName() {
        return "elytraCancel";
    }

    @Override
    public void enable(JavaPlugin plugin) {
        listener = new elytraCancelListener();

        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public void disable() {
        org.bukkit.event.HandlerList.unregisterAll(listener);
    }
}
