package dev.mending.wizard.plugin;

import de.tr7zw.changeme.nbtapi.NBT;
import dev.mending.wizard.api.WizardAPI;
import dev.mending.wizard.core.spell.Spell;
import dev.mending.wizard.core.wand.Wand;
import dev.mending.wizard.plugin.command.WizardCommand;
import dev.mending.wizard.plugin.config.WizardConfig;
import dev.mending.wizard.plugin.listener.WizardListener;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class WizardPlugin extends JavaPlugin {

    private final WizardConfig wizardConfig = new WizardConfig(this);

    @Override
    public void onEnable() {

        if (!NBT.preloadApi()) {
            getLogger().warning("NBT-API wasn't initialized properly, disabling the plugin");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        new InventoryAPI(this).init();
        wizardConfig.init();

        getServer().getPluginManager().registerEvents(new WizardListener(this), this);
        getCommand("wizard").setExecutor(new WizardCommand(this));

        test();
    }

    // Test-Methode: Entferne die Zeile vor dem Build-Prozess
    private void test() {

        Wand wand = new Wand("TestWand", new ItemStack(Material.STICK));

        Spell spell1 = new Spell(player -> {
            player.sendMessage("L-R-R");
        }, Spell.ClickType.LEFT, Spell.ClickType.RIGHT, Spell.ClickType.RIGHT);

        Spell spell2 = new Spell(player -> {
            player.sendMessage("L-R-L");
        }, Spell.ClickType.LEFT, Spell.ClickType.RIGHT, Spell.ClickType.LEFT);

        wand.bind(spell1);
        wand.bind(spell2);

        WizardAPI.getInstance().register(wand);
    }

    public WizardConfig getWizardConfig() {
        return this.wizardConfig;
    }
}
