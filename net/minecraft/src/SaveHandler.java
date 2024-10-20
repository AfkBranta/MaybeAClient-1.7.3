/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import net.minecraft.src.ChunkLoader;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldProviderHell;

public class SaveHandler
implements ISaveHandler {
    private static final Logger logger = Logger.getLogger("Minecraft");
    private final File saveDirectory;
    private final File playersDirectory;
    private final long now = System.currentTimeMillis();

    public SaveHandler(File var1, String var2, boolean var3) {
        this.saveDirectory = new File(var1, var2);
        this.saveDirectory.mkdirs();
        this.playersDirectory = new File(this.saveDirectory, "players");
        if (var3) {
            this.playersDirectory.mkdirs();
        }
        this.func_22154_d();
    }

    private void func_22154_d() {
        try {
            File var1 = new File(this.saveDirectory, "session.lock");
            try (DataOutputStream var2 = new DataOutputStream(new FileOutputStream(var1));){
                var2.writeLong(this.now);
            }
        }
        catch (IOException var7) {
            var7.printStackTrace();
            throw new RuntimeException("Failed to check session lock, aborting");
        }
    }

    public File getSaveDirectory() {
        return this.saveDirectory;
    }

    @Override
    public void func_22150_b() {
        try {
            File var1 = new File(this.saveDirectory, "session.lock");
            try (DataInputStream var2 = new DataInputStream(new FileInputStream(var1));){
                if (var2.readLong() != this.now) {
                    throw new MinecraftException("The save is being accessed from another location, aborting");
                }
            }
        }
        catch (IOException var7) {
            throw new MinecraftException("Failed to check session lock, aborting");
        }
    }

    @Override
    public IChunkLoader getChunkLoader(WorldProvider var1) {
        if (var1 instanceof WorldProviderHell) {
            File var2 = new File(this.saveDirectory, "DIM-1");
            var2.mkdirs();
            return new ChunkLoader(var2, true);
        }
        return new ChunkLoader(this.saveDirectory, true);
    }

    @Override
    public WorldInfo loadWorldInfo() {
        File var1 = new File(this.saveDirectory, "level.dat");
        if (var1.exists()) {
            try {
                NBTTagCompound var2 = CompressedStreamTools.func_1138_a(new FileInputStream(var1));
                NBTTagCompound var3 = var2.getCompoundTag("Data");
                return new WorldInfo(var3);
            }
            catch (Exception var4) {
                var4.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void saveWorldInfoAndPlayer(WorldInfo var1, List var2) {
        NBTTagCompound var3 = var1.getNBTTagCompoundWithPlayer(var2);
        NBTTagCompound var4 = new NBTTagCompound();
        var4.setTag("Data", var3);
        try {
            File var5 = new File(this.saveDirectory, "level.dat_new");
            File var6 = new File(this.saveDirectory, "level.dat_old");
            File var7 = new File(this.saveDirectory, "level.dat");
            CompressedStreamTools.writeGzippedCompoundToOutputStream(var4, new FileOutputStream(var5));
            if (var6.exists()) {
                var6.delete();
            }
            var7.renameTo(var6);
            if (var7.exists()) {
                var7.delete();
            }
            var5.renameTo(var7);
            if (var5.exists()) {
                var5.delete();
            }
        }
        catch (Exception var8) {
            var8.printStackTrace();
        }
    }

    @Override
    public void saveWorldInfo(WorldInfo var1) {
        NBTTagCompound var2 = var1.getNBTTagCompound();
        NBTTagCompound var3 = new NBTTagCompound();
        var3.setTag("Data", var2);
        try {
            File var4 = new File(this.saveDirectory, "level.dat_new");
            File var5 = new File(this.saveDirectory, "level.dat_old");
            File var6 = new File(this.saveDirectory, "level.dat");
            CompressedStreamTools.writeGzippedCompoundToOutputStream(var3, new FileOutputStream(var4));
            if (var5.exists()) {
                var5.delete();
            }
            var6.renameTo(var5);
            if (var6.exists()) {
                var6.delete();
            }
            var4.renameTo(var6);
            if (var4.exists()) {
                var4.delete();
            }
        }
        catch (Exception var7) {
            var7.printStackTrace();
        }
    }
}

