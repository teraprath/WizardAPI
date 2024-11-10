package dev.mending.wizard.plugin.listener;

import dev.mending.wizard.api.WizardAPI;
import dev.mending.wizard.core.spell.Spell;
import dev.mending.wizard.core.wand.Wand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class WizardListener implements Listener {

    private final long clickTimeout; // Zeitlimit für Klicks
    private final Map<UUID, List<Spell.ClickType>> playerClickSequences = new HashMap<>();
    private final Map<UUID, Long> playerLastClickTime = new HashMap<>();

    // Konstruktor
    public WizardListener() {
        this.clickTimeout = 1000;
    }

    // Event-Handler für Spielerinteraktionen
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // Überprüfen, ob das Item eine Wand ist
        if (!WizardAPI.getInstance().isWand(itemInHand)) {
            return; // Wenn es keine Wand ist, nichts tun
        }

        // Bestimmen, welcher Klick (Links oder Rechts) erfolgt ist
        Spell.ClickType clickType = (event.getAction().toString().contains("LEFT")) ? Spell.ClickType.LEFT : Spell.ClickType.RIGHT;

        // Klicksequenz und Zauberverwaltung
        handleClick(player, clickType);

        // Action Bar mit den aktuellen Klicks anzeigen
        updateActionBar(player, playerClickSequences.get(player.getUniqueId()));
    }


    private void handleClick(Player player, Spell.ClickType clickType) {
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();

        // Initialisieren der HashMaps, falls nicht vorhanden
        playerClickSequences.putIfAbsent(playerId, new ArrayList<>());
        playerLastClickTime.putIfAbsent(playerId, currentTime);

        List<Spell.ClickType> clicks = playerClickSequences.get(playerId);

        // Klicksequenz zurücksetzen, wenn das Zeitlimit überschritten ist
        if (currentTime - playerLastClickTime.get(playerId) > clickTimeout) {
            clicks.clear();
        }

        // Füge den neuen Klick hinzu
        clicks.add(clickType);

        // Wenn 4 Klicks erreicht sind, wird die Sequenz zurückgesetzt und der 4. Klick als neuer Start gesetzt
        if (clicks.size() == 4) {
            clicks.clear();  // Setzt die Sequenz zurück
            clicks.add(clickType);  // Der 4. Klick wird als erster Klick der neuen Sequenz behandelt
        }

        // Sobald 3 Klicks erreicht sind, den Zauber ausführen
        if (clicks.size() == 3) {
            Wand wand = WizardAPI.getInstance().getWandByItem(player.getInventory().getItemInMainHand());

            // Alle Spells der Wand überprüfen
            for (Spell spell : wand.getSpells()) {
                if (clicks.equals(spell.getCombination())) {
                    spell.execute(player);
                    break;
                }
            }
        }

        // Player-Click-Zeit aktualisieren
        playerLastClickTime.put(playerId, currentTime);
    }

    // Action Bar aktualisieren
    private void updateActionBar(Player player, List<Spell.ClickType> clicks) {
        StringBuilder actionBarText = new StringBuilder();

        // Iteriere über die Klicktypen und füge sie der Actionbar hinzu
        for (int i = 0; i < 3; i++) {  // Immer 3 Klicks anzeigen
            if (i < clicks.size()) {
                // Zeige den tatsächlichen Klicktyp (L oder R)
                actionBarText.append(clicks.get(i) == Spell.ClickType.LEFT ? "L" : "R");
            } else {
                // Zeige "_" für fehlende Klicks
                actionBarText.append("_");
            }

            // Füge ein Leerzeichen nach jedem Klick (außer dem letzten) hinzu
            if (i < 2) {
                actionBarText.append(" ");
            }
        }

        player.sendActionBar(actionBarText.toString());
    }
}