/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.ChunkBlockMap;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.Entity;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NibbleArray;
import net.minecraft.src.RegionFileCache;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.WorldClient;
import net.minecraft.src.WorldProviderHell;

public class Chunk {
    public static boolean isLit;
    public static WorldClient wc;
    public byte[] blocks;
    public boolean isChunkLoaded;
    public World worldObj;
    public NibbleArray data;
    public NibbleArray skylightMap;
    public NibbleArray blocklightMap;
    public byte[] heightMap;
    public int field_1532_i;
    public final int xPosition;
    public final int zPosition;
    public Map chunkTileEntityMap;
    public List[] entities;
    public boolean isTerrainPopulated = false;
    public boolean isModified = false;
    public boolean neverSave;
    public boolean hasEntities = false;
    public long lastSaveTime = 0L;
    public Map newChunkTileEntityMap = new HashMap();
    public boolean isFilled = false;

    public Chunk(World var1, int var2, int var3) {
        this.chunkTileEntityMap = new HashMap();
        this.entities = new List[8];
        this.worldObj = var1;
        this.xPosition = var2;
        this.zPosition = var3;
        this.heightMap = new byte[256];
        int var4 = 0;
        while (var4 < this.entities.length) {
            this.entities[var4] = new ArrayList();
            ++var4;
        }
    }

    public void setNewChunkBlockTileEntity(int i, int j, int k, TileEntity tileentity) {
        ChunkPosition chunkposition = new ChunkPosition(i, j, k);
        tileentity.worldObj = this.worldObj;
        tileentity.xCoord = this.xPosition * 16 + i;
        tileentity.yCoord = j;
        tileentity.zCoord = this.zPosition * 16 + k;
        this.newChunkTileEntityMap.put(chunkposition, tileentity);
    }

    public Chunk(World var1, byte[] var2, int var3, int var4) {
        this(var1, var3, var4);
        this.blocks = var2;
        this.data = new NibbleArray(var2.length);
        this.skylightMap = new NibbleArray(var2.length);
        this.blocklightMap = new NibbleArray(var2.length);
    }

    public boolean isAtLocation(int var1, int var2) {
        return var1 == this.xPosition && var2 == this.zPosition;
    }

    public int getHeightValue(int var1, int var2) {
        return this.heightMap[var2 << 4 | var1] & 0xFF;
    }

    public void func_1014_a() {
    }

    public void generateHeightMap() {
        int var1 = 127;
        int var2 = 0;
        while (var2 < 16) {
            int var3 = 0;
            while (var3 < 16) {
                int var4 = 127;
                int var5 = var2 << 11 | var3 << 7;
                while (var4 > 0 && Block.lightOpacity[this.blocks[var5 + var4 - 1] & 0xFF] == 0) {
                    --var4;
                }
                this.heightMap[var3 << 4 | var2] = (byte)var4;
                if (var4 < var1) {
                    var1 = var4;
                }
                ++var3;
            }
            ++var2;
        }
        this.field_1532_i = var1;
        this.isModified = true;
    }

    public void func_1024_c() {
        int var3;
        int var1 = 127;
        int var2 = 0;
        while (var2 < 16) {
            var3 = 0;
            while (var3 < 16) {
                int var4 = 127;
                int var5 = var2 << 11 | var3 << 7;
                while (var4 > 0 && Block.lightOpacity[this.blocks[var5 + var4 - 1] & 0xFF] == 0) {
                    --var4;
                }
                this.heightMap[var3 << 4 | var2] = (byte)var4;
                if (var4 < var1) {
                    var1 = var4;
                }
                if (!this.worldObj.worldProvider.field_6478_e) {
                    int var6 = 15;
                    int var7 = 127;
                    do {
                        if ((var6 -= Block.lightOpacity[this.blocks[var5 + var7] & 0xFF]) <= 0) continue;
                        this.skylightMap.setNibble(var2, var7, var3, var6);
                    } while (--var7 > 0 && var6 > 0);
                }
                ++var3;
            }
            ++var2;
        }
        this.field_1532_i = var1;
        var2 = 0;
        while (var2 < 16) {
            var3 = 0;
            while (var3 < 16) {
                this.func_996_c(var2, var3);
                ++var3;
            }
            ++var2;
        }
        this.isModified = true;
    }

    public void func_4143_d() {
    }

