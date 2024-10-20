/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagInt;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NBTTagString;
import net.skidcode.gh.maybeaclient.console.Command;
import net.skidcode.gh.maybeaclient.console.CommandAlias;
import net.skidcode.gh.maybeaclient.console.CommandHelp;
import net.skidcode.gh.maybeaclient.console.CommandModule;
import net.skidcode.gh.maybeaclient.console.CommandSlot9;
import net.skidcode.gh.maybeaclient.console.CommandTab;
import net.skidcode.gh.maybeaclient.gui.altman.AccountInfo;
import net.skidcode.gh.maybeaclient.gui.altman.GuiAccManager;
import net.skidcode.gh.maybeaclient.gui.altman.PasswordInfo;
import net.skidcode.gh.maybeaclient.gui.click.ClickGUI;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.gui.server.GuiServerSelector;
import net.skidcode.gh.maybeaclient.gui.server.ServerInfo;
import net.skidcode.gh.maybeaclient.hacks.AntiKnockbackHack;
import net.skidcode.gh.maybeaclient.hacks.AntiSlowdownHack;
import net.skidcode.gh.maybeaclient.hacks.ArrayListHack;
import net.skidcode.gh.maybeaclient.hacks.AutoEatHack;
import net.skidcode.gh.maybeaclient.hacks.AutoLoginHack;
import net.skidcode.gh.maybeaclient.hacks.AutoMouseClickHack;
import net.skidcode.gh.maybeaclient.hacks.AutoReconnectHack;
import net.skidcode.gh.maybeaclient.hacks.AutoToolHack;
import net.skidcode.gh.maybeaclient.hacks.AutoTunnelHack;
import net.skidcode.gh.maybeaclient.hacks.AutoWalkHack;
import net.skidcode.gh.maybeaclient.hacks.BlockESPHack;
import net.skidcode.gh.maybeaclient.hacks.CameraLockHack;
import net.skidcode.gh.maybeaclient.hacks.ClickGUIHack;
import net.skidcode.gh.maybeaclient.hacks.ClientInfoHack;
import net.skidcode.gh.maybeaclient.hacks.ClientNameHack;
import net.skidcode.gh.maybeaclient.hacks.ClockspeedHack;
import net.skidcode.gh.maybeaclient.hacks.CombatLogHack;
import net.skidcode.gh.maybeaclient.hacks.EntityESPHack;
import net.skidcode.gh.maybeaclient.hacks.FOVHack;
import net.skidcode.gh.maybeaclient.hacks.FastLadderHack;
import net.skidcode.gh.maybeaclient.hacks.FastPlaceHack;
import net.skidcode.gh.maybeaclient.hacks.FlyHack;
import net.skidcode.gh.maybeaclient.hacks.ForceFieldHack;
import net.skidcode.gh.maybeaclient.hacks.FreecamHack;
import net.skidcode.gh.maybeaclient.hacks.FullBrightHack;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.InstantHack;
import net.skidcode.gh.maybeaclient.hacks.InventoryViewHack;
import net.skidcode.gh.maybeaclient.hacks.InventoryWalkHack;
import net.skidcode.gh.maybeaclient.hacks.ItemNameTagsHack;
import net.skidcode.gh.maybeaclient.hacks.JesusHack;
import net.skidcode.gh.maybeaclient.hacks.KeybindingsHack;
import net.skidcode.gh.maybeaclient.hacks.LiquidInteractHack;
import net.skidcode.gh.maybeaclient.hacks.LockTimeHack;
import net.skidcode.gh.maybeaclient.hacks.NameTagsHack;
import net.skidcode.gh.maybeaclient.hacks.NoClientSideDestroyHack;
import net.skidcode.gh.maybeaclient.hacks.NoClipHack;
import net.skidcode.gh.maybeaclient.hacks.NoFallHack;
import net.skidcode.gh.maybeaclient.hacks.NoPushHack;
import net.skidcode.gh.maybeaclient.hacks.NoRenderHack;
import net.skidcode.gh.maybeaclient.hacks.Packet19SenderHack;
import net.skidcode.gh.maybeaclient.hacks.PlayerViewHack;
import net.skidcode.gh.maybeaclient.hacks.RadarHack;
import net.skidcode.gh.maybeaclient.hacks.ReachHack;
import net.skidcode.gh.maybeaclient.hacks.SafeWalkHack;
import net.skidcode.gh.maybeaclient.hacks.ScaffoldHack;
import net.skidcode.gh.maybeaclient.hacks.SchematicaHack;
import net.skidcode.gh.maybeaclient.hacks.ServerStatusHack;
import net.skidcode.gh.maybeaclient.hacks.SpeedMineHack;
import net.skidcode.gh.maybeaclient.hacks.StepHack;
import net.skidcode.gh.maybeaclient.hacks.StrafeHack;
import net.skidcode.gh.maybeaclient.hacks.TracersHack;
import net.skidcode.gh.maybeaclient.hacks.WorldDLHack;
import net.skidcode.gh.maybeaclient.hacks.XCarryHack;
import net.skidcode.gh.maybeaclient.hacks.XRayHack;
import net.skidcode.gh.maybeaclient.hacks.settings.Setting;
import net.skidcode.gh.maybeaclient.utils.ChatColor;
import net.skidcode.gh.maybeaclient.utils.TextureUtils;

