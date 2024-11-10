package dev.mending.wizard.plugin.listener;

import com.github.teraprath.tinylib.utils.UpdateChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateListener implements Listener {

    private final UpdateChecker updateChecker;

    public UpdateListener(UpdateChecker updateChecker) {
        this.updateChecker = updateChecker;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (e.getPlayer().isOp() || e.getPlayer().hasPermission("*")) {
            updateChecker.checkForUpdate(e.getPlayer());
        }
    }

}