    private void func_996_c(int var1, int var2) {
        int var3 = this.getHeightValue(var1, var2);
        int var4 = this.xPosition * 16 + var1;
        int var5 = this.zPosition * 16 + var2;
        this.func_1020_f(var4 - 1, var5, var3);
        this.func_1020_f(var4 + 1, var5, var3);
        this.func_1020_f(var4, var5 - 1, var3);
        this.func_1020_f(var4, var5 + 1, var3);
    }

    private void func_1020_f(int var1, int var2, int var3) {
        int var4 = this.worldObj.getHeightValue(var1, var2);
        if (var4 > var3) {
            this.worldObj.func_616_a(EnumSkyBlock.Sky, var1, var3, var2, var1, var4, var2);
            this.isModified = true;
        } else if (var4 < var3) {
            this.worldObj.func_616_a(EnumSkyBlock.Sky, var1, var4, var2, var1, var3, var2);
            this.isModified = true;
        }
    }

    private void func_1003_g(int var1, int var2, int var3) {
        int var4;
        int var5 = var4 = this.heightMap[var3 << 4 | var1] & 0xFF;
        if (var2 > var4) {
            var5 = var2;
        }
        int var6 = var1 << 11 | var3 << 7;
        while (var5 > 0 && Block.lightOpacity[this.blocks[var6 + var5 - 1] & 0xFF] == 0) {
            --var5;
        }
        if (var5 != var4) {
            int var9;
            int var8;
            int var7;
            this.worldObj.markBlocksDirtyVertical(var1, var3, var5, var4);
            this.heightMap[var3 << 4 | var1] = (byte)var5;
            if (var5 < this.field_1532_i) {
                this.field_1532_i = var5;
            } else {
                var7 = 127;
                var8 = 0;
                while (var8 < 16) {
                    var9 = 0;
                    while (var9 < 16) {
                        if ((this.heightMap[var9 << 4 | var8] & 0xFF) < var7) {
                            var7 = this.heightMap[var9 << 4 | var8] & 0xFF;
                        }
                        ++var9;
                    }
                    ++var8;
                }
                this.field_1532_i = var7;
            }
            var7 = this.xPosition * 16 + var1;
            var8 = this.zPosition * 16 + var3;
            if (var5 < var4) {
                var9 = var5;
                while (var9 < var4) {
                    this.skylightMap.setNibble(var1, var9, var3, 15);
                    ++var9;
                }
            } else {
                this.worldObj.func_616_a(EnumSkyBlock.Sky, var7, var4, var8, var7, var5, var8);
                var9 = var4;
                while (var9 < var5) {
                    this.skylightMap.setNibble(var1, var9, var3, 0);
                    ++var9;
                }
            }
            var9 = 15;
            int var10 = var5;
            while (var5 > 0 && var9 > 0) {
                int var11;
                if ((var11 = Block.lightOpacity[this.getBlockID(var1, --var5, var3)]) == 0) {
                    var11 = 1;
                }
                if ((var9 -= var11) < 0) {
                    var9 = 0;
                }
                this.skylightMap.setNibble(var1, var5, var3, var9);
            }
            while (var5 > 0 && Block.lightOpacity[this.getBlockID(var1, var5 - 1, var3)] == 0) {
                --var5;
            }
            if (var5 != var10) {
                this.worldObj.func_616_a(EnumSkyBlock.Sky, var7 - 1, var5, var8 - 1, var7 + 1, var10, var8 + 1);
            }
            this.isModified = true;
        }
    }

    public int getBlockID(int var1, int var2, int var3) {
        return this.blocks[var1 << 11 | var3 << 7 | var2] & 0xFF;
    }

    public boolean setBlockIDWithMetadata(int var1, int var2, int var3, int var4, int var5) {
        byte var6 = (byte)var4;
        int var7 = this.heightMap[var3 << 4 | var1] & 0xFF;
        int var8 = this.blocks[var1 << 11 | var3 << 7 | var2] & 0xFF;
        if (var8 == var4 && this.data.getNibble(var1, var2, var3) == var5) {
            return false;
        }
        int var9 = this.xPosition * 16 + var1;
        int var10 = this.zPosition * 16 + var3;
        this.blocks[var1 << 11 | var3 << 7 | var2] = (byte)(var6 & 0xFF);
        if (var8 != 0 && !this.worldObj.multiplayerWorld) {
            Block.blocksList[var8].onBlockRemoval(this.worldObj, var9, var2, var10);
        }
        this.data.setNibble(var1, var2, var3, var5);
        if (!this.worldObj.worldProvider.field_6478_e) {
            if (Block.lightOpacity[var6 & 0xFF] != 0) {
                if (var2 >= var7) {
                    this.func_1003_g(var1, var2 + 1, var3);
                }
            } else if (var2 == var7 - 1) {
                this.func_1003_g(var1, var2, var3);
            }
            this.worldObj.func_616_a(EnumSkyBlock.Sky, var9, var2, var10, var9, var2, var10);
        }
        this.worldObj.func_616_a(EnumSkyBlock.Block, var9, var2, var10, var9, var2, var10);
        this.func_996_c(var1, var3);
        this.data.setNibble(var1, var2, var3, var5);
        if (var4 != 0) {
            Block.blocksList[var4].onBlockAdded(this.worldObj, var9, var2, var10);
        }
        this.isModified = true;
        return true;
    }

