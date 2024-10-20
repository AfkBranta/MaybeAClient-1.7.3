/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import net.minecraft.src.Chunk;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NibbleArray;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.WorldInfo;

public class ChunkLoader
implements IChunkLoader {
    private File saveDir;
    private boolean createIfNecessary;

    public ChunkLoader(File var1, boolean var2) {
        this.saveDir = var1;
        this.createIfNecessary = var2;
    }

    private File chunkFileForXZ(int var1, int var2) {
        String var3 = "c." + Integer.toString(var1, 36) + "." + Integer.toString(var2, 36) + ".dat";
        String var4 = Integer.toString(var1 & 0x3F, 36);
        String var5 = Integer.toString(var2 & 0x3F, 36);
        File var6 = new File(this.saveDir, var4);
        if (!var6.exists()) {
            if (!this.createIfNecessary) {
                return null;
            }
            var6.mkdir();
        }
        if (!(var6 = new File(var6, var5)).exists()) {
            if (!this.createIfNecessary) {
                return null;
            }
            var6.mkdir();
        }
        return !(var6 = new File(var6, var3)).exists() && !this.createIfNecessary ? null : var6;
    }

    @Override
    public Chunk loadChunk(World var1, int var2, int var3) throws IOException {
        File var4 = this.chunkFileForXZ(var2, var3);
        if (var4 != null && var4.exists()) {
            NBTTagCompound var6;
            block7: {
                block6: {
                    FileInputStream var5 = new FileInputStream(var4);
                    var6 = CompressedStreamTools.func_1138_a(var5);
                    if (var6.hasKey("Level")) break block6;
                    System.out.println("Chunk file at " + var2 + "," + var3 + " is missing level data, skipping");
                    return null;
                }
                if (var6.getCompoundTag("Level").hasKey("Blocks")) break block7;
                System.out.println("Chunk file at " + var2 + "," + var3 + " is missing block data, skipping");
                return null;
            }
            try {
                Chunk var7 = ChunkLoader.loadChunkIntoWorldFromCompound(var1, var6.getCompoundTag("Level"));
                if (!var7.isAtLocation(var2, var3)) {
                    System.out.println("Chunk file at " + var2 + "," + var3 + " is in the wrong location; relocating. (Expected " + var2 + ", " + var3 + ", got " + var7.xPosition + ", " + var7.zPosition + ")");
                    var6.setInteger("xPos", var2);
                    var6.setInteger("zPos", var3);
                    var7 = ChunkLoader.loadChunkIntoWorldFromCompound(var1, var6.getCompoundTag("Level"));
                }
                var7.func_25124_i();
                return var7;
            }
            catch (Exception var8) {
                var8.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void saveChunk(World var1, Chunk var2) throws IOException {
        var1.checkSessionLock();
        File var3 = this.chunkFileForXZ(var2.xPosition, var2.zPosition);
        if (var3.exists()) {
            WorldInfo var4 = var1.func_22144_v();
            var4.setSizeOnDisk(var4.getSizeOnDisk() - var3.length());
        }
        try {
            File var10 = new File(this.saveDir, "tmp_chunk.dat");
            FileOutputStream var5 = new FileOutputStream(var10);
            NBTTagCompound var6 = new NBTTagCompound();
            NBTTagCompound var7 = new NBTTagCompound();
            var6.setTag("Level", var7);
            ChunkLoader.storeChunkInCompound(var2, var1, var7);
            CompressedStreamTools.writeGzippedCompoundToOutputStream(var6, var5);
            var5.close();
            if (var3.exists()) {
                var3.delete();
            }
            var10.renameTo(var3);
            WorldInfo var8 = var1.func_22144_v();
            var8.setSizeOnDisk(var8.getSizeOnDisk() + var3.length());
        }
        catch (Exception var9) {
            var9.printStackTrace();
        }
    }

    public static void storeChunkInCompound(Chunk var0, World var1, NBTTagCompound var2) {
        NBTTagCompound var7;
        var1.checkSessionLock();
        var2.setInteger("xPos", var0.xPosition);
        var2.setInteger("zPos", var0.zPosition);
        var2.setLong("LastUpdate", var1.getWorldTime());
        var2.setByteArray("Blocks", var0.blocks);
        var2.setByteArray("Data", var0.data.data);
        var2.setByteArray("SkyLight", var0.skylightMap.data);
        var2.setByteArray("BlockLight", var0.blocklightMap.data);
        var2.setByteArray("HeightMap", var0.heightMap);
        var2.setBoolean("TerrainPopulated", var0.isTerrainPopulated);
        var0.hasEntities = false;
        NBTTagList var3 = new NBTTagList();
        int var4 = 0;
        while (var4 < var0.entities.length) {
            for (Entity var6 : var0.entities[var4]) {
                var0.hasEntities = true;
                var7 = new NBTTagCompound();
                if (!var6.addEntityID(var7)) continue;
                var3.setTag(var7);
            }
            ++var4;
        }
        var2.setTag("Entities", var3);
        NBTTagList var8 = new NBTTagList();
        for (TileEntity var9 : var0.chunkTileEntityMap.values()) {
            var7 = new NBTTagCompound();
            var9.writeToNBT(var7);
            var8.setTag(var7);
        }
        var2.setTag("TileEntities", var8);
    }

    public static Chunk loadChunkIntoWorldFromCompound(World var0, NBTTagCompound var1) {
        NBTTagList var10;
        NBTTagList var5;
        int var2 = var1.getInteger("xPos");
        int var3 = var1.getInteger("zPos");
        Chunk var4 = new Chunk(var0, var2, var3);
        var4.blocks = var1.getByteArray("Blocks");
        var4.data = new NibbleArray(var1.getByteArray("Data"));
        var4.skylightMap = new NibbleArray(var1.getByteArray("SkyLight"));
        var4.blocklightMap = new NibbleArray(var1.getByteArray("BlockLight"));
        var4.heightMap = var1.getByteArray("HeightMap");
        var4.isTerrainPopulated = var1.getBoolean("TerrainPopulated");
        if (!var4.data.isValid()) {
            var4.data = new NibbleArray(var4.blocks.length);
        }
        if (var4.heightMap == null || !var4.skylightMap.isValid()) {
            var4.heightMap = new byte[256];
            var4.skylightMap = new NibbleArray(var4.blocks.length);
            var4.func_1024_c();
        }
        if (!var4.blocklightMap.isValid()) {
            var4.blocklightMap = new NibbleArray(var4.blocks.length);
            var4.func_1014_a();
        }
        if ((var5 = var1.getTagList("Entities")) != null) {
            int var6 = 0;
            while (var6 < var5.tagCount()) {
                NBTTagCompound var7 = (NBTTagCompound)var5.tagAt(var6);
                Entity var8 = EntityList.createEntityFromNBT(var7, var0);
                var4.hasEntities = true;
                if (var8 != null) {
                    var4.addEntity(var8);
                }
                ++var6;
            }
        }
        if ((var10 = var1.getTagList("TileEntities")) != null) {
            int var11 = 0;
            while (var11 < var10.tagCount()) {
                NBTTagCompound var12 = (NBTTagCompound)var10.tagAt(var11);
                TileEntity var9 = TileEntity.createAndLoadEntity(var12);
                if (var9 != null) {
                    var4.func_1001_a(var9);
                }
                ++var11;
            }
        }
        return var4;
    }

    @Override
    public void func_814_a() {
    }

    @Override
    public void saveExtraData() {
    }

    @Override
    public void saveExtraChunkData(World var1, Chunk var2) throws IOException {
    }
}

