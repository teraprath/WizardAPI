package dev.mending.wizard.core.wand;

import de.tr7zw.changeme.nbtapi.NBT;
import dev.mending.wizard.core.spell.Spell;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wand {

    private final String id;  // Der Name der Wand
    private final ItemStack item;  // Der ItemStack der Wand
    private final List<Spell> spells;  // Liste der Spells, die dieser Wand zugeordnet sind

    // Konstruktor der Wand
    public Wand(String id, ItemStack item) {
        this.id = id;
        this.item = item;
        this.spells = new ArrayList<>();
    }

    // Gibt den Namen der Wand zurück
    public String getId() {
        return id;
    }

    // Gibt das Item der Wand zurück
    public ItemStack getItem() {
        NBT.modify(item, nbt -> {
            nbt.setString("wizard_id", id);
            nbt.setUUID("wizard_uuid", UUID.randomUUID());
        });
        return item;
    }

    // Fügt der Wand einen neuen Spell hinzu
    public void bind(Spell spell) {
        spells.add(spell);
    }

    // Gibt die Liste der Spells der Wand zurück
    public List<Spell> getBinds() {
        return spells;
    }

    // Überprüft, ob der ItemStack dieser Wand entspricht
    public boolean isItem(ItemStack otherItem) {
        return item.isSimilar(otherItem);  // Vergleicht mit isSimilar(), um zu prüfen, ob es sich um dasselbe Item handelt
    }
}