    public boolean setBlockID(int var1, int var2, int var3, int var4) {
        byte var5 = (byte)var4;
        int var6 = this.heightMap[var3 << 4 | var1] & 0xFF;
        int var7 = this.blocks[var1 << 11 | var3 << 7 | var2] & 0xFF;
        if (var7 == var4) {
            return false;
        }
        int var8 = this.xPosition * 16 + var1;
        int var9 = this.zPosition * 16 + var3;
        this.blocks[var1 << 11 | var3 << 7 | var2] = (byte)(var5 & 0xFF);
        if (var7 != 0) {
            Block.blocksList[var7].onBlockRemoval(this.worldObj, var8, var2, var9);
        }
        this.data.setNibble(var1, var2, var3, 0);
        if (Block.lightOpacity[var5 & 0xFF] != 0) {
            if (var2 >= var6) {
                this.func_1003_g(var1, var2 + 1, var3);
            }
        } else if (var2 == var6 - 1) {
            this.func_1003_g(var1, var2, var3);
        }
        this.worldObj.func_616_a(EnumSkyBlock.Sky, var8, var2, var9, var8, var2, var9);
        this.worldObj.func_616_a(EnumSkyBlock.Block, var8, var2, var9, var8, var2, var9);
        this.func_996_c(var1, var3);
        if (var4 != 0 && !this.worldObj.multiplayerWorld) {
            Block.blocksList[var4].onBlockAdded(this.worldObj, var8, var2, var9);
        }
        this.isModified = true;
        return true;
    }

    public int getBlockMetadata(int var1, int var2, int var3) {
        return this.data.getNibble(var1, var2, var3);
    }

    public void setBlockMetadata(int var1, int var2, int var3, int var4) {
        this.isModified = true;
        this.data.setNibble(var1, var2, var3, var4);
    }

    public int getSavedLightValue(EnumSkyBlock var1, int var2, int var3, int var4) {
        if (var1 == EnumSkyBlock.Sky) {
            return this.skylightMap.getNibble(var2, var3, var4);
        }
        return var1 == EnumSkyBlock.Block ? this.blocklightMap.getNibble(var2, var3, var4) : 0;
    }

    public void setLightValue(EnumSkyBlock var1, int var2, int var3, int var4, int var5) {
        this.isModified = true;
        if (var1 == EnumSkyBlock.Sky) {
            this.skylightMap.setNibble(var2, var3, var4, var5);
        } else {
            if (var1 != EnumSkyBlock.Block) {
                return;
            }
            this.blocklightMap.setNibble(var2, var3, var4, var5);
        }
    }

    public int getBlockLightValue(int var1, int var2, int var3, int var4) {
        int var6;
        int var5 = this.skylightMap.getNibble(var1, var2, var3);
        if (var5 > 0) {
            isLit = true;
        }
        if ((var6 = this.blocklightMap.getNibble(var1, var2, var3)) > (var5 -= var4)) {
            var5 = var6;
        }
        return var5;
    }

    public void addEntity(Entity var1) {
        int var4;
        this.hasEntities = true;
        int var2 = MathHelper.floor_double(var1.posX / 16.0);
        int var3 = MathHelper.floor_double(var1.posZ / 16.0);
        if (var2 != this.xPosition || var3 != this.zPosition) {
            System.out.println("Wrong location! " + var1);
            Thread.dumpStack();
        }
        if ((var4 = MathHelper.floor_double(var1.posY / 16.0)) < 0) {
            var4 = 0;
        }
        if (var4 >= this.entities.length) {
            var4 = this.entities.length - 1;
        }
        var1.addedToChunk = true;
        var1.chunkCoordX = this.xPosition;
        var1.chunkCoordY = var4;
        var1.chunkCoordZ = this.zPosition;
        this.entities[var4].add(var1);
    }

    public void func_1015_b(Entity var1) {
        this.func_1016_a(var1, var1.chunkCoordY);
    }

