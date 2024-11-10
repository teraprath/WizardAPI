package dev.mending.wizard.plugin.gui;

import dev.mending.wizard.api.WizardAPI;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import mc.obliviate.inventory.pagination.PaginationManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;

public class WandGui extends Gui {

    private final PaginationManager pagination = new PaginationManager(this);

    public WandGui(@NotNull Player player) {
        super(player, "wizard-list", "Wands", 6);
        pagination.registerPageSlotsBetween(9, 44);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

        WizardAPI.getInstance().getAllWands().forEach((id, wand) -> {
            pagination.addItem(new Icon(wand.getItem()).onClick(clickEvent -> {
                player.getInventory().addItem(wand.getItem());
            }));
        });

        pagination.setPage(0);

        fillRow(new Icon(Material.BLACK_STAINED_GLASS_PANE).setName(""), 0);
        addItem(4, new Icon(Material.PAPER).setName("§dRegistered Wands").setLore("§7" + WizardAPI.getInstance().getAllWands().size()));

        if (pagination.getCurrentPage() != 0) {
            addItem(0, new Icon(Material.ARROW).setName("§fPrevious").onClick(e -> {
                pagination.goPreviousPage();
                pagination.update();
            }));
        }
        if (!pagination.isLastPage()) {
            addItem(8, new Icon(Material.ARROW).setName("§fNext").onClick(e -> {
                pagination.goNextPage();
            }));
            pagination.update();
        }

        pagination.update();
    }
}
