package dev.mending.wizard.core.spell;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.bukkit.entity.Player;

public class Spell {

    private final List<ClickType> combination;
    private final Consumer<Player> action;  // Die Aktion, die ausgeführt wird

    // Konstruktor, der sicherstellt, dass genau 3 Klicktypen übergeben werden
    public Spell(Consumer<Player> action, ClickType... clickTypes) {
        if (clickTypes.length != 3) {
            throw new IllegalArgumentException("Exactly 3 ClickTypes must be provided.");
        }

        this.action = action;
        this.combination = new ArrayList<>();

        // Füge die übergebenen Klicktypen zur Kombination hinzu
        this.combination.addAll(Arrays.asList(clickTypes));
    }

    // Aktion ausführen
    public void execute(Player player) {
        action.accept(player);
    }

    // Kombination der Klicktypen zurückgeben
    public List<ClickType> getCombination() {
        return combination;
    }

    // Klicktypen für die Spell definieren
    public enum ClickType {
        LEFT, RIGHT
    }
}
