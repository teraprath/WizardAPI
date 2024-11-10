package dev.mending.wizard.plugin.command;

import dev.mending.wizard.api.WizardAPI;
import dev.mending.wizard.core.wand.Wand;
import dev.mending.wizard.plugin.WizardPlugin;
import dev.mending.wizard.plugin.gui.WandGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WizardCommand implements CommandExecutor, TabCompleter {

    private final WizardPlugin plugin;

    public WizardCommand(WizardPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length > 0) {
            if (args.length >= 2) {
                if (args[0].equalsIgnoreCase("give")) {
                    String wandId = args[1];
                    if (args.length > 2) {
                        Player target = plugin.getServer().getPlayer(args[2]);
                        if (target != null) {
                            giveWand(target, wandId);
                        } else {
                            sender.sendMessage("§cPlayer '" + args[2] + "' not found.");
                        }
                    } else {
                        if (sender instanceof Player player) {
                            giveWand(player, wandId);
                        } else {
                            sender.sendMessage("Please specify a player!");
                        }
                    }
                } else {
                    showHelp(sender);
                }
            } else {
                switch (args[0].toLowerCase()) {
                    case "reload":
                        sender.sendMessage("§8[§dWizardAPI§8] §7Configuration has been reloaded.");
                        break;
                    case "list":
                        if (sender instanceof Player player) {
                            new WandGui(player).open();
                        } else {
                            plugin.getLogger().info("A list of all wands:");
                            WizardAPI.getInstance().getAllWands().forEach((id, wand) -> {
                                plugin.getLogger().info("- " + id + " [" + wand.getBinds().size()  + " Spells]");
                            });
                        }
                        break;
                    default:
                        showHelp(sender);
                        break;
                }
            }
        } else {
            showHelp(sender);
        }

        return false;
    }

    private void giveWand(Player player, String id) {
        Wand wand = WizardAPI.getInstance().getWandById(id);
        if (wand != null) {
            player.getInventory().addItem(wand.getItem());
        }
    }

    private void showHelp(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage("§dWizardAPI");
        sender.sendMessage("§f" + plugin.getDescription().getDescription());
        sender.sendMessage("");
        sender.sendMessage("§8┃ §7Version: §f" + plugin.getDescription().getVersion());
        sender.sendMessage("§8┃ §7Author: §f" + plugin.getDescription().getAuthors().getFirst());
        sender.sendMessage("");
        sender.sendMessage("§dCommands");
        sender.sendMessage("§8┃ §7/wizard reload: §fReload Configuration");
        sender.sendMessage("§8┃ §7/wizard list: §fOpen Wands Gui");
        sender.sendMessage("§8┃ §7/wizard give [wand] [<player>]: §fGive specific wand to a player");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> list = new ArrayList<>();
        String current = args[args.length - 1].toLowerCase();

        switch (args.length) {
            case 1:
                list.add("reload");
                list.add("list");
                list.add("give");
                break;
            case 2:
                if (args[0].equals("give")) {
                    WizardAPI.getInstance().getAllWands().forEach((id, wand) -> {
                        list.add(id);
                    });
                }
                break;
            case 3:
                if (args[0].equals("give")) {
                    plugin.getServer().getOnlinePlayers().forEach(all -> {
                        list.add(all.getName());
                    });
                }
                break;
            default:
                break;
        }

        list.removeIf(s -> !s.toLowerCase().startsWith(current));
        return list;
    }
}
