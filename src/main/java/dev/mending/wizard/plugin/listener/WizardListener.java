package dev.mending.wizard.plugin.listener;

import dev.mending.wizard.api.WizardAPI;
import dev.mending.wizard.core.spell.Spell;
import dev.mending.wizard.core.wand.Wand;
import dev.mending.wizard.plugin.WizardPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.*;

public class WizardListener implements Listener {

    private final WizardPlugin plugin;
    private final Map<UUID, List<Spell.ClickType>> playerClickSequences = new HashMap<>();
    private final Map<UUID, Long> playerLastClickTime = new HashMap<>();

    // Konstruktor
    public WizardListener(@Nonnull WizardPlugin plugin) {
        this.plugin = plugin;
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
        displayClickSequenz(player, playerClickSequences.get(player.getUniqueId()));
        playSound(player);
    }


    private void handleClick(Player player, Spell.ClickType clickType) {
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();

        // Initialisieren der HashMaps, falls nicht vorhanden
        playerClickSequences.putIfAbsent(playerId, new ArrayList<>());
        playerLastClickTime.putIfAbsent(playerId, currentTime);

        List<Spell.ClickType> clicks = playerClickSequences.get(playerId);

        // Klicksequenz zurücksetzen, wenn das Zeitlimit überschritten ist
        if (currentTime - playerLastClickTime.get(playerId) > plugin.getWizardConfig().getClickTimeout()) {
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
            for (Spell spell : wand.getBinds()) {
                if (clicks.equals(spell.getCombination())) {
                    spell.execute(player);
                    break;
                }
            }
        }

        // Player-Click-Zeit aktualisieren
        playerLastClickTime.put(playerId, currentTime);
    }

    private void playSound(Player player) {
        if (plugin.getWizardConfig().isClickSequenzSoundEnabled()) {
            player.playSound(
                    player.getLocation(),
                    Sound.valueOf(plugin.getWizardConfig().getClickSequenzSoundType()),
                    plugin.getWizardConfig().getClickSequenzSoundVolume(),
                    plugin.getWizardConfig().getClickSequenzSoundPitch()
            );
        }
    }

    // Action Bar aktualisieren
    private void displayClickSequenz(Player player, List<Spell.ClickType> clicks) {

        if (!plugin.getWizardConfig().isClickSequenzMessageEnabled()) {
            return;
        }

        Component clickSequenzComponent = Component.empty();

        // Iteriere über die Klicktypen und füge sie der Actionbar hinzu
        for (int i = 0; i < 3; i++) {  // Immer 3 Klicks anzeigen
            Component clickComponent;

            // Zeige "L" oder "R" für vorhandene Klicks, sonst "_"
            if (i < clicks.size()) {
                clickComponent = clicks.get(i) == Spell.ClickType.LEFT
                        ? plugin.getWizardConfig().getClickSequenzLeftComponent()
                        : plugin.getWizardConfig().getClickSequenzRightComponent();
            } else {
                clickComponent = plugin.getWizardConfig().getClickSequenzEmptyComponent();
            }

            // Text hinzufügen und bei Bedarf Leerzeichen einfügen
            clickSequenzComponent = clickSequenzComponent.append(clickComponent);
            if (i < 2) {
                clickSequenzComponent = clickSequenzComponent.append(plugin.getWizardConfig().getClickSequenzSpacerComponent());
            }
        }

        // Sende die ActionBar an den Spieler
        if (plugin.getWizardConfig().getClickSequenzType().equalsIgnoreCase("ACTIONBAR")) {
            player.sendActionBar(clickSequenzComponent);
        } else if (plugin.getWizardConfig().getClickSequenzType().equalsIgnoreCase("TITLE")) {
            player.showTitle(Title.title(Component.empty(), clickSequenzComponent, Title.Times.times(Duration.ZERO, Duration.ofMillis(plugin.getWizardConfig().getClickTimeout()), Duration.ZERO)));
        }
    }
}