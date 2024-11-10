package dev.mending.wizard.plugin;

import dev.mending.wizard.api.WizardAPI;
import dev.mending.wizard.core.spell.Spell;
import dev.mending.wizard.core.wand.Wand;
import dev.mending.wizard.plugin.listener.WizardListener;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class WizardPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new WizardListener(), this);

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

        wand.addSpell(spell1);
        wand.addSpell(spell2);

        WizardAPI.getInstance().registerWand(wand);
    }
}
