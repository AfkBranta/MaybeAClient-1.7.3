/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkLoader;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.RegionFileCache;
import net.minecraft.src.World;
import net.minecraft.src.WorldInfo;

public class McRegionChunkLoader
implements IChunkLoader {
    private final File worldDir;

    public McRegionChunkLoader(File var1) {
        this.worldDir = var1;
    }

    @Override
    public Chunk loadChunk(World var1, int var2, int var3) throws IOException {
        DataInputStream var4 = RegionFileCache.getChunkInputStream(this.worldDir, var2, var3);
        if (var4 != null) {
            NBTTagCompound var5 = CompressedStreamTools.func_1141_a(var4);
            if (!var5.hasKey("Level")) {
                System.out.println("Chunk file at " + var2 + "," + var3 + " is missing level data, skipping");
                return null;
            }
            if (!var5.getCompoundTag("Level").hasKey("Blocks")) {
                System.out.println("Chunk file at " + var2 + "," + var3 + " is missing block data, skipping");
                return null;
            }
            Chunk var6 = ChunkLoader.loadChunkIntoWorldFromCompound(var1, var5.getCompoundTag("Level"));
            if (!var6.isAtLocation(var2, var3)) {
                System.out.println("Chunk file at " + var2 + "," + var3 + " is in the wrong location; relocating. (Expected " + var2 + ", " + var3 + ", got " + var6.xPosition + ", " + var6.zPosition + ")");
                var5.setInteger("xPos", var2);
                var5.setInteger("zPos", var3);
                var6 = ChunkLoader.loadChunkIntoWorldFromCompound(var1, var5.getCompoundTag("Level"));
            }
            var6.func_25124_i();
            return var6;
        }
        return null;
    }

    @Override
    public void saveChunk(World var1, Chunk var2) throws IOException {
        var1.checkSessionLock();
        try {
            DataOutputStream var3 = RegionFileCache.getChunkOutputStream(this.worldDir, var2.xPosition, var2.zPosition);
            NBTTagCompound var4 = new NBTTagCompound();
            NBTTagCompound var5 = new NBTTagCompound();
            var4.setTag("Level", var5);
            ChunkLoader.storeChunkInCompound(var2, var1, var5);
            CompressedStreamTools.func_1139_a(var4, var3);
            var3.close();
            WorldInfo var6 = var1.func_22144_v();
            var6.setSizeOnDisk(var6.getSizeOnDisk() + (long)RegionFileCache.func_22191_b(this.worldDir, var2.xPosition, var2.zPosition));
        }
        catch (Exception var7) {
            var7.printStackTrace();
        }
    }

    @Override
    public void saveExtraChunkData(World var1, Chunk var2) throws IOException {
    }

    @Override
    public void func_814_a() {
    }

    @Override
    public void saveExtraData() {
    }
}

