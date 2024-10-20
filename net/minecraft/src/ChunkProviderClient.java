/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.src.Block;
import net.minecraft.src.BlockChest;
import net.minecraft.src.BlockDispenser;
import net.minecraft.src.BlockFurnace;
import net.minecraft.src.BlockNote;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkCoordIntPair;
import net.minecraft.src.EmptyChunk;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.WorldClient;

public class ChunkProviderClient
implements IChunkProvider {
    private Chunk blankChunk;
    private Map chunkMapping = new HashMap();
    private List field_889_c = new ArrayList();
    private World worldObj;

    public ChunkProviderClient(World var1) {
        this.blankChunk = new EmptyChunk(var1, new byte[32768], 0, 0);
        this.worldObj = var1;
    }

    @Override
    public boolean chunkExists(int var1, int var2) {
        ChunkCoordIntPair var3 = new ChunkCoordIntPair(var1, var2);
        return this.chunkMapping.containsKey(var3);
    }

    public void func_539_c(int var1, int var2) {
        Chunk var3 = this.provideChunk(var1, var2);
        if (!var3.func_21167_h()) {
            var3.onChunkUnload();
        }
        if (((WorldClient)this.worldObj).downloadThisWorld && !var3.neverSave && var3.isFilled) {
            this.saveChunk(var3);
            try {
                ((WorldClient)this.worldObj).downloadChunkLoader.saveExtraChunkData(this.worldObj, var3);
            }
            catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
        }
        this.chunkMapping.remove(new ChunkCoordIntPair(var1, var2));
        this.field_889_c.remove(var3);
    }

    @Override
    public Chunk func_538_d(int var1, int var2) {
        ChunkCoordIntPair var3 = new ChunkCoordIntPair(var1, var2);
        byte[] var4 = new byte[32768];
        Chunk var5 = new Chunk(this.worldObj, var4, var1, var2);
        Arrays.fill(var5.skylightMap.data, (byte)-1);
        this.chunkMapping.put(var3, var5);
        var5.isChunkLoaded = true;
        if (((WorldClient)this.worldObj).downloadThisWorld) {
            var5.importOldChunkTileEntities();
        }
        return var5;
    }

    @Override
    public Chunk provideChunk(int var1, int var2) {
        ChunkCoordIntPair var3 = new ChunkCoordIntPair(var1, var2);
        Chunk var4 = (Chunk)this.chunkMapping.get(var3);
        return var4 == null ? this.blankChunk : var4;
    }

    @Override
    public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
        if (!((WorldClient)this.worldObj).downloadThisWorld) {
            return true;
        }
        for (Object obj : this.chunkMapping.keySet()) {
            Chunk chunk = (Chunk)this.chunkMapping.get(obj);
            if (flag && chunk != null && !chunk.neverSave && chunk.isFilled) {
                try {
                    ((WorldClient)this.worldObj).downloadChunkLoader.saveExtraChunkData(this.worldObj, chunk);
                }
                catch (IOException ioexception) {
                    ioexception.printStackTrace();
                }
            }
            if (chunk == null || chunk.neverSave || !chunk.isFilled) continue;
            this.saveChunk(chunk);
        }
        if (flag) {
            ((WorldClient)this.worldObj).downloadChunkLoader.saveExtraData();
        }
        return true;
    }

    private void saveChunk(Chunk chunk) {
        if (!((WorldClient)this.worldObj).downloadThisWorld) {
            return;
        }
        chunk.lastSaveTime = this.worldObj.getWorldTime();
        chunk.isTerrainPopulated = true;
        try {
            for (Object obj : chunk.newChunkTileEntityMap.keySet()) {
                Block block;
                TileEntity tileentity = (TileEntity)chunk.newChunkTileEntityMap.get(obj);
                if (tileentity == null || !((block = Block.blocksList[this.worldObj.getBlockId(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord)]) instanceof BlockChest) && !(block instanceof BlockDispenser) && !(block instanceof BlockFurnace) && !(block instanceof BlockNote)) continue;
                chunk.chunkTileEntityMap.put(obj, tileentity);
            }
            ((WorldClient)this.worldObj).downloadChunkLoader.saveChunk(this.worldObj, chunk);
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    @Override
    public boolean func_532_a() {
        return false;
    }

    @Override
    public boolean func_536_b() {
        return false;
    }

    @Override
    public void populate(IChunkProvider var1, int var2, int var3) {
    }

    @Override
    public String toString() {
        return "MultiplayerChunkCache: " + this.chunkMapping.size();
    }

    public void importOldTileEntities() {
        for (Object obj : this.chunkMapping.keySet()) {
            Chunk chunk = (Chunk)this.chunkMapping.get(obj);
            if (chunk == null || !chunk.isFilled) continue;
            chunk.importOldChunkTileEntities();
        }
    }
}