    public void func_1016_a(Entity var1, int var2) {
        if (var2 < 0) {
            var2 = 0;
        }
        if (var2 >= this.entities.length) {
            var2 = this.entities.length - 1;
        }
        this.entities[var2].remove(var1);
    }

    public boolean canBlockSeeTheSky(int var1, int var2, int var3) {
        return var2 >= (this.heightMap[var3 << 4 | var1] & 0xFF);
    }

    public TileEntity getChunkBlockTileEntity(int var1, int var2, int var3) {
        ChunkPosition var4 = new ChunkPosition(var1, var2, var3);
        TileEntity var5 = (TileEntity)this.chunkTileEntityMap.get(var4);
        if (var5 == null) {
            int var6 = this.getBlockID(var1, var2, var3);
            if (!Block.isBlockContainer[var6]) {
                return null;
            }
            BlockContainer var7 = (BlockContainer)Block.blocksList[var6];
            var7.onBlockAdded(this.worldObj, this.xPosition * 16 + var1, var2, this.zPosition * 16 + var3);
            var5 = (TileEntity)this.chunkTileEntityMap.get(var4);
        }
        return var5;
    }

    public void func_1001_a(TileEntity var1) {
        int var2 = var1.xCoord - this.xPosition * 16;
        int var3 = var1.yCoord;
        int var4 = var1.zCoord - this.zPosition * 16;
        this.setChunkBlockTileEntity(var2, var3, var4, var1);
    }

    public void setChunkBlockTileEntity(int var1, int var2, int var3, TileEntity var4) {
        ChunkPosition var5 = new ChunkPosition(var1, var2, var3);
        var4.worldObj = this.worldObj;
        var4.xCoord = this.xPosition * 16 + var1;
        var4.yCoord = var2;
        var4.zCoord = this.zPosition * 16 + var3;
        if (this.getBlockID(var1, var2, var3) != 0 && Block.blocksList[this.getBlockID(var1, var2, var3)] instanceof BlockContainer) {
            if (this.isChunkLoaded) {
                if (this.chunkTileEntityMap.get(var5) != null) {
                    this.worldObj.loadedTileEntityList.remove(this.chunkTileEntityMap.get(var5));
                }
                this.worldObj.loadedTileEntityList.add(var4);
            }
            this.chunkTileEntityMap.put(var5, var4);
        } else {
            System.out.println("Attempted to place a tile entity where there was no entity tile!");
        }
    }

    public void removeChunkBlockTileEntity(int var1, int var2, int var3) {
        ChunkPosition var4 = new ChunkPosition(var1, var2, var3);
        if (this.isChunkLoaded) {
            this.worldObj.loadedTileEntityList.remove(this.chunkTileEntityMap.remove(var4));
        }
    }

    public void onChunkLoad() {
        this.isChunkLoaded = true;
        this.worldObj.loadedTileEntityList.addAll(this.chunkTileEntityMap.values());
        int var1 = 0;
        while (var1 < this.entities.length) {
            this.worldObj.func_636_a(this.entities[var1]);
            ++var1;
        }
    }

    public void onChunkUnload() {
        this.isChunkLoaded = false;
        this.worldObj.loadedTileEntityList.removeAll(this.chunkTileEntityMap.values());
        int var1 = 0;
        while (var1 < this.entities.length) {
            this.worldObj.func_632_b(this.entities[var1]);
            ++var1;
        }
    }

    public void setChunkModified() {
        this.isModified = true;
    }

    public void getEntitiesWithinAABBForEntity(Entity var1, AxisAlignedBB var2, List var3) {
        int var4 = MathHelper.floor_double((var2.minY - 2.0) / 16.0);
        int var5 = MathHelper.floor_double((var2.maxY + 2.0) / 16.0);
        if (var4 < 0) {
            var4 = 0;
        }
        if (var5 >= this.entities.length) {
            var5 = this.entities.length - 1;
        }
        int var6 = var4;
        while (var6 <= var5) {
            List var7 = this.entities[var6];
            int var8 = 0;
            while (var8 < var7.size()) {
                Entity var9 = (Entity)var7.get(var8);
                if (var9 != var1 && var9.boundingBox.intersectsWith(var2)) {
                    var3.add(var9);
                }
                ++var8;
            }
            ++var6;
        }
    }

