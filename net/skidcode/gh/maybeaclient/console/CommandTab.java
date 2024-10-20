/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.console;

import java.util.Arrays;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.console.Command;
import net.skidcode.gh.maybeaclient.gui.click.ClickGUI;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class CommandTab
extends Command {
    public CommandTab() {
        super("tab", "Manage ClickGUI Tabs");
    }

    @Override
    public void onTyped(String[] args) {
        int argc = args.length;
        if (argc < 1 || args[0].equalsIgnoreCase("help")) {
            args = new String[]{"help"};
        }
        switch (args[0]) {
            case "reset": {
                if (argc < 2) {
                    Client.addMessage("Not enough arguments!");
                    break;
                }
                String tabname = String.join((CharSequence)" ", Arrays.copyOfRange(args, 1, args.length));
                for (Tab tab : ClickGUI.tabs) {
                    if (!tab.name.equalsIgnoreCase(tabname)) continue;
                    tab.xPos = tab.xDefPos;
                    tab.yPos = tab.yDefPos;
                    Client.addMessage("Tab position was changed!");
                    return;
                }
                Client.addMessage("Tab with name '" + (Object)((Object)ChatColor.LIGHTCYAN) + tabname + "' was not found!");
                break;
            }
            case "list": {
                String result = "Available tabs: ";
                for (Tab tab : ClickGUI.tabs) {
                    result = String.valueOf(result) + (Object)((Object)ChatColor.LIGHTCYAN) + tab.name + (Object)((Object)ChatColor.WHITE) + ", ";
                }
                result = result.substring(0, result.length() - 2);
                Client.addMessage(result);
                break;
            }
            default: {
                Client.addMessage(".tab list - List tabs in ClickGUI");
                Client.addMessage(".tab reset <tabname...> - Reset positon of a tab");
            }
        }
    }
}

