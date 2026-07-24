package io.github.oceanio.ribbonplugin;

import io.github.oceanio.ribbonplugin.core.FeatureManager;

import io.github.oceanio.ribbonplugin.feature.antiEnderman.antiEndermanFeature;
import io.github.oceanio.ribbonplugin.feature.elytraCancel.elytraCancelFeature;
import io.github.oceanio.ribbonplugin.feature.tracker.ToolTrackerFeature;
import io.github.oceanio.ribbonplugin.feature.tracker.ToolTrackerService;
import io.github.oceanio.ribbonplugin.feature.villagerfix.FixVillagerFeature;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private FeatureManager featureManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        featureManager = new FeatureManager(this);

        //ここでfeature登録
        featureManager.register(new FixVillagerFeature());
        featureManager.register((new antiEndermanFeature()));
        featureManager.register(new elytraCancelFeature());
        featureManager.register(new ToolTrackerFeature(new ToolTrackerService(this)));

        //要素許可
        featureManager.enableAll();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