    public void getEntitiesOfTypeWithinAAAB(Class var1, AxisAlignedBB var2, List var3) {
        int var4 = MathHelper.floor_double((var2.minY - 2.0) / 16.0);
        int var5 = MathHelper.floor_double((var2.maxY + 2.0) / 16.0);
        if (var4 < 0) {
            var4 = 0;
        }
        if (var5 >= this.entities.length) {
            var5 = this.entities.length - 1;
        }
        int var6 = var4;
        while (var6 <= var5) {
            List var7 = this.entities[var6];
            int var8 = 0;
            while (var8 < var7.size()) {
                Entity var9 = (Entity)var7.get(var8);
                if (var1.isAssignableFrom(var9.getClass()) && var9.boundingBox.intersectsWith(var2)) {
                    var3.add(var9);
                }
                ++var8;
            }
            ++var6;
        }
    }

    public boolean needsSaving(boolean var1) {
        if (this.neverSave) {
            return false;
        }
        if (var1 ? this.hasEntities && this.worldObj.getWorldTime() != this.lastSaveTime : this.hasEntities && this.worldObj.getWorldTime() >= this.lastSaveTime + 600L) {
            return true;
        }
        return this.isModified;
    }

    public int setChunkData(byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
        int var12;
        int var11;
        int var10;
        int var9 = var2;
        while (var9 < var5) {
            var10 = var4;
            while (var10 < var7) {
                var11 = var9 << 11 | var10 << 7 | var3;
                var12 = var6 - var3;
                System.arraycopy(var1, var8, this.blocks, var11, var12);
                var8 += var12;
                ++var10;
            }
            ++var9;
        }
        this.generateHeightMap();
        var9 = var2;
        while (var9 < var5) {
            var10 = var4;
            while (var10 < var7) {
                var11 = (var9 << 11 | var10 << 7 | var3) >> 1;
                var12 = (var6 - var3) / 2;
                System.arraycopy(var1, var8, this.data.data, var11, var12);
                var8 += var12;
                ++var10;
            }
            ++var9;
        }
        var9 = var2;
        while (var9 < var5) {
            var10 = var4;
            while (var10 < var7) {
                var11 = (var9 << 11 | var10 << 7 | var3) >> 1;
                var12 = (var6 - var3) / 2;
                System.arraycopy(var1, var8, this.blocklightMap.data, var11, var12);
                var8 += var12;
                ++var10;
            }
            ++var9;
        }
        var9 = var2;
        while (var9 < var5) {
            var10 = var4;
            while (var10 < var7) {
                var11 = (var9 << 11 | var10 << 7 | var3) >> 1;
                var12 = (var6 - var3) / 2;
                System.arraycopy(var1, var8, this.skylightMap.data, var11, var12);
                var8 += var12;
                ++var10;
            }
            ++var9;
        }
        this.isFilled = true;
        return var8;
    }

    public Random func_997_a(long var1) {
        return new Random(this.worldObj.getRandomSeed() + (long)(this.xPosition * this.xPosition * 4987142) + (long)(this.xPosition * 5947611) + (long)(this.zPosition * this.zPosition) * 4392871L + (long)(this.zPosition * 389711) ^ var1);
    }

    public boolean func_21167_h() {
        return false;
    }

    public void func_25124_i() {
        ChunkBlockMap.func_26002_a(this.blocks);
    }

    public void importOldChunkTileEntities() {
        NBTTagCompound nbttagcompound;
        DataInputStream datainputstream;
        File file = Chunk.wc.downloadSaveHandler.getSaveDirectory();
        if (Chunk.wc.worldProvider instanceof WorldProviderHell) {
            file = new File(file, "DIM-1");
            file.mkdirs();
        }
        if ((datainputstream = RegionFileCache.getChunkInputStream(file, this.xPosition, this.zPosition)) != null) {
            try {
                nbttagcompound = CompressedStreamTools.func_1141_a(datainputstream);
            }
            catch (IOException ioexception) {
                return;
            }
        } else {
            return;
        }
        if (!nbttagcompound.hasKey("Level")) {
            return;
        }
        NBTTagList nbttaglist = nbttagcompound.getCompoundTag("Level").getTagList("TileEntities");
        if (nbttaglist != null) {
            int i = 0;
            while (i < nbttaglist.tagCount()) {
                NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
                TileEntity tileentity = TileEntity.createAndLoadEntity(nbttagcompound1);
                if (tileentity != null) {
                    ChunkPosition chunkposition = new ChunkPosition(tileentity.xCoord & 0xF, tileentity.yCoord, tileentity.zCoord & 0xF);
                    this.newChunkTileEntityMap.put(chunkposition, tileentity);
                }
                ++i;
            }
        }
    }
}

