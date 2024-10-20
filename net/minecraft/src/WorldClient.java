/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ChunkProviderClient;
import net.minecraft.src.Entity;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.IWorldAccess;
import net.minecraft.src.MCHashTable;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.Packet255KickDisconnect;
import net.minecraft.src.SaveHandler;
import net.minecraft.src.SaveHandlerMP;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityNote;
import net.minecraft.src.World;
import net.minecraft.src.WorldBlockPositionType;
import net.minecraft.src.WorldProvider;
import net.skidcode.gh.maybeaclient.hacks.LockTimeHack;

public class WorldClient
extends World {
    private LinkedList field_1057_z = new LinkedList();
    private NetClientHandler sendQueue;
    private ChunkProviderClient field_20915_C;
    private MCHashTable field_1055_D = new MCHashTable();
    private Set field_20914_E = new HashSet();
    private Set field_1053_F = new HashSet();
    public SaveHandler downloadSaveHandler;
    public IChunkLoader downloadChunkLoader;
    public boolean downloadThisWorld = false;

    public WorldClient(NetClientHandler var1, long var2, int var4) {
        super((ISaveHandler)new SaveHandlerMP(), "MpServer", WorldProvider.func_4101_a(var4), var2);
        this.sendQueue = var1;
        this.setSpawnPoint(new ChunkCoordinates(8, 64, 8));
    }

    @Override
    public void saveWorld(boolean var1, IProgressUpdate var2) {
        if (this.downloadThisWorld) {
            this.downloadSaveHandler.saveWorldInfoAndPlayer(this.worldInfo, this.playerEntities);
            this.chunkProvider.saveChunks(var1, var2);
        }
        super.saveWorld(var1, var2);
    }

    @Override
    public void playNoteAt(int x, int y, int z, int var4, int var5) {
        super.playNoteAt(x, y, z, var4, var5);
        if (!this.downloadThisWorld) {
            return;
        }
        if (this.getBlockId(x, y, z) == Block.musicBlock.blockID) {
            TileEntityNote tileentitynote = (TileEntityNote)this.getBlockTileEntity(x, y, z);
            if (tileentitynote == null) {
                this.setBlockTileEntity(x, y, z, new TileEntityNote());
            }
            tileentitynote.note = (byte)(var5 % 25);
            tileentitynote.x_();
            this.setNewBlockTileEntity(x, y, z, tileentitynote);
        }
    }

    public void setNewBlockTileEntity(int x, int y, int z, TileEntity tile) {
        Chunk chunk = this.getChunkFromChunkCoords(x >> 4, z >> 4);
        if (chunk != null) {
            chunk.setNewChunkBlockTileEntity(x & 0xF, y, z & 0xF, tile);
        }
    }

    @Override
    public void tick() {
        int var2;
        int var1;
        this.setWorldTime(this.getWorldTime() + 1L);
        long oldTime = this.getWorldTime();
        if (LockTimeHack.INSTANCE.status) {
            this.worldInfo.setWorldTime(LockTimeHack.INSTANCE.lockedTime.value);
        }
        if ((var1 = this.calculateSkylightSubtracted(1.0f)) != this.skylightSubtracted) {
            this.skylightSubtracted = var1;
            var2 = 0;
            while (var2 < this.worldAccesses.size()) {
                ((IWorldAccess)this.worldAccesses.get(var2)).updateAllRenderers();
                ++var2;
            }
        }
        if (LockTimeHack.INSTANCE.status) {
            this.worldInfo.setWorldTime(oldTime);
        }
        var2 = 0;
        while (var2 < 10 && !this.field_1053_F.isEmpty()) {
            Entity var3 = (Entity)this.field_1053_F.iterator().next();
            if (!this.loadedEntityList.contains(var3)) {
                this.entityJoinedWorld(var3);
            }
            ++var2;
        }
        this.sendQueue.processReadPackets();
        var2 = 0;
        while (var2 < this.field_1057_z.size()) {
            WorldBlockPositionType var4 = (WorldBlockPositionType)this.field_1057_z.get(var2);
            if (--var4.field_1206_d == 0) {
                super.setBlockAndMetadata(var4.field_1202_a, var4.field_1201_b, var4.field_1207_c, var4.field_1205_e, var4.field_1204_f);
                super.markBlockNeedsUpdate(var4.field_1202_a, var4.field_1201_b, var4.field_1207_c);
                this.field_1057_z.remove(var2--);
            }
            ++var2;
        }
    }

    public void func_711_c(int var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = 0;
        while (var7 < this.field_1057_z.size()) {
            WorldBlockPositionType var8 = (WorldBlockPositionType)this.field_1057_z.get(var7);
            if (var8.field_1202_a >= var1 && var8.field_1201_b >= var2 && var8.field_1207_c >= var3 && var8.field_1202_a <= var4 && var8.field_1201_b <= var5 && var8.field_1207_c <= var6) {
                this.field_1057_z.remove(var7--);
            }
            ++var7;
        }
    }

    @Override
    protected IChunkProvider getChunkProvider() {
        this.field_20915_C = new ChunkProviderClient(this);
        return this.field_20915_C;
    }

    @Override
    public void setSpawnLocation() {
        this.setSpawnPoint(new ChunkCoordinates(8, 64, 8));
    }

    @Override
    protected void updateBlocksAndPlayCaveSounds() {
    }

    @Override
    public void scheduleBlockUpdate(int var1, int var2, int var3, int var4, int var5) {
    }

    @Override
    public boolean TickUpdates(boolean var1) {
        return false;
    }

    public void func_713_a(int var1, int var2, boolean var3) {
        if (var3) {
            this.field_20915_C.func_538_d(var1, var2);
        } else {
            this.field_20915_C.func_539_c(var1, var2);
        }
        if (!var3) {
            this.markBlocksDirty(var1 * 16, 0, var2 * 16, var1 * 16 + 15, 128, var2 * 16 + 15);
        }
    }

    @Override
    public boolean entityJoinedWorld(Entity var1) {
        boolean var2 = super.entityJoinedWorld(var1);
        this.field_20914_E.add(var1);
        if (!var2) {
            this.field_1053_F.add(var1);
        }
        return var2;
    }

    @Override
    public void setEntityDead(Entity var1) {
        super.setEntityDead(var1);
        this.field_20914_E.remove(var1);
    }

    @Override
    protected void obtainEntitySkin(Entity var1) {
        super.obtainEntitySkin(var1);
        if (this.field_1053_F.contains(var1)) {
            this.field_1053_F.remove(var1);
        }
    }

    @Override
    protected void releaseEntitySkin(Entity var1) {
        super.releaseEntitySkin(var1);
        if (this.field_20914_E.contains(var1)) {
            this.field_1053_F.add(var1);
        }
    }

    public void func_712_a(int var1, Entity var2) {
        Entity var3 = this.func_709_b(var1);
        if (var3 != null) {
            this.setEntityDead(var3);
        }
        this.field_20914_E.add(var2);
        var2.entityId = var1;
        if (!this.entityJoinedWorld(var2)) {
            this.field_1053_F.add(var2);
        }
        this.field_1055_D.addKey(var1, var2);
    }

    public Entity func_709_b(int var1) {
        return (Entity)this.field_1055_D.lookup(var1);
    }

    public Entity removeEntityFromWorld(int var1) {
        Entity var2 = (Entity)this.field_1055_D.removeObject(var1);
        if (var2 != null) {
            this.field_20914_E.remove(var2);
            this.setEntityDead(var2);
        }
        return var2;
    }

    @Override
    public boolean setBlockMetadata(int var1, int var2, int var3, int var4) {
        int var5 = this.getBlockId(var1, var2, var3);
        int var6 = this.getBlockMetadata(var1, var2, var3);
        if (super.setBlockMetadata(var1, var2, var3, var4)) {
            this.field_1057_z.add(new WorldBlockPositionType(this, var1, var2, var3, var5, var6));
            return true;
        }
        return false;
    }

    @Override
    public boolean setBlockAndMetadata(int var1, int var2, int var3, int var4, int var5) {
        int var6 = this.getBlockId(var1, var2, var3);
        int var7 = this.getBlockMetadata(var1, var2, var3);
        if (super.setBlockAndMetadata(var1, var2, var3, var4, var5)) {
            this.field_1057_z.add(new WorldBlockPositionType(this, var1, var2, var3, var6, var7));
            return true;
        }
        return false;
    }

    @Override
    public boolean setBlock(int var1, int var2, int var3, int var4) {
        int var5 = this.getBlockId(var1, var2, var3);
        int var6 = this.getBlockMetadata(var1, var2, var3);
        if (super.setBlock(var1, var2, var3, var4)) {
            this.field_1057_z.add(new WorldBlockPositionType(this, var1, var2, var3, var5, var6));
            return true;
        }
        return false;
    }

    public boolean func_714_c(int var1, int var2, int var3, int var4, int var5) {
        this.func_711_c(var1, var2, var3, var1, var2, var3);
        if (super.setBlockAndMetadata(var1, var2, var3, var4, var5)) {
            this.notifyBlockChange(var1, var2, var3, var4);
            return true;
        }
        return false;
    }

    @Override
    public void sendQuittingDisconnectingPacket() {
        this.sendQueue.addToSendQueue(new Packet255KickDisconnect("Quitting"));
    }
}

