package me.boni.moder.llmoder.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.OfflinePlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Moderator implements CommandExecutor {
    List<Player> moderators = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command!");
            return false;
        }
        Player player = (Player) commandSender;
        if (player.hasPermission("moder.moder")) {
            if (strings.length == 0) {
                commandSender.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Список команд:");
                commandSender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "/moder list" + ChatColor.GRAY + "" + ChatColor.BOLD + ": Список модераторов");
                commandSender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "/moder add " + ChatColor.GRAY + "" + ChatColor.BOLD + "<игрок>: Добавить игрока в список");
                commandSender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "/moder remove " + ChatColor.GRAY + "" + ChatColor.BOLD + "<игрок>: Убрать игрока из списка");

                return true;
            }
            if (strings[0].equalsIgnoreCase("add")) {

                String nick = strings[1];
                String cmd = "lp user " + nick + " parent set moder";

                CommandSender console = Bukkit.getConsoleSender();
                Bukkit.dispatchCommand(console, cmd);

                moderators.add(Bukkit.getPlayer(nick));

                commandSender.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + nick + ChatColor.GRAY + "" + ChatColor.BOLD + " добавлен в список модераторов!");
                return true;
            }
            if (strings[0].equalsIgnoreCase("remove")) {
                String nick = strings[1];
                String cmd = "lp user " + nick + " parent remove moder";

                CommandSender console = Bukkit.getConsoleSender();
                Bukkit.dispatchCommand(console, cmd);

                moderators.remove(Bukkit.getPlayer(nick));

                commandSender.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + nick + ChatColor.GRAY + "" + ChatColor.BOLD + " убран из списка модераторов!");
                return true;
            }

            if (strings[0].equalsIgnoreCase("list")) {
                StringBuilder messageBuilder = new StringBuilder();
                commandSender.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Список модераторов:");
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.hasPermission("group.moder")) {
                        String moderator = onlinePlayer.getName();
                        String status = onlinePlayer.isOnline() ? ChatColor.GOLD + " Онлайн" : ChatColor.RED + "";
                        String line = ChatColor.BLUE + "" + ChatColor.BOLD + "- Moder " + moderator + status;

                        messageBuilder.append(line).append("\n");
                    }
                }

                for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                    UUID offlinePlayerUUID = offlinePlayer.getUniqueId();
                    if (!Bukkit.getOfflinePlayer(offlinePlayerUUID).isOnline() && commandSender.hasPermission("group.moder")) {
                        String moderator = offlinePlayer.getName();
                        String status = ChatColor.RED + "";
                        String line = ChatColor.BLUE + "" + ChatColor.BOLD + "- Moder " + moderator + status;

                        messageBuilder.append(line).append("\n");
                    }
                }

                commandSender.sendMessage(messageBuilder.toString());
                return true;
            }
        }
        return false;
    }
}
