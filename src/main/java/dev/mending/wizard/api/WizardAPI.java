package dev.mending.wizard.api;

import de.tr7zw.changeme.nbtapi.NBT;
import dev.mending.wizard.core.wand.Wand;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class WizardAPI {

    private static WizardAPI instance;

    // Die HashMap speichert den Namen der Wand (String) als Schlüssel
    private final Map<String, Wand> wands = new HashMap<>();

    // Private Konstruktor verhindert die Instanziierung von außen
    private WizardAPI() {}

    // Zugriff auf die einzige Instanz von WizardAPI
    public static WizardAPI getInstance() {
        if (instance == null) {
            instance = new WizardAPI();
        }
        return instance;
    }

    // Wand registrieren (Wand-Objekt anstelle eines ItemStack)
    public void register(Wand wand) {
        wands.put(wand.getId(), wand);  // Wand wird mit ihrem Namen als Schlüssel gespeichert
    }

    // Wand anhand des Namens abrufen
    public Wand getWandById(String id) {
        return wands.get(id);  // Gibt die Wand zurück, die mit dem Namen verknüpft ist
    }

    // Überprüft, ob ein ItemStack eine Wand repräsentiert
    public Wand getWandByItem(ItemStack itemStack) {
        // Hier wird überprüft, ob das Item ein Wand-Item ist.
        String id = NBT.get(itemStack, nbt -> (String) nbt.getString("wizard_id"));
        return wands.get(id);
    }

    public boolean isWand(ItemStack itemStack) {
        return getWandByItem(itemStack) != null;
    }

    // Gibt alle Wände zurück
    public Map<String, Wand> getAllWands() {
        return wands;
    }
}