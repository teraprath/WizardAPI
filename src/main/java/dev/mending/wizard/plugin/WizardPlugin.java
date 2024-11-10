package dev.mending.wizard.plugin;

import com.github.teraprath.tinylib.utils.UpdateChecker;
import de.tr7zw.changeme.nbtapi.NBT;
import dev.mending.wizard.plugin.command.WizardCommand;
import dev.mending.wizard.plugin.config.WizardConfig;
import dev.mending.wizard.plugin.listener.UpdateListener;
import dev.mending.wizard.plugin.listener.WizardListener;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class WizardPlugin extends JavaPlugin {

    private final UpdateChecker updateChecker = new UpdateChecker(this, "teraprath", "WizardAPI");
    private final WizardConfig wizardConfig = new WizardConfig(this);

    @Override
    public void onEnable() {

        new InventoryAPI(this).init();
        wizardConfig.init();

        if (!NBT.preloadApi()) {
            getLogger().warning("NBT-API wasn't initialized properly, disabling the plugin");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (wizardConfig.isCheckForUpdates()) {
            getServer().getPluginManager().registerEvents(new UpdateListener(updateChecker), this);
            updateChecker.checkForUpdate();
        }

        getServer().getPluginManager().registerEvents(new WizardListener(this), this);
        getCommand("wizard").setExecutor(new WizardCommand(this));
    }

    public WizardConfig getWizardConfig() {
        return this.wizardConfig;
    }
}