public class Client {
    public static HashMap<String, Hack> hacksByName = new HashMap();
    public static HashMap<String, Command> commands = new HashMap();
    public static HashMap<String, String> aliases = new HashMap();
    public static ArrayList<String> aliasesList = new ArrayList();
    public static String cmdPrefix = ".";
    public static Minecraft mc;
    public static final String clientName = "MaybeAClient";
    public static final String clientVersion = "1.7.3";
    public static final int saveVersion = 3;
    public static int convertingVersion;
    public static BufferedImage outlinedItemsTexture;
    public static BufferedImage outlinedTerrainTexture;

    static {
        convertingVersion = 0;
        Client.registerHack(new FlyHack());
        Client.registerHack(new JesusHack());
        Client.registerHack(new NoFallHack());
        Client.registerHack(new XRayHack());
        Client.registerHack(new FullBrightHack());
        Client.registerHack(new ForceFieldHack());
        Client.registerHack(new EntityESPHack());
        Client.registerHack(new FreecamHack());
        Client.registerHack(new RadarHack());
        Client.registerHack(new LockTimeHack());
        Client.registerHack(new LiquidInteractHack());
        Client.registerHack(new InstantHack());
        Client.registerHack(new NoClipHack());
        Client.registerHack(new ClientInfoHack());
        Client.registerHack(new KeybindingsHack());
        Client.registerHack(new ClockspeedHack());
        Client.registerHack(new AntiKnockbackHack());
        Client.registerHack(new ServerStatusHack());
        Client.registerHack(new AutoWalkHack());
        Client.registerHack(new SafeWalkHack());
        Client.registerHack(new StrafeHack());
        Client.registerHack(new CameraLockHack());
        Client.registerHack(new SpeedMineHack());
        Client.registerHack(new AutoTunnelHack());
        Client.registerHack(new ArrayListHack());
        Client.registerHack(new StepHack());
        Client.registerHack(new ClickGUIHack());
        Client.registerHack(new ClientNameHack());
        Client.registerHack(new Packet19SenderHack());
        Client.registerHack(new TracersHack());
        Client.registerHack(new BlockESPHack());
        Client.registerHack(new NameTagsHack());
        Client.registerHack(new NoClientSideDestroyHack());
        Client.registerHack(new SchematicaHack());
        Client.registerHack(new FastPlaceHack());
        Client.registerHack(new ReachHack());
        Client.registerHack(new FastLadderHack());
        Client.registerHack(new NoPushHack());
        Client.registerHack(new WorldDLHack());
        Client.registerHack(new FOVHack());
        Client.registerHack(new ScaffoldHack());
        Client.registerHack(new InventoryViewHack());
        Client.registerHack(new PlayerViewHack());
        Client.registerHack(new CombatLogHack());
        Client.registerHack(new AutoEatHack());
        Client.registerHack(new AutoMouseClickHack());
        Client.registerHack(new InventoryWalkHack());
        Client.registerHack(new XCarryHack());
        Client.registerHack(new AutoToolHack());
        Client.registerHack(new AutoReconnectHack());
        Client.registerHack(new AutoLoginHack());
        Client.registerHack(new ItemNameTagsHack());
        Client.registerHack(new AntiSlowdownHack());
        Client.registerHack(new NoRenderHack());
        Client.registerCommand(new CommandModule());
        Client.registerCommand(new CommandHelp());
        Client.registerCommand(new CommandSlot9());
        Client.registerCommand(new CommandAlias());
        Client.registerCommand(new CommandTab());
        try {
            Client.postClientLoad();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerHack(Hack hack) {
        hacksByName.put(hack.name.toLowerCase(), hack);
    }

    public static void registerCommand(Command cmd) {
        commands.put(cmd.name.toLowerCase(), cmd);
    }

    public static void addMessage(String msg) {
        Client.addMessageRaw(String.format("%s[%s]:%s %s", new Object[]{ChatColor.CYAN, clientName, ChatColor.WHITE, msg}));
    }

    public static void addMessageRaw(String msg) {
        Client.mc.thePlayer.addChatMessage(msg);
    }

    public static void handleCommand(String command) {
        Client.handleCommand(command, true);
    }

    public static void handleCommand(String command, boolean sendToChat) {
        Command cmd;
        String[] cmds = command.split(" ");
        int parcnt = cmds.length;
        if (sendToChat) {
            Client.mc.thePlayer.addChatMessageWithMoreOpacityBG((Object)((Object)ChatColor.LIGHTGRAY) + cmdPrefix + command);
        }
        if ((cmd = commands.get(cmds[0].toLowerCase())) == null) {
            String cmdalias = aliases.get(cmds[0].toLowerCase());
            if (cmdalias != null) {
                String newcommand = cmdalias;
                int i = 1;
                while (i < cmds.length) {
                    newcommand = String.valueOf(newcommand) + " " + cmds[i];
                    ++i;
                }
                Client.handleCommand(newcommand, false);
            } else {
                Client.addMessage("Invalid command: " + (Object)((Object)ChatColor.GOLD) + cmds[0] + (Object)((Object)ChatColor.WHITE) + ". Use " + (Object)((Object)ChatColor.GOLD) + cmdPrefix + "help" + (Object)((Object)ChatColor.WHITE) + " to view the available commands.");
            }
        } else {
            String[] args = Arrays.copyOfRange(cmds, 1, cmds.length);
            cmd.onTyped(args);
        }
    }

    public static String getAlias(String alias) {
        return aliases.get(alias.toLowerCase());
    }

    public static void removeAlias(String alias) {
        alias = alias.toLowerCase();
        aliases.remove(alias);
        if (aliasesList.contains(alias)) {
            aliasesList.remove(alias);
        }
    }

    public static void addAlias(String alias, String command) {
        alias = alias.toLowerCase();
        aliases.put(alias, command);
        if (!aliasesList.contains(alias)) {
            aliasesList.add(alias);
        }
    }

    public static void overrideHackInfo(Hack hack, NBTTagCompound input) {
        hack.status = input.getBoolean("Status");
        if (input.hasKey("Keybind")) {
            hack.keybinding.setValue(input.getInteger("Keybind"));
        }
        NBTTagCompound settings = input.getCompoundTag("Settings");
        if (hack.equals(EntityESPHack.instance) && settings.hasKey("Mode")) {
            EntityESPHack.instance.animalsMode.setValue(settings.getString("Mode"));
            EntityESPHack.instance.hostileMode.setValue(settings.getString("Mode"));
            EntityESPHack.instance.playersMode.setValue(settings.getString("Mode"));
        }
        for (Map.Entry<String, Setting> entry : hack.settings.entrySet()) {
            entry.getValue().readFromNBT(settings);
        }
    }

    public static void writeHackInfo(Hack hack, NBTTagCompound output) {
        NBTTagCompound hacc = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        hacc.setBoolean("Status", hack.status);
        NBTTagCompound settings = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        for (Map.Entry<String, Setting> entry : hack.settings.entrySet()) {
            entry.getValue().writeToNBT(settings);
        }
        hacc.setCompoundTag("Settings", settings);
        output.setCompoundTag(hack.name.toLowerCase(), hacc);
    }

    public static void writeModuleSettings(File f) throws IOException {
        NBTTagCompound comp = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        for (Map.Entry<String, Hack> entry : hacksByName.entrySet()) {
            Hack hack = entry.getValue();
            Client.writeHackInfo(hack, comp);
        }
        NBTTagCompound write = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        write.setCompoundTag("Modules", comp);
        write.setInteger("FormatVersion", 3);
        write.writeTagContents(new DataOutputStream(new FileOutputStream(f)));
    }

    public static void saveModules() {
        try {
            File f = new File(Minecraft.getMinecraftDir() + "/MaybeAClient/");
            f.mkdirs();
            File ms = new File(f, "module-settings");
            if (!ms.exists()) {
                ms.createNewFile();
            }
            Client.writeModuleSettings(ms);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save module settings!");
        }
    }

    public static void saveClickGUI() {
        try {
            File f = new File(Minecraft.getMinecraftDir() + "/MaybeAClient/");
            f.mkdirs();
            File ms = new File(f, "clickgui-settings");
            if (!ms.exists()) {
                ms.createNewFile();
            }
            Client.writeClickGUISettings(ms);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save clickgui!");
        }
    }

    public static void saveAliases() {
        try {
            File f = new File(Minecraft.getMinecraftDir() + "/MaybeAClient/");
            f.mkdirs();
            File ms = new File(f, "aliases");
            if (!ms.exists()) {
                ms.createNewFile();
            }
            Client.writeCommandAliases(ms);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save aliases!");
        }
    }

    public static void readModuleSettings(File f) throws IOException {
        NBTTagCompound modules = null;
        NBTTagInt version = null;
        DataInputStream dis = new DataInputStream(new FileInputStream(f));
        while (true) {
            NBTBase base;
            if ((base = NBTBase.readTag(dis)) instanceof NBTTagInt) {
                version = (NBTTagInt)base;
                continue;
            }
            if (!(base instanceof NBTTagCompound)) break;
            modules = (NBTTagCompound)base;
        }
        if (modules == null) {
            System.out.println("No module information was found!");
        }
        if (version == null) {
            System.out.println("No version was found!");
        }
        if (modules == null) {
            return;
        }
        if (version.intValue != 3) {
            System.out.println(String.format("Module settings save version does not match client(%d != %d). Converting...", version.intValue, 3));
            convertingVersion = version.intValue;
        }
        for (Map.Entry<String, Hack> entry : hacksByName.entrySet()) {
            NBTTagCompound hacc;
            Hack hack = entry.getValue();
            if (!modules.hasKey(hack.name.toLowerCase())) {
                if (hack.name.equalsIgnoreCase(ClientInfoHack.instance.name) && modules.hasKey("coords")) {
                    hacc = modules.getCompoundTag("coords");
                    Client.overrideHackInfo(hack, hacc);
                } else if (hack.name.equalsIgnoreCase(NoClientSideDestroyHack.instance.name) && modules.hasKey("noclientsidedestroy")) {
                    hacc = modules.getCompoundTag("noclientsidedestroy");
                    Client.overrideHackInfo(hack, hacc);
                } else {
                    System.out.println("Information about " + hack.name + " was not found");
                }
            } else {
                hacc = modules.getCompoundTag(hack.name.toLowerCase());
                Client.overrideHackInfo(hack, hacc);
            }
            Client.writeHackInfo(hack, modules);
        }
        NBTTagCompound write = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        write.setCompoundTag("Modules", modules);
        write.setInteger("FormatVersion", 3);
        write.writeTagContents(new DataOutputStream(new FileOutputStream(f)));
        convertingVersion = 0;
    }

    public static void readClickGUISettings(File f) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(f));
        NBTBase base = NBTBase.readTag(dis);
        NBTTagCompound cc = (NBTTagCompound)base;
        NBTTagCompound clickgui = cc.getCompoundTag("ClickGUI");
        int version = cc.getInteger("FormatVersion");
        if (version != 3) {
            System.out.println(String.format("Gui settings save version does not match client(%d != %d). Converting...", version, 3));
            convertingVersion = version;
        }
        int lowest = Integer.MIN_VALUE;
        HashMap<Integer, Tab> priority = new HashMap<Integer, Tab>();
        for (Tab tab : ClickGUI.tabs) {
            NBTTagCompound tg = clickgui.getCompoundTag(tab.name.toLowerCase());
            if (!clickgui.hasKey(tab.name.toLowerCase())) {
                System.out.println("Information about " + tab.name + " not found.");
                priority.put(lowest++, tab);
                continue;
            }
            tab.readFromNBT(tg);
            if (tg.hasKey("Priority")) {
                int tabprio = tg.getInteger("Priority");
                if (priority.containsKey(tabprio)) {
                    System.out.println("Priortity " + tabprio + " was set to another tab already!");
                    priority.put(lowest++, tab);
                    continue;
                }
                priority.put(tabprio, tab);
                continue;
            }
            priority.put(lowest++, tab);
        }
        ClickGUI.tabs.clear();
        ArrayList arr = new ArrayList(priority.entrySet());
        arr.sort(Map.Entry.comparingByKey());
        for (Map.Entry entry : arr) {
            ClickGUI.tabs.add((Tab)entry.getValue());
        }
    }

