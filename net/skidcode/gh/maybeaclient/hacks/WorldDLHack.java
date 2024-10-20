/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import java.io.File;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkProviderClient;
import net.minecraft.src.Packet255KickDisconnect;
import net.minecraft.src.SaveHandler;
import net.minecraft.src.WorldClient;
import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventPacketReceive;
import net.skidcode.gh.maybeaclient.events.impl.EventPacketSend;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class WorldDLHack
extends Hack
implements EventListener {
    public static WorldDLHack instance;

    public WorldDLHack() {
        super("WorldDL", "Creates a world download", 0, Category.MISC);
        instance = this;
        EventRegistry.registerListener(EventPacketSend.class, this);
        EventRegistry.registerListener(EventPacketReceive.class, this);
    }

    @Override
    public void onEnable() {
        if (mc.isMultiplayerWorld()) {
            this.startDownload();
        } else {
            this.status = false;
        }
    }

    @Override
    public void onDisable() {
        if (mc.isMultiplayerWorld()) {
            this.stopDownload();
        }
    }

    public void stopDownload() {
        WorldClient worldclient = (WorldClient)WorldDLHack.mc.theWorld;
        worldclient.saveWorld(true, null);
        worldclient.worldInfo.setWorldName(String.valueOf(WorldDLHack.mc.getSendQueue().netManager.remoteSocketAddress.toString()) + " " + System.currentTimeMillis());
        worldclient.downloadThisWorld = false;
        worldclient.downloadChunkLoader = null;
        worldclient.downloadSaveHandler = null;
        WorldDLHack.mc.ingameGUI.addChatMessage("\u00a7c[WorldDL] \u00a7cDownload stopped.");
    }

    public void startDownload() {
        String s = WorldDLHack.mc.gameSettings.lastServer;
        if (s.isEmpty()) {
            s = "Downloaded World";
        }
        WorldClient worldclient = (WorldClient)WorldDLHack.mc.theWorld;
        worldclient.worldInfo.setWorldName(String.valueOf(s) + " " + System.currentTimeMillis());
        worldclient.downloadSaveHandler = (SaveHandler)mc.getSaveLoader().getSaveLoader(s, false);
        worldclient.downloadChunkLoader = worldclient.downloadSaveHandler.getChunkLoader(worldclient.worldProvider);
        worldclient.worldInfo.setSizeOnDisk(this.getFileSizeRecursive(worldclient.downloadSaveHandler.getSaveDirectory()));
        Chunk.wc = worldclient;
        ((ChunkProviderClient)worldclient.chunkProvider).importOldTileEntities();
        worldclient.downloadThisWorld = true;
        WorldDLHack.mc.ingameGUI.addChatMessage("\u00a7c[WorldDL] \u00a7cDownloading everything you can see...");
        WorldDLHack.mc.ingameGUI.addChatMessage("\u00a7c[WorldDL] \u00a76You can increase that area by travelling around.");
    }

    private long getFileSizeRecursive(File file) {
        File[] afile;
        long l = 0L;
        File[] afile1 = afile = file.listFiles();
        int i = afile1.length;
        int j = 0;
        while (j < i) {
            File file1 = afile1[j];
            if (file1.isDirectory()) {
                l += this.getFileSizeRecursive(file1);
            } else if (file1.isFile()) {
                l += file1.length();
            }
            ++j;
        }
        return l;
    }

    public void handleEvent(Event event) {
        if (event instanceof EventPacketSend && ((EventPacketSend)event).packet instanceof Packet255KickDisconnect && this.status) {
            this.stopDownload();
        }
        if (event instanceof EventPacketReceive && ((EventPacketReceive)event).packet instanceof Packet255KickDisconnect && this.status) {
            this.stopDownload();
        }
    }
}

