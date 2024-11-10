package dev.mending.wizard.core.wand;

import dev.mending.wizard.core.spell.Spell;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class Wand {

    private final String name;  // Der Name der Wand
    private final ItemStack item;  // Der ItemStack der Wand
    private final List<Spell> spells;  // Liste der Spells, die dieser Wand zugeordnet sind

    // Konstruktor der Wand
    public Wand(String name, ItemStack item) {
        this.name = name;
        this.item = item;
        this.spells = new ArrayList<>();
    }

    // Gibt den Namen der Wand zurück
    public String getName() {
        return name;
    }

    // Gibt das Item der Wand zurück
    public ItemStack getItem() {
        return item;
    }

    // Fügt der Wand einen neuen Spell hinzu
    public void addSpell(Spell spell) {
        spells.add(spell);
    }

    // Gibt die Liste der Spells der Wand zurück
    public List<Spell> getSpells() {
        return spells;
    }

    // Überprüft, ob der ItemStack dieser Wand entspricht
    public boolean isItem(ItemStack otherItem) {
        return item.isSimilar(otherItem);  // Vergleicht mit isSimilar(), um zu prüfen, ob es sich um dasselbe Item handelt
    }
}