    public static void writeClickGUISettings(File f) throws IOException {
        NBTTagCompound clickgui = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        for (Tab tab : ClickGUI.tabs) {
            NBTTagCompound tb = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
            clickgui.setCompoundTag(tab.name.toLowerCase(), tb);
            tab.writeToNBT(tb);
        }
        NBTTagCompound write1 = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        NBTTagCompound write2 = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        write1.setCompoundTag("ClickGUI", clickgui);
        write1.setInteger("FormatVersion", 3);
        write2.setCompoundTag("", write1);
        write2.writeTagContents(new DataOutputStream(new FileOutputStream(f)));
    }

    public static void readCommandAliases(File f) throws IOException {
        NBTTagCompound aliases = null;
        NBTTagInt version = null;
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        while (true) {
            NBTBase base;
            if ((base = NBTBase.readTag(in)) instanceof NBTTagInt) {
                version = (NBTTagInt)base;
                continue;
            }
            if (!(base instanceof NBTTagCompound)) break;
            aliases = (NBTTagCompound)base;
        }
        if (aliases == null) {
            System.out.println("No aliases information was found!");
        }
        if (version == null) {
            System.out.println("No version was found!");
        }
        for (Map.Entry<String, NBTBase> entry : aliases.tagMap.entrySet()) {
            String alias = entry.getKey();
            NBTBase bs = entry.getValue();
            if (bs instanceof NBTTagString) {
                NBTTagString cmd = (NBTTagString)bs;
                Client.addAlias(alias, cmd.stringValue);
                continue;
            }
            System.out.println("Alias " + alias + " has wrong nbttag type(" + bs.getType() + ")!");
        }
        NBTTagCompound write = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        write.setCompoundTag("Aliases", aliases);
        write.setInteger("FormatVersion", 3);
        write.writeTagContents(new DataOutputStream(new FileOutputStream(f)));
    }

