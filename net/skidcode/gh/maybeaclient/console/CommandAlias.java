/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.console;

import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.console.Command;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class CommandAlias
extends Command {
    public CommandAlias() {
        super("alias", "Manage command aliases");
    }

    @Override
    public void onTyped(String[] args) {
        int argc = args.length;
        if (argc < 1 || args[0].equalsIgnoreCase("help")) {
            args = new String[]{"help"};
        }
        block7 : switch (args[0]) {
            case "add": 
            case "new": {
                if (argc < 3) {
                    Client.addMessage("Not enough arguments!");
                    break;
                }
                String alias = args[1];
                String command = Client.getAlias(alias);
                if (command != null) {
                    Client.addMessage("Alias " + (Object)((Object)ChatColor.GOLD) + alias + (Object)((Object)ChatColor.WHITE) + " already has a command attached to it: " + (Object)((Object)ChatColor.GOLD) + command);
                    break;
                }
                command = args[2];
                int i = 3;
                while (i < args.length) {
                    command = String.valueOf(command) + " " + args[i];
                    ++i;
                }
                Client.addAlias(alias, command);
                Client.saveAliases();
                Client.addMessage("Alias " + (Object)((Object)ChatColor.GOLD) + alias + (Object)((Object)ChatColor.WHITE) + " was attached to " + (Object)((Object)ChatColor.GOLD) + command);
                break;
            }
            case "list": {
                int pages = (int)Math.ceil((float)Client.aliasesList.size() / 10.0f);
                int page = 0;
                int page4humans = page + 1;
                Client.addMessage("Aliases list page " + (Object)((Object)ChatColor.GOLD) + page4humans + (Object)((Object)ChatColor.WHITE) + "/" + (Object)((Object)ChatColor.GOLD) + pages + (Object)((Object)ChatColor.WHITE) + ":");
                int i = page * 10;
                while (i < page * 10 + 10) {
                    if (Client.aliasesList.size() <= i) break block7;
                    String alias = Client.aliasesList.get(i);
                    String cmd = Client.aliases.get(alias);
                    Client.addMessage((Object)((Object)ChatColor.GOLD) + alias + (Object)((Object)ChatColor.WHITE) + ": " + (Object)((Object)ChatColor.LIGHTCYAN) + cmd);
                    ++i;
                }
                break;
            }
            case "delete": 
            case "remove": {
                String alias;
                String command;
                if (argc < 2) {
                    Client.addMessage("Not enough arguments!");
                }
                if ((command = Client.getAlias(alias = args[1])) != null) {
                    Client.removeAlias(alias);
                    Client.saveAliases();
                    Client.addMessage("Alias " + (Object)((Object)ChatColor.GOLD) + alias + (Object)((Object)ChatColor.WHITE) + " was removed");
                    break;
                }
                Client.addMessage("Alias " + (Object)((Object)ChatColor.GOLD) + alias + (Object)((Object)ChatColor.WHITE) + " doesn't exist");
                break;
            }
            default: {
                Client.addMessage(".alias list - List available aliases");
                Client.addMessage(".alias new <alias> <command...> - Create a new alias");
                Client.addMessage(".alias delete <alias> - Delete an alias");
            }
        }
    }
}

