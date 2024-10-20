/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.skidcode.gh.maybeaclient.console;

import java.util.Map;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.console.Command;
import net.skidcode.gh.maybeaclient.gui.GuiBindModule;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.settings.Setting;
import net.skidcode.gh.maybeaclient.utils.ChatColor;
import org.lwjgl.input.Keyboard;

public class CommandModule
extends Command {
    public CommandModule() {
        super("module", "Modules settings");
    }

    @Override
    public void onTyped(String[] args) {
        int argc = args.length;
        if (argc < 1 || args[0].equalsIgnoreCase("help")) {
            args = new String[]{"help"};
        }
        switch (args[0]) {
            case "list": {
                String result = "Available modules: ";
                for (Map.Entry<String, Hack> entry : Client.hacksByName.entrySet()) {
                    result = String.valueOf(result) + (Object)((Object)(entry.getValue().status ? ChatColor.LIGHTGREEN : ChatColor.LIGHTRED)) + entry.getValue().name + (Object)((Object)ChatColor.WHITE) + ", ";
                }
                result = result.substring(0, result.length() - 2);
                Client.addMessage(result);
                break;
            }
            case "toggle": {
                if (argc < 2) {
                    Client.addMessage("Not enough arguments!");
                    break;
                }
                String name = args[1].toLowerCase();
                Hack h = Client.hacksByName.get(name);
                if (h == null) {
                    Client.addMessage("Module " + (Object)((Object)ChatColor.GOLD) + args[1] + (Object)((Object)ChatColor.WHITE) + " is not found!");
                    break;
                }
                h.toggle();
                Client.addMessage("Module " + (Object)((Object)ChatColor.GOLD) + h.name + (Object)((Object)ChatColor.WHITE) + " is now " + (h.status ? (Object)((Object)ChatColor.LIGHTGREEN) + "Enabled" : (Object)((Object)ChatColor.LIGHTRED) + "Disabled"));
                break;
            }
            case "gbind": {
                if (argc < 2) {
                    Client.addMessage("Not enough arguments!");
                    break;
                }
                String name = args[1].toLowerCase();
                Hack h = Client.hacksByName.get(name);
                if (h == null) {
                    Client.addMessage("Module " + (Object)((Object)ChatColor.GOLD) + args[1] + (Object)((Object)ChatColor.WHITE) + " is not found!");
                    break;
                }
                Client.mc.displayGuiScreen(new GuiBindModule(null, h));
                break;
            }
            case "bind": {
                int bind;
                if (argc < 3) {
                    Client.addMessage("Not enough arguments!");
                    break;
                }
                String name = args[1].toLowerCase();
                Hack h = Client.hacksByName.get(name);
                if (h == null) {
                    Client.addMessage("Module " + (Object)((Object)ChatColor.GOLD) + args[1] + (Object)((Object)ChatColor.WHITE) + " is not found!");
                    break;
                }
                String keybindRaw = args[2].toUpperCase();
                try {
                    bind = Integer.parseInt(keybindRaw);
                }
                catch (NumberFormatException e) {
                    if (!keybindRaw.startsWith("KEY_")) {
                        Client.addMessage("Invalid keyname format.");
                        break;
                    }
                    bind = Keyboard.getKeyIndex((String)keybindRaw.substring(4));
                }
                h.bind(bind);
                break;
            }
            case "reset": {
                if (argc < 3) {
                    Client.addMessage("Not enough arguments!");
                    break;
                }
                String name = args[1].toLowerCase();
                String settingName = args[2].toLowerCase();
                Hack h = Client.hacksByName.get(name);
                if (h == null) {
                    Client.addMessage("Module " + (Object)((Object)ChatColor.GOLD) + args[1] + (Object)((Object)ChatColor.WHITE) + " is not found!");
                    break;
                }
                if (!h.hasSettings) {
                    Client.addMessage("Module " + (Object)((Object)ChatColor.GOLD) + h.name + (Object)((Object)ChatColor.WHITE) + " has no settings!");
                    break;
                }
                if (h.settings.get(settingName) == null) {
                    Client.addMessage("Module " + (Object)((Object)ChatColor.GOLD) + h.name + (Object)((Object)ChatColor.WHITE) + " doesn't have a setting named " + (Object)((Object)ChatColor.GOLD) + args[2] + (Object)((Object)ChatColor.WHITE) + "!");
                    break;
                }
                Setting s = h.settings.get(settingName);
                s.reset();
                Client.addMessage("Setting " + (Object)((Object)ChatColor.GOLD) + s.noWhitespacesName + (Object)((Object)ChatColor.WHITE) + " was changed to " + (Object)((Object)ChatColor.GOLD) + s.valueToString() + (Object)((Object)ChatColor.WHITE) + "!");
                break;
            }
            case "set": {
                String valueRaw;
                if (argc < 4) {
                    Client.addMessage("Not enough arguments!");
                    break;
                }
                String name = args[1].toLowerCase();
                String settingName = args[2].toLowerCase();
                Hack h = Client.hacksByName.get(name);
                if (h == null) {
                    Client.addMessage("Module " + (Object)((Object)ChatColor.GOLD) + args[1] + (Object)((Object)ChatColor.WHITE) + " is not found!");
                    break;
                }
                if (!h.hasSettings) {
                    Client.addMessage("Module " + (Object)((Object)ChatColor.GOLD) + h.name + (Object)((Object)ChatColor.WHITE) + " has no settings!");
                    break;
                }
                if (h.settings.get(settingName) == null) {
                    Client.addMessage("Module " + (Object)((Object)ChatColor.GOLD) + h.name + (Object)((Object)ChatColor.WHITE) + " doesn't have a setting named " + (Object)((Object)ChatColor.GOLD) + args[2] + (Object)((Object)ChatColor.WHITE) + "!");
                    break;
                }
                Setting s = h.settings.get(settingName);
                if (!s.validateValue(valueRaw = args[3])) {
                    Client.addMessage("Invalid setting value!");
                    break;
                }
                s.setValue_(valueRaw);
                Client.saveModules();
                Client.addMessage("Setting " + (Object)((Object)ChatColor.GOLD) + s.noWhitespacesName + (Object)((Object)ChatColor.WHITE) + " was changed to " + (Object)((Object)ChatColor.GOLD) + s.valueToString() + (Object)((Object)ChatColor.WHITE) + "!");
                break;
            }
            case "info": {
                if (argc < 2) {
                    Client.addMessage("Not enough arguments!");
                    break;
                }
                String name = args[1].toLowerCase();
                Hack h = Client.hacksByName.get(name);
                if (h == null) {
                    Client.addMessage("Module " + (Object)((Object)ChatColor.GOLD) + args[1] + (Object)((Object)ChatColor.WHITE) + " is not found!");
                    break;
                }
                Client.addMessage((Object)((Object)ChatColor.GOLD) + h.name);
                Client.addMessage("Status: " + (h.status ? (Object)((Object)ChatColor.LIGHTGREEN) + "Enabled" : (Object)((Object)ChatColor.LIGHTRED) + "Disabled"));
                String s = "Settings: ";
                boolean hazSettings = false;
                for (Setting set : h.settings.values()) {
                    if (set.hidden) continue;
                    s = String.valueOf(s) + (Object)((Object)ChatColor.GOLD) + set.noWhitespacesName + (Object)((Object)ChatColor.WHITE) + "[" + set.valueToStringConsole() + (Object)((Object)ChatColor.WHITE) + "], ";
                    hazSettings = true;
                }
                if (!hazSettings) break;
                Client.addMessage(s.substring(0, s.length() - 2));
                break;
            }
            default: {
                Client.addMessage(".module list - List available modules");
                Client.addMessage(".module info <modulename> - Get information about module");
                Client.addMessage(".module toggle <modulename> - Toggle module by name");
                Client.addMessage(".module set <modulename> <settingname> <value> - Change module setting value");
                Client.addMessage(".module reset <modulename> <settingname> - Reset module setting to default");
                Client.addMessage(".module bind <modulename> <keycode|keyname> - Bind module to some key. The key name must start with 'KEY_'.");
                Client.addMessage(".module gbind <modulename> - Bind module to a key using gui.");
            }
        }
    }
}