    public static void writeCommandAliases(File f) throws IOException {
        NBTTagCompound comp = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        for (Map.Entry<String, String> entry : aliases.entrySet()) {
            comp.setString(entry.getKey().toLowerCase(), entry.getValue());
        }
        NBTTagCompound write = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        write.setCompoundTag("Aliases", comp);
        write.setInteger("FormatVersion", 3);
        write.writeTagContents(new DataOutputStream(new FileOutputStream(f)));
    }

    public static void readCurrentServers(File f) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(f));
        NBTBase base = NBTBase.readTag(dis);
        NBTTagCompound cc = (NBTTagCompound)base;
        NBTTagList servers = cc.getTagList("Servers");
        int i = 0;
        while (i < servers.tagCount()) {
            NBTBase tag = servers.tagAt(i);
            if (tag instanceof NBTTagCompound) {
                NBTTagCompound server = (NBTTagCompound)tag;
                String name = server.getString("Name");
                String ip = server.getString("IP");
                int port = server.getInteger("Port");
                GuiServerSelector.servers.add(new ServerInfo(name, ip, port));
            } else {
                System.out.println("Server entry is not compound! " + tag);
            }
            ++i;
        }
    }

    public static void writeCurrentServers() {
        try {
            File fpr = new File(Minecraft.getMinecraftDir() + "/MaybeAClient_USER/");
            File servers = new File(fpr, "servers");
            Client.writeCurrentServers(servers);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCurrentServers(File f) throws IOException {
        NBTTagList comp = (NBTTagList)NBTBase.createTagOfType((byte)9);
        for (ServerInfo server : GuiServerSelector.servers) {
            NBTTagCompound info = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
            info.setString("Name", server.name);
            info.setString("IP", server.ip);
            info.setInteger("Port", server.port);
            comp.setTag(info);
        }
        NBTTagCompound write = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        NBTTagCompound write2 = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        write.setTag("Servers", comp);
        write.setInteger("FormatVersion", 3);
        write2.setCompoundTag("", write);
        write2.writeTagContents(new DataOutputStream(new FileOutputStream(f)));
    }

    public static void postClientLoad() throws IOException {
        System.out.println("Loaded " + hacksByName.size() + " modules, " + commands.size() + " commands.");
        ClickGUI.registerTabs();
        File f = new File(Minecraft.getMinecraftDir() + "/MaybeAClient/");
        File fpr = new File(Minecraft.getMinecraftDir() + "/MaybeAClient_USER/");
        f.mkdirs();
        fpr.mkdirs();
        System.out.println("Getting module settings");
        File ms = new File(f, "module-settings");
        if (ms.exists()) {
            Client.readModuleSettings(ms);
        } else {
            ms.createNewFile();
            Client.writeModuleSettings(ms);
        }
        System.out.println("Getting command aliases");
        File aliases = new File(f, "aliases");
        if (aliases.exists()) {
            Client.readCommandAliases(aliases);
        } else {
            aliases.createNewFile();
            Client.writeCommandAliases(aliases);
        }
        System.out.println("Getting clickgui settings");
        File clickgui = new File(f, "clickgui-settings");
        if (clickgui.exists()) {
            Client.readClickGUISettings(clickgui);
            Client.writeClickGUISettings(clickgui);
        } else {
            clickgui.createNewFile();
            Client.writeClickGUISettings(clickgui);
        }
        System.out.println("Getting servers");
        File servers = new File(fpr, "servers");
        if (servers.exists()) {
            Client.readCurrentServers(servers);
            Client.writeCurrentServers(servers);
        } else {
            servers.createNewFile();
            Client.writeCurrentServers(servers);
        }
        System.out.println("Getting accounts");
        File accounts = new File(fpr, "accounts");
        if (accounts.exists()) {
            Client.readCurrentAccounts(accounts);
            Client.writeCurrentAccounts(accounts);
        } else {
            accounts.createNewFile();
            Client.writeCurrentAccounts(accounts);
        }
        ClickGUIHack.instance.status = false;
        Client.generateOutlinedTextures();
    }

    public static void writeCurrentAccounts() {
        try {
            File fpr = new File(Minecraft.getMinecraftDir() + "/MaybeAClient_USER/");
            File servers = new File(fpr, "accounts");
            Client.writeCurrentAccounts(servers);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readCurrentAccounts(File f) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(f));
        NBTBase base = NBTBase.readTag(dis);
        NBTTagCompound cc = (NBTTagCompound)base;
        NBTTagList accounts = cc.getTagList("Accounts");
        int i = 0;
        while (i < accounts.tagCount()) {
            NBTBase tag = accounts.tagAt(i);
            if (tag instanceof NBTTagCompound) {
                NBTTagCompound account = (NBTTagCompound)tag;
                String username = account.getString("Username");
                AccountInfo info = new AccountInfo(username);
                NBTTagList passwords = account.getTagList("Passwords");
                int j = 0;
                while (j < passwords.tagCount()) {
                    NBTBase pwdtag = passwords.tagAt(j);
                    if (pwdtag instanceof NBTTagCompound) {
                        NBTTagCompound pwdtag2 = (NBTTagCompound)pwdtag;
                        String password = pwdtag2.getString("Password");
                        String prompt = pwdtag2.getString("Prompt");
                        String loginCmd = pwdtag2.getString("LoginCommand");
                        String ipAddress = pwdtag2.getString("IP");
                        PasswordInfo.MatchMode[] vals = PasswordInfo.MatchMode.values();
                        int ii = pwdtag2.getInteger("Mode");
                        if (ii >= vals.length) {
                            System.out.println("Invalid mode! (" + ii + "/" + vals.length + ")");
                            ii = 0;
                        }
                        PasswordInfo passInfo = new PasswordInfo(password, ipAddress);
                        passInfo.loginPrompt = prompt;
                        passInfo.loginCommand = loginCmd;
                        passInfo.mode = vals[ii];
                        info.addPassword(passInfo);
                    }
                    ++j;
                }
                GuiAccManager.addAccount(info);
            } else {
                System.out.println("Account entry is not compound! " + tag);
            }
            ++i;
        }
    }

    public static void writeCurrentAccounts(File f) throws IOException {
        NBTTagList comp = (NBTTagList)NBTBase.createTagOfType((byte)9);
        for (AccountInfo acc : GuiAccManager.accounts) {
            NBTTagCompound info = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
            info.setString("Username", acc.name);
            NBTTagList passwds = (NBTTagList)NBTBase.createTagOfType((byte)9);
            for (PasswordInfo pass : acc.pwds) {
                NBTTagCompound ps = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
                ps.setString("Password", pass.password);
                ps.setString("Prompt", pass.loginPrompt);
                ps.setString("LoginCommand", pass.loginCommand);
                ps.setString("IP", pass.serverIP);
                ps.setInteger("Mode", pass.mode.ordinal());
                passwds.setTag(ps);
            }
            info.setTag("Passwords", passwds);
            comp.setTag(info);
        }
        NBTTagCompound write = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        NBTTagCompound write2 = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        write.setTag("Accounts", comp);
        write.setInteger("FormatVersion", 3);
        write2.setCompoundTag("", write);
        write2.writeTagContents(new DataOutputStream(new FileOutputStream(f)));
    }

    public static void generateOutlinedTextures() throws IOException {
        outlinedItemsTexture = TextureUtils.generateOutlinedTexture(ImageIO.read(Minecraft.class.getResource("/gui/items.png")));
        outlinedTerrainTexture = TextureUtils.generateOutlinedTexture(ImageIO.read(Minecraft.class.getResource("/terrain.png")));
    }

    public static void onKeyPress(int keycode, boolean pressed) {
        if (keycode == 0) {
            System.out.println("Tried activating 0 keycode(NONE) somehow??");
            return;
        }
        if (pressed) {
            for (Hack h : hacksByName.values()) {
                if (h.keybinding.value != keycode) continue;
                h.toggle();
            }
        }
    }
}

